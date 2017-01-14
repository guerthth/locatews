package amtc.gue.ws.test.tournament.delegate.persist;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.IDelegatorOutput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.test.tournament.delegate.TournamentServiceDelegatorTest;
import amtc.gue.ws.tournament.persistence.dao.player.PlayerDAO;
import amtc.gue.ws.tournament.persistence.model.GAEJPAPlayerEntity;
import amtc.gue.ws.tournament.util.TournamentServiceErrorConstants;

/**
 * Testclass for the PlayerPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerPersistenceDelegatorTest extends
		TournamentServiceDelegatorTest {

	private static PlayerDAO playerDAOImpl;
	private static PlayerDAO playerDAOImplFail;
	private static PlayerDAO playerDAOImplNoFoundPlayers;
	private static PlayerDAO playerDAOImplNullPlayers;
	private static PlayerDAO playerDAOImplDeletionFail;
	private static PlayerDAO playerDAOImplRetrievalFail;

	@BeforeClass
	public static void initialSetup() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {
		oneTimeInitialSetup();
		setUpPlayerPersistenceDAOMocks();
	}

	@AfterClass
	public static void tearDown() {
		EasyMock.verify(playerDAOImpl);
		EasyMock.verify(playerDAOImplFail);
		EasyMock.verify(playerDAOImplNoFoundPlayers);
	}

	@Override
	public void testDelegateUsingNullInput() {
		playerPersistenceDelegator.initialize(nullDelegatorInput);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateUsingUnrecognizedInputType() {
		playerPersistenceDelegator.initialize(unrecognizedDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateAddUsingCorrectInput() {
		playerPersistenceDelegator.initialize(addPlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(
				TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Override
	public void testDelegateAddUsingIncorrectDAOSetup() {
		playerPersistenceDelegator.initialize(addPlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImplFail);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(TournamentServiceErrorConstants.ADD_PLAYER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateAddUsingInvalidInput() {
		playerPersistenceDelegator.initialize(invalidAddDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateDeleteUsingCorrectIdInput() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInputWithId);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(
				TournamentServiceErrorConstants.DELETE_PLAYER_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateDeleteUsingNonExistingObjects() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImplNoFoundPlayers);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(
				TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingNullObjects() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImplNullPlayers);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(
				TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingIncorrectDAOSetup() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImplFail);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(
				TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingInvalidInput() {
		playerPersistenceDelegator.initialize(invalidDeleteDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteDeletionFail() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInputWithId);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImplDeletionFail);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(
				TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteRetrievalFail() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInputWithId);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImplRetrievalFail);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(
				TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateReadUsingCorrectInput() {
		playerPersistenceDelegator.initialize(readPlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(
				TournamentServiceErrorConstants.RETRIEVE_PLAYER_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateReadUsingIncorrectDAOSetup() {
		playerPersistenceDelegator.initialize(readPlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerDAOImplFail);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator
				.delegate();
		assertEquals(
				TournamentServiceErrorConstants.RETRIEVE_PLAYER_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(
				TournamentServiceErrorConstants.RETRIEVE_PLAYER_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateReadUsingInvalidInput() {
		// there are no invalid inputs
	}

	/**
	 * Method setting up DAO mocks
	 * @throws EntityPersistenceException when error occurs while persisting entity
	 * @throws EntityRetrievalException when error occurs while retrieving entity
	 * @throws EntityRemovalException when error occurs while removing entity
	 */
	private static void setUpPlayerPersistenceDAOMocks()
			throws EntityPersistenceException, EntityRetrievalException,
			EntityRemovalException {
		// positive scenario (all calls return values)
		playerDAOImpl = EasyMock.createNiceMock(PlayerDAO.class);
		EasyMock.expect(
				playerDAOImpl.persistEntity(EasyMock
						.isA(GAEJPAPlayerEntity.class))).andReturn(
				retrievedPlayerEntity);
		EasyMock.expect(
				playerDAOImpl.findEntityById(EasyMock.isA(String.class)))
				.andReturn(retrievedPlayerEntity);
		EasyMock.expect(
				playerDAOImpl.removeEntity(EasyMock
						.isA(GAEJPAPlayerEntity.class))).andReturn(
				removedPlayerEntity);
		EasyMock.expect(playerDAOImpl.findAllEntities()).andReturn(
				emptyPlayerEntityList);
		EasyMock.replay(playerDAOImpl);

		// positive scenario. No Players found
		playerDAOImplNoFoundPlayers = EasyMock.createNiceMock(PlayerDAO.class);
		EasyMock.expect(
				playerDAOImplNoFoundPlayers.findSpecificEntity(EasyMock
						.isA(GAEJPAPlayerEntity.class))).andReturn(
				emptyPlayerEntityList);
		EasyMock.replay(playerDAOImplNoFoundPlayers);

		// Positive Scenario (null returned after player retrieval)
		playerDAOImplNullPlayers = EasyMock.createNiceMock(PlayerDAO.class);
		EasyMock.expect(
				playerDAOImplNullPlayers.findSpecificEntity(EasyMock
						.isA(GAEJPAPlayerEntity.class))).andReturn(null);
		EasyMock.replay(playerDAOImplNullPlayers);

		// negative scenario (all calls throw exceptions)
		playerDAOImplFail = EasyMock.createNiceMock(PlayerDAO.class);
		EasyMock.expect(
				playerDAOImplFail.persistEntity(EasyMock
						.isA(GAEJPAPlayerEntity.class)))
				.andThrow(new EntityPersistenceException()).times(2);
		EasyMock.expect(playerDAOImplFail.findAllEntities()).andThrow(
				new EntityRetrievalException());
		EasyMock.replay(playerDAOImplFail);

		// negative scenario mock for PlayerDAO (removeEntity() call fails)
		playerDAOImplDeletionFail = EasyMock.createNiceMock(PlayerDAO.class);
		EasyMock.expect(
				playerDAOImplDeletionFail.findEntityById(EasyMock
						.isA(String.class))).andReturn(retrievedPlayerEntity);
		EasyMock.expect(
				playerDAOImplDeletionFail.removeEntity(EasyMock
						.isA(GAEJPAPlayerEntity.class))).andThrow(
				new EntityRemovalException());
		EasyMock.replay(playerDAOImplDeletionFail);

		// negative scenario mock for PlayerDAO (getEntitiyById() call fails)
		playerDAOImplRetrievalFail = EasyMock.createNiceMock(PlayerDAO.class);
		EasyMock.expect(
				playerDAOImplRetrievalFail.findEntityById(EasyMock
						.isA(String.class))).andThrow(
				new EntityRetrievalException());
		EasyMock.replay(playerDAOImplRetrievalFail);
	}
}
