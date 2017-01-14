package amtc.gue.ws.base.delegate;

/**
 * Delegator Interface
 * 
 * @author Thomas
 *
 */
public interface IDelegator {

	/**
	 * Delegate method persisting object to the underlying DB
	 * 
	 * @return the created IDelegatorOutput
	 */
	IDelegatorOutput delegate();

	/**
	 * Method initializing the persistenceDelegatorInput
	 * 
	 * @param input
	 *            the input used by the delegator
	 */
	void initialize(IDelegatorInput input);

	/**
	 * Method setting the delegator output due to unrecognized input type
	 */
	void setUnrecognizedInputDelegatorOutput();
}
