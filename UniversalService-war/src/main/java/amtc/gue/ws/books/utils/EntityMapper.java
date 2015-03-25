package amtc.gue.ws.books.utils;

import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.persistence.model.BookEntity;

/**
 * Class mapping objects to JPA entities
 * @author Thomas
 *
 */
public class EntityMapper {
	
	/**
	 * Method for the mapping of Books
	 * @param book element
	 * @return mapped BookEntity
	 */
	public static BookEntity mapBookToEntity(Book book){
		
		BookEntity bookEntity = new BookEntity();
		bookEntity.setAuthor(book.getAuthor());
		bookEntity.setDescription(book.getDescription());
		bookEntity.setISBN(book.getISBN());
		bookEntity.setPrice(book.getPrice());
		bookEntity.setTags(book.getTags());
		bookEntity.setTitle(book.getTitle());
		
		return bookEntity;
	}
}
