package amtc.gue.ws.books.util;

import java.util.List;
import java.util.Set;

import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;

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
			List<GAEJPABookEntity> successfullyAddedBookEntities,
			List<GAEJPABookEntity> unSuccessfullyAddedBookEntities) {
		int numberOfSuccessfullyAddedEntities = (successfullyAddedBookEntities != null) ? successfullyAddedBookEntities
				.size() : 0;
		int numberOfUnsuccessfullyAddedEntities = (unSuccessfullyAddedBookEntities != null) ? unSuccessfullyAddedBookEntities
				.size() : 0;
		StringBuilder sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append("'");
		sb.append(BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(successfullyAddedBookEntities));
		sb.append("'.");
		sb.append(" ").append(numberOfSuccessfullyAddedEntities);
		sb.append(" books were successfully added.");
		if (numberOfUnsuccessfullyAddedEntities > 0) {
			sb.append(System.getProperty("line.separator"));
			sb.append("'");
			sb.append(BookServiceErrorConstants.ADD_BOOK_FAILURE_MSG);
			sb.append(BookServiceEntityMapper
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
			Tags searchTags, List<GAEJPABookEntity> foundBooks) {
		StringBuilder sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.RETRIEVE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(searchTags.getTags().toString());
		sb.append("': '");
		sb.append(BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(foundBooks));
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
	public static String buildRemoveBooksSuccessStatusMessage(
			List<GAEJPABookEntity> removedBookEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.DELETE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(removedBookEntities));
		sb.append("'. ");
		sb.append(removedBookEntities.size());
		sb.append(" Entities were removed.");
		return sb.toString();
	}

	/**
	 * Method copying a GAEJPABookEntity. Relationships to tags and users are
	 * not included.
	 * 
	 * @param bookEntity
	 *            the GAEJPABookEntity that should be copied
	 * @return the copy if the GAEJPABookEntity
	 */
	public static GAEJPABookEntity copyBookEntity(GAEJPABookEntity bookEntity) {
		GAEJPABookEntity bookEntityCopy = new GAEJPABookEntity();
		bookEntityCopy.setKey(bookEntity.getKey());
		bookEntityCopy.setAuthor(bookEntity.getAuthor());
		bookEntityCopy.setDescription(bookEntity.getDescription());
		bookEntityCopy.setISBN(bookEntity.getISBN());
		bookEntityCopy.setPrice(bookEntity.getPrice());
		bookEntityCopy.setTags(null, false);
		bookEntityCopy.setTitle(bookEntity.getTitle());
		bookEntityCopy.setUsers(null, false);
		return bookEntityCopy;
	}

	/**
	 * Method that add a GAEJPATagEntity to a specific GAEJPABookEntity if the
	 * tag does not already exist for the book
	 * 
	 * @param bookEntity
	 *            the bookEntity that possible gets a new tag added to its
	 *            tagset
	 * @param tagsToAdd
	 *            the tags that should be added if not existing yet
	 * @return the bookEntity with the updated tagset
	 */
	public static GAEJPABookEntity addTagsToBookEntityIfNotAlreadyExisting(
			GAEJPABookEntity bookEntity, Set<GAEJPATagEntity> tagsToAdd) {
		Set<GAEJPATagEntity> currentlyExistingTags = bookEntity.getTags();
		if (tagsToAdd != null) {
			for (GAEJPATagEntity tagToAdd : tagsToAdd) {
				boolean isNewTag = true;
				for (GAEJPATagEntity existingTag : currentlyExistingTags) {
					if (tagToAdd.getTagName().equals(existingTag.getTagName())) {
						isNewTag = false;
						break;
					}
				}
				if (isNewTag) {
					currentlyExistingTags.add(tagToAdd);
				}
			}
		}
		return bookEntity;
	}
}
