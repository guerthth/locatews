package amtc.gue.ws.books.delegate.persist.output;

import amtc.gue.ws.books.delegate.IDelegatorOutput;

public class PersistenceDelegatorOutput implements IDelegatorOutput {

	/** status code */
	private int statusCode;
	
	/** status message */
	private String statusMessage;
	
	/** output object */
	private Object outputObject;
	
	@Override
	public void setStatusCode(int statusCode) {
		
		this.statusCode = statusCode;
	}

	@Override
	public int getStatusCode() {
		
		return this.statusCode;
	}

	@Override
	public void setStatusMessage(String statusMessage) {
		
		this.statusMessage = statusMessage;
	}

	@Override
	public String getStatusMessage() {
		
		return this.statusMessage;
	}

	@Override
	public void setOutputObject(Object outputObject) {

		this.outputObject = outputObject;
		
	}

	@Override
	public Object getOutputObject() {
		
		return this.outputObject;
	}
	
}