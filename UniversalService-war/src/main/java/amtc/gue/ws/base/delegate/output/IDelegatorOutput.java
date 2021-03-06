package amtc.gue.ws.base.delegate.output;


/**
 * Delegator Output Object interface
 * 
 * @author Thomas
 *
 */
public interface IDelegatorOutput {

	/**
	 * Setter for statuscode of the WS operation
	 * 
	 * @param statusCode
	 *            the statuscode of the operation
	 */
	public void setStatusCode(int statusCode);

	/**
	 * Getter for statuscode of the WS operation
	 * 
	 * @return the statuscode of the WS operation
	 */
	public int getStatusCode();

	/**
	 * Setter for the status message of the WS operation
	 * 
	 * @param statusMessage
	 *            the statusmessage of the WS operation
	 */
	public void setStatusMessage(String statusMessage);

	/**
	 * Getter for the status message of the WS operation
	 * 
	 * @return the status message of the WS operation
	 */
	public String getStatusMessage();

	/**
	 * Setter for the status reason of the WS operation
	 * 
	 * @param statusReason
	 *            the statusReason of the WS operation
	 */
	public void setStatusReason(String statusReason);

	/**
	 * Getter for the status reason of the WS operation
	 * 
	 * @return the statusReason of the WS operation
	 */
	public String getStatusReason();

	/**
	 * Setter for the output object
	 * 
	 * @param object
	 *            the output object
	 */
	public void setOutputObject(Object object);

	/**
	 * Getter for the output object
	 * 
	 * @return the output object
	 */
	public Object getOutputObject();
}
