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
import amtc.gue.ws.base.delegate.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.UserPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.output.PersistenceDelegatorOutput;
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
	private static IDelegatorOutput delegatorOutput;
	private static AbstractPersistenceDelegator userDelegator;

	@Override
	protected Application configure() {
		return new ResourceConfig().register(new UserService(userDelegator));
	}

	@BeforeClass
	public static void inititalSetup() {
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void checkMocks() {
		EasyMock.verify(userDelegator);
	}

	@Test(expected = NotFoundException.class)
	public void testServiceUsingIncorrectURL() {
		target("incorrectUrl").request().get(UserServiceResponse.class);
	}

	@Test
	public void testAddUsers() {
		Users usersToAdd = new Users();
		final Response resp = target("/users").request().post(
				Entity.json(usersToAdd));
		assertNotNull(resp);
	}

	@Test
	public void testGetUsers() {
		final UserServiceResponse resp = target("/users").request().get(
				UserServiceResponse.class);
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus()
				.getStatusMessage());
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus()
				.getStatusCode());
	}

	@Test(expected = NotAllowedException.class)
	public void testRemoveUserUsingIncorrectCall() {
		final UserServiceResponse resp = target("/users").request().delete(
				UserServiceResponse.class);
		assertNotNull(resp);
	}

	@Test
	public void testRemoveBookUsingCorrectCall() {
		final UserServiceResponse resp = target("/users/1").request().delete(
				UserServiceResponse.class);
		assertNotNull(resp);
	}

	// Helper methods
	private static void setUpDelegatorOutputs() {
		delegatorOutput = new PersistenceDelegatorOutput();
	}

	private static void setUpDelegatorMocks() {
		userDelegator = EasyMock.createNiceMock(UserPersistenceDelegator.class);
		EasyMock.expect(userDelegator.delegate()).andReturn(delegatorOutput)
				.times(3);
		EasyMock.replay(userDelegator);
	}
}
