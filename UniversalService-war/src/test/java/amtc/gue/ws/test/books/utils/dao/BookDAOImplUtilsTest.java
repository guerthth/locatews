package amtc.gue.ws.test.books.utils.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.utils.dao.BookDAOImplUtils;
import amtc.gue.ws.test.books.utils.UtilTest;

/**
 * This class tests the functionality of the BookDAOImplUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookDAOImplUtilsTest extends UtilTest {

	private static final String BASIC_BOOK_SPECIFIC_QUERY = "select be from BookEntity be";
	private static final String UPDATED_BOOK_QUERY_1 = "select be from BookEntity be"
			+ " where be.bookId = :id"
			+ " and be.title = :title"
			+ " and be.author = :author and be.price = :price and be.ISBN = :ISBN"
			+ " and be.description = :description";
	private static final String UPDATED_BOOK_QUERY_2 = "select be from BookEntity be"
			+ " where be.author = :author and be.price = :price and be.ISBN = :ISBN"
			+ " and be.description = :description";

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
						existingSearchTags).size();
		assertEquals(0, resultListSize);
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
						bookEntityTagNullList, existingSearchTags).size();
		assertEquals(0, resultListSize);
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
}
