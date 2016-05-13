package amtc.gue.ws.test.books.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRemovalException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;

/**
 * Testclass for the Book DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookDAOTest extends JPATest {

	private static final String BOOK_TITLE_FOR_TESTING = "BookTitleForTesting";

	@Test
	public void testDAOSetUp() {
		assertNotNull(bookEntityDAO);
		assertNotNull(failureBookEntityDAO);
	}

	@Test
	public void testAddSimpleBookEntity() throws EntityPersistenceException,
			EntityRetrievalException {
		assertNull(bookEntity1.getKey());
		bookEntityDAO.persistEntity(bookEntity1);
		assertNotNull(bookEntity1.getKey());
	}

	@Test
	public void testAddBookEntityWithSingleTag()
			throws EntityPersistenceException {
		tagEntityDAO.persistEntity(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity1);
		bookEntityDAO.persistEntity(bookEntity1);
		assertEquals(1, bookEntity1.getTags().size());
	}

	@Test
	public void testAddBookEntityWithMultipleTags()
			throws EntityPersistenceException, EntityRetrievalException {
		// persist bookentity and add two tagentities to it
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		bookEntity1.addToTagsAndBooks(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity2);
		assertEquals(2, bookEntity1.getTags().size());
		assertNull(bookEntity1.getKey());
		bookEntityDAO.persistEntity(bookEntity1);
		assertNotNull(bookEntity1.getKey());
		assertEquals(1, bookEntityDAO.findAllEntities().size());
		assertEquals(2, tagEntityDAO.findAllEntities().size());

	}

	@Test(expected = EntityPersistenceException.class)
	public void testAddSameBookEntityTwice() throws EntityPersistenceException {
		// add be1 to the datastore two times
		// EntityPersistenceException is expected
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity1);
	}

	@Test(expected = EntityPersistenceException.class)
	public void testAddBookEntityUsingInvalidEM()
			throws EntityPersistenceException {
		failureBookEntityDAO.persistEntity(bookEntity1);
	}

	@Test
	public void testGetAllBookEntitiesWithoutAdding()
			throws EntityRetrievalException {
		assertEquals(0, bookEntityDAO.findAllEntities().size());
	}

	@Test
	public void testGetAllBookEntitiesAfterAddingSimpleBookEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		bookEntityDAO.persistEntity(bookEntity3);
		bookEntityDAO.persistEntity(bookEntity4);
		bookEntityDAO.persistEntity(bookEntity5);
		List<GAEJPABookEntity> foundBooks = bookEntityDAO.findAllEntities();
		assertEquals(5, foundBooks.size());
	}

	@Test
	public void testGetAllBookEntitiesAfterAddingComplexBookEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		tagEntityDAO.persistEntity(tagEntity3);
		bookEntity1.addToTagsAndBooks(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity2);
		bookEntity1.addToTagsAndBooks(tagEntity3);
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		List<GAEJPABookEntity> foundBooks = bookEntityDAO.findAllEntities();
		List<GAEJPATagEntity> foundTags = tagEntityDAO.findAllEntities();
		assertEquals(2, foundBooks.size());
		assertEquals(3, foundTags.size());
		List<GAEJPATagEntity> bookTags = new ArrayList<GAEJPATagEntity>(
				foundBooks.get(0).getTags());
		assertEquals(3, bookTags.size());
	}

	@Test(expected = EntityRetrievalException.class)
	public void testGetAllBookEntitiesUsingInvalidEM()
			throws EntityRetrievalException {
		failureBookEntityDAO.findAllEntities();
	}

	@Test
	public void testGetSimpleBookEntity() throws EntityPersistenceException,
			EntityRetrievalException {
		bookEntityDAO.persistEntity(bookEntity1);
		GAEJPABookEntity retrievedEntity = bookEntityDAO
				.findEntityById(bookEntity1.getKey());
		assertNotNull(retrievedEntity);
		assertEquals(bookEntity1.getKey(), retrievedEntity.getKey());
	}

	@Test
	public void testGetComplexBookEntity() throws EntityPersistenceException,
			EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		tagEntityDAO.persistEntity(tagEntity3);
		bookEntity1.addToTagsAndBooks(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity2);
		bookEntity1.addToTagsAndBooks(tagEntity3);
		bookEntityDAO.persistEntity(bookEntity1);

		GAEJPABookEntity retrievedEntity = bookEntityDAO
				.findEntityById(bookEntity1.getKey());
		assertNotNull(retrievedEntity);
		assertEquals(bookEntity1.getKey(), retrievedEntity.getKey());
		assertNotNull(retrievedEntity.getTags());
	}

	@Test(expected = EntityRetrievalException.class)
	public void testGetBookEntityWithNullID() throws EntityRetrievalException {
		bookEntityDAO.findEntityById(null);
	}

	@Test(expected = EntityRetrievalException.class)
	public void testGetBookEntityUsingInvalidEM()
			throws EntityRetrievalException, EntityPersistenceException {
		bookEntityDAO.persistEntity(bookEntity1);
		failureBookEntityDAO.findEntityById(bookEntity1.getKey());
	}
	
	@Test
	public void testDeleteSimpleBookEntity() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		assertEquals(2, bookEntityDAO.findAllEntities().size());
		bookEntityDAO.removeEntity(bookEntity2);
		assertEquals(1, bookEntityDAO.findAllEntities().size());
	}

	@Test
	public void testDeleteComplexBookEntity()
			throws EntityPersistenceException, EntityRetrievalException,
			EntityRemovalException {
		tagEntityDAO.persistEntity(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity1);
		bookEntityDAO.persistEntity(bookEntity1);

		assertEquals(1, bookEntityDAO.findAllEntities().size());
		assertEquals(1, tagEntityDAO.findAllEntities().size());

		GAEJPABookEntity foundBook = bookEntityDAO.findEntityById(bookEntity1
				.getKey());
		bookEntityDAO.removeEntity(foundBook);
		assertEquals(0, bookEntityDAO.findAllEntities().size());
		assertEquals(1, tagEntityDAO.findAllEntities().size());

	}

	@Test(expected = EntityRemovalException.class)
	public void testDeleteBookEntityBeforeAddingEntity()
			throws EntityRemovalException {
		bookEntityDAO.removeEntity(bookEntity1);
	}

	@Test(expected = EntityRemovalException.class)
	public void testDeleteBookEntityUsingInvalidEM()
			throws EntityRemovalException {
		failureBookEntityDAO.removeEntity(bookEntity1);
	}

	@Test
	public void testUpdateSimpleBookEntity() throws EntityRetrievalException,
			EntityPersistenceException {
		bookEntityDAO.persistEntity(bookEntity1);
		assertEquals(TEST_AUTHOR_A, bookEntity1.getAuthor());
		String entityKey = bookEntity1.getKey();

		bookEntity1.setAuthor(TEST_AUTHOR_B);
		bookEntityDAO.updateEntity(bookEntity1);
		assertEquals(entityKey, bookEntity1.getKey());

		// retrieve that item from DB and check some values
		GAEJPABookEntity retrievedEntity = bookEntityDAO
				.findEntityById(bookEntity1.getKey());
		assertTrue(TEST_AUTHOR_B.equals(retrievedEntity.getAuthor()));
	}

	@Test
	public void testUpdateComplexBookEntity()
			throws EntityPersistenceException, EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity1);
		bookEntityDAO.persistEntity(bookEntity1);
		assertEquals(1, bookEntityDAO.findAllEntities().size());
		assertEquals(1, tagEntityDAO.findAllEntities().size());
		assertEquals(1, bookEntity1.getTags().size());

		GAEJPABookEntity foundBookEntity = bookEntityDAO.findEntityById(bookEntity1
				.getKey());
		ArrayList<GAEJPATagEntity> list = new ArrayList<GAEJPATagEntity>(
				foundBookEntity.getTags());
		assertNotNull(list);
		GAEJPATagEntity firstTag = list.get(0);
		assertEquals(TAG_NAME_A, firstTag.getTagName());

		// update entity
		tagEntity1.setTagName(TAG_NAME_C);
		tagEntityDAO.updateEntity(tagEntity1);
		bookEntityDAO.updateEntity(bookEntity1);

		GAEJPABookEntity retrievedEntity = bookEntityDAO
				.findEntityById(bookEntity1.getKey());
		list = new ArrayList<GAEJPATagEntity>(retrievedEntity.getTags());
		GAEJPATagEntity firstRetrievedTag = list.get(0);
		assertEquals(TAG_NAME_C, firstRetrievedTag.getTagName());
	}

	@Test(expected = EntityPersistenceException.class)
	public void testUpdateBookEntityWithoutAdding()
			throws EntityPersistenceException {
		// try updating be1 without initially adding
		bookEntityDAO.updateEntity(bookEntity1);
	}

	@Test(expected = EntityPersistenceException.class)
	public void testUpdateBookEntityUsingInvalidEM()
			throws EntityPersistenceException {
		failureBookEntityDAO.updateEntity(bookEntity1);
	}

	@Test
	public void testFindSpecificSimpleBookEntity()
			throws EntityRetrievalException, EntityPersistenceException {
		// add be1 and assure that it is found
		bookEntityDAO.persistEntity(bookEntity1);
		List<GAEJPABookEntity> foundBooks = bookEntityDAO
				.findSpecificEntity(bookEntity1);
		assertEquals(1, foundBooks.size());
	}

	@Test
	public void testFindSpecificComplexBookEntity()
			throws EntityPersistenceException, EntityRetrievalException {
		bookEntity1.setTitle(null);
		tagEntityDAO.persistEntity(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity1);
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		List<GAEJPABookEntity> foundBooks = bookEntityDAO
				.findSpecificEntity(bookEntity1);
		assertEquals(1, foundBooks.size());
	}

	@Test
	public void testFindSpecificComplexTagEntityByCommonSearchCriteria()
			throws EntityPersistenceException, EntityRetrievalException {
		bookEntity1.setTitle(BOOK_TITLE_FOR_TESTING);
		bookEntity4.setTitle(BOOK_TITLE_FOR_TESTING);
		bookEntity5.setTitle(BOOK_TITLE_FOR_TESTING);
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity4);
		bookEntityDAO.persistEntity(bookEntity5);

		GAEJPABookEntity searchBook = new GAEJPABookEntity();
		searchBook.setTitle(BOOK_TITLE_FOR_TESTING);

		List<GAEJPABookEntity> foundBooks = bookEntityDAO
				.findSpecificEntity(searchBook);
		assertEquals(3, foundBooks.size());
	}

	@Test
	public void testFindSpecificBookEntityWithoutPersising()
			throws EntityRetrievalException {
		List<GAEJPABookEntity> foundBooks = bookEntityDAO
				.findSpecificEntity(bookEntity1);
		assertEquals(0, foundBooks.size());
	}

	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificBookEntityusinInvalidEM()
			throws EntityRetrievalException {
		failureBookEntityDAO.findSpecificEntity(bookEntity1);
	}

	@Test
	public void testGetEntityByTagSingleResult()
			throws EntityRetrievalException, EntityPersistenceException {
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		tagEntityDAO.persistEntity(tagEntity4);
		bookEntity1.addToTagsAndBooks(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity2);
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntity2.addToTagsAndBooks(tagEntity2);
		bookEntity2.addToTagsAndBooks(tagEntity4);
		bookEntityDAO.persistEntity(bookEntity2);
		List<GAEJPABookEntity> foundBooksHavingTagAB = bookEntityDAO
				.getBookEntityByTag(tagsAB);
		assertEquals(1, foundBooksHavingTagAB.size());
	}

	@Test
	public void testGetEntityByTagMultipleResult()
			throws EntityPersistenceException, EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		bookEntity1.addToTagsAndBooks(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity2);
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntity2.addToTagsAndBooks(tagEntity1);
		bookEntity2.addToTagsAndBooks(tagEntity2);
		bookEntityDAO.persistEntity(bookEntity2);
		bookEntity3.addToTagsAndBooks(tagEntity1);
		bookEntityDAO.persistEntity(bookEntity3);
		bookEntity4.addToTagsAndBooks(tagEntity1);
		bookEntityDAO.persistEntity(bookEntity4);
		List<GAEJPABookEntity> foundBooksHavingTagA = bookEntityDAO
				.getBookEntityByTag(tagsA);
		List<GAEJPABookEntity> foundBooksHavingTagB = bookEntityDAO
				.getBookEntityByTag(tagsB);
		assertEquals("Incorrect Number of Bookentities with Tag A", 4,
				foundBooksHavingTagA.size());
		assertEquals("Incorrect Number of Bookentities with Tag B", 2,
				foundBooksHavingTagB.size());
	}

	@Test
	public void testGetEntityByTagOnlyBooksWithoutTags()
			throws EntityRetrievalException, EntityPersistenceException {
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		List<GAEJPABookEntity> foundBooksHavingTagAB = bookEntityDAO
				.getBookEntityByTag(tagsAB);
		assertEquals(0,foundBooksHavingTagAB.size());
	}

	@Test
	public void testGetEntityByTagNoBooksPersisted()
			throws EntityRetrievalException {
		List<GAEJPABookEntity> foundBooksHavingTagAB = bookEntityDAO
				.getBookEntityByTag(tagsAB);
		assertEquals(0,foundBooksHavingTagAB.size());
	}

	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByTagUsingInvalidEM() throws EntityRetrievalException {
		failureBookEntityDAO.getBookEntityByTag(tagsAB);
	}
}
