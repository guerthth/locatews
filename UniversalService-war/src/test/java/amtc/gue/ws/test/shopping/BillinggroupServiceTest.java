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
import amtc.gue.ws.shopping.BillinggroupService;
import amtc.gue.ws.shopping.delegate.persist.BillinggroupPersistenceDelegator;
import amtc.gue.ws.shopping.response.BillinggroupServiceResponse;

/**
 * Testclass for the Billinggroup REST Service
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BillinggroupServiceTest extends ShoppingTest {
	private static IDelegatorOutput delegatorOutput;
	private static IDelegatorOutput userDelegatorOutput;
	private static IDelegatorOutput failureUserDelegatorOutput;

	private static AbstractPersistenceDelegator billinggroupDelegator;
	private static AbstractPersistenceDelegator userDelegator;
	
	@BeforeClass
	public static void initialSetup() {
		setUpBasicShoppingEnvironment();
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void checkMocks() {
		EasyMock.verify(billinggroupDelegator);
		EasyMock.verify(userDelegator);
	}

	@Test(expected = UnauthorizedException.class)
	public void testAddBillinggroupsUsingUnauthorizedUser() throws UnauthorizedException {
		new BillinggroupService().addBillinggroups(null, billinggroups);
	}


	@Test
	public void testAddBillinggroups() throws UnauthorizedException {
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator)
				.addBillinggroups(user, billinggroups);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testGetBillinggroupsUsingUnauthorizedUser() throws UnauthorizedException {
		new BillinggroupService().getBillinggroups(null);
	}

	@Test
	public void testGetBillinggroups() throws UnauthorizedException {
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator)
				.getBillinggroups(user);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}
	
	@Test
	public void testGetBillinggroupsForUser() throws UnauthorizedException{
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator)
				.getBillinggroupsForUser(user, EMAIL);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}
	
	@Test(expected = UnauthorizedException.class)
	public void testGetBillinggroupsForUserUsingUnauthorizedUser() throws UnauthorizedException{
		new BillinggroupService().getBillinggroupsForUser(null, EMAIL);
	}

	@Test(expected = UnauthorizedException.class)
	public void testRemoveBillinggroupUsingUnauthorizedUser() throws UnauthorizedException {
		new BillinggroupService().removeBillinggroup(null, null);
	}

	@Test
	public void testRemoveBillinggroup() throws UnauthorizedException {
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator)
				.removeBillinggroup(user, billinggroup1.getBillinggroupId());
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testUpdateBillinggroupsUsingUnauthorizedUser() throws UnauthorizedException {
		new BillinggroupService().updateBillinggroups(null, null);
	}

	@Test
	public void testUpdateBillinggroups() throws UnauthorizedException {
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator)
				.updateBillinggroups(user, billinggroups);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testAddBillsToBillinggroupUsingUnauthorizedUser() throws UnauthorizedException {
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator)
				.addBillToBillinggroup(null, billinggroup1.getBillinggroupId(), bill1);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test
	public void testAddBillsToBillinggroups() throws UnauthorizedException {
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator)
				.addBillToBillinggroup(user, billinggroup1.getBillinggroupId(), bill1);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	// Helper Methods
	private static void setUpDelegatorOutputs() {
		delegatorOutput = new DelegatorOutput();
		userDelegatorOutput = new DelegatorOutput();
		userDelegatorOutput.setOutputObject(serviceUsers);
		failureUserDelegatorOutput = new DelegatorOutput();
		failureUserDelegatorOutput.setOutputObject(invalidServiceUsers);
	}

	private static void setUpDelegatorMocks() {
		billinggroupDelegator = EasyMock.createNiceMock(BillinggroupPersistenceDelegator.class);
		EasyMock.expect(billinggroupDelegator.delegate()).andReturn(delegatorOutput).times(6);
		EasyMock.replay(billinggroupDelegator);

		userDelegator = EasyMock.createNiceMock(UserPersistenceDelegator.class);
		EasyMock.expect(userDelegator.delegate()).andReturn(userDelegatorOutput).times(6);
		EasyMock.replay(userDelegator);
	}
}
