package amtc.gue.ws.books.delegate.persist.exception;

public class EntityRetrievalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Default constructor
	 */
	public EntityRetrievalException() {
		
		super();
	}
	
	/**
	 * Constructor
	 * 
	 * @param message the exception message
	 */
	public EntityRetrievalException(String message) {
		
		super(message);
	}
	
	/**
	 * Constructor
	 * 
	 * @param message the exception message
	 * @param exception the exception
	 */
	public EntityRetrievalException(String message, Throwable exception){
		
		super(message, exception);
	}

}
