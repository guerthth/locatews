package amtc.gue.ws.test.tournament.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.tournament.inout.Player;
import amtc.gue.ws.tournament.inout.Players;
import amtc.gue.ws.tournament.persistence.model.GAEJPAPlayerEntity;
import amtc.gue.ws.tournament.response.PlayerServiceResponse;
import amtc.gue.ws.tournament.util.TournamentServiceEntityMapper;

/**
 * Testclass for the TournamentServiceEntityMapper
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TournamentServiceEntityMapperTest extends
		TournamentServiceUtilTest {

	@Test
	public void testMapPlayerToEntityForDeleteType() {
		player1.setId(TEST_PLAYER_ENTITY_KEY);
		GAEJPAPlayerEntity playerEntity = TournamentServiceEntityMapper
				.mapPlayerToEntity(player1, DelegatorTypeEnum.DELETE);
		assertEquals(player1.getId(), playerEntity.getKey());
		assertEquals(player1.getPlayerName(), playerEntity.getPlayerName());
	}

	@Test
	public void testMapPlayerToEntityForAddType() {
		GAEJPAPlayerEntity playerEntity = TournamentServiceEntityMapper
				.mapPlayerToEntity(player1, DelegatorTypeEnum.ADD);
		assertNull(playerEntity.getKey());
		assertEquals(player1.getPlayerName(), playerEntity.getPlayerName());
	}

	@Test
	public void testMapPlayerToEntityForUpdateType() {
		player1.setId(TEST_PLAYER_ENTITY_KEY);
		GAEJPAPlayerEntity playerEntity = TournamentServiceEntityMapper
				.mapPlayerToEntity(player1, DelegatorTypeEnum.UPDATE);
		assertEquals(player1.getId(), playerEntity.getKey());
		assertEquals(player1.getPlayerName(), playerEntity.getPlayerName());
	}

	@Test
	public void testMapPlayerToEntityForAddTypeUsingId() {
		player1.setId(TEST_PLAYER_ENTITY_KEY);
		GAEJPAPlayerEntity playerEntity = TournamentServiceEntityMapper
				.mapPlayerToEntity(player1, DelegatorTypeEnum.ADD);
		assertNull(playerEntity.getKey());
		assertEquals(player1.getPlayerName(), playerEntity.getPlayerName());
	}

	@Test
	public void testTransformPlayersToPlayerEntitiesUsingSimplePlayers() {
		List<GAEJPAPlayerEntity> playerEntityList = TournamentServiceEntityMapper
				.transformPlayersToPlayerEntities(simplePlayers,
						DelegatorTypeEnum.ADD);
		assertNotNull(playerEntityList);
		assertEquals(2, playerEntityList.size());
	}

	@Test
	public void testTransformPlayersToPlayerEntitiesUsingEmptyPlayers() {
		List<GAEJPAPlayerEntity> playerEntityList = TournamentServiceEntityMapper
				.transformPlayersToPlayerEntities(emptyPlayers,
						DelegatorTypeEnum.ADD);
		assertNotNull(playerEntityList);
		assertEquals(0, playerEntityList.size());
	}

	@Test
	public void testTransformPlayersToPlayerEntitiesUsingNullPlayers() {
		List<GAEJPAPlayerEntity> playerEntityList = TournamentServiceEntityMapper
				.transformPlayersToPlayerEntities(null, DelegatorTypeEnum.ADD);
		assertNotNull(playerEntityList);
		assertEquals(0, playerEntityList.size());
	}

	@Test
	public void testMapPlayerEntityToPlayerUsingSimplePlayerEntity() {
		Player player = TournamentServiceEntityMapper
				.mapPlayerEntityToPlayer(playerEntity2);
		assertNotNull(player);
		assertEquals(playerEntity2.getKey(), player.getId());
		assertEquals(playerEntity2.getPlayerName(), player.getPlayerName());
	}

	@Test
	public void testMapPlayerEntityToPlayerUsingNullPlayerEntity() {
		Player player = TournamentServiceEntityMapper
				.mapPlayerEntityToPlayer(null);
		assertNotNull(player);
		assertNull(player.getId());
		assertNull(player.getPlayerName());
	}

	@Test
	public void testTransformPlayerEntitiesToPlayersUsingSimplePlayerEntityList() {
		Players players = TournamentServiceEntityMapper
				.transformPlayerEntitiesToPlayers(playerEntityList);
		assertNotNull(players);
		assertEquals(playerEntityList.size(), players.getPlayers().size());
	}

	@Test
	public void testTransformPlayerEntitiesToPlayersUsingNullInput() {
		Players players = TournamentServiceEntityMapper
				.transformPlayerEntitiesToPlayers(null);
		assertNotNull(players);
		assertTrue(players.getPlayers().isEmpty());
	}

	@Test
	public void testMapPlayerEntityToJSONStringUsingNullPlayerEntity() {
		String JSONString = TournamentServiceEntityMapper
				.mapPlayerEntityToJSONString(null);
		assertEquals(NULL_PLAYER_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapPlayerEntityToJSONStringUsingSimplePlayerEntity() {
		String JSONString = TournamentServiceEntityMapper
				.mapPlayerEntityToJSONString(playerEntity1);
		assertEquals(PLAYER_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapPlayerEntityListToConsolidatedJSONStringUsingSimpleList() {
		String JSONString = TournamentServiceEntityMapper
				.mapPlayerEntityListToConsolidatedJSONString(playerEntityList);
		assertEquals(PLAYER_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapPlayerEntityListToConsolidatedJSONStringUsingNullList() {
		String JSONString = TournamentServiceEntityMapper
				.mapPlayerEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_PLAYER_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapPlayerEntityListToConsolidatedJSONStringUsingEmptyList() {
		String JSONString = TournamentServiceEntityMapper
				.mapPlayerEntityListToConsolidatedJSONString(emptyPlayerEntityList);
		assertEquals(NULL_PLAYER_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapBdOutputToPlayerServiceResponseUsingPlayersOutputObject() {
		PlayerServiceResponse playerResponse = TournamentServiceEntityMapper
				.mapBdOutputToPlayerServiceResponse(playerDelegatorOutput);
		assertNotNull(playerResponse.getPlayers());
	}

	@Test
	public void testMapBdOutputToPlayerServiceResponseUsingUnrecognizedOutputObject() {
		PlayerServiceResponse playerResponse = TournamentServiceEntityMapper
				.mapBdOutputToPlayerServiceResponse(unrecognizedPlayerDelegatorOutput);
		assertNull(playerResponse.getPlayers());
	}

	@Test
	public void testMapBdOutputToPlayerServiceResponseUsingNullInput() {
		PlayerServiceResponse playerResponse = TournamentServiceEntityMapper
				.mapBdOutputToPlayerServiceResponse(null);
		assertNull(playerResponse);
	}
}
