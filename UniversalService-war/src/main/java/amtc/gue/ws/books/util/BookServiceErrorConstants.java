package amtc.gue.ws.books.util;

/**
 * Class holding error constants that are BookService specific
 * 
 * @author Thomas
 *
 */
public class BookServiceErrorConstants {

	/**
	 * BookService Constants
	 */
	public static final int ADD_BOOK_SUCCESS_CODE = 201;

	public static final int ADD_BOOK_FAILURE_CODE = 500;

	public static final String ADD_BOOK_SUCCESS_MSG = "Created. Added book(s):";

	public static final String ADD_BOOK_FAILURE_MSG = "Failed to add book(s):";

	public static final int RETRIEVE_BOOK_SUCCESS_CODE = 200;

	public static final int RETRIEVE_BOOK_FAILURE_CODE = 404;

	public static final String RETRIEVE_BOOK_SUCCESS_MSG = "OK. Retrieved books for tag(s)";

	public static final String RETRIEVE_BOOK_FAILURE_MSG = "Not Found. Retrieving books failed for tag(s):";

	public static final int DELETE_BOOK_SUCCESS_CODE = 200;

	public static final int DELETE_BOOK_FAILURE_CODE = 500;

	public static final String DELETE_BOOK_SUCCESS_MSG = "OK. Deleted book(s):";

	public static final String DELETE_BOOK_FAILURE_MSG = "No book(s) found";

	/**
	 * TagService Constants
	 */
	public static final String RETRIEVE_TAGS_SUCCESS_MSG = "OK. Found tags:";

	public static final int RETRIEVE_TAGS_SUCCESS_CODE = 200;

	public static final String RETRIEVE_TAGS_FAILURE_MSG = "Retrieving tags failed";

	public static final int RETRIEVE_TAGS_FAILURE_CODE = 500;
}
