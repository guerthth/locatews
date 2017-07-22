package amtc.gue.ws.test.shopping;

import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.api.server.spi.response.UnauthorizedException;

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.UserPersistenceDelegator;
import amtc.gue.ws.shopping.ShopService;
import amtc.gue.ws.shopping.delegate.persist.ShopPersistenceDelegator;
import amtc.gue.ws.shopping.response.ShopServiceResponse;

/**
 * Testclass for the Shop REST Service
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShopServiceTest extends ShoppingTest {
	private static IDelegatorOutput delegatorOutput;
	private static IDelegatorOutput userDelegatorOutput;
	private static IDelegatorOutput failureUserDelegatorOutput;

	private static AbstractPersistenceDelegator shopDelegator;
	private static AbstractPersistenceDelegator userDelegator;

	@BeforeClass
	public static void initialSetup() {
		setUpBasicShoppingEnvironment();
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void checkMocks() {
		EasyMock.verify(shopDelegator);
		EasyMock.verify(userDelegator);
	}

	@Test(expected = UnauthorizedException.class)
	public void testAddShopsUsingUnauthorizedUser() throws UnauthorizedException {
		new ShopService().addShops(null, shops);
	}

	@Test
	public void testAddShops() throws UnauthorizedException {
		ShopServiceResponse resp = new ShopService(userDelegator, shopDelegator).addShops(user, shops);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testGetShopsUsingUnauthorizedUser() throws UnauthorizedException {
		new ShopService().getShops(null);
	}

	@Test
	public void testGetShops() throws UnauthorizedException {
		ShopServiceResponse resp = new ShopService(userDelegator, shopDelegator).getShops(user);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testRemoveShopUsingUnauthorizedUser() throws UnauthorizedException {
		new ShopService().removeShop(null, null);
	}

	@Test
	public void testRemoveShop() throws UnauthorizedException {
		ShopServiceResponse resp = new ShopService(userDelegator, shopDelegator).removeShop(user, user.getEmail());
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testUpdateShopsUsingUnauthorizedUser() throws UnauthorizedException {
		new ShopService().updateShops(null, null);
	}

	@Test
	public void testUpdateShops() throws UnauthorizedException {
		ShopServiceResponse resp = new ShopService(userDelegator, shopDelegator).updateShops(user, shops);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	// Helper Methods
	private static void setUpDelegatorOutputs() {
		delegatorOutput = new DelegatorOutput();
		userDelegatorOutput = new DelegatorOutput();
		userDelegatorOutput.setOutputObject(serviceUser);
		failureUserDelegatorOutput = new DelegatorOutput();
		failureUserDelegatorOutput.setOutputObject(invalidServiceUser);
	}

	private static void setUpDelegatorMocks() {
		shopDelegator = EasyMock.createNiceMock(ShopPersistenceDelegator.class);
		EasyMock.expect(shopDelegator.delegate()).andReturn(delegatorOutput).times(4);
		EasyMock.replay(shopDelegator);

		userDelegator = EasyMock.createNiceMock(UserPersistenceDelegator.class);
		EasyMock.expect(userDelegator.delegate()).andReturn(userDelegatorOutput).times(4);
		EasyMock.replay(userDelegator);
	}
}
