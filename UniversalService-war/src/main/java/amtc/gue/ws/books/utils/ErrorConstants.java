package amtc.gue.ws.books.utils;

/**
 * Class holding errorconstants
 * 
 * @author Thomas
 *
 */
public class ErrorConstants {

	/** OK statuscode for adding books */
	public static final int ADD_BOOK_SUCCESS_CODE = 201;

	/** NOK statuscode for adding books */
	public static final int ADD_BOOK_FAILURE_CODE = 12;

	/** message for adding books */
	public static final String ADD_BOOK_SUCCESS_MSG = "Created. Added book(s):";

	/** message for failed adding of books */
	public static final String ADD_BOOK_FAILURE_MSG = "Failed to add book(s):";

	/** OK statuscode for retrieving books */
	public static final int RETRIEVE_BOOK_SUCCESS_CODE = 200;

	/** NOK statuscode for retrieving books */
	public static final int RETRIEVE_BOOK_FAILURE_CODE = 14;

	/** message for retrieving books */
	public static final String RETRIEVE_BOOK_SUCCESS_MSG = "OK. Retrieved books for tag(s)";

	/** fail message for retrieving books */
	public static final String RETRIEVE_BOOK_FAILURE_MSG = "Retrieving books failed for tag(s):";

	/** OK statuscode for deleting books */
	public static final int DELETE_BOOK_SUCCESS_CODE = 200;

	/** NOK statuscode for deleting books */
	public static final int DELETE_BOOK_FAILURE_CODE = 16;

	/** message for deleting books */
	public static final String DELETE_BOOK_SUCCESS_MSG = "OK. Deleted book(s):";

	/** fail message for deleting books */
	public static final String DELETE_BOOK_FAILURE_MSG = "No book(s) found";

	/** unrecognnized persistence object */
	public static final String UNRECOGNIZED_INPUT_OBJECT_MSG = "Bad Request. Input object type not recognized";

	/** statuscode for unrecognnized persistence object */
	public static final int UNRECOGNIZED_INPUT_OBJECT_CODE = 400;

	/** message for successful tag retrieval */
	public static final String RETRIEVE_TAGS_SUCCESS_MSG = "OK. Found tags:";

	/** status code for successful tag retrieval */
	public static final int RETRIEVE_TAGS_SUCCESS_CODE = 200;

	/** message for unsuccessful tag retrieval */
	public static final String RETRIEVE_TAGS_FAILURE_MSG = "Retrieving tags failed";

	/** status code for unsuccessful tag retrieval */
	public static final int RETRIEVE_TAGS_FAILURE_CODE = 19;

}
