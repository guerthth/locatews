package amtc.gue.ws.test.base.delegate.mail;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.input.DelegatorInput;
import amtc.gue.ws.base.delegate.mail.UserMailDelegator;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.test.base.delegate.BaseDelegatorTest;

/**
 * Testclass for the UserMailDelegator
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserMailDelegatorTest extends BaseDelegatorTest {

	private static UserMailDelegator userMailDelegator;
	private static DelegatorInput unrecognizedUserMailDelegatorInput;
	private static DelegatorInput userMailDelegatorInput;
	private static final String USERNAME = "testUser";

	private static UserDAO userDAOImplFail;
	private static UserDAO userDAOImplReturningNull;

	@BeforeClass
	public static void initialSetup() throws EntityRetrievalException {
		setUpBaseDelegatorInputs();
		setUpUserMailDelegatorInputs();
		setUpUserMailDelegators();
		setUpMocks();
	}

	@AfterClass
	public static void tearDown() {
		EasyMock.verify(userDAOImplFail);
	}

	@Override
	public void testDelegateUsingNullInput() {
		userMailDelegator.initialize(nullDelegatorInput);
		IDelegatorOutput delegatorOutput = userMailDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	public void testDelegateUsingUnrecognizedInputType() {
		userMailDelegator.initialize(unrecognizedDelegatorInput);
		IDelegatorOutput delegatorOutput = userMailDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateUsingMailInputTypeWithNoFoundUser() {
		userMailDelegator.initialize(userMailDelegatorInput);
		userMailDelegator.setUserDAO(userDAOImplFail);
		IDelegatorOutput delegatorOutput = userMailDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	@Test
	public void testDelegateUsingMailInputTypeUserSearchReturnsNull() {
		userMailDelegator.initialize(userMailDelegatorInput);
		userMailDelegator.setUserDAO(userDAOImplReturningNull);
		IDelegatorOutput delegatorOutput = userMailDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(ErrorConstants.SEND_MAIL_USER_NOT_FOUND));
	}

	@Test
	public void testDelegateUsingMailInputTypeButInvalidInputObject() {
		userMailDelegator.initialize(unrecognizedUserMailDelegatorInput);
		IDelegatorOutput delegatorOutput = userMailDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	/**
	 * Method setting up UserMailDelegator Inputs
	 */
	private static void setUpUserMailDelegatorInputs() {
		userMailDelegatorInput = new DelegatorInput();
		userMailDelegatorInput.setInputObject(USERNAME);
		userMailDelegatorInput.setType(DelegatorTypeEnum.MAIL);

		unrecognizedUserMailDelegatorInput = new DelegatorInput();
		unrecognizedUserMailDelegatorInput.setInputObject(null);
		unrecognizedUserMailDelegatorInput.setType(DelegatorTypeEnum.MAIL);
	}

	/**
	 * Method setting up userMailDelegator instances
	 */
	private static void setUpUserMailDelegators() {
		userMailDelegator = new UserMailDelegator();
	}

	/**
	 * Method setting up some mock objects
	 * 
	 * @throws EntityRetrievalException
	 */
	private static void setUpMocks() throws EntityRetrievalException {
		// negative scenario (all calls throw exceptions)
		userDAOImplFail = EasyMock.createNiceMock(UserDAO.class);
		EasyMock.expect(userDAOImplFail.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(userDAOImplFail);

		// negative scenario (foundUser is null)
		userDAOImplReturningNull = EasyMock.createNiceMock(UserDAO.class);
		EasyMock.expect(userDAOImplReturningNull.findEntityById(EasyMock.isA(String.class))).andReturn(null);
		EasyMock.replay(userDAOImplReturningNull);
	}
}
