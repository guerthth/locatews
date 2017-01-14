package amtc.gue.ws.tournament.util;

/**
 * Class holding error constants that are TournamentService specific
 * 
 * @author Thomas
 *
 */
public class TournamentServiceErrorConstants {

	/**
	 * PlayerService Constants
	 */
	public static final int ADD_PLAYER_SUCCESS_CODE = 201;

	public static final int ADD_PLAYER_FAILURE_CODE = 500;

	public static final String ADD_PLAYER_SUCCESS_MSG = "Created. Added player(s):";

	public static final String ADD_PLAYER_FAILURE_MSG = "Failed to add player(s):";

	public static final int RETRIEVE_PLAYER_SUCCESS_CODE = 200;

	public static final int RETRIEVE_PLAYER_FAILURE_CODE = 404;

	public static final String RETRIEVE_PLAYER_SUCCESS_MSG = "OK. Retrieved players";

	public static final String RETRIEVE_PLAYER_FAILURE_MSG = "Not Found. Retrieving players failed";

	public static final int DELETE_PLAYER_SUCCESS_CODE = 200;

	public static final int DELETE_PLAYER_FAILURE_CODE = 500;

	public static final String DELETE_PLAYER_SUCCESS_MSG = "OK. Deleted player(s):";

	public static final String DELETE_PLAYER_FAILURE_MSG = "No player(s) found";
}
