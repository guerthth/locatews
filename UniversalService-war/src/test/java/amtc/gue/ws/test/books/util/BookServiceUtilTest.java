package amtc.gue.ws.test.books.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;

import amtc.gue.ws.base.delegate.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.output.PersistenceDelegatorOutput;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.util.BookServiceEntityMapper;
import amtc.gue.ws.books.util.BookServiceErrorConstants;

/**
 * Class holding setups for helper object setups for testing the BookService
 * Utility Classes
 *
 * @author Thomas
 *
 */
public abstract class BookServiceUtilTest {

	protected GAEJPABookEntity bookEntity1;
	protected GAEJPABookEntity bookEntity2;
	protected GAEJPABookEntity bookEntity3;
	protected GAEJPABookEntity bookEntity4;
	protected GAEJPABookEntity bookEntity5;
	protected GAEJPABookEntity bookEntity6;

	protected List<GAEJPABookEntity> bookEntityList;
	protected List<GAEJPABookEntity> bookEntityNullList;
	protected List<GAEJPABookEntity> bookEntityTagNullList;
	protected List<GAEJPABookEntity> bookEntitySimpleList;

	protected GAEJPAUserEntity userEntity1;

	protected Set<GAEJPAUserEntity> userEntitySet;
	protected Set<GAEJPATagEntity> tagEntitySet;

	protected GAEJPATagEntity tagEntity1;
	protected GAEJPATagEntity tagEntity2;
	protected GAEJPATagEntity tagEntity3;
	protected GAEJPATagEntity tagEntity4;
	protected GAEJPATagEntity tagEntity5;
	protected GAEJPATagEntity tagEntity6;
	protected GAEJPATagEntity tagEntity7;

	protected List<GAEJPATagEntity> tagEntityList;
	protected List<GAEJPATagEntity> tagEntityListWithNullTags;
	protected List<GAEJPATagEntity> tagEntitySimpleList;

	protected Tags existingSearchTags;
	protected Tags nonExistingSearchTags;

	protected Book book1;
	protected Book book2;
	protected Books simpleBooks;
	protected Books emptyBooks;

	protected final String tagStringA = "tagStringA";
	protected final String tagStringB = "tagStringB";
	protected final String tagStringC = "tagStringC";
	protected final String tagStringD = "tagStringD";

	private final String TEST_BOOK_ENTITY_DESCRIPTION = "testBookEntityDescription";
	private final String TEST_BOOK_ENTITY_ISBN = "testBookEntityISBN";
	private final String TEST_BOOK_ENTITY_PRICE = "testBookEntityPrice";
	private final String TEST_BOOK_ENTITY_AUTHOR = "testBookEntityAuthor";
	private final String TEST_BOOK_ENTITY_TITLE = "testBookEntityTitle";
	private final String TEST_BOOK_ENTITY_KEY = "testBookEntityKey";

	private final String TEST_USER_ENTITY_KEY = "testUserEntityKey";

	private static final String TEST_TAG_ENTITY_KEY = "testTagEntityKey";

	protected final String BOOKENTITY1_JSON_1 = "{id: null, title: null, "
			+ "author: null, description: null, ISBN: null, price: null, tags: ";
	protected final String BOOKENTITY1_JSON_2 = "[" + tagStringA + ", "
			+ tagStringB + ", " + tagStringC + "]}";
	protected final String BOOKENTITY4_JSON = "{id: " + TEST_BOOK_ENTITY_KEY
			+ ", title: " + TEST_BOOK_ENTITY_TITLE + ", " + "author: "
			+ TEST_BOOK_ENTITY_AUTHOR + ", description: "
			+ TEST_BOOK_ENTITY_DESCRIPTION + ", ISBN: " + TEST_BOOK_ENTITY_ISBN
			+ ", price: " + TEST_BOOK_ENTITY_PRICE + ", tags: []}";
	protected final String BOOKENTITY3_JSON = "{id: null, title: null, "
			+ "author: " + TEST_BOOK_ENTITY_AUTHOR + ", description: "
			+ TEST_BOOK_ENTITY_DESCRIPTION + ", ISBN: " + TEST_BOOK_ENTITY_ISBN
			+ ", price: " + TEST_BOOK_ENTITY_PRICE + ", tags: [null]}";
	protected final String BOOKENTITY6_JSON = "{id: null, title: null, "
			+ "author: " + TEST_BOOK_ENTITY_AUTHOR
			+ ", description: null, ISBN: " + TEST_BOOK_ENTITY_ISBN
			+ ", price: " + TEST_BOOK_ENTITY_PRICE + ", tags: [null]}";
	protected final String NULL_BOOKENTITIY_JSON = "{}";

