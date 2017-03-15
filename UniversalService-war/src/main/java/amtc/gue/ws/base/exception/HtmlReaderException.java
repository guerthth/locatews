package amtc.gue.ws.base.exception;

/**
 * Exception implementation for Html Reading
 * 
 * @author Thomas
 *
 */
public class HtmlReaderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2491247682064449417L;

	private static final String HTMLEXCEPTIONMESSAGE = "Error occured while trying to read HTML File: ";

	public HtmlReaderException() {
		super();
	}

	public HtmlReaderException(String htmlFile) {
		super(HTMLEXCEPTIONMESSAGE + htmlFile);
	}

	public HtmlReaderException(String htmlFile, Throwable exception) {
		super(HTMLEXCEPTIONMESSAGE + htmlFile, exception);
	}
}
