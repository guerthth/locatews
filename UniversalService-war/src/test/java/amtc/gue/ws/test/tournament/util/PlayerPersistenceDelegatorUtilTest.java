package amtc.gue.ws.test.tournament.util;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.tournament.util.PlayerPersistenceDelegatorUtils;

/**
 * test class for the PlayerPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerPersistenceDelegatorUtilTest extends
		TournamentServiceUtilTest {
	@Test
	public void testbuildPersistPlayersSuccessStatusMessageWithNoFailures() {
		String message = PlayerPersistenceDelegatorUtils
				.buildPersistPlayerSuccessStatusMessage(playerEntityList,
						emptyPlayerEntityList);
		assertEquals(EXPECTED_NO_FAILURES_PLAYER_PERSISTENCE_MESSAGE_RESULT,
				message);
	}

	@Test
	public void testbuildPersistPlayersSuccessStatusMessageWithNoSuccesses() {
		String message = PlayerPersistenceDelegatorUtils
				.buildPersistPlayerSuccessStatusMessage(emptyPlayerEntityList,
						playerEntityList);
		assertEquals(EXPECTED_NO_SUCCESSES_PLAYER_PERSISTENCE_MESSAGE_RESULT,
				message);
	}

	@Test
	public void testbuildPersistPlayersSuccessStatusMessageUsingNullInputs() {
		String message = PlayerPersistenceDelegatorUtils
				.buildPersistPlayerSuccessStatusMessage(null, null);
		assertEquals(EXPECTED_NULL_INPUT_PLAYER_PERSISTENCE_MESSAGE_RESULT,
				message);
	}

	@Test
	public void testBuildRemovePlayersSuccessStatusMessageSimpleList() {
		String message = PlayerPersistenceDelegatorUtils
				.buildRemovePlayersSuccessStatusMessage(playerEntityList);
		assertEquals(EXPECTED_PLAYER_REMOVAL_MESSAGE_SIMPLE_RESULT, message);
	}

	@Test
	public void testBuildRetrievePlayersSuccessStatusMessageSimpleList() {
		String message = PlayerPersistenceDelegatorUtils
				.buildRetrievePlayersSuccessStatusMessage(playerEntityList);
		assertEquals(EXPECTED_PLAYER_RETRIEVAL_MESSAGE_SIMPLE_RESULT, message);
	}
}
