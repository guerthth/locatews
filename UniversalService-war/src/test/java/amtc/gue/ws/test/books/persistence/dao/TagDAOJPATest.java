package amtc.gue.ws.test.books.persistence.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;

/**
 * Testclass for the Tag DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagDAOJPATest extends BookServiceJPATest {
	@Override
	public void testDAOSetup() {
		assertNotNull(tagEntityDAO);
		assertNotNull(failureTagEntityDAO);
	}

	@Override
	public void testAddEntity() throws EntityPersistenceException {
		assertNull(tagEntity1.getKey());
		assertNull(tagEntity2.getKey());
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		assertNotNull(tagEntity1.getKey());
		assertNotNull(tagEntity2.getKey());
	}

	/**
	 * Test adding tagentity with single book
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public void testAddTagEntityWithSingleBook()
			throws EntityPersistenceException {
		assertNull(tagEntity1.getKey());
		bookEntityDAO.persistEntity(bookEntity1);
		tagEntity1.addToBooks(bookEntity1);
		assertEquals(1, tagEntity1.getBooks().size());
		tagEntityDAO.persistEntity(tagEntity1);
		assertNotNull(tagEntity1.getKey());
	}

	/**
	 * Test adding entity with multiple books
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
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

	@Override
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity1);
	}

	@Override
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureTagEntityDAO.persistEntity(tagEntity1);
	}

	@Override
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		assertEquals(0, tagEntityDAO.findAllEntities().size());
	}

	@Override
	public void testGetEntitiesAfterAddingSimpleEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		tagEntityDAO.persistEntity(tagEntity3);
		tagEntityDAO.persistEntity(tagEntity4);
		assertEquals(4, tagEntityDAO.findAllEntities().size());
	}

	/**
	 * Test retrieving entity after adding complex tag entities
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testGetBookEntitiesAfterAddingComplexTagEntities()
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

	@Override
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureTagEntityDAO.findAllEntities();
	}

	@Override
	public void testGetEntityById() throws EntityRetrievalException,
			EntityPersistenceException {
		tagEntityDAO.persistEntity(tagEntity1);
		GAEJPATagEntity retrievedTagEntity = tagEntityDAO
				.findEntityById(tagEntity1.getKey());
		assertNotNull(retrievedTagEntity);
		assertEquals(tagEntity1.getKey(), retrievedTagEntity.getKey());
	}

	/**
	 * Test retrieving complex entity (having books)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
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

	@Override
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		tagEntityDAO.findEntityById(null);
	}

	@Override
	public void testGetEntityByIdUsingInvalidEM()
			throws EntityPersistenceException, EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		failureTagEntityDAO.findEntityById(tagEntity1.getKey());
	}

	@Override
	public void testDeleteEntity() throws EntityRetrievalException,
			EntityRemovalException, EntityPersistenceException {
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		assertEquals(2, tagEntityDAO.findAllEntities().size());
		tagEntityDAO.removeEntity(tagEntity1);
		List<GAEJPATagEntity> foundTagEntities = tagEntityDAO.findAllEntities();
		assertEquals(1, foundTagEntities.size());
		assertEquals(tagEntity2.getKey(), foundTagEntities.get(0).getKey());
	}

	/**
	 * Test deleting complex entity
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 */
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

	@Override
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		GAEJPATagEntity removedTagEntity = tagEntityDAO
				.removeEntity(tagEntity1);
		assertNull(removedTagEntity);
	}

	@Override
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureTagEntityDAO.removeEntity(tagEntity1);
	}

	@Override
	public void testUpdateSimpleEntity() throws EntityRetrievalException,
			EntityPersistenceException {
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

	/**
	 * Test updating complex entity
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
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

	@Override
	public void testUpdateEntityWithoutAdding()
			throws EntityPersistenceException {
		tagEntityDAO.updateEntity(tagEntity1);
	}

	@Override
	public void testUpdateEntityUsingInvalidEM()
			throws EntityPersistenceException {
		failureTagEntityDAO.updateEntity(tagEntity1);
	}

	@Override
	public void testFindSpecificEntity() throws EntityRetrievalException,
			EntityPersistenceException {
		tagEntityDAO.persistEntity(tagEntity1);
		List<GAEJPATagEntity> foundTags = tagEntityDAO
				.findSpecificEntity(tagEntity1);
		assertEquals(1, foundTags.size());
	}

	/**
	 * Test retriebing complex entity
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
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

	@Override
	public void testFindSpecificEntityBySearchCriteria()
			throws EntityRetrievalException, EntityPersistenceException {
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

	@Override
	public void testFindSpecificEntityBeforeAdding()
			throws EntityRetrievalException {
		List<GAEJPATagEntity> foundTags = tagEntityDAO
				.findSpecificEntity(tagEntity1);
		assertEquals(0, foundTags.size());
	}

	@Override
	public void testFindSpecificEntityUsingInvalidEM()
			throws EntityRetrievalException {
		failureTagEntityDAO.findSpecificEntity(tagEntity1);
	}
}
