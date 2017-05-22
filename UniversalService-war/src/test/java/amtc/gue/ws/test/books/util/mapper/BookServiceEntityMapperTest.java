package amtc.gue.ws.test.books.util.mapper;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.jpa.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.response.BookServiceResponse;
import amtc.gue.ws.books.response.TagServiceResponse;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;
import amtc.gue.ws.books.util.mapper.jpa.BookServiceJPAEntityMapper;
import amtc.gue.ws.books.util.mapper.objectify.BookServiceObjectifyEntityMapper;
import amtc.gue.ws.test.books.BookTest;

/**
 * Testclass for the BookServiceEntityMapper
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookServiceEntityMapperTest extends BookTest {
	private static String BOOK_ENTITY_JSON;
	private static String COMPLEX_BOOK_ENTITY_JSON;
	private static String BOOK_ENTITY_LIST_JSON;
	private static String NULL_BOOK_ENTITY_JSON = "{}";
	private static String NULL_BOOK_ENTITY_LIST_JSON = "[]";
	private static String TAG_ENTITY_JSON;
	private static String COMPLEX_TAG_ENTITY_JSON;
	private static String TAG_ENTITY_LIST_JSON;
	private static String NULL_TAG_ENTITY_JSON = "{}";
	private static String NULL_TAG_ENTITY_LIST_JSON = "[]";
	private static IDelegatorOutput bookDelegatorOutput;
	private static IDelegatorOutput unrecognizedBookDelegatorOutput;
	private static IDelegatorOutput tagDelegatorOutput;
	private static IDelegatorOutput unrecognizedTagDelegatorOutput;

	private BookServiceEntityMapper JPABookEntityMapper = new BookServiceJPAEntityMapper();
	private BookServiceEntityMapper objectifyBookEntityMapper = new BookServiceObjectifyEntityMapper();

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		setUpBasicBookEnvironment();
		setUpJSONStrings();
		setUpBdOutputs();
	}

	@Test
	public void testMapTagsToTagEntityListUsingNullValue() {
		Set<GAETagEntity> listOfTagEntities = JPABookEntityMapper.mapTagsToTagEntityList(null);
		assertNotNull(listOfTagEntities);
		assertEquals(0, listOfTagEntities.size());
	}

	@Test
	public void testMapTagsToTagEntityListUsingSimpleTags() {
		Set<GAETagEntity> listOfTagEntities = JPABookEntityMapper.mapTagsToTagEntityList(tagsA.getTags());
		assertEquals(1, listOfTagEntities.size());
	}

	@Test
	public void testMapToBookEntityForDeleteType() {
		GAEBookEntity bookEntity = JPABookEntityMapper.mapBookToEntity(book1, DelegatorTypeEnum.DELETE);
		assertEquals(book1.getAuthor(), bookEntity.getAuthor());
		assertEquals(book1.getDescription(), bookEntity.getDescription());
		assertEquals(book1.getId(), bookEntity.getKey());
		assertEquals(book1.getISBN(), bookEntity.getISBN());
		assertEquals(book1.getPrice(), bookEntity.getPrice());
		assertEquals(1, bookEntity.getTags().size());
		assertEquals(book1.getTitle(), bookEntity.getTitle());
	}

	@Test
	public void testMapToBookEntityForAddType() {
		GAEBookEntity bookEntity = JPABookEntityMapper.mapBookToEntity(book1, DelegatorTypeEnum.ADD);
		assertEquals(book1.getAuthor(), bookEntity.getAuthor());
		assertEquals(book1.getDescription(), bookEntity.getDescription());
		assertNull(bookEntity.getKey());
		assertEquals(book1.getISBN(), bookEntity.getISBN());
		assertEquals(book1.getPrice(), bookEntity.getPrice());
		assertEquals(1, bookEntity.getTags().size());
		assertEquals(book1.getTitle(), bookEntity.getTitle());
	}

	@Test
	public void testMapToBookEntityWithNullID() {
		GAEBookEntity bookEntity = objectifyBookEntityMapper.mapBookToEntity(book2, DelegatorTypeEnum.ADD);
		assertEquals(1, bookEntity.getTags().size());
	}

	@Test
	public void testTransformBooksToEntitesUsingSimpleBooks() {
		List<GAEBookEntity> bookEntityList = objectifyBookEntityMapper.transformBooksToBookEntities(books,
				DelegatorTypeEnum.ADD);
		assertNotNull(bookEntityList);
		assertEquals(2, bookEntityList.size());
	}

	@Test
	public void testTransformBooksToEntitiesUsingEmptyBooks() {
		List<GAEBookEntity> bookEntityList = objectifyBookEntityMapper.transformBooksToBookEntities(emptyBooks,
				DelegatorTypeEnum.ADD);
		assertEquals(0, bookEntityList.size());
	}

	@Test
	public void testTransformBooksToEntitiesUsingNullBooks() {
		List<GAEBookEntity> bookEntityList = objectifyBookEntityMapper.transformBooksToBookEntities(null,
				DelegatorTypeEnum.ADD);
		assertNotNull(bookEntityList);
		assertEquals(0, bookEntityList.size());
	}

	@Test
	public void testMapTagEntityListToTagsUsingSimpleList() {
		Tags tags = new Tags();
		tags.setTags(BookServiceEntityMapper.mapTagEntityListToTags(JPATagEntityList));
		assertEquals(JPATagEntityList.size(), tags.getTags().size());
	}

	@Test
	public void testMapTagEntityListToTagsUsingListWithNullTags() {
		Tags tags = new Tags();
		tags.setTags(BookServiceEntityMapper.mapTagEntityListToTags(JPATagEntityNullValueList));
		assertNotNull(tags);
		assertEquals(JPATagEntityNullValueList.size(), tags.getTags().size());
	}

	@Test
	public void testMapTagEntityListToTagsUsingNullInput() {
		Tags tags = new Tags();
		tags.setTags(BookServiceEntityMapper.mapTagEntityListToTags(null));
		assertNotNull(tags);
		assertEquals(0, tags.getTags().size());
	}

	@Test
	public void testMapBookEntityToBookUsingBookEntityWithTags() {
		Book book = BookServiceEntityMapper.mapBookEntityToBook(JPABookEntity1);
		assertEquals(JPABookEntity1.getAuthor(), book.getAuthor());
		assertEquals(JPABookEntity1.getDescription(), book.getDescription());
		assertEquals(JPABookEntity1.getISBN(), book.getISBN());
		assertEquals(JPABookEntity1.getKey(), book.getId());
		assertEquals(JPABookEntity1.getPrice(), book.getPrice());
		assertEquals(JPABookEntity1.getTags().size(), book.getTags().size());
		assertEquals(JPABookEntity1.getTitle(), book.getTitle());
	}

	@Test
	public void testMapBookEntityToBookUsingBookEntityWithoutTags() {
		Book book = BookServiceEntityMapper.mapBookEntityToBook(JPABookEntity2);
		assertEquals(JPABookEntity2.getAuthor(), book.getAuthor());
		assertEquals(JPABookEntity2.getDescription(), book.getDescription());
		assertEquals(JPABookEntity2.getISBN(), book.getISBN());
		assertEquals(JPABookEntity2.getKey(), book.getId());
		assertEquals(JPABookEntity2.getPrice(), book.getPrice());
		assertEquals(JPABookEntity2.getTags().size(), book.getTags().size());
		assertEquals(JPABookEntity2.getTitle(), book.getTitle());
	}

	@Test
	public void testMapBookEntityToBookUsingNullBookEntity() {
		Book book = BookServiceEntityMapper.mapBookEntityToBook(null);
		assertNotNull(book);
		assertNull(book.getAuthor());
		assertNull(book.getDescription());
		assertNull(book.getISBN());
		assertNull(book.getId());
		assertNull(book.getPrice());
		assertNull(book.getTags());
		assertNull(book.getTitle());
	}

	@Test
	public void testTransformBookEntitiesToBooksUsingSimpleBookEntityList() {
		Books books = BookServiceEntityMapper.transformBookEntitiesToBooks(JPABookEntityList);
		assertEquals(JPABookEntityList.size(), books.getBooks().size());
	}

	@Test
	public void testTransformBookEntitiesToBooksUsingBookEntityNullList() {
		Books books = BookServiceEntityMapper.transformBookEntitiesToBooks(JPABookEntityNullValueList);
		assertEquals(JPABookEntityNullValueList.size(), books.getBooks().size());
	}

	@Test
	public void testTransformBookEntitiesToBooksUsingBookEntityTagNullList() {
		Books books = BookServiceEntityMapper.transformBookEntitiesToBooks(JPABookEntityTagNullList);
		assertEquals(JPABookEntityTagNullList.size(), books.getBooks().size());
	}

	@Test
	public void testTransformBookEntitiesToBooksUsingNullInput() {
		Books books = BookServiceEntityMapper.transformBookEntitiesToBooks(null);
		assertNotNull(books);
		assertEquals(0, books.getBooks().size());
	}

	@Test
	public void testMapBookEntityToJSONStringUsingEntityWithTagsOnly() {
		String bookEntityJSON = BookServiceEntityMapper.mapBookEntityToJSONString(JPABookEntity4);
		assertEquals(COMPLEX_BOOK_ENTITY_JSON, bookEntityJSON);
	}

	@Test
	public void testMapBookEntityToJSONStringUsingEntityWithoutTags() {
		String bookEntityJSON = BookServiceEntityMapper.mapBookEntityToJSONString(JPABookEntity5);
		assertEquals(BOOK_ENTITY_JSON, bookEntityJSON);
	}

	@Test
	public void testMapBookEntityToJSONStringUsingNullInput() {
		String bookEntityJSON = BookServiceEntityMapper.mapBookEntityToJSONString(null);
		assertEquals(NULL_BOOK_ENTITY_JSON, bookEntityJSON);
	}

	@Test
	public void testMapBookEntityToJSONStringUsingEntityWithNullTags() {
		GAEBookEntity bookEntity = new GAEJPABookEntity();
		bookEntity.setKey(BOOKKEY);
		bookEntity.getTags().add(null);
		String bookEntityJSON = BookServiceEntityMapper.mapBookEntityToJSONString(bookEntity);
		assertEquals(BOOK_ENTITY_JSON, bookEntityJSON);
	}

	@Test
	public void testMapBookEntityListToConsolidatedJSONStringUsingNullInput() {
		String consolidatedBookEntityJSON = BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_BOOK_ENTITY_LIST_JSON, consolidatedBookEntityJSON);
	}

	@Test
	public void testMapBookEntityListToConsolodatedJSONStringUsingSimpleBookEntities() {
		String consolidatedBookEntityJSON = BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(JPABookEntityList);
		assertEquals(BOOK_ENTITY_LIST_JSON, consolidatedBookEntityJSON);
	}

	@Test
	public void testMapBookEntityListToConsolidatedJSONStringUsingEmptyInput() {
		String consolidatedBookEntityJSON = BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(JPABookEntityEmptyList);
		assertEquals(NULL_BOOK_ENTITY_LIST_JSON, consolidatedBookEntityJSON);
	}

	@Test
	public void testTagEntityToJSONStringUsingEntityWithBooksOnly() {
		String tagEntityJSON = BookServiceEntityMapper.mapTagEntityToJSONString(JPATagEntity5);
		assertEquals(COMPLEX_TAG_ENTITY_JSON, tagEntityJSON);
	}

	@Test
	public void testMapTagEntityToJSONStringUsingEntityWithoutBooks() {
		String tagEntityJSON = BookServiceEntityMapper.mapTagEntityToJSONString(JPATagEntity1);
		assertEquals(TAG_ENTITY_JSON, tagEntityJSON);
	}

	@Test
	public void testMapTagEntityToJSONStringUsingNullInput() {
		String tagEntityJSON = BookServiceEntityMapper.mapTagEntityToJSONString(null);
		assertEquals(NULL_TAG_ENTITY_JSON, tagEntityJSON);
	}

	@Test
	public void testMapTagEntityListToConsolidatedJSONStringUsingNullInput() {
		String consolidatedTagEntityJSON = BookServiceEntityMapper.mapTagEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_TAG_ENTITY_LIST_JSON, consolidatedTagEntityJSON);
	}

	@Test
	public void testMapTagEntityListToConsolodatedJSONStringUsingSimpleTagEntities() {
		String consolidatedTagEntityJSON = BookServiceEntityMapper
				.mapTagEntityListToConsolidatedJSONString(JPATagEntityList);
		assertEquals(TAG_ENTITY_LIST_JSON, consolidatedTagEntityJSON);
	}

	@Test
	public void testMapTagEntityListToConsolidatedJSONStringUsingEmptyInput() {
		String consolidatedTagEntityJSON = BookServiceEntityMapper
				.mapTagEntityListToConsolidatedJSONString(JPATagEntityEmptyList);
		assertEquals(NULL_TAG_ENTITY_LIST_JSON, consolidatedTagEntityJSON);
	}

	@Test
	public void testMapBdOutputToBookServiceResponseUsingBooksOutputObject() {
		BookServiceResponse serviceResponse = BookServiceEntityMapper
				.mapBdOutputToBookServiceResponse(bookDelegatorOutput);
		assertNotNull(serviceResponse.getBooks());
	}

	@Test
	public void testMapBdOutputToBookServiceResponseUsingUnrecognizedOutputObject() {
		BookServiceResponse serviceResponse = BookServiceEntityMapper
				.mapBdOutputToBookServiceResponse(unrecognizedBookDelegatorOutput);
		assertNull(serviceResponse.getBooks());
	}

	@Test
	public void testMapBdOutputToBookServiceResponseUsingNullInput() {
		BookServiceResponse serviceResponse = BookServiceEntityMapper.mapBdOutputToBookServiceResponse(null);
		assertNull(serviceResponse);
	}

	@Test
	public void testMapBdOutputToTagServiceResponseUsingTagsOutputObject() {
		TagServiceResponse serviceResponse = BookServiceEntityMapper
				.mapBdOutputToTagServiceResponse(tagDelegatorOutput);
		assertNotNull(serviceResponse.getTags());
	}

	@Test
	public void testMapBdOutputToTagServiceResponseUsingUnrecognizedOutputObject() {
		TagServiceResponse serviceResponse = BookServiceEntityMapper
				.mapBdOutputToTagServiceResponse(unrecognizedTagDelegatorOutput);
		assertNull(serviceResponse.getTags());
	}

	@Test
	public void testMapBdOutputToTagServiceResponseUsingNullInput() {
		TagServiceResponse serviceResponse = BookServiceEntityMapper.mapBdOutputToTagServiceResponse(null);
		assertNull(serviceResponse);
	}

	@Test
	public void testCopyBookEntity() {
		GAEBookEntity bookEntityCopy = JPABookEntityMapper.copyBookEntity(JPABookEntity1);
		assertNotNull(bookEntityCopy);
		assertEquals(JPABookEntity1.getKey(), bookEntityCopy.getKey());
		assertEquals(JPABookEntity1.getAuthor(), bookEntityCopy.getAuthor());
		assertEquals(JPABookEntity1.getDescription(), bookEntityCopy.getDescription());
		assertEquals(JPABookEntity1.getISBN(), bookEntityCopy.getISBN());
		assertEquals(JPABookEntity1.getPrice(), bookEntityCopy.getPrice());
		assertEquals(0, bookEntityCopy.getTags().size());
		assertEquals(JPABookEntity1.getTitle(), bookEntityCopy.getTitle());
		assertEquals(0, bookEntityCopy.getUsers().size());
	}

	/**
	 * Method initializing JSON Strings
	 */
	private static void setUpJSONStrings() {
		StringBuilder sb = new StringBuilder();
		sb.append("{id: null, ").append("title: null, ").append("author: null, ").append("description: null, ")
				.append("ISBN: null, ").append("price: null, ").append("tags: [").append(TAG).append("]}");
		COMPLEX_BOOK_ENTITY_JSON = sb.toString();

		sb.setLength(0);
		sb.append("{id: ").append(BOOKKEY + ", ").append("title: null, ").append("author: null, ")
				.append("description: null, ").append("ISBN: null, ").append("price: null, ").append("tags: []}");
		BOOK_ENTITY_JSON = sb.toString();

		sb.setLength(0);
		sb.append("[").append("{id: null, ").append("title: ").append(TITLE + ", ").append("author: ")
				.append(AUTHOR + ", ").append("description: ").append(DESCRIPTION + ", ").append("ISBN: ")
				.append(ISBN + ", ").append("price: ").append(PRICE + ", ").append("tags: [").append("]}").append("]");
		BOOK_ENTITY_LIST_JSON = sb.toString();

		sb.setLength(0);
		sb.append("{tagName: null, books: [").append("{id: ").append("null, ").append("title: null, ")
				.append("author: null, ").append("description: null, ").append("ISBN: null, ").append("price: null, ")
				.append("tags: []}").append("]}");
		COMPLEX_TAG_ENTITY_JSON = sb.toString();

		sb.setLength(0);
		sb.append("{tagName: " + TAG + ", ").append("books: []}");
		TAG_ENTITY_JSON = sb.toString();

		sb.setLength(0);
		sb.append("[").append(TAG_ENTITY_JSON).append("]");
		TAG_ENTITY_LIST_JSON = sb.toString();
	}

	/**
	 * Method initializing Delegatoroutputs
	 */
	private static void setUpBdOutputs() {
		bookDelegatorOutput = new DelegatorOutput();
		bookDelegatorOutput.setOutputObject(books);
		unrecognizedBookDelegatorOutput = new DelegatorOutput();
		unrecognizedBookDelegatorOutput.setOutputObject(null);
		tagDelegatorOutput = new DelegatorOutput();
		tagDelegatorOutput.setOutputObject(tagsA);
		unrecognizedTagDelegatorOutput = new DelegatorOutput();
		unrecognizedTagDelegatorOutput.setOutputObject(null);
	}
}
