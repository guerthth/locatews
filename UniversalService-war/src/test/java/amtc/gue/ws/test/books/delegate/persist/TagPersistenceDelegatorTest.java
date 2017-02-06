package amtc.gue.ws.test.books.delegate.persist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.util.BookServiceErrorConstants;
import amtc.gue.ws.test.books.delegate.BookServiceDelegatorTest;

/**
 * Testclass for the TagPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagPersistenceDelegatorTest extends
		BookServiceDelegatorTest {

	private static TagDAO tagDAOImplTagRetrieval;
	private static TagDAO tagDAOImplGeneralFail;

	@BeforeClass
	public static void inititalSetup() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {
		oneTimeInitialSetup();
		setUpTagPersistenceDAOMocks();
	}

	@AfterClass
	public static void tearDown() {
		EasyMock.verify(tagDAOImplTagRetrieval);
		EasyMock.verify(tagDAOImplGeneralFail);
	}

	@Override
	public void testDelegateUsingNullInput() {
		tagPersistenceDelegator.initialize(null);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateUsingUnrecognizedInputType() {
		tagPersistenceDelegator.initialize(unrecognizedDelegatorInput);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateAddUsingCorrectInput() {
		// no add method so far. ADD should not be recognized
		tagPersistenceDelegator.initialize(addTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagDAOImplTagRetrieval);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateAddUsingIncorrectDAOSetup() {
		// no add method so far. ADD should not be recognized
		tagPersistenceDelegator.initialize(addTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateAddUsingInvalidInput() {
		// no add method so far. ADD should not be recognized
		tagPersistenceDelegator.initialize(null);
		tagPersistenceDelegator.setTagDAO(tagDAOImplTagRetrieval);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingCorrectIdInput() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagDAOImplTagRetrieval);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingNonExistingObjects() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagDAOImplTagRetrieval);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingNullObjects() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(nullDeleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagDAOImplTagRetrieval);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingIncorrectDAOSetup() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteDeletionFail() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteRetrievalFail() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateDeleteUsingInvalidInput() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(null);
		tagPersistenceDelegator.setTagDAO(tagDAOImplTagRetrieval);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateReadUsingCorrectInput() {
		tagPersistenceDelegator.initialize(readTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagDAOImplTagRetrieval);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.RETRIEVE_TAGS_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(
				BookServiceErrorConstants.RETRIEVE_TAGS_SUCCESS_MSG));
	}

	@Override
	public void testDelegateReadUsingIncorrectDAOSetup() {
		tagPersistenceDelegator.initialize(readTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(BookServiceErrorConstants.RETRIEVE_TAGS_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(
				BookServiceErrorConstants.RETRIEVE_TAGS_FAILURE_MSG));
	}

	@Override
	public void testDelegateReadUsingInvalidInput() {
		// there are no invalid inputs
	}

	/**
	 * Set up the DAO Implementation Mocks
	 * 
	 * @throws EntityRetrievalException
	 */
	private static void setUpTagPersistenceDAOMocks()
			throws EntityRetrievalException {
		// tagDAO mocks for retrieval of all tags
		tagDAOImplTagRetrieval = EasyMock.createNiceMock(TagDAO.class);
		EasyMock.expect(tagDAOImplTagRetrieval.findAllEntities())
				.andReturn(retrievedTagEntityList);
		EasyMock.replay(tagDAOImplTagRetrieval);

		tagDAOImplGeneralFail = EasyMock.createNiceMock(TagDAO.class);
		EasyMock.expect(tagDAOImplGeneralFail.findAllEntities()).andThrow(
				new EntityRetrievalException());
		EasyMock.replay(tagDAOImplGeneralFail);
	}
}
