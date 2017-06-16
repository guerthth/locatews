package amtc.gue.ws.test.base;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import amtc.gue.ws.base.UserService;
import amtc.gue.ws.base.delegate.mail.AbstractMailDelegator;
import amtc.gue.ws.base.delegate.mail.UserMailDelegator;
import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.UserPersistenceDelegator;
import amtc.gue.ws.base.response.UserServiceResponse;

/**
 * Testclass for the User REST Service
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceTest extends UserTest {
	private static User user;
	private static amtc.gue.ws.base.inout.User serviceUser;
	private static amtc.gue.ws.base.inout.User invalidServiceUser;
	private static IDelegatorOutput userDelegatorOutput;
	private static IDelegatorOutput failureUserDelegatorOutput;
	private static IDelegatorOutput mailDelegatorOutput;
	private static AbstractPersistenceDelegator userDelegator;
	private static AbstractPersistenceDelegator failureUserDelegator;
	private static AbstractMailDelegator mailDelegator;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setupServiceUsers();
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(userDelegator);
		EasyMock.verify(mailDelegator);
	}

	@Test
	public void testAddUsers() throws UnauthorizedException {
		UserServiceResponse resp = new UserService(userDelegator, mailDelegator).addUsers(user, users);
		assertEquals(userDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(userDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testGetUsersUsingUnauthorizedUser() throws UnauthorizedException {
		new UserService().getUsers(null);
	}

	@Test
	public void testGetUsers() throws UnauthorizedException {
		UserServiceResponse resp = new UserService(userDelegator, mailDelegator).getUsers(user);
		assertEquals(userDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(userDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test
	public void testRemoveUser() throws UnauthorizedException {
		UserServiceResponse resp = new UserService(userDelegator, mailDelegator).removeUser(user, user.getUserId());
		assertEquals(userDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(userDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testRemoveUserUsingUnauthorizedUser() throws UnauthorizedException {
		UserServiceResponse resp = new UserService(userDelegator, mailDelegator).removeUser(null, user.getEmail());
		assertEquals(userDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(userDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test
	public void testSendUserMail() throws UnauthorizedException {
		UserServiceResponse resp = new UserService(userDelegator, mailDelegator).sendUserMail(user, user.getUserId());
		assertEquals(userDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(userDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testSendUserMailUsingUnauthorizedUser() throws UnauthorizedException {
		UserServiceResponse resp = new UserService(userDelegator, mailDelegator).sendUserMail(null, user.getUserId());
		assertEquals(userDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(userDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	// Helper methods
	private static void setupServiceUsers() {
		String userMail = "test@test.com";
		String authDomain = "domain";
		user = new User(userMail, authDomain, userMail);
		serviceUser = new amtc.gue.ws.base.inout.User();
		serviceUser.getRoles().add("users");
		invalidServiceUser = new amtc.gue.ws.base.inout.User();
		invalidServiceUser.getRoles().add("users");
	}

	private static void setUpDelegatorOutputs() {
		mailDelegatorOutput = new DelegatorOutput();
		userDelegatorOutput = new DelegatorOutput();
		userDelegatorOutput.setOutputObject(serviceUser);
		failureUserDelegatorOutput = new DelegatorOutput();
		userDelegatorOutput.setOutputObject(invalidServiceUser);
	}

	private static void setUpDelegatorMocks() {
		userDelegator = EasyMock.createNiceMock(UserPersistenceDelegator.class);
		EasyMock.expect(userDelegator.delegate()).andReturn(userDelegatorOutput).times(6);
		EasyMock.replay(userDelegator);

		failureUserDelegator = EasyMock.createNiceMock(UserPersistenceDelegator.class);
		EasyMock.expect(failureUserDelegator.delegate()).andReturn(failureUserDelegatorOutput).times(3);
		EasyMock.replay(failureUserDelegator);

		mailDelegator = EasyMock.createNiceMock(UserMailDelegator.class);
		EasyMock.expect(mailDelegator.delegate()).andReturn(mailDelegatorOutput);
		EasyMock.replay(mailDelegator);
	}
}
