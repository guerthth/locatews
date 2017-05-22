package amtc.gue.ws.test.books.persistence.dao.jpa;

import static org.junit.Assert.*;

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
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.persistence.model.tag.jpa.GAEJPATagEntity;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;
import amtc.gue.ws.test.books.BookTest;

/**
 * Testclass for the Tag JPA DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagDAOJPATest extends BookTest implements IBaseDAOTest {
	@BeforeClass
	public static void oneTimeInititalSetup() {
		setUpBasicBookEnvironment();
	}

	@Override
	@Test
	public void testDAOSetup() {
		assertNotNull(bookJPADAO);
		assertNotNull(tagJPADAO);
		assertNotNull(failureTagJPADAO);
	}

	@Override
	@Test
	public void testAddEntity() throws EntityPersistenceException {
		assertNotNull(JPATagEntity1.getKey());
		tagJPADAO.persistEntity(JPATagEntity1);
		assertNotNull(JPATagEntity1.getKey());
	}

	/**
	 * Test adding complex entity with single book
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public void testAddTagEntityWithSingleBook() throws EntityPersistenceException {
		GAEBookEntity addedBookEntity = bookJPADAO.persistEntity(JPABookEntity1);
		JPATagEntity1.addToBooksAndTags(addedBookEntity);
		tagJPADAO.persistEntity(JPATagEntity1);
		assertEquals(1, JPATagEntity1.getBooks().size());
	}

	/**
	 * Test adding complex entity with multiple books
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testAddTagEntityWithMultipleTags() throws EntityPersistenceException, EntityRetrievalException {
		bookJPADAO.persistEntity(JPABookEntity1);
		bookJPADAO.persistEntity(JPABookEntity2);
		JPATagEntity1.addToBooksAndTags(JPABookEntity1);
		JPATagEntity1.addToBooksAndTags(JPABookEntity2);
		tagJPADAO.persistEntity(JPATagEntity1);
		assertEquals(2, JPATagEntity1.getBooks().size());
		assertEquals(2, bookJPADAO.findAllEntities().size());
		assertEquals(1, tagJPADAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		tagJPADAO.persistEntity(JPATagEntity1);
		tagJPADAO.persistEntity(JPATagEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureTagJPADAO.persistEntity(JPATagEntity1);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		assertEquals(0, tagJPADAO.findAllEntities().size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		tagJPADAO.persistEntity(JPATagEntity1);
		tagJPADAO.persistEntity(JPATagEntity2);
		assertEquals(2, tagJPADAO.findAllEntities().size());
	}

	/**
	 * Test retrieving complex entities (with books)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testGetAllTagEntitiesAfterAddingComplexRoleEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		bookJPADAO.persistEntity(JPABookEntity1);
		bookJPADAO.persistEntity(JPABookEntity2);
		JPATagEntity1.addToBooksAndTags(JPABookEntity1);
		JPATagEntity1.addToBooksAndTags(JPABookEntity2);
		tagJPADAO.persistEntity(JPATagEntity1);
		List<GAEBookEntity> foundBooks = bookJPADAO.findAllEntities();
		List<GAETagEntity> foundTags = tagJPADAO.findAllEntities();
		assertEquals(2, foundBooks.size());
		assertEquals(1, foundTags.size());
		List<GAEBookEntity> tagBooks = new ArrayList<>(foundTags.get(0).getBooks());
		assertEquals(2, tagBooks.size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureTagJPADAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		tagJPADAO.persistEntity(JPATagEntity1);
		GAETagEntity retrievedEntity = tagJPADAO.findEntityById(JPATagEntity1.getKey());
		assertEquals(JPATagEntity1.getKey(), retrievedEntity.getKey());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		tagJPADAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failureTagJPADAO.findEntityById(JPATagEntity1.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		tagJPADAO.persistEntity(JPATagEntity1);
		tagJPADAO.persistEntity(JPATagEntity2);
		assertEquals(2, tagJPADAO.findAllEntities().size());
		tagJPADAO.removeEntity(JPATagEntity1);
		assertEquals(1, tagJPADAO.findAllEntities().size());
	}

	/**
	 * Test deleting complex entity (with books)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to removing an entity
	 */
	@Test
	public void testDeleteComplexTagEntity()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		bookJPADAO.persistEntity(JPABookEntity1);
		JPATagEntity1.addToBooksAndTags(JPABookEntity1);
		tagJPADAO.persistEntity(JPATagEntity1);
		assertEquals(1, tagJPADAO.findAllEntities().size());
		assertEquals(1, bookJPADAO.findAllEntities().size());
		tagJPADAO.removeEntity(JPATagEntity1);
		assertEquals(0, tagJPADAO.findAllEntities().size());
		assertEquals(1, bookJPADAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		tagJPADAO.removeEntity(JPATagEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		tagJPADAO.removeEntity(JPATagEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureTagJPADAO.removeEntity(JPATagEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		bookJPADAO.persistEntity(JPABookEntity1);
		JPATagEntity1.addToBooksAndTags(JPABookEntity1);
		tagJPADAO.persistEntity(JPATagEntity1);
		assertEquals(1, JPATagEntity1.getBooks().size());
		JPATagEntity1.removeBook(JPABookEntity1);
		tagJPADAO.updateEntity(JPATagEntity1);
		GAETagEntity foundEntity = tagJPADAO.findEntityById(JPATagEntity1.getKey());
		assertEquals(0, foundEntity.getBooks().size());
	}

	/**
	 * Test updating complex entity (with books)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRemovalException
	 */
	@Test
	public void testUpdateComplexTagEntity()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		bookJPADAO.persistEntity(JPABookEntity1);
		JPATagEntity1.addToBooksAndTags(JPABookEntity1);
		tagJPADAO.persistEntity(JPATagEntity1);
		assertEquals(1, bookJPADAO.findAllEntities().size());
		assertEquals(1, tagJPADAO.findAllEntities().size());

		GAETagEntity foundTagEntity = tagJPADAO.findEntityById(JPATagEntity1.getKey());
		ArrayList<GAEBookEntity> books = new ArrayList<>(foundTagEntity.getBooks());
		GAEBookEntity firstBook = books.get(0);
		assertEquals(JPABookEntity1.getKey(), firstBook.getKey());

		// remove bookenentity
		JPATagEntity1.removeBook(JPABookEntity1);
		assertNotNull(JPATagEntity1.getBooks());
		tagJPADAO.updateEntity(JPATagEntity1);
		foundTagEntity = tagJPADAO.findEntityById(JPATagEntity1.getKey());
		assertEquals(0, foundTagEntity.getBooks().size());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		GAEJPATagEntity updatedEntity = new GAEJPATagEntity();
		tagJPADAO.updateEntity(updatedEntity);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failureTagJPADAO.updateEntity(JPATagEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		tagJPADAO.persistEntity(JPATagEntity1);
		assertEquals(1, tagJPADAO.findSpecificEntity(JPATagEntity1).size());
	}

	/**
	 * Test retrieving specific complex entity (with books)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testFindSpecificComplexTagEntity() throws EntityPersistenceException, EntityRetrievalException {
		bookJPADAO.persistEntity(JPABookEntity1);
		JPATagEntity1.addToBooksAndTags(JPABookEntity1);
		tagJPADAO.persistEntity(JPATagEntity1);
		assertEquals(1, tagJPADAO.findSpecificEntity(JPATagEntity1).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		assertEquals(0, tagJPADAO.findSpecificEntity(JPATagEntity2).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, tagJPADAO.findSpecificEntity(JPATagEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failureTagJPADAO.findSpecificEntity(JPATagEntity1);
	}
}
