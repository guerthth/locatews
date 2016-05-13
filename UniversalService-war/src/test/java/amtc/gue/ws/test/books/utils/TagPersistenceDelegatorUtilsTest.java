package amtc.gue.ws.test.books.utils;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.utils.TagPersistenceDelegatorUtils;

/**
 * test class for the TagPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagPersistenceDelegatorUtilsTest extends UtilTest {

	@Test
	public void testBuildRetrieveTagsStatusMessage() {
		String statusMessage = TagPersistenceDelegatorUtils
				.buildRetrieveTagsSuccessStatusMessage(tagEntitySimpleList);
		assertEquals(EXPECTED_TAG_RETRIEVAL_MESSAGE, statusMessage);
	}
}
