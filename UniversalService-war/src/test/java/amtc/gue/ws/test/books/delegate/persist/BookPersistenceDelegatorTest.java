package amtc.gue.ws.test.books.delegate.persist;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRemovalException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.dao.DAOs;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.utils.ErrorConstants;
import amtc.gue.ws.test.books.delegate.DelegatorTest;

/**
 * Testclass for the BookPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookPersistenceDelegatorTest extends DelegatorTest {

	private static DAOs validBookDAOImplInput;
	private static DAOs generalFailBookDAOImplInput;
	private static DAOs nonExistingDeletionsBookDAOImplInput;
	private static DAOs retrieveDeletionFailDAOImplInput;
	private static DAOs idRemovalDAOImplInput;
	private static DAOs validBookTagDAOImplInput;
	private static DAOs generalFailBookTagDAOImplInput;

	private static BookDAO bookDAOImpl;
	private static BookDAO bookDAOImplIdRemoval;
	private static BookDAO bookDAOImplGeneralFail;
	private static BookDAO bookDAOImplNonExistingDeletionsFail;
	private static BookDAO bookDAOImplRetrieveDeletionFail;

	private static TagDAO tagDAOImpl;

	@BeforeClass
	public static void intitialSetup() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {
		oneTimeInitialSetup();
		setUpBookPersistenceDAOMocks();
		setUpBookPersistenceDAOInputObjects();
	}

	@AfterClass
	public static void tearDown() {
		EasyMock.verify(tagDAOImpl);
		EasyMock.verify(bookDAOImpl);
		EasyMock.verify(bookDAOImplIdRemoval);
		EasyMock.verify(bookDAOImplGeneralFail);
		EasyMock.verify(bookDAOImplNonExistingDeletionsFail);
		EasyMock.verify(bookDAOImplRetrieveDeletionFail);
	}

	@Test
	public void testDelegateUsingUnrecognizedInput() {
		// testing delegate method with an unrecognized input type
		bookPersistenceDelegator.initialize(unrecognizedDelegatorInput,
				validBookDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateAddUsingCorrectInput() {
		bookPersistenceDelegator.initialize(addDelegatorInput,
				validBookTagDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(
				ErrorConstants.ADD_BOOK_SUCCESS_MSG));
		assertTrue(delegatorOutput.getOutputObject() != null);
	}

	@Test
	public void testDelegateAddUsingIncorrectDAOSetup() {
		bookPersistenceDelegator.initialize(addDelegatorInput,
				generalFailBookTagDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateUsingInvalidInput() {
		bookPersistenceDelegator.initialize(invalidAddDelegatorInput,
				validBookDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateReadUsingCorrectInput() {
		bookPersistenceDelegator.initialize(readDelegatorInput,
				validBookDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateReadUsingIncorrectDAOSetup() {
		bookPersistenceDelegator.initialize(readDelegatorInput,
				generalFailBookDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateReadUsingInvalidInput() {
		bookPersistenceDelegator.initialize(invalidReadDelegatorInput,
				validBookDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateDeleteUsingCorrectInput() {
		// testing behavior when deletion works
		bookPersistenceDelegator.initialize(deleteDelegatorInput,
				validBookDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	@Test
	public void testDelegateDeleteUsingInvalidInput() {
		// testing behavior when input type object is not of type Books
		bookPersistenceDelegator.initialize(invalidDeleteDelegatorInput,
				validBookDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateDeleteDeletionObjectsNotExisting() {
		// testing behavior when the objects that should be removed were not
		// found
		bookPersistenceDelegator.initialize(deleteDelegatorInput,
				nonExistingDeletionsBookDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_BOOK_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateDeleteObjectDeletionFails() {
		// testing behavior when an exception already occurred already on entity
		// retrieval
		bookPersistenceDelegator.initialize(deleteDelegatorInput,
				retrieveDeletionFailDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_BOOK_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateDeleteGeneralFail() {
		// testing behavior when an exception occurred on entity removal
		bookPersistenceDelegator.initialize(deleteDelegatorInput,
				generalFailBookDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_BOOK_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.DELETE_BOOK_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateDeleteUsingCorrectIdInput() {
		bookPersistenceDelegator.initialize(deleteDelegatorInputWithId,
				idRemovalDAOImplInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.DELETE_BOOK_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
	}

	/**
	 * Set up the DAO Implementation Mocks
	 * 
	 * @throws EntityPersistenceException
	 * @throws EntityRetrievalException
	 * @throws EntityRemovalException
	 */
	private static void setUpBookPersistenceDAOMocks()
			throws EntityPersistenceException, EntityRetrievalException,
			EntityRemovalException {
		// Positive Scenario mock
		bookDAOImpl = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImpl.persistEntity(EasyMock.isA(GAEJPABookEntity.class)))
				.andReturn(retrievedBookEntity);
		EasyMock.expect(bookDAOImpl.getBookEntityByTag(searchTags)).andReturn(
				retrievedBookEntityList);
		EasyMock.expect(
				bookDAOImpl.findSpecificEntity(EasyMock
						.isA(GAEJPABookEntity.class))).andReturn(
				retrievedBookEntityList);
		EasyMock.expect(
				bookDAOImpl.removeEntity(EasyMock.isA(GAEJPABookEntity.class)))
				.andReturn(removedBookEntity);
		EasyMock.replay(bookDAOImpl);

		// Positive Scenario mock for book removal with Id
		bookDAOImplIdRemoval = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImplIdRemoval.findSpecificEntity(EasyMock
						.isA(GAEJPABookEntity.class))).andReturn(
				retrievedBookEntityList);
		EasyMock.expect(
				bookDAOImplIdRemoval.removeEntity(EasyMock
						.isA(GAEJPABookEntity.class))).andReturn(
				removedBookEntity);
		EasyMock.replay(bookDAOImplIdRemoval);

		// Negative Scenario mock (general scenario)
		bookDAOImplGeneralFail = EasyMock.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImplGeneralFail.persistEntity(EasyMock
						.isA(GAEJPABookEntity.class))).andThrow(
				new EntityPersistenceException());
		EasyMock.expect(bookDAOImplGeneralFail.getBookEntityByTag(searchTags))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(
				bookDAOImplGeneralFail.findSpecificEntity(EasyMock
						.isA(GAEJPABookEntity.class))).andReturn(
				retrievedBookEntityList);
		EasyMock.expect(
				bookDAOImplGeneralFail.removeEntity(EasyMock
						.isA(GAEJPABookEntity.class))).andThrow(
				new EntityRemovalException());
		EasyMock.replay(bookDAOImplGeneralFail);

		// Negative scenario mock simulating that entities to be removed are not
		// existing
		bookDAOImplNonExistingDeletionsFail = EasyMock
				.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImplNonExistingDeletionsFail.findSpecificEntity(EasyMock
						.isA(GAEJPABookEntity.class))).andReturn(
				emptyBookEntityList);
		EasyMock.replay(bookDAOImplNonExistingDeletionsFail);

		// Negative scenario mock simulation failure on entity removal
		bookDAOImplRetrieveDeletionFail = EasyMock
				.createNiceMock(BookDAO.class);
		EasyMock.expect(
				bookDAOImplRetrieveDeletionFail.findSpecificEntity(EasyMock
						.isA(GAEJPABookEntity.class))).andThrow(
				new EntityRetrievalException());
		EasyMock.replay(bookDAOImplRetrieveDeletionFail);

		// Positive scenario mock for TagDAO
		tagDAOImpl = EasyMock.createNiceMock(TagDAO.class);
		EasyMock.expect(
				tagDAOImpl.findSpecificEntity(EasyMock
						.isA(GAEJPATagEntity.class)))
				.andReturn(retrievedTagEntityList).times(2);
		EasyMock.replay(tagDAOImpl);
	}

	/**
	 * Set up the DAOs input objects
	 */
	private static void setUpBookPersistenceDAOInputObjects() {
		validBookDAOImplInput = new DAOs();
		validBookDAOImplInput.setBookDAO(bookDAOImpl);

		validBookTagDAOImplInput = new DAOs();
		validBookTagDAOImplInput.setBookDAO(bookDAOImpl);
		validBookTagDAOImplInput.setTagDAO(tagDAOImpl);

		generalFailBookDAOImplInput = new DAOs();
		generalFailBookDAOImplInput.setBookDAO(bookDAOImplGeneralFail);

		generalFailBookTagDAOImplInput = new DAOs();
		generalFailBookTagDAOImplInput.setBookDAO(bookDAOImplGeneralFail);
		generalFailBookTagDAOImplInput.setTagDAO(tagDAOImpl);

		nonExistingDeletionsBookDAOImplInput = new DAOs();
		nonExistingDeletionsBookDAOImplInput
				.setBookDAO(bookDAOImplNonExistingDeletionsFail);

		retrieveDeletionFailDAOImplInput = new DAOs();
		retrieveDeletionFailDAOImplInput
				.setBookDAO(bookDAOImplRetrieveDeletionFail);

		idRemovalDAOImplInput = new DAOs();
		idRemovalDAOImplInput.setBookDAO(bookDAOImplIdRemoval);
	}
}
