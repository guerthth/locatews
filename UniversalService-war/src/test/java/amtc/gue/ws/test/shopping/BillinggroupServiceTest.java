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
import amtc.gue.ws.shopping.delegate.persist.BillPersistenceDelegator;
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
	private static AbstractPersistenceDelegator billDelegator;
	private static AbstractPersistenceDelegator userDelegator;

	@BeforeClass
	public static void initialSetup() {
		setUpBasicShoppingEnvironment();
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void checkMocks() {
		// TODO CHeck
		// EasyMock.verify(billinggroupDelegator);
		// EasyMock.verify(userDelegator);
	}

	@Test(expected = UnauthorizedException.class)
	public void testAddBillinggroupsUsingUnauthorizedUser() throws UnauthorizedException {
		new BillinggroupService().addBillinggroups(null, billinggroups);
	}

	@Test
	public void testAddBillinggroups() throws UnauthorizedException {
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator, billDelegator)
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
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator, billDelegator)
				.getBillinggroups(user);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testRemoveBillinggroupUsingUnauthorizedUser() throws UnauthorizedException {
		new BillinggroupService().removeBillinggroup(null, null);
	}

	@Test
	public void testRemoveBillinggroup() throws UnauthorizedException {
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator, billDelegator)
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
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator, billDelegator)
				.updateBillinggroups(user, billinggroups);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testAddBillsToBillinggroupUsingUnauthorizedUser() throws UnauthorizedException {
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator, billDelegator)
				.addBillsToBillinggroup(null, billinggroup1.getBillinggroupId(), bills);
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test
	public void testAddBillsToBillinggroups() throws UnauthorizedException {
		BillinggroupServiceResponse resp = new BillinggroupService(userDelegator, billinggroupDelegator, billDelegator)
				.addBillsToBillinggroup(user, billinggroup1.getBillinggroupId(), bills);
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
		billinggroupDelegator = EasyMock.createNiceMock(BillinggroupPersistenceDelegator.class);
		EasyMock.expect(billinggroupDelegator.delegate()).andReturn(delegatorOutput).times(5);
		EasyMock.replay(billinggroupDelegator);

		billDelegator = EasyMock.createNiceMock(BillPersistenceDelegator.class);
		EasyMock.expect(billDelegator.delegate()).andReturn(delegatorOutput).times(1);
		EasyMock.replay(billDelegator);

		userDelegator = EasyMock.createNiceMock(UserPersistenceDelegator.class);
		EasyMock.expect(userDelegator.delegate()).andReturn(userDelegatorOutput).times(5);
		EasyMock.replay(userDelegator);
	}
}
