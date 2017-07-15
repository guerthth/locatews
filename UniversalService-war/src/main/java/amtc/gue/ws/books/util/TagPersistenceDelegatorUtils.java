package amtc.gue.ws.books.util;

import java.util.List;

import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;

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
	 * @return the status message that can be used in the response as String
	 */
	public static String buildRetrieveTagsSuccessStatusMessage(
			List<GAETagEntity> foundTags) {
		StringBuilder sb = new StringBuilder();
		sb.append(BooksErrorConstants.RETRIEVE_TAGS_SUCCESS_MSG);
		sb.append(" '");
		sb.append(BookServiceEntityMapper
				.mapTagEntityListToConsolidatedJSONString(foundTags));
		sb.append("'. ");
		sb.append(foundTags.size());
		sb.append(" Entities were found");
		return sb.toString();
	}
}
