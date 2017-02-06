package amtc.gue.ws.test.base.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.base.response.UserServiceResponse;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.UserServiceEntityMapper;

/**
 * Testclass for the UserServiceEntityMapper
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceEntityMapperTest extends UserServiceUtilTest {

	@Test
	public void testMapUserToEntityForDeleteType() {
		user1.setId(TEST_USER_ENTITY_KEY);
		GAEJPAUserEntity userEntity = UserServiceEntityMapper.mapUserToEntity(
				user1, DelegatorTypeEnum.DELETE);
		assertEquals(user1.getId(), userEntity.getKey());
		assertEquals("", userEntity.getPassword());
		assertEquals("", userEntity.getEmail());
	}

	@Test
	public void testMapUserToEntityForAddType() {
		GAEJPAUserEntity userEntity = UserServiceEntityMapper.mapUserToEntity(
				user1, DelegatorTypeEnum.ADD);
		assertNull(userEntity.getKey());
	}

	@Test
	public void testMapUserToEntityForUpdateType() {
		user1.setId(TEST_USER_ENTITY_KEY);
		GAEJPAUserEntity userEntity = UserServiceEntityMapper.mapUserToEntity(
				user1, DelegatorTypeEnum.UPDATE);
		assertEquals(user1.getId(), userEntity.getKey());
	}

	@Test
	public void testMapUserToEntityForAddTypeUsingId() {
		user1.setId(TEST_USER_ENTITY_KEY);
		GAEJPAUserEntity userEntity = UserServiceEntityMapper.mapUserToEntity(
				user1, DelegatorTypeEnum.ADD);
		assertNotNull(userEntity.getKey());
		assertEquals("", userEntity.getPassword());
		assertEquals("", userEntity.getEmail());
	}

	@Test
	public void testTransformUsersToUserEntitiesUsingSimpleUsers() {
		List<GAEJPAUserEntity> userEntityList = UserServiceEntityMapper
				.transformUsersToUserEntities(simpleUsers,
						DelegatorTypeEnum.ADD);
		assertNotNull(userEntityList);
		assertEquals(2, userEntityList.size());
	}

	@Test
	public void testTransformUsersToUserEntitiesUsingEmptyUsers() {
		List<GAEJPAUserEntity> userEntityList = UserServiceEntityMapper
				.transformUsersToUserEntities(emptyUsers,
						DelegatorTypeEnum.ADD);
		assertNotNull(userEntityList);
		assertEquals(0, userEntityList.size());
	}

	@Test
	public void testTransformUsersToUserEntitiesUsingNullUsers() {
		List<GAEJPAUserEntity> userEntityList = UserServiceEntityMapper
				.transformUsersToUserEntities(null, DelegatorTypeEnum.ADD);
		assertNotNull(userEntityList);
		assertEquals(0, userEntityList.size());
	}

	@Test
	public void testMapUserEntityToUserUsingSimpleUserEntity() {
		User user = UserServiceEntityMapper.mapUserEntityToUser(userEntity2);
		assertNotNull(user);
		assertEquals(userEntity2.getKey(), user.getId());
		assertEquals(userEntity2.getPassword(), user.getPassword());
	}

	@Test
	public void testMapUserEntityToUserUsingNullUserEntity() {
		User user = UserServiceEntityMapper.mapUserEntityToUser(null);
		assertNotNull(user);
		assertNull(user.getId());
		assertNull(user.getPassword());
		assertTrue(user.getRoles().isEmpty());
	}

	@Test
	public void testTransformUserEntitiesToUsersUsingSimpleUserEntityList() {
		Users users = UserServiceEntityMapper
				.transformUserEntitiesToUsers(userEntityList);
		assertNotNull(users);
		assertEquals(userEntityList.size(), users.getUsers().size());
	}

	@Test
	public void testTransformUserEntitiesToUsersUsingNullInput() {
		Users users = UserServiceEntityMapper
				.transformUserEntitiesToUsers(null);
		assertNotNull(users);
		assertTrue(users.getUsers().isEmpty());
	}

	@Test
	public void testMapRolesToRoleEntityListUsingSimpleRoles() {
		int resultSetSize = UserServiceEntityMapper.mapRolesToRoleEntityList(
				existingRoles.getRoles()).size();
		assertEquals(1, resultSetSize);
	}

	@Test
	public void testMapRolesToRoleEntityListUsingNullInput() {
		assertEquals(0, UserServiceEntityMapper.mapRolesToRoleEntityList(null)
				.size());
	}

	@Test
	public void testMapRoleEntityListToRolesUsingSimpleRoleEntityList() {
		assertEquals(2,
				UserServiceEntityMapper
						.mapRoleEntityListToRoles(roleEntityList).size());
	}

	@Test
	public void testMapRoleEntityListToRolesUsingRoleEntityListWithNullEntries() {
		assertEquals(
				1,
				UserServiceEntityMapper.mapRoleEntityListToRoles(
						roleEntityListWithNullEntries).size());
	}

	@Test
	public void testMapRoleEntityListToRolesUsingNullInput() {
		assertEquals(0, UserServiceEntityMapper.mapRoleEntityListToRoles(null)
				.size());
	}

	@Test
	public void testMapUserEntityToJSONStringUsingNullUserEntity() {
		String JSONString = UserServiceEntityMapper
				.mapUserEntityToJSONString(null);
		assertEquals(NULL_USER_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapUserEntityToJSONStringUsingSimpleUserEntity() {
		String JSONString = UserServiceEntityMapper
				.mapUserEntityToJSONString(userEntity4);
		assertEquals(USER_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapUserEntityToJSONStringUsingComplexUserEntity() {
		String JSONString3 = UserServiceEntityMapper
				.mapUserEntityToJSONString(userEntity4);
		assertTrue(JSONString3.equals(COMPLEX_USER_ENTITY_JSON)
				|| JSONString3.equals(COMPLEX_USER_ENTITY_JSON_V2));
	}

	@Test
	public void testMapUserEntityListToConsolidatedJSONStringUsingSimpleList() {
		String JSONString = UserServiceEntityMapper
				.mapUserEntityListToConsolidatedJSONString(userEntityList);
		assertEquals(USER_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapUserEntityListToConsolidatedJSONStringUsingNullList() {
		String JSONString = UserServiceEntityMapper
				.mapUserEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_USER_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapUserEntityListToConsolidatedJSONStringUsingEmptyList() {
		String JSONString = UserServiceEntityMapper
				.mapUserEntityListToConsolidatedJSONString(emptyUserEntityList);
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
		UserServiceResponse userResponse = UserServiceEntityMapper
				.mapBdOutputToUserServiceResponse(null);
		assertNull(userResponse);
	}

	@Test
	public void testMapRoleEntityToJSONString() {
		String JSONString = UserServiceEntityMapper
				.mapRoleEntityToJSONString(roleEntity1);
		assertEquals(ROLE_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapRoleEntityToJSONStringUsingNullInput() {
		String JSONString = UserServiceEntityMapper
				.mapRoleEntityToJSONString(null);
		assertEquals("{}", JSONString);
	}
}
