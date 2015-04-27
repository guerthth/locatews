package amtc.gue.ws.books.utils;

/**
 * Class holding errorconstants
 * 
 * @author Thomas
 *
 */
public class ErrorConstants {
	
	/** unrecognnized persistence object */
	public static final String UNRECOGNIZED_PERSISTENCE_OBJECT = 
			"Persistence object type not recognized";
	
	/** OK statuscode for adding books */
	public static final int ADD_BOOK_SUCCESS_CODE = 10;
	
	/** NOK statuscode for adding books */
	public static final int ADD_BOOK_FAILURE_CODE = 11;
	
	/** message for adding books */
	public static final String ADD_BOOK_SUCCESS_MSG = "Added books";
	
	/** OK statuscode for retrieving books */
	public static final int RETRIEVE_BOOK_SUCCESS_CODE = 12;
	
	/** NOK statuscode for retrieving books */
	public static final int RETRIEVE_BOOK_FAILURE_CODE = 13;
	
	/** message for retrieving books */
	public static final String RETRIEVE_BOOK_SUCCESS_MSG = "Retrieved books";
	
}
