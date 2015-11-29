package amtc.gue.ws.test.books.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.output.PersistenceDelegatorOutput;
import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.service.inout.Book;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.service.inout.output.BookServiceResponse;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.ErrorConstants;

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
	private static final String firstTag = "firstTag";
	private static final String secondTag = "second,Tag,";
	private static Tags tags;
	private static List<String> listOfTags;
	private static BookEntity bookEntity;
	private static List<BookEntity> listOfBookEntities;
	private static IDelegatorOutput delegatorOutput;
	private static IDelegatorOutput unrecognizedDelegatorOutput;

	private static final String EXPECTED_TAGLIST = "firstTag,secondTag";
	private static final String SPECIALCHARACTERSTRING = "this,is,a,Test?";
	private static final String REMOVEDSPECIALCHARACTERSTRING = "thisisaTest?";
	private static final String NORMALCHARACTERSTRING = "noSpecialCHarsIncluded!";
	private static final String TEST_TITLE = "testTitle";
	private static final String TEST_PRICE = "testPrice";
	private static final String TEST_ISBN = "testISBN";
	private static final String TEST_DESCRIPTION = "testDescription";
	private static final String TEST_AUTHOR = "testAuthor";

	private static List<String> JSONStrings1 = new ArrayList<String>();
	private static List<String> JSONStrings2 = new ArrayList<String>();

	@BeforeClass
	public static void oneTimeSetUp() {
		initializeBooks();
		intitializeBookEntities();
		intitializeDelegatorOutput();
		initializeBookEntityJSONStrings();
	}

	/**
	 * Initialize Books
	 */
	private static void initializeBooks() {
		firstBook = new Book();

		secondBook = new Book();

		listOfBooks = new ArrayList<Book>();
		listOfBooks.add(firstBook);
		listOfBooks.add(secondBook);

		books = new Books();
		books.setBooks(listOfBooks);

		listOfTags = new ArrayList<String>();
		listOfTags.add(firstTag);
		listOfTags.add(secondTag);

		tags = new Tags();
		tags.setTags(listOfTags);
	}

	/**
	 * Method initializing the BookEntity objects
	 */
	private static void intitializeBookEntities() {
		bookEntity = new BookEntity();
		listOfBookEntities = new ArrayList<BookEntity>();
		bookEntity.setAuthor(TEST_AUTHOR);
		bookEntity.setDescription(TEST_DESCRIPTION);
		bookEntity.setId(1L);
		bookEntity.setISBN(TEST_ISBN);
		bookEntity.setPrice(TEST_PRICE);
		bookEntity.setTags(EXPECTED_TAGLIST);
		bookEntity.setTitle(TEST_TITLE);
		listOfBookEntities.add(bookEntity);
	}

	/**
	 * Method intitializing the DelegatorOutput object for testing
	 */
	private static void intitializeDelegatorOutput() {
		delegatorOutput = new PersistenceDelegatorOutput();
		delegatorOutput.setOutputObject(books);
		delegatorOutput.setStatusCode(ErrorConstants.ADD_BOOK_FAILURE_CODE);
		delegatorOutput.setStatusMessage(ErrorConstants.ADD_BOOK_FAILURE_MSG);

		unrecognizedDelegatorOutput = new PersistenceDelegatorOutput();
		unrecognizedDelegatorOutput.setOutputObject(new Object());
		unrecognizedDelegatorOutput
				.setStatusCode(ErrorConstants.ADD_BOOK_SUCCESS_CODE);
		unrecognizedDelegatorOutput
				.setStatusMessage(ErrorConstants.ADD_BOOK_SUCCESS_MSG);
	}

	/**
	 * Method building up JSONStrings based on the definedf BookEntity objects
	 */
	private static void initializeBookEntityJSONStrings() {
		String bookEntityJSON = EntityMapper
				.mapBookEntityToJSONString(bookEntity);
		JSONStrings1.add(bookEntityJSON);
		JSONStrings2.add(bookEntityJSON);
		JSONStrings2.add(bookEntityJSON);
	}

	@Test
	public void testMapToBookEntity() {
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
	public void testTransformBooksToEntites() {
		List<BookEntity> bookEntityList = EntityMapper
				.transformBooksToBookEntities(books);
		assertTrue(bookEntityList != null);
	}

	@Test
	public void testRemoveSpecialCharacters() {
		assertEquals(REMOVEDSPECIALCHARACTERSTRING,
				EntityMapper.removeSpecialCharacters(SPECIALCHARACTERSTRING));
	}

	@Test
	public void testRemoveSpecialCharactersWhenNoSecialChars() {
		assertEquals(NORMALCHARACTERSTRING,
				EntityMapper.removeSpecialCharacters(NORMALCHARACTERSTRING));
	}

	@Test
	public void testMapTagsToStringWithValidData() {
		String tagStringResult = EntityMapper.mapTagsToString(tags);
		assertNotNull(tagStringResult);
		assertEquals(EXPECTED_TAGLIST, tagStringResult);
	}

	@Test
	public void testMapTagsToStringWithInvalidDate() {
		String tagStringResult = EntityMapper.mapTagsToString(null);
		assertNull(tagStringResult);
	}

	@Test
	public void testMapStringToTags() {
		Tags tags = EntityMapper.mapTagStringToTags(EXPECTED_TAGLIST);
		List<String> tagList = Arrays.asList(EXPECTED_TAGLIST.split(","));
		for (int i = 0; i < tagList.size(); i++) {
			assertEquals(tagList.get(i), tags.getTags().get(i));
		}
	}

	@Test
	public void testMapBookEntityToBook() {
		Book book = EntityMapper.mapBookEntityToBook(bookEntity);
		assertEquals(bookEntity.getAuthor(), book.getAuthor());
		assertEquals(bookEntity.getDescription(), book.getDescription());
		assertEquals(bookEntity.getISBN(), book.getISBN());
		assertEquals(bookEntity.getPrice(), book.getPrice());
		assertEquals(bookEntity.getTitle(), book.getTitle());
	}

	@Test
	public void testTransformBookEntitiesToBooks() {
		Books books = EntityMapper
				.transformBookEntitiesToBooks(listOfBookEntities);
		assertEquals(listOfBookEntities.size(), books.getBooks().size());
	}

	@Test
	public void testMapBdOutputToServiceResponse() {
		BookServiceResponse serviceResponse = EntityMapper
				.mapBdOutputToServiceResponse(delegatorOutput);
		assertNotEquals(null, serviceResponse.getBook());
	}

	@Test
	public void testMapBdOutputToServiceResponseWithUnrecognizedType() {
		BookServiceResponse serviceResponse = EntityMapper
				.mapBdOutputToServiceResponse(unrecognizedDelegatorOutput);
		assertEquals(null, serviceResponse.getBook());
	}

	@Test
	public void testMapBookEntityToJSONString1() {
		String JSON = EntityMapper.mapBookEntityToJSONString(bookEntity);
		assertTrue(JSON.length() > 2);
	}

	@Test
	public void testMapBookEntityToJSONString2() {
		String JSON = EntityMapper.mapBookEntityToJSONString(null);
		assertEquals("{}", JSON);
	}

	@Test
	public void testMapJSONStringsToConsolidatedString1() {
		String consolidatedString = EntityMapper
				.mapJSONStringsToConsolidatedString(null);
		assertEquals("", consolidatedString);
	}

	@Test
	public void testMapJSONStringsToConsolidatedString2() {
		String consolidatedString = EntityMapper
				.mapJSONStringsToConsolidatedString(JSONStrings1);
		assertEquals(
				"{id: 1, title: testTitle, author: testAuthor,description: testDescription, ISBN: testISBN, price: testPrice, tags: firstTag,secondTag}",
				consolidatedString);
	}

	@Test
	public void testMapJSONStringsToConsolidatedString3() {
		String consolidatedString = EntityMapper
				.mapJSONStringsToConsolidatedString(JSONStrings2);
		StringBuilder sb = new StringBuilder()
				.append("{id: 1, title: testTitle, author: testAuthor,description: testDescription, ISBN: testISBN, price: testPrice, tags: firstTag,secondTag}")
				.append(System.getProperty("line.separator"))
				.append("{id: 1, title: testTitle, author: testAuthor,description: testDescription, ISBN: testISBN, price: testPrice, tags: firstTag,secondTag}");
		assertEquals(sb.toString(), consolidatedString);
	}
}
