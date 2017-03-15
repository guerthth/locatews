package amtc.gue.ws.test.base.delegate;

import org.junit.Test;

import amtc.gue.ws.base.delegate.input.DelegatorInput;
import amtc.gue.ws.base.util.DelegatorTypeEnum;

/**
 * Abstract Class holding all methods that should be used for testing general
 * delegators implementation happens in the specific testclasses
 * 
 * @author Thomas
 *
 */
public abstract class BaseDelegatorTest {

	protected static DelegatorInput unrecognizedDelegatorInput;
	protected static DelegatorInput nullDelegatorInput;
	protected static final String TESTKEY = "testKey";

	/**
	 * Testing persistence delegator functionality with null input type
	 */
	@Test
	public abstract void testDelegateUsingNullInput();

	/**
	 * Testing persistence delegator functionality with unrecognized input type
	 */
	@Test
	public abstract void testDelegateUsingUnrecognizedInputType();

	/**
	 * Method setting up delegator inputs used by all delegator testclasses
	 */
	protected static void setUpBaseDelegatorInputs() {
		// DelegatorInput with null input
		nullDelegatorInput = null;

		// DelegatorInput with unrecognized input type
		unrecognizedDelegatorInput = new DelegatorInput();
		unrecognizedDelegatorInput.setInputObject(null);
		unrecognizedDelegatorInput.setType(DelegatorTypeEnum.UNRECOGNIZED);
	}
}
