package amtc.gue.ws.books.utils;

import java.util.List;

import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.service.inout.Tags;

/**
 * Util class for the BookPersistenceDelegator
 * 
 * @author Thomas
 *
 */
public class BookPersistenceDelegatorUtils {

	/**
	 * Method building the String status message used in the response for the
	 * persistance of books
	 * 
	 * @param successfullyAddedBookEntities
	 *            a list of successfully added BoobEntities
	 * @param unSuccessfullyAddedBookEntities
	 *            a list of BookEntities that was not added successfully
	 * @return the status message that can be used in the response as String
	 */
	public static String buildPersistBookSuccessStatusMessage(
			List<BookEntity> successfullyAddedBookEntities,
			List<BookEntity> unSuccessfullyAddedBookEntities) {
		int numberOfSuccessfullyAddedEntities = (successfullyAddedBookEntities != null) ? successfullyAddedBookEntities
				.size() : 0;
		int numberOfUnsuccessfullyAddedEntities = (unSuccessfullyAddedBookEntities != null) ? unSuccessfullyAddedBookEntities
				.size() : 0;
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append("'");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(successfullyAddedBookEntities));
		sb.append("'.");
		sb.append(" ").append(numberOfSuccessfullyAddedEntities);
		sb.append(" books were successfully added.");
		if (numberOfUnsuccessfullyAddedEntities > 0) {
			sb.append(System.getProperty("line.separator"));
			sb.append("'");
			sb.append(ErrorConstants.ADD_BOOK_FAILURE_MSG);
			sb.append(EntityMapper
					.mapBookEntityListToConsolidatedJSONString(unSuccessfullyAddedBookEntities));
			sb.append("'.");
			sb.append(" ").append(numberOfUnsuccessfullyAddedEntities);
			sb.append(" books were not added successfully.");
		}
		return sb.toString();
	}

	/**
	 * Method building the String status message used in the response when
	 * retrieving BookEntities by specific search tags
	 * 
	 * @param searchTags
	 *            the search tags that were used
	 * @param foundBooks
	 *            a list of BookEntities that was found based on the search tags
	 * @return the status message that can bed used in the response as String
	 */
	public static String buildGetBooksByTagSuccessStatusMessage(
			Tags searchTags, List<BookEntity> foundBooks) {
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.RETRIEVE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(searchTags.getTags().toString());
		sb.append("': '");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(foundBooks));
		sb.append("'. ");
		sb.append(foundBooks.size());
		sb.append(" Entities were found.");
		return sb.toString();
	}

	/**
	 * Method building the String status message used in the response when
	 * removing BookEntities from the datastore
	 * 
	 * @param removedBookEntities
	 *            a list of removed BookEntities
	 * @return the status message that can bed used in the response as String
	 */
	public static String buildRemoveBooksSuccessStatusMessage(
			List<BookEntity> removedBookEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.DELETE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(removedBookEntities));
		sb.append("'. ");
		sb.append(removedBookEntities.size());
		sb.append(" Entities were removed.");
		return sb.toString();
	}
}
