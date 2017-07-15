package amtc.gue.ws.test.shopping.persistence.dao;

import static org.junit.Assert.*;

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
import amtc.gue.ws.shopping.persistence.dao.ShopDAO;
import amtc.gue.ws.shopping.persistence.dao.objectify.ShopObjectifyDAOImpl;
import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyShopEntity;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;
import amtc.gue.ws.test.shopping.ShoppingTest;

/**
 * Testclass for the Shop Objectify DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopDAOObjectifyTest extends ShoppingTest implements IBaseDAOTest {
	private static ShopDAO<GAEShopEntity, GAEObjectifyShopEntity, String> failureShopObjectifyDAO;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		setUpBasicShoppingEnvironment();
		setupDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(failureShopObjectifyDAO);
	}

	@Override
	@Test
	public void testDAOSetup() {
		assertNotNull(shopObjectifyDAO);
	}

	@Override
	@Test
	public void testAddEntity() throws EntityPersistenceException {
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
		assertNotNull(objectifyShopEntity1.getKey());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureShopObjectifyDAO.persistEntity(objectifyShopEntity1);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		List<GAEShopEntity> foundShops = shopObjectifyDAO.findAllEntities();
		assertEquals(0, foundShops.size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
		shopObjectifyDAO.persistEntity(objectifyShopEntity2);
		assertEquals(2, shopObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureShopObjectifyDAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
		assertNotNull(shopObjectifyDAO.findEntityById(objectifyShopEntity1.getKey()));
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		shopObjectifyDAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failureShopObjectifyDAO.findEntityById(objectifyShopEntity1.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
		shopObjectifyDAO.persistEntity(objectifyShopEntity2);
		assertEquals(2, shopObjectifyDAO.findAllEntities().size());
		shopObjectifyDAO.removeEntity(objectifyShopEntity1);
		assertEquals(1, shopObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		shopObjectifyDAO.removeEntity(objectifyShopEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		shopObjectifyDAO.removeEntity(objectifyShopEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureShopObjectifyDAO.removeEntity(objectifyShopEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
		shopObjectifyDAO.updateEntity(objectifyShopEntity1);
		assertNotNull(objectifyShopEntity1.getKey());
	}

	@Override
	@Test
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		shopObjectifyDAO.updateEntity(objectifyShopEntity1);
		assertNotNull(objectifyShopEntity1.getKey());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failureShopObjectifyDAO.updateEntity(objectifyShopEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
		shopObjectifyDAO.persistEntity(objectifyShopEntity2);
		List<GAEShopEntity> list = shopObjectifyDAO.findAllEntities();
		assertEquals(2, list.size());
		assertEquals(1, shopObjectifyDAO.findSpecificEntity(objectifyShopEntity1).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		GAEObjectifyShopEntity searchEntity = new GAEObjectifyShopEntity();
		searchEntity.setShopName(SHOPNAME);
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
		shopObjectifyDAO.persistEntity(objectifyShopEntity2);
		assertEquals(2, shopObjectifyDAO.findAllEntities().size());
		List<GAEShopEntity> list = shopObjectifyDAO.findSpecificEntity(searchEntity);
		assertEquals(1, list.size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, shopObjectifyDAO.findSpecificEntity(objectifyShopEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failureShopObjectifyDAO.findSpecificEntity(objectifyShopEntity1);
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
	private static void setupDAOMocks()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		failureShopObjectifyDAO = EasyMock.createNiceMock(ShopObjectifyDAOImpl.class);
		EasyMock.expect(failureShopObjectifyDAO.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.expect(failureShopObjectifyDAO.findEntityById(null)).andThrow(new EntityRetrievalException());
		EasyMock.expect(failureShopObjectifyDAO.findSpecificEntity(EasyMock.isA(GAEObjectifyShopEntity.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failureShopObjectifyDAO.persistEntity(EasyMock.isA(GAEObjectifyShopEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(failureShopObjectifyDAO.removeEntity(EasyMock.isA(GAEObjectifyShopEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.expect(failureShopObjectifyDAO.updateEntity(EasyMock.isA(GAEObjectifyShopEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.replay(failureShopObjectifyDAO);
	}
}
