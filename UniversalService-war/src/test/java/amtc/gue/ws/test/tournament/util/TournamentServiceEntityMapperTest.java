package amtc.gue.ws.test.tournament.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.test.tournament.TournamentTest;
import amtc.gue.ws.tournament.inout.Player;
import amtc.gue.ws.tournament.inout.Players;
import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;
import amtc.gue.ws.tournament.response.PlayerServiceResponse;
import amtc.gue.ws.tournament.util.mapper.TournamentServiceEntityMapper;
import amtc.gue.ws.tournament.util.mapper.jpa.TournamentServiceJPAEntityMapper;
import amtc.gue.ws.tournament.util.mapper.objectify.TournamentServiceObjectifyEntityMapper;

/**
 * Testclass for the TournamentServiceEntityMapper
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TournamentServiceEntityMapperTest extends TournamentTest {
	private static String NULL_PLAYER_ENTITY_JSON = "{}";
	private static String NULL_PLAYER_ENTITY_LIST_JSON = "[]";
	private static String PLAYER_ENTITY_JSON;
	private static String PLAYER_ENTITY_LIST_JSON;

	private static IDelegatorOutput playerDelegatorOutput;
	private static IDelegatorOutput unrecognizedPlayerDelegatorOutput;

	private TournamentServiceEntityMapper JPATournamentEntityMapper = new TournamentServiceJPAEntityMapper();
	private TournamentServiceEntityMapper objectifyTournamentEntityMapper = new TournamentServiceObjectifyEntityMapper();

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		setUpBasicTournamentEnvironment();
		setUpJSONStrings();
		setUpBdOutputs();
	}

	@Test
	public void testMapPlayerToEntityForDeleteType() {
		player1.setId(PLAYERKEY);
		GAEPlayerEntity playerEntity = JPATournamentEntityMapper.mapPlayerToEntity(player1, DelegatorTypeEnum.DELETE);
		assertEquals(player1.getId(), playerEntity.getKey());
		assertEquals(player1.getDescription(), playerEntity.getDescription());
	}

	@Test
	public void testMapPlayerToEntityForAddType() {
		GAEPlayerEntity playerEntity = JPATournamentEntityMapper.mapPlayerToEntity(player1, DelegatorTypeEnum.ADD);
		assertNull(playerEntity.getKey());
		assertEquals(player1.getDescription(), playerEntity.getDescription());
	}

	@Test
	public void testMapPlayerToEntityForUpdateType() {
		player1.setId(PLAYERKEY);
		GAEPlayerEntity playerEntity = JPATournamentEntityMapper.mapPlayerToEntity(player1, DelegatorTypeEnum.UPDATE);
		assertEquals(player1.getId(), playerEntity.getKey());
		assertEquals(player1.getDescription(), playerEntity.getDescription());
	}

	@Test
	public void testMapPlayerToEntityForAddTypeUsingId() {
		player1.setId(PLAYERKEY);
		GAEPlayerEntity playerEntity = JPATournamentEntityMapper.mapPlayerToEntity(player1, DelegatorTypeEnum.ADD);
		assertNull(playerEntity.getKey());
		assertEquals(player1.getDescription(), playerEntity.getDescription());
	}

	@Test
	public void testTransformPlayersToPlayerEntitiesUsingSimplePlayers() {
		List<GAEPlayerEntity> playerEntityList = objectifyTournamentEntityMapper
				.transformPlayersToPlayerEntities(players, DelegatorTypeEnum.ADD);
		assertNotNull(playerEntityList);
		assertEquals(2, playerEntityList.size());
	}

	@Test
	public void testTransformPlayersToPlayerEntitiesUsingEmptyPlayers() {
		List<GAEPlayerEntity> playerEntityList = objectifyTournamentEntityMapper
				.transformPlayersToPlayerEntities(emptyPlayers, DelegatorTypeEnum.ADD);
		assertNotNull(playerEntityList);
		assertEquals(0, playerEntityList.size());
	}

	@Test
	public void testTransformPlayersToPlayerEntitiesUsingNullPlayers() {
		List<GAEPlayerEntity> playerEntityList = objectifyTournamentEntityMapper.transformPlayersToPlayerEntities(null,
				DelegatorTypeEnum.ADD);
		assertNotNull(playerEntityList);
		assertEquals(0, playerEntityList.size());
	}

	@Test
	public void testMapPlayerEntityToPlayerUsingSimplePlayerEntity() {
		Player player = TournamentServiceEntityMapper.mapPlayerEntityToPlayer(JPAPlayerEntity2);
		assertNotNull(player);
		assertEquals(JPAPlayerEntity2.getKey(), player.getId());
		assertEquals(JPAPlayerEntity2.getDescription(), player.getDescription());
	}

	@Test
	public void testMapPlayerEntityToPlayerUsingNullPlayerEntity() {
		Player player = TournamentServiceEntityMapper.mapPlayerEntityToPlayer(null);
		assertNotNull(player);
		assertNull(player.getId());
		assertNull(player.getDescription());
	}

	@Test
	public void testTransformPlayerEntitiesToPlayersUsingSimplePlayerEntityList() {
		Players players = TournamentServiceEntityMapper.transformPlayerEntitiesToPlayers(JPAPlayerEntityList);
		assertNotNull(players);
		assertEquals(JPAPlayerEntityList.size(), players.getPlayers().size());
	}

	@Test
	public void testTransformPlayerEntitiesToPlayersUsingNullInput() {
		Players players = TournamentServiceEntityMapper.transformPlayerEntitiesToPlayers(null);
		assertNotNull(players);
		assertTrue(players.getPlayers().isEmpty());
	}

	@Test
	public void testMapPlayerEntityToJSONStringUsingNullPlayerEntity() {
		String JSONString = TournamentServiceEntityMapper.mapPlayerEntityToJSONString(null);
		assertEquals(NULL_PLAYER_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapPlayerEntityToJSONStringUsingSimplePlayerEntity() {
		String JSONString = TournamentServiceEntityMapper.mapPlayerEntityToJSONString(JPAPlayerEntity1);
		assertEquals(PLAYER_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapPlayerEntityListToConsolidatedJSONStringUsingSimpleList() {
		String JSONString = TournamentServiceEntityMapper
				.mapPlayerEntityListToConsolidatedJSONString(JPAPlayerEntityList);
		assertEquals(PLAYER_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapPlayerEntityListToConsolidatedJSONStringUsingNullList() {
		String JSONString = TournamentServiceEntityMapper.mapPlayerEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_PLAYER_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapPlayerEntityListToConsolidatedJSONStringUsingEmptyList() {
		String JSONString = TournamentServiceEntityMapper
				.mapPlayerEntityListToConsolidatedJSONString(JPAPlayerEntityEmptyList);
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
		PlayerServiceResponse playerResponse = TournamentServiceEntityMapper.mapBdOutputToPlayerServiceResponse(null);
		assertNull(playerResponse);
	}

	/**
	 * Method initializing JSON Strings
	 */
	private static void setUpJSONStrings() {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("id: testPlayerKey, ");
		sb.append("description: null");
		sb.append("}");
		PLAYER_ENTITY_JSON = sb.toString();
		sb.setLength(0);
		sb.append("[");
		sb.append("{");
		sb.append("id: testPlayerKey, ");
		sb.append("description: null");
		sb.append("}").append(", ");
		sb.append("{");
		sb.append("id: ").append(PLAYERKEY_2).append(", ");
		sb.append("description: null");
		sb.append("}");
		sb.append("]");
		PLAYER_ENTITY_LIST_JSON = sb.toString();
	}

	/**
	 * Method initializing Delegatoroutputs
	 */
	private static void setUpBdOutputs() {
		playerDelegatorOutput = new DelegatorOutput();
		playerDelegatorOutput.setOutputObject(players);
		unrecognizedPlayerDelegatorOutput = new DelegatorOutput();
		unrecognizedPlayerDelegatorOutput.setOutputObject(null);
	}
}
