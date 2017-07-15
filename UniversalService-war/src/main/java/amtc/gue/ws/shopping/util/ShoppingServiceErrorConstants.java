package amtc.gue.ws.shopping.util;

/**
 * Class holding error constants that are ShoppingService specific
 * 
 * @author Thomas
 *
 */
public class ShoppingServiceErrorConstants {

	/**
	 * ShoppingService Constants
	 */
	public static final int ADD_SHOP_SUCCESS_CODE = 201;

	public static final int ADD_SHOP_FAILURE_CODE = 500;

	public static final String ADD_SHOP_SUCCESS_MSG = "Created. Added shop(s):";

	public static final String ADD_SHOP_FAILURE_MSG = "Failed to add shop(s):";

	public static final int DELETE_SHOP_SUCCESS_CODE = 200;

	public static final int DELETE_SHOP_FAILURE_CODE = 500;

	public static final String DELETE_SHOP_SUCCESS_MSG = "OK. Deleted shop(s):";

	public static final String DELETE_SHOP_FAILURE_MSG = "No shop(s) found";

	public static final int RETRIEVE_SHOP_SUCCESS_CODE = 200;

	public static final int RETRIEVE_SHOP_FAILURE_CODE = 404;

	public static final String RETRIEVE_SHOP_SUCCESS_MSG = "OK. Retrieved shops";

	public static final String RETRIEVE_SHOP_BY_ID_SUCCESS_MSG = "OK. Retrieved shop for shopId";

	public static final String RETRIEVE_SHOP_FAILURE_MSG = "Not Found. Retrieving shops failed";

	public static final int UPDATE_SHOP_SUCCESS_CODE = 201;

	public static final int UPDATE_SHOP_FAILURE_CODE = 500;

	public static final String UPDATE_SHOP_SUCCESS_MSG = "Updated shop(s):";

	public static final String UPDATE_SHOP_FAILURE_MSG = "Failed to update shop(s)";
}
