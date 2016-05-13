package amtc.gue.ws.test.books.persistence.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRemovalException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;

/**
 * Testclass for the Tag DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagDAOTest extends JPATest {

	private static final String TAG_NAME_FOR_TESTING = "TagNameForTesting";

	@Test
	public void testDAOSetup() {
		assertNotNull(tagEntityDAO);
		assertNotNull(failureTagEntityDAO);
	}

	@Test
	public void testAddSimpleTagEntity() throws EntityPersistenceException,
			EntityRetrievalException {
		assertNull(tagEntity1.getKey());
		assertNull(tagEntity2.getKey());
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		assertNotNull(tagEntity1.getKey());
		assertNotNull(tagEntity2.getKey());
	}

	@Test
	public void testAddTagEntityWithSingleBook()
			throws EntityPersistenceException, EntityRetrievalException {
		assertNull(tagEntity1.getKey());
		bookEntityDAO.persistEntity(bookEntity1);
		tagEntity1.addToBooks(bookEntity1);
		assertEquals(1, tagEntity1.getBooks().size());
		tagEntityDAO.persistEntity(tagEntity1);
		assertNotNull(tagEntity1.getKey());
	}

	@Test
	public void testAddTagEntityWithMultipleBooks()
			throws EntityPersistenceException {
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		tagEntity1.addToBooks(bookEntity1);
		tagEntity1.addToBooks(bookEntity2);
		assertEquals(2, tagEntity1.getBooks().size());
		assertNull(tagEntity1.getKey());
		tagEntityDAO.persistEntity(tagEntity1);
		assertNotNull(tagEntity1.getKey());
	}

	@Test(expected = EntityPersistenceException.class)
	public void testAddSameTagEntityTwice() throws EntityPersistenceException,
			EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity1);
	}

	@Test(expected = EntityPersistenceException.class)
	public void testAddTagEntityUsingInvalidEM()
			throws EntityPersistenceException, EntityRetrievalException {
		failureTagEntityDAO.persistEntity(tagEntity1);
	}

	@Test
	public void testGetAllTagEntitiesWithoutAdding()
			throws EntityRetrievalException {
		assertEquals(0, tagEntityDAO.findAllEntities().size());
	}

	@Test
	public void testGetAllBookEntitiesAfterAddingSimpleTagEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		tagEntityDAO.persistEntity(tagEntity3);
		tagEntityDAO.persistEntity(tagEntity4);
		assertEquals(4, tagEntityDAO.findAllEntities().size());
	}

	@Test
	public void testGetAllBookEntitiesAfterAddingComplexTagEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		bookEntityDAO.persistEntity(bookEntity3);
		tagEntity1.addToBooks(bookEntity1);
		tagEntity1.addToBooks(bookEntity2);
		tagEntity1.addToBooks(bookEntity3);
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		List<GAEJPABookEntity> foundBooks = bookEntityDAO.findAllEntities();
		List<GAEJPATagEntity> foundTags = tagEntityDAO.findAllEntities();
		assertEquals(3, foundBooks.size());
		assertEquals(2, foundTags.size());
		Set<GAEJPABookEntity> tagBooks = foundTags.get(0).getBooks();
		assertEquals(3, tagBooks.size());
	}

	@Test(expected = EntityRetrievalException.class)
	public void testGetAllTagEntitiesUsinInvalidEM()
			throws EntityPersistenceException, EntityRetrievalException {
		failureTagEntityDAO.findAllEntities();
	}

	@Test
	public void testGetSimpleTagEntity() throws EntityPersistenceException,
			EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		GAEJPATagEntity retrievedTagEntity = tagEntityDAO
				.findEntityById(tagEntity1.getKey());
		assertNotNull(retrievedTagEntity);
		assertEquals(tagEntity1.getKey(), retrievedTagEntity.getKey());
	}

	@Test
	public void testGetComplexTagEntity() throws EntityPersistenceException,
			EntityRetrievalException {
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		bookEntityDAO.persistEntity(bookEntity3);
		tagEntity1.addToBooks(bookEntity1);
		tagEntity1.addToBooks(bookEntity2);
		tagEntity1.addToBooks(bookEntity3);
		tagEntityDAO.persistEntity(tagEntity1);
		GAEJPATagEntity retrievedTagEntity = tagEntityDAO
				.findEntityById(tagEntity1.getKey());
		assertNotNull(retrievedTagEntity);
		assertEquals(tagEntity1.getKey(), retrievedTagEntity.getKey());
		assertEquals(3, retrievedTagEntity.getBooks().size());
	}

	@Test(expected = EntityRetrievalException.class)
	public void testGetTagEntityWithNullID() throws EntityRetrievalException {
		tagEntityDAO.findEntityById(null);
	}

	@Test(expected = EntityRetrievalException.class)
	public void testGetTagEntityUsingInvalidEM()
			throws EntityPersistenceException, EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		failureTagEntityDAO.findEntityById(tagEntity1.getKey());
	}

	@Test
	public void testDeleteSimpleTagEntity() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		assertEquals(2, tagEntityDAO.findAllEntities().size());
		tagEntityDAO.removeEntity(tagEntity1);
		List<GAEJPATagEntity> foundTagEntities = tagEntityDAO.findAllEntities();
		assertEquals(1, foundTagEntities.size());
		assertEquals(tagEntity2.getKey(), foundTagEntities.get(0).getKey());
	}

	@Test
	public void testDeleteComplexTagEntity() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		bookEntityDAO.persistEntity(bookEntity3);
		tagEntity1.addToBooks(bookEntity1);
		tagEntity2.addToBooks(bookEntity2);
		tagEntity2.addToBooks(bookEntity3);
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		assertEquals(2, tagEntityDAO.findAllEntities().size());
		assertEquals(3, bookEntityDAO.findAllEntities().size());
		tagEntityDAO.removeEntity(tagEntity1);
		List<GAEJPATagEntity> foundTagEntities = tagEntityDAO.findAllEntities();
		assertEquals(1, foundTagEntities.size());
		assertEquals(tagEntity2.getKey(), foundTagEntities.get(0).getKey());
		assertEquals(2, foundTagEntities.get(0).getBooks().size());
	}

	@Test(expected = EntityRemovalException.class)
	public void testDeleteTagEntityBeforeAddingEntity()
			throws EntityRemovalException {
		tagEntityDAO.removeEntity(tagEntity1);
	}

	@Test(expected = EntityRemovalException.class)
	public void testDeleteTagEntityUsingInvalidEM()
			throws EntityRemovalException {
		failureTagEntityDAO.removeEntity(tagEntity1);
	}

	@Test
	public void testUpdateSimpleTagEntity() throws EntityPersistenceException,
			EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		assertEquals(TAG_NAME_A, tagEntity1.getTagName());
		String entityKey = tagEntity1.getKey();

		tagEntity1.setTagName(TAG_NAME_B);
		tagEntityDAO.updateEntity(tagEntity1);
		assertEquals(entityKey, tagEntity1.getKey());

		GAEJPATagEntity retrievedEntity = tagEntityDAO
				.findEntityById(tagEntity1.getKey());
		assertEquals(TAG_NAME_B, retrievedEntity.getTagName());
	}

	@Test
	public void testUpdateComplexTagEntity() throws EntityPersistenceException,
			EntityRetrievalException {
		bookEntityDAO.persistEntity(bookEntity1);
		tagEntity1.addToBooks(bookEntity1);
		tagEntityDAO.persistEntity(tagEntity1);
		assertEquals(1, bookEntityDAO.findAllEntities().size());
		assertEquals(1, tagEntityDAO.findAllEntities().size());
		assertEquals(1, tagEntityDAO.findAllEntities().get(0).getBooks().size());

		GAEJPATagEntity foundTagEntity = tagEntityDAO.findEntityById(tagEntity1
				.getKey());
		ArrayList<GAEJPABookEntity> bookEntityList = new ArrayList<GAEJPABookEntity>(
				foundTagEntity.getBooks());
		assertNotNull(bookEntityList);
		GAEJPABookEntity firstBookEntity = bookEntityList.get(0);
		assertEquals(TEST_TITLE_A, firstBookEntity.getTitle());

		// update entity
		bookEntity1.setTitle(TEST_TITLE_B);
		bookEntityDAO.updateEntity(bookEntity1);
		tagEntityDAO.updateEntity(tagEntity1);

		foundTagEntity = tagEntityDAO.findEntityById(tagEntity1.getKey());
		bookEntityList = new ArrayList<GAEJPABookEntity>(
				foundTagEntity.getBooks());
		firstBookEntity = bookEntityList.get(0);
		assertEquals(TEST_TITLE_B, firstBookEntity.getTitle());
	}

	@Test(expected = EntityPersistenceException.class)
	public void testUpdateTagEntityWithoutAdding()
			throws EntityPersistenceException {
		tagEntityDAO.updateEntity(tagEntity1);
	}

	@Test(expected = EntityPersistenceException.class)
	public void testUpdateTagEntityUsingInvalidEM()
			throws EntityPersistenceException {
		failureTagEntityDAO.updateEntity(tagEntity1);
	}

	@Test
	public void testFindSpecificSimpleTagEntity()
			throws EntityPersistenceException, EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		List<GAEJPATagEntity> foundTags = tagEntityDAO
				.findSpecificEntity(tagEntity1);
		assertEquals(1, foundTags.size());
	}

	@Test
	public void testFindSpecificComplexTagEntity()
			throws EntityPersistenceException, EntityRetrievalException {
		tagEntity1.setTagName(null);
		bookEntityDAO.persistEntity(bookEntity1);
		tagEntity1.addToBooks(bookEntity1);
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		List<GAEJPATagEntity> foundTags = tagEntityDAO
				.findSpecificEntity(tagEntity1);
		assertEquals(1, foundTags.size());
	}

	@Test
	public void testFindSpecificComplexTagEntityByCommonSearchCriteria()
			throws EntityPersistenceException, EntityRetrievalException {
		tagEntity1.setTagName(TAG_NAME_FOR_TESTING);
		tagEntity2.setTagName(TAG_NAME_FOR_TESTING);
		tagEntity3.setTagName(TAG_NAME_FOR_TESTING);

		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		tagEntity1.addToBooks(bookEntity1);
		tagEntity4.addToBooks(bookEntity2);

		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		tagEntityDAO.persistEntity(tagEntity3);
		tagEntityDAO.persistEntity(tagEntity4);

		assertEquals(2, bookEntityDAO.findAllEntities().size());
		assertEquals(4, tagEntityDAO.findAllEntities().size());

		GAEJPATagEntity searchTag = new GAEJPATagEntity();
		searchTag.setTagName(TAG_NAME_FOR_TESTING);

		List<GAEJPATagEntity> foundTags = tagEntityDAO
				.findSpecificEntity(searchTag);
		assertEquals(3, foundTags.size());
	}

	@Test
	public void testFindSpecificTagEntityWithoutPersisting() throws EntityRetrievalException {
		List<GAEJPATagEntity> foundTags = tagEntityDAO
				.findSpecificEntity(tagEntity1);
		assertEquals(0, foundTags.size());
	}

	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificTagEntityUsingInvalidEM()
			throws EntityRetrievalException {
		failureTagEntityDAO.findSpecificEntity(tagEntity1);
	}
}
