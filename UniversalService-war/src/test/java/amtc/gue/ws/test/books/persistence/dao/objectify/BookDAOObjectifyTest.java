package amtc.gue.ws.test.books.persistence.dao.objectify;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.dao.book.objectify.BookObjectifyDAOImpl;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.objectify.GAEObjectifyBookEntity;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;
import amtc.gue.ws.test.books.BookTest;

/**
 * Testclass for the Book Objectify DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookDAOObjectifyTest extends BookTest implements IBaseDAOTest {
	private static BookDAO<GAEBookEntity, GAEObjectifyBookEntity, String> failureBookObjectifyDAO;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		setUpBasicBookEnvironment();
		setUpDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(failureBookObjectifyDAO);
	}

	@Override
	@Test
	public void testDAOSetup() {
		assertNotNull(bookObjectifyDAO);
		assertNotNull(tagObjectifyDAO);
	}

	@Override
	@Test
	public void testAddEntity() throws EntityPersistenceException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
	}

	/**
	 * Test adding complex book entity with single tag and user
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testAddBookEntityWithSingleTagAndUser() throws EntityPersistenceException, EntityRetrievalException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity1.addToUsersAndBooks(objectifyUserEntity1);

		assertEquals(1, tagObjectifyDAO.findAllEntities().size());
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
		assertEquals(1, bookObjectifyDAO.findAllEntities().size());
		assertEquals(1, tagObjectifyDAO.findEntityById(objectifyTagEntity1.getKey()).getBooks().size());
		assertEquals(1, userObjectifyDAO.findEntityById(objectifyUserEntity1.getKey()).getBooks().size());
		assertNotNull(bookObjectifyDAO.findSpecificEntity(objectifyBookEntity1));
		assertEquals(1, bookObjectifyDAO.findEntityById(objectifyBookEntity1.getKey()).getUsers().size());
		assertEquals(1, bookObjectifyDAO.findEntityById(objectifyBookEntity1.getKey()).getTags().size());
	}

	/**
	 * Test adding complex book entity with multiple user and tags
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testAddBookEntityWithMultipleRoles() throws EntityPersistenceException, EntityRetrievalException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity2);
		objectifyBookEntity1.addToUsersAndBooks(objectifyUserEntity1);

		assertEquals(2, tagObjectifyDAO.findAllEntities().size());
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
		assertEquals(1, bookObjectifyDAO.findAllEntities().size());
		assertEquals(1, tagObjectifyDAO.findEntityById(objectifyTagEntity1.getKey()).getBooks().size());
		assertEquals(1, userObjectifyDAO.findEntityById(objectifyUserEntity1.getKey()).getBooks().size());
		assertNotNull(bookObjectifyDAO.findSpecificEntity(objectifyBookEntity1));
		assertEquals(1, bookObjectifyDAO.findEntityById(objectifyBookEntity1.getKey()).getUsers().size());
		assertEquals(2, bookObjectifyDAO.findEntityById(objectifyBookEntity1.getKey()).getTags().size());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureBookObjectifyDAO.persistEntity(objectifyBookEntity1);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		List<GAEBookEntity> foundBooks = bookObjectifyDAO.findAllEntities();
		assertEquals(0, foundBooks.size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		List<GAEBookEntity> foundBooks = bookObjectifyDAO.findAllEntities();
		assertEquals(2, foundBooks.size());
	}

	/**
	 * Test retrieving complex entities (with tags and users)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testGetBookEntitiesAfterAddingComplexEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		objectifyBookEntity1.addToTagsOnly(objectifyTagEntity1);
		objectifyBookEntity1.addToTagsOnly(objectifyTagEntity2);
		objectifyBookEntity1.addToUsersOnly(objectifyUserEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);

		List<GAEBookEntity> foundBooks = bookObjectifyDAO.findAllEntities();
		assertEquals(1, foundBooks.size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureBookObjectifyDAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		GAEBookEntity bookEntity = bookObjectifyDAO.findEntityById(objectifyBookEntity1.getKey());
		assertNotNull(bookEntity);
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
	public void testGetComplexBookEntityById() throws EntityPersistenceException, EntityRetrievalException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		objectifyBookEntity1.addToTagsOnly(objectifyTagEntity1);
		objectifyBookEntity1.addToTagsOnly(objectifyTagEntity2);
		objectifyBookEntity1.addToUsersOnly(objectifyUserEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);

		GAEBookEntity bookEntity = bookObjectifyDAO.findEntityById(objectifyBookEntity1.getKey());
		assertNotNull(bookEntity);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		bookObjectifyDAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failureBookObjectifyDAO.findEntityById(objectifyBookEntity1.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		assertEquals(2, bookObjectifyDAO.findAllEntities().size());
		bookObjectifyDAO.removeEntity(objectifyBookEntity1);
		assertEquals(1, bookObjectifyDAO.findAllEntities().size());
	}

	/**
	 * Test deleting complex entity (with users and tags)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to removing an entity
	 */
	@Test
	public void testDeleteComplexEntity()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity2);
		objectifyBookEntity1.addToUsersAndBooks(objectifyUserEntity1);

		assertEquals(1, userObjectifyDAO.findAllEntities().size());
		assertEquals(2, tagObjectifyDAO.findAllEntities().size());
		assertEquals(1, bookObjectifyDAO.findAllEntities().size());
		bookObjectifyDAO.removeEntity(objectifyBookEntity1);
		assertEquals(0, bookObjectifyDAO.findAllEntities().size());
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
		assertEquals(2, tagObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		bookObjectifyDAO.removeEntity(objectifyBookEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		bookObjectifyDAO.removeEntity(objectifyBookEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureBookObjectifyDAO.removeEntity(objectifyBookEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		assertNull(bookObjectifyDAO.findEntityById(objectifyBookEntity2.getKey()).getDescription());
		objectifyBookEntity2.setDescription(DESCRIPTION);
		bookObjectifyDAO.updateEntity(objectifyBookEntity2);
		assertEquals(DESCRIPTION, bookObjectifyDAO.findEntityById(objectifyBookEntity2.getKey()).getDescription());
	}

	/**
	 * Test updating complex entity (with users and tags)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testUpdateComplexEntity() throws EntityPersistenceException, EntityRetrievalException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity2);
		objectifyBookEntity1.addToUsersAndBooks(objectifyUserEntity1);

		assertEquals(2, bookObjectifyDAO.findEntityById(objectifyBookEntity1.getKey()).getTags().size());
		assertEquals(1, bookObjectifyDAO.findEntityById(objectifyBookEntity1.getKey()).getUsers().size());
		objectifyBookEntity1.setUsers(null, false);
		bookObjectifyDAO.updateEntity(objectifyBookEntity1);
		assertEquals(0, bookObjectifyDAO.findEntityById(objectifyBookEntity1.getKey()).getUsers().size());
		assertEquals(2, bookObjectifyDAO.findEntityById(objectifyBookEntity1.getKey()).getTags().size());
	}

	@Override
	@Test
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		// objectify update without adding equals persisting
		bookObjectifyDAO.updateEntity(objectifyBookEntity1);
		assertNotNull(objectifyBookEntity1.getKey());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failureBookObjectifyDAO.updateEntity(objectifyBookEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		GAEObjectifyBookEntity searchBookEntity = new GAEObjectifyBookEntity();
		searchBookEntity.setAuthor(AUTHOR);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		assertEquals(2, bookObjectifyDAO.findAllEntities().size());
		assertEquals(2, bookObjectifyDAO.findSpecificEntity(searchBookEntity).size());
	}

	/**
	 * Test retrieving specific complex entity (with users and tags)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testFindSpecificComplexBookEntity() throws EntityPersistenceException, EntityRetrievalException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity2);
		objectifyBookEntity1.addToUsersAndBooks(objectifyUserEntity1);

		assertEquals(2, bookObjectifyDAO.findAllEntities().size());
		assertEquals(1, bookObjectifyDAO.findSpecificEntity(objectifyBookEntity1).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity2.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity2.addToTagsAndBooks(objectifyTagEntity2);
		objectifyBookEntity1.addToUsersAndBooks(objectifyUserEntity1);
		bookObjectifyDAO.updateEntity(objectifyBookEntity1);
		bookObjectifyDAO.updateEntity(objectifyBookEntity2);

		GAEObjectifyBookEntity searchBook = new GAEObjectifyBookEntity();
		searchBook.addToTagsOnly(objectifyTagEntity1);
		searchBook.addToTagsOnly(objectifyTagEntity2);
		assertEquals(1, bookObjectifyDAO.findSpecificEntity(searchBook).size());

		GAEObjectifyBookEntity searchBook2 = new GAEObjectifyBookEntity();
		searchBook2.addToTagsOnly(objectifyTagEntity1);
		assertEquals(2, bookObjectifyDAO.findSpecificEntity(searchBook2).size());

		GAEObjectifyBookEntity searchBook3 = new GAEObjectifyBookEntity();
		searchBook3.addToUsersOnly(objectifyUserEntity1);
		assertEquals(1, bookObjectifyDAO.findSpecificEntity(searchBook3).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, bookObjectifyDAO.findSpecificEntity(objectifyBookEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failureBookObjectifyDAO.findSpecificEntity(objectifyBookEntity1);
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
	public void testGetEntityByTagSingleResult() throws EntityRetrievalException, EntityPersistenceException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		tagObjectifyDAO.persistEntity(objectifyTagEntity3);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity2);
		objectifyBookEntity2.addToTagsAndBooks(objectifyTagEntity2);
		objectifyBookEntity2.addToTagsAndBooks(objectifyTagEntity3);
		bookObjectifyDAO.updateEntity(objectifyBookEntity1);
		bookObjectifyDAO.updateEntity(objectifyBookEntity2);

		assertEquals(2, bookObjectifyDAO.findAllEntities().size());
		List<GAEBookEntity> foundBooksHavingTagAB = bookObjectifyDAO.getBookEntityByTag(tagsAB);
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
	public void testGetEntityByTagMultipleResult() throws EntityPersistenceException, EntityRetrievalException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		tagObjectifyDAO.persistEntity(objectifyTagEntity3);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		bookObjectifyDAO.persistEntity(objectifyBookEntity3);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity2);
		objectifyBookEntity2.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity3.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity3.addToTagsAndBooks(objectifyTagEntity3);
		bookObjectifyDAO.updateEntity(objectifyBookEntity1);
		bookObjectifyDAO.updateEntity(objectifyBookEntity2);
		bookObjectifyDAO.updateEntity(objectifyBookEntity3);

		List<GAEBookEntity> foundBooksHavingTagA = bookObjectifyDAO.getBookEntityByTag(tagsA);
		List<GAEBookEntity> foundBooksHavingTagAB = bookObjectifyDAO.getBookEntityByTag(tagsAB);

		assertEquals("Incorrect Number of Bookentities with Tag A", 3, foundBooksHavingTagA.size());
		assertEquals("Incorrect Number of Bookentities with Tag A + B", 1, foundBooksHavingTagAB.size());
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
	public void testGetEntityByTagOnlyBooksWithoutTags() throws EntityRetrievalException, EntityPersistenceException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		List<GAEBookEntity> foundBooksHavingTagAB = bookObjectifyDAO.getBookEntityByTag(tagsAB);
		assertEquals(0, foundBooksHavingTagAB.size());
	}

	/**
	 * Test retrieving entity by tag when no entities are persisted yet
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testGetEntityByTagNoBooksPersisted() throws EntityRetrievalException {
		List<GAEBookEntity> foundBooksHavingTagAB = bookObjectifyDAO.getBookEntityByTag(tagsAB);
		assertEquals(0, foundBooksHavingTagAB.size());
	}

	/**
	 * Test retrieving entity by tag using invalid EM
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByTagUsingInvalidEM() throws EntityRetrievalException {
		failureBookObjectifyDAO.getBookEntityByTag(tagsAB);
	}

	/**
	 * Test retrieving entity for user by tag
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 */
	@Test
	public void testGetEntityForUserByTag() throws EntityRetrievalException, EntityPersistenceException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		tagObjectifyDAO.persistEntity(objectifyTagEntity3);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		bookObjectifyDAO.persistEntity(objectifyBookEntity3);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity2);
		objectifyBookEntity1.addToUsersAndBooks(objectifyUserEntity1);
		objectifyBookEntity2.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity3.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity3.addToTagsAndBooks(objectifyTagEntity3);
		bookObjectifyDAO.updateEntity(objectifyBookEntity1);
		bookObjectifyDAO.updateEntity(objectifyBookEntity2);
		bookObjectifyDAO.updateEntity(objectifyBookEntity3);

		List<GAEBookEntity> foundBooksForUser = bookObjectifyDAO.getBookEntityForUserByTag(tagsA);
		assertEquals(1, foundBooksForUser.size());
	}

	/**
	 * Test retrieving entity for user by tag when no user is set
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 */
	public void testGetEntityForUserByTagNoUserSet() throws EntityRetrievalException, EntityPersistenceException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		tagObjectifyDAO.persistEntity(objectifyTagEntity3);
		noUserBookObjectifyDAO.persistEntity(objectifyBookEntity1);
		noUserBookObjectifyDAO.persistEntity(objectifyBookEntity2);
		noUserBookObjectifyDAO.persistEntity(objectifyBookEntity3);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity2);
		objectifyBookEntity1.addToUsersAndBooks(objectifyUserEntity1);
		objectifyBookEntity2.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity3.addToTagsAndBooks(JPATagEntity1);
		objectifyBookEntity3.addToTagsAndBooks(JPATagEntity3);
		noUserBookObjectifyDAO.updateEntity(objectifyBookEntity1);
		noUserBookObjectifyDAO.updateEntity(objectifyBookEntity2);
		noUserBookObjectifyDAO.updateEntity(objectifyBookEntity3);

		List<GAEBookEntity> foundBooksForUser = noUserBookObjectifyDAO.getBookEntityForUserByTag(tagsA);
		assertEquals(0, foundBooksForUser.size());
	}

	/**
	 * Test retrieving entity for user by tag using invalid EM
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityForUserByTagUsingInvalidEM() throws EntityRetrievalException {
		failureBookObjectifyDAO.getBookEntityForUserByTag(tagsAB);
	}

	/**
	 * Test retrieving specific entity for user
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 */
	@Test
	public void testFindSpecificEntityForUser() throws EntityRetrievalException, EntityPersistenceException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		tagObjectifyDAO.persistEntity(objectifyTagEntity3);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		bookObjectifyDAO.persistEntity(objectifyBookEntity3);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity2);
		objectifyBookEntity1.addToUsersAndBooks(objectifyUserEntity1);
		objectifyBookEntity2.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity3.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity3.addToTagsAndBooks(objectifyTagEntity3);
		bookObjectifyDAO.updateEntity(objectifyBookEntity1);
		bookObjectifyDAO.updateEntity(objectifyBookEntity2);
		bookObjectifyDAO.updateEntity(objectifyBookEntity3);

		List<GAEBookEntity> foundBooksForUser = bookObjectifyDAO.findSpecificBookEntityForUser(objectifyBookEntity1);
		assertEquals(1, foundBooksForUser.size());
	}

	/**
	 * Test retrieving specific entity for user using simple BookEntity
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 */
	@Test
	public void testFindSpecificEntityForUserUsingSimpleBook()
			throws EntityRetrievalException, EntityPersistenceException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		objectifyBookEntity1.addToUsersAndBooks(objectifyUserEntity1);
		bookObjectifyDAO.updateEntity(objectifyBookEntity1);
		List<GAEBookEntity> foundBooksForUser = bookObjectifyDAO.findSpecificBookEntityForUser(objectifyBookEntity1);
		assertEquals(1, foundBooksForUser.size());
	}

	/**
	 * Test retrieving specific entity for user when no user is set
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 */
	public void testFindSpecificEntityForUserNoUserSet() throws EntityRetrievalException, EntityPersistenceException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		tagObjectifyDAO.persistEntity(objectifyTagEntity3);
		noUserBookObjectifyDAO.persistEntity(objectifyBookEntity1);
		noUserBookObjectifyDAO.persistEntity(objectifyBookEntity2);
		noUserBookObjectifyDAO.persistEntity(objectifyBookEntity3);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity1.addToTagsAndBooks(objectifyTagEntity2);
		objectifyBookEntity1.addToUsersAndBooks(objectifyUserEntity1);
		objectifyBookEntity2.addToTagsAndBooks(objectifyTagEntity1);
		objectifyBookEntity3.addToTagsAndBooks(JPATagEntity1);
		objectifyBookEntity3.addToTagsAndBooks(JPATagEntity3);
		noUserBookObjectifyDAO.updateEntity(objectifyBookEntity1);
		noUserBookObjectifyDAO.updateEntity(objectifyBookEntity2);
		noUserBookObjectifyDAO.updateEntity(objectifyBookEntity3);

		noUserBookObjectifyDAO.findSpecificBookEntityForUser(objectifyBookEntity1);
	}

	/**
	 * Test retrieving specific entity for user using invalid EM
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityForUserUsingInvalidEM() throws EntityRetrievalException {
		failureBookObjectifyDAO.findSpecificBookEntityForUser(objectifyBookEntity1);
	}

	/**
	 * Method setting up DAO Mocks for testing purpose
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 */
	private static void setUpDAOMocks()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		failureBookObjectifyDAO = EasyMock.createNiceMock(BookObjectifyDAOImpl.class);
		EasyMock.expect(failureBookObjectifyDAO.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.expect(failureBookObjectifyDAO.findEntityById(null)).andThrow(new EntityRetrievalException());
		EasyMock.expect(failureBookObjectifyDAO.findSpecificEntity(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(
				failureBookObjectifyDAO.findSpecificBookEntityForUser(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failureBookObjectifyDAO.getBookEntityByTag(EasyMock.isA(Tags.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failureBookObjectifyDAO.getBookEntityForUserByTag(EasyMock.isA(Tags.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failureBookObjectifyDAO.persistEntity(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(failureBookObjectifyDAO.removeEntity(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.expect(failureBookObjectifyDAO.updateEntity(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.replay(failureBookObjectifyDAO);
	}
}
