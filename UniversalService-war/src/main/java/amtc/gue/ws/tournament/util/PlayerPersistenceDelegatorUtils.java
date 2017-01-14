package amtc.gue.ws.tournament.util;

import java.util.List;

import amtc.gue.ws.tournament.persistence.model.GAEJPAPlayerEntity;

/**
 * Utility class for the PlayerPersistenceDelegator
 * 
 * @author Thomas
 *
 */
public class PlayerPersistenceDelegatorUtils {
	/**
	 * Method building the String status message for successful removal of
	 * Player entities
	 * 
	 * @param successfullyAddedPlayerEntities
	 *            a list of successfully added PlayerEntities
	 * @param unsuccessfullyAddedPlayerEntities
	 *            a list of unsuccessfully added PlayerEntities
	 * @return success status message for player entity removal
	 */
	public static String buildPersistPlayerSuccessStatusMessage(
			List<GAEJPAPlayerEntity> successfullyAddedPlayerEntities,
			List<GAEJPAPlayerEntity> unsuccessfullyAddedPlayerEntities) {
		int numberOfSuccessfullyAddedEntities = (successfullyAddedPlayerEntities != null) ? successfullyAddedPlayerEntities
				.size() : 0;
		int numberOfUnsuccessfullyAddedEntities = (successfullyAddedPlayerEntities != null) ? unsuccessfullyAddedPlayerEntities
				.size() : 0;
		StringBuilder sb = new StringBuilder();
		sb.append(TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_MSG);
		sb.append(" '");
		sb.append(TournamentServiceEntityMapper
				.mapPlayerEntityListToConsolidatedJSONString(successfullyAddedPlayerEntities));
		sb.append("'. ").append(numberOfSuccessfullyAddedEntities)
				.append(" players were successfully added.");
		if (numberOfUnsuccessfullyAddedEntities > 0) {
			sb.append(System.getProperty("line.seperator"));
			sb.append("'");
			sb.append(TournamentServiceErrorConstants.ADD_PLAYER_FAILURE_MSG);
			sb.append(TournamentServiceEntityMapper
					.mapPlayerEntityListToConsolidatedJSONString(unsuccessfullyAddedPlayerEntities));
			sb.append("'. ").append(numberOfUnsuccessfullyAddedEntities)
					.append(" players were not added successfully.");
		}
		return sb.toString();
	}

	/**
	 * Method building the String status message used in the response when
	 * removing PlayerEntity from the datastore
	 * 
	 * @param removedPlayerEntities
	 *            a list of removed PlayerEntities
	 * @return the status message that can be used in the response as String
	 */
	public static String buildRemovePlayersSuccessStatusMessage(
			List<GAEJPAPlayerEntity> removedPlayerEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append(TournamentServiceErrorConstants.DELETE_PLAYER_SUCCESS_MSG);
		sb.append(" '");
		sb.append(TournamentServiceEntityMapper
				.mapPlayerEntityListToConsolidatedJSONString(removedPlayerEntities));
		sb.append("'. ");
		sb.append(removedPlayerEntities.size());
		sb.append(" Entities were removed.");
		return sb.toString();
	}

	/**
	 * Method building the String status message used in the response when
	 * retrieving all PlayerEntities from the datastore
	 * 
	 * @param foundPlayers
	 *            the players that were found
	 * @return the status message that can be used in the response as String
	 */
	public static String buildRetrievePlayersSuccessStatusMessage(
			List<GAEJPAPlayerEntity> foundPlayers) {
		StringBuilder sb = new StringBuilder();
		sb.append(TournamentServiceErrorConstants.RETRIEVE_PLAYER_SUCCESS_MSG);
		sb.append(" '");
		sb.append(TournamentServiceEntityMapper
				.mapPlayerEntityListToConsolidatedJSONString(foundPlayers));
		sb.append("'. ");
		sb.append((foundPlayers != null) ? foundPlayers.size() : "0");
		sb.append(" Entities were found");
		return sb.toString();
	}
}
