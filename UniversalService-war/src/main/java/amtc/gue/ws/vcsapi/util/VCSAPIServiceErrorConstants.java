package amtc.gue.ws.vcsapi.util;

/**
 * Class holding error constants that are GithubAPIService specific
 * 
 * @author Thomas
 *
 */
public class VCSAPIServiceErrorConstants {
	
	public static final int ADD_ISSUE_SUCCESS_CODE = 201;
	
	public static final int ADD_ISSUE_FAILURE_CODE = 500;
	
	public static final String ADD_ISSUE_SUCCESS_MSG = "Created. Added issue";
	
	public static final String ADD_ISSUE_FAILURE_MSG = "Failed to add issue";
	
	public static final int RETRIEVE_ISSUES_SUCCESS_CODE = 200;
	
	public static final int RETRIEVE_ISSUES_FAILURE_CODE = 404;
	
	public static final String RETRIEVE_ISSUES_SUCCESS_MSG = "OK. Retrieved issues";
	
	public static final String RETRIEVE_ISSUES_FAILURE_MSG = "Not Found. Retrieving issues failed";
}
