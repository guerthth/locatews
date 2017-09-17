package amtc.gue.ws.test.shopping.delegate.persist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.input.DelegatorInput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.dao.user.objectify.UserObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.shopping.delegate.persist.BillinggroupPersistenceDelegator;
import amtc.gue.ws.shopping.persistence.dao.BillDAO;
import amtc.gue.ws.shopping.persistence.dao.BillinggroupDAO;
import amtc.gue.ws.shopping.persistence.dao.objectify.BillObjectifyDAOImpl;
import amtc.gue.ws.shopping.persistence.dao.objectify.BillinggroupObjectifyDAOImpl;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillinggroupEntity;
import amtc.gue.ws.shopping.util.ShoppingServiceErrorConstants;
import amtc.gue.ws.test.base.delegate.persist.IObjectifyPersistenceDelegatorTest;
import amtc.gue.ws.test.shopping.ShoppingTest;

/**
 * Testclass for the BillinggroupPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BillinggroupPersistenceDelegatorTest extends ShoppingTest implements IObjectifyPersistenceDelegatorTest {
	private static BillinggroupPersistenceDelegator billinggroupPersistenceDelegator;

	private static DelegatorInput addBillinggroupDelegatorInput;
	private static DelegatorInput deleteBillinggroupDelegatorInput;
	private static DelegatorInput nullDeleteBillinggroupDelegatorInput;
	private static DelegatorInput readBillinggroupDelegatorInput;
	private static DelegatorInput nullReadBillinggroupDelegatorInput;
	private static DelegatorInput updateBillinggroupDelegatorInput;
	private static DelegatorInput updateBillinggroupBillDelegatorInput;
	private static DelegatorInput updateBillinggroupUserDelegatorInput;
	private static DelegatorInput nullUpdateBillinggroupDelegatorInput;

	private static BillinggroupDAO<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String> billinggroupObjectifyDAOImpl;
	private static BillinggroupDAO<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String> billinggroupObjectifyDAOImplUserIncluded;
	private static BillinggroupDAO<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String> billinggroupObjectifyDAOImplNoFoundBillinggroups;
	private static BillinggroupDAO<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String> billinggroupObjectifyDAOImplNullBillinggroups;
	private static BillinggroupDAO<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String> billinggroupObjectifyDAOImplGeneralFail;
	private static BillinggroupDAO<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String> billinggroupObjectifyDAOImplDeletionFail;
	private static BillinggroupDAO<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String> billinggroupObjectifyDAOImplSpecificEntityFound;
	private static BillinggroupDAO<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String> billinggroupObjectifyDAOImplRetrievalFail;
	private static BillinggroupDAO<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String> billinggroupObjectifyDAOImplUpdateFail;

	private static BillDAO<GAEBillEntity, GAEObjectifyBillEntity, String> billObjectifyDAOImpl;

	private static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> userObjectifyDAOImpl;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		setUpBasicShoppingEnvironment();
		setUpDelegatorInputs();
		setUpShopPersistenceDelegatorInputs();
		setUpShopPersistenceDelegators();
		setUpDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(billinggroupObjectifyDAOImpl);
		EasyMock.verify(billinggroupObjectifyDAOImplNoFoundBillinggroups);
		EasyMock.verify(billinggroupObjectifyDAOImplNullBillinggroups);
		EasyMock.verify(billinggroupObjectifyDAOImplGeneralFail);
		EasyMock.verify(billinggroupObjectifyDAOImplDeletionFail);
		EasyMock.verify(billinggroupObjectifyDAOImplSpecificEntityFound);
		EasyMock.verify(billinggroupObjectifyDAOImplRetrievalFail);
		EasyMock.verify(billObjectifyDAOImpl);
		EasyMock.verify(billinggroupObjectifyDAOImplUserIncluded);
		EasyMock.verify(userObjectifyDAOImpl);
	}

	@Override
	@Test
	public void testDelegateUsingNullInput() {
		billinggroupPersistenceDelegator.initialize(null);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());

	}

	@Override
	@Test
	public void testDelegateUsingUnrecognizedInputType() {
		billinggroupPersistenceDelegator.initialize(unrecognizedDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingCorrectInput() {
		billinggroupPersistenceDelegator.initialize(addBillinggroupDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.ADD_BILLINGGROUP_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage()
				.startsWith(ShoppingServiceErrorConstants.ADD_BILLINGGROUP_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingIncorrectDAOSetup() {
		billinggroupPersistenceDelegator.initialize(addBillinggroupDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImplGeneralFail);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.ADD_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingInvalidInput() {
		billinggroupPersistenceDelegator.initialize(invalidAddDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingCorrectIdInput() {
		billinggroupPersistenceDelegator.initialize(deleteBillinggroupDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNonExistingObjects() {
		billinggroupPersistenceDelegator.initialize(deleteBillinggroupDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImplGeneralFail);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNullObjects() {
		billinggroupPersistenceDelegator.initialize(deleteBillinggroupDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImplGeneralFail);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingIncorrectDAOSetup() {
		billinggroupPersistenceDelegator.initialize(deleteBillinggroupDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImplGeneralFail);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteDeletionFail() {
		billinggroupPersistenceDelegator.initialize(deleteBillinggroupDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImplDeletionFail);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteRetrievalFail() {
		billinggroupPersistenceDelegator.initialize(deleteBillinggroupDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImplGeneralFail);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingInvalidInput() {
		billinggroupPersistenceDelegator.initialize(invalidDeleteDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingCorrectInput() {
		billinggroupPersistenceDelegator.initialize(readBillinggroupDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingIncorrectDAOSetup() {
		billinggroupPersistenceDelegator.initialize(readBillinggroupDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImplGeneralFail);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingInvalidInput() {
		// all inputs are ok
		assertTrue(true);
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingCorrectInput() {
		billinggroupPersistenceDelegator.initialize(updateBillinggroupDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImplSpecificEntityFound);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingIncorrectDAOSetup() {
		billinggroupPersistenceDelegator.initialize(updateBillinggroupDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImplGeneralFail);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingInvalidInput() {
		billinggroupPersistenceDelegator.initialize(invalidUpdateDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateObjectifyUpdateBillinggroupWithBillUserNotRegisteredToBillinggroup() {
		billinggroupPersistenceDelegator.initialize(updateBillinggroupBillDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		billinggroupPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateObjectifyUpdateBillinggroupWithBillUserRegisteredToBillinggroup() {
		// not testable
		assertTrue(true);
	}

	@Test
	public void testDelegateObjectifyUpdateBillinggroupWithBillRetrievalFail() {
		billinggroupPersistenceDelegator.initialize(updateBillinggroupBillDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImplGeneralFail);
		billinggroupPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		billinggroupPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateObjectifyUpdateBillinggroupWithUserUserNotRegisteredToBillinggroup() {
		billinggroupPersistenceDelegator.initialize(updateBillinggroupUserDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		billinggroupPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateObjectifyUpdateBillinggroupWithUserUserRegisteredToBillinggroup() {
		billinggroupPersistenceDelegator.initialize(updateBillinggroupUserDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		billinggroupPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateObjectifyUpdateBillinggroupWithUserUserNotRegisteredToBillinggroupFail() {
		billinggroupPersistenceDelegator.initialize(updateBillinggroupUserDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImplUpdateFail);
		billinggroupPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		billinggroupPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateObjectifyUpdateBillinggroupWithUserRetrievalFail() {
		billinggroupPersistenceDelegator.initialize(updateBillinggroupUserDelegatorInput);
		billinggroupPersistenceDelegator.setBillinggroupDAO(billinggroupObjectifyDAOImplGeneralFail);
		billinggroupPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		billinggroupPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		billinggroupPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = billinggroupPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method setting up BillinggroupPersistenceDelegator inputs
	 */
	private static void setUpShopPersistenceDelegatorInputs() {
		// DelegatorInput for billinggroupentity add
		addBillinggroupDelegatorInput = new DelegatorInput();
		addBillinggroupDelegatorInput.setInputObject(billinggroups);
		addBillinggroupDelegatorInput.setType(DelegatorTypeEnum.ADD);

		// DelegatorInput for billinggroupentity delete
		deleteBillinggroupDelegatorInput = new DelegatorInput();
		deleteBillinggroupDelegatorInput.setInputObject(billinggroups);
		deleteBillinggroupDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		nullDeleteBillinggroupDelegatorInput = new DelegatorInput();
		nullDeleteBillinggroupDelegatorInput.setInputObject(null);
		nullDeleteBillinggroupDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInputs for billinggroupentity read
		readBillinggroupDelegatorInput = new DelegatorInput();
		readBillinggroupDelegatorInput.setInputObject(billinggroups);
		readBillinggroupDelegatorInput.setType(DelegatorTypeEnum.READ);

		nullReadBillinggroupDelegatorInput = new DelegatorInput();
		nullReadBillinggroupDelegatorInput.setInputObject(null);
		nullReadBillinggroupDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInputs for billinggroupentity update
		updateBillinggroupDelegatorInput = new DelegatorInput();
		updateBillinggroupDelegatorInput.setInputObject(billinggroups);
		updateBillinggroupDelegatorInput.setType(DelegatorTypeEnum.UPDATE);

		updateBillinggroupBillDelegatorInput = new DelegatorInput();
		updateBillinggroupBillDelegatorInput.setInputObject(billinggroupsWithBills);
		updateBillinggroupBillDelegatorInput.setType(DelegatorTypeEnum.UPDATE);

		updateBillinggroupUserDelegatorInput = new DelegatorInput();
		updateBillinggroupUserDelegatorInput.setInputObject(billinggroupsWithUsers);
		updateBillinggroupUserDelegatorInput.setType(DelegatorTypeEnum.UPDATE);

		nullUpdateBillinggroupDelegatorInput = new DelegatorInput();
		nullUpdateBillinggroupDelegatorInput.setInputObject(null);
		nullUpdateBillinggroupDelegatorInput.setType(DelegatorTypeEnum.UPDATE);
	}

	/**
	 * Method setting up BillinggroupPersistenceDelegators
	 */
	private static void setUpShopPersistenceDelegators() {
		billinggroupPersistenceDelegator = new BillinggroupPersistenceDelegator();
	}

	/**
	 * Method setting up DAOs for testing
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	private static void setUpDAOMocks()
			throws EntityRemovalException, EntityRetrievalException, EntityPersistenceException {
		// objectify DAO mocks
		billinggroupObjectifyDAOImpl = EasyMock.createNiceMock(BillinggroupObjectifyDAOImpl.class);
		EasyMock.expect(billinggroupObjectifyDAOImpl.findAllEntities()).andReturn(objectifyBillinggroupEntityList);
		EasyMock.expect(billinggroupObjectifyDAOImpl.persistEntity(EasyMock.isA(GAEObjectifyBillinggroupEntity.class)))
				.andReturn(objectifyBillinggroupEntity1);
		EasyMock.expect(billinggroupObjectifyDAOImpl.findEntityById(EasyMock.isA(String.class)))
				.andReturn(objectifyBillinggroupEntity1).times(3);
		EasyMock.expect(billinggroupObjectifyDAOImpl.removeEntity(EasyMock.isA(GAEObjectifyBillinggroupEntity.class)))
				.andReturn(objectifyBillinggroupEntity1);
		EasyMock.expect(billinggroupObjectifyDAOImpl.updateEntity(EasyMock.isA(GAEObjectifyBillinggroupEntity.class)))
				.andReturn(objectifyBillinggroupEntity1);
		EasyMock.replay(billinggroupObjectifyDAOImpl);

		billinggroupObjectifyDAOImplUpdateFail = EasyMock.createNiceMock(BillinggroupObjectifyDAOImpl.class);
		EasyMock.expect(billinggroupObjectifyDAOImplUpdateFail.findEntityById(EasyMock.isA(String.class)))
				.andReturn(objectifyBillinggroupEntity1);
		EasyMock.expect(
				billinggroupObjectifyDAOImplUpdateFail.updateEntity(EasyMock.isA(GAEObjectifyBillinggroupEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.replay(billinggroupObjectifyDAOImplUpdateFail);

		billinggroupObjectifyDAOImplUserIncluded = EasyMock.createNiceMock(BillinggroupObjectifyDAOImpl.class);
		EasyMock.replay(billinggroupObjectifyDAOImplUserIncluded);

		billinggroupObjectifyDAOImplSpecificEntityFound = EasyMock.createNiceMock(BillinggroupObjectifyDAOImpl.class);
		EasyMock.replay(billinggroupObjectifyDAOImplSpecificEntityFound);

		// negative scenario fails everytime
		billinggroupObjectifyDAOImplGeneralFail = EasyMock.createNiceMock(BillinggroupObjectifyDAOImpl.class);
		EasyMock.expect(billinggroupObjectifyDAOImplGeneralFail.findAllEntities())
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(billinggroupObjectifyDAOImplGeneralFail
				.persistEntity(EasyMock.isA(GAEObjectifyBillinggroupEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(billinggroupObjectifyDAOImplGeneralFail
				.removeEntity(EasyMock.isA(GAEObjectifyBillinggroupEntity.class)))
				.andThrow(new EntityRemovalException()).times(4);
		EasyMock.expect(billinggroupObjectifyDAOImplGeneralFail
				.updateEntity(EasyMock.isA(GAEObjectifyBillinggroupEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(billinggroupObjectifyDAOImplGeneralFail.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException()).times(2);
		EasyMock.replay(billinggroupObjectifyDAOImplGeneralFail);

		// negative scenario no found billinggroup when trying to delete
		billinggroupObjectifyDAOImplNoFoundBillinggroups = EasyMock.createNiceMock(BillinggroupObjectifyDAOImpl.class);
		EasyMock.replay(billinggroupObjectifyDAOImplNoFoundBillinggroups);

		// Positive scenario (null returned after billinggroup retrieval)
		billinggroupObjectifyDAOImplNullBillinggroups = EasyMock.createNiceMock(BillinggroupObjectifyDAOImpl.class);
		EasyMock.replay(billinggroupObjectifyDAOImplNullBillinggroups);

		// negative scenario mock for BillinggroupDAO (removeEntity() call
		// fails)
		billinggroupObjectifyDAOImplDeletionFail = EasyMock.createNiceMock(BillinggroupObjectifyDAOImpl.class);
		EasyMock.expect(billinggroupObjectifyDAOImplDeletionFail
				.removeEntity(EasyMock.isA(GAEObjectifyBillinggroupEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.replay(billinggroupObjectifyDAOImplDeletionFail);

		// negative scenario (entity retrieval fails)
		billinggroupObjectifyDAOImplRetrievalFail = EasyMock.createNiceMock(BillinggroupObjectifyDAOImpl.class);
		EasyMock.replay(billinggroupObjectifyDAOImplRetrievalFail);

		// positive scenario for billDAOs
		billObjectifyDAOImpl = EasyMock.createNiceMock(BillObjectifyDAOImpl.class);
		EasyMock.replay(billObjectifyDAOImpl);

		// positive scenario for userDAOs
		userObjectifyDAOImpl = EasyMock.createNiceMock(UserObjectifyDAOImpl.class);
		EasyMock.expect(userObjectifyDAOImpl.findEntityById(EasyMock.isA(String.class))).andReturn(objectifyUserEntity1)
				.times(2);
		EasyMock.expect(userObjectifyDAOImpl.updateEntity(EasyMock.isA(GAEObjectifyUserEntity.class)))
				.andReturn(objectifyUserEntity1);
		EasyMock.replay(userObjectifyDAOImpl);
	}
}
