package amtc.gue.ws.test.base.util.dao;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.util.dao.RoleDAOImplUtils;
import amtc.gue.ws.test.base.util.UserServiceUtilTest;

/**
 * Testclass for the RoleDAOImplUtil class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleDAOImplUtilTest extends UserServiceUtilTest {
	private static final String BASIC_ROLE_SPECIFIC_QUERY = "select r from RoleEntity r";
	private static final String UPDATED_ROLE_QUERY_1 = "select r from RoleEntity r"
			+ " where r.role = :role and r.description = :description";
	private static final String UPDATED_ROLE_QUERY_2 = "select r from RoleEntity r"
			+ " where r.description = :description";

	@Test
	public void testBuildSpecificRoleQueryWithNullEntity() {
		String updatedQuery = RoleDAOImplUtils.buildSpecificRoleQuery(
				BASIC_ROLE_SPECIFIC_QUERY, null);
		assertEquals(BASIC_ROLE_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildeSpecificRoleQueryWithNullValues() {
		String updatedQuery = RoleDAOImplUtils.buildSpecificRoleQuery(
				BASIC_ROLE_SPECIFIC_QUERY, roleEntity2);
		assertEquals(BASIC_ROLE_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildSpecificRoleQueryMajorCriteria() {
		String updatedQuery = RoleDAOImplUtils.buildSpecificRoleQuery(
				BASIC_ROLE_SPECIFIC_QUERY, roleEntity3);
		assertEquals(UPDATED_ROLE_QUERY_1, updatedQuery);
	}

	@Test
	public void testBuildSpecificRoleQueryMinorCriteria() {
		String updatedQuery = RoleDAOImplUtils.buildSpecificRoleQuery(
				BASIC_ROLE_SPECIFIC_QUERY, roleEntity4);
		assertEquals(UPDATED_ROLE_QUERY_2, updatedQuery);
	}
}
