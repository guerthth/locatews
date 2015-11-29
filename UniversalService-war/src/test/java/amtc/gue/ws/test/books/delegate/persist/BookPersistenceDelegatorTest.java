package amtc.gue.ws.test.books.delegate.persist;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.service.inout.Book;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.utils.ErrorConstants;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;

/**
 * Testclass for the BookPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookPersistenceDelegatorTest {

	private static BookPersistenceDelegator bookPersistenceDelegator;
	private static PersistenceDelegatorInput addDelegatorInput;
	private static PersistenceDelegatorInput readDelegatorInput;
	private static PersistenceDelegatorInput invalidReadDelegatorInput;
	private static PersistenceDelegatorInput invalidAddDelegatorInput;
	
	private static Books books;
	private static Book firstBook;
	private static Book secondBook;
	private static List<Book> bookList;
	private static Tags searchTags;
	private static List<String> tagList;

	private static BookDAO bookDAOImpl;
	private static BookDAO bookDAOImplFail;

	private static BookEntity retrievedBookEntity;
	private static List<BookEntity> retrievedBookEntityList;

	private static final String searchTag1 = "testtag";

	@BeforeClass
	public static void oneTimeSetUp() throws EntityPersistenceException, EntityRetrievalException {

		// Tags object setup
		setupTags();
		
		// Books object setup
		setupBooks();

		// Delegatorinput setup
		setUpDelegatorInputs();

		// DAO Impl mock setup
		setUpDAOMocks();

		bookPersistenceDelegator = new BookPersistenceDelegator();
	}

	/**
	 * Set up Books
	 */
	private static void setupBooks() {

		bookList = new ArrayList<Book>();

		firstBook = new Book();
		firstBook.setAuthor("Testauthor1");
		firstBook.setDescription("Testdescription1");
		firstBook.setISBN("TestISBN");
		firstBook.setPrice("100");
		firstBook.setTags(searchTags);
		firstBook.setTitle("Testtitle1");
		bookList.add(firstBook);

		secondBook = new Book();
		secondBook.setAuthor("Testauthor2");
		secondBook.setDescription("Testdescription2");
		secondBook.setISBN("TestISBN");
		secondBook.setPrice("100");
		secondBook.setTags(searchTags);
		secondBook.setTitle("Testtitle2");
		// bookList.add(secondBook);

		books = new Books();
		books.setBooks(bookList);
	}
	
	/**
	 * Set up tags
	 */
	private static void setupTags(){
		tagList = new ArrayList<String>();
		searchTags = new Tags();
		tagList.add(searchTag1);
		searchTags.setTags(tagList);
	}

	/**
	 * Set up DelegatorInput
	 */
	private static void setUpDelegatorInputs() {

		// DelegatorInput for entity adding
		addDelegatorInput = new PersistenceDelegatorInput();
		addDelegatorInput.setInputObject(books);
		addDelegatorInput.setType(PersistenceTypeEnum.ADD);

		// DelegatorInput for entity reading
		readDelegatorInput = new PersistenceDelegatorInput();
		readDelegatorInput.setInputObject(searchTags);
		readDelegatorInput.setType(PersistenceTypeEnum.READ);
		
		// DelegatorInput for entity reading with invalid input
		invalidReadDelegatorInput = new PersistenceDelegatorInput();
		invalidReadDelegatorInput.setInputObject(books);
		invalidReadDelegatorInput.setType(PersistenceTypeEnum.READ);
		
		// DelefatorInput for entity adding with invalid input
		invalidAddDelegatorInput = new PersistenceDelegatorInput();
		invalidAddDelegatorInput.setInputObject(searchTags);
		invalidAddDelegatorInput.setType(PersistenceTypeEnum.ADD);
	}

	/**
	 * Set up the DAP Implementation Mocks
	 * 
	 * @throws EntityPersistenceException
	 * @throws EntityRetrievalException 
	 */
	private static void setUpDAOMocks() throws EntityPersistenceException, EntityRetrievalException {

		// setup the BookEntities that should be returned
		setUpRetrievedBookEntities();

		// Positive Scenario mock
		bookDAOImpl = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImpl.persistEntity(EasyMock.isA(BookEntity.class)))
				.andReturn(retrievedBookEntity);
		EasyMock.expect(bookDAOImpl.getBookEntityByTag(searchTags)).andReturn(retrievedBookEntityList);
		EasyMock.replay(bookDAOImpl);
		
		// Negative Scenario mock
		bookDAOImplFail = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImplFail.persistEntity(EasyMock.isA(BookEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(bookDAOImplFail.getBookEntityByTag(searchTags)).andThrow(new EntityRetrievalException());
		EasyMock.replay(bookDAOImplFail);
	}

	/**
	 * Set up the BookEntity that should be returned
	 */
	private static void setUpRetrievedBookEntities() {

		retrievedBookEntity = new BookEntity();
		retrievedBookEntityList = new ArrayList<BookEntity>();
		retrievedBookEntity.setId(1L);
		retrievedBookEntity.setAuthor("ReturnAuthor");
		retrievedBookEntity.setDescription("Testdescription");
		retrievedBookEntity.setTags(null);
		
		retrievedBookEntityList.add(retrievedBookEntity);
	}

	@Test
	public void testDelegateAdd1() {

		try {
			bookPersistenceDelegator.initialize(addDelegatorInput, bookDAOImpl);
			IDelegatorOutput delegatorOutput = bookPersistenceDelegator
					.delegate();
			assertEquals(10, delegatorOutput.getStatusCode());
			assertTrue(delegatorOutput.getStatusMessage().startsWith(
					"Added books"));
			assertTrue(delegatorOutput.getOutputObject() != null);
		} catch (Exception e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testDelegateAdd2() {
		bookPersistenceDelegator.initialize(addDelegatorInput, bookDAOImplFail);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator
				.delegate();
		assertEquals(ErrorConstants.ADD_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
	}
	
	@Test
	public void testDelegateAdd3(){
		bookPersistenceDelegator.initialize(invalidAddDelegatorInput, bookDAOImpl);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator
				.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,delegatorOutput.getStatusCode());
	}

//	@Test
//	public void testDelegateRead1() {
//		bookPersistenceDelegator.initialize(readDelegatorInput, bookDAOImpl);
//		IDelegatorOutput delegatorOutput = bookPersistenceDelegator
//				.delegate();
//		assertEquals(ErrorConstants.RETRIEVE_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
//	}
	
	@Test
	public void testDelegateRead2() {
		bookPersistenceDelegator.initialize(readDelegatorInput, bookDAOImplFail);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator
				.delegate();
		assertEquals(ErrorConstants.RETRIEVE_BOOK_FAILURE_CODE,delegatorOutput.getStatusCode());
	}
	
	@Test
	public void testDelegateRead3() {
		bookPersistenceDelegator.initialize(invalidReadDelegatorInput, bookDAOImpl);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator
				.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}
	
	@AfterClass
	public static void tearDown(){
		EasyMock.verify(bookDAOImplFail);
	}
}