	protected final String NULL_CONSOLIDATED_BOOKENTITY_JSON = "[]";
	protected final String SIMPLE_CONSOLIDATED_BOOKENTITY_JSON = "["
			+ BOOKENTITY4_JSON + ", " + BOOKENTITY4_JSON + "]";

	protected final String TAGENTITY6_JSON = "{id: null, tagName: null, books: ["
			+ BOOKENTITY6_JSON + "]}";
	protected final String TAGENTITY6_JSON_2_1 = "{id: null, tagName: null, books: ["
			+ BOOKENTITY6_JSON + ", null]}";
	protected final String TAGENTITY6_JSON_2_2 = "{id: null, tagName: null, books: [null, "
			+ BOOKENTITY6_JSON + "]}";
	protected final String TAGENTITY7_JSON = "{id: " + TEST_TAG_ENTITY_KEY
			+ ", tagName: " + tagStringA + ", books: []}";
	protected final String NULL_TAGENTITIY_JSON = "{}";

	protected final String NULL_CONSOLIDATED_TAGENTITY_JSON = "[]";
	protected final String SIMPLE_CONSOLIDATED_TAGENTITY_JSON = "["
			+ TAGENTITY7_JSON + ", " + TAGENTITY7_JSON + "]";

	protected IDelegatorOutput bookDelegatorOutput;
	protected IDelegatorOutput unrecognizedBookDelegatorOutput;
	protected IDelegatorOutput tagDelegatorOutput;
	protected IDelegatorOutput unrecognizedTagDelegatorOutput;

	protected String EXPECTED_PERSIST_STATUS_ONLY_SUCCESSES;
	protected String EXPECTED_PERSIST_STATUS_SUCCESS_AND_FAILS;
	protected String EXPECTED_PERSIST_STATUS_ONLY_FAILS;
	protected String EXPECTED_TAG_RETRIEVAL_MESSAGE_SIMPLE_RESULT;
	protected String EXPECTED_TAG_RETRIEVAL_MESSAGE_EMPTY_RESULT;
	protected String EXPECTED_BOOK_REMOVAL_MESSAGE_SIMPLE_RESULT;
	protected String EXPECTED_BOOK_REMOVAL_MESSAGE_EMPTY_RESULT;
	protected String EXPECTED_TAG_RETRIEVAL_MESSAGE;

	@Before
	public void setUp() {
		setupGAEJPAEntities();
		setupTags();
		setupBooks();
		setupBdOutputs();
		setUpExpectedPersistStatusMessages();
		setUpExpectedTagRetrievalStatusMessages();
		setUpExpectedRemoveStatusMessages();
		setUpExpectedRetrieveTagsStatusMessages();
	}

