package amtc.gue.ws.books.util.dao;

import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;

/**
 * Utility class for the TagDAOImpl
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
			GAEJPATagEntity tagEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append(initialTagQuery);
		int initialLength = sb.length();
		if(tagEntity != null){
			if(tagEntity.getKey() != null){
				sb.append(" and t.tagId = :id");
			}
			if(tagEntity.getTagName() != null){
				sb.append(" and t.tagname = :tagname");
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
