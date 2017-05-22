package amtc.gue.ws.test.books.util.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.util.dao.BookDAOImplUtils;
import amtc.gue.ws.test.books.BookTest;

/**
 * This class tests the functionality of the BookDAOImplUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookDAOImplUtilTest extends BookTest {

	private static final String BASIC_BOOK_SPECIFIC_QUERY = "select e from BookEntity e";
	private static final String UPDATED_BOOK_QUERY_1 = "select e from BookEntity e where e.title = :title "
			+ "and e.author = :author and e.price = :price " + "and e.ISBN = :ISBN and e.description = :description";
	private static final String UPDATED_BOOK_QUERY_2 = "select e from BookEntity e" + " where e.author = :author";

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicBookEnvironment();
	}

	@Test
	public void testCopyBookListUsingSimpleList() {
		List<GAEBookEntity> copiedBookEntityList = BookDAOImplUtils.copyBookList(JPABookEntityList);
		assertEquals(JPABookEntityList.size(), copiedBookEntityList.size());
	}

	@Test
	public void testCopyBookListUsingNullList() {
		List<GAEBookEntity> copiedBookEntityList = BookDAOImplUtils.copyBookList(null);
		assertNull(copiedBookEntityList);
	}

	@Test
	public void testRetrieveBookEntitiesWithExistingTags() {
		int resultListSize = BookDAOImplUtils.retrieveBookEntitiesWithSpecificTags(JPABookEntityTagAList, tagsA).size();
		assertEquals(1, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithNonExistingTags() {
		int resultListSize = BookDAOImplUtils.retrieveBookEntitiesWithSpecificTags(JPABookEntityTagAList, tagsAB)
				.size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithNullTags() {
		int resultListSize = BookDAOImplUtils.retrieveBookEntitiesWithSpecificTags(JPABookEntityTagNullList, null)
				.size();
		assertEquals(1, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithEmptyBookList() {
		int resultListSize = BookDAOImplUtils.retrieveBookEntitiesWithSpecificTags(JPABookEntityEmptyList, tagsAB)
				.size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithTagsOnlyUsingExistingTags() {
		int resultListSize = BookDAOImplUtils.retrieveBookEntitiesWithSpecificTagsOnly(JPABookEntityTagAList, tagsA)
				.size();
		assertEquals(1, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithTagsOnlyUsingNonExistingTags() {
		int resultListSize = BookDAOImplUtils.retrieveBookEntitiesWithSpecificTagsOnly(JPABookEntityTagAList, tagsC)
				.size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithTagsOnlyUsingNullTags() {
		int resultListSize = BookDAOImplUtils.retrieveBookEntitiesWithSpecificTagsOnly(JPABookEntityTagNullList, null)
				.size();
		assertEquals(1, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithTagsOnlyUsingEmptyBookList() {
		int resultListSize = BookDAOImplUtils.retrieveBookEntitiesWithSpecificTagsOnly(JPABookEntityTagNullList, tagsAB)
				.size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testBuildSpecificBookQueryWithNullEntity() {
		String updatedQuery = BookDAOImplUtils.buildSpecificBookQuery(BASIC_BOOK_SPECIFIC_QUERY, null);
		assertEquals(BASIC_BOOK_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildSpecificBookQueryNoSpecialCriteria() {
		String updatedQuery = BookDAOImplUtils.buildSpecificBookQuery(BASIC_BOOK_SPECIFIC_QUERY, JPABookEntity4);
		assertEquals(BASIC_BOOK_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildSpecificBookQueryMajorCriteria() {
		String updatedQuery = BookDAOImplUtils.buildSpecificBookQuery(BASIC_BOOK_SPECIFIC_QUERY, JPABookEntity1);
		assertEquals(UPDATED_BOOK_QUERY_1, updatedQuery);
	}

	@Test
	public void testBuildSpecificBookQueryMinorCriteria() {
		String updatedQuery = BookDAOImplUtils.buildSpecificBookQuery(BASIC_BOOK_SPECIFIC_QUERY, JPABookEntity2);
		assertEquals(UPDATED_BOOK_QUERY_2, updatedQuery);
	}
}
