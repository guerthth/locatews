package amtc.gue.ws.base.util;

import java.util.List;

import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;

/**
 * Utility class for the UserPersistenceDelegator
 * 
 * @author Thomas
 *
 */
public class UserPersistenceDelegatorUtils {
	/**
	 * Method building the String status message for successful removal of User
	 * entities
	 * 
	 * @param successfullyAddedUserEntities
	 *            a list of successfully added UserEntities
	 * @param unsuccessfullyAddedUserEntities
	 *            a list of unsuccessfully added UserEntities
	 * @return success status message for user entity removal
	 */
	public static String buildPersistUserSuccessStatusMessage(List<GAEUserEntity> successfullyAddedUserEntities,
			List<GAEUserEntity> unsuccessfullyAddedUserEntities) {
		int numberOfSuccessfullyAddedEntities = (successfullyAddedUserEntities != null)
				? successfullyAddedUserEntities.size() : 0;
		int numberOfUnsuccessfullyAddedEntities = (successfullyAddedUserEntities != null)
				? unsuccessfullyAddedUserEntities.size() : 0;
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.ADD_USER_SUCCESS_MSG);
		sb.append(" '");
		sb.append(UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(successfullyAddedUserEntities));
		sb.append("'. ").append(numberOfSuccessfullyAddedEntities).append(" users were successfully added.");
		if (numberOfUnsuccessfullyAddedEntities > 0) {
			sb.append(System.getProperty("line.separator"));
			sb.append(ErrorConstants.ADD_USER_FAILURE_MSG);
			sb.append(" '");
			sb.append(
					UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(unsuccessfullyAddedUserEntities));
			sb.append("'. ").append(numberOfUnsuccessfullyAddedEntities).append(" users were not added successfully.");
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
	public static String buildRemoveUsersSuccessStatusMessage(List<GAEUserEntity> removedUserEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.DELETE_USER_SUCCESS_MSG);
		sb.append(" '");
		sb.append(UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(removedUserEntities));
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
	public static String buildGetUsersByRoleSuccessStatusMessage(Roles roles, List<GAEUserEntity> foundUsers) {
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.RETRIEVE_USER_FOR_ROLES_SUCCESS_MSG);
		sb.append(" '");
		sb.append((roles != null && roles.getRoles() != null) ? roles.getRoles().toString() : "[]");
		sb.append("': '");
		sb.append(UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(foundUsers));
		sb.append("'. ");
		sb.append((foundUsers != null) ? foundUsers.size() : "0");
		sb.append(" Entities were found");
		return sb.toString();
	}

	/**
	 * Method building the String status message for successful retrieval of a
	 * specific UserEntity from the datastore
	 * 
	 * @param foundUser
	 *            the user that was found
	 * @return the created status message
	 */
	public static String buildGetUsersByIdSuccessStatusMessage(String userName, GAEUserEntity foundUser) {
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.RETRIEVE_USER_BY_ID_SUCCESS_MSG);
		sb.append(" '");
		sb.append(userName);
		sb.append("': '");
		sb.append(UserServiceEntityMapper.mapUserEntityToJSONString(foundUser));
		sb.append("'.");
		return sb.toString();
	}

	/**
	 * Method building the String status message for successful pw reset for
	 * userentities
	 * 
	 * @param successfullyUpdatedUserEntities
	 *            a list of successfully updated UserEntities
	 * @param unsuccessfullyUpdatedUserEntities
	 *            a list of unsuccessfully updated UserEntities
	 * @return the created String status message
	 */
	public static String buildResetPwSuccessStatusMessage(List<GAEUserEntity> successfullyUpdatedUserEntities,
			List<GAEUserEntity> unsuccessfullyUpdatedUserEntities) {
		int numberOfSuccessfullyAddedEntities = (successfullyUpdatedUserEntities != null)
				? successfullyUpdatedUserEntities.size() : 0;
		int numberOfUnsuccessfullyAddedEntities = (successfullyUpdatedUserEntities != null)
				? successfullyUpdatedUserEntities.size() : 0;
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.UPDATE_USER_SUCCESS_MSG);
		sb.append(" '");
		sb.append(UserServiceEntityMapper.mapUserEntityListToConsolidatedJSONString(successfullyUpdatedUserEntities));
		sb.append("'. ").append(numberOfSuccessfullyAddedEntities)
				.append(" user password resets were successfully added.");
		if (numberOfUnsuccessfullyAddedEntities > 0) {
			sb.append(System.getProperty("line.seperator"));
			sb.append("'");
			sb.append(ErrorConstants.UPDATE_USER_FAILURE_MSG);
			sb.append(UserServiceEntityMapper
					.mapUserEntityListToConsolidatedJSONString(unsuccessfullyUpdatedUserEntities));
			sb.append("'. ").append(numberOfUnsuccessfullyAddedEntities)
					.append(" user password resets were not added successfully.");
		}
		return sb.toString();
	}
}
