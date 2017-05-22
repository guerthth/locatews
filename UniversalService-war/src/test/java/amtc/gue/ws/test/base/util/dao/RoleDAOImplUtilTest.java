package amtc.gue.ws.test.base.util.dao;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.util.dao.RoleDAOImplUtils;
import amtc.gue.ws.test.base.UserTest;

/**
 * Testclass for the RoleDAOImplUtil class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleDAOImplUtilTest extends UserTest {
	private static final String BASIC_ROLE_SPECIFIC_QUERY = "select r from RoleEntity r";
	private static final String UPDATED_ROLE_QUERY_1 = "select r from RoleEntity r"
			+ " where r.role = :role and r.description = :description";
	private static final String UPDATED_ROLE_QUERY_2 = "select r from RoleEntity r"
			+ " where r.description = :description";

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		setUpBasicEnvironment();
	}

	@Test
	public void testBuildSpecificRoleQueryWithNullEntity() {
		String updatedQuery = RoleDAOImplUtils.buildSpecificRoleQuery(BASIC_ROLE_SPECIFIC_QUERY, null);
		assertEquals(BASIC_ROLE_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildeSpecificRoleQueryWithNullValues() {
		String updatedQuery = RoleDAOImplUtils.buildSpecificRoleQuery(BASIC_ROLE_SPECIFIC_QUERY, JPARoleEntity5);
		assertEquals(BASIC_ROLE_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildSpecificRoleQueryMajorCriteria() {
		String updatedQuery = RoleDAOImplUtils.buildSpecificRoleQuery(BASIC_ROLE_SPECIFIC_QUERY, JPARoleEntity4);
		assertEquals(UPDATED_ROLE_QUERY_1, updatedQuery);
	}

	@Test
	public void testBuildSpecificRoleQueryMinorCriteria() {
		String updatedQuery = RoleDAOImplUtils.buildSpecificRoleQuery(BASIC_ROLE_SPECIFIC_QUERY, JPARoleEntity3);
		assertEquals(UPDATED_ROLE_QUERY_2, updatedQuery);
	}
}
