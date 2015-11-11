package amtc.gue.ws.books.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.service.inout.Tags;

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
			for (String tag : searchTags.getTags()) {
				if (book != null) {
					if ((book.getTags() == null)
							|| (book.getTags() != null && !book.getTags()
									.contains(tag))) {
						bookIterator.remove();
						break;
					}
				}
			}
		}
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
}
