package amtc.gue.ws.test.shopping.util;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.shopping.util.BillPersistenceDelegatorUtils;
import amtc.gue.ws.shopping.util.ShoppingServiceErrorConstants;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;
import amtc.gue.ws.test.shopping.ShoppingTest;

/**
 * test class for the BillPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BillPersistenceDelegatorUtilTest extends ShoppingTest {
	private static String EXPECTED_NO_FAILURES_BILL_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_NO_SUCCESSES_BILL_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_NULL_INPUT_BILL_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_BILL_REMOVAL_MESSAGE_SIMPLE_RESULT;
	private static String EXPECTED_BILL_RETRIEVAL_MESSAGE_RESULT;
	private static String EXPECTED_BILL_UPDATE_MESSAGE_RESULT;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicShoppingEnvironment();
		setUpExpectedBillPersistStatusMessages();
	}

	@Test
	public void testbuildPersistBillSuccessStatusMessageWithNoFailures() {
		String message = BillPersistenceDelegatorUtils.buildPersistBillsSuccessStatusMessage(objectifyBillEntityList,
				objectifyBillEntityEmptyList);
		assertEquals(EXPECTED_NO_FAILURES_BILL_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testbuildPersistBillSuccessStatusMessageWithNoSuccesses() {
		String message = BillPersistenceDelegatorUtils
				.buildPersistBillsSuccessStatusMessage(objectifyBillEntityEmptyList, objectifyBillEntityList);
		assertEquals(EXPECTED_NO_SUCCESSES_BILL_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testbuildPersistBillSuccessStatusMessageUsingNullInputs() {
		String message = BillPersistenceDelegatorUtils.buildPersistBillsSuccessStatusMessage(null, null);
		assertEquals(EXPECTED_NULL_INPUT_BILL_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testBuildRemoveBillinggroupsSuccessStatusMessageSimpleList() {
		String message = BillPersistenceDelegatorUtils.buildRemoveBillsSuccessStatusMessage(objectifyBillEntityList);
		assertEquals(EXPECTED_BILL_REMOVAL_MESSAGE_SIMPLE_RESULT, message);
	}

	@Test
	public void testBuildRetrieveBillinggroupByIdSuccessStatusMessage() {
		String message = BillPersistenceDelegatorUtils.buildGetBillsSuccessStatusMessage(objectifyBillEntityList);
		assertEquals(EXPECTED_BILL_RETRIEVAL_MESSAGE_RESULT, message);
	}

	@Test
	public void testBuildUpdateBillinggroupsSuccessStatusMessage() {
		String message = BillPersistenceDelegatorUtils.buildUpdateBillsSuccessStatusMessage(objectifyBillEntityList);
		assertEquals(EXPECTED_BILL_UPDATE_MESSAGE_RESULT, message);
	}

	/**
	 * Setting up status messages for testing
	 */
	private static void setUpExpectedBillPersistStatusMessages() {
		// EXPECTED_NO_FAILURES_BILL_PERSISTENCE_MESSAGE_RESULT
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.ADD_BILL_SUCCESS_MSG);
		sb.append(" '")
				.append(ShoppingServiceEntityMapper.mapBillEntityListToConsolidatedJSONString(objectifyBillEntityList))
				.append("'. ").append(objectifyBillEntityList.size()).append(" bills were successfully added.");
		EXPECTED_NO_FAILURES_BILL_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NO_SUCCESSES_BILL_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.ADD_BILL_SUCCESS_MSG);
		sb.append(" '")
				.append(ShoppingServiceEntityMapper
						.mapBillEntityListToConsolidatedJSONString(objectifyBillEntityEmptyList))
				.append("'. ").append(objectifyBillEntityEmptyList.size()).append(" bills were successfully added.")
				.append(System.getProperty("line.separator")).append(ShoppingServiceErrorConstants.ADD_BILL_FAILURE_MSG)
				.append(" '")
				.append(ShoppingServiceEntityMapper.mapBillEntityListToConsolidatedJSONString(objectifyBillEntityList))
				.append("'. ").append(objectifyBillEntityList.size()).append(" bills were not added successfully.");
		EXPECTED_NO_SUCCESSES_BILL_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NULL_INPUT_BILL_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.ADD_BILL_SUCCESS_MSG).append(" '")
				.append(ShoppingServiceEntityMapper.mapBillEntityListToConsolidatedJSONString(null)).append("'. ")
				.append(0).append(" bills were successfully added.");
		EXPECTED_NULL_INPUT_BILL_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_BILL_REMOVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.DELETE_BILL_SUCCESS_MSG).append(" '")
				.append(ShoppingServiceEntityMapper.mapBillEntityListToConsolidatedJSONString(objectifyBillEntityList))
				.append("'. ").append(objectifyBillEntityList.size()).append(" Entities were removed.");
		EXPECTED_BILL_REMOVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		// EXPECTED_BILLINGGROUP_RETRIEVAL_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.RETRIEVE_BILL_SUCCESS_MSG).append(" : '")
				.append(ShoppingServiceEntityMapper.mapBillEntityListToConsolidatedJSONString(objectifyBillEntityList))
				.append("'.");
		EXPECTED_BILL_RETRIEVAL_MESSAGE_RESULT = sb.toString();

		// EXPECTED_BILL_UPDATE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ShoppingServiceErrorConstants.UPDATE_BILL_SUCCESS_MSG).append(" : '")
				.append(ShoppingServiceEntityMapper.mapBillEntityListToConsolidatedJSONString(objectifyBillEntityList))
				.append("'.");
		EXPECTED_BILL_UPDATE_MESSAGE_RESULT = sb.toString();
	}
}
