package amtc.gue.ws.test.books.persistence.dao.objectify;

import static org.junit.Assert.*;

import java.util.List;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.dao.tag.objectify.TagObjectifyDAOImpl;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.persistence.model.tag.objectify.GAEObjectifyTagEntity;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;
import amtc.gue.ws.test.books.BookTest;

/**
 * Testclass for the Tag OBjectify DAO
 * 
 * @author Thomas
 *
 */
public class TagDAOObjectifyTest extends BookTest implements IBaseDAOTest {
	private static TagDAO<GAETagEntity, GAEObjectifyTagEntity, String> failureTagObjectifyDAO;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		setUpBasicBookEnvironment();
		setUpDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(failureTagObjectifyDAO);
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
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
	}

	/**
	 * Test adding complex entity with single book
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testAddTagEntityWithSingleBook() throws EntityPersistenceException, EntityRetrievalException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		objectifyTagEntity1.addToBooksAndTags(objectifyBookEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		GAETagEntity foundTagEntity = tagObjectifyDAO.findEntityById(objectifyTagEntity1.getWebsafeKey());
		assertEquals(1, foundTagEntity.getBooks().size());
		GAEBookEntity foundBookForTag = foundTagEntity.getBooks().iterator().next();
		assertEquals(objectifyBookEntity1.getKey(), foundBookForTag.getKey());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureTagObjectifyDAO.persistEntity(objectifyTagEntity1);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		List<GAETagEntity> foundTags = tagObjectifyDAO.findAllEntities();
		assertEquals(0, foundTags.size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		List<GAETagEntity> foundTags = tagObjectifyDAO.findAllEntities();
		assertEquals(2, foundTags.size());
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
	public void testGetTagEntitiesAfterAddingComplexEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		objectifyTagEntity1.addToBooksAndTags(objectifyBookEntity1);
		objectifyTagEntity1.addToBooksAndTags(objectifyBookEntity2);
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);

		assertEquals(1, tagObjectifyDAO.findAllEntities().size());
		assertEquals(2, bookObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureTagObjectifyDAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		GAETagEntity tagEntity = tagObjectifyDAO.findEntityById(objectifyTagEntity1.getWebsafeKey());
		assertNotNull(tagEntity);
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
	public void testGetComplexTagEntityById() throws EntityPersistenceException, EntityRetrievalException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		objectifyTagEntity1.addToBooksAndTags(objectifyBookEntity1);
		objectifyTagEntity1.addToBooksAndTags(objectifyBookEntity2);
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);

		GAETagEntity tagEntity = tagObjectifyDAO.findEntityById(objectifyTagEntity1.getWebsafeKey());
		assertNotNull(tagEntity);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		tagObjectifyDAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failureTagObjectifyDAO.findEntityById(objectifyTagEntity1.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		assertEquals(2, tagObjectifyDAO.findAllEntities().size());
		tagObjectifyDAO.removeEntity(objectifyTagEntity1);
		assertEquals(1, tagObjectifyDAO.findAllEntities().size());
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
	public void testDeleteComplexEntity()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);
		objectifyTagEntity1.addToBooksAndTags(objectifyBookEntity1);
		objectifyTagEntity1.addToBooksAndTags(objectifyBookEntity2);
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);

		assertEquals(2, tagObjectifyDAO.findAllEntities().size());
		assertEquals(2, bookObjectifyDAO.findAllEntities().size());
		tagObjectifyDAO.removeEntity(objectifyTagEntity1);
		assertEquals(2, bookObjectifyDAO.findAllEntities().size());
		assertEquals(1, tagObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		try {
			assertEquals(0, tagObjectifyDAO.findAllEntities().size());
			tagObjectifyDAO.persistEntity(objectifyTagEntity2);
			assertEquals(1, tagObjectifyDAO.findAllEntities().size());
			tagObjectifyDAO.removeEntity(objectifyTagEntity1);
			assertEquals(1, tagObjectifyDAO.findAllEntities().size());
		} catch (Exception e) {
			fail();
		}
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		GAETagEntity tagEntity = new GAEObjectifyTagEntity();
		tagObjectifyDAO.removeEntity(tagEntity);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureTagObjectifyDAO.removeEntity(objectifyTagEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		objectifyTagEntity1.addToBooksAndTags(objectifyBookEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		assertEquals(1, tagObjectifyDAO.findSpecificEntity(objectifyTagEntity1).size());
		assertEquals(1, tagObjectifyDAO.findAllEntities().get(0).getBooks().size());
		objectifyTagEntity1.removeBook(objectifyBookEntity1);
		tagObjectifyDAO.updateEntity(objectifyTagEntity1);
		assertEquals(1, tagObjectifyDAO.findAllEntities().size());
		assertEquals(1, bookObjectifyDAO.findAllEntities().size());
		assertEquals(0, tagObjectifyDAO.findEntityById(objectifyTagEntity1.getWebsafeKey()).getBooks().size());
	}

	@Override
	@Test
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		try {
			assertEquals(0, tagObjectifyDAO.findAllEntities().size());
			tagObjectifyDAO.updateEntity(objectifyTagEntity1);
			assertEquals(1, tagObjectifyDAO.findAllEntities().size());
		} catch (EntityRetrievalException e) {
			fail();
		}

	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failureTagObjectifyDAO.updateEntity(objectifyTagEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		assertEquals(1, tagObjectifyDAO.findSpecificEntity(objectifyTagEntity1).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		bookObjectifyDAO.persistEntity(objectifyBookEntity1);
		bookObjectifyDAO.persistEntity(objectifyBookEntity2);

		GAETagEntity searchTag = new GAEObjectifyTagEntity();
		searchTag.addToBooksOnly(objectifyBookEntity1);
		searchTag.addToBooksOnly(objectifyBookEntity2);

		objectifyTagEntity1.addToBooksAndTags(objectifyBookEntity1);
		objectifyTagEntity1.addToBooksAndTags(objectifyBookEntity2);
		objectifyTagEntity2.addToBooksAndTags(objectifyBookEntity2);
		tagObjectifyDAO.persistEntity(objectifyTagEntity1);
		tagObjectifyDAO.persistEntity(objectifyTagEntity2);
		assertEquals(2, bookObjectifyDAO.findAllEntities().size());
		assertEquals(2, tagObjectifyDAO.findAllEntities().size());
		assertEquals(1, tagObjectifyDAO.findSpecificEntity(searchTag).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, tagObjectifyDAO.findSpecificEntity(objectifyTagEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failureTagObjectifyDAO.findSpecificEntity(objectifyTagEntity1);
	}

	private static void setUpDAOMocks()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		failureTagObjectifyDAO = EasyMock.createNiceMock(TagObjectifyDAOImpl.class);
		EasyMock.expect(failureTagObjectifyDAO.persistEntity(EasyMock.isA(GAEObjectifyTagEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(failureTagObjectifyDAO.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.expect(failureTagObjectifyDAO.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failureTagObjectifyDAO.removeEntity(EasyMock.isA(GAEObjectifyTagEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.expect(failureTagObjectifyDAO.updateEntity(EasyMock.isA(GAEObjectifyTagEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(failureTagObjectifyDAO.findSpecificEntity(EasyMock.isA(GAEObjectifyTagEntity.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(failureTagObjectifyDAO);
	}
}
