package amtc.gue.ws.test.books.util;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.util.TagPersistenceDelegatorUtils;

/**
 * test class for the TagPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagPersistenceDelegatorUtilTest extends BookServiceUtilTest {

	@Test
	public void testBuildRetrieveTagsStatusMessage() {
		String statusMessage = TagPersistenceDelegatorUtils
				.buildRetrieveTagsSuccessStatusMessage(tagEntitySimpleList);
		assertEquals(EXPECTED_TAG_RETRIEVAL_MESSAGE, statusMessage);
	}
}
