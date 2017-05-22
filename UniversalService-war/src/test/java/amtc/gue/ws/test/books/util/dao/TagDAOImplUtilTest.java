package amtc.gue.ws.test.books.util.dao;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.util.dao.TagDAOImplUtils;
import amtc.gue.ws.test.books.BookTest;

/**
 * Testclass for the TagDAOImplUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagDAOImplUtilTest extends BookTest {

	private static final String BASIC_TAG_SPECIFIC_QUERY = "select t from TagEntity t";
	private static final String BASIC_TAG_SPECIFIC_QUERY_1 = "select t from TagEntity t where t.tagname = :id";

	@Test
	public void testBuildSpecificTagQueryWithNullEntity() {
		String updatedQuery = TagDAOImplUtils.buildSpecificTagQuery(BASIC_TAG_SPECIFIC_QUERY, null);
		assertEquals(BASIC_TAG_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildSpecificTagQueryWithNullValues() {
		String updatedQuery = TagDAOImplUtils.buildSpecificTagQuery(BASIC_TAG_SPECIFIC_QUERY, JPATagEntity4);
		assertEquals(BASIC_TAG_SPECIFIC_QUERY, updatedQuery);
	}

	@Test
	public void testBuildSpecificTagQueryWithSimpleKey() {
		String updatedQuery = TagDAOImplUtils.buildSpecificTagQuery(BASIC_TAG_SPECIFIC_QUERY, JPATagEntity1);
		assertEquals(BASIC_TAG_SPECIFIC_QUERY_1, updatedQuery);
	}
}
