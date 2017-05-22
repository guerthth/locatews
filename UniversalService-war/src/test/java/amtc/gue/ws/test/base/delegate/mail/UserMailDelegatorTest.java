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
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.dao.user.jpa.UserJPADAOImpl;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.jpa.GAEJPAUserEntity;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.test.base.UserTest;

/**
 * Testclass for the UserMailDelegator
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserMailDelegatorTest extends UserTest implements IBaseMailDelegatorTest {
	private static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAOImpl;
	private static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAOImplFail;
	private static UserDAO<GAEUserEntity, GAEJPAUserEntity, String> userJPADAOImplReturningNull;

	private static UserMailDelegator userMailDelegator;
	private static DelegatorInput userMailDelegatorInput;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		setUpBasicEnvironment();
		setUpDelegatorInputs();
		setUpUserMailDelegatorInputs();
		setUpUserMailDelegators();
		setUpDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(userJPADAOImplFail);
	}

	@Override
	@Test
	public void testDelegateUsingNullInput() {
		userMailDelegator.initialize(nullDelegatorInput);
		IDelegatorOutput delegatorOutput = userMailDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateUsingUnrecognizedInputType() {
		userMailDelegator.initialize(unrecognizedDelegatorInput);
		IDelegatorOutput delegatorOutput = userMailDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testDelegateMailUsingCorrectInput() {
		// not testable
	}

	/**
	 * Method testing mail sending functionality when user is not found
	 */
	@Test
	public void testDelegateUsingMailInputTypeWithNoFoundUser() {
		userMailDelegator.initialize(userMailDelegatorInput);
		userMailDelegator.setUserDAO(userJPADAOImplFail);
		IDelegatorOutput delegatorOutput = userMailDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_MSG, delegatorOutput.getStatusMessage());
	}

	/**
	 * Method testing mail sending functionality when found user is null
	 */
	@Test
	public void testDelegateUsingMailInputTypeUserSearchReturnsNull() {
		userMailDelegator.initialize(userMailDelegatorInput);
		userMailDelegator.setUserDAO(userJPADAOImplReturningNull);
		IDelegatorOutput delegatorOutput = userMailDelegator.delegate();
		assertEquals(ErrorConstants.RETRIEVE_USER_FAILURE_CODE, delegatorOutput.getStatusCode());
		assertTrue(delegatorOutput.getStatusMessage().startsWith(ErrorConstants.SEND_MAIL_USER_NOT_FOUND));
	}

	/**
	 * Method setting up UserMailDelegator Inputs
	 */
	private static void setUpUserMailDelegatorInputs() {
		userMailDelegatorInput = new DelegatorInput();
		userMailDelegatorInput.setInputObject(USERNAME);
		userMailDelegatorInput.setType(DelegatorTypeEnum.MAIL);
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
	 *             when issue occurs while trying to retrive entity
	 * @throws EntityPersistenceException
	 *             when issue occurs when trying to persist an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 */
	private static void setUpDAOMocks()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		// positive scenario (all calls return values)
		userJPADAOImpl = EasyMock.createNiceMock(UserJPADAOImpl.class);
		EasyMock.expect(userJPADAOImpl.findEntityById(EasyMock.isA(String.class))).andReturn(JPAUserEntity1);
		EasyMock.expect(userJPADAOImpl.getUserEntitiesByRoles(roles)).andReturn(JPAUserEntityList);
		EasyMock.expect(userJPADAOImpl.persistEntity(EasyMock.isA(GAEJPAUserEntity.class))).andReturn(JPAUserEntity1);
		EasyMock.expect(userJPADAOImpl.removeEntity(EasyMock.isA(GAEJPAUserEntity.class))).andReturn(JPAUserEntity1);
		EasyMock.replay(userJPADAOImpl);

		// negative scenario (all calls throw exceptions)
		userJPADAOImplFail = EasyMock.createNiceMock(UserJPADAOImpl.class);
		EasyMock.expect(userJPADAOImplFail.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.replay(userJPADAOImplFail);

		// negative scenario (foundUser is null)
		userJPADAOImplReturningNull = EasyMock.createNiceMock(UserJPADAOImpl.class);
		EasyMock.expect(userJPADAOImplReturningNull.findEntityById(EasyMock.isA(String.class))).andReturn(null);
		EasyMock.replay(userJPADAOImplReturningNull);
	}
}
