package amtc.gue.ws.test.books.utils;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.utils.BookDAOImplUtils;

/**
 * This class tests the functionality of the BookDAOImplUtils class
 * 
 * @author Thomas
 *
 */
public class BookDAOImplUtilsTest {

	private static final String BOOK_ENTITIY1_TAGS = "tag1,tag2,tag3,tag4";
	private static final String BOOK_ENTITY1_TITLE = "bookEntity1";
	private static final String BOOK_ENTITIY2_TAGS = "tag1,tag2,tag3,tag5";
	private static final String BOOK_ENTITY2_TITLE = "bookEntity2";
	private static final String BOOK_ENTITIY3_TAGS = "tag1,tag2";
	private static final String BOOK_ENTITY3_TITLE = "bookEntity3";
	private static final String BOOK_ENTITIY4_TAGS = "tag1,tag5";
	private static final String BOOK_ENTITY4_TITLE = "bookEntity4";
	private static final String BOOK_ENTITIY5_TAGS = "tag1";
	private static final String BOOK_ENTITY5_TITLE = "bookEntity5";
	private static List<BookEntity> bookList;
	private static List<BookEntity> emptyBookList;
	private static List<BookEntity> nullTagBookList;
	private static Tags tags;
	private static List<String> searchTags;
	private static final String searchTag1 = "tag1";
	private static final String searchTag2 = "tag2";
	private static Tags nonExistingTags;
	private static List<String> nonExistingSearchTags;
	private static final String searchTag3 = "tagX";

	@BeforeClass
	public static void oneTimeIntitialSetup() {
		setupSearchTags();
		setupBookLists();
	}

	@Test
	public void testCorrectnessOfSetup() {
		assertEquals(5, bookList.size());
		assertEquals(2, tags.getTags().size());
	}

	@Test
	public void testRetrieveBookEntitiesWithExistingTags() {
		int resultListSize = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTags(bookList, tags).size();
		assertEquals(3, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithNonExistingTags() {
		int resultListSize = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTags(bookList, nonExistingTags)
				.size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithNullTags() {
		// BookDAOImplUtils.retrieveBookEntitiesWithSpecificTags(nullTagBookList,
		// tags);
		int resultListSize = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTags(nullTagBookList, tags)
				.size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testRetrieveBookEntitiesWithEmptyBookList() {
		int resultListSize = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTags(emptyBookList, tags)
				.size();
		assertEquals(0, resultListSize);
	}

	@Test
	public void testCopyBookList() {
		List<BookEntity> resultList = BookDAOImplUtils
				.retrieveBookEntitiesWithSpecificTags(bookList, tags);
		assertEquals(5, bookList.size());
		assertEquals(3, resultList.size());
	}

	/**
	 * Method setting up some booklists for testing purposes
	 */
	private static void setupBookLists() {
		emptyBookList = new ArrayList<BookEntity>();
		bookList = new ArrayList<BookEntity>();
		nullTagBookList = new ArrayList<BookEntity>();

		BookEntity firstBookEntity = setupBookEntityWithTags(
				BOOK_ENTITY1_TITLE, BOOK_ENTITIY1_TAGS);
		bookList.add(firstBookEntity);
		BookEntity secondBookEntity = setupBookEntityWithTags(
				BOOK_ENTITY2_TITLE, BOOK_ENTITIY2_TAGS);
		bookList.add(secondBookEntity);
		BookEntity thirdBookEntity = setupBookEntityWithTags(
				BOOK_ENTITY3_TITLE, BOOK_ENTITIY3_TAGS);
		bookList.add(thirdBookEntity);
		BookEntity fourthBookEntity = setupBookEntityWithTags(
				BOOK_ENTITY4_TITLE, BOOK_ENTITIY4_TAGS);
		bookList.add(fourthBookEntity);
		BookEntity fifthBookEntity = setupBookEntityWithTags(
				BOOK_ENTITY5_TITLE, BOOK_ENTITIY5_TAGS);
		bookList.add(fifthBookEntity);

		BookEntity bookEntityWithNullTag = setupBookEntityWithTags(
				BOOK_ENTITY1_TITLE, null);
		nullTagBookList.add(bookEntityWithNullTag);
	}

	/**
	 * Method setting up some searchtags for testing purposes
	 */
	private static void setupSearchTags() {
		searchTags = new ArrayList<String>();
		searchTags.add(searchTag1);
		searchTags.add(searchTag2);
		tags = new Tags();
		tags.setTags(searchTags);

		nonExistingSearchTags = new ArrayList<String>();
		nonExistingSearchTags.add(searchTag3);
		nonExistingTags = new Tags();
		nonExistingTags.setTags(nonExistingSearchTags);
	}

	/**
	 * Method building up BookEntities including specific tags
	 * 
	 * @param title
	 *            the title used for the testentity
	 * @param tags
	 *            the tags used for the testentity
	 * @return the created BookEntity object
	 */
	private static BookEntity setupBookEntityWithTags(String title, String tags) {
		BookEntity bookEntity = new BookEntity();
		bookEntity.setTitle(title);
		bookEntity.setAuthor("test");
		bookEntity.setDescription("testDescription");
		bookEntity.setId(1L);
		bookEntity.setISBN("testISBN");
		bookEntity.setPrice("testPrice");
		bookEntity.setTags(tags);
		return bookEntity;
	}

}
