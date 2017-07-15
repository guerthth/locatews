package amtc.gue.ws.test.books.delegate.persist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.books.delegate.persist.TagPersistenceDelegator;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.dao.tag.jpa.TagJPADAOImpl;
import amtc.gue.ws.books.persistence.dao.tag.objectify.TagObjectifyDAOImpl;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.persistence.model.tag.jpa.GAEJPATagEntity;
import amtc.gue.ws.books.persistence.model.tag.objectify.GAEObjectifyTagEntity;
import amtc.gue.ws.books.util.BooksErrorConstants;
import amtc.gue.ws.test.base.delegate.persist.IBasePersistenceDelegatorTest;
import amtc.gue.ws.test.books.BookTest;

/**
 * Testclass for the TagPersistenceDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagPersistenceDelegatorTest extends BookTest implements IBasePersistenceDelegatorTest {
	private static TagPersistenceDelegator tagPersistenceDelegator;

	private static DelegatorInput addTagDelegatorInput;
	private static DelegatorInput deleteTagDelegatorInput;
	private static DelegatorInput nullDeleteTagDelegatorInput;
	private static DelegatorInput readTagDelegatorInput;
	private static DelegatorInput nullReadTagDelegatorInput;
	private static DelegatorInput updateTagDelegatorInput;
	private static DelegatorInput nullUpdateTagDelegatorInput;

	private static TagDAO<GAETagEntity, GAEJPATagEntity, String> tagJPADAOImpl;
	private static TagDAO<GAETagEntity, GAEJPATagEntity, String> tagJPADAOImplGeneralFail;

	private static TagDAO<GAETagEntity, GAEObjectifyTagEntity, String> tagObjectifyDAOImpl;
	private static TagDAO<GAETagEntity, GAEObjectifyTagEntity, String> tagObjectifyDAOImplGeneralFail;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		setUpBasicBookEnvironment();
		setUpDelegatorInputs();
		setUpTagPersistenceDelegatorInputs();
		setUpTagPersistenceDelegators();
		setUpDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(tagJPADAOImpl);
		EasyMock.verify(tagJPADAOImplGeneralFail);
		EasyMock.verify(tagObjectifyDAOImpl);
		EasyMock.verify(tagObjectifyDAOImplGeneralFail);
	}

	@Override
	@Test
	public void testDelegateUsingNullInput() {
		tagPersistenceDelegator.initialize(null);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateUsingUnrecognizedInputType() {
		tagPersistenceDelegator.initialize(unrecognizedDelegatorInput);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAAddUsingCorrectInput() {
		// no add method so far. ADD should not be recognized
		tagPersistenceDelegator.initialize(addTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingCorrectInput() {
		// no add method so far. ADD should not be recognized
		tagPersistenceDelegator.initialize(addTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAAddUsingIncorrectDAOSetup() {
		// no add method so far. ADD should not be recognized
		tagPersistenceDelegator.initialize(addTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingIncorrectDAOSetup() {
		// no add method so far. ADD should not be recognized
		tagPersistenceDelegator.initialize(addTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAAddUsingInvalidInput() {
		// no add method so far. ADD should not be recognized
		tagPersistenceDelegator.initialize(null);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyAddUsingInvalidInput() {
		// no add method so far. ADD should not be recognized
		tagPersistenceDelegator.initialize(null);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingCorrectIdInput() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingCorrectIdInput() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingNonExistingObjects() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNonExistingObjects() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingNullObjects() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(nullDeleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingNullObjects() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(nullDeleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingIncorrectDAOSetup() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingIncorrectDAOSetup() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteDeletionFail() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteDeletionFail() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteRetrievalFail() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteRetrievalFail() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(deleteTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPADeleteUsingInvalidInput() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(null);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyDeleteUsingInvalidInput() {
		// no delete method so far. DELETE should not be recognized
		tagPersistenceDelegator.initialize(null);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAReadUsingCorrectInput() {
		tagPersistenceDelegator.initialize(readTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(BooksErrorConstants.RETRIEVE_TAGS_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(BooksErrorConstants.RETRIEVE_TAGS_SUCCESS_MSG));
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingCorrectInput() {
		tagPersistenceDelegator.initialize(readTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(BooksErrorConstants.RETRIEVE_TAGS_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(BooksErrorConstants.RETRIEVE_TAGS_SUCCESS_MSG));
	}

	@Override
	@Test
	public void testDelegateJPAReadUsingIncorrectDAOSetup() {
		tagPersistenceDelegator.initialize(readTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(BooksErrorConstants.RETRIEVE_TAGS_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(BooksErrorConstants.RETRIEVE_TAGS_FAILURE_MSG));
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingIncorrectDAOSetup() {
		tagPersistenceDelegator.initialize(readTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(BooksErrorConstants.RETRIEVE_TAGS_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(BooksErrorConstants.RETRIEVE_TAGS_FAILURE_MSG));
	}

	@Override
	@Test
	public void testDelegateJPAReadUsingInvalidInput() {
		// there are no invalid inputs
	}

	@Override
	@Test
	public void testDelegateObjectifyReadUsingInvalidInput() {
		// there are no invalid inputs
	}

	@Override
	@Test
	public void testDelegateJPAUpdateUsingCorrectInput() {
		// no update method so far. UPDATE should not be recognized
		tagPersistenceDelegator.initialize(updateTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingCorrectInput() {
		// no update method so far. UPDATE should not be recognized
		tagPersistenceDelegator.initialize(updateTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAUpdateUsingIncorrectDAOSetup() {
		// no update method so far. UPDATE should not be recognized
		tagPersistenceDelegator.initialize(updateTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingIncorrectDAOSetup() {
		// no update method so far. UPDATE should not be recognized
		tagPersistenceDelegator.initialize(updateTagDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImplGeneralFail);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateJPAUpdateUsingInvalidInput() {
		// no update method so far. UPDATE should not be recognized
		tagPersistenceDelegator.initialize(invalidUpdateDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagJPADAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateObjectifyUpdateUsingInvalidInput() {
		// no update method so far. UPDATE should not be recognized
		tagPersistenceDelegator.initialize(invalidUpdateDelegatorInput);
		tagPersistenceDelegator.setTagDAO(tagObjectifyDAOImpl);
		IDelegatorOutput delegatorOutput = tagPersistenceDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	/**
	 * Method setting up TagPersistenceDelegator inputs
	 */
	private static void setUpTagPersistenceDelegatorInputs() {
		// DelegatorInput for tagentity add
		addTagDelegatorInput = new DelegatorInput();
		addTagDelegatorInput.setInputObject(tagsA);
		addTagDelegatorInput.setType(DelegatorTypeEnum.ADD);

		// DelegatorInput for tagentity delete
		deleteTagDelegatorInput = new DelegatorInput();
		deleteTagDelegatorInput.setInputObject(tagsA);
		deleteTagDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		nullDeleteTagDelegatorInput = new DelegatorInput();
		nullDeleteTagDelegatorInput.setInputObject(null);
		nullDeleteTagDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInputs for tagentity read
		readTagDelegatorInput = new DelegatorInput();
		readTagDelegatorInput.setInputObject(tagsA);
		readTagDelegatorInput.setType(DelegatorTypeEnum.READ);

		nullReadTagDelegatorInput = new DelegatorInput();
		nullReadTagDelegatorInput.setInputObject(null);
		nullReadTagDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInputs for tagentity update
		updateTagDelegatorInput = new DelegatorInput();
		updateTagDelegatorInput.setInputObject(tagsA);
		updateTagDelegatorInput.setType(DelegatorTypeEnum.UPDATE);

		nullUpdateTagDelegatorInput = new DelegatorInput();
		nullUpdateTagDelegatorInput.setInputObject(null);
		nullUpdateTagDelegatorInput.setType(DelegatorTypeEnum.UPDATE);
	}

	/**
	 * Method setting up TagPersistenceDelegators
	 */
	private static void setUpTagPersistenceDelegators() {
		tagPersistenceDelegator = new TagPersistenceDelegator();
	}

	private static void setUpDAOMocks() throws EntityRetrievalException {
		// tagDAO mocks for retrieval of all tags
		tagJPADAOImpl = EasyMock.createNiceMock(TagJPADAOImpl.class);
		EasyMock.expect(tagJPADAOImpl.findAllEntities()).andReturn(JPATagEntityList);
		EasyMock.replay(tagJPADAOImpl);

		tagJPADAOImplGeneralFail = EasyMock.createNiceMock(TagJPADAOImpl.class);
		EasyMock.expect(tagJPADAOImplGeneralFail.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.replay(tagJPADAOImplGeneralFail);

		tagObjectifyDAOImpl = EasyMock.createNiceMock(TagObjectifyDAOImpl.class);
		EasyMock.expect(tagObjectifyDAOImpl.findAllEntities()).andReturn(objectifyTagEntityList);
		EasyMock.replay(tagObjectifyDAOImpl);

		tagObjectifyDAOImplGeneralFail = EasyMock.createNiceMock(TagObjectifyDAOImpl.class);
		EasyMock.expect(tagObjectifyDAOImplGeneralFail.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.replay(tagObjectifyDAOImplGeneralFail);
	}
}
