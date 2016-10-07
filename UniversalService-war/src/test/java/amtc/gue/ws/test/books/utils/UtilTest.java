package amtc.gue.ws.test.books.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.output.PersistenceDelegatorOutput;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.service.inout.Book;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.ErrorConstants;

public abstract class UtilTest {

	protected GAEJPABookEntity bookEntity1;
	protected GAEJPABookEntity bookEntity2;
	protected GAEJPABookEntity bookEntity3;
	protected GAEJPABookEntity bookEntity4;
	protected GAEJPABookEntity bookEntity5;

	protected List<GAEJPABookEntity> bookEntityList;
	protected List<GAEJPABookEntity> bookEntityNullList;
	protected List<GAEJPABookEntity> bookEntityTagNullList;
	protected List<GAEJPABookEntity> bookEntitySimpleList;

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

	protected static final String tagStringA = "tagStringA";
	protected static final String tagStringB = "tagStringB";
	protected static final String tagStringC = "tagStringC";
	protected static final String tagStringD = "tagStringD";

	private static final String TEST_BOOK_ENTITY_DESCRIPTION = "testBookEntityDescription";
	private static final String TEST_BOOK_ENTITY_ISBN = "testBookEntityISBN";
	private static final String TEST_BOOK_ENTITY_PRICE = "testBookEntityPrice";
	private static final String TEST_BOOK_ENTITY_AUTHOR = "testBookEntityAuthor";
	private static final String TEST_BOOK_ENTITY_TITLE = "testBookEntityTitle";
	private static final String TEST_BOOK_ENTITY_KEY = "testBookEntityKey";

	private static final String TEST_TAG_ENTITY_KEY = "testTagEntityKey";

	protected static final String BOOKENTITY1_JSON_1 = "{id: null, title: null, "
			+ "author: null, description: null, ISBN: null, price: null, tags: ";
	protected static final String BOOKENTITY1_JSON_2 = "[" + tagStringA + ", "
			+ tagStringB + ", " + tagStringC + "]}";
	protected static final String BOOKENTITY4_JSON = "{id: "
			+ TEST_BOOK_ENTITY_KEY + ", title: " + TEST_BOOK_ENTITY_TITLE
			+ ", " + "author: " + TEST_BOOK_ENTITY_AUTHOR + ", description: "
			+ TEST_BOOK_ENTITY_DESCRIPTION + ", ISBN: " + TEST_BOOK_ENTITY_ISBN
			+ ", price: " + TEST_BOOK_ENTITY_PRICE + ", tags: []}";
	protected static final String BOOKENTITY3_JSON = "{id: null, title: null, "
			+ "author: " + TEST_BOOK_ENTITY_AUTHOR + ", description: "
			+ TEST_BOOK_ENTITY_DESCRIPTION + ", ISBN: " + TEST_BOOK_ENTITY_ISBN
			+ ", price: " + TEST_BOOK_ENTITY_PRICE + ", tags: [null]}";
	protected static final String BOOKENTITY6_JSON = "{id: null, title: null, "
			+ "author: " + TEST_BOOK_ENTITY_AUTHOR
			+ ", description: null, ISBN: " + TEST_BOOK_ENTITY_ISBN
			+ ", price: " + TEST_BOOK_ENTITY_PRICE + ", tags: [null]}";
	protected static final String NULL_BOOKENTITIY_JSON = "{}";

	protected static final String NULL_CONSOLIDATED_BOOKENTITY_JSON = "[]";
	protected static final String SIMPLE_CONSOLIDATED_BOOKENTITY_JSON = "["
			+ BOOKENTITY4_JSON + ", " + BOOKENTITY4_JSON + "]";

	protected static final String TAGENTITY6_JSON = "{id: null, tagName: null, books: ["
			+ BOOKENTITY6_JSON + "]}";
	protected static final String TAGENTITY6_JSON_2_1 = "{id: null, tagName: null, books: ["
			+ BOOKENTITY6_JSON + ", null]}";
	protected static final String TAGENTITY6_JSON_2_2 = "{id: null, tagName: null, books: [null, "
			+ BOOKENTITY6_JSON + "]}";
	protected static final String TAGENTITY7_JSON = "{id: "
			+ TEST_TAG_ENTITY_KEY + ", tagName: " + tagStringA + ", books: []}";
	protected static final String NULL_TAGENTITIY_JSON = "{}";

	protected static final String NULL_CONSOLIDATED_TAGENTITY_JSON = "[]";
	protected static final String SIMPLE_CONSOLIDATED_TAGENTITY_JSON = "["
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

	@BeforeClass
	public static void oneTimeInitialSetup() {

	}

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

	@After
	public void tearDown() {

	}

	/**
	 * Method intitializing some GAEJPABookEntities
	 */
	private void setupGAEJPAEntities() {
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
		sb.append(ErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append("'");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList));
		sb.append("'.");
		sb.append(" ").append(bookEntitySimpleList.size());
		sb.append(" books were successfully added.");
		EXPECTED_PERSIST_STATUS_ONLY_SUCCESSES = sb.toString();

		// status message for successfully and also unsuccessfully added
		// bookentities
		sb = new StringBuilder();
		sb.append(ErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append("'");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList));
		sb.append("'.");
		sb.append(" ").append(bookEntitySimpleList.size());
		sb.append(" books were successfully added.");
		sb.append(System.getProperty("line.separator"));
		sb.append("'");
		sb.append(ErrorConstants.ADD_BOOK_FAILURE_MSG);
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList));
		sb.append("'.");
		sb.append(" ").append(bookEntitySimpleList.size());
		sb.append(" books were not added successfully.");
		EXPECTED_PERSIST_STATUS_SUCCESS_AND_FAILS = sb.toString();

		// status message for unseccessfully added bookentities only
		sb = new StringBuilder();
		sb.append(ErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append("'");
		sb.append(EntityMapper.mapBookEntityListToConsolidatedJSONString(null));
		sb.append("'.");
		sb.append(" ").append(0);
		sb.append(" books were successfully added.");
		sb.append(System.getProperty("line.separator"));
		sb.append("'");
		sb.append(ErrorConstants.ADD_BOOK_FAILURE_MSG);
		sb.append(EntityMapper
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
		sb.append(ErrorConstants.RETRIEVE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(existingSearchTags.getTags().toString());
		sb.append("': '");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList));
		sb.append("'. ");
		sb.append(bookEntitySimpleList.size());
		sb.append(" Entities were found.");
		EXPECTED_TAG_RETRIEVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		sb = new StringBuilder();
		sb.append(ErrorConstants.RETRIEVE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(existingSearchTags.getTags().toString());
		sb.append("': '");
		sb.append(EntityMapper
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
		sb.append(ErrorConstants.DELETE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList));
		sb.append("'. ");
		sb.append(bookEntitySimpleList.size());
		sb.append(" Entities were removed.");
		EXPECTED_BOOK_REMOVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		sb = new StringBuilder();
		sb.append(ErrorConstants.DELETE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(EntityMapper
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
		sb.append(ErrorConstants.RETRIEVE_TAGS_SUCCESS_MSG);
		sb.append(" '");
		sb.append(EntityMapper
				.mapTagEntityListToConsolidatedJSONString(tagEntitySimpleList));
		sb.append("'. ");
		sb.append(tagEntitySimpleList.size());
		sb.append(" Entities were found");
		EXPECTED_TAG_RETRIEVAL_MESSAGE = sb.toString();
	}
}
