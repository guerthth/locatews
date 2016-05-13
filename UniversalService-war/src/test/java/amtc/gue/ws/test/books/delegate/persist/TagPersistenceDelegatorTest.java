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
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.utils.ErrorConstants;
import amtc.gue.ws.test.books.delegate.DelegatorTest;

/**
 * Testclass for the TagPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagPersistenceDelegatorTest extends DelegatorTest {

	private static DAOs validTagDAOImplInput;
	private static DAOs generalFailTagDAOImplInput;

	private static TagDAO tagDAOImplTagRetrieval;
	private static TagDAO tagDAOImplGeneralFail;

	@BeforeClass
	public static void inititalSetup() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {
		oneTimeInitialSetup();
		setUpTagPersistenceDAOMocks();
		setUpTagPersistenceDAOInputObjects();
	}

	@AfterClass
	public static void tearDown() {
		EasyMock.verify(tagDAOImplTagRetrieval);
		EasyMock.verify(tagDAOImplGeneralFail);
	}

	@Test
	public void testDelegateReadUsingSimpleInput() {
		tagPersistenceDelegator.initialize(readDelegatorInput,
				validTagDAOImplInput);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_TAGS_SUCCESS_CODE,
				delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(
				ErrorConstants.RETRIEVE_TAGS_SUCCESS_MSG));
	}

	@Test
	public void testDelegateReadUsingIncorrectDAOSetup() {
		tagPersistenceDelegator.initialize(readDelegatorInput,
				generalFailTagDAOImplInput);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_TAGS_FAILURE_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.RETRIEVE_TAGS_FAILURE_MSG,
				delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateReadUsingUnrecognizedInput() {
		tagPersistenceDelegator.initialize(unrecognizedDelegatorInput,
				validTagDAOImplInput);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE,
				delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG,
				delegatorOutput.getStatusMessage());
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
		EasyMock.expect(tagDAOImplTagRetrieval.findAllEntities()).andReturn(
				retrievedTagEntityList);
		EasyMock.replay(tagDAOImplTagRetrieval);

		tagDAOImplGeneralFail = EasyMock.createNiceMock(TagDAO.class);
		EasyMock.expect(tagDAOImplGeneralFail.findAllEntities()).andThrow(
				new EntityRetrievalException());
		EasyMock.replay(tagDAOImplGeneralFail);
	}

	/**
	 * Set up the DAOs input objects
	 */
	private static void setUpTagPersistenceDAOInputObjects() {
		validTagDAOImplInput = new DAOs();
		validTagDAOImplInput.setTagDAO(tagDAOImplTagRetrieval);

		generalFailTagDAOImplInput = new DAOs();
		generalFailTagDAOImplInput.setTagDAO(tagDAOImplGeneralFail);
	}
}
