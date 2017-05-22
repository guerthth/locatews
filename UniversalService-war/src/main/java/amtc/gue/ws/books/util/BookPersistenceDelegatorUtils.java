package amtc.gue.ws.books.util;

import java.util.List;
import java.util.Set;

import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;

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
	public static String buildPersistBookSuccessStatusMessage(List<GAEBookEntity> successfullyAddedBookEntities,
			List<GAEBookEntity> unSuccessfullyAddedBookEntities) {
		int numberOfSuccessfullyAddedEntities = (successfullyAddedBookEntities != null)
				? successfullyAddedBookEntities.size() : 0;
		int numberOfUnsuccessfullyAddedEntities = (unSuccessfullyAddedBookEntities != null)
				? unSuccessfullyAddedBookEntities.size() : 0;
		StringBuilder sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(successfullyAddedBookEntities));
		sb.append("'.");
		sb.append(" ").append(numberOfSuccessfullyAddedEntities);
		sb.append(" books were successfully added.");
		if (numberOfUnsuccessfullyAddedEntities > 0) {
			sb.append(System.getProperty("line.separator"));
			sb.append("'");
			sb.append(BookServiceErrorConstants.ADD_BOOK_FAILURE_MSG);
			sb.append(
					BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(unSuccessfullyAddedBookEntities));
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
	public static String buildGetBooksByTagSuccessStatusMessage(Tags searchTags, List<GAEBookEntity> foundBooks) {
		StringBuilder sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.RETRIEVE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(searchTags.getTags().toString());
		sb.append("': '");
		sb.append(BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(foundBooks));
		sb.append("'. ");
		sb.append((foundBooks != null) ? foundBooks.size() : "0");
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
	public static String buildRemoveBooksSuccessStatusMessage(List<GAEBookEntity> removedBookEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.DELETE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(removedBookEntities));
		sb.append("'. ");
		sb.append(removedBookEntities.size());
		sb.append(" Entities were removed.");
		return sb.toString();
	}

	/**
	 * Method that add a GAETagEntity to a specific GAEBookEntity if the tag
	 * does not already exist for the book
	 * 
	 * @param bookEntity
	 *            the bookEntity that possible gets a new tag added to its
	 *            tagset
	 * @param tagsToAdd
	 *            the tags that should be added if not existing yet
	 * @return the bookEntity with the updated tagset
	 */
	public static GAEBookEntity addTagsToBookEntityIfNotAlreadyExisting(GAEBookEntity bookEntity,
			Set<GAETagEntity> tagsToAdd) {
		if (tagsToAdd != null) {
			for (GAETagEntity tagToAdd : tagsToAdd) {
				boolean isNewTag = true;
				for (GAETagEntity existingTag : bookEntity.getTags()) {
					if (existingTag != null && tagToAdd.getKey().equals(existingTag.getKey())) {
						isNewTag = false;
						break;
					}
				}
				if (isNewTag) {
					bookEntity.addToTagsOnly(tagToAdd);
				}
			}
		}
		return bookEntity;
	}
}
