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

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.persistence.dao.role.RoleDAO;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.model.GAEJPARoleEntity;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.tournament.util.TournamentServiceErrorConstants;

/**
 * Testclass for the UserPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserPersistenceDelegatorTest extends BasePersistenceDelegatorTest {
	private static UserDAO userDAOImpl;
	private static UserDAO userDAOImplFail;
	private static UserDAO userDAOImplNoFoundUsers;
	private static UserDAO userDAOImplNullUsers;
	private static UserDAO userDAOImplDeletionFail;
	private static UserDAO userDAOImplRetrievalFail;
	private static RoleDAO roleDAOImpl;
	private static RoleDAO roleDAONoRolesImpl;
	private static RoleDAO roleDAOImplRetrievalFail;
	private static RoleDAO roleDAOImplPersistenceFail;

	@BeforeClass
	public static void initialSetup() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {
		setUpPersistenceDelegatorInputs();
		oneTimeInitialSetup();
		setUpUserPersistenceDAOMocks();
	}

	@AfterClass
	public static void tearDown() {
		EasyMock.verify(userDAOImpl);
		EasyMock.verify(userDAOImplFail);
		EasyMock.verify(userDAOImplNoFoundUsers);
		EasyMock.verify(roleDAOImpl);
		EasyMock.verify(roleDAONoRolesImpl);
		EasyMock.verify(roleDAOImplRetrievalFail);
		EasyMock.verify(roleDAOImplPersistenceFail);
	}

	@Override
	public void testDelegateUsingNullInput() {
		userPersistenceDelegator.initialize(nullDelegatorInput);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateUsingUnrecognizedInputType() {
		userPersistenceDelegator.initialize(unrecognizedDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateAddUsingCorrectInput() {
		userPersistenceDelegator.initialize(addUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(
				ErrorConstants.ADD_USER_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Override
	public void testDelegateAddUsingIncorrectDAOSetup() {
		userPersistenceDelegator.initialize(addUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImplFail);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.ADD_PLAYER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateAddUsingInvalidInput() {
		userPersistenceDelegator.initialize(invalidAddDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateDeleteUsingCorrectIdInput() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(
				TournamentServiceErrorConstants.DELETE_PLAYER_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateDeleteUsingNonExistingObjects() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImplNoFoundUsers);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingNullObjects() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImplNullUsers);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingIncorrectDAOSetup() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImplFail);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteDeletionFail() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userDAOImplDeletionFail);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteRetrievalFail() {
		userPersistenceDelegator.initialize(deleteUserDelegatorInputWithId);
		userPersistenceDelegator.setUserDAO(userDAOImplRetrievalFail);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_USER_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingInvalidInput() {
		userPersistenceDelegator.initialize(invalidDeleteDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateReadUsingCorrectInput() {
		userPersistenceDelegator.initialize(readUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateReadUsingIncorrectDAOSetup() {
		userPersistenceDelegator.initialize(readUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImplFail);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateReadUsingInvalidInput() {
		userPersistenceDelegator.initialize(invalidReadDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
	}

	// additional userpersistencedelegator specific tests
	@Test
	public void testDelegateAddUsingInputWithNoFoundRoles() {
		userPersistenceDelegator.initialize(addUserDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleDAONoRolesImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(
				ErrorConstants.ADD_USER_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Test
	public void testDelegateAddUsingCorrectInputAndTagRetrievalFail() {
		userPersistenceDelegator
				.initialize(addUserDelegatorInputForRoleTesting);
		userPersistenceDelegator.setUserDAO(userDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleDAOImplRetrievalFail);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateAddUsingCorrectInputAndRolePersistenceFail() {
		userPersistenceDelegator
				.initialize(addUserDelegatorInputForRoleTesting);
		userPersistenceDelegator.setUserDAO(userDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleDAOImplPersistenceFail);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateAddUsingCorrectInputAddingRole() {
		userPersistenceDelegator
				.initialize(addUserDelegatorInputForRoleTesting);
		userPersistenceDelegator.setUserDAO(userDAOImpl);
		userPersistenceDelegator.setRoleDAO(roleDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateReadUsingSearchById() {
		userPersistenceDelegator.initialize(readUserByIdDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImpl);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateReadUsingSearchByIdRetrievalFail() {
		userPersistenceDelegator.initialize(readUserByIdDelegatorInput);
		userPersistenceDelegator.setUserDAO(userDAOImplFail);
		IDelegatorOutput delegatorOutput = userPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
	}

	/**
	 * Method setting up DAO mocks
	 * 
	 * @throws EntityPersistenceException
	 *             when error occurs while persisting entity
	 * @throws EntityRetrievalException
	 *             when error occurs while retrieving entity
	 * @throws EntityRemovalException
	 *             when error occurs while removing entity
	 */
	private static void setUpUserPersistenceDAOMocks()
			throws EntityPersistenceException, EntityRetrievalException,
			EntityRemovalException {
		// positive scenario (all calls return values)
		userDAOImpl = EasyMock.createNiceMock(UserDAO.class);
		EasyMock.expect(
				userDAOImpl.persistEntity(EasyMock.isA(GAEJPAUserEntity.class)))
				.andReturn(retrievedUserEntity);
		EasyMock.expect(userDAOImpl.getUserEntitiesByRoles(roles)).andReturn(
				retrievedUserEntityList);
		EasyMock.expect(userDAOImpl.findEntityById(EasyMock.isA(String.class)))
				.andReturn(retrievedUserEntity).times(2);
		EasyMock.expect(
				userDAOImpl.removeEntity(EasyMock.isA(GAEJPAUserEntity.class)))
				.andReturn(removedUserEntity);
		EasyMock.replay(userDAOImpl);

		// positive scenario. No Users found
		userDAOImplNoFoundUsers = EasyMock.createNiceMock(UserDAO.class);
		EasyMock.expect(
				userDAOImplNoFoundUsers.findSpecificEntity(EasyMock
						.isA(GAEJPAUserEntity.class))).andReturn(
				emptyUserEntityList);
		EasyMock.replay(userDAOImplNoFoundUsers);

		// Positive Scenario (null returned after user retrieval)
		userDAOImplNullUsers = EasyMock.createNiceMock(UserDAO.class);
		EasyMock.expect(
				userDAOImplNullUsers.findSpecificEntity(EasyMock
						.isA(GAEJPAUserEntity.class))).andReturn(null);
		EasyMock.replay(userDAOImplNullUsers);

		// negative scenario (all calls throw exceptions)
		userDAOImplFail = EasyMock.createNiceMock(UserDAO.class);
		EasyMock.expect(
				userDAOImplFail.persistEntity(EasyMock
						.isA(GAEJPAUserEntity.class))).andThrow(
				new EntityPersistenceException());
		EasyMock.expect(
				userDAOImplFail.getUserEntitiesByRoles(EasyMock
						.isA(Roles.class))).andThrow(
				new EntityRetrievalException());
		EasyMock.expect(
				userDAOImplFail.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(userDAOImplFail);

		// negative scenario mock for UserDAO (removeEntity() call fails)
		userDAOImplDeletionFail = EasyMock.createNiceMock(UserDAO.class);
		EasyMock.expect(
				userDAOImplDeletionFail.findEntityById(EasyMock
						.isA(String.class))).andReturn(retrievedUserEntity);
		EasyMock.expect(
				userDAOImplDeletionFail.removeEntity(EasyMock
						.isA(GAEJPAUserEntity.class))).andThrow(
				new EntityRemovalException());
		EasyMock.replay(userDAOImplDeletionFail);

		// negative scenario mock for UserDAO (getEntitiyById() call fails)
		userDAOImplRetrievalFail = EasyMock.createNiceMock(UserDAO.class);
		EasyMock.expect(
				userDAOImplRetrievalFail.findEntityById(EasyMock
						.isA(String.class))).andThrow(
				new EntityRetrievalException());
		EasyMock.replay(userDAOImplRetrievalFail);

		// positive scenario mock for RoleDAO (no roles are found)
		roleDAONoRolesImpl = EasyMock.createNiceMock(RoleDAO.class);
		EasyMock.expect(
				roleDAONoRolesImpl.findSpecificEntity(EasyMock
						.isA(GAEJPARoleEntity.class))).andReturn(
				emptyRoleEntityList);
		EasyMock.replay(roleDAONoRolesImpl);

		// Negative scenario mock for RoleDAO (findSpecificEntity() call fails)
		roleDAOImplRetrievalFail = EasyMock.createNiceMock(RoleDAO.class);
		EasyMock.expect(
				roleDAOImplRetrievalFail.findSpecificEntity(EasyMock
						.isA(GAEJPARoleEntity.class))).andThrow(
				new EntityRetrievalException());
		EasyMock.replay(roleDAOImplRetrievalFail);

		// Negative scenario mock for RoleDAO (persistEntity() call fails)
		roleDAOImplPersistenceFail = EasyMock.createNiceMock(RoleDAO.class);
		EasyMock.expect(
				roleDAOImplPersistenceFail.persistEntity(EasyMock
						.isA(GAEJPARoleEntity.class))).andThrow(
				new EntityPersistenceException());
		EasyMock.expect(
				roleDAOImplPersistenceFail.findSpecificEntity(EasyMock
						.isA(GAEJPARoleEntity.class))).andReturn(
				emptyRoleEntityList);
		EasyMock.replay(roleDAOImplPersistenceFail);

		// positive scenario mock for RoleDAO (roles are found)
		roleDAOImpl = EasyMock.createNiceMock(RoleDAO.class);
		EasyMock.expect(
				roleDAOImpl.findSpecificEntity(EasyMock
						.isA(GAEJPARoleEntity.class))).andReturn(
				retrievedRoleEntityList);
		EasyMock.replay(roleDAOImpl);
	}
}
