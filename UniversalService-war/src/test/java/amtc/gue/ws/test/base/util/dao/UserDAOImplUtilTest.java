package amtc.gue.ws.test.base.util.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.util.dao.UserDAOImplUtils;
import amtc.gue.ws.test.base.UserTest;

/**
 * Class testing the functionality of the UserDAOImplUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOImplUtilTest extends UserTest {
	private static final String BASIC_USER_SPECIFIC_QUERY = "select u from UserEntity u";
	private static final String UPDATED_USER_QUERY_1 = "select u from UserEntity u"
			+ " where u.userId = :id and u.password = :password";
	private static final String UPDATED_USER_QUERY_2 = "select u from UserEntity u" + " where u.userId = :id";

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		setUpBasicEnvironment();
	}

	@Test
	public void testCopyUserListUsingSimpleList() {
		List<GAEUserEntity> copiedUserEntityList = UserDAOImplUtils.copyUserList(JPAUserEntityList);
		assertEquals(JPAUserEntityList.size(), copiedUserEntityList.size());
	}

	@Test
	public void testCopyUserListUsingNullList() {
		List<GAEUserEntity> copiedUserEntityList = UserDAOImplUtils.copyUserList(null);
		assertNull(copiedUserEntityList);
	}

	@Test
	public void testRetrieveUserEntitiesWithExistingRoles() {
		// retrieveUserEntitiesWIthSpecificRolesOnly
		int resultListSize = UserDAOImplUtils.retrieveUserEntitiesWithSpecificRolesOnly(JPAUserEntityList, roles)
				.size();
		assertEquals(0, resultListSize);

		// test retrieveUserEntitiesWIthSpecificRoles
		resultListSize = UserDAOImplUtils.retrieveUserEntitiesWithSpecificRoles(JPAUserEntityListWithRole, roles)
				.size();
		assertEquals(1, resultListSize);
	}

	@Test
	public void testRetrieveUserEntitiesWithNonExistingRoles() {
		// retrieveUserEntitiesWIthSpecificRolesOnly
		int resultListSize = UserDAOImplUtils.retrieveUserEntitiesWithSpecificRolesOnly(JPAUserEntityList, rolesAB)
				.size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testRetrieveUserEntitiesUsingNullRoles() {
		// retrieveUserEntitiesWithSpecificRolesOnly
		int resultListSize = UserDAOImplUtils.retrieveUserEntitiesWithSpecificRolesOnly(JPAUserEntityList, null).size();
		assertEquals(JPAUserEntityList.size(), resultListSize);
	}

	@Test
	public void testBuildSpecificUserQueryWithNullEntity() {
		String updatedQuery = UserDAOImplUtils.buildSpecificUserQuery(BASIC_USER_SPECIFIC_QUERY, null);
		assertEquals(BASIC_USER_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildSpecificUserQueryWithNullValues() {
		String updatedQuery = UserDAOImplUtils.buildSpecificUserQuery(BASIC_USER_SPECIFIC_QUERY, JPAUserEntity3);
		assertEquals(BASIC_USER_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildSpecificUserQuerMajorCriteria() {
		String updatedQuery = UserDAOImplUtils.buildSpecificUserQuery(BASIC_USER_SPECIFIC_QUERY, JPAUserEntity1);
		assertEquals(UPDATED_USER_QUERY_1, updatedQuery);
	}

	@Test
	public void testBuildSpecificUserQueryMinorCriteria() {
		String updatedQuery = UserDAOImplUtils.buildSpecificUserQuery(BASIC_USER_SPECIFIC_QUERY, JPAUserEntity5);
		assertEquals(UPDATED_USER_QUERY_2, updatedQuery);
	}

}
