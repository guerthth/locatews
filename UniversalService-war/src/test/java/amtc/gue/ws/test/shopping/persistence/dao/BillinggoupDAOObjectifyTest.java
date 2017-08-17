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
import amtc.gue.ws.shopping.persistence.dao.BillinggroupDAO;
import amtc.gue.ws.shopping.persistence.dao.objectify.BillinggroupObjectifyDAOImpl;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillinggroupEntity;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;
import amtc.gue.ws.test.shopping.ShoppingTest;

/**
 * Testclass for the Billinggroup Objectify DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BillinggoupDAOObjectifyTest extends ShoppingTest implements IBaseDAOTest {
	private static BillinggroupDAO<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String> failureBillinggroupObjectifyDAO;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		setUpBasicShoppingEnvironment();
		setupDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(failureBillinggroupObjectifyDAO);
	}

	@Override
	@Test
	public void testDAOSetup() {
		assertNotNull(userObjectifyDAO);
		assertNotNull(billinggroupObjectifyDAO);
	}

	@Override
	@Test
	public void testAddEntity() throws EntityPersistenceException {
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
	}

	/**
	 * Testing if adding complex billinggroups works as expected
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testAddBillinggroupEntityWithUser() throws EntityPersistenceException, EntityRetrievalException {
		// precheck DB status
		assertEquals(0, userObjectifyDAO.findAllEntities().size());
		assertEquals(0, billinggroupObjectifyDAO.findAllEntities().size());

		// add billinggroup with users
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		objectifyBillinggroupEntity1.addToUsersAndBillinggroups(objectifyUserEntity1);

		// postcheck DB status
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
		assertEquals(1, billinggroupObjectifyDAO.findAllEntities().size());
	}

	/**
	 * Testing if adding complex billinggroups works as expected Multiple users
	 * are added
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testAddBillinggroupEntityWithMultipleUsers()
			throws EntityRetrievalException, EntityPersistenceException {
		// precheck DB status
		assertEquals(0, userObjectifyDAO.findAllEntities().size());
		assertEquals(0, billinggroupObjectifyDAO.findAllEntities().size());

		// add billinggroup with users
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		objectifyBillinggroupEntity1.addToUsersAndBillinggroups(objectifyUserEntity1);
		objectifyBillinggroupEntity1.addToUsersAndBillinggroups(objectifyUserEntity2);

		// postcheck DB status
		assertEquals(2, userObjectifyDAO.findAllEntities().size());
		assertEquals(1, billinggroupObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureBillinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		List<GAEBillinggroupEntity> foundBillingroups = billinggroupObjectifyDAO.findAllEntities();
		assertEquals(0, foundBillingroups.size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity2);
		assertEquals(2, billinggroupObjectifyDAO.findAllEntities().size());
	}

	/**
	 * Testing if retrieval of all billinggroupentities works as expected when
	 * complex entities were added
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testGetBillinggroupsAfterAddingComplexEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		objectifyBillinggroupEntity1.addToUsersAndBillinggroups(objectifyUserEntity1);
		objectifyBillinggroupEntity1.addToUsersAndBillinggroups(objectifyUserEntity2);
		assertEquals(1, billinggroupObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureBillinggroupObjectifyDAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		assertNotNull(billinggroupObjectifyDAO.findEntityById(objectifyBillinggroupEntity1.getWebsafeKey()));
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
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		objectifyBillinggroupEntity1.addToUsersAndBillinggroups(objectifyUserEntity1);
		objectifyBillinggroupEntity1.addToUsersAndBillinggroups(objectifyUserEntity2);
		assertNotNull(billinggroupObjectifyDAO.findEntityById(objectifyBillinggroupEntity1.getWebsafeKey()));
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		billinggroupObjectifyDAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failureBillinggroupObjectifyDAO.findEntityById(objectifyBillinggroupEntity1.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity2);
		assertEquals(2, billinggroupObjectifyDAO.findAllEntities().size());
		billinggroupObjectifyDAO.removeEntity(objectifyBillinggroupEntity1);
		assertEquals(1, billinggroupObjectifyDAO.findAllEntities().size());
	}

	/**
	 * Testing if removal of complex billinggroupentity works as expected
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 */
	@Test
	public void testDeleteComplexBillinggroupEntity()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		objectifyBillinggroupEntity1.addToUsersAndBillinggroups(objectifyUserEntity1);
		objectifyBillinggroupEntity1.addToUsersAndBillinggroups(objectifyUserEntity2);
		assertEquals(1, billinggroupObjectifyDAO.findAllEntities().size());
		billinggroupObjectifyDAO.removeEntity(objectifyBillinggroupEntity1);
		assertEquals(0, billinggroupObjectifyDAO.findAllEntities().size());
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
		failureBillinggroupObjectifyDAO.removeEntity(objectifyBillinggroupEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		billinggroupObjectifyDAO.updateEntity(objectifyBillinggroupEntity1);
		assertNotNull(objectifyBillinggroupEntity1.getKey());
	}

	@Override
	@Test
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		billinggroupObjectifyDAO.updateEntity(objectifyBillinggroupEntity1);
		assertNotNull(objectifyBillinggroupEntity1.getKey());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failureBillinggroupObjectifyDAO.updateEntity(objectifyBillinggroupEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity2);
		List<GAEBillinggroupEntity> list = billinggroupObjectifyDAO.findAllEntities();
		assertEquals(2, list.size());
		assertEquals(1, billinggroupObjectifyDAO.findSpecificEntity(objectifyBillinggroupEntity1).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		GAEObjectifyBillinggroupEntity searchEntity = new GAEObjectifyBillinggroupEntity();
		searchEntity.addToUsersOnly(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity1);
		billinggroupObjectifyDAO.persistEntity(objectifyBillinggroupEntity2);
		objectifyBillinggroupEntity1.addToUsersAndBillinggroups(objectifyUserEntity1);
		billinggroupObjectifyDAO.updateEntity(objectifyBillinggroupEntity1);
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
		assertEquals(2, billinggroupObjectifyDAO.findAllEntities().size());
		List<GAEBillinggroupEntity> list = billinggroupObjectifyDAO.findSpecificEntity(searchEntity);
		assertEquals(1, list.size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, billinggroupObjectifyDAO.findSpecificEntity(objectifyBillinggroupEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failureBillinggroupObjectifyDAO.findSpecificEntity(objectifyBillinggroupEntity1);
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
		failureBillinggroupObjectifyDAO = EasyMock.createNiceMock(BillinggroupObjectifyDAOImpl.class);
		EasyMock.expect(failureBillinggroupObjectifyDAO.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.expect(failureBillinggroupObjectifyDAO.findEntityById(null)).andThrow(new EntityRetrievalException());
		EasyMock.expect(
				failureBillinggroupObjectifyDAO.findSpecificEntity(EasyMock.isA(GAEObjectifyBillinggroupEntity.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(
				failureBillinggroupObjectifyDAO.persistEntity(EasyMock.isA(GAEObjectifyBillinggroupEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(
				failureBillinggroupObjectifyDAO.removeEntity(EasyMock.isA(GAEObjectifyBillinggroupEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.expect(
				failureBillinggroupObjectifyDAO.updateEntity(EasyMock.isA(GAEObjectifyBillinggroupEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.replay(failureBillinggroupObjectifyDAO);
	}
}
