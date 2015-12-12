package amtc.gue.ws.test.books.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import amtc.gue.ws.books.service.inout.Book;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;

/**
 * Testclass for the BookGrabber Service class
 * 
 * @author Thomas
 *
 */
public class BookGrabberTest {

	private static Books books;
	private static Book firstBook;
	private static Book secondBook;
	private static List<Book> bookList;
	private static List<String> tagList;
	private static Tags tags;

	private static final String searchTag1 = "testtag";

	@BeforeClass
	public static void oneTimeSetup() {

		tagList = new ArrayList<String>();
		tagList.add(searchTag1);

		tags = new Tags();
		tags.setTags(tagList);

		bookList = new ArrayList<Book>();

		firstBook = new Book();
		firstBook.setAuthor("Testauthor1");
		firstBook.setDescription("Testdescription1");
		firstBook.setISBN("TestISBN");
		firstBook.setPrice("100");
		firstBook.setTags(tags);
		firstBook.setTitle("Testtitle1");
		bookList.add(firstBook);

		secondBook = new Book();
		secondBook.setAuthor("Testauthor2");
		secondBook.setDescription("Testdescription2");
		secondBook.setISBN("TestISBN");
		secondBook.setPrice("100");
		secondBook.setTags(tags);
		secondBook.setTitle("Testtitle2");
		bookList.add(secondBook);

		books = new Books();
		books.setBooks(bookList);
	}

	@Test
	public void testAddBooks() {
		assertTrue(true);
	}

}
