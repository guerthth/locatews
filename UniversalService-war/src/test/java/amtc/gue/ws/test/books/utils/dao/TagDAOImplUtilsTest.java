package amtc.gue.ws.test.books.utils.dao;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.utils.dao.TagDAOImplUtils;
import amtc.gue.ws.test.books.utils.UtilTest;

/**
 * Testclass for the TagDAOImplUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagDAOImplUtilsTest extends UtilTest {

	private static final String BASIC_TAG_SPECIFIC_QUERY = "select t from TagEntity t";
	private static final String UPDATED_TAG_QUERY_1 = "select t from TagEntity t"
			+ " where t.tagId = :id and t.tagname = :tagname";
	private static final String UPDATED_TAG_QUERY_2 = "select t from TagEntity t"
			+ " where t.tagname = :tagname";

	@Test
	public void testBuildSpecificTagQueryWithNullEntity() {
		String updatedQuery = TagDAOImplUtils.buildSpecificTagQuery(
				BASIC_TAG_SPECIFIC_QUERY, null);
		assertEquals(BASIC_TAG_SPECIFIC_QUERY, updatedQuery);
	}
	
	@Test
	public void testBuildSpecificTagQueryWithNullValues() {
		String updatedQuery = TagDAOImplUtils.buildSpecificTagQuery(
				BASIC_TAG_SPECIFIC_QUERY, tagEntity5);
		assertEquals(BASIC_TAG_SPECIFIC_QUERY, updatedQuery);
	}
	
	@Test
	public void testBuildSpecificTagQueryMajorCriteria() {
		String updatedQuery = TagDAOImplUtils.buildSpecificTagQuery(
				BASIC_TAG_SPECIFIC_QUERY, tagEntity4);
		assertEquals(UPDATED_TAG_QUERY_1, updatedQuery);
	}
	
	@Test
	public void testBuildSpecificTagQueryMinorCriteria() {
		String updatedQuery = TagDAOImplUtils.buildSpecificTagQuery(
				BASIC_TAG_SPECIFIC_QUERY, tagEntity2);
		assertEquals(UPDATED_TAG_QUERY_2, updatedQuery);
	}
}
