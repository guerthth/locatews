package amtc.gue.ws.test.books.persistence.dao.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.jpa.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;
import amtc.gue.ws.test.books.BookTest;

/**
 * Testclass for the Book JPA DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookDAOJPATest extends BookTest implements IBaseDAOTest {
	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicBookEnvironment();
	}

	@Override
	@Test
	public void testDAOSetup() {
		assertNotNull(bookJPADAO);
		assertNotNull(failureBookJPADAO);
		assertNotNull(tagJPADAO);
	}

	@Override
	@Test
	public void testAddEntity() throws EntityPersistenceException {
		assertNull(JPABookEntity1.getKey());
		bookJPADAO.persistEntity(JPABookEntity1);
		assertNotNull(JPABookEntity1.getKey());
	}

	/**
	 * Test adding complex entity with single tag
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public void testAddBookEntityWithSingleTag() throws EntityPersistenceException {
		GAETagEntity addedTagEntity = tagJPADAO.persistEntity(JPATagEntity1);
		JPABookEntity1.addToTagsAndBooks(addedTagEntity);
		bookJPADAO.persistEntity(JPABookEntity1);
		assertEquals(1, JPABookEntity1.getTags().size());
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
	public void testAddBookEntityWithMultipleTags() throws EntityPersistenceException, EntityRetrievalException {
		tagJPADAO.persistEntity(JPATagEntity1);
		tagJPADAO.persistEntity(JPATagEntity2);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity1);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity2);
		bookJPADAO.persistEntity(JPABookEntity1);
		assertEquals(2, JPABookEntity1.getTags().size());
		assertEquals(2, tagJPADAO.findAllEntities().size());
		assertEquals(1, bookJPADAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		bookJPADAO.persistEntity(JPABookEntity1);
		bookJPADAO.persistEntity(JPABookEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureBookJPADAO.persistEntity(JPABookEntity1);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		assertEquals(0, bookJPADAO.findAllEntities().size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		bookJPADAO.persistEntity(JPABookEntity1);
		bookJPADAO.persistEntity(JPABookEntity2);
		assertEquals(2, bookJPADAO.findAllEntities().size());
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
	public void testGetAllBookEntitiesAfterAddingComplexRoleEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		tagJPADAO.persistEntity(JPATagEntity1);
		tagJPADAO.persistEntity(JPATagEntity2);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity1);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity2);
		bookJPADAO.persistEntity(JPABookEntity1);
		List<GAEBookEntity> foundBooks = bookJPADAO.findAllEntities();
		List<GAETagEntity> foundTags = tagJPADAO.findAllEntities();
		assertEquals(1, foundBooks.size());
		assertEquals(2, foundTags.size());
		List<GAETagEntity> bookTags = new ArrayList<>(foundBooks.get(0).getTags());
		assertEquals(2, bookTags.size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureBookJPADAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		bookJPADAO.persistEntity(JPABookEntity1);
		GAEBookEntity retrievedEntity = bookJPADAO.findEntityById(JPABookEntity1.getKey());
		assertEquals(JPABookEntity1.getKey(), retrievedEntity.getKey());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		bookJPADAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failureBookJPADAO.findEntityById(JPABookEntity5.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		bookJPADAO.persistEntity(JPABookEntity1);
		bookJPADAO.persistEntity(JPABookEntity2);
		assertEquals(2, bookJPADAO.findAllEntities().size());
		bookJPADAO.removeEntity(JPABookEntity1);
		assertEquals(1, bookJPADAO.findAllEntities().size());
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
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		tagJPADAO.persistEntity(JPATagEntity1);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity1);
		bookJPADAO.persistEntity(JPABookEntity1);
		assertEquals(1, tagJPADAO.findAllEntities().size());
		assertEquals(1, bookJPADAO.findAllEntities().size());

		bookJPADAO.removeEntity(JPABookEntity1);
		assertEquals(1, tagJPADAO.findAllEntities().size());
		assertEquals(0, bookJPADAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		bookJPADAO.removeEntity(JPABookEntity5);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		bookJPADAO.removeEntity(JPABookEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureBookJPADAO.removeEntity(JPABookEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		bookJPADAO.persistEntity(JPABookEntity1);
		assertEquals(JPABookEntity1.getDescription(), DESCRIPTION);
		String entityKey = JPABookEntity1.getKey();
		JPABookEntity1.setDescription(DESCRIPTION_B);
		bookJPADAO.updateEntity(JPABookEntity1);
		assertEquals(entityKey, JPABookEntity1.getKey());
		GAEBookEntity retrievedUdatedEntity = bookJPADAO.findEntityById(JPABookEntity1.getKey());
		assertEquals(DESCRIPTION_B, retrievedUdatedEntity.getDescription());
	}

	/**
	 * Test updating complex entity (with tags)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRemovalException
	 */
	@Test
	public void testUpdateComplexBookEntity()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		tagJPADAO.persistEntity(JPATagEntity1);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity1);
		bookJPADAO.persistEntity(JPABookEntity1);
		assertEquals(1, bookJPADAO.findAllEntities().size());
		assertEquals(1, tagJPADAO.findAllEntities().size());

		GAEBookEntity foundBookEntity = bookJPADAO.findEntityById(JPABookEntity1.getKey());
		ArrayList<GAETagEntity> tags = new ArrayList<>(foundBookEntity.getTags());
		assertNotNull(tags);
		GAETagEntity firstTag = tags.get(0);
		assertEquals(TAG, firstTag.getKey());

		// remove tagenentity
		JPABookEntity1.removeTag(JPATagEntity1);
		assertNotNull(JPABookEntity1.getTags());
		bookJPADAO.updateEntity(JPABookEntity1);
		foundBookEntity = bookJPADAO.findEntityById(JPABookEntity1.getKey());
		assertEquals(0, foundBookEntity.getTags().size());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		GAEJPABookEntity updatedEntity = new GAEJPABookEntity();
		bookJPADAO.updateEntity(updatedEntity);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failureBookJPADAO.updateEntity(JPABookEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		bookJPADAO.persistEntity(JPABookEntity1);
		assertEquals(1, bookJPADAO.findSpecificEntity(JPABookEntity1).size());
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
	public void testFindSpecificComplexBookEntity() throws EntityPersistenceException, EntityRetrievalException {
		tagJPADAO.persistEntity(JPATagEntity1);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity1);
		bookJPADAO.persistEntity(JPABookEntity1);
		bookJPADAO.persistEntity(JPABookEntity2);
		List<GAEBookEntity> foundBooks = bookJPADAO.findSpecificEntity(JPABookEntity1);
		assertEquals(1, foundBooks.size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		assertEquals(0, bookJPADAO.findSpecificEntity(JPABookEntity2).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, bookJPADAO.findSpecificEntity(JPABookEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failureBookJPADAO.findSpecificEntity(JPABookEntity1);
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
		tagJPADAO.persistEntity(JPATagEntity1);
		tagJPADAO.persistEntity(JPATagEntity2);
		tagJPADAO.persistEntity(JPATagEntity3);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity1);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity2);
		bookJPADAO.persistEntity(JPABookEntity1);
		JPABookEntity2.addToTagsAndBooks(JPATagEntity2);
		JPABookEntity2.addToTagsAndBooks(JPATagEntity3);
		bookJPADAO.persistEntity(JPABookEntity2);
		List<GAEBookEntity> foundBooksHavingTagAB = bookJPADAO.getBookEntityByTag(tagsAB);
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
		tagJPADAO.persistEntity(JPATagEntity1);
		tagJPADAO.persistEntity(JPATagEntity2);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity1);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity2);
		bookJPADAO.persistEntity(JPABookEntity1);
		JPABookEntity2.addToTagsAndBooks(JPATagEntity1);
		JPABookEntity2.addToTagsAndBooks(JPATagEntity2);
		bookJPADAO.persistEntity(JPABookEntity2);
		JPABookEntity3.addToTagsAndBooks(JPATagEntity1);
		bookJPADAO.persistEntity(JPABookEntity3);
		List<GAEBookEntity> foundBooksHavingTagA = bookJPADAO.getBookEntityByTag(tagsA);
		List<GAEBookEntity> foundBooksHavingTagAB = bookJPADAO.getBookEntityByTag(tagsAB);
		assertEquals("Incorrect Number of Bookentities with Tag A", 3, foundBooksHavingTagA.size());
		assertEquals("Incorrect Number of Bookentities with Tag A + B", 2, foundBooksHavingTagAB.size());
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
		bookJPADAO.persistEntity(JPABookEntity1);
		bookJPADAO.persistEntity(JPABookEntity2);
		List<GAEBookEntity> foundBooksHavingTagAB = bookJPADAO.getBookEntityByTag(tagsAB);
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
		List<GAEBookEntity> foundBooksHavingTagAB = bookJPADAO.getBookEntityByTag(tagsAB);
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
		failureBookJPADAO.getBookEntityByTag(tagsAB);
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
		userJPADAO.persistEntity(JPAUserEntity1);
		JPABookEntity1.addToUsersAndBooks(JPAUserEntity1);
		tagJPADAO.persistEntity(JPATagEntity1);
		tagJPADAO.persistEntity(JPATagEntity2);
		tagJPADAO.persistEntity(JPATagEntity3);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity1);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity2);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity3);
		bookJPADAO.persistEntity(JPABookEntity1);
		List<GAEBookEntity> foundBooksForUser = bookJPADAO.getBookEntityForUserByTag(tagsA);
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
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityForUserByTagNoUserSet() throws EntityRetrievalException, EntityPersistenceException {
		userJPADAO.persistEntity(JPAUserEntity1);
		JPABookEntity1.addToUsersAndBooks(JPAUserEntity1);
		tagJPADAO.persistEntity(JPATagEntity1);
		tagJPADAO.persistEntity(JPATagEntity2);
		tagJPADAO.persistEntity(JPATagEntity3);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity1);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity2);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity3);
		noUserBookJPADAO.persistEntity(JPABookEntity1);
		noUserBookJPADAO.getBookEntityForUserByTag(tagsA);
	}

	/**
	 * Test retrieving entity for user by tag using invalid EM
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityForUserByTagUsingInvalidEM() throws EntityRetrievalException {
		failureBookJPADAO.getBookEntityForUserByTag(tagsAB);
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
		userJPADAO.persistEntity(JPAUserEntity1);
		JPABookEntity1.addToUsersAndBooks(JPAUserEntity1);
		tagJPADAO.persistEntity(JPATagEntity1);
		tagJPADAO.persistEntity(JPATagEntity2);
		tagJPADAO.persistEntity(JPATagEntity3);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity1);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity2);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity3);
		bookJPADAO.persistEntity(JPABookEntity1);
		List<GAEBookEntity> foundBooksForUser = bookJPADAO.findSpecificBookEntityForUser(JPABookEntity1);
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
		userJPADAO.persistEntity(JPAUserEntity1);
		JPABookEntity2.addToUsersAndBooks(JPAUserEntity1);
		bookJPADAO.persistEntity(JPABookEntity2);
		List<GAEBookEntity> foundBooksForUser = bookJPADAO.findSpecificBookEntityForUser(JPABookEntity2);
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
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityForUserNoUserSet() throws EntityRetrievalException, EntityPersistenceException {
		userJPADAO.persistEntity(JPAUserEntity1);
		JPABookEntity1.addToUsersAndBooks(JPAUserEntity1);
		tagJPADAO.persistEntity(JPATagEntity1);
		tagJPADAO.persistEntity(JPATagEntity2);
		tagJPADAO.persistEntity(JPATagEntity3);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity1);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity2);
		JPABookEntity1.addToTagsAndBooks(JPATagEntity3);
		noUserBookJPADAO.persistEntity(JPABookEntity1);
		noUserBookJPADAO.findSpecificBookEntityForUser(JPABookEntity1);
	}

	/**
	 * Test retrieving specific entity for user using invalid EM
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityForUserUsingInvalidEM() throws EntityRetrievalException {
		failureBookJPADAO.findSpecificBookEntityForUser(JPABookEntity1);
	}
}
