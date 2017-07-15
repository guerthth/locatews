package amtc.gue.ws.test.shopping.persistence.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;
import java.util.GregorianCalendar;
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
import amtc.gue.ws.shopping.persistence.dao.BillDAO;
import amtc.gue.ws.shopping.persistence.dao.objectify.BillObjectifyDAOImpl;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillEntity;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;
import amtc.gue.ws.test.shopping.ShoppingTest;

/**
 * Testclass for the Bill Objectify DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BillDAOObjectifyTest extends ShoppingTest implements IBaseDAOTest {
	private static BillDAO<GAEBillEntity, GAEObjectifyBillEntity, String> failureBillObjectifyDAO;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		setUpBasicShoppingEnvironment();
		setupDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(failureBillObjectifyDAO);
	}

	@Override
	public void testDAOSetup() {
		assertNotNull(userObjectifyDAO);
		assertNotNull(billinggroupObjectifyDAO);
		assertNotNull(shopObjectifyDAO);
		assertNotNull(billObjectifyDAO);
	}

	@Override
	@Test
	public void testAddEntity() throws EntityPersistenceException {
		billObjectifyDAO.persistEntity(objectifyBillEntity1);
	}

	/**
	 * Testing if adding bill with relationships works
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public void testAddBillEntityWithUserAndBillingGroups()
			throws EntityRetrievalException, EntityPersistenceException {
		// precheck DB status
		assertEquals(0, userObjectifyDAO.findAllEntities().size());
		assertEquals(0, billinggroupObjectifyDAO.findAllEntities().size());
		assertEquals(0, billObjectifyDAO.findAllEntities().size());
		assertEquals(0, shopObjectifyDAO.findAllEntities().size());

		// add bill with user
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		GAEObjectifyBillEntity billEntity = new GAEObjectifyBillEntity(objectifyShopEntity1.getKey(),
				Long.valueOf(objectifyShopEntity1.getKey()), Long.valueOf(objectifyBillinggroupEntity1.getKey()));
		billObjectifyDAO.persistEntity(billEntity);

		// postcheck DB status
		assertEquals(2, userObjectifyDAO.findAllEntities().size());
		assertEquals(1, shopObjectifyDAO.findAllEntities().size());
		assertEquals(1, billinggroupObjectifyDAO.findAllEntities().size());
		assertEquals(1, billObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		billObjectifyDAO.persistEntity(objectifyBillEntity1);
		billObjectifyDAO.persistEntity(objectifyBillEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureBillObjectifyDAO.persistEntity(objectifyBillEntity1);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		List<GAEBillEntity> foundBills = billObjectifyDAO.findAllEntities();
		assertEquals(0, foundBills.size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		billObjectifyDAO.persistEntity(objectifyBillEntity1);
		billObjectifyDAO.persistEntity(objectifyBillEntity2);
		assertEquals(2, billObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureBillObjectifyDAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		billObjectifyDAO.persistEntity(objectifyBillEntity1);
		assertNotNull(billObjectifyDAO.findEntityById(objectifyBillEntity1.getKey()));
	}

	/**
	 * Testing if retrieval of all billinggroupentity by id works as expected
	 * when complex entities were added
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testGetComplexBillinggroupEntityById() throws EntityPersistenceException, EntityRetrievalException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		GAEObjectifyBillEntity billEntity = new GAEObjectifyBillEntity(objectifyUserEntity1.getKey(), null,
				Long.valueOf(objectifyBillinggroupEntity1.getKey()));
		billEntity.setDate(new Date());
		billEntity.setAmount(22.0);
		billObjectifyDAO.persistEntity(billEntity);
		List<GAEBillEntity> foundBills = billObjectifyDAO.findAllEntities();
		assertEquals(1, foundBills.size());
		assertEquals(1, billObjectifyDAO.findSpecificEntity(billEntity).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		billObjectifyDAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failureBillObjectifyDAO.findEntityById(objectifyBillEntity1.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		billObjectifyDAO.persistEntity(objectifyBillEntity1);
		billObjectifyDAO.persistEntity(objectifyBillEntity2);
		assertEquals(2, billObjectifyDAO.findAllEntities().size());
		billObjectifyDAO.removeEntity(objectifyBillEntity1);
		assertEquals(1, billObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		billinggroupObjectifyDAO.removeEntity(objectifyBillinggroupEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		billinggroupObjectifyDAO.removeEntity(objectifyBillinggroupEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureBillObjectifyDAO.removeEntity(objectifyBillEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		billObjectifyDAO.persistEntity(objectifyBillEntity1);
		billObjectifyDAO.updateEntity(objectifyBillEntity1);
		assertNotNull(objectifyBillEntity1);
	}

	@Override
	@Test
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		billObjectifyDAO.updateEntity(objectifyBillEntity1);
		assertNotNull(objectifyBillEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failureBillObjectifyDAO.updateEntity(objectifyBillEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		billObjectifyDAO.persistEntity(objectifyBillEntity1);
		billObjectifyDAO.persistEntity(objectifyBillEntity2);
		List<GAEBillEntity> list = billObjectifyDAO.findAllEntities();
		assertEquals(2, list.size());
		assertEquals(1, billObjectifyDAO.findSpecificEntity(objectifyBillEntity1).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		GregorianCalendar gc = new GregorianCalendar(2014, 01, 01);
		Date date = gc.getTime();
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		GAEObjectifyBillEntity billEntity = new GAEObjectifyBillEntity(objectifyUserEntity1.getKey(),
				Long.valueOf(objectifyShopEntity1.getKey()), Long.valueOf(objectifyBillinggroupEntity1.getKey()));
		billEntity.setDate(date);
		billEntity.setAmount(22.0);
		billObjectifyDAO.persistEntity(billEntity);
		GAEObjectifyBillEntity searchBillEntity = new GAEObjectifyBillEntity(objectifyUserEntity1.getKey(), null,
				Long.valueOf(objectifyBillinggroupEntity1.getKey()));
		searchBillEntity.setDate(date);
		searchBillEntity.setAmount(22.0);
		assertEquals(1, billObjectifyDAO.findSpecificEntity(searchBillEntity).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, billObjectifyDAO.findSpecificEntity(objectifyBillEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failureBillObjectifyDAO.findSpecificEntity(objectifyBillEntity1);
	}

	/**
	 * Testing if getting Bills for User works as expected
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public void testGetBillsForUser() throws EntityPersistenceException, EntityRetrievalException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		shopObjectifyDAO.persistEntity(objectifyShopEntity1);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		// adding first bill for user
		GAEObjectifyBillEntity billEntity = new GAEObjectifyBillEntity(objectifyUserEntity1.getKey(),
				Long.valueOf(objectifyShopEntity1.getKey()), Long.valueOf(objectifyBillinggroupEntity1.getKey()));
		billObjectifyDAO.persistEntity(billEntity);
		assertEquals(1, billObjectifyDAO.getBillsForUser(serviceUser).size());
		// adding second bill for user
		GAEObjectifyBillEntity billEntity2 = new GAEObjectifyBillEntity(objectifyUserEntity1.getKey(),
				Long.valueOf(objectifyShopEntity1.getKey()), Long.valueOf(objectifyBillinggroupEntity1.getKey()));
		billObjectifyDAO.persistEntity(billEntity2);
		assertEquals(2, billObjectifyDAO.getBillsForUser(serviceUser).size());
	}

	/**
	 * Method setting up DAO Mocjs for testing purpose
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
		failureBillObjectifyDAO = EasyMock.createNiceMock(BillObjectifyDAOImpl.class);
		EasyMock.expect(failureBillObjectifyDAO.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.expect(failureBillObjectifyDAO.findEntityById(null)).andThrow(new EntityRetrievalException());
		EasyMock.expect(failureBillObjectifyDAO.findSpecificEntity(EasyMock.isA(GAEObjectifyBillEntity.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failureBillObjectifyDAO.persistEntity(EasyMock.isA(GAEObjectifyBillEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(failureBillObjectifyDAO.removeEntity(EasyMock.isA(GAEObjectifyBillEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.expect(failureBillObjectifyDAO.updateEntity(EasyMock.isA(GAEObjectifyBillEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.replay(failureBillObjectifyDAO);
	}
}
