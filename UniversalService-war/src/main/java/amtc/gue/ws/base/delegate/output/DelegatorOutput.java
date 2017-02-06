package amtc.gue.ws.base.delegate.output;


/**
 * Represents an Outputobject created by PersistenceDelegators
 * 
 * @author Thomas
 *
 */
public class DelegatorOutput implements IDelegatorOutput {

	private int statusCode;
	private String statusMessage;
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
