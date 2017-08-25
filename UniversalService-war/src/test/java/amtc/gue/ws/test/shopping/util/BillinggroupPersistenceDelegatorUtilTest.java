package amtc.gue.ws.test.shopping.util;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.shopping.util.BillinggroupPersistenceDelegatorUtils;
import amtc.gue.ws.shopping.util.ShoppingServiceErrorConstants;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;
import amtc.gue.ws.test.shopping.ShoppingTest;

/**
 * test class for the BillinggroupPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BillinggroupPersistenceDelegatorUtilTest extends ShoppingTest {
	private static String EXPECTED_NO_FAILURES_BILLINGGROUP_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_NO_SUCCESSES_BILLINGGROUP_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_NULL_INPUT_BILLINGGROUP_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_BILLINGGROUP_REMOVAL_MESSAGE_SIMPLE_RESULT;
	private static String EXPECTED_BILLINGGROUP_RETRIEVAL_MESSAGE_RESULT;
	private static String EXPECTED_BILLINGGROUP_UPDATE_MESSAGE_RESULT;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicShoppingEnvironment();
		setUpExpectedBillinggroupPersistStatusMessages();
	}

	@Test
	public void testbuildPersistBillinggroupsSuccessStatusMessageWithNoFailures() {
		String message = BillinggroupPersistenceDelegatorUtils.buildPersistBillinggroupSuccessStatusMessage(
				objectifyBillinggroupEntityList, objectifyBillinggroupEntityEmptyList);
		assertEquals(EXPECTED_NO_FAILURES_BILLINGGROUP_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testbuildPersistBillinggroupsSuccessStatusMessageWithNoSuccesses() {
		String message = BillinggroupPersistenceDelegatorUtils.buildPersistBillinggroupSuccessStatusMessage(
				objectifyBillinggroupEntityEmptyList, objectifyBillinggroupEntityList);
		assertEquals(EXPECTED_NO_SUCCESSES_BILLINGGROUP_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testbuildPersistBillinggroupsSuccessStatusMessageUsingNullInputs() {
		String message = BillinggroupPersistenceDelegatorUtils.buildPersistBillinggroupSuccessStatusMessage(null, null);
		assertEquals(EXPECTED_NULL_INPUT_BILLINGGROUP_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testBuildRemoveBillinggroupsSuccessStatusMessageSimpleList() {
		String message = BillinggroupPersistenceDelegatorUtils
				.buildRemoveBillinggroupsSuccessStatusMessage(objectifyBillinggroupEntityList);
		assertEquals(EXPECTED_BILLINGGROUP_REMOVAL_MESSAGE_SIMPLE_RESULT, message);
	}

	@Test
	public void testBuildRetrieveBillinggroupByIdSuccessStatusMessage() {
		String message = BillinggroupPersistenceDelegatorUtils
				.buildGetBillinggroupsSuccessStatusMessage(objectifyBillinggroupEntityList);
		assertEquals(EXPECTED_BILLINGGROUP_RETRIEVAL_MESSAGE_RESULT, message);
	}

	@Test
	public void testBuildUpdateBillinggroupsSuccessStatusMessage() {
		String message = BillinggroupPersistenceDelegatorUtils
				.buildUpdateBillinggroupsSuccessStatusMessage(objectifyBillinggroupEntityList);
		assertEquals(EXPECTED_BILLINGGROUP_UPDATE_MESSAGE_RESULT, message);
	}

	@Test
	public void testBuildGetBillinggroupsByIdSuccessStatusMessage() {
		String message = BillinggroupPersistenceDelegatorUtils
				.buildGetBillinggroupsByIdSuccessStatusMessage(BILLINGGROUPID, objectifyBillinggroupEntity1);
		assertNotNull(message);
	}

	/**
	 * Setting up status messages for testing
	 */
	private static void setUpExpectedBillinggroupPersistStatusMessages() {
		// EXPECTED_NO_FAILURES_BILLINGGROUP_PERSISTENCE_MESSAGE_RESULT
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.ADD_BILLINGGROUP_SUCCESS_MSG);
		sb.append(" '")
				.append(ShoppingServiceEntityMapper
						.mapBillinggroupEntityListToConsolidatedJSONString(objectifyBillinggroupEntityList))
				.append("'. ").append(objectifyBillinggroupEntityList.size())
				.append(" billinggroups were successfully added.");
		EXPECTED_NO_FAILURES_BILLINGGROUP_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NO_SUCCESSES_BILLINGGROUP_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.ADD_BILLINGGROUP_SUCCESS_MSG);
		sb.append(" '")
				.append(ShoppingServiceEntityMapper
						.mapBillinggroupEntityListToConsolidatedJSONString(objectifyBillinggroupEntityEmptyList))
				.append("'. ").append(objectifyBillinggroupEntityEmptyList.size())
				.append(" billinggroups were successfully added.").append(System.getProperty("line.separator"))
				.append(ShoppingServiceErrorConstants.ADD_BILLINGGROUP_FAILURE_MSG).append(" '")
				.append(ShoppingServiceEntityMapper
						.mapBillinggroupEntityListToConsolidatedJSONString(objectifyBillinggroupEntityList))
				.append("'. ").append(objectifyBillinggroupEntityList.size())
				.append(" billinggroups were not added successfully.");
		EXPECTED_NO_SUCCESSES_BILLINGGROUP_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NULL_INPUT_BILLINGGROUP_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.ADD_BILLINGGROUP_SUCCESS_MSG).append(" '")
				.append(ShoppingServiceEntityMapper.mapBillinggroupEntityListToConsolidatedJSONString(null))
				.append("'. ").append(0).append(" billinggroups were successfully added.");
		EXPECTED_NULL_INPUT_BILLINGGROUP_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_BILLINGGROUP_REMOVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_SUCCESS_MSG).append(" '")
				.append(ShoppingServiceEntityMapper
						.mapBillinggroupEntityListToConsolidatedJSONString(objectifyBillinggroupEntityList))
				.append("'. ").append(objectifyBillinggroupEntityList.size()).append(" Entities were removed.");
		EXPECTED_BILLINGGROUP_REMOVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		// EXPECTED_BILLINGGROUP_RETRIEVAL_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_SUCCESS_MSG).append(" : '")
				.append(ShoppingServiceEntityMapper
						.mapBillinggroupEntityListToConsolidatedJSONString(objectifyBillinggroupEntityList))
				.append("'.");
		EXPECTED_BILLINGGROUP_RETRIEVAL_MESSAGE_RESULT = sb.toString();

		// EXPECTED_BILLINGGROUP_UPDATE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_SUCCESS_MSG).append(" : '")
				.append(ShoppingServiceEntityMapper
						.mapBillinggroupEntityListToConsolidatedJSONString(objectifyBillinggroupEntityList))
				.append("'.");
		EXPECTED_BILLINGGROUP_UPDATE_MESSAGE_RESULT = sb.toString();
	}
}
