package amtc.gue.ws.books.delegate.persist.exception;

/**
 * Exception implementation for entity removal problems 
 * 
 * @author Thomas
 *
 */
public class EntityRemovalException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor
	 */
	public EntityRemovalException() {

		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            the exception message
	 */
	public EntityRemovalException(String message) {

		super(message);

	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            the exception message
	 * @param exception
	 *            the exception
	 */
	public EntityRemovalException(String message, Throwable exception) {

		super(message, exception);

	}
}
