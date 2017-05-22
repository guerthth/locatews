package amtc.gue.ws.books.util.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;

/**
 * Utility class for the BookDAOImpl
 * 
 * @author Thomas
 *
 */
public class BookDAOImplUtils {

	/**
	 * Method that iterates through a list of BookEntities and removes those
	 * which do not contain specific tags
	 * 
	 * @param books
	 *            the list of BookEntities that should be cleaned up
	 * @param searchTags
	 *            the searchtags the BookEntities should possess
	 * @return the list of books containing only those who do possess all the
	 *         tags
	 */
	public static List<GAEBookEntity> retrieveBookEntitiesWithSpecificTags(
			List<GAEBookEntity> books, Tags searchTags) {
		List<GAEBookEntity> modifiableBookList = copyBookList(books);

		if (searchTags != null && !searchTags.getTags().isEmpty()) {
			for (Iterator<GAEBookEntity> bookIterator = modifiableBookList
					.iterator(); bookIterator.hasNext();) {
				GAEBookEntity book = bookIterator.next();
				int foundTags = 0;
				for (String tag : searchTags.getTags()) {
					if (book != null && book.getTags() != null
							&& !book.getTags().isEmpty()) {
						// evaluate how many tags are found in the bookEntity
						for (GAETagEntity tagEntity : book.getTags()) {
							if (BookServiceEntityMapper
									.mapTagsToSimplyfiedString(
											tagEntity.getKey())
									.equals(BookServiceEntityMapper
											.mapTagsToSimplyfiedString(tag))) {
								foundTags++;
								break;
							}
						}
					}
				}
				// check if all searchtags were found for the book
				if (foundTags != searchTags.getTags().size()) {
					bookIterator.remove();
				}
			}
		}

		return modifiableBookList;
	}

	/**
	 * Method that iterates through a list of BookEntities and removes those
	 * which do not contain all of the specified tags
	 * 
	 * @param books
	 *            the list of BookEntities that should be cleaned up
	 * @param searchTags
	 *            the searchtags the BookEntities should possess
	 * @return the list of books containing only those who do possess all the
	 *         tags
	 */
	public static List<GAEBookEntity> retrieveBookEntitiesWithSpecificTagsOnly(
			List<GAEBookEntity> books, Tags searchTags) {
		List<GAEBookEntity> modifiableBookList = copyBookList(books);

		if (searchTags != null && !searchTags.getTags().isEmpty()) {
			for (Iterator<GAEBookEntity> bookIterator = modifiableBookList
					.iterator(); bookIterator.hasNext();) {
				GAEBookEntity book = bookIterator.next();
				int foundTags = 0;
				for (String tag : searchTags.getTags()) {
					if (book != null && book.getTags() != null
							&& book.getTags().size() > 0) {
						// evaluate how many tags are found in the bookEntity
						for (GAETagEntity tagEntity : book.getTags()) {
							if (tagEntity.getKey().equals(tag)) {
								foundTags++;
								break;
							}
						}
					}
				}

				if (foundTags != searchTags.getTags().size()
						|| (book != null && (book.getTags().size() != foundTags))) {
					bookIterator.remove();
				}
			}
		}

		return modifiableBookList;
	}

	/**
	 * Method that copies the content of a specific List to another list
	 * 
	 * @param bookEntityListToCopy
	 *            the list that should be copied
	 * @return the copy of the list
	 */
	public static List<GAEBookEntity> copyBookList(
			List<GAEBookEntity> bookEntityListToCopy) {
		List<GAEBookEntity> copiedList = null;
		if (bookEntityListToCopy != null) {
			copiedList = new ArrayList<>(bookEntityListToCopy);
		}
		return copiedList;
	}

	/**
	 * Method building a specific bookquery depending on the given BookEntity
	 * 
	 * @param initialBookQuery
	 *            the basic bookQuery
	 * @param bookEntity
	 *            the BookEntity that is searched for
	 * @return the built up complete query based on the searched BookEntity note
	 *         that tag comparison is not included here. The reason therefore is
	 *         that there are separate tag search operations
	 */
	public static String buildSpecificBookQuery(String initialBookQuery,
			GAEBookEntity bookEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append(initialBookQuery);
		int initialLength = sb.length();
		if (bookEntity != null) {
			if (bookEntity.getKey() != null && bookEntity.getKey().length() > 0) {
				sb.append(" and e.bookId = :id");
			}
			if (bookEntity.getTitle() != null) {
				sb.append(" and e.title = :title");
			}
			if (bookEntity.getAuthor() != null) {
				sb.append(" and e.author = :author");
			}
			if (bookEntity.getPrice() != null) {
				sb.append(" and e.price = :price");
			}
			if (bookEntity.getISBN() != null) {
				sb.append(" and e.ISBN = :ISBN");
			}
			if (bookEntity.getDescription() != null) {
				sb.append(" and e.description = :description");
			}
		}
		int newLength = sb.length();
		if (initialLength != newLength) {
			if (sb.indexOf("where") == -1) {
				sb.delete(initialLength, initialLength + 4);
				sb.insert(initialLength, " where");
			}
		}
		return sb.toString();
	}

}
