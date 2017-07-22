package amtc.gue.ws.test.shopping.util;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.shopping.util.ShopPersistenceDelegatorUtils;
import amtc.gue.ws.shopping.util.ShoppingServiceErrorConstants;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;
import amtc.gue.ws.test.shopping.ShoppingTest;

/**
 * test class for the ShopPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopPersistenceDelegatorUtilTest extends ShoppingTest {
	private static String EXPECTED_NO_FAILURES_SHOP_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_NO_SUCCESSES_SHOP_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_NULL_INPUT_SHOP_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_SHOP_REMOVAL_MESSAGE_SIMPLE_RESULT;
	private static String EXPECTED_SHOP_RETRIEVAL_MESSAGE_RESULT;
	private static String EXPECTED_SHOP_UPDATE_MESSAGE_RESULT;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicShoppingEnvironment();
		setUpExpectedUserPersistStatusMessages();
	}

	@Test
	public void testbuildPersistShopsSuccessStatusMessageWithNoFailures() {
		String message = ShopPersistenceDelegatorUtils.buildPersistShopsSuccessStatusMessage(objectifyShopEntityList,
				objectifyShopEntityEmptyList);
		assertEquals(EXPECTED_NO_FAILURES_SHOP_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testbuildPersistShopsSuccessStatusMessageWithNoSuccesses() {
		String message = ShopPersistenceDelegatorUtils
				.buildPersistShopsSuccessStatusMessage(objectifyShopEntityEmptyList, objectifyShopEntityList);
		assertEquals(EXPECTED_NO_SUCCESSES_SHOP_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testbuildPersistShopsSuccessStatusMessageUsingNullInputs() {
		String message = ShopPersistenceDelegatorUtils.buildPersistShopsSuccessStatusMessage(null, null);
		assertEquals(EXPECTED_NULL_INPUT_SHOP_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testBuildRemoveShopSuccessStatusMessageSimpleList() {
		String message = ShopPersistenceDelegatorUtils.buildRemoveShopsSuccessStatusMessage(objectifyShopEntityList);
		assertEquals(EXPECTED_SHOP_REMOVAL_MESSAGE_SIMPLE_RESULT, message);
	}

	@Test
	public void testBuildRetrieveShopsByIdSuccessStatusMessage() {
		String message = ShopPersistenceDelegatorUtils.buildGetShopsSuccessStatusMessage(objectifyShopEntityList);
		assertEquals(EXPECTED_SHOP_RETRIEVAL_MESSAGE_RESULT, message);
	}

	@Test
	public void testBuildUpdateShopsSuccessStatusMessage() {
		String message = ShopPersistenceDelegatorUtils.buildUpdateShopsSuccessStatusMessage(objectifyShopEntityList);
		assertEquals(EXPECTED_SHOP_UPDATE_MESSAGE_RESULT, message);
	}

	/**
	 * Setting up status messages for testing
	 */
	private static void setUpExpectedUserPersistStatusMessages() {
		// EXPECTED_NO_FAILURES_SHOP_PERSISTENCE_MESSAGE_RESULT
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.ADD_SHOP_SUCCESS_MSG);
		sb.append(" '")
				.append(ShoppingServiceEntityMapper.mapShopEntityListToConsolidatedJSONString(objectifyShopEntityList))
				.append("'. ").append(objectifyShopEntityList.size()).append(" shops were successfully added.");
		EXPECTED_NO_FAILURES_SHOP_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NO_SUCCESSES_SHOP_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.ADD_SHOP_SUCCESS_MSG);
		sb.append(" '")
				.append(ShoppingServiceEntityMapper
						.mapShopEntityListToConsolidatedJSONString(objectifyShopEntityEmptyList))
				.append("'. ").append(objectifyShopEntityEmptyList.size()).append(" shops were successfully added.")
				.append(System.getProperty("line.separator")).append(ShoppingServiceErrorConstants.ADD_SHOP_FAILURE_MSG)
				.append(" '")
				.append(ShoppingServiceEntityMapper.mapShopEntityListToConsolidatedJSONString(objectifyShopEntityList))
				.append("'. ").append(objectifyShopEntityList.size()).append(" shops were not added successfully.");
		EXPECTED_NO_SUCCESSES_SHOP_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NULL_INPUT_SHOP_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.ADD_SHOP_SUCCESS_MSG).append(" '")
				.append(ShoppingServiceEntityMapper.mapShopEntityListToConsolidatedJSONString(null)).append("'. ")
				.append(0).append(" shops were successfully added.");
		EXPECTED_NULL_INPUT_SHOP_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_SHOP_REMOVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.DELETE_SHOP_SUCCESS_MSG).append(" '")
				.append(ShoppingServiceEntityMapper.mapShopEntityListToConsolidatedJSONString(objectifyShopEntityList))
				.append("'. ").append(objectifyShopEntityList.size()).append(" Entities were removed.");
		EXPECTED_SHOP_REMOVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		// EXPECTED_SHOP_RETRIEVAL_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.RETRIEVE_SHOP_SUCCESS_MSG).append(" : '")
				.append(ShoppingServiceEntityMapper.mapShopEntityListToConsolidatedJSONString(objectifyShopEntityList))
				.append("'.");
		EXPECTED_SHOP_RETRIEVAL_MESSAGE_RESULT = sb.toString();

		// EXPECTED_SHOP_UPDATE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.UPDATE_SHOP_SUCCESS_MSG).append(" : '")
				.append(ShoppingServiceEntityMapper.mapShopEntityListToConsolidatedJSONString(objectifyShopEntityList))
				.append("'.");
		EXPECTED_SHOP_UPDATE_MESSAGE_RESULT = sb.toString();
	}
}
