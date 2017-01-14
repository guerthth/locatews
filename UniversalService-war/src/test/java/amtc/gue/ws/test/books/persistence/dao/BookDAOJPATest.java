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

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;

/**
 * Testclass for the Book DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookDAOJPATest extends BookServiceJPATest {

	@Override
	public void testDAOSetup() {
		assertNotNull(bookEntityDAO);
		assertNotNull(failureBookEntityDAO);
		assertNotNull(tagEntityDAO);
		assertNotNull(failureTagEntityDAO);
		assertNotNull(userEntityDAO);
		assertNotNull(failureUserEntityDAO);
	}

	@Override
	public void testAddEntity() throws EntityPersistenceException {
		assertNull(bookEntity1.getKey());
		bookEntityDAO.persistEntity(bookEntity1);
		assertNotNull(bookEntity1.getKey());
	}

	/**
	 * Test adding complex entity with single tag
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public void testAddBookEntityWithSingleTag()
			throws EntityPersistenceException {
		tagEntityDAO.persistEntity(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity1);
		bookEntityDAO.persistEntity(bookEntity1);
		assertEquals(1, bookEntity1.getTags().size());
	}

	/**
	 * Test adding complex entity with multiple tags
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
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

	/**
	 * Test adding complex entity with tags and users
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve entity
	 */
	@Test
	public void testAddBookEntityWithTagsAndUsersWithUser()
			throws EntityPersistenceException, EntityRetrievalException {
		assertEquals(0, bookEntityDAO.findAllBookEntitiesForUser().size());
		tagEntityDAO.persistEntity(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity1);
		userEntityDAO.persistEntity(userEntity1);
		bookEntity1.addToUsersAndBooks(userEntity1);
		assertEquals(1, bookEntity1.getTags().size());
		assertEquals(1, bookEntity1.getUsers().size());
		assertNull(bookEntity1.getKey());
		bookEntityDAO.persistEntity(bookEntity1);
		assertNotNull(bookEntity1.getKey());
		assertEquals(1, bookEntityDAO.findAllBookEntitiesForUser().size());
	}

	@Override
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		// add be1 to the datastore two times
		// EntityPersistenceException is expected
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity1);

	}

	@Override
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureBookEntityDAO.persistEntity(bookEntity1);

	}

	@Override
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		assertEquals(0, bookEntityDAO.findAllEntities().size());
	}

	@Override
	public void testGetEntitiesAfterAddingSimpleEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		bookEntityDAO.persistEntity(bookEntity3);
		bookEntityDAO.persistEntity(bookEntity4);
		bookEntityDAO.persistEntity(bookEntity5);
		List<GAEJPABookEntity> foundBooks = bookEntityDAO.findAllEntities();
		assertEquals(5, foundBooks.size());
	}

	/**
	 * Test retrieving complex entities (with tags)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
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

	@Override
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureBookEntityDAO.findAllEntities();
	}

	/**
	 * Test retrieving all book entities for a specific user when the user is
	 * null
	 * 
	 * @throws EntityRetrievalException
	 *             when an issue occurs while trying to retrieve entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public void testGetAllBookEntitiesForUserUsingNullUser()
			throws EntityRetrievalException {
		bookEntityDAONullUser.findAllBookEntitiesForUser();
	}

	/**
	 * Test retrieving all book entities for a specific user using invalid EM
	 * 
	 * @throws EntityRetrievalException
	 *             when an issue occurs while trying to retrieve entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public void testGetAllBookEntitiesForUserUsingInvalidEM()
			throws EntityRetrievalException {
		failureBookEntityDAO.findAllBookEntitiesForUser();
	}

	@Override
	public void testGetEntityById() throws EntityRetrievalException,
			EntityPersistenceException {
		bookEntityDAO.persistEntity(bookEntity1);
		GAEJPABookEntity retrievedEntity = bookEntityDAO
				.findEntityById(bookEntity1.getKey());
		assertNotNull(retrievedEntity);
		assertEquals(bookEntity1.getKey(), retrievedEntity.getKey());
	}

	/**
	 * Test retrieving specific complex entity by id
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testGetComplexBookEntityById()
			throws EntityPersistenceException, EntityRetrievalException {
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

	@Override
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		bookEntityDAO.findEntityById(null);
	}

	@Override
	public void testGetEntityByIdUsingInvalidEM()
			throws EntityPersistenceException, EntityRetrievalException {
		bookEntityDAO.persistEntity(bookEntity1);
		failureBookEntityDAO.findEntityById(bookEntity1.getKey());
	}

	@Override
	public void testDeleteEntity() throws EntityRetrievalException,
			EntityRemovalException, EntityPersistenceException {
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		assertEquals(2, bookEntityDAO.findAllEntities().size());
		bookEntityDAO.removeEntity(bookEntity2);
		assertEquals(1, bookEntityDAO.findAllEntities().size());
	}

	/**
	 * Test deleting complex entity (with tags)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to removing an entity
	 */
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

	/**
	 * Testing in detail if the deletion of book/user relations works as
	 * expected
	 * 
	 * @throws EntityPersistenceException
	 *             when an issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when an issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testDeleteOnlyBookUserRelation()
			throws EntityPersistenceException, EntityRetrievalException {
		// persist one userEntity possessing two bookEntities
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		
		userEntity1.addToBooksAndUsers(bookEntity1);
		userEntity1.addToBooksAndUsers(bookEntity2);
		userEntityDAO.persistEntity(userEntity1);

		// check found users and books
		List<GAEJPAUserEntity> foundUsers = userEntityDAO.findAllEntities();
		assertEquals(1, foundUsers.size());
		GAEJPAUserEntity foundUser = foundUsers.get(0);
		assertEquals(2, foundUser.getBooks().size());
		List<GAEJPABookEntity> foundBooks = bookEntityDAO.findAllEntities();
		assertEquals(2, foundBooks.size());
		GAEJPABookEntity foundBookA = foundBooks.get(0);
		assertEquals(1, foundBookA.getUsers().size());
		GAEJPABookEntity foundBookB = foundBooks.get(1);
		assertEquals(1, foundBookB.getUsers().size());

		// check number of books for user
		assertEquals(2, bookEntityDAO.findAllBookEntitiesForUser().size());
		
		// remove the user from one book
		foundBookA.removeUser(foundUser);
		bookEntityDAO.mergeEntity(foundBookA);
		userEntityDAO.mergeEntity(foundUser);

		// check users and books after updating the relationship
		foundUsers = userEntityDAO.findAllEntities();
		assertEquals(1, foundUsers.size());
		foundUser = foundUsers.get(0);
		assertEquals(1, foundUser.getBooks().size());
		foundBooks = bookEntityDAO.findAllEntities();
		assertEquals(2, foundBooks.size());
		foundBookA = foundBooks.get(0);
		assertEquals(0, foundBookA.getUsers().size());
		
		// check number of books for user after updating the relationship
		assertEquals(1, bookEntityDAO.findAllBookEntitiesForUser().size());
	}

	@Override
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		GAEJPABookEntity removedBookEntity = bookEntityDAO
				.removeEntity(bookEntity3);
		assertNull(removedBookEntity);
	}

	/**
	 * Test removing entity without id
	 * 
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to removing an entity
	 */
	@Test(expected = EntityRemovalException.class)
	public void testDeleteBookEntityWithoutId() throws EntityRemovalException {
		bookEntityDAO.removeEntity(bookEntity1);
	}

	@Override
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureBookEntityDAO.removeEntity(bookEntity1);
	}

	@Override
	public void testUpdateSimpleEntity() throws EntityRetrievalException,
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

	/**
	 * Test updating complex entity (with tags)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testUpdateComplexBookEntity()
			throws EntityPersistenceException, EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity1);
		bookEntityDAO.persistEntity(bookEntity1);
		assertEquals(1, bookEntityDAO.findAllEntities().size());
		assertEquals(1, tagEntityDAO.findAllEntities().size());
		assertEquals(1, bookEntity1.getTags().size());

		GAEJPABookEntity foundBookEntity = bookEntityDAO
				.findEntityById(bookEntity1.getKey());
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

	@Override
	public void testUpdateEntityWithoutAdding()
			throws EntityPersistenceException {
		// try updating be1 without initially adding
		bookEntityDAO.updateEntity(bookEntity1);
	}

	@Override
	public void testUpdateEntityUsingInvalidEM()
			throws EntityPersistenceException {
		failureBookEntityDAO.updateEntity(bookEntity1);
	}

	@Override
	public void testFindSpecificEntity() throws EntityRetrievalException,
			EntityPersistenceException {
		// add be1 and assure that it is found
		bookEntityDAO.persistEntity(bookEntity1);
		List<GAEJPABookEntity> foundBooks = bookEntityDAO
				.findSpecificEntity(bookEntity1);
		assertEquals(1, foundBooks.size());
	}

	/**
	 * Test retrieving specific complex entity (with tags)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
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

	@Override
	public void testFindSpecificEntityBySearchCriteria()
			throws EntityRetrievalException, EntityPersistenceException {
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

	@Override
	public void testFindSpecificEntityBeforeAdding()
			throws EntityRetrievalException {
		List<GAEJPABookEntity> foundBooks = bookEntityDAO
				.findSpecificEntity(bookEntity1);
		assertEquals(0, foundBooks.size());
	}

	@Override
	public void testFindSpecificEntityUsingInvalidEM()
			throws EntityRetrievalException {
		failureBookEntityDAO.findSpecificEntity(bookEntity1);
	}

	/**
	 * Test retrieving entity by tag
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 */
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

	/**
	 * Test retrieving entity by tag. Multiple results found
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
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

	/**
	 * Test retrieving entity by tag. No books with tags in DB
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public void testGetEntityByTagOnlyBooksWithoutTags()
			throws EntityRetrievalException, EntityPersistenceException {
		bookEntityDAO.persistEntity(bookEntity1);
		bookEntityDAO.persistEntity(bookEntity2);
		List<GAEJPABookEntity> foundBooksHavingTagAB = bookEntityDAO
				.getBookEntityByTag(tagsAB);
		assertEquals(0, foundBooksHavingTagAB.size());
	}

	/**
	 * Test retrieving entity by tag when no entities are persisted yet
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testGetEntityByTagNoBooksPersisted()
			throws EntityRetrievalException {
		List<GAEJPABookEntity> foundBooksHavingTagAB = bookEntityDAO
				.getBookEntityByTag(tagsAB);
		assertEquals(0, foundBooksHavingTagAB.size());
	}

	/**
	 * Test retrieving entity by tag using invalid EM
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByTagUsingInvalidEM()
			throws EntityRetrievalException {
		failureBookEntityDAO.getBookEntityByTag(tagsAB);
	}
}
