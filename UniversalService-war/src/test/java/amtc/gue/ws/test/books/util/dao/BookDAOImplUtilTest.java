package amtc.gue.ws.test.books.util.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.util.dao.BookDAOImplUtils;
import amtc.gue.ws.test.books.util.BookServiceUtilTest;

/**
 * This class tests the functionality of the BookDAOImplUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookDAOImplUtilTest extends BookServiceUtilTest {

	private static final String BASIC_BOOK_SPECIFIC_QUERY = "select e from BookEntity e";
	private static final String UPDATED_BOOK_QUERY_1 = "select e from BookEntity e"
			+ " where e.bookId = :id"
			+ " and e.title = :title"
			+ " and e.author = :author and e.price = :price and e.ISBN = :ISBN"
			+ " and e.description = :description";
	private static final String UPDATED_BOOK_QUERY_2 = "select e from BookEntity e"
			+ " where e.author = :author and e.price = :price and e.ISBN = :ISBN"
			+ " and e.description = :description";
	private static final String UPDATED_BOOK_QUERY_3 = "select e from BookEntity e"
			+ " where e.author = :author and e.users = :user";

	@Test
	public void testCopyBookListUsingSimpleList() {
		List<GAEJPABookEntity> copiedBookEntityList = BookDAOImplUtils
				.copyBookList(bookEntityList);
		assertEquals(bookEntityList.size(), copiedBookEntityList.size());
	}

	@Test
	public void testCopyBookListUsingNullList() {
		List<GAEJPABookEntity> copiedBookEntityList = BookDAOImplUtils
				.copyBookList(null);
		assertNull(copiedBookEntityList);
	}

	@Test
	public void testRetrieveBookEntitiesWithExistingTags() {
		int resultListSize = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTags(bookEntityList,
						existingSearchTags).size();
		assertEquals(2, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithNonExistingTags() {
		int resultListSize = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTags(bookEntityList,
						nonExistingSearchTags).size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithNullTags() {
		int resultListSize = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTags(bookEntityTagNullList,
						null).size();
		assertEquals(1, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithEmptyBookList() {
		int resultListSize = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTags(bookEntityNullList,
						existingSearchTags).size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithTagsOnlyUsingExistingTags() {
		int resultListSize = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTagsOnly(bookEntityList,
						existingSearchTags).size();
		assertEquals(2, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithTagsOnlyUsingNonExistingTags() {
		int resultListSize = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTagsOnly(bookEntityList,
						nonExistingSearchTags).size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithTagsOnlyUsingNullTags() {
		int resultListSize = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTagsOnly(
						bookEntityTagNullList, null).size();
		assertEquals(1, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithTagsOnlyUsingEmptyBookList() {
		int resultListSize = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTagsOnly(bookEntityNullList,
						existingSearchTags).size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testBuildSpecificBookQueryWithNullEntity() {
		String updatedQuery = BookDAOImplUtils.buildSpecificBookQuery(
				BASIC_BOOK_SPECIFIC_QUERY, null);
		assertEquals(BASIC_BOOK_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildSpecificBookQueryNoSpecialCriteria() {
		String updatedQuery = BookDAOImplUtils.buildSpecificBookQuery(
				BASIC_BOOK_SPECIFIC_QUERY, bookEntity1);
		assertEquals(BASIC_BOOK_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildSpecificBookQueryMajorCriteria() {
		String updatedQuery = BookDAOImplUtils.buildSpecificBookQuery(
				BASIC_BOOK_SPECIFIC_QUERY, bookEntity4);
		assertEquals(UPDATED_BOOK_QUERY_1, updatedQuery);
	}

	@Test
	public void testBuildSpecificBookQueryMinorCriteria() {
		String updatedQuery = BookDAOImplUtils.buildSpecificBookQuery(
				BASIC_BOOK_SPECIFIC_QUERY, bookEntity3);
		assertEquals(UPDATED_BOOK_QUERY_2, updatedQuery);
	}

	@Test
	public void testBuildSpecificBookQueryWithUsers() {
		String updatedQuery = BookDAOImplUtils.buildSpecificBookQuery(
				BASIC_BOOK_SPECIFIC_QUERY, bookEntity6);
		assertEquals(UPDATED_BOOK_QUERY_3, updatedQuery);
	}
}
