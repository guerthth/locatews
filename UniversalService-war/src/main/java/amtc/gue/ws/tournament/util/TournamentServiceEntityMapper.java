package amtc.gue.ws.tournament.util;

import java.util.ArrayList;
import java.util.List;

import amtc.gue.ws.base.delegate.IDelegatorOutput;
import amtc.gue.ws.base.util.PersistenceTypeEnum;
import amtc.gue.ws.base.util.StatusMapper;
import amtc.gue.ws.tournament.inout.Player;
import amtc.gue.ws.tournament.inout.Players;
import amtc.gue.ws.tournament.persistence.model.GAEJPAPlayerEntity;
import amtc.gue.ws.tournament.response.PlayerServiceResponse;

/**
 * Class responsible for mapping of TournamentService related objects Use Case
 * examples: - building up TournamentServiceResponse objects - mapping objects
 * from one type to another - creating JSON Strings for specific objects
 * 
 * @author Thomas
 *
 */
public class TournamentServiceEntityMapper {

	/**
	 * Method mapping Player object to GAEJPAPlayerEntity
	 * 
	 * @param player
	 *            the player element that should be transformed
	 * @param type
	 *            the database action type
	 * @return the mapped GAEJPAPlayerEntity
	 */
	public static GAEJPAPlayerEntity mapPlayerToEntity(Player player,
			PersistenceTypeEnum type) {
		GAEJPAPlayerEntity playerEntity = new GAEJPAPlayerEntity();
		if (player.getId() != null && type != PersistenceTypeEnum.ADD)
			playerEntity.setKey(player.getId());
		playerEntity.setPlayerName(player.getPlayerName());
		return playerEntity;
	}

	/**
	 * Method mapping Players to a list of GAEJPAPlayerEntities
	 * 
	 * @param players
	 *            the Players input object that should be mapped
	 * @param type
	 *            the database action type
	 * @return the mapped list of GAEJPAPlayerEntities
	 */
	public static List<GAEJPAPlayerEntity> transformPlayersToPlayerEntities(
			Players players, PersistenceTypeEnum type) {
		List<GAEJPAPlayerEntity> playerEntityList = new ArrayList<GAEJPAPlayerEntity>();
		if (players != null) {
			for (Player player : players.getPlayers()) {
				playerEntityList.add(mapPlayerToEntity(player, type));
			}
		}
		return playerEntityList;
	}

	/**
	 * Method mapping a GAEJPAPlayerEntity to a Player object
	 * 
	 * @param playerEntity
	 *            the GAEJPAPlayerEntity that should be mapped
	 * @return the Player object
	 */
	public static Player mapPlayerEntityToPlayer(GAEJPAPlayerEntity playerEntity) {
		Player player = new Player();
		if (playerEntity != null) {
			player.setId(playerEntity.getKey());
			player.setPlayerName(playerEntity.getPlayerName());
		}
		return player;
	}

	/**
	 * Method mapping a list of GAEJPAPlayerEntities to a Players object
	 * 
	 * @param playerEntityList
	 *            a list of GAEJPAPlayerEntities
	 * @return a Players object
	 */
	public static Players transformPlayerEntitiesToPlayers(
			List<GAEJPAPlayerEntity> playerEntityList) {
		Players players = new Players();
		List<Player> playerList = new ArrayList<>();
		if (playerEntityList != null) {
			for (GAEJPAPlayerEntity playerEntity : playerEntityList) {
				playerList.add(mapPlayerEntityToPlayer(playerEntity));
			}
		}
		players.setPlayers(playerList);
		return players;
	}

	/**
	 * Method mapping a GAEJPAPlayerEntity to a JSON String
	 * 
	 * @param playerEntity
	 *            the GAEJPAPlayerEntity that should be mapped
	 * @return the created GAEJPAPlayerEntity JSON String
	 */
	public static String mapPlayerEntityToJSONString(
			GAEJPAPlayerEntity playerEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (playerEntity != null) {
			sb.append("id: ");
			String id = playerEntity.getKey() != null ? playerEntity.getKey()
					+ ", " : "null, ";
			sb.append(id);
			sb.append("playerName: ");
			String playerName = playerEntity.getPlayerName() != null ? playerEntity
					.getPlayerName() : "null";
			sb.append(playerName);
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Method mapping a list of PlayerEntities to one consolidated JSON String
	 * 
	 * @param playerEntities
	 *            the list of PlayerEntities that should be mapped to a JSON
	 *            String
	 * @return the consolidated JSON String
	 */
	public static String mapPlayerEntityListToConsolidatedJSONString(
			List<GAEJPAPlayerEntity> playerEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (playerEntities != null && !playerEntities.isEmpty()) {
			int listSize = playerEntities.size();
			for (int i = 0; i < listSize; i++) {
				sb.append(mapPlayerEntityToJSONString(playerEntities.get(i)));
				if (i != listSize - 1) {
					sb.append(", ");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Method mapping a delegatoroutput to a PlayerResponse
	 * 
	 * @param bdOutput
	 *            delegatoroutput that should be included in the response
	 * @return mapped PlayerServiceReponse
	 */
	public static PlayerServiceResponse mapBdOutputToPlayerServiceResponse(
			IDelegatorOutput bdOutput) {
		PlayerServiceResponse playerServiceResponse = null;

		if (bdOutput != null) {
			playerServiceResponse = new PlayerServiceResponse();
			playerServiceResponse.setStatus(StatusMapper
					.buildStatusForDelegatorOutput(bdOutput));
			if (bdOutput.getOutputObject() instanceof Players) {
				List<Player> playerList = ((Players) bdOutput.getOutputObject())
						.getPlayers();
				playerServiceResponse.setPlayers(playerList);
			} else {
				playerServiceResponse.setPlayers(null);
			}
		}
		return playerServiceResponse;
	}

}
