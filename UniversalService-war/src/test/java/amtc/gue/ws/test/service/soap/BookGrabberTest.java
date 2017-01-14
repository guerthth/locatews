package amtc.gue.ws.test.service.soap;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.service.soap.BookGrabber;

/**
 * Testclass for the BookGrabber Service class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookGrabberTest {
	private static Books books;
	private static Book firstBook;
	private static Book secondBook;
	private static List<Book> bookList;
	private static List<String> tagList;
	private static Tags tags;

	private static BookGrabber bookGrabber;

	private static final String searchTag1 = "testtag";

	@BeforeClass
	public static void oneTimeSetup() {

		bookGrabber = new BookGrabber();

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
		firstBook.setTags(tags.getTags());
		firstBook.setTitle("Testtitle1");
		bookList.add(firstBook);

		secondBook = new Book();
		secondBook.setAuthor("Testauthor2");
		secondBook.setDescription("Testdescription2");
		secondBook.setISBN("TestISBN");
		secondBook.setPrice("100");
		secondBook.setTags(tags.getTags());
		secondBook.setTitle("Testtitle2");
		bookList.add(secondBook);

		books = new Books();
		books.setBooks(bookList);
	}

	@Test
	public void testAddBooks() {
		assertNotNull(bookGrabber.addBooks(books));
	}

	@Test
	public void testGetBooksByTags() {
		assertNotNull(bookGrabber.getBooksByTag(tags));
	}

	@Test
	public void testRemoveBooks() {
		assertNotNull(bookGrabber.removeBooks(books));
	}

	@Test
	public void testGetTags() {
		assertNotNull(bookGrabber.getTags());
	}

}
