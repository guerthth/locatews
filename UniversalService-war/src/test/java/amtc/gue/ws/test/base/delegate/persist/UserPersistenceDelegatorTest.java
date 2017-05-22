package amtc.gue.ws.test.base.delegate.persist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.input.DelegatorInput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.UserPersistenceDelegator;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.Roles;
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
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.test.base.UserTest;

/**
 * Testclass for the UserPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserPersistenceDelegatorTest extends UserTest implements IBasePersistenceDelegatorTest {
	private static DelegatorInput addUserDelegatorInput;
	private static DelegatorInput addUserDelegatorInputForRoleTesting;
	private static DelegatorInput deleteUserDelegatorInput;
	private static DelegatorInput deleteUserDelegatorInputWithId;
	private static DelegatorInput readUserDelegatorInput;
	private static DelegatorInput readUserByIdDelegatorInput;
	private static DelegatorInput updateUserDelegatorInput;

	private static UserPersistenceDelegator userPersistenceDelegator;

	private static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAOImpl;
	private static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAOImplGeneralFail;
	private static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAOImplDeletionFail;
	private static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAOImplRetrievalFail;
	private static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAOImplNoFoundUsers;
	private static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAOImplNullUsers;
	private static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAOImplSpecificEntityFound;
	private static RoleDAO<GAERoleEntity, GAEJPARoleEntity, String> roleJPADAOImpl;
	private static RoleDAO<GAERoleEntity, GAEJPARoleEntity, String> roleJPADAOImplNoFoundRoles;
	private static RoleDAO<GAERoleEntity, GAEJPARoleEntity, String> roleJPADAOImplRetrievalFail;
	private static RoleDAO<GAERoleEntity, GAEJPARoleEntity, String> roleJPADAOImplPersistenceFail;

	private static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> userObjectifyDAOImpl;
	private static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> userObjectifyDAOImplGeneralFail;
	private static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> userObjectifyDAOImplDeletionFail;
	private static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> userObjectifyDAOImplRetrievalFail;
	private static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> userObjectifyDAOImplNoFoundUsers;
	private static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> userObjectifyDAOImplNullUsers;
	private static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> userObjectifyDAOImplSpecificEntityFound;
	private static RoleDAO<GAERoleEntity, GAEObjectifyRoleEntity, String> roleObjectifyDAOImpl;
	private static RoleDAO<GAERoleEntity, GAEObjectifyRoleEntity, String> roleObjectifyDAOImplNoFoundRoles;
	private static RoleDAO<GAERoleEntity, GAEObjectifyRoleEntity, String> roleObjectifyDAOImplRetrievalFail;
	private static RoleDAO<GAERoleEntity, GAEObjectifyRoleEntity, String> roleObjectifyDAOImplPersistenceFail;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		setUpBasicEnvironment();
		setUpDelegatorInputs();
		setUpUserPersistenceDelegatorInputs();
		setUpUserPersistenceDelegators();
		setUpDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(userJPADAOImpl);
		EasyMock.verify(userJPADAOImplGeneralFail);
		EasyMock.verify(userJPADAOImplDeletionFail);
		EasyMock.verify(userJPADAOImplRetrievalFail);
		EasyMock.verify(userJPADAOImplNoFoundUsers);
		EasyMock.verify(userJPADAOImplNullUsers);
		EasyMock.verify(userJPADAOImplSpecificEntityFound);
		EasyMock.verify(roleJPADAOImpl);
		EasyMock.verify(roleJPADAOImplNoFoundRoles);
		EasyMock.verify(roleJPADAOImplRetrievalFail);
		EasyMock.verify(roleJPADAOImplPersistenceFail);
		EasyMock.verify(userObjectifyDAOImpl);
		EasyMock.verify(userObjectifyDAOImplGeneralFail);
		EasyMock.verify(userObjectifyDAOImplDeletionFail);
		EasyMock.verify(userObjectifyDAOImplRetrievalFail);
		EasyMock.verify(userObjectifyDAOImplNoFoundUsers);
		EasyMock.verify(userObjectifyDAOImplNullUsers);
		EasyMock.verify(userObjectifyDAOImplSpecificEntityFound);
		EasyMock.verify(roleObjectifyDAOImpl);
		EasyMock.verify(roleObjectifyDAOImplNoFoundRoles);
		EasyMock.verify(roleObjectifyDAOImplRetrievalFail);
		EasyMock.verify(roleObjectifyDAOImplPersistenceFail);
	}

	@Override
	@Test
	public void testDelegateUsingNullInput() {
		userPersistenceDelegator.initialize(nullDelegatorInput);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateUsingUnrecognizedInputType() {
		userPersistenceDelegator.initialize(unrecognizedDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImpl);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAAddUsingCorrectInput() {
		userPersistenceDelegator.initialize(addUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImpl);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		userPersistenceDelegator.setUserEntityMapper(JPAUserEntityMapper);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(ErrorConstants.ADD_USER_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingCorrectInput() {
		userPersistenceDelegator.initialize(addUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(ErrorConstants.ADD_USER_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Override
	@Test
	public void testDelegateJPAAddUsingIncorrectDAOSetup() {
		userPersistenceDelegator.initialize(addUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImplGeneralFail);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		userPersistenceDelegator.setUserEntityMapper(JPAUserEntityMapper);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingIncorrectDAOSetup() {
		userPersistenceDelegator.initialize(addUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImplGeneralFail);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		userPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAAddUsingInvalidInput() {
		userPersistenceDelegator.initialize(invalidAddDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImpl);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingInvalidInput() {
		userPersistenceDelegator.initialize(invalidAddDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing persisting user entity with roles when no roles are
	 * persisted yet using JPA DAO
	 */
	@Test
	public void testDelegateJPAAddUsingInputWithNoFoundRoles() {
		userPersistenceDelegator.initialize(addUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImpl);
		userPersistenceDelegator.setUserEntityMapper(JPAUserEntityMapper);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImplNoFoundRoles);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(ErrorConstants.ADD_USER_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	/**
	 * Method testing persisting user entity with roles when no roles are
	 * persisted yet using Objectify DAO
	 */
	@Test
	public void testDelegateObjectifyAddUsingInputWithNoFoundRoles() {
		userPersistenceDelegator.initialize(addUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImplNoFoundRoles);
		userPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(ErrorConstants.ADD_USER_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	/**
	 * Method testing persisting user entity with role when role retrieval fails
	 * using JPA DAO
	 */
	@Test
	public void testDelegateJPAAddUsingCorrectInputAndTagRetrievalFail() {
		userPersistenceDelegator.initialize(addUserDelegatorInputForRoleTesting);
		userPersistenceDelegator.setUserDAO(userJPADAOImpl);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImplRetrievalFail);
		userPersistenceDelegator.setUserEntityMapper(JPAUserEntityMapper);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing persisting user entity with role when role retrieval fails
	 * using Objectify DAO
	 */
	@Test
	public void testDelegateObjectifyAddUsingCorrectInputAndTagRetrievalFail() {
		userPersistenceDelegator.initialize(addUserDelegatorInputForRoleTesting);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImplRetrievalFail);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing persisting user entity with role when role persistence
	 * fails using JPA DAO
	 */
	@Test
	public void testDelegateJPAAddUsingCorrectInputAndRolePersistenceFail() {
		userPersistenceDelegator.initialize(addUserDelegatorInputForRoleTesting);
		userPersistenceDelegator.setUserDAO(userJPADAOImpl);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImplPersistenceFail);
		userPersistenceDelegator.setUserEntityMapper(JPAUserEntityMapper);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing persisting user entity with role when role persistence
	 * fails using Objectify DAO
	 */
	@Test
	public void testDelegateObjectifysAddUsingCorrectInputAndRolePersistenceFail() {
		userPersistenceDelegator.initialize(addUserDelegatorInputForRoleTesting);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImplPersistenceFail);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingCorrectIdInput() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userJPADAOImpl);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingCorrectIdInput() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingNonExistingObjects() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImplNoFoundUsers);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNonExistingObjects() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImplNoFoundUsers);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());

	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingNullObjects() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userJPADAOImplNullUsers);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNullObjects() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImplNullUsers);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingIncorrectDAOSetup() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userJPADAOImplGeneralFail);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingIncorrectDAOSetup() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImplGeneralFail);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteDeletionFail() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userJPADAOImplDeletionFail);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteDeletionFail() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImplDeletionFail);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteRetrievalFail() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userJPADAOImplGeneralFail);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteRetrievalFail() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImplGeneralFail);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingInvalidInput() {
		userPersistenceDelegator.initialize(invalidDeleteDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImpl);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingInvalidInput() {
		userPersistenceDelegator.initialize(invalidDeleteDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAReadUsingCorrectInput() {
		userPersistenceDelegator.initialize(readUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImpl);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingCorrectInput() {
		userPersistenceDelegator.initialize(readUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAReadUsingIncorrectDAOSetup() {
		userPersistenceDelegator.initialize(readUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImplGeneralFail);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingIncorrectDAOSetup() {
		userPersistenceDelegator.initialize(readUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImplGeneralFail);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAReadUsingInvalidInput() {
		userPersistenceDelegator.initialize(invalidReadDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImpl);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingInvalidInput() {
		userPersistenceDelegator.initialize(invalidReadDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing retrieving user entity by ID using JPA DAO
	 */
	@Test
	public void testDelegateJPAReadUsingSearchById() {
		userPersistenceDelegator.initialize(readUserByIdDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing retrieving user entity by ID using Objectify DAO
	 */
	@Test
	public void testDelegateObjectifyReadUsingSearchById() {
		userPersistenceDelegator.initialize(readUserByIdDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing retrieving user entity by ID when Entity retrieval fails
	 * using JPA DAO
	 */
	@Test
	public void testDelegateJPAReadUsingSearchByIdRetrievalFail() {
		userPersistenceDelegator.initialize(readUserByIdDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImplRetrievalFail);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing retrieving user entity by ID when Entity retrieval fails
	 * using Objectify DAO
	 */
	@Test
	public void testDelegateObjectifyReadUsingSearchByIdRetrievalFail() {
		userPersistenceDelegator.initialize(readUserByIdDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImplRetrievalFail);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAUpdateUsingCorrectInput() {
		userPersistenceDelegator.initialize(updateUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImplSpecificEntityFound);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UPDATE_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingCorrectInput() {
		userPersistenceDelegator.initialize(updateUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImplSpecificEntityFound);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UPDATE_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing entity update when no persisted user was found using JPA
	 * DAO
	 */
	@Test
	public void testDelegateJPAUpdateUsingCorrectInputNoFoundUser() {
		userPersistenceDelegator.initialize(updateUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImplNoFoundUsers);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UPDATE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing entity update when no persisted user was found using
	 * Objectify DAO
	 */
	@Test
	public void testDelegateObjectifyUpdateUsingCorrectInputNoFoundUser() {
		userPersistenceDelegator.initialize(updateUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImplNoFoundUsers);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UPDATE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAUpdateUsingIncorrectDAOSetup() {
		userPersistenceDelegator.initialize(updateUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImplRetrievalFail);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UPDATE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UPDATE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingIncorrectDAOSetup() {
		userPersistenceDelegator.initialize(updateUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImplRetrievalFail);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UPDATE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UPDATE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAUpdateUsingInvalidInput() {
		userPersistenceDelegator.initialize(invalidUpdateDelegatorInput);
		userPersistenceDelegator.setUserDAO(userJPADAOImpl);
		userPersistenceDelegator.setRoleDAO(roleJPADAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingInvalidInput() {
		userPersistenceDelegator.initialize(invalidUpdateDelegatorInput);
		userPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method setting up UserPersistenceDelegator inputs
	 */
	private static void setUpUserPersistenceDelegatorInputs() {
		// DelegatorInput for user entity adding
		addUserDelegatorInput = new DelegatorInput();
		addUserDelegatorInput.setInputObject(users);
		addUserDelegatorInput.setType(DelegatorTypeEnum.ADD);

		addUserDelegatorInputForRoleTesting = new DelegatorInput();
		addUserDelegatorInputForRoleTesting.setInputObject(usersWithRole);
		addUserDelegatorInputForRoleTesting.setType(DelegatorTypeEnum.ADD);

		// DelegatorInput for user entity deletion
		deleteUserDelegatorInput = new DelegatorInput();
		deleteUserDelegatorInput.setInputObject(users);
		deleteUserDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInput for user entity deletion with ID user
		deleteUserDelegatorInputWithId = new DelegatorInput();
		deleteUserDelegatorInputWithId.setInputObject(usersWithId);
		deleteUserDelegatorInputWithId.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInput for user entity read
		readUserDelegatorInput = new DelegatorInput();
		readUserDelegatorInput.setInputObject(roles);
		readUserDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInput for user entity read by Id
		readUserByIdDelegatorInput = new DelegatorInput();
		readUserByIdDelegatorInput.setInputObject(USERNAME);
		readUserByIdDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInput for user entity update
		updateUserDelegatorInput = new DelegatorInput();
		updateUserDelegatorInput.setInputObject(usersWithId);
		updateUserDelegatorInput.setType(DelegatorTypeEnum.UPDATE);
	}

	/**
	 * Method setting up BookPersistenceDelegator instances
	 */
	private static void setUpUserPersistenceDelegators() {
		userPersistenceDelegator = new UserPersistenceDelegator();
	}

	/**
	 * Method setting up Mocks for testing purpose
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 */
	private static void setUpDAOMocks()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		// positive scenario (all calls return values)
		userJPADAOImpl = EasyMock.createNiceMock(UserJPADAOImpl.class);
		EasyMock.expect(userJPADAOImpl.findEntityById(EasyMock.isA(String.class))).andReturn(JPAUserEntity1);
		EasyMock.expect(userJPADAOImpl.getUserEntitiesByRoles(roles)).andReturn(JPAUserEntityList);
		EasyMock.expect(userJPADAOImpl.persistEntity(EasyMock.isA(GAEJPAUserEntity.class))).andReturn(JPAUserEntity1);
		EasyMock.expect(userJPADAOImpl.removeEntity(EasyMock.isA(GAEJPAUserEntity.class))).andReturn(JPAUserEntity1);
		EasyMock.replay(userJPADAOImpl);

		userObjectifyDAOImpl = EasyMock.createNiceMock(UserObjectifyDAOImpl.class);
		EasyMock.expect(userObjectifyDAOImpl.findEntityById(EasyMock.isA(String.class)))
				.andReturn(objectifyUserEntity1);
		EasyMock.expect(userObjectifyDAOImpl.getUserEntitiesByRoles(roles)).andReturn(JPAUserEntityList);
		EasyMock.expect(userObjectifyDAOImpl.persistEntity(EasyMock.isA(GAEObjectifyUserEntity.class)))
				.andReturn(objectifyUserEntity1);
		EasyMock.expect(userObjectifyDAOImpl.removeEntity(EasyMock.isA(GAEObjectifyUserEntity.class)))
				.andReturn(objectifyUserEntity1);
		EasyMock.replay(userObjectifyDAOImpl);

		userJPADAOImplSpecificEntityFound = EasyMock.createNiceMock(UserJPADAOImpl.class);
		EasyMock.expect(userJPADAOImplSpecificEntityFound.findEntityById(EasyMock.isA(String.class)))
				.andReturn(JPAUserEntity1);
		EasyMock.replay(userJPADAOImplSpecificEntityFound);

		userObjectifyDAOImplSpecificEntityFound = EasyMock.createNiceMock(UserObjectifyDAOImpl.class);
		EasyMock.expect(userObjectifyDAOImplSpecificEntityFound.findEntityById(EasyMock.isA(String.class)))
				.andReturn(objectifyUserEntity1);
		EasyMock.replay(userObjectifyDAOImplSpecificEntityFound);

		roleJPADAOImpl = EasyMock.createNiceMock(RoleJPADAOImpl.class);
		EasyMock.expect(roleJPADAOImpl.findEntityById(EasyMock.isA(String.class))).andReturn(JPARoleEntity4);
		EasyMock.expect(roleJPADAOImpl.findSpecificEntity(EasyMock.isA(GAEJPARoleEntity.class)))
				.andReturn(JPARoleEntityList);
		EasyMock.replay(roleJPADAOImpl);

		roleObjectifyDAOImpl = EasyMock.createNiceMock(RoleObjectifyDAOImpl.class);
		EasyMock.replay(roleObjectifyDAOImpl);

		// negative scenario (all calls throw exceptions)
		userJPADAOImplGeneralFail = EasyMock.createNiceMock(UserJPADAOImpl.class);
		EasyMock.expect(userJPADAOImplGeneralFail.persistEntity(EasyMock.isA(GAEJPAUserEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(userJPADAOImplGeneralFail.getUserEntitiesByRoles(EasyMock.isA(Roles.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(userJPADAOImplGeneralFail.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(userJPADAOImplGeneralFail);

		userObjectifyDAOImplGeneralFail = EasyMock.createNiceMock(UserObjectifyDAOImpl.class);
		EasyMock.expect(userObjectifyDAOImplGeneralFail.persistEntity(EasyMock.isA(GAEObjectifyUserEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(userObjectifyDAOImplGeneralFail.getUserEntitiesByRoles(EasyMock.isA(Roles.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(userObjectifyDAOImplGeneralFail.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(userObjectifyDAOImplGeneralFail);

		// negative scenario mock for UserDAO (removeEntity() call fails)
		userJPADAOImplDeletionFail = EasyMock.createNiceMock(UserJPADAOImpl.class);
		EasyMock.expect(userJPADAOImplDeletionFail.findEntityById(EasyMock.isA(String.class)))
				.andReturn(JPAUserEntity1);
		EasyMock.expect(userJPADAOImplDeletionFail.removeEntity(EasyMock.isA(GAEJPAUserEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.replay(userJPADAOImplDeletionFail);

		userObjectifyDAOImplDeletionFail = EasyMock.createNiceMock(UserObjectifyDAOImpl.class);
		EasyMock.expect(userObjectifyDAOImplDeletionFail.findEntityById(EasyMock.isA(String.class)))
				.andReturn(objectifyUserEntity1);
		EasyMock.expect(userObjectifyDAOImplDeletionFail.removeEntity(EasyMock.isA(GAEObjectifyUserEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.replay(userObjectifyDAOImplDeletionFail);

		// negative scenario. Entity retrieval fails
		userJPADAOImplRetrievalFail = EasyMock.createNiceMock(UserJPADAOImpl.class);
		EasyMock.expect(userJPADAOImplRetrievalFail.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException()).times(2);
		EasyMock.replay(userJPADAOImplRetrievalFail);

		userObjectifyDAOImplRetrievalFail = EasyMock.createNiceMock(UserObjectifyDAOImpl.class);
		EasyMock.expect(userObjectifyDAOImplRetrievalFail.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException()).times(2);
		EasyMock.replay(userObjectifyDAOImplRetrievalFail);

		// positive scenario. No Users found
		userJPADAOImplNoFoundUsers = EasyMock.createNiceMock(UserJPADAOImpl.class);
		EasyMock.expect(userJPADAOImplNoFoundUsers.findSpecificEntity(EasyMock.isA(GAEJPAUserEntity.class)))
				.andReturn(JPAUserEntityEmptyList);
		EasyMock.expect(userJPADAOImplNoFoundUsers.findEntityById(EasyMock.isA(String.class))).andReturn(null);
		EasyMock.replay(userJPADAOImplNoFoundUsers);

		userObjectifyDAOImplNoFoundUsers = EasyMock.createNiceMock(UserObjectifyDAOImpl.class);
		EasyMock.expect(userObjectifyDAOImplNoFoundUsers.findSpecificEntity(EasyMock.isA(GAEObjectifyUserEntity.class)))
				.andReturn(objectifyUserEntityEmptyList);
		EasyMock.expect(userObjectifyDAOImplNoFoundUsers.findEntityById(EasyMock.isA(String.class))).andReturn(null);
		EasyMock.replay(userObjectifyDAOImplNoFoundUsers);

		// Positive scenario (null returned after user retrieval)
		userJPADAOImplNullUsers = EasyMock.createNiceMock(UserJPADAOImpl.class);
		EasyMock.expect(userJPADAOImplNullUsers.findEntityById(EasyMock.isA(String.class))).andReturn(null);
		EasyMock.replay(userJPADAOImplNullUsers);

		userObjectifyDAOImplNullUsers = EasyMock.createNiceMock(UserObjectifyDAOImpl.class);
		EasyMock.expect(userObjectifyDAOImplNullUsers.findEntityById(EasyMock.isA(String.class))).andReturn(null);
		EasyMock.replay(userObjectifyDAOImplNullUsers);

		// Positive scenario. No roles found
		roleJPADAOImplNoFoundRoles = EasyMock.createNiceMock(RoleJPADAOImpl.class);
		EasyMock.expect(roleJPADAOImplNoFoundRoles.findSpecificEntity(EasyMock.isA(GAEJPARoleEntity.class)))
				.andReturn(JPARoleEntityEmptyList);
		EasyMock.replay(roleJPADAOImplNoFoundRoles);

		roleObjectifyDAOImplNoFoundRoles = EasyMock.createNiceMock(RoleObjectifyDAOImpl.class);
		EasyMock.replay(roleObjectifyDAOImplNoFoundRoles);

		// negative scenario. role retrieval fail
		roleJPADAOImplRetrievalFail = EasyMock.createNiceMock(RoleJPADAOImpl.class);
		EasyMock.expect(roleJPADAOImplRetrievalFail.findSpecificEntity(EasyMock.isA(GAEJPARoleEntity.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(roleJPADAOImplRetrievalFail);

		roleObjectifyDAOImplRetrievalFail = EasyMock.createNiceMock(RoleObjectifyDAOImpl.class);
		EasyMock.replay(roleObjectifyDAOImplRetrievalFail);

		// negative scenario. role persistence fail
		roleJPADAOImplPersistenceFail = EasyMock.createNiceMock(RoleJPADAOImpl.class);
		EasyMock.expect(roleJPADAOImplPersistenceFail.findSpecificEntity(EasyMock.isA(GAEJPARoleEntity.class)))
				.andReturn(JPARoleEntityEmptyList);
		EasyMock.expect(roleJPADAOImplPersistenceFail.persistEntity(EasyMock.isA(GAEJPARoleEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.replay(roleJPADAOImplPersistenceFail);

		roleObjectifyDAOImplPersistenceFail = EasyMock.createNiceMock(RoleObjectifyDAOImpl.class);
		EasyMock.replay(roleObjectifyDAOImplPersistenceFail);
	}
}
