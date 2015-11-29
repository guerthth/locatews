package amtc.gue.ws.books.utils;

/**
 * Class holding errorconstants
 * 
 * @author Thomas
 *
 */
public class ErrorConstants {
	
	/** OK statuscode for adding books */
	public static final int ADD_BOOK_SUCCESS_CODE = 10;
	
	/** NOK statuscode for adding books */
	public static final int ADD_BOOK_FAILURE_CODE = 11;
	
	/** message for adding books */
	public static final String ADD_BOOK_SUCCESS_MSG = "Added books";
	
	/** message for failed adding of books */
	public static final String ADD_BOOK_FAILURE_MSG = "Adding books failed";
	
	/** OK statuscode for retrieving books */
	public static final int RETRIEVE_BOOK_SUCCESS_CODE = 12;
	
	/** NOK statuscode for retrieving books */
	public static final int RETRIEVE_BOOK_FAILURE_CODE = 13;
	
	/** message for retrieving books */
	public static final String RETRIEVE_BOOK_SUCCESS_MSG = "Retrieved books";
	
	/** fail message for retrieving books */
	public static final String RETRIEVE_BOOK_FAILURE_MSG = "Retrieving books failed";
	
	/** OK statuscode for deleting books */
	public static final int DELETE_BOOK_SUCCESS_CODE = 14;
	
	/** NOK statuscode for deleting books */
	public static final int DELETE_BOOK_FAILURE_CODE = 15;
	
	/** message for deleting books */
	public static final String DELETE_BOOK_SUCCESS_MSG = "Deleted book(s): ";
	
	/** fail message for deleting books */
	public static final String DELETE_BOOK_FAILURE_MSG = "Deleting book(s) failed";
	
	/** unrecognnized persistence object */
	public static final String UNRECOGNIZED_INPUT_OBJECT_MSG = 
			"Input object type not recognized";
	
	/** statuscode for unrecognnized persistence object */
	public static final int UNRECOGNIZED_INPUT_OBJECT_CODE = 14;
	
}
