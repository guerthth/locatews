package amtc.gue.ws;

import com.google.api.server.spi.Constant;

/**
 * Contains the client IDs and scopes for allowed clients consuming the service
 * APIs.
 * 
 * @author Thomas
 *
 */
public class Constants {
	public static final String WEB_CLIENT_ID = "1017704499337-8s3a0grnio4emiura8673l33qrst7nu2.apps.googleusercontent.com";
	public static final String EMAIL_SCOPE = Constant.API_EMAIL_SCOPE;
	public static final String API_EXPLORER_CLIENT_ID = Constant.API_EXPLORER_CLIENT_ID;
	
	public static final String MEMCACHE_BILLINGGROUP_ANNOUNCEMENTS_KEY = "RECENT_BILLINGGROUP_ANNOUNCEMENTS";
}
