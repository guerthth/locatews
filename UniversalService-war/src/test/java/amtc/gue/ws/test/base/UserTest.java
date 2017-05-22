package amtc.gue.ws.test.base;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.ProductiveEMF;
import amtc.gue.ws.base.persistence.dao.role.RoleDAO;
import amtc.gue.ws.base.persistence.dao.role.jpa.RoleJPADAOImpl;
import amtc.gue.ws.base.persistence.dao.role.objectify.RoleObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.dao.user.jpa.UserJPADAOImpl;
import amtc.gue.ws.base.persistence.dao.user.objectify.UserObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.role.jpa.GAEJPARoleEntity;
import amtc.gue.ws.base.persistence.model.role.objectify.GAEObjectifyRoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.jpa.GAEJPAUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;
import amtc.gue.ws.base.util.mapper.jpa.UserServiceJPAEntityMapper;
import amtc.gue.ws.base.util.mapper.objectify.UserServiceObjectifyEntityMapper;

/**
 * Class holding common data for all BaseService Tests
 * 
 * @author Thomas
 *
 */
public abstract class UserTest extends BaseTest {
	protected static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> userObjectifyDAO;
	protected static RoleDAO<GAERoleEntity, GAEObjectifyRoleEntity, String> roleObjectifyDAO;
	protected static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAO;
	protected static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> failureUserJPADAO;
	protected static RoleDAO<GAERoleEntity, GAEJPARoleEntity, String> roleJPADAO;
	protected static RoleDAO<GAERoleEntity, GAEJPARoleEntity, String> failureRoleJPADAO;

	protected static User user1;
	protected static User user2;
	protected static User user3;

	protected static List<User> userList;
	protected static List<User> userListWithRoles;
	protected static List<User> userListWithId;

	protected static Roles roles;
	protected static Roles rolesAB;
	protected static Roles rolesWithNullContent;
	protected static Users users;
	protected static Users usersWithRole;
	protected static Users usersWithId;
	protected static Users usersWithoutContent;

	protected static GAEObjectifyUserEntity objectifyUserEntity1;
	protected static GAEObjectifyUserEntity objectifyUserEntity2;
	protected static GAEObjectifyUserEntity objectifyUserEntity3;
	protected static GAEObjectifyRoleEntity objectifyRoleEntity1;
	protected static GAEObjectifyRoleEntity objectifyRoleEntity2;
	protected static GAEObjectifyRoleEntity objectifyRoleEntity3;
	protected static GAEObjectifyRoleEntity objectifyRoleEntity4;

	protected static List<GAEUserEntity> objectifyUserEntityList;
	protected static List<GAEUserEntity> objectifyUserEntityEmptyList;
	protected static List<GAERoleEntity> objectifyRoleEntityList;
	protected static List<GAERoleEntity> objectifyRoleEntityEmptyList;

	protected static GAEJPAUserEntity JPAUserEntity1;
	protected static GAEJPAUserEntity JPAUserEntity2;
	protected static GAEJPAUserEntity JPAUserEntity3;
	protected static GAEJPAUserEntity JPAUserEntity4;
	protected static GAEJPAUserEntity JPAUserEntity5;
	protected static GAEJPARoleEntity JPARoleEntity1;
	protected static GAEJPARoleEntity JPARoleEntity2;
	protected static GAEJPARoleEntity JPARoleEntity3;
	protected static GAEJPARoleEntity JPARoleEntity4;
	protected static GAEJPARoleEntity JPARoleEntity5;

	protected static List<GAEUserEntity> JPAUserEntityList;
	protected static List<GAEUserEntity> JPAUserEntityEmptyList;
	protected static List<GAEUserEntity> JPAUserEntityListWithRole;
	protected static List<GAERoleEntity> JPARoleEntityList;
	protected static List<GAERoleEntity> JPARoleEntityEmptyList;
	protected static List<GAERoleEntity> JPARoleEntityNullValueList;

	protected static final String USERNAME = "userName";
	protected static final String USERNAME_B = "userNameB";
	protected static final String PASSWORD = "userPasswordA";
	protected static final String PASSWORD_B = "userPasswordB";
	protected static final String EMAIL = "test@test.com";
	protected static final String ROLE = "testRole";
	protected static final String ROLE_B = "otherTestRole";
	protected static final String ROLE_C = "testRoleC";
	protected static final String DESCRIPTION = "testDescription";

	protected static UserServiceEntityMapper JPAUserEntityMapper;
	protected static UserServiceEntityMapper objectifyUserEntityMapper;

	@Before
	public void setUp() {
		setupDBHelpers();
		setupRoleEntities();
		setupUserEntities();
	}

	@After
	public void tearDown() {
		tearDownDBHelpers();
	}

	/**
	 * Setting up basic environment
	 * 
	 */
	protected static void setUpBasicEnvironment() {
		// Objectify setup
		ObjectifyService.setFactory(new ObjectifyFactory());
		ObjectifyService.register(GAEObjectifyUserEntity.class);
		ObjectifyService.register(GAEObjectifyRoleEntity.class);

		userObjectifyDAO = new UserObjectifyDAOImpl();
		roleObjectifyDAO = new RoleObjectifyDAOImpl();

		// JPA setup
		EMF emf = new ProductiveEMF();
		userJPADAO = new UserJPADAOImpl(emf);
		failureUserJPADAO = new UserJPADAOImpl(null);
		roleJPADAO = new RoleJPADAOImpl(emf);
		failureRoleJPADAO = new RoleJPADAOImpl(null);

		setupRoles();
		setupUsers();
		setupRoleEntities();
		setupUserEntities();
		setupEntityMappers();
	}

