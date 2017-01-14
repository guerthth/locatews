package amtc.gue.ws.base.exception;

/**
 * Exception implementation for CSV Reading
 * 
 * @author Thomas
 *
 */
public class CSVReaderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2026166831240419551L;

	private static final String CSVEXCEPTIONMESSAGE = "Error occured while trying to retrieve users from CSV file: ";

	/**
	 * Default constructor
	 */
	public CSVReaderException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param csvFile
	 *            the CSV file name
	 */
	public CSVReaderException(String csvFile) {
		super(CSVEXCEPTIONMESSAGE + csvFile);
	}

	/**
	 * Constructor
	 * 
	 * @param csvFile
	 *            the CSV file name
	 * @param exception
	 *            the exception that occured
	 */
	public CSVReaderException(String csvFile, Throwable exception) {
		super(CSVEXCEPTIONMESSAGE + csvFile, exception);
	}
}
