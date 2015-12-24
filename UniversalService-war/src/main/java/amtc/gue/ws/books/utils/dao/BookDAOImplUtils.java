package amtc.gue.ws.books.utils.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.persistence.model.TagEntity;
import amtc.gue.ws.books.service.inout.Tags;

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
	public static List<BookEntity> retrieveBookEntitiesWithSpecificTags(
			List<BookEntity> books, Tags searchTags) {
		List<BookEntity> modifiableBookList = copyBookList(books);

		for (Iterator<BookEntity> bookIterator = modifiableBookList.iterator(); bookIterator
				.hasNext();) {
			BookEntity book = bookIterator.next();
			int foundTags = 0;
			for (String tag : searchTags.getTags()) {
				if (book != null && book.getTags() != null
						&& book.getTags().size() > 0) {
					// evaluate how many tags are found in the bookEntity
					for (TagEntity tagEntity : book.getTags()) {
						if (tagEntity.getTagName().equals(tag)) {
							foundTags++;
							break;
						}
					}
				}
			}
			// check if all searchtags were found for the book
			if(foundTags != searchTags.getTags().size()){
				bookIterator.remove();
			}
		}

		// TODO: CLean up
//		for (Iterator<BookEntity> bookIterator = modifiableBookList.iterator(); bookIterator
//				.hasNext();) {
//			BookEntity book = bookIterator.next();
//			for (String tag : searchTags.getTags()) {
//				if (book != null) {
//					// TODO: due to the implementation with contains and the
//					// consolidated tags string in books the contains method is
//					// somehow fuzzy which will be fixed once the data model is
//					// updated.
//					if ((book.getTags() == null)
//							|| (book.getTags() != null && !book.getTags()
//									.contains(tag))) {
//						bookIterator.remove();
//						break;
//					}
//				}
//			}
//		}
		return modifiableBookList;
	}

	/**
	 * Method that copies the content of a specific List to another list
	 * 
	 * @param books
	 *            the list that should be copied
	 * @return the copy of the list
	 */
	public static List<BookEntity> copyBookList(List<BookEntity> books) {
		List<BookEntity> copiedList = new ArrayList<BookEntity>(books);
		return copiedList;
	}

	/**
	 * Method building a specific bookquery depending on the given BookEntity
	 * 
	 * @param initialBookQuery
	 *            the basic bookQuery
	 * @param bookEntity
	 *            the BookEntity that is searched for
	 * @return the built up complete query based on the searched BookEntity
	 */
	public static String buildSpecificBookQuery(String initialBookQuery,
			BookEntity bookEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append(initialBookQuery);
		int initialLength = sb.length();
		if (bookEntity != null) {
			if (bookEntity.getId() != null) {
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
			if (bookEntity.getTags() != null) {
				sb.append(" and be.tags = :tags");
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
