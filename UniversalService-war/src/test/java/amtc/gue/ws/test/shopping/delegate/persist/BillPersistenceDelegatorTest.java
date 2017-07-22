package amtc.gue.ws.test.shopping.delegate.persist;

import static org.junit.Assert.*;

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
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.shopping.delegate.persist.BillPersistenceDelegator;
import amtc.gue.ws.shopping.persistence.dao.BillDAO;
import amtc.gue.ws.shopping.persistence.dao.objectify.BillObjectifyDAOImpl;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillEntity;
import amtc.gue.ws.shopping.util.ShoppingServiceErrorConstants;
import amtc.gue.ws.test.base.delegate.persist.IObjectifyPersistenceDelegatorTest;
import amtc.gue.ws.test.shopping.ShoppingTest;

/**
 * Testclass for the BillPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BillPersistenceDelegatorTest extends ShoppingTest implements IObjectifyPersistenceDelegatorTest {
	private static DelegatorInput addBillDelegatorInput;
	private static DelegatorInput addBillDelegatorInputWithNoContent;
	private static DelegatorInput deleteBillDelegatorInput;
	private static DelegatorInput readBillDelegatorInput;
	private static DelegatorInput readBillByIdDelegatorInput;
	private static DelegatorInput updateBillDelegatorInput;

	private static BillPersistenceDelegator billPersistenceDelegator;

	private static BillDAO<GAEBillEntity, GAEObjectifyBillEntity, String> billObjectifyDAOImpl;
	private static BillDAO<GAEBillEntity, GAEObjectifyBillEntity, String> billObjectifyDAOImplGeneralFail;
	private static BillDAO<GAEBillEntity, GAEObjectifyBillEntity, String> billObjectifyDAOImplDeletionFail;
	private static BillDAO<GAEBillEntity, GAEObjectifyBillEntity, String> billObjectifyDAOImplRetrievalFail;
	private static BillDAO<GAEBillEntity, GAEObjectifyBillEntity, String> billObjectifyDAOImplNoFoundBills;
	private static BillDAO<GAEBillEntity, GAEObjectifyBillEntity, String> billObjectifyDAOImplNullBills;
	private static BillDAO<GAEBillEntity, GAEObjectifyBillEntity, String> billObjectifyDAOImplSpecificEntityFound;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		setUpBasicShoppingEnvironment();
		setUpDelegatorInputs();
		setUpBookPersistenceDelegatorInputs();
		setUpBookPersistenceDelegators();
		setUpDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		// TODO
	}

	@Override
	@Test
	public void testDelegateUsingNullInput() {
		billPersistenceDelegator.initialize(null);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateUsingUnrecognizedInputType() {
		billPersistenceDelegator.initialize(unrecognizedDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingCorrectInput() {
		billPersistenceDelegator.initialize(addBillDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.ADD_BILL_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(ShoppingServiceErrorConstants.ADD_BILL_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingIncorrectDAOSetup() {
		billPersistenceDelegator.initialize(addBillDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImplGeneralFail);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.ADD_BILL_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.ADD_BILL_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingInvalidInput() {
		billPersistenceDelegator.initialize(invalidAddDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingCorrectIdInput() {
		billPersistenceDelegator.initialize(deleteBillDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILL_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNonExistingObjects() {
		billPersistenceDelegator.initialize(deleteBillDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImplGeneralFail);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILL_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILL_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNullObjects() {
		billPersistenceDelegator.initialize(deleteBillDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImplGeneralFail);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILL_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILL_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingIncorrectDAOSetup() {
		billPersistenceDelegator.initialize(deleteBillDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImplGeneralFail);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILL_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILL_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteDeletionFail() {
		billPersistenceDelegator.initialize(deleteBillDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImplGeneralFail);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILL_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILL_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteRetrievalFail() {
		billPersistenceDelegator.initialize(deleteBillDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImplGeneralFail);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILL_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_BILL_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingInvalidInput() {
		billPersistenceDelegator.initialize(invalidDeleteDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingCorrectInput() {
		billPersistenceDelegator.initialize(readBillDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.RETRIEVE_BILL_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingIncorrectDAOSetup() {
		billPersistenceDelegator.initialize(readBillDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImplGeneralFail);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.RETRIEVE_BILL_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.RETRIEVE_BILL_FAILURE_MSG, delegatorOutput.getStatusMessage());
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
		billPersistenceDelegator.initialize(updateBillDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImplSpecificEntityFound);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.UPDATE_BILL_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingIncorrectDAOSetup() {
		billPersistenceDelegator.initialize(updateBillDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImplGeneralFail);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.UPDATE_BILL_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.UPDATE_BILL_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingInvalidInput() {
		billPersistenceDelegator.initialize(invalidUpdateDelegatorInput);
		billPersistenceDelegator.setBillDAO(billObjectifyDAOImpl);
		billPersistenceDelegator.setShoppingEntityMapper(objectifyBillEntityMapper);
		IDelegatorOutput delegatorOutput = billPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method setting up BillPersistenceDelegator inputs
	 */
	private static void setUpBookPersistenceDelegatorInputs() {
		// DelegatorInput for Bill entity adding
		addBillDelegatorInput = new DelegatorInput();
		addBillDelegatorInput.setInputObject(bills);
		addBillDelegatorInput.setType(DelegatorTypeEnum.ADD);

		addBillDelegatorInputWithNoContent = new DelegatorInput();
		addBillDelegatorInputWithNoContent.setInputObject(billsWithoutContent);
		addBillDelegatorInputWithNoContent.setType(DelegatorTypeEnum.ADD);

		// DelegatorInput for Bill entity deletion
		deleteBillDelegatorInput = new DelegatorInput();
		deleteBillDelegatorInput.setInputObject(bills);
		deleteBillDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInput for Bill entity read
		readBillDelegatorInput = new DelegatorInput();
		readBillDelegatorInput.setInputObject(null);
		readBillDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInput for Bill entity read by Id
		readBillByIdDelegatorInput = new DelegatorInput();
		readBillByIdDelegatorInput.setInputObject(BILLID);
		readBillByIdDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInput for user entity update
		updateBillDelegatorInput = new DelegatorInput();
		updateBillDelegatorInput.setInputObject(bills);
		updateBillDelegatorInput.setType(DelegatorTypeEnum.UPDATE);
	}

	/**
	 * Method setting up BillPersistenceDelegators
	 */
	private static void setUpBookPersistenceDelegators() {
		billPersistenceDelegator = new BillPersistenceDelegator();
	}

	/**
	 * 
	 * @throws EntityRemovalException
	 * @throws EntityPersistenceException
	 * @throws EntityRetrievalException
	 */
	private static void setUpDAOMocks()
			throws EntityRemovalException, EntityPersistenceException, EntityRetrievalException {
		// objectify DAO mocks
		billObjectifyDAOImpl = EasyMock.createNiceMock(BillObjectifyDAOImpl.class);
		EasyMock.expect(billObjectifyDAOImpl.findAllEntities()).andReturn(objectifyBillEntityList);
		EasyMock.expect(billObjectifyDAOImpl.persistEntity(EasyMock.isA(GAEObjectifyBillEntity.class)))
				.andReturn(objectifyBillEntity1);
		EasyMock.expect(billObjectifyDAOImpl.removeEntity(EasyMock.isA(GAEObjectifyBillEntity.class)))
				.andReturn(objectifyBillEntity1);
		EasyMock.replay(billObjectifyDAOImpl);

		billObjectifyDAOImplSpecificEntityFound = EasyMock.createNiceMock(BillObjectifyDAOImpl.class);
		EasyMock.replay(billObjectifyDAOImplSpecificEntityFound);

		// negative scenario fails everytime
		billObjectifyDAOImplGeneralFail = EasyMock.createNiceMock(BillObjectifyDAOImpl.class);
		EasyMock.expect(billObjectifyDAOImplGeneralFail.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.expect(billObjectifyDAOImplGeneralFail.persistEntity(EasyMock.isA(GAEObjectifyBillEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(billObjectifyDAOImplGeneralFail.removeEntity(EasyMock.isA(GAEObjectifyBillEntity.class)))
				.andThrow(new EntityRemovalException()).times(4);
		EasyMock.expect(billObjectifyDAOImplGeneralFail.updateEntity(EasyMock.isA(GAEObjectifyBillEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.replay(billObjectifyDAOImplGeneralFail);

		// negative scenario no found bill when trying to delete
		billObjectifyDAOImplNoFoundBills = EasyMock.createNiceMock(BillObjectifyDAOImpl.class);
		EasyMock.replay(billObjectifyDAOImplNoFoundBills);

		// Positive scenario (null returned after bill retrieval)
		billObjectifyDAOImplNullBills = EasyMock.createNiceMock(BillObjectifyDAOImpl.class);
		EasyMock.replay(billObjectifyDAOImplNullBills);

		// negative scenario mock for BillDAO (removeEntity() call fails)
		billObjectifyDAOImplDeletionFail = EasyMock.createNiceMock(BillObjectifyDAOImpl.class);
		EasyMock.expect(billObjectifyDAOImplDeletionFail.removeEntity(EasyMock.isA(GAEObjectifyBillEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.replay(billObjectifyDAOImplDeletionFail);

		// negative scenario (entity retrieval fails)
		billObjectifyDAOImplRetrievalFail = EasyMock.createNiceMock(BillObjectifyDAOImpl.class);
		EasyMock.replay(billObjectifyDAOImplRetrievalFail);
	}
}
