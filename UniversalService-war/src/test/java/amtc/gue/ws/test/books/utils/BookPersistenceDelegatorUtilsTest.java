package amtc.gue.ws.test.books.utils;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.utils.BookPersistenceDelegatorUtils;

/**
 * test class for the BookPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookPersistenceDelegatorUtilsTest extends UtilTest {
	
	@Test
	public void testBuildPersistBookStatusMessageOnlySuccesses() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildPersistBookSuccessStatusMessage(bookEntitySimpleList,
						null);
		assertEquals(EXPECTED_PERSIST_STATUS_ONLY_SUCCESSES, statusMessage);
	}

	@Test
	public void testBuildPersistBookStatusMessageSuccessesAndFailures() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildPersistBookSuccessStatusMessage(bookEntitySimpleList,
						bookEntitySimpleList);
		assertEquals(EXPECTED_PERSIST_STATUS_SUCCESS_AND_FAILS, statusMessage);
	}

	@Test
	public void testBuildPersistBookStatusMessageOnlyFailures() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildPersistBookSuccessStatusMessage(null,
						bookEntitySimpleList);
		assertEquals(EXPECTED_PERSIST_STATUS_ONLY_FAILS, statusMessage);
	}

	@Test
	public void testBuildGetBooksByTagSuccessStatusMessageSimpleList() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildGetBooksByTagSuccessStatusMessage(existingSearchTags,
						bookEntitySimpleList);
		assertEquals(EXPECTED_TAG_RETRIEVAL_MESSAGE_SIMPLE_RESULT, statusMessage);
	}

	@Test
	public void testBuildGetBooksByTagSuccessStatusMessageEmptyBookList() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildGetBooksByTagSuccessStatusMessage(existingSearchTags,
						bookEntityNullList);
		assertEquals(EXPECTED_TAG_RETRIEVAL_MESSAGE_EMPTY_RESULT, statusMessage);
	}

	@Test
	public void testBuildRemoveBooksSuccessStatusMessageSimpleList() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildRemoveBooksSuccessStatusMessage(bookEntitySimpleList);
		assertEquals(EXPECTED_BOOK_REMOVAL_MESSAGE_SIMPLE_RESULT, statusMessage);
	}

	@Test
	public void testBuildRemoveBooksSuccessStatusMessageEmptyList() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildRemoveBooksSuccessStatusMessage(bookEntityNullList);
		assertEquals(EXPECTED_BOOK_REMOVAL_MESSAGE_EMPTY_RESULT, statusMessage);
	}
}
