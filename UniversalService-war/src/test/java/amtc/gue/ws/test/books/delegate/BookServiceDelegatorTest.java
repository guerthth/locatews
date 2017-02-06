package amtc.gue.ws.test.books.delegate;

import java.util.ArrayList;
import java.util.List;

import amtc.gue.ws.base.delegate.input.DelegatorInput;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
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

	protected static DelegatorInput addBookDelegatorInput;
	protected static DelegatorInput deleteBookDelegatorInput;
	protected static DelegatorInput readBookDelegatorInput;
	protected static DelegatorInput deleteBookDelegatorInputWithId;
	protected static DelegatorInput addTagDelegatorInput;
	protected static DelegatorInput deleteTagDelegatorInput;
	protected static DelegatorInput nullDeleteTagDelegatorInput;
	protected static DelegatorInput readTagDelegatorInput;
	protected static DelegatorInput nullReadTagDelegatorInput;

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
	protected static GAEJPAUserEntity currentUserEntity;
	protected static final String TESTUSERNAME = "testUserName";

	/**
	 * InitialSetup before tastclass
	 */
	public static void oneTimeInitialSetup() {
		setupUser();
		setupTags();
		setupBooks();
		setUpUserEntities();
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
		addBookDelegatorInput = new DelegatorInput();
		addBookDelegatorInput.setInputObject(books);
		addBookDelegatorInput.setType(DelegatorTypeEnum.ADD);

		// DelegatorInput for bookentity deletion
		deleteBookDelegatorInput = new DelegatorInput();
		deleteBookDelegatorInput.setInputObject(books);
		deleteBookDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInput for bookentity deletion with ID book input type
		deleteBookDelegatorInputWithId = new DelegatorInput();
		deleteBookDelegatorInputWithId.setInputObject(booksWithId);
		deleteBookDelegatorInputWithId.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInput for bookentity read
		readBookDelegatorInput = new DelegatorInput();
		readBookDelegatorInput.setInputObject(searchTags);
		readBookDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInput for tagentity add
		addTagDelegatorInput = new DelegatorInput();
		addTagDelegatorInput.setInputObject(searchTags);
		addTagDelegatorInput.setType(DelegatorTypeEnum.ADD);

		// DelegatorInput for tagentity delete
		deleteTagDelegatorInput = new DelegatorInput();
		deleteTagDelegatorInput.setInputObject(searchTags);
		deleteTagDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		nullDeleteTagDelegatorInput = new DelegatorInput();
		nullDeleteTagDelegatorInput.setInputObject(null);
		nullDeleteTagDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInputs for tagentity read
		readTagDelegatorInput = new DelegatorInput();
		readTagDelegatorInput.setInputObject(searchTags);
		readTagDelegatorInput.setType(DelegatorTypeEnum.READ);

		nullReadTagDelegatorInput = new DelegatorInput();
		nullReadTagDelegatorInput.setInputObject(null);
		nullReadTagDelegatorInput.setType(DelegatorTypeEnum.READ);

	}

	/**
	 * Set up UserEntities
	 */
	private static void setUpUserEntities() {
		currentUserEntity = new GAEJPAUserEntity();
		currentUserEntity.setKey(TESTUSERNAME);
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
