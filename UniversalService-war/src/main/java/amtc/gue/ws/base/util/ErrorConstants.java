package amtc.gue.ws.base.util;

/**
 * Class holding general Universalservice errorconstants
 * 
 * @author Thomas
 *
 */
public class ErrorConstants {
	/**
	 * General error constants
	 */
	public static final String UNRECOGNIZED_INPUT_OBJECT_MSG = "Bad Request. Input object type not recognized";

	public static final int UNRECOGNIZED_INPUT_OBJECT_CODE = 400;
	
	/**
	 * Userservice error constants
	 */
	public static final int ADD_USER_SUCCESS_CODE = 201;
	
	public static final int ADD_USER_FAILURE_CODE = 500;
	
	public static final String ADD_USER_SUCCESS_MSG = "Created. Added user(s):";

	public static final String ADD_USER_FAILURE_MSG = "Failed to add user(s):";
	
	public static final int DELETE_USER_SUCCESS_CODE = 200;

	public static final int DELETE_USER_FAILURE_CODE = 500;

	public static final String DELETE_USER_SUCCESS_MSG = "OK. Deleted user(s):";

	public static final String DELETE_USER_FAILURE_MSG = "No user(s) found";
	
	public static final int RETRIEVE_USER_SUCCESS_CODE = 200;

	public static final int RETRIEVE_USER_FAILURE_CODE = 404;

	public static final String RETRIEVE_USER_SUCCESS_MSG = "OK. Retrieved users for role(s)";

	public static final String RETRIEVE_USER_FAILURE_MSG = "Not Found. Retrieving users failed";
}
