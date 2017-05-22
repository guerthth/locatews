package amtc.gue.ws.test.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.easymock.EasyMock;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.UserService;
import amtc.gue.ws.base.delegate.mail.AbstractMailDelegator;
import amtc.gue.ws.base.delegate.mail.UserMailDelegator;
import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.UserPersistenceDelegator;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.response.UserServiceResponse;

/**
 * Testclass for the User REST Service
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest extends JerseyTest {
	private static IDelegatorOutput userDelegatorOutput;
	private static IDelegatorOutput mailDelegatorOutput;
	private static AbstractPersistenceDelegator userDelegator;
	private static AbstractMailDelegator mailDelegator;

	@Override
	protected Application configure() {
		return new ResourceConfig().register(new UserService(userDelegator, mailDelegator));
	}

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(userDelegator);
		EasyMock.verify(mailDelegator);
	}

	@Test(expected = NotFoundException.class)
	public void testServiceUsingIncorrectURL() {
		target("incorrectUrl").request().get(UserServiceResponse.class);
	}

	@Test
	public void testAddUsers() {
		Users usersToAdd = new Users();
		final Response resp = target("/users").request().post(Entity.json(usersToAdd));
		assertNotNull(resp);
	}

	@Test
	public void testGetUsers() {
		final UserServiceResponse resp = target("/users").request().get(UserServiceResponse.class);
		assertEquals(userDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
		assertEquals(userDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
	}

	@Test(expected = NotAllowedException.class)
	public void testRemoveUserUsingIncorrectCall() {
		target("/users").request().delete(UserServiceResponse.class);
	}

	@Test
	public void testRemoveBookUsingCorrectCall() {
		final UserServiceResponse resp = target("/users/1").request().delete(UserServiceResponse.class);
		assertNotNull(resp);
	}

	@Test
	public void testSendUserPasswordMail() {
		final Response resp = target("/users/1/password").request().put(Entity.json("test"));
		assertNotNull(resp);
	}

	@Test
	public void testResetPassword() {
		final UserServiceResponse resp = target("/users/1/password").request().get(UserServiceResponse.class);
		assertNotNull(resp);
	}

	// Helper methods
	private static void setUpDelegatorOutputs() {
		userDelegatorOutput = new DelegatorOutput();
		mailDelegatorOutput = new DelegatorOutput();
	}

	private static void setUpDelegatorMocks() {
		userDelegator = EasyMock.createNiceMock(UserPersistenceDelegator.class);
		EasyMock.expect(userDelegator.delegate()).andReturn(userDelegatorOutput).times(3);
		EasyMock.replay(userDelegator);
		mailDelegator = EasyMock.createNiceMock(UserMailDelegator.class);
		EasyMock.expect(mailDelegator.delegate()).andReturn(mailDelegatorOutput);
		EasyMock.replay(mailDelegator);
	}
}
