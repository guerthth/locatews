package amtc.gue.ws.test.vcsapi.delegate;

import amtc.gue.ws.test.base.delegate.IBaseDelegatorTest;

/**
 * Interface holding all methods that should be applied for VCSAPI delegator
 * testing
 * 
 * @author Thomas
 *
 */
public interface IVCSAPIDelegatorTest extends IBaseDelegatorTest {
	/**
	 * Testing VCSAPI delegator ADD functionality success
	 */
	public void testAddIssueSucess();

	/**
	 * Testing VCSAPI delegator ADD functionality failure
	 */
	public void testAddIssueFailure();

	/**
	 * Testing VCSAPI delegator ADD using an unrecognized input object
	 */
	public void testAddIssueUsingUnrecognizedInputObject();

	/**
	 * Testing VCSAPI delegator GET functionality success
	 */
	public void testGetIssueSuccess();

	/**
	 * Testing VCSAPI delegator GET functionality failure
	 */
	public void testGetIssueFailure();
}
