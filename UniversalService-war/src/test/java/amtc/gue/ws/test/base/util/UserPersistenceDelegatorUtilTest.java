package amtc.gue.ws.test.base.util;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.base.util.UserPersistenceDelegatorUtils;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;
import amtc.gue.ws.test.base.UserTest;

/**
 * test class for the UserPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserPersistenceDelegatorUtilTest extends UserTest {
	private static String EXPECTED_NO_FAILURES_USER_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_NO_SUCCESSES_USER_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_NULL_INPUT_USER_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_USER_REMOVAL_MESSAGE_SIMPLE_RESULT;
	private static String EXPECTED_USER_RETRIEVAL_MESSAGE_SIMPLE_RESULT;
	private static String EXPECTED_USER_RETRIEVAL_BY_ID_MESSAGE_RESULT;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicEnvironment();
		setUpExpectedUserPersistStatusMessages();
	}

	@Test
	public void testbuildPersistUsersSuccessStatusMessageWithNoFailures() {
		String message = UserPersistenceDelegatorUtils.buildPersistUserSuccessStatusMessage(JPAUserEntityList,
				JPAUserEntityEmptyList);
		assertEquals(EXPECTED_NO_FAILURES_USER_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testbuildPersistUsersSuccessStatusMessageWithNoSuccesses() {
		String message = UserPersistenceDelegatorUtils.buildPersistUserSuccessStatusMessage(JPAUserEntityEmptyList,
				JPAUserEntityList);
		assertEquals(EXPECTED_NO_SUCCESSES_USER_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testbuildPersistUsersSuccessStatusMessageUsingNullInputs() {
		String message = UserPersistenceDelegatorUtils.buildPersistUserSuccessStatusMessage(null, null);
		assertEquals(EXPECTED_NULL_INPUT_USER_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testBuildRemoveUsersSuccessStatusMessageSimpleList() {
		String message = UserPersistenceDelegatorUtils.buildRemoveUsersSuccessStatusMessage(JPAUserEntityList);
		assertEquals(EXPECTED_USER_REMOVAL_MESSAGE_SIMPLE_RESULT, message);
	}

	@Test
	public void testBuildRetrieveUsersSuccessStatusMessageSimpleList() {
		String message = UserPersistenceDelegatorUtils.buildGetUsersByRoleSuccessStatusMessage(roles,
				JPAUserEntityList);
		assertEquals(EXPECTED_USER_RETRIEVAL_MESSAGE_SIMPLE_RESULT, message);
	}

	@Test
	public void testBuildRetrieveUsersByIdSuccessStatusMessage() {
		String message = UserPersistenceDelegatorUtils.buildGetUsersByIdSuccessStatusMessage(JPAUserEntity1.getKey(),
				JPAUserEntity1);
		assertEquals(EXPECTED_USER_RETRIEVAL_BY_ID_MESSAGE_RESULT, message);
	}

	/**
	 * Setting up status messages for testing
	 */
	private static void setUpExpectedUserPersistStatusMessages() {
		// EXPECTED_NO_FAILURES_USER_PERSISTENCE_MESSAGE_RESULT
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.ADD_USER_SUCCESS_MSG);
		sb.append(" '").append(UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(JPAUserEntityList))
				.append("'. ").append(JPAUserEntityList.size()).append(" users were successfully added.");
		EXPECTED_NO_FAILURES_USER_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NO_SUCCESSES_USER_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ErrorConstants.ADD_USER_SUCCESS_MSG);
		sb.append(" '")
				.append(UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(JPAUserEntityEmptyList))
				.append("'. ").append(JPAUserEntityEmptyList.size()).append(" users were successfully added.")
				.append(System.getProperty("line.seperator")).append(ErrorConstants.ADD_USER_FAILURE_MSG).append(" '")
				.append(UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(JPAUserEntityList))
				.append("'. ").append(JPAUserEntityList.size()).append(" users were not added successfully.");
		EXPECTED_NO_SUCCESSES_USER_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NULL_INPUT_USER_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ErrorConstants.ADD_USER_SUCCESS_MSG).append(" '")
				.append(UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(null)).append("'. ").append(0)
				.append(" users were successfully added.");
		EXPECTED_NULL_INPUT_USER_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_USER_REMOVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(ErrorConstants.DELETE_USER_SUCCESS_MSG).append(" '")
				.append(UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(JPAUserEntityList))
				.append("'. ").append(JPAUserEntityList.size()).append(" Entities were removed.");
		EXPECTED_USER_REMOVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		// EXPECTED_USER_RETRIEVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(ErrorConstants.RETRIEVE_USER_FOR_ROLES_SUCCESS_MSG).append(" '").append(roles.getRoles().toString())
				.append("':").append(" '")
				.append(UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(JPAUserEntityList))
				.append("'. ").append(JPAUserEntityList.size()).append(" Entities were found");
		EXPECTED_USER_RETRIEVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		// EXPECTED_USER_RETRIEVAL_BY_ID_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ErrorConstants.RETRIEVE_USER_BY_ID_SUCCESS_MSG).append(" '").append(JPAUserEntity1.getKey())
				.append("': '").append(UserServiceEntityMapper.mapUserEntityToJSONString(JPAUserEntity1)).append("'.");
		EXPECTED_USER_RETRIEVAL_BY_ID_MESSAGE_RESULT = sb.toString();
	}
}
