package amtc.gue.ws.test.books.delegate.persist;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.util.BookServiceErrorConstants;
import amtc.gue.ws.test.books.delegate.BookServiceDelegatorTest;

/**
 * Testclass for the BookPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookPersistenceDelegatorTest extends BookServiceDelegatorTest {
	private static TagDAO tagDAONoTagsImpl;
	private static TagDAO tagDAOImpl;
	private static TagDAO tagDAOImplRetrievalFail;
	private static TagDAO tagDAOImplPersistenceFail;
	private static BookDAO bookDAOImplNullBooks;
	private static BookDAO bookDAOImplNoFoundBooks;
	private static BookDAO bookDAOImpl;
	private static BookDAO bookDAOImplFail;
	private static BookDAO bookDAOImplDeletionFail;
	private static BookDAO bookDAOImplRetrievalFail;
	private static UserDAO userDAOImpl;

	@BeforeClass
	public static void intitialSetup() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {
		oneTimeInitialSetup();
		setUpBookPersistenceDAOMocks();
	}

	@AfterClass
	public static void tearDown() {
		EasyMock.verify(tagDAONoTagsImpl);
		EasyMock.verify(tagDAOImpl);
		EasyMock.verify(tagDAOImplRetrievalFail);
		EasyMock.verify(tagDAOImplPersistenceFail);
		EasyMock.verify(bookDAOImplNullBooks);
		EasyMock.verify(bookDAOImplNoFoundBooks);
		EasyMock.verify(bookDAOImpl);
		EasyMock.verify(bookDAOImplFail);
		EasyMock.verify(bookDAOImplDeletionFail);
		EasyMock.verify(bookDAOImplRetrievalFail);
		EasyMock.verify(userDAOImpl);
	}

	@Override
	public void testDelegateUsingNullInput() {
		bookPersistenceDelegator.initialize(null);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateUsingUnrecognizedInputType() {
		bookPersistenceDelegator.initialize(unrecognizedDelegatorInput);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateAddUsingCorrectInput() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImpl);
		bookPersistenceDelegator.setTagDAO(tagDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(
				BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	// additional bookpersistencedelegator specific tests
	@Test
	public void testDelegateAddUsingCorrectInputWithUsers() {
		bookPersistenceDelegator.setCurrentUser(currentUser);
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImpl);
		bookPersistenceDelegator.setTagDAO(tagDAOImpl);
		bookPersistenceDelegator.setUserDAO(userDAOImpl);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(
				BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Test
	public void testDelegateAddUsingCorrectInputWithNoFoundTags() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImpl);
		bookPersistenceDelegator.setTagDAO(tagDAONoTagsImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(
				BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Test
	public void testDelegateAddUsingCorrectInputAndTagRetrievalFail() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImpl);
		bookPersistenceDelegator.setTagDAO(tagDAOImplRetrievalFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateAddUsingCorrectInputAndTagPersistenceFail() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImpl);
		bookPersistenceDelegator.setTagDAO(tagDAOImplPersistenceFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateAddUsingIncorrectDAOSetup() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImplFail);
		bookPersistenceDelegator.setTagDAO(tagDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateAddUsingInvalidInput() {
		bookPersistenceDelegator.initialize(invalidAddDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateDeleteUsingCorrectIdInput() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInputWithId);
		bookPersistenceDelegator.setBookDAO(bookDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateDeleteUsingNonExistingObjects() {
		// testing behavior when the objects that should be removed were not
		// found
		bookPersistenceDelegator.initialize(deleteBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImplNoFoundBooks);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingNullObjects() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImplNullBooks);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingIncorrectDAOSetup() {
		// testing behavior when an exception occurred on entity removal
		bookPersistenceDelegator.initialize(deleteBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImplFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingInvalidInput() {
		bookPersistenceDelegator.initialize(invalidDeleteDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteDeletionFail() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInputWithId);
		bookPersistenceDelegator.setBookDAO(bookDAOImplDeletionFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteRetrievalFail() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInputWithId);
		bookPersistenceDelegator.setBookDAO(bookDAOImplRetrievalFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateDeleteWithExistingUsers() {
		currentUserEntity.setBooks(null, false);
		bookPersistenceDelegator.initialize(deleteBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImpl);
		bookPersistenceDelegator.setCurrentUser(currentUser);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateReadUsingCorrectInput() {
		bookPersistenceDelegator.initialize(readBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.RETRIEVE_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateReadUsingIncorrectDAOSetup() {
		bookPersistenceDelegator.initialize(readBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImplFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.RETRIEVE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());

	}

	@Override
	public void testDelegateReadUsingInvalidInput() {
		bookPersistenceDelegator.initialize(invalidReadDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
	}

	/**
	 * Method setting up the relevant testing mocks
	 * 
	 * @throws EntityPersistenceException
	 *             Exception occurs when persisting entity fails
	 * @throws EntityRetrievalException
	 *             Exception occurs when retrieving entity fails
	 * @throws EntityRemovalException
	 *             Exception occurs when removing entity fails
	 */
	private static void setUpBookPersistenceDAOMocks()
			throws EntityPersistenceException, EntityRetrievalException,
			EntityRemovalException {
		// Positive Scenario (all calls return values)
		bookDAOImpl = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImpl.persistEntity(EasyMock.isA(GAEJPABookEntity.class)))
				.andReturn(retrievedBookEntity);
		EasyMock.expect(
				bookDAOImpl.findSpecificBookEntityForUser(EasyMock
						.isA(GAEJPABookEntity.class)))
				.andReturn(retrievedBookEntityList).times(2);
		EasyMock.expect(bookDAOImpl.getBookEntityByTag(searchTags)).andReturn(
				retrievedBookEntityList);
		EasyMock.expect(bookDAOImpl.findEntityById(EasyMock.isA(String.class)))
				.andReturn(retrievedBookEntity);
		EasyMock.expect(
				bookDAOImpl.updateEntity(EasyMock.isA(GAEJPABookEntity.class)))
				.andReturn(removedBookEntity).times(2);
		EasyMock.expect(
				bookDAOImpl.removeEntity(EasyMock.isA(GAEJPABookEntity.class)))
				.andReturn(removedBookEntity);
		EasyMock.replay(bookDAOImpl);

		// Positive Scenario. No books found
		bookDAOImplNoFoundBooks = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImplNoFoundBooks.findSpecificEntity(EasyMock
						.isA(GAEJPABookEntity.class))).andReturn(
				emptyBookEntityList);
		EasyMock.replay(bookDAOImplNoFoundBooks);

		// Positive Scenario (null returned after retrieval)
		bookDAOImplNullBooks = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(EasyMock.expect(
				bookDAOImplNullBooks.findSpecificEntity(EasyMock
						.isA(GAEJPABookEntity.class))).andReturn(null));
		EasyMock.replay(bookDAOImplNullBooks);

		// negative scenario (all calls throw exceptions)
		bookDAOImplFail = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(bookDAOImplFail.getBookEntityByTag(searchTags))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(
				bookDAOImplFail.persistEntity(EasyMock
						.isA(GAEJPABookEntity.class))).andThrow(
				new EntityPersistenceException());
		EasyMock.replay(bookDAOImplFail);

		// negative scenario mock for BookDAO (removeEntity() call fails)
		bookDAOImplDeletionFail = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImplDeletionFail.findEntityById(EasyMock
						.isA(String.class))).andReturn(retrievedBookEntity);
		EasyMock.expect(
				bookDAOImplDeletionFail.removeEntity(EasyMock
						.isA(GAEJPABookEntity.class))).andThrow(
				new EntityRemovalException());
		EasyMock.replay(bookDAOImplDeletionFail);

		// negative scenario mock for BookDAO (getEntitiyById() call fails)
		bookDAOImplRetrievalFail = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImplRetrievalFail.findEntityById(EasyMock
						.isA(String.class))).andThrow(
				new EntityRetrievalException());
		EasyMock.replay(bookDAOImplRetrievalFail);

		// Positive scenario mock for TagDAO (tags are found)
		tagDAOImpl = EasyMock.createNiceMock(TagDAO.class);
		EasyMock.expect(
				tagDAOImpl.findSpecificEntity(EasyMock
						.isA(GAEJPATagEntity.class)))
				.andReturn(retrievedTagEntityList).times(3);
		EasyMock.expect(tagDAOImpl.findEntityById(EasyMock.isA(String.class)))
				.andReturn(retrievedTagEntity);
		EasyMock.replay(tagDAOImpl);

		// Positive scenario mock for TagDAO (no tags are found)
		tagDAONoTagsImpl = EasyMock.createNiceMock(TagDAO.class);
		EasyMock.expect(
				tagDAONoTagsImpl.findSpecificEntity(EasyMock
						.isA(GAEJPATagEntity.class))).andReturn(
				emptyTagEntityList);
		EasyMock.replay(tagDAONoTagsImpl);

		// Negative scenario mock for TagDAO (findSpecificEntity() call fails)
		tagDAOImplRetrievalFail = EasyMock.createNiceMock(TagDAO.class);
		EasyMock.expect(
				tagDAOImplRetrievalFail.findSpecificEntity(EasyMock
						.isA(GAEJPATagEntity.class))).andThrow(
				new EntityRetrievalException());
		EasyMock.replay(tagDAOImplRetrievalFail);

		// Negative scenario mock for TagDAO (persistEntity() call fails)
		tagDAOImplPersistenceFail = EasyMock.createNiceMock(TagDAO.class);
		EasyMock.expect(
				tagDAOImplPersistenceFail.persistEntity(EasyMock
						.isA(GAEJPATagEntity.class))).andThrow(
				new EntityPersistenceException());
		EasyMock.expect(
				tagDAOImplPersistenceFail.findSpecificEntity(EasyMock
						.isA(GAEJPATagEntity.class))).andReturn(
				emptyTagEntityList);
		EasyMock.replay(tagDAOImplPersistenceFail);

		// Positive scenario mock for UserDAO
		userDAOImpl = EasyMock.createNiceMock(UserDAO.class);
		EasyMock.replay(userDAOImpl);
	}
}
