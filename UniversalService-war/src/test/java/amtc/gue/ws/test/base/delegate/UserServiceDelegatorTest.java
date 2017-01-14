package amtc.gue.ws.test.base.delegate;

import java.util.ArrayList;
import java.util.List;

import amtc.gue.ws.base.delegate.persist.UserPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.persistence.model.GAEJPARoleEntity;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.base.util.PersistenceTypeEnum;
import amtc.gue.ws.test.base.delegate.persist.BasePersistenceDelegatorTest;

/**
 * Super Testclass for all UserService Delegator Testcases
 * 
 * @author Thomas
 *
 */
public abstract class UserServiceDelegatorTest extends
		BasePersistenceDelegatorTest {

	protected static final String TESTUSERNAME = "testUserName";
	protected static final String TESTUSERPASSWORD = "testUserPassword";
	protected static final String ROLE_A = "roleA";

	protected static GAEJPAUserEntity retrievedUserEntity;
	protected static GAEJPAUserEntity removedUserEntity;
	protected static GAEJPARoleEntity retrievedRoleEntity;

	protected static List<GAEJPAUserEntity> emptyUserEntityList;
	protected static List<GAEJPAUserEntity> retrievedUserEntityList;
	protected static List<GAEJPAUserEntity> removedUserEntityList;
	
	protected static List<GAEJPARoleEntity> emptyRoleEntityList;
	protected static List<GAEJPARoleEntity> retrievedRoleEntityList;

	protected static UserPersistenceDelegator userPersistenceDelegator;

	protected static PersistenceDelegatorInput addUserDelegatorInput;
	protected static PersistenceDelegatorInput addUserDelegatorInputForRoleTesting;
	protected static PersistenceDelegatorInput deleteUserDelegatorInput;
	protected static PersistenceDelegatorInput deleteUserDelegatorInputWithId;
	protected static PersistenceDelegatorInput readUserDelegatorInput;

	protected static List<String> roleList;
	protected static Roles roles;

	protected static List<User> userList;
	protected static List<User> userListForRoleTesting;
	protected static List<User> userListWithId;

	protected static User firstUser;
	protected static User secondUser;
	protected static User thirdUser;

	protected static Users users;
	protected static Users roleTestUsers;
	protected static Users usersWithId;

	/**
	 * Method building the initial setup for delegatortests of the UserService
	 */
	protected static void oneTimeInitialSetup() {
		setUpRoles();
		setUpUsers();
		setUpUserEntities();
		setUpRoleEntities();
		setUpBaseDelegatorInputs();
		setUpUserDelegatorInputs();
		setUpUserPersistenceDelegators();
	}

	/**
	 * Method setting up Tags
	 */
	private static void setUpRoles() {
		roleList = new ArrayList<>();
		roleList.add(ROLE_A);

		roles = new Roles();
		roles.setRoles(roleList);
	}

	/**
	 * Method setting up Users
	 */
	private static void setUpUsers() {
		userList = new ArrayList<>();
		userListForRoleTesting = new ArrayList<>();

		firstUser = new User();
		firstUser.setPassword(TESTUSERPASSWORD);
		firstUser.setRoles(roles.getRoles());
		userList.add(firstUser);
		userListForRoleTesting.add(firstUser);

		secondUser = new User();
		secondUser.setPassword(TESTUSERPASSWORD);
		userList.add(secondUser);

		users = new Users();
		users.setUsers(userList);
		
		roleTestUsers = new Users();
		roleTestUsers.setUsers(userListForRoleTesting);

		userListWithId = new ArrayList<>();

		thirdUser = new User();
		thirdUser.setId(TESTUSERNAME);
		userListWithId.add(thirdUser);

		usersWithId = new Users();
		usersWithId.setUsers(userListWithId);
	}

	/**
	 * Method setting up some UserEntities
	 */
	private static void setUpUserEntities() {
		emptyUserEntityList = new ArrayList<>();

		retrievedUserEntity = new GAEJPAUserEntity();
		retrievedUserEntity.setKey(TESTUSERNAME);

		removedUserEntity = new GAEJPAUserEntity();
		removedUserEntity.setKey(TESTUSERNAME);

		removedUserEntityList = new ArrayList<>();
		removedUserEntityList.add(removedUserEntity);

		retrievedUserEntityList = new ArrayList<>();
		retrievedUserEntityList.add(retrievedUserEntity);
	}
	
	/**
	 * Method setting up some RoleEntities
	 */
	private static void setUpRoleEntities() {
		emptyRoleEntityList = new ArrayList<>();
		
		retrievedRoleEntity = new GAEJPARoleEntity();
		retrievedRoleEntityList = new ArrayList<>();
		retrievedRoleEntityList.add(retrievedRoleEntity);
	}

	/**
	 * Method setting up delegator inputs
	 */
	private static void setUpUserDelegatorInputs() {
		// DelegatorInput for user entity adding
		addUserDelegatorInput = new PersistenceDelegatorInput();
		addUserDelegatorInput.setInputObject(users);
		addUserDelegatorInput.setType(PersistenceTypeEnum.ADD);
		
		addUserDelegatorInputForRoleTesting = new PersistenceDelegatorInput();
		addUserDelegatorInputForRoleTesting.setInputObject(roleTestUsers);
		addUserDelegatorInputForRoleTesting.setType(PersistenceTypeEnum.ADD);

		// DelegatorInput for user entity deletion
		deleteUserDelegatorInput = new PersistenceDelegatorInput();
		deleteUserDelegatorInput.setInputObject(users);
		deleteUserDelegatorInput.setType(PersistenceTypeEnum.DELETE);

		// DelegatorInput for user entity deletion with ID user
		deleteUserDelegatorInputWithId = new PersistenceDelegatorInput();
		deleteUserDelegatorInputWithId.setInputObject(usersWithId);
		deleteUserDelegatorInputWithId.setType(PersistenceTypeEnum.DELETE);

		// DelegatorInput for user entity read
		readUserDelegatorInput = new PersistenceDelegatorInput();
		readUserDelegatorInput.setInputObject(roles);
		readUserDelegatorInput.setType(PersistenceTypeEnum.READ);
	}

	/**
	 * Method setting up persistence delegator instances
	 */
	private static void setUpUserPersistenceDelegators() {
		userPersistenceDelegator = new UserPersistenceDelegator();
	}
}
