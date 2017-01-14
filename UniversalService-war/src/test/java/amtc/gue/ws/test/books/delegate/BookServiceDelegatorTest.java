package amtc.gue.ws.test.books.delegate;

import java.util.ArrayList;
import java.util.List;

import amtc.gue.ws.base.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.util.PersistenceTypeEnum;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.TagPersistenceDelegator;
import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.test.base.delegate.persist.BasePersistenceDelegatorTest;

/**
 * Super Testclass for all BookService Delegator Testcases
 * 
 * @author Thomas
 *
 */
public abstract class BookServiceDelegatorTest extends
		BasePersistenceDelegatorTest {

	protected static Tags searchTags;
	protected static List<String> tagList;

	protected static BookPersistenceDelegator bookPersistenceDelegator;
	protected static TagPersistenceDelegator tagPersistenceDelegator;

	protected static PersistenceDelegatorInput addBookDelegatorInput;
	protected static PersistenceDelegatorInput deleteBookDelegatorInput;
	protected static PersistenceDelegatorInput readBookDelegatorInput;
	protected static PersistenceDelegatorInput deleteBookDelegatorInputWithId;
	protected static PersistenceDelegatorInput addTagDelegatorInput;
	protected static PersistenceDelegatorInput deleteTagDelegatorInput;
	protected static PersistenceDelegatorInput nullDeleteTagDelegatorInput;
	protected static PersistenceDelegatorInput readTagDelegatorInput;
	protected static PersistenceDelegatorInput nullReadTagDelegatorInput;

	protected static GAEJPABookEntity removedBookEntity;

	protected static GAEJPABookEntity retrievedBookEntity;
	protected static List<GAEJPABookEntity> emptyBookEntityList;
	protected static List<GAEJPABookEntity> retrievedBookEntityList;

	protected static GAEJPATagEntity retrievedTagEntity;
	protected static List<GAEJPATagEntity> emptyTagEntityList;
	protected static List<GAEJPATagEntity> retrievedTagEntityList;

	protected static List<GAEJPABookEntity> removedBookEntityList;

	protected static final String TESTAUTHOR = "testAuthor";
	protected static final String TESTDESCRIPTION = "testDescription";
	protected static final String TESTISBN = "testISBN";
	protected static final String TESTTITLE = "testTitle";
	protected static final String TESTPRICE = "100";
	protected static final String SEARCHTAGA = "searchTagA";

	protected static Book firstBook;
	protected static Book secondBook;
	protected static List<Book> bookList;
	protected static List<Book> bookListWithId;
	protected static Books books;
	protected static Books booksWithId;

	protected static User currentUser;
	protected static final String TESTUSERNAME = "testUserName";

	/**
	 * InitialSetup before tastclass
	 */
	public static void oneTimeInitialSetup() {
		setupUser();
		setupTags();
		setupBooks();
		setUpTagEntities();
		setUpBookEntities();
		setUpBaseDelegatorInputs();
		setUpBookDelegatorInputs();
		setUpBookPersistenceDelegators();
	}

	/**
	 * Method setting up a User
	 */
	private static void setupUser() {
		currentUser = new User();
		currentUser.setId(TESTUSERNAME);
	}

	/**
	 * Method setting up some Tags
	 */
	private static void setupTags() {
		tagList = new ArrayList<>();
		tagList.add(SEARCHTAGA);

		searchTags = new Tags();
		searchTags.setTags(tagList);
	}

	/**
	 * Method setting up some Books
	 */
	private static void setupBooks() {
		bookList = new ArrayList<>();
		firstBook = new Book();
		firstBook.setAuthor(TESTAUTHOR);
		firstBook.setDescription(TESTDESCRIPTION);
		firstBook.setISBN(TESTISBN);
		firstBook.setPrice(TESTPRICE);
		firstBook.setTags(searchTags.getTags());
		firstBook.setTitle(TESTTITLE);
		bookList.add(firstBook);

		books = new Books();
		books.setBooks(bookList);

		bookListWithId = new ArrayList<>();
		secondBook = new Book();
		secondBook.setId(TESTKEY);
		secondBook.setAuthor(TESTAUTHOR);
		secondBook.setDescription(TESTDESCRIPTION);
		secondBook.setISBN(TESTISBN);
		secondBook.setPrice(TESTPRICE);
		secondBook.setTags(searchTags.getTags());
		secondBook.setTitle(TESTTITLE);
		bookListWithId.add(secondBook);

		booksWithId = new Books();
		booksWithId.setBooks(bookListWithId);
	}

	/**
	 * Method setting up some DelegatorInputs
	 */
	private static void setUpBookDelegatorInputs() {
		// DelegatorInput for book bookentity adding
		addBookDelegatorInput = new PersistenceDelegatorInput();
		addBookDelegatorInput.setInputObject(books);
		addBookDelegatorInput.setType(PersistenceTypeEnum.ADD);

		// DelegatorInput for bookentity deletion
		deleteBookDelegatorInput = new PersistenceDelegatorInput();
		deleteBookDelegatorInput.setInputObject(books);
		deleteBookDelegatorInput.setType(PersistenceTypeEnum.DELETE);

		// DelegatorInput for bookentity deletion with ID book input type
		deleteBookDelegatorInputWithId = new PersistenceDelegatorInput();
		deleteBookDelegatorInputWithId.setInputObject(booksWithId);
		deleteBookDelegatorInputWithId.setType(PersistenceTypeEnum.DELETE);

		// DelegatorInput for bookentity read
		readBookDelegatorInput = new PersistenceDelegatorInput();
		readBookDelegatorInput.setInputObject(searchTags);
		readBookDelegatorInput.setType(PersistenceTypeEnum.READ);

		// DelegatorInput for tagentity add
		addTagDelegatorInput = new PersistenceDelegatorInput();
		addTagDelegatorInput.setInputObject(searchTags);
		addTagDelegatorInput.setType(PersistenceTypeEnum.ADD);

		// DelegatorInput for tagentity delete
		deleteTagDelegatorInput = new PersistenceDelegatorInput();
		deleteTagDelegatorInput.setInputObject(searchTags);
		deleteTagDelegatorInput.setType(PersistenceTypeEnum.DELETE);

		nullDeleteTagDelegatorInput = new PersistenceDelegatorInput();
		nullDeleteTagDelegatorInput.setInputObject(null);
		nullDeleteTagDelegatorInput.setType(PersistenceTypeEnum.DELETE);

		// DelegatorInputs for tagentity read
		readTagDelegatorInput = new PersistenceDelegatorInput();
		readTagDelegatorInput.setInputObject(searchTags);
		readTagDelegatorInput.setType(PersistenceTypeEnum.READ);

		nullReadTagDelegatorInput = new PersistenceDelegatorInput();
		nullReadTagDelegatorInput.setInputObject(null);
		nullReadTagDelegatorInput.setType(PersistenceTypeEnum.READ);

	}

	/**
	 * Set up the BookEntities that should be removed
	 */
	private static void setUpBookEntities() {

		emptyBookEntityList = new ArrayList<>();

		retrievedBookEntity = new GAEJPABookEntity();
		retrievedBookEntity.setKey(TESTKEY);
		retrievedBookEntity.setAuthor(TESTAUTHOR);
		retrievedBookEntity.setDescription(TESTDESCRIPTION);
		retrievedBookEntity.setTags(null, false);

		retrievedBookEntityList = new ArrayList<>();
		retrievedBookEntityList.add(retrievedBookEntity);

		removedBookEntity = new GAEJPABookEntity();
		removedBookEntity.setISBN(TESTISBN);
		removedBookEntity.setAuthor(TESTKEY);
		removedBookEntity.setDescription(TESTDESCRIPTION);
		removedBookEntity.setTags(null, false);

		removedBookEntityList = new ArrayList<>();
		removedBookEntityList.add(removedBookEntity);
	}

	/**
	 * Set up the TagEntities that should be retrieved
	 */
	private static void setUpTagEntities() {
		emptyTagEntityList = new ArrayList<>();

		retrievedTagEntity = new GAEJPATagEntity();
		retrievedTagEntity.setKey(TESTKEY);
		retrievedTagEntity.setTagName(SEARCHTAGA);

		retrievedTagEntityList = new ArrayList<>();
		retrievedTagEntityList.add(retrievedTagEntity);
	}

	/**
	 * Setup persistence delegators
	 */
	private static void setUpBookPersistenceDelegators() {
		bookPersistenceDelegator = new BookPersistenceDelegator();
		tagPersistenceDelegator = new TagPersistenceDelegator();
	}

}
