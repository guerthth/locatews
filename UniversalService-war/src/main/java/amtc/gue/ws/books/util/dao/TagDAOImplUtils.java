package amtc.gue.ws.books.util.dao;

import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;

/**
 * Utility class for the TagJPADAOImpl
 * 
 * @author Thomas
 *
 */
public class TagDAOImplUtils {

	/**
	 * Method building a specific tagquery depending on the given TagEntity
	 * 
	 * @param initialTagQuery
	 *            the basic tagQuery
	 * @param tagEntity
	 *            the TagEntity that is searched for
	 * @return the built up complete query based on the search TagEntity
	 */
	public static String buildSpecificTagQuery(String initialTagQuery,
			GAETagEntity tagEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append(initialTagQuery);
		int initialLength = sb.length();
		if(tagEntity != null){
			if(tagEntity.getKey() != null){
				sb.append(" and t.tagname = :id");
			}
		}
		int newLength = sb.length();
		if(initialLength != newLength){
			sb.delete(initialLength, initialLength + 4);
			sb.insert(initialLength, " where");
		}
		return sb.toString();
	}
}
