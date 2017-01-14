package amtc.gue.ws.test.tournament.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;

import amtc.gue.ws.base.delegate.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.output.PersistenceDelegatorOutput;
import amtc.gue.ws.tournament.inout.Player;
import amtc.gue.ws.tournament.inout.Players;
import amtc.gue.ws.tournament.persistence.model.GAEJPAPlayerEntity;
import amtc.gue.ws.tournament.util.TournamentServiceEntityMapper;
import amtc.gue.ws.tournament.util.TournamentServiceErrorConstants;

/**
 * Class holding helper object setup for testing the
 * TournamentService Utility Classes
 *
 * @author Thomas
 *
 */
public abstract class TournamentServiceUtilTest {

	protected static final String TEST_PLAYER_ENTITY_KEY = "testPlayerEntityKey";
	protected static final String TEST_PLAYER_NAME = "testPlayerName";

	protected static final String NULL_PLAYER_ENTITY_JSON = "{}";
	protected static final String NULL_PLAYER_ENTITY_LIST_JSON = "[]";
	protected static String PLAYER_ENTITY_JSON;
	protected static String PLAYER_ENTITY_LIST_JSON;

	protected String EXPECTED_NO_FAILURES_PLAYER_PERSISTENCE_MESSAGE_RESULT;
	protected String EXPECTED_NO_SUCCESSES_PLAYER_PERSISTENCE_MESSAGE_RESULT;
	protected String EXPECTED_NULL_INPUT_PLAYER_PERSISTENCE_MESSAGE_RESULT;
	protected String EXPECTED_PLAYER_REMOVAL_MESSAGE_SIMPLE_RESULT;
	protected String EXPECTED_PLAYER_RETRIEVAL_MESSAGE_SIMPLE_RESULT;

	protected GAEJPAPlayerEntity playerEntity1;
	protected GAEJPAPlayerEntity playerEntity2;
	protected GAEJPAPlayerEntity playerEntity3;

	protected List<GAEJPAPlayerEntity> playerEntityList;
	protected List<GAEJPAPlayerEntity> emptyPlayerEntityList;

	protected Player player1;
	protected Player player2;

	protected Players simplePlayers;
	protected Players emptyPlayers;

	protected IDelegatorOutput playerDelegatorOutput;
	protected IDelegatorOutput unrecognizedPlayerDelegatorOutput;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpJSONStrings();
	}

	@Before
	public void setUp() {
		setupPlayerGAEJPAEntities();
		setupUpPlayers();
		setUpBdOutputs();
		setUpExpectedPlayerPersistStatusMessages();
	}

	/**
	 * Method intitializing some GAEJPAPlayerEntities
	 */
	private void setupPlayerGAEJPAEntities() {
		playerEntity1 = new GAEJPAPlayerEntity();
		playerEntity2 = new GAEJPAPlayerEntity();
		playerEntity2.setKey(TEST_PLAYER_ENTITY_KEY);
		playerEntity2.setPlayerName(TEST_PLAYER_NAME);
		playerEntity3 = new GAEJPAPlayerEntity();
		playerEntity3.setPlayerName(TEST_PLAYER_NAME);

		playerEntityList = new ArrayList<>();
		playerEntityList.add(playerEntity1);
		playerEntityList.add(playerEntity2);
		emptyPlayerEntityList = new ArrayList<>();
	}

	/**
	 * Method initializing some Players
	 */
	private void setupUpPlayers() {
		player1 = new Player();
		player2 = new Player();
		simplePlayers = new Players();
		simplePlayers.getPlayers().add(player1);
		simplePlayers.getPlayers().add(player2);
		emptyPlayers = new Players();
	}

	/**
	 * Method initializing some DelegatorOutputs
	 */
	private void setUpBdOutputs() {
		playerDelegatorOutput = new PersistenceDelegatorOutput();
		playerDelegatorOutput.setOutputObject(simplePlayers);
		unrecognizedPlayerDelegatorOutput = new PersistenceDelegatorOutput();
		unrecognizedPlayerDelegatorOutput.setOutputObject(null);
	}

	/**
	 * Method initializing some JSON Strings
	 */
	private static void setUpJSONStrings() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id: null, ");
		sb.append("playerName: null");
		sb.append("}");
		PLAYER_ENTITY_JSON = sb.toString();
		sb.setLength(0);
		sb.append("[");
		sb.append("{");
		sb.append("id: null, ");
		sb.append("playerName: null");
		sb.append("}").append(", ");
		sb.append("{");
		sb.append("id: ").append(TEST_PLAYER_ENTITY_KEY).append(", ");
		sb.append("playerName: ").append(TEST_PLAYER_NAME);
		sb.append("}");
		sb.append("]");
		PLAYER_ENTITY_LIST_JSON = sb.toString();
	}

	/**
	 * Method initializing expected Player retrieval status messages
	 */
	private void setUpExpectedPlayerPersistStatusMessages() {
		// EXPECTED_NO_FAILURES_PLAYER_PERSISTENCE_MESSAGE_RESULT
		StringBuilder sb = new StringBuilder();
		sb.append(TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_MSG);
		sb.append(" '")
				.append(TournamentServiceEntityMapper
						.mapPlayerEntityListToConsolidatedJSONString(playerEntityList))
				.append("'. ").append(playerEntityList.size())
				.append(" players were successfully added.");
		EXPECTED_NO_FAILURES_PLAYER_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NO_SUCCESSES_PLAYER_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_MSG);
		sb.append(" '")
				.append(TournamentServiceEntityMapper
						.mapPlayerEntityListToConsolidatedJSONString(emptyPlayerEntityList))
				.append("'. ")
				.append(emptyPlayerEntityList.size())
				.append(" players were successfully added.")
				.append(System.getProperty("line.seperator"))
				.append("'")
				.append(TournamentServiceErrorConstants.ADD_PLAYER_FAILURE_MSG)
				.append(TournamentServiceEntityMapper
						.mapPlayerEntityListToConsolidatedJSONString(playerEntityList))
				.append("'. ").append(playerEntityList.size())
				.append(" players were not added successfully.");
		EXPECTED_NO_SUCCESSES_PLAYER_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NULL_INPUT_PLAYER_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_MSG)
				.append(" '")
				.append(TournamentServiceEntityMapper
						.mapPlayerEntityListToConsolidatedJSONString(null))
				.append("'. ").append(0)
				.append(" players were successfully added.");
		EXPECTED_NULL_INPUT_PLAYER_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_PLAYER_REMOVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(TournamentServiceErrorConstants.DELETE_PLAYER_SUCCESS_MSG)
				.append(" '")
				.append(TournamentServiceEntityMapper
						.mapPlayerEntityListToConsolidatedJSONString(playerEntityList))
				.append("'. ").append(playerEntityList.size())
				.append(" Entities were removed.");
		EXPECTED_PLAYER_REMOVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		// EXPECTED_PLAYER_RETRIEVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(TournamentServiceErrorConstants.RETRIEVE_PLAYER_SUCCESS_MSG)
				.append(" '")
				.append(TournamentServiceEntityMapper
						.mapPlayerEntityListToConsolidatedJSONString(playerEntityList))
				.append("'. ").append(playerEntityList.size())
				.append(" Entities were found");
		EXPECTED_PLAYER_RETRIEVAL_MESSAGE_SIMPLE_RESULT = sb.toString();
	}
}
