package amtc.gue.ws.base.response;

import com.googlecode.objectify.annotation.Cache;

/**
 * Announcement from datastore memcache
 * 
 * @author Thomas
 *
 */
@Cache
public class Announcement {
	private String message;

	public Announcement(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
