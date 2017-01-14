package amtc.gue.ws.base.exception;

/**
 * Custom exception identifying exceptions occuring on service buildup
 * 
 * @author Thomas
 *
 */
public class ServiceInitializationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceInitializationException() {
		super();
	}

	/**
	 * 
	 * @param message the specified exception message
	 */
	public ServiceInitializationException(String message) {
		super(message);
	}

	/**
	 * 
	 * @param message the specified exception message
	 * @param exception the specific exception
	 */
	public ServiceInitializationException(String message, Throwable exception) {

		super(message, exception);

	}

}
