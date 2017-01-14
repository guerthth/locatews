package amtc.gue.ws.tournament.util.dao;

import amtc.gue.ws.tournament.persistence.model.GAEJPAPlayerEntity;

/**
 * Utility class for the PlayerDAOImpl
 * 
 * @author Thomas
 *
 */
public class PlayerDAOImplUtils {

	/**
	 * Method building a specific playerquery depending on the given
	 * PlayerEntity
	 * 
	 * @param initialPlayerQuery
	 *            the basic playerQuery
	 * @param playerEntity
	 *            the PlayerEntity that is searched for
	 * @return the built up complete query based on the search PlayerEntity
	 */
	public static String buildSpecificPlayerQuery(String initialPlayerQuery,
			GAEJPAPlayerEntity playerEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append(initialPlayerQuery);
		int inititalLength = sb.length();
		if(playerEntity != null){
			if(playerEntity.getKey() != null){
				sb.append(" and p.playerId = :id");
			}
			if(playerEntity.getPlayerName() != null){
				sb.append(" and p.playerName = :playerName");
			}
		}
		int newLength = sb.length();
		if(inititalLength != newLength){
			sb.delete(inititalLength, inititalLength + 4);
			sb.insert(inititalLength, " where");
		}
		return sb.toString();
	}
}
