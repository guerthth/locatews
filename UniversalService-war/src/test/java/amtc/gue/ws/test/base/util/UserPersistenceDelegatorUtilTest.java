package amtc.gue.ws.test.base.util;

import static org.junit.Assert.assertEquals;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.util.UserPersistenceDelegatorUtils;

/**
 * test class for the UserPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserPersistenceDelegatorUtilTest extends UserServiceUtilTest {
	@Test
	public void testbuildPersistUsersSuccessStatusMessageWithNoFailures() {
		String message = UserPersistenceDelegatorUtils
				.buildPersistUserSuccessStatusMessage(userEntityList,
						emptyUserEntityList);
		assertEquals(EXPECTED_NO_FAILURES_USER_PERSISTENCE_MESSAGE_RESULT,
				message);
	}

	@Test
	public void testbuildPersistUsersSuccessStatusMessageWithNoSuccesses() {
		String message = UserPersistenceDelegatorUtils
				.buildPersistUserSuccessStatusMessage(emptyUserEntityList,
						userEntityList);
		assertEquals(EXPECTED_NO_SUCCESSES_USER_PERSISTENCE_MESSAGE_RESULT,
				message);
	}

	@Test
	public void testbuildPersistUsersSuccessStatusMessageUsingNullInputs() {
		String message = UserPersistenceDelegatorUtils
				.buildPersistUserSuccessStatusMessage(null, null);
		assertEquals(EXPECTED_NULL_INPUT_USER_PERSISTENCE_MESSAGE_RESULT,
				message);
	}

	@Test
	public void testBuildRemoveUsersSuccessStatusMessageSimpleList() {
		String message = UserPersistenceDelegatorUtils
				.buildRemoveUsersSuccessStatusMessage(userEntityList);
		assertEquals(EXPECTED_USER_REMOVAL_MESSAGE_SIMPLE_RESULT, message);
	}

	@Test
	public void testBuildRetrieveUsersSuccessStatusMessageSimpleList() {
		String message = UserPersistenceDelegatorUtils
				.buildGetUsersByRoleSuccessStatusMessage(existingRoles,
						userEntityList);
		assertEquals(EXPECTED_USER_RETRIEVAL_MESSAGE_SIMPLE_RESULT, message);
	}

	@Test
	public void testBuildRetrieveUsersByIdSuccessStatusMessage() {
		String message = UserPersistenceDelegatorUtils
				.buildGetUsersByIdSuccessStatusMessage(userEntity1.getKey(),
						userEntity1);
		assertEquals(EXPECTED_USER_RETRIEVAL_BY_ID_MESSAGE_RESULT, message);
	}
}
