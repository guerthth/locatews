package amtc.gue.ws.test.books.util;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.util.BookPersistenceDelegatorUtils;
import amtc.gue.ws.books.util.BooksErrorConstants;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;
import amtc.gue.ws.test.books.BookTest;

/**
 * test class for the BookPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookPersistenceDelegatorUtilTest extends BookTest {
	private static String EXPECTED_NO_FAILURES_BOOK_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_FAILURE_AND_SUCCESS_BOOK_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_NO_SUCCESSES_BOOK_PERSISTENCE_MESSAGE_RESULT;
	private static String EXPECTED_BOOK_RETRIEVAL_MESSAGE_SIMPLE_RESULT;
	private static String EXPECTED_BOOK_RETRIEVAL_MESSAGE_EMPTY_RESULT;
	private static String EXPECTED_BOOK_REMOVAL_MESSAGE_SIMPLE_RESULT;
	private static String EXPECTED_BOOK_REMOVAL_MESSAGE_EMPTY_RESULT;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicBookEnvironment();
		setUpExpectedBookPersistStatusMessages();
	}

	@Test
	public void testBuildPersistBookStatusMessageOnlySuccesses() {
		String statusMessage = BookPersistenceDelegatorUtils.buildPersistBookSuccessStatusMessage(JPABookEntityList,
				null);
		assertEquals(EXPECTED_NO_FAILURES_BOOK_PERSISTENCE_MESSAGE_RESULT, statusMessage);
	}

	@Test
	public void testBuildPersistBookStatusMessageSuccessesAndFailures() {
		String statusMessage = BookPersistenceDelegatorUtils.buildPersistBookSuccessStatusMessage(JPABookEntityList,
				JPABookEntityList);
		assertEquals(EXPECTED_FAILURE_AND_SUCCESS_BOOK_PERSISTENCE_MESSAGE_RESULT, statusMessage);
	}

	@Test
	public void testBuildPersistBookStatusMessageOnlyFailures() {
		String statusMessage = BookPersistenceDelegatorUtils.buildPersistBookSuccessStatusMessage(null,
				JPABookEntityList);
		assertEquals(EXPECTED_NO_SUCCESSES_BOOK_PERSISTENCE_MESSAGE_RESULT, statusMessage);
	}

	@Test
	public void testBuildGetBooksByTagSuccessStatusMessageSimpleList() {
		String statusMessage = BookPersistenceDelegatorUtils.buildGetBooksByTagSuccessStatusMessage(tagsA,
				JPABookEntityList);
		assertEquals(EXPECTED_BOOK_RETRIEVAL_MESSAGE_SIMPLE_RESULT, statusMessage);
	}

	@Test
	public void testBuildGetBooksByTagSuccessStatusMessageEmptyBookList() {
		String statusMessage = BookPersistenceDelegatorUtils.buildGetBooksByTagSuccessStatusMessage(tagsA,
				JPABookEntityEmptyList);
		assertEquals(EXPECTED_BOOK_RETRIEVAL_MESSAGE_EMPTY_RESULT, statusMessage);
	}

	@Test
	public void testBuildRemoveBooksSuccessStatusMessageSimpleList() {
		String statusMessage = BookPersistenceDelegatorUtils.buildRemoveBooksSuccessStatusMessage(JPABookEntityList);
		assertEquals(EXPECTED_BOOK_REMOVAL_MESSAGE_SIMPLE_RESULT, statusMessage);
	}

	@Test
	public void testBuildRemoveBooksSuccessStatusMessageEmptyList() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildRemoveBooksSuccessStatusMessage(JPABookEntityEmptyList);
		assertEquals(EXPECTED_BOOK_REMOVAL_MESSAGE_EMPTY_RESULT, statusMessage);
	}

	@Test
	public void testAddTagsToBookEntityIfNotAlreadyExistingUsingExistingTagsOnly() {
		int initialTagSetSize = JPABookEntity1.getTags().size();
		int newTagSetSize = BookPersistenceDelegatorUtils
				.addTagsToBookEntityIfNotAlreadyExisting(JPABookEntity4, JPABookEntity4.getTags()).getTags().size();
		assertEquals(initialTagSetSize+1, newTagSetSize);
	}

	@Test
	public void testAddTagsToBookEntityIfNotAlreadyExistingUsingNullTags() {
		int initialTagSetSize = JPABookEntity1.getTags().size();
		int newTagSetSize = BookPersistenceDelegatorUtils.addTagsToBookEntityIfNotAlreadyExisting(JPABookEntity1, null)
				.getTags().size();
		assertEquals(initialTagSetSize, newTagSetSize);
	}

	@Test
	public void testAddTagsToBookEntityIfNotAlreadyExistingUsingNewTags() {
		int initialTagSetSize = JPABookEntity1.getTags().size();
		int newTagSetSize = BookPersistenceDelegatorUtils
				.addTagsToBookEntityIfNotAlreadyExisting(JPABookEntity1, JPATagEntitySet).getTags().size();
		assertEquals(initialTagSetSize + 1, newTagSetSize);
	}

	/**
	 * Setting up status messages for testing
	 */
	private static void setUpExpectedBookPersistStatusMessages() {
		// EXPECTED_NO_FAILURES_BOOK_PERSISTENCE_MESSAGE_RESULT
		StringBuilder sb = new StringBuilder();
		sb.append(BooksErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append(" '").append(BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(JPABookEntityList))
				.append("'. ").append(JPABookEntityList.size()).append(" books were successfully added.");
		EXPECTED_NO_FAILURES_BOOK_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_FAILURE_AND_SUCCESS_BOOK_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(BooksErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append(" '").append(BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(JPABookEntityList))
				.append("'.").append(" ").append(JPABookEntityList.size()).append(" books were successfully added.")
				.append(System.getProperty("line.separator")).append("'")
				.append(BooksErrorConstants.ADD_BOOK_FAILURE_MSG)
				.append(BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(JPABookEntityList))
				.append("'. ").append(JPABookEntityList.size()).append(" books were not added successfully.");
		EXPECTED_FAILURE_AND_SUCCESS_BOOK_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_NO_SUCCESSES_BOOK_PERSISTENCE_MESSAGE_RESULT
		sb.setLength(0);
		sb.append(BooksErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append(" '").append(BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(null)).append("'.")
				.append(" ").append("0").append(" books were successfully added.")
				.append(System.getProperty("line.separator")).append("'")
				.append(BooksErrorConstants.ADD_BOOK_FAILURE_MSG)
				.append(BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(JPABookEntityList))
				.append("'. ").append(JPABookEntityList.size()).append(" books were not added successfully.");

		EXPECTED_NO_SUCCESSES_BOOK_PERSISTENCE_MESSAGE_RESULT = sb.toString();

		// EXPECTED_BOOK_RETRIEVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(BooksErrorConstants.RETRIEVE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(tagsA.getTags().toString());
		sb.append("': '");
		sb.append(BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(JPABookEntityList));
		sb.append("'. ");
		sb.append(JPABookEntityList.size());
		sb.append(" Entities were found.");
		EXPECTED_BOOK_RETRIEVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		// EXPECTED_BOOK_RETRIEVAL_MESSAGE_EMPTY_RESULT
		sb.setLength(0);
		sb.append(BooksErrorConstants.RETRIEVE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(tagsA.getTags().toString());
		sb.append("': '");
		sb.append(BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(JPABookEntityEmptyList));
		sb.append("'. ");
		sb.append(JPABookEntityEmptyList.size());
		sb.append(" Entities were found.");
		EXPECTED_BOOK_RETRIEVAL_MESSAGE_EMPTY_RESULT = sb.toString();

		// EXPECTED_BOOK_REMOVAL_MESSAGE_SIMPLE_RESULT
		sb.setLength(0);
		sb.append(BooksErrorConstants.DELETE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(JPABookEntityList));
		sb.append("'. ");
		sb.append(JPABookEntityList.size());
		sb.append(" Entities were removed.");
		EXPECTED_BOOK_REMOVAL_MESSAGE_SIMPLE_RESULT = sb.toString();

		// EXPECTED_BOOK_REMOVAL_MESSAGE_EMPTY_RESULT
		sb.setLength(0);
		sb.append(BooksErrorConstants.DELETE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(BookServiceEntityMapper.mapBookEntityListToConsolidatedJSONString(JPABookEntityEmptyList));
		sb.append("'. ");
		sb.append(JPABookEntityEmptyList.size());
		sb.append(" Entities were removed.");
		EXPECTED_BOOK_REMOVAL_MESSAGE_EMPTY_RESULT = sb.toString();
	}
}
