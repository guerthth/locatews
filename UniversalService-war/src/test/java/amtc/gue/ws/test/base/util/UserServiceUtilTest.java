package amtc.gue.ws.test.base.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.persistence.model.GAEJPARoleEntity;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.base.util.UserServiceEntityMapper;

/**
 * Class holding helper object setup for the UserService utility classes
 * 
 * @author Thomas
 *
 */
public class UserServiceUtilTest {
	protected static final String TEST_USER_ENTITY_KEY = "testUserEntityKey";
	protected static final String TEST_USER_PASSWORD = "testUserPassword";
	protected static final String TEST_USER_USERNAME = "testUserUsername";

	protected static final String NULL_USER_ENTITY_JSON = "{}";
	protected static final String NULL_USER_ENTITY_LIST_JSON = "[]";
	protected static String USER_ENTITY_JSON;
	protected static String COMPLEX_USER_ENTITY_JSON;
	protected static String COMPLEX_USER_ENTITY_JSON_V2;
	protected static String USER_ENTITY_LIST_JSON;

	protected String EXPECTED_NO_FAILURES_USER_PERSISTENCE_MESSAGE_RESULT;
	protected String EXPECTED_NO_SUCCESSES_USER_PERSISTENCE_MESSAGE_RESULT;
	protected String EXPECTED_NULL_INPUT_USER_PERSISTENCE_MESSAGE_RESULT;
	protected String EXPECTED_USER_REMOVAL_MESSAGE_SIMPLE_RESULT;
	protected String EXPECTED_USER_RETRIEVAL_MESSAGE_SIMPLE_RESULT;
	protected String EXPECTED_USER_RETRIEVAL_BY_ID_MESSAGE_RESULT;

	protected GAEJPAUserEntity userEntity1;
	protected GAEJPAUserEntity userEntity2;
	protected GAEJPAUserEntity userEntity3;
	protected GAEJPAUserEntity userEntity4;

	protected List<GAEJPAUserEntity> userEntityList;
	protected List<GAEJPAUserEntity> userEntityListWithRoles;
	protected List<GAEJPAUserEntity> emptyUserEntityList;

	protected User user1;
	protected User user2;

	protected Users simpleUsers;
	protected Users emptyUsers;

	protected IDelegatorOutput userDelegatorOutput;
	protected IDelegatorOutput unrecognizedUserDelegatorOutput;

	protected static final String ROLE_A = "roleA";
	protected static final String ROLE_B = "roleB";
	protected static final String ROLE_C = "roleC";
	protected static final String DESCRIPTION = "description";

	protected Roles existingRoles;
	protected Roles nonExistingRoles;
	protected GAEJPARoleEntity roleEntity1;
	protected GAEJPARoleEntity roleEntity2;
	protected GAEJPARoleEntity roleEntity3;
	protected GAEJPARoleEntity roleEntity4;
	protected List<GAEJPARoleEntity> roleEntityList;
	protected List<GAEJPARoleEntity> roleEntityListWithNullEntries;

