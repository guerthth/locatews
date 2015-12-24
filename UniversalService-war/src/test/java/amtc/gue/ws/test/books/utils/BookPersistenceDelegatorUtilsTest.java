package amtc.gue.ws.test.books.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.persistence.model.TagEntity;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.utils.BookPersistenceDelegatorUtils;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.ErrorConstants;

/**
 * test class for the BookPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
public class BookPersistenceDelegatorUtilsTest {

	private static final String TAG2 = "tag2";
	private static final String TAG1 = "tag1";
	private static final String PRICE = "price";
	private static final String ISBN = "isbn";
	private static final String DESCRIPTION = "description";
	private static final String TITLE = "title";

	private static List<BookEntity> successfullyAddedBookEntities;
	private static List<BookEntity> successfullyAddedBookEntitiesEmpty;
	private static List<BookEntity> unsuccessfullyAddedBookEntities;
	private static List<BookEntity> unsuccessfullyAddedBookEntitiesEmpty;
	private static List<BookEntity> removedBookEntities;
	private static List<BookEntity> removedBookEntitiesEmpty;

	private static Tags searchTags;
	private static Tags searchTagsEmpty;

	private static List<TagEntity> tagEntityList;

	private static String exptextedPersisStatusOnlySuccesses;
	private static String exptextedPersistStatusSuccessesAndFails;
	private static String expectedTagRetrievalStatusSuccess;
	private static String expectedTagRetrievalStatusNothingFound;
	private static String expectedRemoveStatusSuccess;
	private static String expectedRemoveStatusSuccessNothingRemoved;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpSearchTags();
		setUpTagEntityLists();
		setUpBookEntityLists();
		setUpExpectedPersistStatusMessages();
		setUpExpectedTagRetrievalStatusMessages();
		setUpExpectedRemoveStatusMessage();
	}

	/**
	 * Method setting up TagEntityLists for testing
	 */
	public static void setUpTagEntityLists() {
		tagEntityList = EntityMapper.mapTagsToTagEntityList(searchTags);
	}

	/**
	 * Method setting up BookEntityLists for testing
	 */
	private static void setUpBookEntityLists() {
		successfullyAddedBookEntities = new ArrayList<BookEntity>();
		successfullyAddedBookEntitiesEmpty = new ArrayList<BookEntity>();
		removedBookEntities = new ArrayList<BookEntity>();
		removedBookEntitiesEmpty = new ArrayList<BookEntity>();

		for (int i = 0; i < 3; i++) {
			BookEntity bookEntity = new BookEntity();
			bookEntity.setTags(tagEntityList);
			bookEntity.setDescription(DESCRIPTION + i);
			bookEntity.setId(1L);
			bookEntity.setISBN(ISBN + i);
			bookEntity.setPrice(PRICE + i);
			bookEntity.setTitle(TITLE + i);
			successfullyAddedBookEntities.add(bookEntity);
			removedBookEntities.add(bookEntity);
		}

		unsuccessfullyAddedBookEntities = new ArrayList<BookEntity>();
		unsuccessfullyAddedBookEntitiesEmpty = new ArrayList<BookEntity>();
		for (int i = 3; i < 5; i++) {
			BookEntity bookEntity = new BookEntity();
			bookEntity.setTags(tagEntityList);
			bookEntity.setDescription(DESCRIPTION + i);
			bookEntity.setId(1L);
			bookEntity.setISBN(ISBN + i);
			bookEntity.setPrice(PRICE + i);
			bookEntity.setTitle(TITLE + i);
			unsuccessfullyAddedBookEntities.add(bookEntity);
		}
	}

	/**
	 * Method setting up Tags for testings
	 */
	private static void setUpSearchTags() {
		searchTags = new Tags();
		List<String> tags = new ArrayList<String>();
		tags.add(TAG1);
		tags.add(TAG2);
		searchTags.setTags(tags);

		searchTagsEmpty = new Tags();
		List<String> emptyTags = new ArrayList<String>();
		searchTagsEmpty.setTags(emptyTags);
	}

	/**
	 * Method setting up the expected Persist status message outputs
	 */
	private static void setUpExpectedPersistStatusMessages() {
		// status message for only successfully added bookentities
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append("'");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(successfullyAddedBookEntities));
		sb.append("'.");
		sb.append(" ").append(successfullyAddedBookEntities.size());
		sb.append(" books were successfully added.");
		exptextedPersisStatusOnlySuccesses = sb.toString();

		// status message for successfully and also unsuccessfully added
		// bookentities
		sb = new StringBuilder();
		sb.append(ErrorConstants.ADD_BOOK_SUCCESS_MSG);
		sb.append("'");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(successfullyAddedBookEntities));
		sb.append("'.");
		sb.append(" ").append(successfullyAddedBookEntities.size());
		sb.append(" books were successfully added.");
		sb.append(System.getProperty("line.separator"));
		sb.append("'");
		sb.append(ErrorConstants.ADD_BOOK_FAILURE_MSG);
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(unsuccessfullyAddedBookEntities));
		sb.append("'.");
		sb.append(" ").append(unsuccessfullyAddedBookEntities.size());
		sb.append(" books were not added successfully.");
		exptextedPersistStatusSuccessesAndFails = sb.toString();
	}

	/**
	 * Method setting up the expected Tag Retrieval status message outputs
	 */
	private static void setUpExpectedTagRetrievalStatusMessages() {
		// status message for successful tag retrieval
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.RETRIEVE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(searchTags.getTags().toString());
		sb.append("': '");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(successfullyAddedBookEntities));
		sb.append("'. ");
		sb.append(successfullyAddedBookEntities.size());
		sb.append(" Entities were found.");
		expectedTagRetrievalStatusSuccess = sb.toString();

		// status message when no tags were found
		sb = new StringBuilder();
		sb.append(ErrorConstants.RETRIEVE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(searchTagsEmpty.getTags().toString());
		sb.append("': '");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(successfullyAddedBookEntitiesEmpty));
		sb.append("'. ");
		sb.append(successfullyAddedBookEntitiesEmpty.size());
		sb.append(" Entities were found.");
		expectedTagRetrievalStatusNothingFound = sb.toString();
	}

	/**
	 * Method setting up the expected Remove status message outputs
	 */
	public static void setUpExpectedRemoveStatusMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append(ErrorConstants.DELETE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(removedBookEntities));
		sb.append("'. ");
		sb.append(removedBookEntities.size());
		sb.append(" Entities were removed.");
		expectedRemoveStatusSuccess = sb.toString();

		sb = new StringBuilder();
		sb.append(ErrorConstants.DELETE_BOOK_SUCCESS_MSG);
		sb.append(" '");
		sb.append(EntityMapper
				.mapBookEntityListToConsolidatedJSONString(removedBookEntitiesEmpty));
		sb.append("'. ");
		sb.append(removedBookEntitiesEmpty.size());
		sb.append(" Entities were removed.");
		expectedRemoveStatusSuccessNothingRemoved = sb.toString();
	}

	@Test
	public void testBuildPersistBookStatusMessage1() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildPersistBookSuccessStatusMessage(
						successfullyAddedBookEntities,
						unsuccessfullyAddedBookEntitiesEmpty);
		assertEquals(exptextedPersisStatusOnlySuccesses, statusMessage);
	}

	@Test
	public void testBuildPersistBookStatusMessage2() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildPersistBookSuccessStatusMessage(
						successfullyAddedBookEntities, null);
		assertEquals(exptextedPersisStatusOnlySuccesses, statusMessage);
	}

	@Test
	public void testBuildPersistBookStatusMessage3() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildPersistBookSuccessStatusMessage(
						successfullyAddedBookEntities,
						unsuccessfullyAddedBookEntities);
		assertEquals(exptextedPersistStatusSuccessesAndFails, statusMessage);
	}

	@Test
	public void testBuildGetBooksByTagSuccessStatusMessage1() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildGetBooksByTagSuccessStatusMessage(searchTags,
						successfullyAddedBookEntities);
		assertEquals(expectedTagRetrievalStatusSuccess, statusMessage);
	}

	@Test
	public void testBuildGetBooksByTagSuccessStatusMessage2() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildGetBooksByTagSuccessStatusMessage(searchTagsEmpty,
						successfullyAddedBookEntitiesEmpty);
		assertEquals(expectedTagRetrievalStatusNothingFound, statusMessage);
	}

	@Test
	public void testBuildRemoveBooksSuccessStatusMessage1() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildRemoveBooksSuccessStatusMessage(removedBookEntities);
		assertEquals(expectedRemoveStatusSuccess, statusMessage);
	}

	@Test
	public void testBuildRemoveBooksSuccessStatusMessage2() {
		String statusMessage = BookPersistenceDelegatorUtils
				.buildRemoveBooksSuccessStatusMessage(removedBookEntitiesEmpty);
		assertEquals(expectedRemoveStatusSuccessNothingRemoved, statusMessage);
	}
}
