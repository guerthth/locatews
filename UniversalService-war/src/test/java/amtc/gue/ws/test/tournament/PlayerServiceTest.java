package amtc.gue.ws.test.tournament;

import static org.junit.Assert.*;

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

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.tournament.PlayerService;
import amtc.gue.ws.tournament.delegate.persist.PlayerPersistenceDelegator;
import amtc.gue.ws.tournament.inout.Players;
import amtc.gue.ws.tournament.response.PlayerServiceResponse;

/**
 * Testclass for the Player REST Service
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerServiceTest extends JerseyTest {
	private static IDelegatorOutput delegatorOutput;
	private static AbstractPersistenceDelegator playerDelegator;

	@Override
	protected Application configure() {
		return new ResourceConfig()
				.register(new PlayerService(playerDelegator));
	}

	@BeforeClass
	public static void initialSetup() {
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void checkMocks() {
		EasyMock.verify(playerDelegator);
	}

	@Test(expected = NotFoundException.class)
	public void testServiceUsingIncorrectURL() {
		target("incorrectUrl").request().get(PlayerServiceResponse.class);
	}

	@Test
	public void testAddPlayers() {
		Players playersToAdd = new Players();
		final Response resp = target("/players").request().post(
				Entity.json(playersToAdd));
		assertNotNull(resp);
	}

	@Test
	public void testGetPlayers() {
		final PlayerServiceResponse resp = target("/players").request().get(
				PlayerServiceResponse.class);
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus()
				.getStatusMessage());
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus()
				.getStatusCode());
	}
	
	@Test(expected = NotAllowedException.class)
	public void testRemovePlayerUsingIncorrectCall() {
		final PlayerServiceResponse resp = target("/players").request().delete(
				PlayerServiceResponse.class);
		assertNotNull(resp);
	}

	@Test
	public void testRemoveBookUsingCorrectCall() {
		final PlayerServiceResponse resp = target("/players/1").request().delete(
				PlayerServiceResponse.class);
		assertNotNull(resp);
	}

	// Helper Methods
	private static void setUpDelegatorOutputs() {
		delegatorOutput = new DelegatorOutput();
	}

	private static void setUpDelegatorMocks() {
		playerDelegator = EasyMock
				.createNiceMock(PlayerPersistenceDelegator.class);
		EasyMock.expect(playerDelegator.delegate()).andReturn(delegatorOutput)
				.times(3);
		EasyMock.replay(playerDelegator);
	}
}
