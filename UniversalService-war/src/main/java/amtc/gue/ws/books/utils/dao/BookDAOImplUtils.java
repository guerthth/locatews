package amtc.gue.ws.books.utils.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.utils.EntityMapper;

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
	 * @return the list if books containing only those who do possess all the
	 *         tags
	 */
	public static List<GAEJPABookEntity> retrieveBookEntitiesWithSpecificTags(
			List<GAEJPABookEntity> books, Tags searchTags) {
		List<GAEJPABookEntity> modifiableBookList = copyBookList(books);

		for (Iterator<GAEJPABookEntity> bookIterator = modifiableBookList
				.iterator(); bookIterator.hasNext();) {
			GAEJPABookEntity book = bookIterator.next();
			int foundTags = 0;
			for (String tag : searchTags.getTags()) {
				if (book != null && book.getTags() != null
						&& book.getTags().size() > 0) {
					// evaluate how many tags are found in the bookEntity
					for (GAEJPATagEntity tagEntity : book.getTags()) {
						if (EntityMapper.mapTagsToSimplyfiedString(
								tagEntity.getTagName()).equals(
								EntityMapper.mapTagsToSimplyfiedString(tag))) {
							foundTags++;
							break;
						}
					}
				}
			}
			// check if all searchtags were found for the book
			// we have an issue here when trying to delete books possesing ALL
			// of the tags
			if (foundTags != searchTags.getTags().size()) {
				bookIterator.remove();
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
	 * @return the list if books containing only those who do possess all the
	 *         tags
	 */
	public static List<GAEJPABookEntity> retrieveBookEntitiesWithSpecificTagsOnly(
			List<GAEJPABookEntity> books, Tags searchTags) {
		List<GAEJPABookEntity> modifiableBookList = copyBookList(books);

		for (Iterator<GAEJPABookEntity> bookIterator = modifiableBookList
				.iterator(); bookIterator.hasNext();) {
			GAEJPABookEntity book = bookIterator.next();
			int foundTags = 0;
			for (String tag : searchTags.getTags()) {
				if (book != null && book.getTags() != null
						&& book.getTags().size() > 0) {
					// evaluate how many tags are found in the bookEntity
					for (GAEJPATagEntity tagEntity : book.getTags()) {
						if (tagEntity.getTagName().equals(tag)) {
							foundTags++;
							break;
						}
					}
				}
			}
			// check if all searchtags were found for the book
			// we have an issue here when trying to delete books possesing ALL
			// of the tags
			if (foundTags != searchTags.getTags().size()
					|| (book != null && (book.getTags().size() != foundTags))) {
				bookIterator.remove();
			}
		}
		return modifiableBookList;
	}

	/**
	 * Method that copies the content of a specific List to another list
	 * 
	 * @param bookListToCopy
	 *            the list that should be copied
	 * @return the copy of the list
	 */
	public static List<GAEJPABookEntity> copyBookList(
			List<GAEJPABookEntity> bookListToCopy) {
		List<GAEJPABookEntity> copiedList = null;
		if (bookListToCopy != null) {
			copiedList = new ArrayList<GAEJPABookEntity>(bookListToCopy);
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
			GAEJPABookEntity bookEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append(initialBookQuery);
		int initialLength = sb.length();
		if (bookEntity != null) {
			if (bookEntity.getKey() != null && bookEntity.getKey().length() > 0) {
				sb.append(" and be.bookId = :id");
			}
			if (bookEntity.getTitle() != null) {
				sb.append(" and be.title = :title");
			}
			if (bookEntity.getAuthor() != null) {
				sb.append(" and be.author = :author");
			}
			if (bookEntity.getPrice() != null) {
				sb.append(" and be.price = :price");
			}
			if (bookEntity.getISBN() != null) {
				sb.append(" and be.ISBN = :ISBN");
			}
			if (bookEntity.getDescription() != null) {
				sb.append(" and be.description = :description");
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
