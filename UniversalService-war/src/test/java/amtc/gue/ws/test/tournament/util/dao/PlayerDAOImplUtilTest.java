package amtc.gue.ws.test.tournament.util.dao;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.test.tournament.util.TournamentServiceUtilTest;
import amtc.gue.ws.tournament.util.dao.PlayerDAOImplUtils;

/**
 * This class tests the functionality of the PlayerDAOImplUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerDAOImplUtilTest extends TournamentServiceUtilTest {
	private static final String BASIC_PLAYER_SPECIFIC_QUERY = "select p from PlayerEntity p";
	private static final String UPDATED_PLAYER_QUERY_1 = BASIC_PLAYER_SPECIFIC_QUERY
			+ " where p.playerId = :id" + " and p.playerName = :playerName";
	private static final String UPDATED_PLAYER_QUERY_2 = BASIC_PLAYER_SPECIFIC_QUERY
			+ " where p.playerName = :playerName";
	
	@Test
	public void testBuildSpecificPlayerQueryWithNullEntity() {
		String updatedQuery = PlayerDAOImplUtils.buildSpecificPlayerQuery(
				BASIC_PLAYER_SPECIFIC_QUERY, null);
		assertEquals(BASIC_PLAYER_SPECIFIC_QUERY, updatedQuery);
	}
	
	@Test
	public void testBuildSpecificPlayerQueryWithNullValues(){
		String updatedQuery = PlayerDAOImplUtils.buildSpecificPlayerQuery(
				BASIC_PLAYER_SPECIFIC_QUERY, playerEntity1);
		assertEquals(BASIC_PLAYER_SPECIFIC_QUERY, updatedQuery);
	}
	
	@Test
	public void testBuildSpecificPlayerQueryMajorCriteria() {
		String updatedQuery = PlayerDAOImplUtils.buildSpecificPlayerQuery(
				BASIC_PLAYER_SPECIFIC_QUERY, playerEntity2);
		assertEquals(UPDATED_PLAYER_QUERY_1, updatedQuery);
	}
	
	@Test
	public void testBuildSpecificPlayerQueryMinorCriteria() {
		String updatedQuery = PlayerDAOImplUtils.buildSpecificPlayerQuery(
				BASIC_PLAYER_SPECIFIC_QUERY, playerEntity3);
		assertEquals(UPDATED_PLAYER_QUERY_2, updatedQuery);
	}
}
