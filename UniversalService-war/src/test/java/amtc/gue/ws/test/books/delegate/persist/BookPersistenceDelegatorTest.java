package amtc.gue.ws.test.books.delegate.persist;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.input.DelegatorInput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.dao.user.jpa.UserJPADAOImpl;
import amtc.gue.ws.base.persistence.dao.user.objectify.UserObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.jpa.GAEJPAUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.dao.book.jpa.BookJPADAOImpl;
import amtc.gue.ws.books.persistence.dao.book.objectify.BookObjectifyDAOImpl;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.dao.tag.jpa.TagJPADAOImpl;
import amtc.gue.ws.books.persistence.dao.tag.objectify.TagObjectifyDAOImpl;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.jpa.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.book.objectify.GAEObjectifyBookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.persistence.model.tag.jpa.GAEJPATagEntity;
import amtc.gue.ws.books.persistence.model.tag.objectify.GAEObjectifyTagEntity;
import amtc.gue.ws.books.util.BookServiceErrorConstants;
import amtc.gue.ws.test.base.delegate.persist.IBasePersistenceDelegatorTest;
import amtc.gue.ws.test.books.BookTest;

/**
 * Testclass for the BookPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookPersistenceDelegatorTest extends BookTest implements IBasePersistenceDelegatorTest {
	private static DelegatorInput addBookDelegatorInput;
	private static DelegatorInput addBookDelegatorInputForTagTesting;
	private static DelegatorInput deleteBookDelegatorInput;
	private static DelegatorInput deleteBookDelegatorInputWithId;
	private static DelegatorInput readBookDelegatorInput;
	private static DelegatorInput readBookByIdDelegatorInput;
	private static DelegatorInput updateBookDelegatorInput;

	private static BookPersistenceDelegator bookPersistenceDelegator;

	private static BookDAO<GAEBookEntity, GAEJPABookEntity, String> bookJPADAOImpl;
	private static BookDAO<GAEBookEntity, GAEJPABookEntity, String> bookJPADAOImplGeneralFail;
	private static BookDAO<GAEBookEntity, GAEJPABookEntity, String> bookJPADAOImplDeletionFail;
	private static BookDAO<GAEBookEntity, GAEJPABookEntity, String> bookJPADAOImplRetrievalFail;
	private static BookDAO<GAEBookEntity, GAEJPABookEntity, String> bookJPADAOImplNoFoundBooks;
	private static BookDAO<GAEBookEntity, GAEJPABookEntity, String> bookJPADAOImplNullBooks;
	private static BookDAO<GAEBookEntity, GAEJPABookEntity, String> bookJPADAOImplSpecificEntityFound;
	private static TagDAO<GAETagEntity, GAEJPATagEntity, String> tagJPADAOImpl;
	private static TagDAO<GAETagEntity, GAEJPATagEntity, String> tagJPADAOImplNoFoundTags;
	private static TagDAO<GAETagEntity, GAEJPATagEntity, String> tagJPADAOImplRetrievalFail;
	private static TagDAO<GAETagEntity, GAEJPATagEntity, String> tagJPADAOImplPersistenceFail;
	private static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAOImpl;

	private static BookDAO<GAEBookEntity, GAEObjectifyBookEntity, String> bookObjectifyDAOImpl;
	private static BookDAO<GAEBookEntity, GAEObjectifyBookEntity, String> bookObjectifyDAOImplGeneralFail;
	private static BookDAO<GAEBookEntity, GAEObjectifyBookEntity, String> bookObjectifyDAOImplDeletionFail;
	private static BookDAO<GAEBookEntity, GAEObjectifyBookEntity, String> bookObjectifyDAOImplRetrievalFail;
	private static BookDAO<GAEBookEntity, GAEObjectifyBookEntity, String> bookObjectifyDAOImplNoFoundBooks;
	private static BookDAO<GAEBookEntity, GAEObjectifyBookEntity, String> bookObjectifyDAOImplNullBooks;
	private static BookDAO<GAEBookEntity, GAEObjectifyBookEntity, String> bookObjectifyDAOImplSpecificEntityFound;
	private static TagDAO<GAETagEntity, GAEObjectifyTagEntity, String> tagObjectifyDAOImpl;
	private static TagDAO<GAETagEntity, GAEObjectifyTagEntity, String> tagObjectifyDAOImplNoFoundTags;
	private static TagDAO<GAETagEntity, GAEObjectifyTagEntity, String> tagObjectifyDAOImplRetrievalFail;
	private static TagDAO<GAETagEntity, GAEObjectifyTagEntity, String> tagObjectifyDAOImplPersistenceFail;
	private static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> userObjectifyDAOImpl;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		setUpBasicBookEnvironment();
		setUpDelegatorInputs();
		setUpBookPersistenceDelegatorInputs();
		setUpBookPersistenceDelegators();
		setUpDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(bookJPADAOImpl);
		EasyMock.verify(bookJPADAOImplGeneralFail);
		EasyMock.verify(bookJPADAOImplDeletionFail);
		EasyMock.verify(bookJPADAOImplRetrievalFail);
		EasyMock.verify(bookJPADAOImplNoFoundBooks);
		EasyMock.verify(bookJPADAOImplNullBooks);
		EasyMock.verify(bookJPADAOImplSpecificEntityFound);
		EasyMock.verify(tagJPADAOImpl);
		EasyMock.verify(tagJPADAOImplNoFoundTags);
		EasyMock.verify(tagJPADAOImplRetrievalFail);
		EasyMock.verify(tagJPADAOImplPersistenceFail);
		EasyMock.verify(userJPADAOImpl);
	}

	@Override
	@Test
	public void testDelegateUsingNullInput() {
		bookPersistenceDelegator.initialize(nullDelegatorInput);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateUsingUnrecognizedInputType() {
		bookPersistenceDelegator.initialize(unrecognizedDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setTagDAO(tagJPADAOImpl);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAAddUsingCorrectInput() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setTagDAO(tagJPADAOImpl);
		bookPersistenceDelegator.setBookEntityMapper(JPABookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(JPAUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingCorrectInput() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImpl);
		bookPersistenceDelegator.setTagDAO(tagObjectifyDAOImpl);
		bookPersistenceDelegator.setBookEntityMapper(objectifyBookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	/**
	 * Method testing adding book entity with users using JPA DAO
	 */
	@Test
	public void testDelegateJPAAddUsingCorrectInputWithUsers() {
		bookPersistenceDelegator.setCurrentUser(serviceUser);
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setTagDAO(tagJPADAOImpl);
		bookPersistenceDelegator.setUserDAO(userJPADAOImpl);
		bookPersistenceDelegator.setBookEntityMapper(JPABookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(JPAUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	/**
	 * Method testing adding book entity with users using Objectify DAO
	 */
	@Test
	public void testDelegateObjectifyAddUsingCorrectInputWithUsers() {
		bookPersistenceDelegator.setCurrentUser(serviceUser);
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImplSpecificEntityFound);
		bookPersistenceDelegator.setTagDAO(tagObjectifyDAOImpl);
		bookPersistenceDelegator.setUserDAO(userObjectifyDAOImpl);
		bookPersistenceDelegator.setBookEntityMapper(objectifyBookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	/**
	 * Method testing adding book entity when no tags are found using JPA DAO
	 */
	@Test
	public void testDelegateJPAAddUsingCorrectInputWithNoFoundTags() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setTagDAO(tagJPADAOImplNoFoundTags);
		bookPersistenceDelegator.setCurrentUser(null);
		bookPersistenceDelegator.setBookEntityMapper(JPABookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(JPAUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	/**
	 * Method testing adding book entity when no tags are found using Objectify
	 * DAO
	 */
	@Test
	public void testDelegateObjectifyAddUsingCorrectInputWithNoFoundTags() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImpl);
		bookPersistenceDelegator.setTagDAO(tagObjectifyDAOImplNoFoundTags);
		bookPersistenceDelegator.setCurrentUser(null);
		bookPersistenceDelegator.setBookEntityMapper(objectifyBookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(BookServiceErrorConstants.ADD_BOOK_SUCCESS_MSG));
		assertNotNull(delegatorOutput.getOutputObject());
	}

	/**
	 * Method testing adding book entity when tag retrieval fails using JPA DAO
	 */
	@Test
	public void testDelegateJPAAddUsingCorrectInputAndTagRetrievalFail() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setTagDAO(tagJPADAOImplRetrievalFail);
		bookPersistenceDelegator.setCurrentUser(null);
		bookPersistenceDelegator.setBookEntityMapper(JPABookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(JPAUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing adding book entity when tag retrieval fails using
	 * Objectify DAO
	 */
	@Test
	public void testDelegateObjectifyAddUsingCorrectInputAndTagRetrievalFail() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImpl);
		bookPersistenceDelegator.setTagDAO(tagObjectifyDAOImplRetrievalFail);
		bookPersistenceDelegator.setCurrentUser(null);
		bookPersistenceDelegator.setBookEntityMapper(objectifyBookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing adding book entity when tag persistence fails using JPA
	 * DAO
	 */
	@Test
	public void testDelegateJPAAddUsingCorrectInputAndTagPersistenceFail() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setTagDAO(tagJPADAOImplPersistenceFail);
		bookPersistenceDelegator.setCurrentUser(null);
		bookPersistenceDelegator.setBookEntityMapper(JPABookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(JPAUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing adding book entity when tag persistence fails using
	 * Objectify DAO
	 */
	@Test
	public void testDelegateObjectifyAddUsingCorrectInputAndTagPersistenceFail() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImpl);
		bookPersistenceDelegator.setTagDAO(tagObjectifyDAOImplPersistenceFail);
		bookPersistenceDelegator.setCurrentUser(null);
		bookPersistenceDelegator.setBookEntityMapper(objectifyBookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.ADD_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAAddUsingIncorrectDAOSetup() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImplGeneralFail);
		bookPersistenceDelegator.setTagDAO(tagJPADAOImpl);
		bookPersistenceDelegator.setBookEntityMapper(JPABookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(JPAUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingIncorrectDAOSetup() {
		bookPersistenceDelegator.initialize(addBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImplGeneralFail);
		bookPersistenceDelegator.setTagDAO(tagObjectifyDAOImpl);
		bookPersistenceDelegator.setBookEntityMapper(objectifyBookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.ADD_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAAddUsingInvalidInput() {
		bookPersistenceDelegator.initialize(invalidAddDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingInvalidInput() {
		bookPersistenceDelegator.initialize(invalidAddDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingCorrectIdInput() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInputWithId);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingCorrectIdInput() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInputWithId);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		bookPersistenceDelegator.setBookEntityMapper(objectifyBookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingNonExistingObjects() {
		// testing behavior when the objects that should be removed were not
		// found
		bookPersistenceDelegator.initialize(deleteBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImplNoFoundBooks);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNonExistingObjects() {
		// testing behavior when the objects that should be removed were not
		// found
		bookPersistenceDelegator.initialize(deleteBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImplNoFoundBooks);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingNullObjects() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImplNullBooks);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNullObjects() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImplNullBooks);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingIncorrectDAOSetup() {
		// testing behavior when an exception occurred on entity removal
		bookPersistenceDelegator.initialize(deleteBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImplGeneralFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingIncorrectDAOSetup() {
		// testing behavior when an exception occurred on entity removal
		bookPersistenceDelegator.initialize(deleteBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImplGeneralFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteDeletionFail() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInputWithId);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImplDeletionFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteDeletionFail() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInputWithId);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImplDeletionFail);
		bookPersistenceDelegator.setCurrentUser(null);
		bookPersistenceDelegator.setBookEntityMapper(objectifyBookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteRetrievalFail() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInputWithId);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImplRetrievalFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteRetrievalFail() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInputWithId);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImplRetrievalFail);
		bookPersistenceDelegator.setCurrentUser(null);
		bookPersistenceDelegator.setBookEntityMapper(objectifyBookEntityMapper);
		bookPersistenceDelegator.setUserEntityMapper(objectifyUserEntityMapper);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingInvalidInput() {
		bookPersistenceDelegator.initialize(invalidDeleteDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingInvalidInput() {
		bookPersistenceDelegator.initialize(invalidDeleteDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	/**
	 * Method testing deleting bookentity with existing user using JPA DAO
	 */
	@Test
	public void testDelegateJPADeleteWithExistingUsers() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setCurrentUser(serviceUser);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method testing deleting bookentity with existing user using Objectify DAO
	 */
	@Test
	public void testDelegateObjectifyDeleteWithExistingUsers() {
		bookPersistenceDelegator.initialize(deleteBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImpl);
		bookPersistenceDelegator.setCurrentUser(serviceUser);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.DELETE_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAReadUsingCorrectInput() {
		bookPersistenceDelegator.initialize(readBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.RETRIEVE_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingCorrectInput() {
		bookPersistenceDelegator.initialize(readBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.RETRIEVE_BOOK_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAReadUsingIncorrectDAOSetup() {
		bookPersistenceDelegator.initialize(readBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImplGeneralFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.RETRIEVE_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingIncorrectDAOSetup() {
		bookPersistenceDelegator.initialize(readBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImplGeneralFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.RETRIEVE_BOOK_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAReadUsingInvalidInput() {
		bookPersistenceDelegator.initialize(invalidReadDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingInvalidInput() {
		bookPersistenceDelegator.initialize(invalidReadDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAUpdateUsingCorrectInput() {
		// update for bookdelegator not implemented
		bookPersistenceDelegator.initialize(updateBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingCorrectInput() {
		// update for bookdelegator not implemented
		bookPersistenceDelegator.initialize(updateBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAUpdateUsingIncorrectDAOSetup() {
		// update for bookdelegator not implemented
		bookPersistenceDelegator.initialize(updateBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImplGeneralFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingIncorrectDAOSetup() {
		// update for bookdelegator not implemented
		bookPersistenceDelegator.initialize(updateBookDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImplGeneralFail);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateJPAUpdateUsingInvalidInput() {
		// update for bookdelegator not implemented
		bookPersistenceDelegator.initialize(invalidUpdateDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookJPADAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingInvalidInput() {
		// update for bookdelegator not implemented
		bookPersistenceDelegator.initialize(invalidUpdateDelegatorInput);
		bookPersistenceDelegator.setBookDAO(bookObjectifyDAOImpl);
		bookPersistenceDelegator.setCurrentUser(null);
		IDelegatorOutput delegatorOutput = bookPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method setting up BookPersistenceDelegator inputs
	 */
	private static void setUpBookPersistenceDelegatorInputs() {
		// DelegatorInput for Book entity adding
		addBookDelegatorInput = new DelegatorInput();
		addBookDelegatorInput.setInputObject(books);
		addBookDelegatorInput.setType(DelegatorTypeEnum.ADD);

		addBookDelegatorInputForTagTesting = new DelegatorInput();
		addBookDelegatorInputForTagTesting.setInputObject(booksWithTag);
		addBookDelegatorInputForTagTesting.setType(DelegatorTypeEnum.ADD);

		// DelegatorInput for Book entity deletion
		deleteBookDelegatorInput = new DelegatorInput();
		deleteBookDelegatorInput.setInputObject(books);
		deleteBookDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInput for Book entity deletion with ID Book
		deleteBookDelegatorInputWithId = new DelegatorInput();
		deleteBookDelegatorInputWithId.setInputObject(booksWithId);
		deleteBookDelegatorInputWithId.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInput for Book entity read
		readBookDelegatorInput = new DelegatorInput();
		readBookDelegatorInput.setInputObject(tagsA);
		readBookDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInput for Book entity read by Id
		readBookByIdDelegatorInput = new DelegatorInput();
		readBookByIdDelegatorInput.setInputObject(BOOKKEY);
		readBookByIdDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInput for user entity update
		updateBookDelegatorInput = new DelegatorInput();
		updateBookDelegatorInput.setInputObject(booksWithId);
		updateBookDelegatorInput.setType(DelegatorTypeEnum.UPDATE);
	}

	/**
	 * Method setting up BookPersistenceDelegators
	 */
	private static void setUpBookPersistenceDelegators() {
		bookPersistenceDelegator = new BookPersistenceDelegator();
	}

	/**
	 * Method setting up Mocks for testing purpose
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 */
	private static void setUpDAOMocks()
			throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		// positive scenario (all calls return values)
		bookJPADAOImpl = EasyMock.createNiceMock(BookJPADAOImpl.class);
		EasyMock.expect(bookJPADAOImpl.findEntityById(EasyMock.isA(String.class))).andReturn(JPABookEntity1);
		EasyMock.expect(bookJPADAOImpl.persistEntity(EasyMock.isA(GAEJPABookEntity.class))).andReturn(JPABookEntity1);
		EasyMock.expect(bookJPADAOImpl.removeEntity(EasyMock.isA(GAEJPABookEntity.class))).andReturn(JPABookEntity1);
		EasyMock.expect(bookJPADAOImpl.findSpecificBookEntityForUser(EasyMock.isA(GAEJPABookEntity.class)))
				.andReturn(JPABookEntityList).times(4);
		EasyMock.expect(bookJPADAOImpl.updateEntity(EasyMock.isA(GAEJPABookEntity.class))).andReturn(JPABookEntity1)
				.times(2);
		EasyMock.replay(bookJPADAOImpl);

		bookJPADAOImplSpecificEntityFound = EasyMock.createNiceMock(BookJPADAOImpl.class);
		EasyMock.replay(bookJPADAOImplSpecificEntityFound);

		tagJPADAOImpl = EasyMock.createNiceMock(TagJPADAOImpl.class);
		EasyMock.expect(tagJPADAOImpl.findEntityById(EasyMock.isA(String.class))).andReturn(JPATagEntity1);
		EasyMock.expect(tagJPADAOImpl.findSpecificEntity(EasyMock.isA(GAEJPATagEntity.class)))
				.andReturn(JPATagEntityList).times(6);
		EasyMock.replay(tagJPADAOImpl);

		userJPADAOImpl = EasyMock.createNiceMock(UserJPADAOImpl.class);
		EasyMock.expect(userJPADAOImpl.findEntityById(EasyMock.isA(String.class))).andReturn(JPAUserEntity1);
		EasyMock.replay(userJPADAOImpl);

		bookObjectifyDAOImpl = EasyMock.createNiceMock(BookObjectifyDAOImpl.class);
		EasyMock.expect(bookObjectifyDAOImpl.findEntityById(EasyMock.isA(String.class)))
				.andReturn(objectifyBookEntity1);
		EasyMock.expect(bookObjectifyDAOImpl.persistEntity(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andReturn(objectifyBookEntity1);
		EasyMock.expect(bookObjectifyDAOImpl.removeEntity(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andReturn(objectifyBookEntity1);
		EasyMock.expect(bookObjectifyDAOImpl.findSpecificBookEntityForUser(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andReturn(objectifyBookEntityList).times(4);
		EasyMock.expect(bookObjectifyDAOImpl.updateEntity(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andReturn(objectifyBookEntity1).times(2);
		EasyMock.replay(bookObjectifyDAOImpl);

		bookObjectifyDAOImplSpecificEntityFound = EasyMock.createNiceMock(BookObjectifyDAOImpl.class);
		EasyMock.expect(
				bookObjectifyDAOImplSpecificEntityFound.findSpecificEntity(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andReturn(objectifyBookEntityListWithID);
		EasyMock.replay(bookObjectifyDAOImplSpecificEntityFound);

		tagObjectifyDAOImpl = EasyMock.createNiceMock(TagObjectifyDAOImpl.class);
		EasyMock.expect(tagObjectifyDAOImpl.findEntityById(EasyMock.isA(String.class))).andReturn(objectifyTagEntity1);
		EasyMock.expect(tagObjectifyDAOImpl.findSpecificEntity(EasyMock.isA(GAEObjectifyTagEntity.class)))
				.andReturn(objectifyTagEntityList).times(6);
		EasyMock.replay(tagObjectifyDAOImpl);

		userObjectifyDAOImpl = EasyMock.createNiceMock(UserObjectifyDAOImpl.class);
		EasyMock.expect(userObjectifyDAOImpl.findEntityById(EasyMock.isA(String.class)))
				.andReturn(objectifyUserEntity1);
		EasyMock.replay(userObjectifyDAOImpl);

		// negative scenario (all calls throw exceptions)
		bookJPADAOImplGeneralFail = EasyMock.createNiceMock(BookJPADAOImpl.class);
		EasyMock.expect(bookJPADAOImplGeneralFail.persistEntity(EasyMock.isA(GAEJPABookEntity.class)))
				.andThrow(new EntityPersistenceException()).times(2);
		EasyMock.expect(bookJPADAOImplGeneralFail.getBookEntityByTag(EasyMock.isA(Tags.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(bookJPADAOImplGeneralFail);

		bookObjectifyDAOImplGeneralFail = EasyMock.createNiceMock(BookObjectifyDAOImpl.class);
		EasyMock.expect(bookObjectifyDAOImplGeneralFail.persistEntity(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andThrow(new EntityPersistenceException()).times(2);
		EasyMock.expect(bookObjectifyDAOImplGeneralFail.getBookEntityByTag(EasyMock.isA(Tags.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(bookObjectifyDAOImplGeneralFail);

		// negative scenario mock for BookDAO (removeEntity() call fails)
		bookJPADAOImplDeletionFail = EasyMock.createNiceMock(BookJPADAOImpl.class);
		EasyMock.expect(bookJPADAOImplDeletionFail.findEntityById(EasyMock.isA(String.class)))
				.andReturn(JPABookEntity1);
		EasyMock.expect(bookJPADAOImplDeletionFail.removeEntity(EasyMock.isA(GAEJPABookEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.replay(bookJPADAOImplDeletionFail);

		bookObjectifyDAOImplDeletionFail = EasyMock.createNiceMock(BookObjectifyDAOImpl.class);
		EasyMock.expect(bookObjectifyDAOImplDeletionFail.findEntityById(EasyMock.isA(String.class)))
				.andReturn(objectifyBookEntity1);
		EasyMock.expect(bookObjectifyDAOImplDeletionFail.removeEntity(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.replay(bookObjectifyDAOImplDeletionFail);

		// negative scenario. Entity retrieval fails
		bookJPADAOImplRetrievalFail = EasyMock.createNiceMock(BookJPADAOImpl.class);
		EasyMock.expect(bookJPADAOImplRetrievalFail.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(bookJPADAOImplRetrievalFail);

		bookObjectifyDAOImplRetrievalFail = EasyMock.createNiceMock(BookObjectifyDAOImpl.class);
		EasyMock.expect(bookObjectifyDAOImplRetrievalFail.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(bookObjectifyDAOImplRetrievalFail);

		// positive scenario. No Books found
		bookJPADAOImplNoFoundBooks = EasyMock.createNiceMock(BookJPADAOImpl.class);
		EasyMock.expect(bookJPADAOImplNoFoundBooks.findSpecificEntity(EasyMock.isA(GAEJPABookEntity.class)))
				.andReturn(JPABookEntityEmptyList);
		EasyMock.replay(bookJPADAOImplNoFoundBooks);

		bookObjectifyDAOImplNoFoundBooks = EasyMock.createNiceMock(BookObjectifyDAOImpl.class);
		EasyMock.expect(bookObjectifyDAOImplNoFoundBooks.findSpecificEntity(EasyMock.isA(GAEObjectifyBookEntity.class)))
				.andReturn(objectifyBookEntityEmptyList);
		EasyMock.replay(bookObjectifyDAOImplNoFoundBooks);

		// Positive scenario (null returned after Book retrieval)
		bookJPADAOImplNullBooks = EasyMock.createNiceMock(BookJPADAOImpl.class);
		EasyMock.replay(bookJPADAOImplNullBooks);

		bookObjectifyDAOImplNullBooks = EasyMock.createNiceMock(BookObjectifyDAOImpl.class);
		EasyMock.replay(bookObjectifyDAOImplNullBooks);

		// Positive scenario. No tags found
		tagJPADAOImplNoFoundTags = EasyMock.createNiceMock(TagJPADAOImpl.class);
		EasyMock.expect(tagJPADAOImplNoFoundTags.findSpecificEntity(EasyMock.isA(GAEJPATagEntity.class)))
				.andReturn(JPATagEntityEmptyList).times(2);
		EasyMock.replay(tagJPADAOImplNoFoundTags);

		tagObjectifyDAOImplNoFoundTags = EasyMock.createNiceMock(TagObjectifyDAOImpl.class);
		EasyMock.expect(tagObjectifyDAOImplNoFoundTags.findSpecificEntity(EasyMock.isA(GAEObjectifyTagEntity.class)))
				.andReturn(objectifyTagEntityEmptyList).times(2);
		EasyMock.replay(tagObjectifyDAOImplNoFoundTags);

		// negative scenario. role retrieval fail
		tagJPADAOImplRetrievalFail = EasyMock.createNiceMock(TagJPADAOImpl.class);
		EasyMock.expect(tagJPADAOImplRetrievalFail.findSpecificEntity(EasyMock.isA(GAEJPATagEntity.class)))
				.andThrow(new EntityRetrievalException()).times(2);
		EasyMock.replay(tagJPADAOImplRetrievalFail);

		tagObjectifyDAOImplRetrievalFail = EasyMock.createNiceMock(TagObjectifyDAOImpl.class);
		EasyMock.expect(tagObjectifyDAOImplRetrievalFail.findSpecificEntity(EasyMock.isA(GAEObjectifyTagEntity.class)))
				.andThrow(new EntityRetrievalException()).times(2);
		EasyMock.replay(tagObjectifyDAOImplRetrievalFail);

		// negative scenario. tag persistence fail
		tagJPADAOImplPersistenceFail = EasyMock.createNiceMock(TagJPADAOImpl.class);
		EasyMock.expect(tagJPADAOImplPersistenceFail.findSpecificEntity(EasyMock.isA(GAEJPATagEntity.class)))
				.andReturn(JPATagEntityEmptyList).times(2);
		EasyMock.expect(tagJPADAOImplPersistenceFail.persistEntity(EasyMock.isA(GAEJPATagEntity.class)))
				.andThrow(new EntityPersistenceException()).times(2);
		EasyMock.replay(tagJPADAOImplPersistenceFail);

		tagObjectifyDAOImplPersistenceFail = EasyMock.createNiceMock(TagObjectifyDAOImpl.class);
		EasyMock.expect(
				tagObjectifyDAOImplPersistenceFail.findSpecificEntity(EasyMock.isA(GAEObjectifyTagEntity.class)))
				.andReturn(objectifyTagEntityEmptyList).times(2);
		EasyMock.expect(tagObjectifyDAOImplPersistenceFail.persistEntity(EasyMock.isA(GAEObjectifyTagEntity.class)))
				.andThrow(new EntityPersistenceException()).times(2);
		EasyMock.replay(tagObjectifyDAOImplPersistenceFail);
	}
}