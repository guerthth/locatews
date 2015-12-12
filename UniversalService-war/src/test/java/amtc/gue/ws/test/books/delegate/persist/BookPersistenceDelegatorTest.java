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
import amtc.gue.ws.books.delegate.persist.exception.EntityRemovalException;
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
	private static PersistenceDelegatorInput deleteDelegatorInput;
	private static PersistenceDelegatorInput invalidReadDelegatorInput;
	private static PersistenceDelegatorInput invalidAddDelegatorInput;
	private static PersistenceDelegatorInput invalidDeleteDelegatorInput;
	private static PersistenceDelegatorInput deleteDelegatorInputWithId;
	private static PersistenceDelegatorInput unrecognizedDelegatorInput;

	private static Books books;
	private static Books booksWithId;
	private static Book firstBook;
	private static Book secondBook;
	private static List<Book> bookList;
	private static List<Book> bookListWithId;
	private static Tags searchTags;
	private static List<String> tagList;

	private static BookDAO bookDAOImpl;
	private static BookDAO bookDAOImplIdRemoval;
	private static BookDAO bookDAOImplGeneralFail;
	private static BookDAO bookDAOImplNonExistingDeletionsFail;
	private static BookDAO bookDAOImplRetrieveDeletionFail;

	private static BookEntity retrievedBookEntity;
	private static List<BookEntity> retrievedBookEntityList;
	private static BookEntity removedBookEntity;
	private static BookEntity foundIdBookEntity;
	private static List<BookEntity> removedBookEntityList;
	private static List<BookEntity> emptyBookEntityList;

	private static final String searchTag1 = "testtag";

	@BeforeClass
	public static void oneTimeSetUp() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {

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
		bookListWithId = new ArrayList<Book>();

		firstBook = new Book();
		firstBook.setAuthor("Testauthor1");
		firstBook.setDescription("Testdescription1");
		firstBook.setISBN("TestISBN");
		firstBook.setPrice("100");
		firstBook.setTags(searchTags);
		firstBook.setTitle("Testtitle1");
		bookList.add(firstBook);

		secondBook = new Book();
		secondBook.setId(2L);
		secondBook.setAuthor("Testauthor2");
		secondBook.setDescription("Testdescription2");
		secondBook.setISBN("TestISBN");
		secondBook.setPrice("100");
		secondBook.setTags(searchTags);
		secondBook.setTitle("Testtitle2");
		bookListWithId.add(secondBook);

		books = new Books();
		books.setBooks(bookList);
		booksWithId = new Books();
		booksWithId.setBooks(bookListWithId);
	}

	/**
	 * Set up tags
	 */
	private static void setupTags() {
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

		// DelegatorInput for entity adding with invalid input
		invalidAddDelegatorInput = new PersistenceDelegatorInput();
		invalidAddDelegatorInput.setInputObject(searchTags);
		invalidAddDelegatorInput.setType(PersistenceTypeEnum.ADD);

		// DelegatorInput for entity deletion
		deleteDelegatorInput = new PersistenceDelegatorInput();
		deleteDelegatorInput.setInputObject(books);
		deleteDelegatorInput.setType(PersistenceTypeEnum.DELETE);

		// DelegatorInput for entity deletion with invalid input
		invalidDeleteDelegatorInput = new PersistenceDelegatorInput();
		invalidDeleteDelegatorInput.setInputObject(searchTags);
		invalidDeleteDelegatorInput.setType(PersistenceTypeEnum.DELETE);

		// DelegatorInput with unrecognized input type
		unrecognizedDelegatorInput = new PersistenceDelegatorInput();
		unrecognizedDelegatorInput.setInputObject(books);
		unrecognizedDelegatorInput.setType(PersistenceTypeEnum.UNRECOGNIZED);

		// DelegatorInput with ID book input type
		deleteDelegatorInputWithId = new PersistenceDelegatorInput();
		deleteDelegatorInputWithId.setInputObject(booksWithId);
		deleteDelegatorInputWithId.setType(PersistenceTypeEnum.DELETE);
	}

	/**
	 * Set up the DAO Implementation Mocks
	 * 
	 * @throws EntityPersistenceException
	 * @throws EntityRetrievalException
	 * @throws EntityRemovalException
	 */
	private static void setUpDAOMocks() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {

		// setup the BookEntities that should be returned or removed
		setUpRetrievedBookEntities();
		setUpRemovedBookEntities();

		// Positive Scenario mock
		bookDAOImpl = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImpl.persistEntity(EasyMock.isA(BookEntity.class)))
				.andReturn(retrievedBookEntity);
		EasyMock.expect(bookDAOImpl.getBookEntityByTag(searchTags)).andReturn(
				retrievedBookEntityList);
		EasyMock.expect(
				bookDAOImpl.findSpecificEntity(EasyMock.isA(BookEntity.class)))
				.andReturn(retrievedBookEntityList);
		EasyMock.expect(
				bookDAOImpl.removeEntity(EasyMock.isA(BookEntity.class)))
				.andReturn(removedBookEntity);
		EasyMock.replay(bookDAOImpl);

		// Positive Scenario mock for book removal with Id
		bookDAOImplIdRemoval = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImplIdRemoval.findSpecificEntity(EasyMock.isA(BookEntity.class)))
				.andReturn(retrievedBookEntityList);
		EasyMock.expect(
				bookDAOImplIdRemoval.removeEntity(EasyMock
						.isA(BookEntity.class))).andReturn(removedBookEntity);
		EasyMock.replay(bookDAOImplIdRemoval);

		// Negative Scenario mock (general scenario)
		bookDAOImplGeneralFail = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImplGeneralFail.persistEntity(EasyMock
						.isA(BookEntity.class))).andThrow(
				new EntityPersistenceException());
		EasyMock.expect(bookDAOImplGeneralFail.getBookEntityByTag(searchTags))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(
				bookDAOImplGeneralFail.findSpecificEntity(EasyMock
						.isA(BookEntity.class))).andReturn(
				retrievedBookEntityList);
		EasyMock.expect(
				bookDAOImplGeneralFail.removeEntity(EasyMock
						.isA(BookEntity.class))).andThrow(
				new EntityRemovalException());
		EasyMock.replay(bookDAOImplGeneralFail);

		// Negative scenario mock simulating that entities to be removed are not
		// existing
		bookDAOImplNonExistingDeletionsFail = EasyMock
				.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImplNonExistingDeletionsFail.findSpecificEntity(EasyMock
						.isA(BookEntity.class))).andReturn(emptyBookEntityList);
		EasyMock.replay(bookDAOImplNonExistingDeletionsFail);

		// Negative scenario mock simulation failure on entity removal
		bookDAOImplRetrieveDeletionFail = EasyMock
				.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImplRetrieveDeletionFail.findSpecificEntity(EasyMock
						.isA(BookEntity.class))).andThrow(
				new EntityRetrievalException());
		EasyMock.replay(bookDAOImplRetrieveDeletionFail);
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

		foundIdBookEntity = new BookEntity();
		foundIdBookEntity.setId(1L);
		foundIdBookEntity.setAuthor("ReturnAuthor");
		foundIdBookEntity.setDescription("Testdescription");
		foundIdBookEntity.setTags(null);
	}

	/**
	 * Set up the BookEntity that should be removed
	 */
	private static void setUpRemovedBookEntities() {
		removedBookEntity = new BookEntity();
		removedBookEntityList = new ArrayList<BookEntity>();
		removedBookEntity.setId(1L);
		removedBookEntity.setAuthor("TestAuthor");
		removedBookEntity.setDescription("Testdescription");
		removedBookEntity.setTags(null);
		removedBookEntityList.add(removedBookEntity);

		emptyBookEntityList = new ArrayList<BookEntity>();
	}

	@Test
	public void testDelegate() {
		// testing delegate method with an unrecognized input type
		bookPersistenceDelegator.initialize(unrecognizedDelegatorInput,
				bookDAOImpl);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateAdd1() {
		bookPersistenceDelegator.initialize(addDelegatorInput, bookDAOImpl);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(
				ErrorConstants.ADD_BOOK_SUCCESS_MSG));
		assertTrue(delegatorOutput.getOutputObject() != null);
	}

	@Test
	public void testDelegateAdd2() {
		bookPersistenceDelegator.initialize(addDelegatorInput,
				bookDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateAdd3() {
		bookPersistenceDelegator.initialize(invalidAddDelegatorInput,
				bookDAOImpl);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateRead1() {
		bookPersistenceDelegator.initialize(readDelegatorInput, bookDAOImpl);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateRead2() {
		bookPersistenceDelegator.initialize(readDelegatorInput,
				bookDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateRead3() {
		bookPersistenceDelegator.initialize(invalidReadDelegatorInput,
				bookDAOImpl);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateDelete1() {
		// testing behavior when deletion works
		bookPersistenceDelegator.initialize(deleteDelegatorInput, bookDAOImpl);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateDelete2() {
		// testing behavior when input type object is not of type Books
		bookPersistenceDelegator.initialize(invalidDeleteDelegatorInput,
				bookDAOImpl);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateDelete3() {
		// testing behavior when the objects that should be removed were not
		// found
		bookPersistenceDelegator.initialize(deleteDelegatorInput,
				bookDAOImplNonExistingDeletionsFail);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_BOOK_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateDelete4() {
		// testing behavior when an exception already occurred already on entity
		// retrieval
		bookPersistenceDelegator.initialize(deleteDelegatorInput,
				bookDAOImplRetrieveDeletionFail);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_BOOK_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateDelete5() {
		// testing behavior when an exception occurred on entity removal
		bookPersistenceDelegator.initialize(deleteDelegatorInput,
				bookDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_BOOK_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateDelete6() {
		bookPersistenceDelegator.initialize(deleteDelegatorInputWithId,
				bookDAOImplIdRemoval);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@AfterClass
	public static void tearDown() {
		EasyMock.verify(bookDAOImpl);
		EasyMock.verify(bookDAOImplIdRemoval);
		EasyMock.verify(bookDAOImplGeneralFail);
		EasyMock.verify(bookDAOImplNonExistingDeletionsFail);
		EasyMock.verify(bookDAOImplRetrieveDeletionFail);
	}
}
