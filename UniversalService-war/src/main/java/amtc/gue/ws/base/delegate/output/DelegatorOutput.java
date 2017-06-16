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
	private String statusReason;
	private Object outputObject;

	@Override
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public int getStatusCode() {
		return statusCode;
	}

	@Override
	public void setStatusMessage(String statusMessage) {

		this.statusMessage = statusMessage;
	}

	@Override
	public String getStatusMessage() {
		return statusMessage;
	}

	@Override
	public void setOutputObject(Object outputObject) {
		this.outputObject = outputObject;

	}

	@Override
	public Object getOutputObject() {
		return outputObject;
	}

	@Override
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	@Override
	public String getStatusReason() {
		return statusReason;
	}
}
