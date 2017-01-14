package amtc.gue.ws.test.base.util.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.base.util.dao.UserDAOImplUtils;
import amtc.gue.ws.test.base.util.UserServiceUtilTest;

/**
 * Class testing the functionality of the UserDAOImplUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOImplUtilTest extends UserServiceUtilTest {
	private static final String BASIC_USER_SPECIFIC_QUERY = "select u from UserEntity u";
	private static final String UPDATED_USER_QUERY_1 = "select u from UserEntity u"
			+ " where u.userId = :id and u.password = :password";
	private static final String UPDATED_USER_QUERY_2 = "select u from UserEntity u"
			+ " where u.userId = :id";

	@Test
	public void testCopyUserListUsingSimpleList() {
		List<GAEJPAUserEntity> copiedUserEntityList = UserDAOImplUtils
				.copyUserList(userEntityList);
		assertEquals(userEntityList.size(), copiedUserEntityList.size());
	}

	@Test
	public void testCopyUserListUsingNullList() {
		List<GAEJPAUserEntity> copiedUserEntityList = UserDAOImplUtils
				.copyUserList(null);
		assertNull(copiedUserEntityList);
	}

	@Test
	public void testRetrieveUserEntitiesWithExistingRoles() {
		// retrieveUserEntitiesWIthSpecificRolesOnly
		int resultListSize = UserDAOImplUtils
				.retrieveUserEntitiesWithSpecificRolesOnly(userEntityList,
						existingRoles).size();
		assertEquals(1, resultListSize);

		// test retrieveUserEntitiesWIthSpecificRoles
		resultListSize = UserDAOImplUtils
				.retrieveUserEntitiesWithSpecificRoles(userEntityList,
						existingRoles).size();
		assertEquals(1, resultListSize);
	}

	@Test
	public void testRetrieveUserEntitiesWithNonExistingRoles() {
		// retrieveUserEntitiesWIthSpecificRolesOnly
		int resultListSize = UserDAOImplUtils
				.retrieveUserEntitiesWithSpecificRolesOnly(userEntityList,
						nonExistingRoles).size();
		assertEquals(0, resultListSize);

		// test retrieveUserEntitiesWIthSpecificRoles
		resultListSize = UserDAOImplUtils
				.retrieveUserEntitiesWithSpecificRoles(userEntityList,
						nonExistingRoles).size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testRetrieveUserEntitiesUsingNullRoles() {
		// retrieveUserEntitiesWIthSpecificRolesOnly
		int resultListSize = UserDAOImplUtils
				.retrieveUserEntitiesWithSpecificRolesOnly(userEntityList, null)
				.size();
		assertEquals(userEntityList.size(), resultListSize);

		// test retrieveUserEntitiesWIthSpecificRoles
		resultListSize = UserDAOImplUtils
				.retrieveUserEntitiesWithSpecificRoles(userEntityList, null)
				.size();
		assertEquals(userEntityList.size(), resultListSize);
	}

	@Test
	public void testBuildSpecificUserQueryWithNullEntity() {
		String updatedQuery = UserDAOImplUtils.buildSpecificUserQuery(
				BASIC_USER_SPECIFIC_QUERY, null);
		assertEquals(BASIC_USER_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildSpecificUserQueryWithNullValues() {
		String updatedQuery = UserDAOImplUtils.buildSpecificUserQuery(
				BASIC_USER_SPECIFIC_QUERY, userEntity1);
		assertEquals(BASIC_USER_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildSpecificUserQuerMajorCriteria() {
		String updatedQuery = UserDAOImplUtils.buildSpecificUserQuery(
				BASIC_USER_SPECIFIC_QUERY, userEntity2);
		assertEquals(UPDATED_USER_QUERY_1, updatedQuery);
	}

	@Test
	public void testBuildSpecificUserQueryMinorCriteria() {
		String updatedQuery = UserDAOImplUtils.buildSpecificUserQuery(
				BASIC_USER_SPECIFIC_QUERY, userEntity3);
		assertEquals(UPDATED_USER_QUERY_2, updatedQuery);
	}

}
