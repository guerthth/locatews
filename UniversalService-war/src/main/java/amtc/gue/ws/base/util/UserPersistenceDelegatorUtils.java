package amtc.gue.ws.base.util;

import java.util.List;

import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;

/**
 * Utility class for the UserPersistenceDelegator
 * 
 * @author Thomas
 *
 */
public class UserPersistenceDelegatorUtils {
	/**
	 * Method building the String status message for successful removal of
	 * User entities
	 * 
	 * @param successfullyAddedUserEntities
	 *            a list of successfully added UserEntities
	 * @param unsuccessfullyAddedUserEntities
	 *            a list of unsuccessfully added UserEntities
	 * @return success status message for user entity removal
	 */
	public static String buildPersistUserSuccessStatusMessage(
			List<GAEJPAUserEntity> successfullyAddedUserEntities,
			List<GAEJPAUserEntity> unsuccessfullyAddedUserEntities) {
		int numberOfSuccessfullyAddedEntities = (successfullyAddedUserEntities != null) ? successfullyAddedUserEntities
				.size() : 0;
		int numberOfUnsuccessfullyAddedEntities = (successfullyAddedUserEntities != null) ? unsuccessfullyAddedUserEntities
				.size() : 0;
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.ADD_USER_SUCCESS_MSG);
		sb.append(" '");
		sb.append(UserServiceEntityMapper
				.mapUserEntityListToConsolidatedJSONString(successfullyAddedUserEntities));
		sb.append("'. ").append(numberOfSuccessfullyAddedEntities)
				.append(" users were successfully added.");
		if (numberOfUnsuccessfullyAddedEntities > 0) {
			sb.append(System.getProperty("line.seperator"));
			sb.append("'");
			sb.append(ErrorConstants.ADD_USER_FAILURE_MSG);
			sb.append(UserServiceEntityMapper
					.mapUserEntityListToConsolidatedJSONString(unsuccessfullyAddedUserEntities));
			sb.append("'. ").append(numberOfUnsuccessfullyAddedEntities)
					.append(" users were not added successfully.");
		}
		return sb.toString();
	}

	/**
	 * Method building the String status message used in the response when
	 * removing UserEntity from the datastore
	 * 
	 * @param removedUserEntities
	 *            a list of removed UserEntities
	 * @return the status message that can be used in the response as String
	 */
	public static String buildRemoveUsersSuccessStatusMessage(
			List<GAEJPAUserEntity> removedUserEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.DELETE_USER_SUCCESS_MSG);
		sb.append(" '");
		sb.append(UserServiceEntityMapper
				.mapUserEntityListToConsolidatedJSONString(removedUserEntities));
		sb.append("'. ");
		sb.append(removedUserEntities.size());
		sb.append(" Entities were removed.");
		return sb.toString();
	}

	/**
	 * Method building the String status message used in the response when
	 * retrieving all UserEntities from the datastore
	 * 
	 * @param foundUsers
	 *            the users that were found
	 * @return the status message that can be used in the response as String
	 */
	public static String buildGetUsersByRoleSuccessStatusMessage(
			Roles roles, List<GAEJPAUserEntity> foundUsers) {
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.RETRIEVE_USER_SUCCESS_MSG);
		sb.append(" '");
		sb.append(roles.getRoles().toString());
		sb.append("': '");
		sb.append(UserServiceEntityMapper
				.mapUserEntityListToConsolidatedJSONString(foundUsers));
		sb.append("'. ");
		sb.append((foundUsers != null) ? foundUsers.size() : "0");
		sb.append(" Entities were found");
		return sb.toString();
	}
}
