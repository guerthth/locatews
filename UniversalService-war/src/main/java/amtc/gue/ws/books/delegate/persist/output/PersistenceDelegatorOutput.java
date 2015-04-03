package amtc.gue.ws.books.delegate.persist.output;

public class PersistenceDelegatorOutput implements IDelegatorOutput {

	/** status code */
	private int statusCode;
	
	/** status message */
	private String statusMessage;
	
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
	
}
