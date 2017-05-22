package amtc.gue.ws.tournament.util.mapper;

import java.util.ArrayList;
import java.util.List;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.StatusMapper;
import amtc.gue.ws.tournament.inout.Player;
import amtc.gue.ws.tournament.inout.Players;
import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;
import amtc.gue.ws.tournament.response.PlayerServiceResponse;

/**
 * Class responsible for mapping of TournamentService related objects Use Case
 * examples: - building up TournamentServiceResponse objects - mapping objects
 * from one type to another - creating JSON Strings for specific objects
 * 
 * @author Thomas
 *
 */
public abstract class TournamentServiceEntityMapper {

	/**
	 * Method mapping Player object to GAEPlayerEntity
	 * 
	 * @param player
	 *            the player element that should be transformed
	 * @param type
	 *            the database action type
	 * @return the mapped GAEPlayerEntity
	 */
	public abstract GAEPlayerEntity mapPlayerToEntity(Player player, DelegatorTypeEnum type);

	/**
	 * Method mapping Players to a list of GAEPlayerEntities
	 * 
	 * @param players
	 *            the Players input object that should be mapped
	 * @param type
	 *            the database action type
	 * @return the mapped list of GAEPlayerEntities
	 */
	public List<GAEPlayerEntity> transformPlayersToPlayerEntities(Players players, DelegatorTypeEnum type) {
		List<GAEPlayerEntity> playerEntityList = new ArrayList<>();
		if (players != null) {
			for (Player player : players.getPlayers()) {
				playerEntityList.add(mapPlayerToEntity(player, type));
			}
		}
		return playerEntityList;
	}

	/**
	 * Method mapping a GAEPlayerEntity to a Player object
	 * 
	 * @param playerEntity
	 *            the GAEPlayerEntity that should be mapped
	 * @return the Player object
	 */
	public static Player mapPlayerEntityToPlayer(GAEPlayerEntity playerEntity) {
		Player player = new Player();
		if (playerEntity != null) {
			player.setId(playerEntity.getKey());
			player.setDescription(playerEntity.getDescription());
		}
		return player;
	}

	/**
	 * Method mapping a list of GAEPlayerEntities to a Players object
	 * 
	 * @param playerEntityList
	 *            a list of GAEPlayerEntities
	 * @return a Players object
	 */
	public static Players transformPlayerEntitiesToPlayers(List<GAEPlayerEntity> playerEntityList) {
		Players players = new Players();
		List<Player> playerList = new ArrayList<>();
		if (playerEntityList != null) {
			for (GAEPlayerEntity playerEntity : playerEntityList) {
				playerList.add(mapPlayerEntityToPlayer(playerEntity));
			}
		}
		players.setPlayers(playerList);
		return players;
	}

	/**
	 * Method mapping a GAEPlayerEntity to a JSON String
	 * 
	 * @param playerEntity
	 *            the GAEPlayerEntity that should be mapped
	 * @return the created GAEPlayerEntity JSON String
	 */
	public static String mapPlayerEntityToJSONString(GAEPlayerEntity playerEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (playerEntity != null) {
			sb.append("id: ");
			String id = playerEntity.getKey() != null ? playerEntity.getKey() + ", " : "null, ";
			sb.append(id);
			sb.append("description: ");
			String playerName = playerEntity.getDescription() != null ? playerEntity.getDescription() : "null";
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
	public static String mapPlayerEntityListToConsolidatedJSONString(List<GAEPlayerEntity> playerEntities) {
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
	public static PlayerServiceResponse mapBdOutputToPlayerServiceResponse(IDelegatorOutput bdOutput) {
		PlayerServiceResponse playerServiceResponse = null;

		if (bdOutput != null) {
			playerServiceResponse = new PlayerServiceResponse();
			playerServiceResponse.setStatus(StatusMapper.buildStatusForDelegatorOutput(bdOutput));
			if (bdOutput.getOutputObject() instanceof Players) {
				List<Player> playerList = ((Players) bdOutput.getOutputObject()).getPlayers();
				playerServiceResponse.setPlayers(playerList);
			} else {
				playerServiceResponse.setPlayers(null);
			}
		}
		return playerServiceResponse;
	}

}
