package amtc.gue.ws.books.delegate.persist.exception;

public class InvalidPersistenceObjectException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public InvalidPersistenceObjectException() {
		
	}

	/**
	 * Constructor accepting the exception message
	 * and delegating it to the constructor of the super class
	 * @param message
	 */
	public InvalidPersistenceObjectException(String message) {
		super(message);
	}
	
	
}
