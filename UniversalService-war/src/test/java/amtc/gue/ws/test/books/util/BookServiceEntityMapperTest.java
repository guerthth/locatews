package amtc.gue.ws.test.books.util;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.response.BookServiceResponse;
import amtc.gue.ws.books.response.TagServiceResponse;
import amtc.gue.ws.books.util.BookServiceEntityMapper;

/**
 * EntityMapper Testclass
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookServiceEntityMapperTest extends BookServiceUtilTest {

	@Test
	public void testMapTagsToTagEntityListUsingNullValue() {
		Set<GAEJPATagEntity> listOfTagEntities = BookServiceEntityMapper
				.mapTagsToTagEntityList(null);
		assertNotNull(listOfTagEntities);
		assertEquals(0, listOfTagEntities.size());
	}

	@Test
	public void testMapTagsToTagEntityListUsingSimpleTags() {
		Set<GAEJPATagEntity> listOfTagEntities = BookServiceEntityMapper
				.mapTagsToTagEntityList(existingSearchTags.getTags());
		assertNotNull(listOfTagEntities);
		assertEquals(3, listOfTagEntities.size());
	}

	@Test
	public void testMapToBookEntityForDeleteType() {
		GAEJPABookEntity bookEntity = BookServiceEntityMapper.mapBookToEntity(
				book1, DelegatorTypeEnum.DELETE);
		assertEquals(book1.getAuthor(), bookEntity.getAuthor());
		assertEquals(book1.getDescription(), bookEntity.getDescription());
		assertEquals(book1.getId(), bookEntity.getKey());
		assertEquals(book1.getISBN(), bookEntity.getISBN());
		assertEquals(book1.getPrice(), bookEntity.getPrice());
		assertEquals(3, bookEntity.getTags().size());
		assertEquals(book1.getTitle(), bookEntity.getTitle());
	}

	@Test
	public void testMapToBookEntityForAddType() {
		GAEJPABookEntity bookEntity = BookServiceEntityMapper.mapBookToEntity(
				book1, DelegatorTypeEnum.ADD);
		assertEquals(book1.getAuthor(), bookEntity.getAuthor());
		assertEquals(book1.getDescription(), bookEntity.getDescription());
		assertNull(bookEntity.getKey());
		assertEquals(book1.getISBN(), bookEntity.getISBN());
		assertEquals(book1.getPrice(), bookEntity.getPrice());
		assertEquals(3, bookEntity.getTags().size());
		assertEquals(book1.getTitle(), bookEntity.getTitle());
	}

	@Test
	public void testMapToBookEntityWithNullID() {
		GAEJPABookEntity bookEntity = BookServiceEntityMapper.mapBookToEntity(
				book2, DelegatorTypeEnum.ADD);
		assertEquals(3, bookEntity.getTags().size());
	}

	@Test
	public void testTransformBooksToEntitesUsingSimpleBooks() {
		List<GAEJPABookEntity> bookEntityList = BookServiceEntityMapper
				.transformBooksToBookEntities(simpleBooks,
						DelegatorTypeEnum.ADD);
		assertNotNull(bookEntityList);
		assertEquals(2, bookEntityList.size());
	}

	@Test
	public void testTransformBooksToEntitiesUsingEmptyBooks() {
		List<GAEJPABookEntity> bookEntityList = BookServiceEntityMapper
				.transformBooksToBookEntities(emptyBooks,
						DelegatorTypeEnum.ADD);
		assertEquals(0, bookEntityList.size());
	}

	@Test
	public void testTransformBooksToEntitiesUsingNullBooks() {
		List<GAEJPABookEntity> bookEntityList = BookServiceEntityMapper
				.transformBooksToBookEntities(null, DelegatorTypeEnum.ADD);
		assertNotNull(bookEntityList);
		assertEquals(0, bookEntityList.size());
	}

	@Test
	public void testMapTagEntityListToTagsUsingSimpleList() {
		Tags tags = new Tags();
		tags.setTags(BookServiceEntityMapper
				.mapTagEntityListToTags(tagEntityList));
		assertNotNull(tags);
		assertEquals(tagEntityList.size(), tags.getTags().size());
	}

	@Test
	public void testMapTagEntityListToTagsUsingListWithNullTags() {
		Tags tags = new Tags();
		tags.setTags(BookServiceEntityMapper
				.mapTagEntityListToTags(tagEntityListWithNullTags));
		assertNotNull(tags);
		assertEquals(tagEntityListWithNullTags.size(), tags.getTags().size());
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
		Book book = BookServiceEntityMapper.mapBookEntityToBook(bookEntity1);
		assertNotNull(book);
		assertEquals(bookEntity1.getAuthor(), book.getAuthor());
		assertEquals(bookEntity1.getDescription(), book.getDescription());
		assertEquals(bookEntity1.getISBN(), book.getISBN());
		assertEquals(bookEntity1.getKey(), book.getId());
		assertEquals(bookEntity1.getPrice(), book.getPrice());
		assertEquals(bookEntity1.getTags().size(), book.getTags().size());
		assertEquals(bookEntity1.getTitle(), book.getTitle());
	}

	@Test
	public void testMapBookEntityToBookUsingBookEntityWithoutTags() {
		Book book = BookServiceEntityMapper.mapBookEntityToBook(bookEntity3);
		assertNotNull(book);
		assertEquals(bookEntity3.getAuthor(), book.getAuthor());
		assertEquals(bookEntity3.getDescription(), book.getDescription());
		assertEquals(bookEntity3.getISBN(), book.getISBN());
		assertEquals(bookEntity3.getKey(), book.getId());
		assertEquals(bookEntity3.getPrice(), book.getPrice());
		assertEquals(bookEntity3.getTags().size(), book.getTags().size());
		assertEquals(bookEntity3.getTitle(), book.getTitle());
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
		Books books = BookServiceEntityMapper
				.transformBookEntitiesToBooks(bookEntityList);
		assertNotNull(books);
		assertEquals(bookEntityList.size(), books.getBooks().size());
	}

	@Test
	public void testTransformBookEntitiesToBooksUsingBookEntityNullList() {
		Books books = BookServiceEntityMapper
				.transformBookEntitiesToBooks(bookEntityNullList);
		assertNotNull(books);
		assertEquals(bookEntityNullList.size(), books.getBooks().size());
	}

	@Test
	public void testTransformBookEntitiesToBooksUsingBookEntityTagNullList() {
		Books books = BookServiceEntityMapper
				.transformBookEntitiesToBooks(bookEntityNullList);
		assertNotNull(books);
		assertEquals(bookEntityNullList.size(), books.getBooks().size());
	}

	@Test
	public void testTransformBookEntitiesToBooksUsingNullInput() {
		Books books = BookServiceEntityMapper
				.transformBookEntitiesToBooks(null);
		assertNotNull(books);
		assertEquals(0, books.getBooks().size());
	}

	@Test
	public void testMapBookEntityToJSONStringUsingEntityWithTagsOnly() {
		String bookEntityJSON = BookServiceEntityMapper
				.mapBookEntityToJSONString(bookEntity1);
		String firstPart = bookEntityJSON.substring(0, 88);
		String secondPart = bookEntityJSON.substring(88,
				bookEntityJSON.length());
		assertEquals(BOOKENTITY1_JSON_1, firstPart);
		assertTrue("Expected tag " + tagStringA
				+ " not included in built JSON String",
				secondPart.contains(tagStringA));
		assertTrue("Expected tag " + tagStringB
				+ " not included in built JSON String",
				secondPart.contains(tagStringB));
		assertTrue("Expected tag " + tagStringC
				+ " not included in built JSON String",
				secondPart.contains(tagStringC));
	}

	@Test
	public void testMapBookEntityToJSONStringUsingEntityWithoutTags() {
		String bookEntityJSON = BookServiceEntityMapper
				.mapBookEntityToJSONString(bookEntity4);
		assertEquals(BOOKENTITY4_JSON, bookEntityJSON);
	}

	@Test
	public void testMapBookEntityToJSONStringUsingNullInput() {
		String bookEntityJSON = BookServiceEntityMapper
				.mapBookEntityToJSONString(null);
		assertEquals(NULL_BOOKENTITIY_JSON, bookEntityJSON);
	}

	@Test
	public void testMapBookEntityToJSONStringUsingEntityWithNullTags() {
		bookEntity3.getTags().add(null);
		String bookEntityJSON = BookServiceEntityMapper
				.mapBookEntityToJSONString(bookEntity3);
		assertEquals(BOOKENTITY3_JSON, bookEntityJSON);
	}

	@Test
	public void testMapBookEntityListToConsolidatedJSONStringUsingNullInput() {
		String consolidatedBookEntityJSON = BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_CONSOLIDATED_BOOKENTITY_JSON,
				consolidatedBookEntityJSON);
	}

	@Test
	public void testMapBookEntityListToConsolodatedJSONStringUsingSimpleBookEntities() {
		String consolidatedBookEntityJSON = BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList);
		assertEquals(SIMPLE_CONSOLIDATED_BOOKENTITY_JSON,
				consolidatedBookEntityJSON);
	}

	@Test
	public void testMapBookEntityListToConsolidatedJSONStringUsingEmptyInput() {
		bookEntitySimpleList.clear();
		String consolidatedBookEntityJSON = BookServiceEntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList);
		assertEquals(NULL_CONSOLIDATED_BOOKENTITY_JSON,
				consolidatedBookEntityJSON);
	}

	@Test
	public void testTagEntityToJSONStringUsingEntityWithBooksOnly() {
		String tagEntityJSON = BookServiceEntityMapper
				.mapTagEntityToJSONString(tagEntity6);
		assertEquals(TAGENTITY6_JSON, tagEntityJSON);
	}

	@Test
	public void testMapTagEntityToJSONStringUsingEntityWithoutBooks() {
		String tagEntityJSON = BookServiceEntityMapper
				.mapTagEntityToJSONString(tagEntity7);
		assertEquals(TAGENTITY7_JSON, tagEntityJSON);
	}

	@Test
	public void testMapTagEntityToJSONStringUsingNullInput() {
		String tagEntityJSON = BookServiceEntityMapper
				.mapTagEntityToJSONString(null);
		assertEquals(NULL_TAGENTITIY_JSON, tagEntityJSON);
	}

	@Test
	public void testMapTagEntityToJSONStringUsingEntityWithNullBooks() {
		tagEntity6.getBooks().add(null);
		String tagEntityJSON = BookServiceEntityMapper
				.mapTagEntityToJSONString(tagEntity6);
		assertThat(tagEntityJSON,
				isOneOf(TAGENTITY6_JSON_2_1, TAGENTITY6_JSON_2_2));
	}

	@Test
	public void testMapTagEntityListToConsolidatedJSONStringUsingNullInput() {
		String consolidatedTagEntityJSON = BookServiceEntityMapper
				.mapTagEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_CONSOLIDATED_TAGENTITY_JSON,
				consolidatedTagEntityJSON);
	}

	@Test
	public void testMapTagEntityListToConsolodatedJSONStringUsingSimpleBookEntities() {
		String consolidatedTagEntityJSON = BookServiceEntityMapper
				.mapTagEntityListToConsolidatedJSONString(tagEntitySimpleList);
		assertEquals(SIMPLE_CONSOLIDATED_TAGENTITY_JSON,
				consolidatedTagEntityJSON);
	}

	@Test
	public void testMapTagEntityListToConsolidatedJSONStringUsingEmptyInput() {
		tagEntitySimpleList.clear();
		String consolidatedTagEntityJSON = BookServiceEntityMapper
				.mapTagEntityListToConsolidatedJSONString(tagEntitySimpleList);
		assertEquals(NULL_CONSOLIDATED_TAGENTITY_JSON,
				consolidatedTagEntityJSON);
	}

	@Test
	public void testMapBdOutputToBookServiceResponseUsingBooksOutputObject() {
		BookServiceResponse serviceResponse = BookServiceEntityMapper
				.mapBdOutputToBookServiceResponse(bookDelegatorOutput);
		assertNotEquals(null, serviceResponse.getBooks());
	}

	@Test
	public void testMapBdOutputToBookServiceResponseUsingUnrecognizedOutputObject() {
		BookServiceResponse serviceResponse = BookServiceEntityMapper
				.mapBdOutputToBookServiceResponse(unrecognizedBookDelegatorOutput);
		assertEquals(null, serviceResponse.getBooks());
	}

	@Test
	public void testMapBdOutputToBookServiceResponseUsingNullInput() {
		BookServiceResponse serviceResponse = BookServiceEntityMapper
				.mapBdOutputToBookServiceResponse(null);
		assertNull(serviceResponse);
	}

	@Test
	public void testMapBdOutputToTagServiceResponseUsingTagsDelegatorOutput() {
		TagServiceResponse serviceResponse = BookServiceEntityMapper
				.mapBdOutputToTagServiceResponse(tagDelegatorOutput);
		assertEquals(null, serviceResponse.getTags());
	}

	@Test
	public void testMapBdOutputToTagServiceResponseUsingUnrecognizedOutputObject() {
		TagServiceResponse serviceResponse = BookServiceEntityMapper
				.mapBdOutputToTagServiceResponse(unrecognizedTagDelegatorOutput);
		assertEquals(null, serviceResponse.getTags());
	}

	@Test
	public void testMapBdOutputToTagServiceResponseUsingNullInput() {
		TagServiceResponse serviceResponse = BookServiceEntityMapper
				.mapBdOutputToTagServiceResponse(null);
		assertNull(serviceResponse);
	}
}
