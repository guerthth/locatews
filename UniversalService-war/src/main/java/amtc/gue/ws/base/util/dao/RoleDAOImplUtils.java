package amtc.gue.ws.base.util.dao;

import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;

/**
 * Utility class for the RoleDAOImpl
 * 
 * @author Thomas
 *
 */
public class RoleDAOImplUtils {

	/**
	 * Method building a specific rolequery depending on the given RoleEntity
	 * 
	 * @param initialRoleQuery
	 *            the basic roleQuery
	 * @param roleEntity
	 *            the RoleEntity that is searched for
	 * @return the built up complete query based on the search RoleEntity
	 */
	public static String buildSpecificRoleQuery(String initialRoleQuery,
			GAERoleEntity roleEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append(initialRoleQuery);
		int initialLength = sb.length();
		if (roleEntity != null) {
			if (roleEntity.getKey() != null) {
				sb.append(" and r.role = :role");
			}
			if (roleEntity.getDescription() != null) {
				sb.append(" and r.description = :description");
			}
		}
		int newLength = sb.length();
		if (initialLength != newLength) {
			sb.delete(initialLength, initialLength + 4);
			sb.insert(initialLength, " where");
		}
		return sb.toString();
	}
}
