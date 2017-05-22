package amtc.gue.ws.base.util.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;

/**
 * Utility class for the UserDAOImpl
 * 
 * @author Thomas
 *
 */
public class UserDAOImplUtils {

	/**
	 * Method that iterates through a list of UserEntities and removes those
	 * which do not contain all specified roles
	 * 
	 * @param userEntities
	 *            the list of UserEntities that should be checked for roles
	 * @param roles
	 *            the roles the UserEntities should possess
	 * @return the list of UserEntities containing only those who do possess all
	 *         the roles
	 */
	public static List<GAEUserEntity> retrieveUserEntitiesWithSpecificRolesOnly(List<GAEUserEntity> userEntities,
			Roles roles) {
		List<GAEUserEntity> modifiableUserEntityList = copyUserList(userEntities);

		for (Iterator<GAEUserEntity> userIterator = modifiableUserEntityList.iterator(); userIterator.hasNext();) {
			GAEUserEntity userEntity = userIterator.next();
			int foundRoles = 0;

			if (roles != null) {
				for (String role : roles.getRoles()) {
					if (userEntity != null && userEntity.getRoles() != null && !userEntity.getRoles().isEmpty()) {
						// evaluate how many Roles are found in the userEntity
						for (GAERoleEntity roleEntity : userEntity.getRoles()) {
							if (roleEntity.getKey() != null && roleEntity.getKey().equals(role)) {
								foundRoles++;
								break;
							}
						}
					}
				}

				// check if all roles were found
				if (foundRoles != roles.getRoles().size()) {
					userIterator.remove();
				}
			}
		}
		return modifiableUserEntityList;
	}

	/**
	 * Method that iterates through a list of UserEntities and removes those
	 * which do not contain at least on of the specified roles
	 * 
	 * @param userEntities
	 *            the list of UserEntities that should be checked for roles
	 * @param roles
	 *            the roles the UserEntities should possess
	 * @return the list of UserEntities containing only those who do possess at
	 *         least one of the roles
	 */
	public static List<GAEUserEntity> retrieveUserEntitiesWithSpecificRoles(List<GAEUserEntity> userEntities,
			Roles roles) {
		List<GAEUserEntity> modifiableUserEntityList = copyUserList(userEntities);

		for (Iterator<GAEUserEntity> userIterator = modifiableUserEntityList.iterator(); userIterator.hasNext();) {
			GAEUserEntity userEntity = userIterator.next();
			int foundRoles = 0;

			if (roles != null && !roles.getRoles().isEmpty()) {
				for (String role : roles.getRoles()) {
					if (userEntity != null && userEntity.getRoles() != null && !userEntity.getRoles().isEmpty()) {
						// evaluate how many Roles are found in the userEntity
						for (GAERoleEntity roleEntity : userEntity.getRoles()) {
							if (roleEntity.getKey() != null && roleEntity.getKey().equals(role)) {
								foundRoles++;
								break;
							}
						}
					}
				}

				// check if all roles were found
				if (foundRoles == 0) {
					userIterator.remove();
				}
			}
		}
		return modifiableUserEntityList;
	}

	/**
	 * Method that copies the content of a specific list
	 * 
	 * @param userEntityListToCopy
	 *            the user list that should be copied
	 * @return the copy of the list
	 */
	public static List<GAEUserEntity> copyUserList(List<GAEUserEntity> userEntityListToCopy) {
		List<GAEUserEntity> copiedList = null;
		if (userEntityListToCopy != null) {
			copiedList = new ArrayList<GAEUserEntity>(userEntityListToCopy);
		}
		return copiedList;
	}

	/**
	 * Method building a specific playerquery depending on the given
	 * PlayerEntity
	 * 
	 * @param initialUserQuery
	 *            the basic userQuery
	 * @param userEntity
	 *            the UserEntity that is searched for
	 * @return the built up complete query based on the search UserEntity
	 */
	public static String buildSpecificUserQuery(String initialUserQuery, GAEUserEntity userEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append(initialUserQuery);
		int initialLength = sb.length();
		if (userEntity != null) {
			if (userEntity.getKey() != null) {
				sb.append(" and u.userId = :id");
			}
			if (userEntity.getPassword() != null) {
				sb.append(" and u.password = :password");
			}
			if (userEntity.getEmail() != null) {
				sb.append(" and u.email = :email");
			}
		}
		int newLength = sb.length();
		if (initialLength != newLength) {
			sb.delete(initialLength, initialLength + 4);
			sb.insert(initialLength, " where");
		}
		return sb.toString();
	}

	public static List<GAEObjectifyUserEntity> searchForUsersManually(List<GAEObjectifyUserEntity> completeUserlist,
			GAEObjectifyUserEntity userEntity) {
		return completeUserlist;
	}
}
