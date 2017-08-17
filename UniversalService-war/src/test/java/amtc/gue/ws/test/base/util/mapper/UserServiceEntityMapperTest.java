package amtc.gue.ws.test.base.util.mapper;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.response.UserServiceResponse;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;
import amtc.gue.ws.base.util.mapper.jpa.UserServiceJPAEntityMapper;
import amtc.gue.ws.base.util.mapper.objectify.UserServiceObjectifyEntityMapper;
import amtc.gue.ws.test.base.UserTest;

/**
 * Testclass for the UserServiceEntityMapper
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceEntityMapperTest extends UserTest {

	private static String USER_ENTITY_JSON;
	private static String COMPLEX_USER_ENTITY_JSON;
	private static String COMPLEX_USER_ENTITY_JSON_V2;
	private static String USER_ENTITY_LIST_JSON;
	private static String NULL_USER_ENTITY_JSON = "{}";
	private static String NULL_USER_ENTITY_LIST_JSON = "[]";
	private static String ROLE_ENTITY_JSON;
	private static String ROLE_ENTITY_LIST_JSON;
	private static IDelegatorOutput userDelegatorOutput;
	private static IDelegatorOutput unrecognizedUserDelegatorOutput;

	private UserServiceEntityMapper JPAUserEntityMapper = new UserServiceJPAEntityMapper();
	private UserServiceEntityMapper objectifyUserEntityMapper = new UserServiceObjectifyEntityMapper();

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		setUpBasicEnvironment();
		setUpJSONStrings();
		setUpBdOutputs();
	}

	@Test
	public void testMapUserToEntityForDeleteType() {
		user1.setId(USERNAME);
		GAEUserEntity userEntity = JPAUserEntityMapper.mapUserToEntity(user1, DelegatorTypeEnum.DELETE);
		assertEquals(user1.getId(), userEntity.getKey());
		assertEquals("", userEntity.getUserName());
		assertNotNull("", userEntity.getPassword());
	}

	@Test
	public void testMapUserToEntityForAddType() {
		GAEUserEntity userEntity = JPAUserEntityMapper.mapUserToEntity(user1, DelegatorTypeEnum.ADD);
		assertNull(userEntity.getKey());
	}

	@Test
	public void testMapUserToEntityForUpdateType() {
		user1.setId(USERNAME);
		GAEUserEntity userEntity = JPAUserEntityMapper.mapUserToEntity(user1, DelegatorTypeEnum.UPDATE);
		assertEquals(user1.getId(), userEntity.getKey());
	}

	@Test
	public void testMapUserToEntityForAddTypeUsingId() {
		user1.setId(USERNAME);
		GAEUserEntity userEntity = JPAUserEntityMapper.mapUserToEntity(user1, DelegatorTypeEnum.ADD);
		assertNotNull(userEntity.getKey());
		assertEquals("", userEntity.getUserName());
		assertNotNull(userEntity.getPassword());
	}

	@Test
	public void testTransformUsersToUserEntitiesUsingSimpleUsers() {
		List<GAEUserEntity> userEntityList = JPAUserEntityMapper.transformUsersToUserEntities(users,
				DelegatorTypeEnum.ADD);
		assertNotNull(userEntityList);
		assertEquals(2, userEntityList.size());
	}

	@Test
	public void testTransformUsersToUserEntitiesUsingEmptyUsers() {
		List<GAEUserEntity> userEntityList = JPAUserEntityMapper.transformUsersToUserEntities(usersWithoutContent,
				DelegatorTypeEnum.ADD);
		assertNotNull(userEntityList);
		assertEquals(0, userEntityList.size());
	}

	@Test
	public void testTransformUsersToUserEntitiesUsingNullUsers() {
		List<GAEUserEntity> userEntityList = objectifyUserEntityMapper.transformUsersToUserEntities(null,
				DelegatorTypeEnum.ADD);
		assertNotNull(userEntityList);
		assertEquals(0, userEntityList.size());
	}

	@Test
	public void testMapUserEntityToUserUsingSimpleUserEntity() {
		User user = objectifyUserEntityMapper.mapUserEntityToUser(JPAUserEntity1);
		assertNotNull(user);
		assertEquals(JPAUserEntity1.getKey(), user.getId());
		assertEquals(JPAUserEntity1.getPassword(), user.getPassword());
	}

	@Test
	public void testMapUserEntityToUserUsingNullUserEntity() {
		User user = objectifyUserEntityMapper.mapUserEntityToUser(null);
		assertNotNull(user);
		assertNull(user.getId());
		assertNull(user.getPassword());
		assertTrue(user.getRoles().isEmpty());
	}

	@Test
	public void testTransformUserEntitiesToUsersUsingSimpleUserEntityList() {
		Users users = objectifyUserEntityMapper.transformUserEntitiesToUsers(JPAUserEntityList);
		assertNotNull(users);
		assertEquals(JPAUserEntityList.size(), users.getUsers().size());
	}

	@Test
	public void testTransformUserEntitiesToUsersUsingNullInput() {
		Users users = objectifyUserEntityMapper.transformUserEntitiesToUsers(null);
		assertNotNull(users);
		assertTrue(users.getUsers().isEmpty());
	}

	@Test
	public void testMapRolesToRoleEntityListUsingSimpleRoles() {
		int resultSetSize = objectifyUserEntityMapper.mapRolesToRoleEntityList(roles.getRoles()).size();
		assertEquals(1, resultSetSize);
	}

	@Test
	public void testMapRolesToRoleEntityListUsingNullInput() {
		assertEquals(0, objectifyUserEntityMapper.mapRolesToRoleEntityList(null).size());
	}

	@Test
	public void testMapRoleEntityListToRolesUsingSimpleRoleEntityList() {
		assertEquals(1, UserServiceEntityMapper.mapRoleEntityListToRoles(JPARoleEntityList).size());
	}

	@Test
	public void testMapRoleEntityListToRolesUsingRoleEntityListWithNullEntries() {
		assertEquals(1, UserServiceEntityMapper.mapRoleEntityListToRoles(JPARoleEntityNullValueList).size());
	}

	@Test
	public void testMapRoleEntityListToRolesUsingNullInput() {
		assertEquals(0, UserServiceEntityMapper.mapRoleEntityListToRoles(null).size());
	}

	@Test
	public void testMapUserEntityToJSONStringUsingNullUserEntity() {
		String JSONString = UserServiceEntityMapper.mapUserEntityToJSONString(null);
		assertEquals(NULL_USER_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapUserEntityToJSONStringUsingSimpleUserEntity() {
		String JSONString = UserServiceEntityMapper.mapUserEntityToJSONString(JPAUserEntity1);
		assertEquals(USER_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapUserEntityToJSONStringUsingComplexUserEntity() {
		String JSONString3 = UserServiceEntityMapper.mapUserEntityToJSONString(JPAUserEntity3);
		assertTrue(JSONString3.equals(COMPLEX_USER_ENTITY_JSON) || JSONString3.equals(COMPLEX_USER_ENTITY_JSON_V2));
	}

	@Test
	public void testMapUserEntityListToConsolidatedJSONStringUsingSimpleList() {
		String JSONString = UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(JPAUserEntityList);
		assertEquals(USER_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapUserEntityListToConsolidatedJSONStringUsingNullList() {
		String JSONString = UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_USER_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapUserEntityListToConsolidatedJSONStringUsingEmptyList() {
		String JSONString = UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(JPAUserEntityEmptyList);
		assertEquals(NULL_USER_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapBdOutputToUserServiceResponseUsingUsersOutputObject() {
		UserServiceResponse userResponse = UserServiceEntityMapper
				.mapBdOutputToUserServiceResponse(userDelegatorOutput);
		assertNotNull(userResponse.getUsers());
	}

	@Test
	public void testMapBdOutputToUserServiceResponseUsingUnrecognizedOutputObject() {
		UserServiceResponse userResponse = UserServiceEntityMapper
				.mapBdOutputToUserServiceResponse(unrecognizedUserDelegatorOutput);
		assertNull(userResponse.getUsers());
	}

	@Test
	public void testMapBdOutputToUserServiceResponseUsingNullInput() {
		UserServiceResponse userResponse = UserServiceEntityMapper.mapBdOutputToUserServiceResponse(null);
		assertNull(userResponse);
	}

	@Test
	public void testMapRoleEntityToJSONString() {
		String JSONString = UserServiceEntityMapper.mapRoleEntityToJSONString(JPARoleEntity1);
		assertEquals(ROLE_ENTITY_JSON, JSONString);
		assertNotNull(ROLE_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapRoleEntityToJSONStringUsingNullInput() {
		String JSONString = UserServiceEntityMapper.mapRoleEntityToJSONString(null);
		assertEquals("{}", JSONString);
	}

	/**
	 * Method initializing some JSON Strings
	 */
	private static void setUpJSONStrings() {
		StringBuilder sb = new StringBuilder();

		// USER_ENTITY_JSON
		sb.append("{");
		sb.append("id: ").append(USERNAME);
		sb.append(", userName: null");
		sb.append(", password: ").append(PASSWORD);
		sb.append(", userroles: [], ");
		sb.append("books: []");
		sb.append("}");
		USER_ENTITY_JSON = sb.toString();
		sb.setLength(0);
		sb.append("[").append(USER_ENTITY_JSON).append("]");
		USER_ENTITY_LIST_JSON = sb.toString();

		// COMPLEX_USER_ENTITY_JSON
		sb.setLength(0);
		sb.append("{id: null, userName: null, password: null, userroles: [], books: []}");
		COMPLEX_USER_ENTITY_JSON = sb.toString();
		sb.setLength(0);
		sb.append("{id: null, userName: null, password: null, userroles: [], books: []}");
		COMPLEX_USER_ENTITY_JSON_V2 = sb.toString();

		// ROLE_ENTITY_JSON
		sb.setLength(0);
		sb.append("{");
		sb.append("role: testRole, ");
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
	 * Method initializing some DelegatorOutputs
	 */
	private static void setUpBdOutputs() {
		userDelegatorOutput = new DelegatorOutput();
		userDelegatorOutput.setOutputObject(objectifyUserEntityList);
		unrecognizedUserDelegatorOutput = new DelegatorOutput();
		unrecognizedUserDelegatorOutput.setOutputObject(null);
	}
}
