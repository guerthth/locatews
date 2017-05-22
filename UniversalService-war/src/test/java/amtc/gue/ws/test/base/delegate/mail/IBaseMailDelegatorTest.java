package amtc.gue.ws.test.base.delegate.mail;

import amtc.gue.ws.test.base.delegate.IBaseDelegatorTest;

/**
 * Interface holding all methods that should be applied for mail delegator
 * testing
 * 
 * @author Thomas
 *
 */
public interface IBaseMailDelegatorTest extends IBaseDelegatorTest {

	/**
	 * Testing mail delegator MAIL functionality using correct input
	 */
	public void testDelegateMailUsingCorrectInput();
}
