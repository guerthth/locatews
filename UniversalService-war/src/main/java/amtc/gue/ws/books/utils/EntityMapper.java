package amtc.gue.ws.books.utils;

import java.util.ArrayList;
import java.util.List;

import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.inout.Books;
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
	
	/**
	 * Method to map a Books object to a list of BookEntity objects
	 * 
	 * @param books object containing list of Book object
	 * @return list of BookEntity objects
	 */
	public static List<BookEntity> mapBooksToBookList(Books books){
		
		List<BookEntity> bookEntityList = new ArrayList<BookEntity>();
		
		for(Book book : books.getBooks()){
			bookEntityList.add(mapBookToEntity(book));
		}
		
		return bookEntityList;
	}
	
}
