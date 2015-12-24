package amtc.gue.ws.test.books.utils.dao;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.persistence.model.TagEntity;
import amtc.gue.ws.books.utils.dao.TagDAOImplUtils;

/**
 * Testclass for the TagDAOImplUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagDAOImplUtilsTest {

	private static final String BASIC_BOOK_SPECIFIC_QUERY = "select t from TagEntity t";
	private static final String UPDATED_TAG_QUERY_1 = "select t from TagEntity t"
			+ " where t.id = :id and t.tagname = :tagname";
	private static final String UPDATED_TAG_QUERY_2 = "select t from TagEntity t"
			+ " where t.tagname = :tagname";

	private static final String TAG_NAME1 = "tagName1";
	private static final String TAG_NAME2 = "tagName2";

	private static TagEntity tagEntity1;
	private static TagEntity tagEntity2;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setupTagEntities();
	}

	@Test
	public void testBuildSpecificTagQuery1() {
		String updatedQuery = TagDAOImplUtils.buildSpecificTagQuery(
				BASIC_BOOK_SPECIFIC_QUERY, tagEntity1);
		assertEquals(UPDATED_TAG_QUERY_1, updatedQuery);
	}
	
	@Test
	public void testBuildSpecificTagQuery2() {
		String updatedQuery = TagDAOImplUtils.buildSpecificTagQuery(
				BASIC_BOOK_SPECIFIC_QUERY, tagEntity2);
		assertEquals(UPDATED_TAG_QUERY_2, updatedQuery);
	}

	/**
	 * Set up some TagEntities
	 */
	private static void setupTagEntities() {
		tagEntity1 = new TagEntity();
		tagEntity1.setId(1L);
		tagEntity1.setTagName(TAG_NAME1);

		tagEntity2 = new TagEntity();
		tagEntity2.setTagName(TAG_NAME2);
	}
}
