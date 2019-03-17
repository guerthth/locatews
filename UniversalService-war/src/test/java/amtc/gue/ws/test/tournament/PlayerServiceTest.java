package amtc.gue.ws.test.tournament;

import static org.junit.Assert.*;

import org.easymock.EasyMock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.response.UnauthorizedException;

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.UserPersistenceDelegator;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.tournament.PlayerService;
import amtc.gue.ws.tournament.delegate.persist.PlayerPersistenceDelegator;
import amtc.gue.ws.tournament.response.PlayerServiceResponse;

/**
 * Testclass for the Player REST Service
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerServiceTest extends TournamentTest {
	private static User user;
	private static amtc.gue.ws.base.inout.User serviceUser;
	private static amtc.gue.ws.base.inout.User invalidServiceUser;
	private static Users serviceUsers;
	private static Users invalidServiceUsers;
	private static IDelegatorOutput delegatorOutput;
	private static IDelegatorOutput userDelegatorOutput;
	private static IDelegatorOutput failureUserDelegatorOutput;
	private static AbstractPersistenceDelegator playerDelegator;
	private static AbstractPersistenceDelegator userDelegator;
	private static AbstractPersistenceDelegator failureUserDelegator;

	@BeforeClass
	public static void initialSetup() {
		setUpBasicTournamentEnvironment();
		setUpServiceUsers();
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void checkMocks() {
		EasyMock.verify(playerDelegator);
		EasyMock.verify(userDelegator);
	}

	@Test(expected = UnauthorizedException.class)
	public void testAddPlayersUsingUnauthorizedUser() throws UnauthorizedException {
		new PlayerService().addPlayers(null, players);
	}

	@Test
	public void testAddPlayers() throws UnauthorizedException {
		PlayerServiceResponse resp = new PlayerService(userDelegator, playerDelegator).addPlayers(user, players);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testGetPlayersUsingUnauthorizedUser() throws UnauthorizedException {
		new PlayerService().getPlayers(null);
	}

	@Test
	public void testGetPlayers() throws UnauthorizedException {
		PlayerServiceResponse resp = new PlayerService(userDelegator, playerDelegator).getPlayers(user);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test
	public void testRemovePlayer() throws UnauthorizedException {
		PlayerServiceResponse resp = new PlayerService(userDelegator, playerDelegator).removePlayer(user, null);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testRemovePlayerUsingUnauthorizedUser() throws UnauthorizedException {
		PlayerServiceResponse resp = new PlayerService(userDelegator, playerDelegator).removePlayer(null,
				user.getEmail());
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	// Helper Methods
	private static void setUpServiceUsers() {
		String userMail = "test@test.com";
		String authDomain = "domain";
		user = new User(userMail, authDomain);
		serviceUser = new amtc.gue.ws.base.inout.User();
		serviceUser.getRoles().add("tournament");
		invalidServiceUser = new amtc.gue.ws.base.inout.User();
		invalidServiceUser.getRoles().add("tournament");


		serviceUsers = new Users();
		serviceUsers.getUsers().add(serviceUser);
		invalidServiceUsers = new Users();
		invalidServiceUsers.getUsers().add(invalidServiceUser);	
	}

	private static void setUpDelegatorOutputs() {
		delegatorOutput = new DelegatorOutput();
		userDelegatorOutput = new DelegatorOutput();
		userDelegatorOutput.setOutputObject(serviceUsers);
		failureUserDelegatorOutput = new DelegatorOutput();
		failureUserDelegatorOutput.setOutputObject(invalidServiceUsers);
	}

	private static void setUpDelegatorMocks() {
		playerDelegator = EasyMock.createNiceMock(PlayerPersistenceDelegator.class);
		EasyMock.expect(playerDelegator.delegate()).andReturn(delegatorOutput).times(3);
		EasyMock.replay(playerDelegator);

		userDelegator = EasyMock.createNiceMock(UserPersistenceDelegator.class);
		EasyMock.expect(userDelegator.delegate()).andReturn(userDelegatorOutput).times(3);
		EasyMock.replay(userDelegator);
		
		failureUserDelegator = EasyMock.createNiceMock(UserPersistenceDelegator.class);
		EasyMock.expect(failureUserDelegator.delegate()).andReturn(failureUserDelegatorOutput).times(3);
		EasyMock.replay(failureUserDelegator);
	}
}