	/**
	 * Setting up all required role entities
	 */
	private static void setupRoleEntities() {
		// Objectify Roleentities
		objectifyRoleEntity1 = new GAEObjectifyRoleEntity();
		objectifyRoleEntity1.setKey(ROLE);
		objectifyRoleEntity2 = new GAEObjectifyRoleEntity();
		objectifyRoleEntity2.setKey(ROLE_B);
		objectifyRoleEntity3 = new GAEObjectifyRoleEntity();
		objectifyRoleEntity4 = new GAEObjectifyRoleEntity();
		objectifyRoleEntity4.setKey(ROLE_C);
		objectifyRoleEntity4.setDescription(DESCRIPTION);

		objectifyRoleEntityList = new ArrayList<>();
		objectifyRoleEntityList.add(objectifyRoleEntity1);

		objectifyRoleEntityEmptyList = new ArrayList<>();

		// JPA Roleentities
		JPARoleEntity1 = new GAEJPARoleEntity();
		JPARoleEntity1.setKey(ROLE);
		JPARoleEntity2 = new GAEJPARoleEntity();
		JPARoleEntity2.setKey(ROLE_B);
		JPARoleEntity3 = new GAEJPARoleEntity();
		JPARoleEntity3.setDescription(DESCRIPTION);
		JPARoleEntity4 = new GAEJPARoleEntity();
		JPARoleEntity4.setKey(ROLE_C);
		JPARoleEntity4.setDescription(DESCRIPTION);
		JPARoleEntity5 = new GAEJPARoleEntity();

		JPARoleEntityList = new ArrayList<>();
		JPARoleEntityList.add(JPARoleEntity1);

		JPARoleEntityEmptyList = new ArrayList<>();

		JPARoleEntityNullValueList = new ArrayList<>();
		JPARoleEntityNullValueList.add(null);
	}

	/**
	 * Setting up all required user entities
	 */
	private static void setupUserEntities() {
		// Objectify Userentities
		objectifyUserEntity1 = new GAEObjectifyUserEntity();
		objectifyUserEntity1.setKey(USERNAME);
		objectifyUserEntity2 = new GAEObjectifyUserEntity();
		objectifyUserEntity2.setKey(USERNAME_B);
		objectifyUserEntity2.setEmail(EMAIL);
		objectifyUserEntity3 = new GAEObjectifyUserEntity();

		objectifyUserEntityList = new ArrayList<>();
		objectifyUserEntityList.add(objectifyUserEntity1);

		objectifyUserEntityEmptyList = new ArrayList<>();

		// JPA Userentities
		JPAUserEntity1 = new GAEJPAUserEntity();
		JPAUserEntity1.setKey(USERNAME);
		JPAUserEntity1.setPassword(PASSWORD);
		JPAUserEntity2 = new GAEJPAUserEntity();
		JPAUserEntity2.setKey(USERNAME_B);
		JPAUserEntity2.setPassword(PASSWORD_B);
		JPAUserEntity3 = new GAEJPAUserEntity();
		JPAUserEntity4 = new GAEJPAUserEntity();
		JPAUserEntity4.addToRolesOnly(JPARoleEntity1);
		JPAUserEntity5 = new GAEJPAUserEntity();
		JPAUserEntity5.setKey(USERNAME);

		JPAUserEntityList = new ArrayList<>();
		JPAUserEntityList.add(JPAUserEntity1);

		JPAUserEntityEmptyList = new ArrayList<>();

		JPAUserEntityListWithRole = new ArrayList<>();
		JPAUserEntityListWithRole.add(JPAUserEntity4);
	}

	/**
	 * Setting up all required Role objects
	 */
	private static void setupRoles() {
		List<String> roleList = new ArrayList<>();
		roleList.add(ROLE);
		roles = new Roles();
		roles.setRoles(roleList);

		List<String> roleList2 = new ArrayList<>();
		roleList2.add(ROLE);
		roleList2.add(ROLE_B);
		rolesAB = new Roles();
		rolesAB.setRoles(roleList2);

		rolesWithNullContent = new Roles();
		rolesWithNullContent.setRoles(null);
	}

	/**
	 * Setting up all User objects
	 */
	protected static void setupUsers() {
		userList = new ArrayList<>();
		userListWithRoles = new ArrayList<>();
		userListWithId = new ArrayList<>();

		user1 = new User();
		user1.setPassword(PASSWORD);
		user1.setRoles(roles.getRoles());
		userList.add(user1);
		userListWithRoles.add(user1);

		user2 = new User();
		user2.setPassword(PASSWORD);
		userList.add(user2);

		user3 = new User();
		user3.setId(USERNAME);
		userListWithId.add(user3);

		users = new Users();
		users.setUsers(userList);

		usersWithRole = new Users();
		usersWithRole.setUsers(userListWithRoles);

		usersWithId = new Users();
		usersWithId.setUsers(userListWithId);

		usersWithoutContent = new Users();
	}

	/**
	 * Method setting up entity mappers
	 */
	private static void setupEntityMappers() {
		JPAUserEntityMapper = new UserServiceJPAEntityMapper();
		objectifyUserEntityMapper = new UserServiceObjectifyEntityMapper();
	}
}
