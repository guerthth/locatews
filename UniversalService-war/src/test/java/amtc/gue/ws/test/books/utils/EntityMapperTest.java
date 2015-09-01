package amtc.gue.ws.test.books.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.service.inout.Book;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.utils.EntityMapper;

/**
 * EntityMapper Testclass
 * 
 * @author Thomas
 *
 */
public class EntityMapperTest {

	private static Book firstBook; 
	private static Book secondBook;
	private static Books books;
	private static List<Book> listOfBooks;
	
	@BeforeClass
	public static void oneTimeSetUp(){
		
		initializeBooks();
	}
	
	/**
	 * Initialize Books
	 */
	private static void initializeBooks(){
		firstBook = new Book();
		
		secondBook = new Book();
		
		listOfBooks = new ArrayList<Book>();
		listOfBooks.add(firstBook);
		listOfBooks.add(secondBook);
		
		books = new Books();
		books.setBooks(listOfBooks);
	}
	
	@Test
	public void testMapToBookEntity(){
		BookEntity bookEntity = EntityMapper.mapBookToEntity(firstBook);
		assertTrue(bookEntity != null);
		assertEquals(firstBook.getAuthor(), bookEntity.getAuthor());
		assertEquals(firstBook.getDescription(), bookEntity.getDescription());
		assertEquals(firstBook.getISBN(), bookEntity.getISBN());
		assertEquals(firstBook.getPrice(), bookEntity.getISBN());
		assertEquals(firstBook.getTags(), bookEntity.getTags());
		assertEquals(firstBook.getTitle(), bookEntity.getTitle());
	}
	
	@Test
	public void testTransformBooksToEntites(){
		List<BookEntity> bookEntityList = EntityMapper.transformBooksToBookEntities(books);
		assertTrue(bookEntityList != null);
	}
}
