package amtc.gue.ws.test.books.utils;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.List;
import java.util.Set;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.service.inout.Book;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.service.inout.output.BookServiceResponse;
import amtc.gue.ws.books.service.inout.output.TagServiceResponse;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;

/**
 * EntityMapper Testclass
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EntityMapperTest extends UtilTest {

	@Test
	public void testMapTagsToTagEntityListUsingNullValue() {
		Set<GAEJPATagEntity> listOfTagEntities = EntityMapper
				.mapTagsToTagEntityList(null);
		assertNotNull(listOfTagEntities);
		assertEquals(0, listOfTagEntities.size());
	}

	@Test
	public void testMapTagsToTagEntityListUsingSimpleTags() {
		Set<GAEJPATagEntity> listOfTagEntities = EntityMapper
				.mapTagsToTagEntityList(existingSearchTags);
		assertNotNull(listOfTagEntities);
		assertEquals(3, listOfTagEntities.size());
	}

	@Test
	public void testMapToBookEntityForDeleteType() {
		GAEJPABookEntity bookEntity = EntityMapper.mapBookToEntity(book1,
				PersistenceTypeEnum.DELETE);
		assertEquals(book1.getAuthor(), bookEntity.getAuthor());
		assertEquals(book1.getDescription(), bookEntity.getDescription());
		assertEquals(book1.getId(), bookEntity.getKey());
		assertEquals(book1.getISBN(), bookEntity.getISBN());
		assertEquals(book1.getPrice(), bookEntity.getPrice());
		assertEquals(3, bookEntity.getTags()
				.size());
		assertEquals(book1.getTitle(), bookEntity.getTitle());
	}

	@Test
	public void testMapToBookEntityForAddType() {
		GAEJPABookEntity bookEntity = EntityMapper.mapBookToEntity(book1,
				PersistenceTypeEnum.ADD);
		assertEquals(book1.getAuthor(), bookEntity.getAuthor());
		assertEquals(book1.getDescription(), bookEntity.getDescription());
		assertNull(bookEntity.getKey());
		assertEquals(book1.getISBN(), bookEntity.getISBN());
		assertEquals(book1.getPrice(), bookEntity.getPrice());
		assertEquals(3, bookEntity.getTags()
				.size());
		assertEquals(book1.getTitle(), bookEntity.getTitle());
	}

	@Test
	public void testMapToBookEntitiyWithNullID() {
		GAEJPABookEntity bookEntity = EntityMapper.mapBookToEntity(book2,
				PersistenceTypeEnum.ADD);
		assertEquals(3, bookEntity.getTags()
				.size());
	}

	@Test
	public void testTransformBooksToEntitesUsingSimpleBooks() {
		List<GAEJPABookEntity> bookEntityList = EntityMapper
				.transformBooksToBookEntities(simpleBooks,
						PersistenceTypeEnum.ADD);
		assertNotNull(bookEntityList);
		assertEquals(2, bookEntityList.size());
	}

	@Test
	public void testTransformBooksToEntitiesUsingEmptyBooks() {
		List<GAEJPABookEntity> bookEntityList = EntityMapper
				.transformBooksToBookEntities(emptyBooks,
						PersistenceTypeEnum.ADD);
		assertEquals(0, bookEntityList.size());
	}

	@Test
	public void testTransformBooksToEntitiesUsingNullBooks() {
		List<GAEJPABookEntity> bookEntityList = EntityMapper
				.transformBooksToBookEntities(null, PersistenceTypeEnum.ADD);
		assertNotNull(bookEntityList);
		assertEquals(0, bookEntityList.size());
	}

	@Test
	public void testMapTagEntityListToTagsUsingSimpleList() {
		Tags tags = EntityMapper.mapTagEntityListToTags(tagEntityList);
		assertNotNull(tags);
		assertEquals(tagEntityList.size(), tags.getTags().size());
	}

	@Test
	public void testMapTagEntityListToTagsUsingListWithNullTags() {
		Tags tags = EntityMapper
				.mapTagEntityListToTags(tagEntityListWithNullTags);
		assertNotNull(tags);
		assertEquals(tagEntityListWithNullTags.size(), tags.getTags().size());
	}

	@Test
	public void testMapTagEntityListToTagsUsingNullInput() {
		Tags tags = EntityMapper.mapTagEntityListToTags(null);
		assertNotNull(tags);
		assertEquals(0, tags.getTags().size());
	}

	@Test
	public void testMapBookEntityToBookUsingBookEntityWithTags() {
		Book book = EntityMapper.mapBookEntityToBook(bookEntity1);
		assertNotNull(book);
		assertEquals(bookEntity1.getAuthor(), book.getAuthor());
		assertEquals(bookEntity1.getDescription(), book.getDescription());
		assertEquals(bookEntity1.getISBN(), book.getISBN());
		assertEquals(bookEntity1.getKey(), book.getId());
		assertEquals(bookEntity1.getPrice(), book.getPrice());
		assertEquals(bookEntity1.getTags().size(), book.getTags().getTags()
				.size());
		assertEquals(bookEntity1.getTitle(), book.getTitle());
	}

	@Test
	public void testMapBookEntityToBookUsingBookEntityWithoutTags() {
		Book book = EntityMapper.mapBookEntityToBook(bookEntity3);
		assertNotNull(book);
		assertEquals(bookEntity3.getAuthor(), book.getAuthor());
		assertEquals(bookEntity3.getDescription(), book.getDescription());
		assertEquals(bookEntity3.getISBN(), book.getISBN());
		assertEquals(bookEntity3.getKey(), book.getId());
		assertEquals(bookEntity3.getPrice(), book.getPrice());
		assertEquals(bookEntity3.getTags().size(), book.getTags().getTags()
				.size());
		assertEquals(bookEntity3.getTitle(), book.getTitle());
	}

	@Test
	public void testMapBookEntityToBookUsingNullBookEntity() {
		Book book = EntityMapper.mapBookEntityToBook(null);
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
		Books books = EntityMapper.transformBookEntitiesToBooks(bookEntityList);
		assertNotNull(books);
		assertEquals(bookEntityList.size(), books.getBooks().size());
	}

	@Test
	public void testTransformBookEntitiesToBooksUsingBookEntityNullList() {
		Books books = EntityMapper
				.transformBookEntitiesToBooks(bookEntityNullList);
		assertNotNull(books);
		assertEquals(bookEntityNullList.size(), books.getBooks().size());
	}

	@Test
	public void testTransformBookEntitiesToBooksUsingBookEntityTagNullList() {
		Books books = EntityMapper
				.transformBookEntitiesToBooks(bookEntityNullList);
		assertNotNull(books);
		assertEquals(bookEntityNullList.size(), books.getBooks().size());
	}

	@Test
	public void testTransformBookEntitiesToBooksUsingNullInput() {
		Books books = EntityMapper.transformBookEntitiesToBooks(null);
		assertNotNull(books);
		assertEquals(0, books.getBooks().size());
	}

	@Test
	public void testMapBookEntityToJSONStringUsingEntityWithTagsOnly() {
		String bookEntityJSON = EntityMapper
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
		String bookEntityJSON = EntityMapper
				.mapBookEntityToJSONString(bookEntity4);
		assertEquals(BOOKENTITY4_JSON, bookEntityJSON);
	}

	@Test
	public void testMapBookEntityToJSONStringUsingNullInput() {
		String bookEntityJSON = EntityMapper.mapBookEntityToJSONString(null);
		assertEquals(NULL_BOOKENTITIY_JSON, bookEntityJSON);
	}

	@Test
	public void testMapBookEntityToJSONStringUsingEntityWithNullTags() {
		bookEntity3.getTags().add(null);
		String bookEntityJSON = EntityMapper
				.mapBookEntityToJSONString(bookEntity3);
		assertEquals(BOOKENTITY3_JSON, bookEntityJSON);
	}

	@Test
	public void testMapBookEntityListToConsolidatedJSONStringUsingNullInput() {
		String consolidatedBookEntityJSON = EntityMapper
				.mapBookEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_CONSOLIDATED_BOOKENTITY_JSON,
				consolidatedBookEntityJSON);
	}

	@Test
	public void testMapBookEntityListToConsolodatedJSONStringUsingSimpleBookEntities() {
		String consolidatedBookEntityJSON = EntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList);
		assertEquals(SIMPLE_CONSOLIDATED_BOOKENTITY_JSON,
				consolidatedBookEntityJSON);
	}

	@Test
	public void testMapBookEntityListToConsolidatedJSONStringUsingEmptyInput() {
		bookEntitySimpleList.clear();
		String consolidatedBookEntityJSON = EntityMapper
				.mapBookEntityListToConsolidatedJSONString(bookEntitySimpleList);
		assertEquals(NULL_CONSOLIDATED_BOOKENTITY_JSON,
				consolidatedBookEntityJSON);
	}

	@Test
	public void testTagEntityToJSONStringUsingEntityWithBooksOnly() {
		String tagEntityJSON = EntityMapper
				.mapTagEntityToJSONString(tagEntity6);
		assertEquals(TAGENTITY6_JSON, tagEntityJSON);
	}

	@Test
	public void testMapTagEntityToJSONStringUsingEntityWithoutBooks() {
		String tagEntityJSON = EntityMapper
				.mapTagEntityToJSONString(tagEntity7);
		assertEquals(TAGENTITY7_JSON, tagEntityJSON);
	}

	@Test
	public void testMapTagEntityToJSONStringUsingNullInput() {
		String tagEntityJSON = EntityMapper.mapTagEntityToJSONString(null);
		assertEquals(NULL_TAGENTITIY_JSON, tagEntityJSON);
	}

	@Test
	public void testMapTagEntityToJSONStringUsingEntityWithNullBooks() {
		tagEntity6.getBooks().add(null);
		String tagEntityJSON = EntityMapper
				.mapTagEntityToJSONString(tagEntity6);
		assertThat(tagEntityJSON,
				isOneOf(TAGENTITY6_JSON_2_1, TAGENTITY6_JSON_2_2));
	}

	@Test
	public void testMapTagEntityListToConsolidatedJSONStringUsingNullInput() {
		String consolidatedTagEntityJSON = EntityMapper
				.mapTagEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_CONSOLIDATED_TAGENTITY_JSON,
				consolidatedTagEntityJSON);
	}

	@Test
	public void testMapTagEntityListToConsolodatedJSONStringUsingSimpleBookEntities() {
		String consolidatedTagEntityJSON = EntityMapper
				.mapTagEntityListToConsolidatedJSONString(tagEntitySimpleList);
		assertEquals(SIMPLE_CONSOLIDATED_TAGENTITY_JSON,
				consolidatedTagEntityJSON);
	}

	@Test
	public void testMapTagEntityListToConsolidatedJSONStringUsingEmptyInput() {
		tagEntitySimpleList.clear();
		String consolidatedTagEntityJSON = EntityMapper
				.mapTagEntityListToConsolidatedJSONString(tagEntitySimpleList);
		assertEquals(NULL_CONSOLIDATED_TAGENTITY_JSON,
				consolidatedTagEntityJSON);
	}

	@Test
	public void testMapBdOutputToBookServiceResponseUsingBooksOutputObject() {
		BookServiceResponse serviceResponse = EntityMapper
				.mapBdOutputToBookServiceResponse(bookDelegatorOutput);
		assertNotEquals(null, serviceResponse.getBook());
	}

	@Test
	public void testMapBdOutputToBookServiceResponseUsingUnrecognizedOutputObject() {
		BookServiceResponse serviceResponse = EntityMapper
				.mapBdOutputToBookServiceResponse(unrecognizedBookDelegatorOutput);
		assertEquals(null, serviceResponse.getBook());
	}
	
	@Test
	public void testMapBdOutputToBookServiceResponseUsingNullInput() {
		BookServiceResponse serviceResponse = EntityMapper
				.mapBdOutputToBookServiceResponse(null);
		assertNull(serviceResponse);
	}
	
	@Test
	public void testMapBdOutputToTagServiceResponseUsingTagsDelegatorOutput() {
		TagServiceResponse serviceResponse = EntityMapper
				.mapBdOutputToTagServiceResponse(tagDelegatorOutput);
		assertNotEquals(null, serviceResponse.getTags());
	}
	
	@Test
	public void testMapBdOutputToTagServiceResponseUsingUnrecognizedOutputObject() {
		TagServiceResponse serviceResponse = EntityMapper
				.mapBdOutputToTagServiceResponse(unrecognizedTagDelegatorOutput);
		assertEquals(null, serviceResponse.getTags());
	}
	
	@Test
	public void testMapBdOutputToTagServiceResponseUsingNullInput() {
		TagServiceResponse serviceResponse = EntityMapper
				.mapBdOutputToTagServiceResponse(null);
		assertNull(serviceResponse);
	}
}
