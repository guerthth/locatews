package amtc.gue.ws.books.delegate.persist.output;

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
	 * @param statusCode the statuscode of the operation
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
	 * @param statusMessage the statusmessage of the WS operation
	 */
	public void setStatusMessage(String statusMessage);
	
	/**
	 * Getter for the status message of the WS operation
	 * 
	 * @return the status message of the WS operation
	 */
	public String getStatusMessage();
}
