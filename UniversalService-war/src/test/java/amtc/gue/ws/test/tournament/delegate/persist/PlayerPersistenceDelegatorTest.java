package amtc.gue.ws.test.tournament.delegate.persist;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.input.DelegatorInput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.test.base.delegate.persist.IBasePersistenceDelegatorTest;
import amtc.gue.ws.test.tournament.TournamentTest;
import amtc.gue.ws.tournament.delegate.persist.PlayerPersistenceDelegator;
import amtc.gue.ws.tournament.persistence.dao.player.PlayerDAO;
import amtc.gue.ws.tournament.persistence.dao.player.jpa.PlayerJPADAOImpl;
import amtc.gue.ws.tournament.persistence.dao.player.objectify.PlayerObjectifyDAOImpl;
import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;
import amtc.gue.ws.tournament.persistence.model.player.jpa.GAEJPAPlayerEntity;
import amtc.gue.ws.tournament.persistence.model.player.objectify.GAEObjectifyPlayerEntity;
import amtc.gue.ws.tournament.util.TournamentServiceErrorConstants;

/**
 * Testclass for the PlayerPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerPersistenceDelegatorTest extends TournamentTest implements IBasePersistenceDelegatorTest {
	private static DelegatorInput addPlayerDelegatorInput;
	private static DelegatorInput deletePlayerDelegatorInput;
	private static DelegatorInput deletePlayerDelegatorInputWithId;
	private static DelegatorInput readPlayerDelegatorInput;
	private static DelegatorInput readPlayerByIdDelegatorInput;
	private static DelegatorInput updatePlayerDelegatorInput;

	private static PlayerPersistenceDelegator playerPersistenceDelegator;

	private static PlayerDAO<GAEPlayerEntity, GAEJPAPlayerEntity, String> playerJPADAOImpl;
	private static PlayerDAO<GAEPlayerEntity, GAEJPAPlayerEntity, String> playerJPADAOImplGeneralFail;
	private static PlayerDAO<GAEPlayerEntity, GAEJPAPlayerEntity, String> playerJPADAOImplDeletionFail;
	private static PlayerDAO<GAEPlayerEntity, GAEJPAPlayerEntity, String> playerJPADAOImplRetrievalFail;
	private static PlayerDAO<GAEPlayerEntity, GAEJPAPlayerEntity, String> playerJPADAOImplNoFoundPlayers;
	private static PlayerDAO<GAEPlayerEntity, GAEJPAPlayerEntity, String> playerJPADAOImplNullPlayers;

	private static PlayerDAO<GAEPlayerEntity, GAEObjectifyPlayerEntity, String> playerObjectifyDAOImpl;
	private static PlayerDAO<GAEPlayerEntity, GAEObjectifyPlayerEntity, String> playerObjectifyDAOImplGeneralFail;
	private static PlayerDAO<GAEPlayerEntity, GAEObjectifyPlayerEntity, String> playerObjectifyDAOImplDeletionFail;
	private static PlayerDAO<GAEPlayerEntity, GAEObjectifyPlayerEntity, String> playerObjectifyDAOImplRetrievalFail;
	private static PlayerDAO<GAEPlayerEntity, GAEObjectifyPlayerEntity, String> playerObjectifyDAOImplNoFoundPlayers;
	private static PlayerDAO<GAEPlayerEntity, GAEObjectifyPlayerEntity, String> playerObjectifyDAOImplNullPlayers;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRemovalException, EntityRetrievalException, EntityPersistenceException {
		setUpBasicTournamentEnvironment();
		setUpDelegatorInputs();
		setUpTournamentPersistenceDelegatorInputs();
		setUpTournamentPersistenceDelegators();
		setUpDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(playerJPADAOImpl);
		EasyMock.verify(playerJPADAOImplGeneralFail);
		EasyMock.verify(playerJPADAOImplDeletionFail);
		EasyMock.verify(playerJPADAOImplRetrievalFail);
		EasyMock.verify(playerJPADAOImplNoFoundPlayers);
		EasyMock.verify(playerJPADAOImplNullPlayers);
		EasyMock.verify(playerObjectifyDAOImpl);
		EasyMock.verify(playerObjectifyDAOImplGeneralFail);
		EasyMock.verify(playerObjectifyDAOImplDeletionFail);
		EasyMock.verify(playerObjectifyDAOImplRetrievalFail);
		EasyMock.verify(playerObjectifyDAOImplNoFoundPlayers);
		EasyMock.verify(playerObjectifyDAOImplNullPlayers);
	}

	@Override
	@Test
	public void testDelegateUsingNullInput() {
		playerPersistenceDelegator.initialize(nullDelegatorInput);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateUsingUnrecognizedInputType() {
		playerPersistenceDelegator.initialize(unrecognizedDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAAddUsingCorrectInput() {
		playerPersistenceDelegator.initialize(addPlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImpl);
		playerPersistenceDelegator.setTournamentEntityMapper(JPATournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(
				delegatorOutput.getStatusMessage().startsWith(TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingCorrectInput() {
		playerPersistenceDelegator.initialize(addPlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImpl);
		playerPersistenceDelegator.setTournamentEntityMapper(objectifyTournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(
				delegatorOutput.getStatusMessage().startsWith(TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Override
	@Test
	public void testDelegateJPAAddUsingIncorrectDAOSetup() {
		playerPersistenceDelegator.initialize(addPlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImplGeneralFail);
		playerPersistenceDelegator.setTournamentEntityMapper(JPATournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.ADD_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingIncorrectDAOSetup() {
		playerPersistenceDelegator.initialize(addPlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImplGeneralFail);
		playerPersistenceDelegator.setTournamentEntityMapper(objectifyTournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.ADD_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAAddUsingInvalidInput() {
		playerPersistenceDelegator.initialize(invalidAddDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImpl);
		playerPersistenceDelegator.setTournamentEntityMapper(JPATournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingInvalidInput() {
		playerPersistenceDelegator.initialize(invalidAddDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImpl);
		playerPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingCorrectIdInput() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInputWithId);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImpl);
		playerPersistenceDelegator.setTournamentEntityMapper(JPATournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingCorrectIdInput() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInputWithId);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImpl);
		playerPersistenceDelegator.setCurrentUser(null);
		playerPersistenceDelegator.setTournamentEntityMapper(JPATournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingNonExistingObjects() {
		// testing behavior when the objects that should be removed were not
		// found
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImplNoFoundPlayers);
		playerPersistenceDelegator.setTournamentEntityMapper(JPATournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNonExistingObjects() {
		// testing behavior when the objects that should be removed were not
		// found
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImplNoFoundPlayers);
		playerPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingNullObjects() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImplNullPlayers);
		playerPersistenceDelegator.setTournamentEntityMapper(JPATournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNullObjects() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImplNullPlayers);
		playerPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingIncorrectDAOSetup() {
		// testing behavior when an exception occurred on entity removal
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImplGeneralFail);
		playerPersistenceDelegator.setTournamentEntityMapper(JPATournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingIncorrectDAOSetup() {
		// testing behavior when an exception occurred on entity removal
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImplGeneralFail);
		playerPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteDeletionFail() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInputWithId);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImplDeletionFail);
		playerPersistenceDelegator.setTournamentEntityMapper(JPATournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteDeletionFail() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInputWithId);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImplDeletionFail);
		playerPersistenceDelegator.setCurrentUser(null);
		playerPersistenceDelegator.setTournamentEntityMapper(objectifyTournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteRetrievalFail() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInputWithId);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImplRetrievalFail);
		playerPersistenceDelegator.setTournamentEntityMapper(JPATournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteRetrievalFail() {
		playerPersistenceDelegator.initialize(deletePlayerDelegatorInputWithId);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImplRetrievalFail);
		playerPersistenceDelegator.setCurrentUser(null);
		playerPersistenceDelegator.setTournamentEntityMapper(objectifyTournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingInvalidInput() {
		playerPersistenceDelegator.initialize(invalidDeleteDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImpl);
		playerPersistenceDelegator.setTournamentEntityMapper(JPATournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingInvalidInput() {
		playerPersistenceDelegator.initialize(invalidDeleteDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImpl);
		playerPersistenceDelegator.setTournamentEntityMapper(objectifyTournamentEntityMapper);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAReadUsingCorrectInput() {
		playerPersistenceDelegator.initialize(readPlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.RETRIEVE_PLAYER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingCorrectInput() {
		playerPersistenceDelegator.initialize(readPlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.RETRIEVE_PLAYER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAReadUsingIncorrectDAOSetup() {
		playerPersistenceDelegator.initialize(readPlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.RETRIEVE_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingIncorrectDAOSetup() {
		playerPersistenceDelegator.initialize(readPlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.RETRIEVE_PLAYER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAReadUsingInvalidInput() {
		// no invalid Input for PlayerPersistenceDelegator
		playerPersistenceDelegator.initialize(invalidReadDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.RETRIEVE_PLAYER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingInvalidInput() {
		// no invalid Input for PlayerPersistenceDelegator
		playerPersistenceDelegator.initialize(invalidReadDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(TournamentServiceErrorConstants.RETRIEVE_PLAYER_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAUpdateUsingCorrectInput() {
		// update for playerdelegator not implemented
		playerPersistenceDelegator.initialize(updatePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingCorrectInput() {
		// update for playerdelegator not implemented
		playerPersistenceDelegator.initialize(updatePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAUpdateUsingIncorrectDAOSetup() {
		// update for playerdelegator not implemented
		playerPersistenceDelegator.initialize(updatePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingIncorrectDAOSetup() {
		// update for playerdelegator not implemented
		playerPersistenceDelegator.initialize(updatePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAUpdateUsingInvalidInput() {
		// update for playerdelegator not implemented
		playerPersistenceDelegator.initialize(invalidUpdateDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerJPADAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingInvalidInput() {
		// update for playerdelegator not implemented
		playerPersistenceDelegator.initialize(updatePlayerDelegatorInput);
		playerPersistenceDelegator.setPlayerDAO(playerObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = playerPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method setting up TournamentPersistenceDelegator inputs
	 */
	private static void setUpTournamentPersistenceDelegatorInputs() {
		// DelegatorInput for Player entity adding
		addPlayerDelegatorInput = new DelegatorInput();
		addPlayerDelegatorInput.setInputObject(players);
		addPlayerDelegatorInput.setType(DelegatorTypeEnum.ADD);

		// DelegatorInput for Player entity deletion
		deletePlayerDelegatorInput = new DelegatorInput();
		deletePlayerDelegatorInput.setInputObject(players);
		deletePlayerDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInput for Player entity deletion with ID Player
		deletePlayerDelegatorInputWithId = new DelegatorInput();
		deletePlayerDelegatorInputWithId.setInputObject(playersWithId);
		deletePlayerDelegatorInputWithId.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInput for Player entity read
		readPlayerDelegatorInput = new DelegatorInput();
		readPlayerDelegatorInput.setInputObject(null);
		readPlayerDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInput for Player entity read by Id
		readPlayerByIdDelegatorInput = new DelegatorInput();
		readPlayerByIdDelegatorInput.setInputObject(PLAYERKEY);
		readPlayerByIdDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInput for Player entity update
		updatePlayerDelegatorInput = new DelegatorInput();
		updatePlayerDelegatorInput.setInputObject(playersWithId);
		updatePlayerDelegatorInput.setType(DelegatorTypeEnum.UPDATE);
	}

	/**
	 * Method setting up PlayerPersistenceDelegators
	 */
	private static void setUpTournamentPersistenceDelegators() {
		playerPersistenceDelegator = new PlayerPersistenceDelegator();
	}

	private static void setUpDAOMocks()
			throws EntityRemovalException, EntityRetrievalException, EntityPersistenceException {
		// positive scenario (all calls return values)
		playerJPADAOImpl = EasyMock.createNiceMock(PlayerJPADAOImpl.class);
		EasyMock.expect(playerJPADAOImpl.persistEntity(EasyMock.isA(GAEJPAPlayerEntity.class)))
				.andReturn(JPAPlayerEntity1).times(2);
		EasyMock.expect(playerJPADAOImpl.findEntityById(EasyMock.isA(String.class))).andReturn(JPAPlayerEntity1);
		EasyMock.expect(playerJPADAOImpl.removeEntity(EasyMock.isA(GAEJPAPlayerEntity.class)))
				.andReturn(JPAPlayerEntity1);
		EasyMock.expect(playerJPADAOImpl.findAllEntities()).andReturn(JPAPlayerEntityEmptyList);
		EasyMock.replay(playerJPADAOImpl);

		playerObjectifyDAOImpl = EasyMock.createNiceMock(PlayerObjectifyDAOImpl.class);
		EasyMock.expect(playerObjectifyDAOImpl.persistEntity(EasyMock.isA(GAEObjectifyPlayerEntity.class)))
				.andReturn(objectifyPlayerEntity1).times(2);
		EasyMock.expect(playerObjectifyDAOImpl.findEntityById(EasyMock.isA(String.class)))
				.andReturn(objectifyPlayerEntity1);
		EasyMock.expect(playerObjectifyDAOImpl.removeEntity(EasyMock.isA(GAEObjectifyPlayerEntity.class)))
				.andReturn(objectifyPlayerEntity1);
		EasyMock.expect(playerObjectifyDAOImpl.findAllEntities()).andReturn(objectifyPlayerEntityList);
		EasyMock.replay(playerObjectifyDAOImpl);

		// positive scenario. No Players found
		playerJPADAOImplNoFoundPlayers = EasyMock.createNiceMock(PlayerJPADAOImpl.class);
		EasyMock.expect(playerJPADAOImplNoFoundPlayers.findSpecificEntity(EasyMock.isA(GAEJPAPlayerEntity.class)))
				.andReturn(JPAPlayerEntityEmptyList);
		EasyMock.replay(playerJPADAOImplNoFoundPlayers);

		playerObjectifyDAOImplNoFoundPlayers = EasyMock.createNiceMock(PlayerObjectifyDAOImpl.class);
		EasyMock.expect(
				playerObjectifyDAOImplNoFoundPlayers.findSpecificEntity(EasyMock.isA(GAEObjectifyPlayerEntity.class)))
				.andReturn(objectifyPlayerEntityEmptyList);
		EasyMock.replay(playerObjectifyDAOImplNoFoundPlayers);

		// Positive Scenario (null returned after player retrieval)
		playerJPADAOImplNullPlayers = EasyMock.createNiceMock(PlayerJPADAOImpl.class);
		EasyMock.expect(playerJPADAOImplNullPlayers.findSpecificEntity(EasyMock.isA(GAEJPAPlayerEntity.class)))
				.andReturn(null);
		EasyMock.replay(playerJPADAOImplNullPlayers);

		playerObjectifyDAOImplNullPlayers = EasyMock.createNiceMock(PlayerObjectifyDAOImpl.class);
		EasyMock.expect(
				playerObjectifyDAOImplNullPlayers.findSpecificEntity(EasyMock.isA(GAEObjectifyPlayerEntity.class)))
				.andReturn(null);
		EasyMock.replay(playerObjectifyDAOImplNullPlayers);

		// negative scenario (all calls throw exceptions)
		playerJPADAOImplGeneralFail = EasyMock.createNiceMock(PlayerJPADAOImpl.class);
		EasyMock.expect(playerJPADAOImplGeneralFail.persistEntity(EasyMock.isA(GAEJPAPlayerEntity.class)))
				.andThrow(new EntityPersistenceException()).times(2);
		EasyMock.expect(playerJPADAOImplGeneralFail.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.replay(playerJPADAOImplGeneralFail);

		playerObjectifyDAOImplGeneralFail = EasyMock.createNiceMock(PlayerObjectifyDAOImpl.class);
		EasyMock.expect(playerObjectifyDAOImplGeneralFail.persistEntity(EasyMock.isA(GAEObjectifyPlayerEntity.class)))
				.andThrow(new EntityPersistenceException()).times(2);
		EasyMock.expect(playerObjectifyDAOImplGeneralFail.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.replay(playerObjectifyDAOImplGeneralFail);

		// negative scenario mock for PlayerDAO (removeEntity() call fails)
		playerJPADAOImplDeletionFail = EasyMock.createNiceMock(PlayerJPADAOImpl.class);
		EasyMock.expect(playerJPADAOImplDeletionFail.findEntityById(EasyMock.isA(String.class)))
				.andReturn(JPAPlayerEntity1);
		EasyMock.expect(playerJPADAOImplDeletionFail.removeEntity(EasyMock.isA(GAEJPAPlayerEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.replay(playerJPADAOImplDeletionFail);

		playerObjectifyDAOImplDeletionFail = EasyMock.createNiceMock(PlayerObjectifyDAOImpl.class);
		EasyMock.expect(playerObjectifyDAOImplDeletionFail.findEntityById(EasyMock.isA(String.class)))
				.andReturn(objectifyPlayerEntity1);
		EasyMock.expect(playerObjectifyDAOImplDeletionFail.removeEntity(EasyMock.isA(GAEObjectifyPlayerEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.replay(playerObjectifyDAOImplDeletionFail);

		// negative scenario mock for PlayerDAO (getEntitiyById() call fails)
		playerJPADAOImplRetrievalFail = EasyMock.createNiceMock(PlayerJPADAOImpl.class);
		EasyMock.expect(playerJPADAOImplRetrievalFail.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(playerJPADAOImplRetrievalFail);

		playerObjectifyDAOImplRetrievalFail = EasyMock.createNiceMock(PlayerObjectifyDAOImpl.class);
		EasyMock.expect(playerObjectifyDAOImplRetrievalFail.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(playerObjectifyDAOImplRetrievalFail);
	}
}
