package amtc.gue.ws.books.delegate.persist.exception;

/**
 * Exception implementation for entity persistence problems 
 * 
 * @author Thomas
 *
 */
public class EntityPersistenceException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public EntityPersistenceException() {
		
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message the exception message
	 */
	public EntityPersistenceException(String message) {
		
		super(message);
		
	}

	/**
	 * Constructor
	 * 
	 * @param message the exception message
	 * @param exception the exception 
	 */
	public EntityPersistenceException(String message, Throwable exception) {
		
		super(message, exception);
		
	}
	
	

}
