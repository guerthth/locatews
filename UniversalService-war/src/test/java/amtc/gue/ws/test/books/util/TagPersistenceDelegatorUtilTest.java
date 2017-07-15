package amtc.gue.ws.test.books.util;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.util.BooksErrorConstants;
import amtc.gue.ws.books.util.TagPersistenceDelegatorUtils;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;
import amtc.gue.ws.test.books.BookTest;

/**
 * test class for the TagPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagPersistenceDelegatorUtilTest extends BookTest {
	private static String EXPECTED_TAG_RETRIEVAL_MESSAGE;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicBookEnvironment();
		setUpExpectedTagPersistStatusMessages();
	}

	@Test
	public void testBuildRetrieveTagsStatusMessage() {
		String statusMessage = TagPersistenceDelegatorUtils.buildRetrieveTagsSuccessStatusMessage(JPATagEntityList);
		assertEquals(EXPECTED_TAG_RETRIEVAL_MESSAGE, statusMessage);
	}

	/**
	 * Setting up status messages for testing
	 */
	private static void setUpExpectedTagPersistStatusMessages() {
		// EXPECTED_TAG_RETRIEVAL_MESSAGE
		StringBuilder sb = new StringBuilder();
		sb.append(BooksErrorConstants.RETRIEVE_TAGS_SUCCESS_MSG);
		sb.append(" '");
		sb.append(BookServiceEntityMapper.mapTagEntityListToConsolidatedJSONString(JPATagEntityList));
		sb.append("'. ");
		sb.append(JPATagEntityList.size());
		sb.append(" Entities were found");
		EXPECTED_TAG_RETRIEVAL_MESSAGE = sb.toString();
	}
}
