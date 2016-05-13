package amtc.gue.ws.books.utils;

import java.util.List;

import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;

/**
 * Util class for the TagPersistenceDelegator
 * 
 * @author Thomas
 *
 */
public class TagPersistenceDelegatorUtils {

	/**
	 * Method building the String status message used in the response when
	 * retrieving all TagEntities
	 * 
	 * @param foundTags
	 *            the tags that were found
	 * @return the status message that can bed used in the response as String
	 */
	public static String buildRetrieveTagsSuccessStatusMessage(
			List<GAEJPATagEntity> foundTags) {
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.RETRIEVE_TAGS_SUCCESS_MSG);
		sb.append(" '");
		sb.append(EntityMapper
				.mapTagEntityListToConsolidatedJSONString(foundTags));
		sb.append("'. ");
		sb.append(foundTags.size());
		sb.append(" Entities were found");
		return sb.toString();
	}
}
