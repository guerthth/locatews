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

import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.shopping.delegate.persist.ShopPersistenceDelegator;
import amtc.gue.ws.shopping.persistence.dao.ShopDAO;
import amtc.gue.ws.shopping.persistence.dao.objectify.ShopObjectifyDAOImpl;
import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyShopEntity;
import amtc.gue.ws.shopping.util.ShoppingServiceErrorConstants;
import amtc.gue.ws.test.base.delegate.persist.IObjectifyPersistenceDelegatorTest;
import amtc.gue.ws.test.shopping.ShoppingTest;

/**
 * Testclass for the ShopPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopPersistenceDelegatorTest extends ShoppingTest implements IObjectifyPersistenceDelegatorTest {
	private static ShopPersistenceDelegator shopPersistenceDelegator;

	private static DelegatorInput addShopDelegatorInput;
	private static DelegatorInput deleteShopDelegatorInput;
	private static DelegatorInput nullDeleteShopDelegatorInput;
	private static DelegatorInput readShopDelegatorInput;
	private static DelegatorInput nullReadShopDelegatorInput;
	private static DelegatorInput updateShopDelegatorInput;
	private static DelegatorInput nullUpdateShopDelegatorInput;

	private static ShopDAO<GAEShopEntity, GAEObjectifyShopEntity, String> shopObjectifyDAOImpl;
	private static ShopDAO<GAEShopEntity, GAEObjectifyShopEntity, String> shopObjectifyDAOImplNoFoundShops;
	private static ShopDAO<GAEShopEntity, GAEObjectifyShopEntity, String> shopObjectifyDAOImplNullShops;
	private static ShopDAO<GAEShopEntity, GAEObjectifyShopEntity, String> shopObjectifyDAOImplGeneralFail;
	private static ShopDAO<GAEShopEntity, GAEObjectifyShopEntity, String> shopObjectifyDAOImplDeletionFail;
	private static ShopDAO<GAEShopEntity, GAEObjectifyShopEntity, String> shopObjectifyDAOImplSpecificEntityFound;
	private static ShopDAO<GAEShopEntity, GAEObjectifyShopEntity, String> shopObjectifyDAOImplRetrievalFail;

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
		EasyMock.verify(shopObjectifyDAOImpl);
		EasyMock.verify(shopObjectifyDAOImplNoFoundShops);
		EasyMock.verify(shopObjectifyDAOImplNullShops);
		EasyMock.verify(shopObjectifyDAOImplGeneralFail);
		EasyMock.verify(shopObjectifyDAOImplDeletionFail);
		EasyMock.verify(shopObjectifyDAOImplSpecificEntityFound);
		EasyMock.verify(shopObjectifyDAOImplRetrievalFail);
	}

	@Override
	@Test
	public void testDelegateUsingNullInput() {
		shopPersistenceDelegator.initialize(null);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateUsingUnrecognizedInputType() {
		shopPersistenceDelegator.initialize(unrecognizedDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingCorrectInput() {
		shopPersistenceDelegator.initialize(addShopDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImpl);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.ADD_SHOP_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(ShoppingServiceErrorConstants.ADD_SHOP_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingIncorrectDAOSetup() {
		shopPersistenceDelegator.initialize(addShopDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImplGeneralFail);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.ADD_SHOP_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingInvalidInput() {
		shopPersistenceDelegator.initialize(invalidAddDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImpl);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingCorrectIdInput() {
		shopPersistenceDelegator.initialize(deleteShopDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImpl);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_SHOP_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNonExistingObjects() {
		shopPersistenceDelegator.initialize(deleteShopDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImplGeneralFail);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_SHOP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_SHOP_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNullObjects() {
		shopPersistenceDelegator.initialize(deleteShopDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImplGeneralFail);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_SHOP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_SHOP_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingIncorrectDAOSetup() {
		shopPersistenceDelegator.initialize(deleteShopDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImplGeneralFail);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_SHOP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_SHOP_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteDeletionFail() {
		shopPersistenceDelegator.initialize(deleteShopDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImplDeletionFail);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_SHOP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_SHOP_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteRetrievalFail() {
		shopPersistenceDelegator.initialize(deleteShopDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImplGeneralFail);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.DELETE_SHOP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.DELETE_SHOP_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingInvalidInput() {
		shopPersistenceDelegator.initialize(invalidDeleteDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImpl);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingCorrectInput() {
		shopPersistenceDelegator.initialize(readShopDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImpl);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.RETRIEVE_SHOP_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingIncorrectDAOSetup() {
		shopPersistenceDelegator.initialize(readShopDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImplGeneralFail);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.RETRIEVE_SHOP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.RETRIEVE_SHOP_FAILURE_MSG, delegatorOutput.getStatusMessage());
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
		shopPersistenceDelegator.initialize(updateShopDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImplSpecificEntityFound);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.UPDATE_SHOP_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingIncorrectDAOSetup() {
		shopPersistenceDelegator.initialize(updateShopDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImplGeneralFail);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ShoppingServiceErrorConstants.UPDATE_SHOP_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ShoppingServiceErrorConstants.UPDATE_SHOP_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingInvalidInput() {
		shopPersistenceDelegator.initialize(invalidUpdateDelegatorInput);
		shopPersistenceDelegator.setShopDAO(shopObjectifyDAOImpl);
		shopPersistenceDelegator.setShoppingEntityMapper(objectifyShopEntityMapper);
		IDelegatorOutput delegatorOutput = shopPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method setting up ShopPersistenceDelegator inputs
	 */
	private static void setUpShopPersistenceDelegatorInputs() {
		// DelegatorInput for shopentity add
		addShopDelegatorInput = new DelegatorInput();
		addShopDelegatorInput.setInputObject(shops);
		addShopDelegatorInput.setType(DelegatorTypeEnum.ADD);

		// DelegatorInput for shopentity delete
		deleteShopDelegatorInput = new DelegatorInput();
		deleteShopDelegatorInput.setInputObject(shops);
		deleteShopDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		nullDeleteShopDelegatorInput = new DelegatorInput();
		nullDeleteShopDelegatorInput.setInputObject(null);
		nullDeleteShopDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInputs for shopentity read
		readShopDelegatorInput = new DelegatorInput();
		readShopDelegatorInput.setInputObject(shops);
		readShopDelegatorInput.setType(DelegatorTypeEnum.READ);

		nullReadShopDelegatorInput = new DelegatorInput();
		nullReadShopDelegatorInput.setInputObject(null);
		nullReadShopDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInputs for shopentity update
		updateShopDelegatorInput = new DelegatorInput();
		updateShopDelegatorInput.setInputObject(shops);
		updateShopDelegatorInput.setType(DelegatorTypeEnum.UPDATE);

		nullUpdateShopDelegatorInput = new DelegatorInput();
		nullUpdateShopDelegatorInput.setInputObject(null);
		nullUpdateShopDelegatorInput.setType(DelegatorTypeEnum.UPDATE);
	}

	/**
	 * Method setting up ShopPersistenceDelegators
	 */
	private static void setUpShopPersistenceDelegators() {
		shopPersistenceDelegator = new ShopPersistenceDelegator();
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
			throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		// objectify DAO mocks
		shopObjectifyDAOImpl = EasyMock.createNiceMock(ShopObjectifyDAOImpl.class);
		EasyMock.expect(shopObjectifyDAOImpl.findAllEntities()).andReturn(objectifyShopEntityList);
		EasyMock.expect(shopObjectifyDAOImpl.persistEntity(EasyMock.isA(GAEObjectifyShopEntity.class)))
				.andReturn(objectifyShopEntity3);
		EasyMock.expect(shopObjectifyDAOImpl.removeEntity(EasyMock.isA(GAEObjectifyShopEntity.class)))
				.andReturn(objectifyShopEntity3);
		EasyMock.replay(shopObjectifyDAOImpl);

		shopObjectifyDAOImplSpecificEntityFound = EasyMock.createNiceMock(ShopObjectifyDAOImpl.class);
		EasyMock.replay(shopObjectifyDAOImplSpecificEntityFound);

		// negative scenario fails everytime
		shopObjectifyDAOImplGeneralFail = EasyMock.createNiceMock(ShopObjectifyDAOImpl.class);
		EasyMock.expect(shopObjectifyDAOImplGeneralFail.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.expect(shopObjectifyDAOImplGeneralFail.persistEntity(EasyMock.isA(GAEObjectifyShopEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(shopObjectifyDAOImplGeneralFail.removeEntity(EasyMock.isA(GAEObjectifyShopEntity.class)))
				.andThrow(new EntityRemovalException()).times(4);
		EasyMock.expect(shopObjectifyDAOImplGeneralFail.updateEntity(EasyMock.isA(GAEObjectifyShopEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.replay(shopObjectifyDAOImplGeneralFail);

		// negative scenario no found shop when trying to delete
		shopObjectifyDAOImplNoFoundShops = EasyMock.createNiceMock(ShopObjectifyDAOImpl.class);
		EasyMock.replay(shopObjectifyDAOImplNoFoundShops);

		// Positive scenario (null returned after shop retrieval)
		shopObjectifyDAOImplNullShops = EasyMock.createNiceMock(ShopObjectifyDAOImpl.class);
		EasyMock.replay(shopObjectifyDAOImplNullShops);

		// negative scenario mock for ShopDAO (removeEntity() call fails)
		shopObjectifyDAOImplDeletionFail = EasyMock.createNiceMock(ShopObjectifyDAOImpl.class);
		EasyMock.expect(shopObjectifyDAOImplDeletionFail.removeEntity(EasyMock.isA(GAEObjectifyShopEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.replay(shopObjectifyDAOImplDeletionFail);

		// negative scenario (entity retrieval fails)
		shopObjectifyDAOImplRetrievalFail = EasyMock.createNiceMock(ShopObjectifyDAOImpl.class);
		EasyMock.replay(shopObjectifyDAOImplRetrievalFail);
	}
}
