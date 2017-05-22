package amtc.gue.ws.test.tournament.util;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.test.tournament.TournamentTest;
import amtc.gue.ws.tournament.util.PlayerPersistenceDelegatorUtils;
import amtc.gue.ws.tournament.util.TournamentServiceErrorConstants;
import amtc.gue.ws.tournament.util.mapper.TournamentServiceEntityMapper;

/**
 * test class for the PlayerPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerPersistenceDelegatorUtilTest extends TournamentTest {
	private static String EXPECTED_NO_FAILURES_PLAYER_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_NO_SUCCESSES_PLAYER_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_NULL_INPUT_PLAYER_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_PLAYER_REMOVAL_MESSAGE_SIMPLE_RESULT;
	private static String EXPECTED_PLAYER_RETRIEVAL_MESSAGE_SIMPLE_RESULT;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicTournamentEnvironment();
		setUpExpectedTournamentPersistStatusMessages();
	}

	@Test
	public void testbuildPersistPlayersSuccessStatusMessageWithNoFailures() {
		String message = PlayerPersistenceDelegatorUtils.buildPersistPlayerSuccessStatusMessage(JPAPlayerEntityList,
				JPAPlayerEntityEmptyList);
		assertEquals(EXPECTED_NO_FAILURES_PLAYER_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testbuildPersistPlayersSuccessStatusMessageWithNoSuccesses() {
		String message = PlayerPersistenceDelegatorUtils
				.buildPersistPlayerSuccessStatusMessage(JPAPlayerEntityEmptyList, JPAPlayerEntityList);
		assertEquals(EXPECTED_NO_SUCCESSES_PLAYER_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testbuildPersistPlayersSuccessStatusMessageUsingNullInputs() {
		String message = PlayerPersistenceDelegatorUtils.buildPersistPlayerSuccessStatusMessage(null, null);
		assertEquals(EXPECTED_NULL_INPUT_PLAYER_PERSISTENCE_MESSAGE_RESULT, message);
	}

	@Test
	public void testBuildRemovePlayersSuccessStatusMessageSimpleList() {
		String message = PlayerPersistenceDelegatorUtils.buildRemovePlayersSuccessStatusMessage(JPAPlayerEntityList);
		assertEquals(EXPECTED_PLAYER_REMOVAL_MESSAGE_SIMPLE_RESULT, message);
	}

	@Test
	public void testBuildRetrievePlayersSuccessStatusMessageSimpleList() {
		String message = PlayerPersistenceDelegatorUtils.buildRetrievePlayersSuccessStatusMessage(JPAPlayerEntityList);
		assertEquals(EXPECTED_PLAYER_RETRIEVAL_MESSAGE_SIMPLE_RESULT, message);
	}

	/**
	 * Method initializing expected Player retrieval status messages
	 */
	private static void setUpExpectedTournamentPersistStatusMessages() {
		// EXPECTED_NO_FAILURES_PLAYER_PERSISTENCE_MESSAGE_RESULT
		StringBuilder sb = new StringBuilder();
		sb.append(TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_MSG);
		sb.append(" '")
				.append(TournamentServiceEntityMapper.mapPlayerEntityListToConsolidatedJSONString(JPAPlayerEntityList))
				.append("'. ").append(JPAPlayerEntityList.size()).append(" players were successfully added.");
		EXPECTED_NO_FAILURES_PLAYER_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NO_SUCCESSES_PLAYER_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_MSG);
		sb.append(" '")
				.append(TournamentServiceEntityMapper
						.mapPlayerEntityListToConsolidatedJSONString(JPAPlayerEntityEmptyList))
				.append("'. ").append(JPAPlayerEntityEmptyList.size()).append(" players were successfully added.")
				.append(System.getProperty("line.seperator")).append("'")
				.append(TournamentServiceErrorConstants.ADD_PLAYER_FAILURE_MSG)
				.append(TournamentServiceEntityMapper.mapPlayerEntityListToConsolidatedJSONString(JPAPlayerEntityList))
				.append("'. ").append(JPAPlayerEntityList.size()).append(" players were not added successfully.");
		EXPECTED_NO_SUCCESSES_PLAYER_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NULL_INPUT_PLAYER_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_MSG).append(" '")
				.append(TournamentServiceEntityMapper.mapPlayerEntityListToConsolidatedJSONString(null)).append("'. ")
				.append(0).append(" players were successfully added.");
		EXPECTED_NULL_INPUT_PLAYER_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_PLAYER_REMOVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(TournamentServiceErrorConstants.DELETE_PLAYER_SUCCESS_MSG).append(" '")
				.append(TournamentServiceEntityMapper.mapPlayerEntityListToConsolidatedJSONString(JPAPlayerEntityList))
				.append("'. ").append(JPAPlayerEntityList.size()).append(" Entities were removed.");
		EXPECTED_PLAYER_REMOVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		// EXPECTED_PLAYER_RETRIEVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(TournamentServiceErrorConstants.RETRIEVE_PLAYER_SUCCESS_MSG).append(" '")
				.append(TournamentServiceEntityMapper.mapPlayerEntityListToConsolidatedJSONString(JPAPlayerEntityList))
				.append("'. ").append(JPAPlayerEntityList.size()).append(" Entities were found");
		EXPECTED_PLAYER_RETRIEVAL_MESSAGE_SIMPLE_RESULT = sb.toString();
	}
}
