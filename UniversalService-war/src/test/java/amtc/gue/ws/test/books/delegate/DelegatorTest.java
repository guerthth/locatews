package amtc.gue.ws.test.books.delegate;

import java.util.ArrayList;
import java.util.List;

import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.TagPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRemovalException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.service.inout.Book;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;

public abstract class DelegatorTest {

	protected static Tags searchTags;
	protected static List<String> tagList;

	protected static Books books;
	protected static Books booksWithId;

	protected static Book firstBook;
	protected static Book secondBook;
	protected static List<Book> bookList;
	protected static List<Book> bookListWithId;

	protected static final String searchTagA = "testTagA";
	private static final String testId = "testID";
	private static final String testKey = "testKey";

	protected static PersistenceDelegatorInput addDelegatorInput;
	protected static PersistenceDelegatorInput readDelegatorInput;
	protected static PersistenceDelegatorInput deleteDelegatorInput;
	protected static PersistenceDelegatorInput invalidReadDelegatorInput;
	protected static PersistenceDelegatorInput invalidAddDelegatorInput;
	protected static PersistenceDelegatorInput invalidDeleteDelegatorInput;
	protected static PersistenceDelegatorInput deleteDelegatorInputWithId;
	protected static PersistenceDelegatorInput unrecognizedDelegatorInput;

	protected static GAEJPABookEntity retrievedBookEntity;
	protected static List<GAEJPABookEntity> retrievedBookEntityList;
	protected static GAEJPABookEntity removedBookEntity;
	protected static GAEJPABookEntity foundIdBookEntity;

	protected static List<GAEJPABookEntity> removedBookEntityList;
	protected static List<GAEJPABookEntity> emptyBookEntityList;

	protected static List<GAEJPATagEntity> retrievedTagEntityList;

	protected static BookPersistenceDelegator bookPersistenceDelegator;
	protected static TagPersistenceDelegator tagPersistenceDelegator;

	public static void oneTimeInitialSetup() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {
		setupTags();
		setupBooks();
		setUpDelegatorInputs();
		setUpPersistenceDelegators();
		setUpRetrievedBookEntities();
		setUpRetrievedTagEntities();
		setUpRemovedBookEntities();
	}

	/**
	 * Method setting up some Tags
	 */
	private static void setupTags() {
		tagList = new ArrayList<String>();
		searchTags = new Tags();
		tagList.add(searchTagA);
		searchTags.setTags(tagList);
	}

	/**
	 * Method setting up some Books
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
		secondBook.setId(testId);
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
	 * Method setting up some DelegatorInputs
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
	 * Set up the BookEntities that should be returned
	 */
	private static void setUpRetrievedBookEntities() {
		retrievedBookEntity = new GAEJPABookEntity();
		retrievedBookEntityList = new ArrayList<GAEJPABookEntity>();
		retrievedBookEntity.setKey(testKey);
		retrievedBookEntity.setAuthor("ReturnAuthor");
		retrievedBookEntity.setDescription("Testdescription");
		retrievedBookEntity.setTags(null,false);

		retrievedBookEntityList.add(retrievedBookEntity);

		foundIdBookEntity = new GAEJPABookEntity();
		foundIdBookEntity.setKey(testKey);
		foundIdBookEntity.setAuthor("ReturnAuthor");
		foundIdBookEntity.setDescription("Testdescription");
		foundIdBookEntity.setTags(null,false);
	}

	/**
	 * Set up the BookEntities that should be removed
	 */
	private static void setUpRemovedBookEntities() {
		removedBookEntity = new GAEJPABookEntity();
		removedBookEntityList = new ArrayList<GAEJPABookEntity>();
		removedBookEntity.setISBN(testId);
		removedBookEntity.setAuthor("TestAuthor");
		removedBookEntity.setDescription("Testdescription");
		removedBookEntity.setTags(null,false);
		removedBookEntityList.add(removedBookEntity);

		emptyBookEntityList = new ArrayList<GAEJPABookEntity>();
	}

	/**
	 * Set up the TagEntities that should be retrieved
	 */
	private static void setUpRetrievedTagEntities() {
		retrievedTagEntityList = new ArrayList<GAEJPATagEntity>();
	}

	/**
	 * Setup persistence delegators
	 */
	private static void setUpPersistenceDelegators() {
		bookPersistenceDelegator = new BookPersistenceDelegator();
		tagPersistenceDelegator = new TagPersistenceDelegator();
	}

}