	protected static String ROLE_ENTITY_JSON;
	protected static String ROLE_ENTITY_LIST_JSON;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpJSONStrings();
	}

	@Before
	public void setUp() {
		setUpRoleGAEJPAEntities();
		setUpUserGAEJPAEntities();
		setUpUsers();
		setUpRoles();
		setUpBdOutputs();
		setUpExpectedUserPersistStatusMessages();
	}

	/**
	 * Method initializing some JSON Strings
	 */
	private static void setUpJSONStrings() {
		StringBuilder sb = new StringBuilder();

		// USER_ENTITY_JSON
		sb.append("{");
		sb.append("id: null, ");
		sb.append("password: null, ");
		sb.append("email: null, ");
		sb.append("userroles: [null, roleA], ");
		sb.append("books: []");
		sb.append("}");
		USER_ENTITY_JSON = sb.toString();
		sb.setLength(0);
		sb.append("[");
		sb.append("{");
		sb.append("id: null, ");
		sb.append("password: null, ");
		sb.append("email: null, ");
		sb.append("userroles: [roleA], ");
		sb.append("books: []");
		sb.append("}").append(", ");
		sb.append("{");
		sb.append("id: testUserUsername, ");
		sb.append("password: ").append(TEST_USER_PASSWORD).append(", ");
		sb.append("email: null, ");
		sb.append("userroles: [], ");
		sb.append("books: []");
		sb.append("}");
		sb.append("]");
		USER_ENTITY_LIST_JSON = sb.toString();

		// COMPLEX_USER_ENTITY_JSON
		sb.setLength(0);
		sb.append("{id: null, password: null, email: null, userroles: [null, roleA], books: []}");
		COMPLEX_USER_ENTITY_JSON = sb.toString();
		sb.setLength(0);
		sb.append("{id: null, password: null, email: null, userroles: [roleA, null], books: []}");
		COMPLEX_USER_ENTITY_JSON_V2 = sb.toString();

		// ROLE_ENTITY_JSON
		sb.setLength(0);
		sb.append("{");
		sb.append("role: roleA, ");
		sb.append("description: null, ");
		sb.append("users: []");
		sb.append("}");
		ROLE_ENTITY_JSON = sb.toString();

		// ROLE_ENTITY_LIST_JSON
		sb.setLength(0);
		sb.append("[");
		sb.append(ROLE_ENTITY_JSON);
		sb.append("]");
		ROLE_ENTITY_LIST_JSON = sb.toString();
	}

	/**
	 * Method intitializing GAEJPARoleEntities
	 */
	private void setUpRoleGAEJPAEntities() {
		roleEntity1 = new GAEJPARoleEntity();
		roleEntity1.setKey(ROLE_A);
		roleEntity2 = new GAEJPARoleEntity();
		roleEntity3 = new GAEJPARoleEntity();
		roleEntity3.setKey(ROLE_B);
		roleEntity3.setDescription(DESCRIPTION);
		roleEntity4 = new GAEJPARoleEntity();
		roleEntity4.setDescription(DESCRIPTION);

		roleEntityList = new ArrayList<>();
		roleEntityList.add(roleEntity1);
		roleEntityList.add(roleEntity2);

		roleEntityListWithNullEntries = new ArrayList<>();
		roleEntityListWithNullEntries.add(null);
	}

	/**
	 * Method intitializing some User GAEJPAUserEntities
	 */
	private void setUpUserGAEJPAEntities() {
		userEntity1 = new GAEJPAUserEntity();
		userEntity1.getRoles().add(roleEntity1);
		userEntity2 = new GAEJPAUserEntity();
		userEntity2.setKey(TEST_USER_USERNAME);
		userEntity2.setPassword(TEST_USER_PASSWORD);
		userEntity3 = new GAEJPAUserEntity();
		userEntity3.setKey(TEST_USER_USERNAME);
		userEntity4 = new GAEJPAUserEntity();
		userEntity4.getRoles().add(roleEntity1);
		userEntity4.getRoles().add(null);

		userEntityList = new ArrayList<>();
		userEntityList.add(userEntity1);
		userEntityList.add(userEntity2);
		emptyUserEntityList = new ArrayList<>();
		userEntityListWithRoles = new ArrayList<>();
		userEntityListWithRoles.add(userEntity4);
	}

	/**
	 * Method initializing some Users
	 */
	private void setUpUsers() {
		user1 = new User();
		user2 = new User();
		simpleUsers = new Users();
		simpleUsers.getUsers().add(user1);
		simpleUsers.getUsers().add(user2);
		emptyUsers = new Users();
	}

	/**
	 * Method setting up some Roles
	 */
	private void setUpRoles() {
		existingRoles = new Roles();
		existingRoles.getRoles().add(ROLE_A);

		nonExistingRoles = new Roles();
		nonExistingRoles.getRoles().add(ROLE_C);
	}

	/**
	 * Method initializing some DelegatorOutputs
	 */
	private void setUpBdOutputs() {
		userDelegatorOutput = new DelegatorOutput();
		userDelegatorOutput.setOutputObject(simpleUsers);
		unrecognizedUserDelegatorOutput = new DelegatorOutput();
		unrecognizedUserDelegatorOutput.setOutputObject(null);
	}

	/**
	 * Method initializing expected User retrieval status messages
	 */
	private void setUpExpectedUserPersistStatusMessages() {
		// EXPECTED_NO_FAILURES_USER_PERSISTENCE_MESSAGE_RESULT
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.ADD_USER_SUCCESS_MSG);
		sb.append(" '")
				.append(UserServiceEntityMapper
						.mapUserEntityListToConsolidatedJSONString(userEntityList))
				.append("'. ").append(userEntityList.size())
				.append(" users were successfully added.");
		EXPECTED_NO_FAILURES_USER_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NO_SUCCESSES_USER_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ErrorConstants.ADD_USER_SUCCESS_MSG);
		sb.append(" '")
				.append(UserServiceEntityMapper
						.mapUserEntityListToConsolidatedJSONString(emptyUserEntityList))
				.append("'. ")
				.append(emptyUserEntityList.size())
				.append(" users were successfully added.")
				.append(System.getProperty("line.seperator"))
				.append("'")
				.append(ErrorConstants.ADD_USER_FAILURE_MSG)
				.append(UserServiceEntityMapper
						.mapUserEntityListToConsolidatedJSONString(userEntityList))
				.append("'. ").append(userEntityList.size())
				.append(" users were not added successfully.");
		EXPECTED_NO_SUCCESSES_USER_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NULL_INPUT_USER_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ErrorConstants.ADD_USER_SUCCESS_MSG)
				.append(" '")
				.append(UserServiceEntityMapper
						.mapUserEntityListToConsolidatedJSONString(null))
				.append("'. ").append(0)
				.append(" users were successfully added.");
		EXPECTED_NULL_INPUT_USER_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_USER_REMOVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(ErrorConstants.DELETE_USER_SUCCESS_MSG)
				.append(" '")
				.append(UserServiceEntityMapper
						.mapUserEntityListToConsolidatedJSONString(userEntityList))
				.append("'. ").append(userEntityList.size())
				.append(" Entities were removed.");
		EXPECTED_USER_REMOVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		// EXPECTED_USER_RETRIEVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(ErrorConstants.RETRIEVE_USER_FOR_ROLES_SUCCESS_MSG)
				.append(" '")
				.append(existingRoles.getRoles().toString())
				.append("':")
				.append(" '")
				.append(UserServiceEntityMapper
						.mapUserEntityListToConsolidatedJSONString(userEntityList))
				.append("'. ").append(userEntityList.size())
				.append(" Entities were found");
		EXPECTED_USER_RETRIEVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		// EXPECTED_USER_RETRIEVAL_BY_ID_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(ErrorConstants.RETRIEVE_USER_BY_ID_SUCCESS_MSG)
				.append(" '")
				.append(userEntity1.getKey())
				.append("': '")
				.append(UserServiceEntityMapper
						.mapUserEntityToJSONString(userEntity1)).append("'.");
		EXPECTED_USER_RETRIEVAL_BY_ID_MESSAGE_RESULT = sb.toString();
	}
}
