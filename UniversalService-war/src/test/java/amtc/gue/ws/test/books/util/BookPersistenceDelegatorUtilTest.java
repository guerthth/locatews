package amtc.gue.ws.test.books.util;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.util.BookPersistenceDelegatorUtils;

/**
 * test class for the BookPersistenceDelegatorUtils class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookPersistenceDelegatorUtilTest extends BookServiceUtilTest {

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
		assertEquals(EXPECTED_TAG_RETRIEVAL_MESSAGE_SIMPLE_RESULT,
				statusMessage);
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

	@Test
	public void testCopyBookEntity() {
		GAEJPABookEntity bookEntityCopy = BookPersistenceDelegatorUtils
				.copyBookEntity(bookEntity1);
		assertNotNull(bookEntityCopy);
		assertEquals(bookEntity1.getKey(), bookEntityCopy.getKey());
		assertEquals(bookEntity1.getAuthor(), bookEntityCopy.getAuthor());
		assertEquals(bookEntity1.getDescription(),
				bookEntityCopy.getDescription());
		assertEquals(bookEntity1.getISBN(), bookEntityCopy.getISBN());
		assertEquals(bookEntity1.getPrice(), bookEntityCopy.getPrice());
		assertEquals(0, bookEntityCopy.getTags().size());
		assertEquals(bookEntity1.getTitle(), bookEntityCopy.getTitle());
		assertEquals(0, bookEntityCopy.getUsers().size());
	}

	@Test
	public void testAddTagsToBookEntityIfNotAlreadyExistingUsingExistingTagsOnly() {
		int initialTagSetSize = bookEntity1.getTags().size();
		int newTagSetSize = BookPersistenceDelegatorUtils
				.addTagsToBookEntityIfNotAlreadyExisting(bookEntity1,
						bookEntity1.getTags()).getTags().size();
		assertEquals(initialTagSetSize, newTagSetSize);
	}

	@Test
	public void testAddTagsToBookEntityIfNotAlreadyExistingUsingNullTags() {
		int initialTagSetSize = bookEntity1.getTags().size();
		int newTagSetSize = BookPersistenceDelegatorUtils
				.addTagsToBookEntityIfNotAlreadyExisting(bookEntity1, null)
				.getTags().size();
		assertEquals(initialTagSetSize, newTagSetSize);
	}

	@Test
	public void testAddTagsToBookEntityIfNotAlreadyExistingUsingNewTags() {
		int initialTagSetSize = bookEntity1.getTags().size();
		int newTagSetSize = BookPersistenceDelegatorUtils
				.addTagsToBookEntityIfNotAlreadyExisting(bookEntity1,
						tagEntitySet).getTags().size();
		assertEquals(initialTagSetSize + 1, newTagSetSize);
	}
}
