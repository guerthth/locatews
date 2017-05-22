package amtc.gue.ws.test.base.delegate;

/**
 * Interface holding all methods that should be used for testing general
 * delegators implementation happens in the specific testclasses
 * 
 * @author Thomas
 *
 */
public interface IBaseDelegatorTest {
	/**
	 * Testing persistence delegator functionality with null input type
	 */
	public void testDelegateUsingNullInput();

	/**
	 * Testing persistence delegator functionality with unrecognized input type
	 */
	public void testDelegateUsingUnrecognizedInputType();
}