	/**
	 * Method intitializing some GAEJPABookEntities
	 */
	private void setupGAEJPAEntities() {
		userEntity1 = new GAEJPAUserEntity();
		userEntity1.setKey(TEST_USER_ENTITY_KEY);

		userEntitySet = new HashSet<>();
		userEntitySet.add(userEntity1);

		tagEntity1 = new GAEJPATagEntity();
		tagEntity1.setTagName(tagStringA);
		tagEntity2 = new GAEJPATagEntity();
		tagEntity2.setTagName(tagStringB);
		tagEntity3 = new GAEJPATagEntity();
		tagEntity3.setTagName(tagStringC);
		tagEntity4 = new GAEJPATagEntity();
		tagEntity4.setKey(TEST_TAG_ENTITY_KEY);
		tagEntity4.setTagName(tagStringD);
		tagEntity5 = new GAEJPATagEntity();
		tagEntity6 = new GAEJPATagEntity();
		tagEntity7 = new GAEJPATagEntity();
		tagEntity7.setKey(TEST_TAG_ENTITY_KEY);
		tagEntity7.setTagName(tagStringA);
		tagEntity7.setBooks(null);
		
		tagEntitySet = new HashSet<>();
		tagEntitySet.add(tagEntity4);

		tagEntityList = new ArrayList<GAEJPATagEntity>();
		tagEntityList.add(tagEntity1);
		tagEntityList.add(tagEntity2);
		tagEntityList.add(tagEntity3);
		tagEntityList.add(tagEntity4);
		tagEntityListWithNullTags = new ArrayList<GAEJPATagEntity>();
		tagEntityList.add(null);

		bookEntity1 = new GAEJPABookEntity();
		bookEntity1.addToTagsAndBooks(tagEntity1);
		bookEntity1.addToTagsAndBooks(tagEntity2);
		bookEntity1.addToTagsAndBooks(tagEntity3);
		bookEntity2 = new GAEJPABookEntity();
		bookEntity2.addToTagsAndBooks(tagEntity1);
		bookEntity2.addToTagsAndBooks(tagEntity2);
		bookEntity2.addToTagsAndBooks(tagEntity3);
		bookEntity3 = new GAEJPABookEntity();
		bookEntity3.setAuthor(TEST_BOOK_ENTITY_AUTHOR);
		bookEntity3.setPrice(TEST_BOOK_ENTITY_PRICE);
		bookEntity3.setISBN(TEST_BOOK_ENTITY_ISBN);
		bookEntity3.setDescription(TEST_BOOK_ENTITY_DESCRIPTION);
		bookEntity3.setUsers(null, false);
		bookEntity4 = new GAEJPABookEntity();
		bookEntity4.setKey(TEST_BOOK_ENTITY_KEY);
		bookEntity4.setTitle(TEST_BOOK_ENTITY_TITLE);
		bookEntity4.setAuthor(TEST_BOOK_ENTITY_AUTHOR);
		bookEntity4.setPrice(TEST_BOOK_ENTITY_PRICE);
		bookEntity4.setISBN(TEST_BOOK_ENTITY_ISBN);
		bookEntity4.setDescription(TEST_BOOK_ENTITY_DESCRIPTION);
		bookEntity4.setTags(null, false);
		bookEntity5 = new GAEJPABookEntity();
		bookEntity5.setAuthor(TEST_BOOK_ENTITY_AUTHOR);
		bookEntity5.setPrice(TEST_BOOK_ENTITY_PRICE);
		bookEntity5.setISBN(TEST_BOOK_ENTITY_ISBN);
		bookEntity6 = new GAEJPABookEntity();
		bookEntity6.setAuthor(TEST_BOOK_ENTITY_AUTHOR);
		bookEntity6.setUsers(userEntitySet, false);

		bookEntityList = new ArrayList<GAEJPABookEntity>();
		bookEntityList.add(bookEntity1);
		bookEntityList.add(bookEntity2);
		bookEntityList.add(bookEntity3);
		bookEntityList.add(bookEntity4);

		bookEntityNullList = new ArrayList<GAEJPABookEntity>();
		bookEntityNullList.add(null);

		bookEntityTagNullList = new ArrayList<GAEJPABookEntity>();
		bookEntityTagNullList.add(bookEntity4);

		bookEntitySimpleList = new ArrayList<GAEJPABookEntity>();
		bookEntitySimpleList.add(bookEntity4);
		bookEntitySimpleList.add(bookEntity4);

		tagEntity6.addToBooks(bookEntity5);

		tagEntitySimpleList = new ArrayList<GAEJPATagEntity>();
		tagEntitySimpleList.add(tagEntity7);
		tagEntitySimpleList.add(tagEntity7);
	}

	/**
	 * Method initializing some Tags
	 */
	private void setupTags() {
		existingSearchTags = new Tags();
		existingSearchTags.getTags().add(tagStringA);
		existingSearchTags.getTags().add(tagStringB);
		existingSearchTags.getTags().add(tagStringC);

		nonExistingSearchTags = new Tags();
		nonExistingSearchTags.getTags().add(tagStringD);
	}

	/**
	 * Method initializing some Books
	 */
	private void setupBooks() {
		book1 = new Book();
		book1.setId(TEST_BOOK_ENTITY_KEY);
		book1.setTags(existingSearchTags.getTags());

		book2 = new Book();
		book2.setTags(existingSearchTags.getTags());

		simpleBooks = new Books();
		simpleBooks.getBooks().add(book1);
		simpleBooks.getBooks().add(book2);

		emptyBooks = new Books();
	}

