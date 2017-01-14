package amtc.gue.ws.test.base.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.ProductiveEMF;
import amtc.gue.ws.base.persistence.dao.role.RoleDAO;
import amtc.gue.ws.base.persistence.dao.role.impl.RoleDAOImpl;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.dao.user.impl.UserDAOImpl;
import amtc.gue.ws.base.persistence.model.GAEJPARoleEntity;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;

/**
 * Super Testclass for all UserService JPA Testcases
 * 
 * @author Thomas
 *
 */
public abstract class UserServiceJPATest extends BaseDAOTest {

	protected static UserDAO userEntityDAO;
	protected static UserDAO failureUserEntityDAO;
	protected static RoleDAO roleEntityDAO;
	protected static RoleDAO failureRoleEntityDAO;

	protected GAEJPAUserEntity userEntity1;
	protected GAEJPAUserEntity userEntity2;
	protected GAEJPAUserEntity userEntity3;
	protected GAEJPARoleEntity roleEntity1;
	protected GAEJPARoleEntity roleEntity2;
	protected GAEJPARoleEntity roleEntity3;
	protected GAEJPARoleEntity roleEntity4;
	
	protected Roles rolesA;
	protected Roles rolesAB;

	protected static final String USER_NAME_A = "userNameA";
	protected static final String USER_NAME_B = "userNameB";
	protected static final String USER_PASSWORD_A = "userPasswordA";
	protected static final String USER_PASSWORD_B = "userPasswordB";

	protected static final String ROLE_A = "roleA";
	protected static final String ROLE_B = "roleB";
	protected static final String ROLE_C = "roleC";
	protected static final String DESCRIPTION = "description";

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setupBasicEnvironment();
	}

	@Before
	public void setUp() {
		helper.setUp();
		setupRoleEntities();
		setupUserEntities();
		setupRoles();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	/**
	 * Setting up Basic Environment
	 */
	private static void setupBasicEnvironment() {
		EMF emf = new ProductiveEMF();
		userEntityDAO = new UserDAOImpl(emf);
		failureUserEntityDAO = new UserDAOImpl(null);
		roleEntityDAO = new RoleDAOImpl(emf);
		failureRoleEntityDAO = new RoleDAOImpl(null);
	}

	/**
	 * Setting up RoleEntities
	 */
	private void setupRoleEntities() {
		roleEntity1 = new GAEJPARoleEntity();
		roleEntity1.setKey(ROLE_A);
		roleEntity2 = new GAEJPARoleEntity();
		roleEntity2.setKey(ROLE_B);
		roleEntity3 = new GAEJPARoleEntity();
		roleEntity3.setDescription(DESCRIPTION);
		roleEntity4 = new GAEJPARoleEntity();
		roleEntity4.setKey(ROLE_C);
		roleEntity4.setDescription(DESCRIPTION);
	}

	/**
	 * Setting up UserEntities
	 */
	private void setupUserEntities() {
		userEntity1 = new GAEJPAUserEntity();
		userEntity1.setKey(USER_NAME_A);
		userEntity1.setPassword(USER_PASSWORD_A);
		userEntity2 = new GAEJPAUserEntity();
		userEntity2.setKey(USER_NAME_B);
		userEntity2.setPassword(USER_PASSWORD_B);
		userEntity3 = new GAEJPAUserEntity();
	}
	
	/**
	 * Set up Roles
	 */
	private void setupRoles() {
		rolesA = new Roles();
		rolesA.getRoles().add(ROLE_A);
		rolesAB = new Roles();
		rolesAB.getRoles().add(ROLE_A);
		rolesAB.getRoles().add(ROLE_B);
	}
}