	/**
	 * Method inititializing some DelegatorOutputs
	 */
	private void setupBdOutputs() {
		bookDelegatorOutput = new PersistenceDelegatorOutput();
		bookDelegatorOutput.setOutputObject(simpleBooks);
		unrecognizedBookDelegatorOutput = new PersistenceDelegatorOutput();
		unrecognizedBookDelegatorOutput.setOutputObject(null);

		tagDelegatorOutput = new PersistenceDelegatorOutput();
		tagDelegatorOutput.setOutputObject(existingSearchTags);
		unrecognizedTagDelegatorOutput = new PersistenceDelegatorOutput();
		unrecognizedTagDelegatorOutput.setOutputObject(null);
	}

	/**
	 * Method setting up the expected Persist status message outputs
	 */
	private void setUpExpectedPersistStatusMessages() {
		// status message for only successfully added bookentities
		StringBuilder sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append("'");
		sb.append(BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList));
		sb.append("'.");
		sb.append(" ").append(bookEntitySimpleList.size());
		sb.append(" books were successfully added.");
		EXPECTED_PERSIST_STATUS_ONLY_SUCCESSES = sb.toString();

		// status message for successfully and also unsuccessfully added
		// bookentities
		sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append("'");
		sb.append(BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList));
		sb.append("'.");
		sb.append(" ").append(bookEntitySimpleList.size());
		sb.append(" books were successfully added.");
		sb.append(System.getProperty("line.separator"));
		sb.append("'");
		sb.append(BookServiceErrorConstants.ADD_BOOK_FAILURE_MSG);
		sb.append(BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList));
		sb.append("'.");
		sb.append(" ").append(bookEntitySimpleList.size());
		sb.append(" books were not added successfully.");
		EXPECTED_PERSIST_STATUS_SUCCESS_AND_FAILS = sb.toString();

		// status message for unseccessfully added bookentities only
		sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append("'");
		sb.append(BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(null));
		sb.append("'.");
		sb.append(" ").append(0);
		sb.append(" books were successfully added.");
		sb.append(System.getProperty("line.separator"));
		sb.append("'");
		sb.append(BookServiceErrorConstants.ADD_BOOK_FAILURE_MSG);
		sb.append(BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList));
		sb.append("'.");
		sb.append(" ").append(bookEntitySimpleList.size());
		sb.append(" books were not added successfully.");
		EXPECTED_PERSIST_STATUS_ONLY_FAILS = sb.toString();
	}

	/**
	 * Method initializing some tagrestrieval statusmessages
	 */
	private void setUpExpectedTagRetrievalStatusMessages() {
		StringBuilder sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.RETRIEVE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(existingSearchTags.getTags().toString());
		sb.append("': '");
		sb.append(BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList));
		sb.append("'. ");
		sb.append(bookEntitySimpleList.size());
		sb.append(" Entities were found.");
		EXPECTED_TAG_RETRIEVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.RETRIEVE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(existingSearchTags.getTags().toString());
		sb.append("': '");
		sb.append(BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntityNullList));
		sb.append("'. ");
		sb.append(bookEntityNullList.size());
		sb.append(" Entities were found.");
		EXPECTED_TAG_RETRIEVAL_MESSAGE_EMPTY_RESULT = sb.toString();
	}

	/**
	 * Method initializing some Remove statusmessages
	 */
	private void setUpExpectedRemoveStatusMessages() {
		StringBuilder sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.DELETE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList));
		sb.append("'. ");
		sb.append(bookEntitySimpleList.size());
		sb.append(" Entities were removed.");
		EXPECTED_BOOK_REMOVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.DELETE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntityNullList));
		sb.append("'. ");
		sb.append(bookEntityNullList.size());
		sb.append(" Entities were removed.");
		EXPECTED_BOOK_REMOVAL_MESSAGE_EMPTY_RESULT = sb.toString();
	}

	/**
	 * Method intitializing some Retrieve tags statusmessages
	 */
	private void setUpExpectedRetrieveTagsStatusMessages() {
		StringBuilder sb = new StringBuilder();
		sb.append(BookServiceErrorConstants.RETRIEVE_TAGS_SUCCESS_MSG);
		sb.append(" '");
		sb.append(BookServiceEntityMapper
				.mapTagEntityListToConsolidatedJSONString(tagEntitySimpleList));
		sb.append("'. ");
		sb.append(tagEntitySimpleList.size());
		sb.append(" Entities were found");
		EXPECTED_TAG_RETRIEVAL_MESSAGE = sb.toString();
	}
}
