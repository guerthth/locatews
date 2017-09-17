package amtc.gue.ws.base.service.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.appengine.labs.repackaged.com.google.common.base.Joiner;
import com.googlecode.objectify.ObjectifyService;

import amtc.gue.ws.Constants;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillinggroupEntity;

/**
 * Servlet adding billinggroup announcements to memcache
 * 
 * @author Thomas
 *
 */
public class SetBillinggroupAnnouncementServlet extends HttpServlet {

	static {
		ObjectifyService.register(GAEObjectifyBillinggroupEntity.class);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		// load all billinggroups
		Iterable<GAEObjectifyBillinggroupEntity> billinggroups = ofy().load()
				.type(GAEObjectifyBillinggroupEntity.class);

		List<String> billinggroupsToSubscribe = new ArrayList<>();
		for (GAEBillinggroupEntity billinggroup : billinggroups) {
			billinggroupsToSubscribe.add(billinggroup.getWebsafeKey());
		}
		if (!billinggroupsToSubscribe.isEmpty()) {
			StringBuilder sb = new StringBuilder("You can subscribe yourself to the following billinggroups: ");
			Joiner joiner = Joiner.on(", ").skipNulls();
			sb.append(joiner.join(billinggroupsToSubscribe));

			// use Memcache service to store that information
			MemcacheService memcacheService = MemcacheServiceFactory.getMemcacheService();
			String announcementKey = Constants.MEMCACHE_BILLINGGROUP_ANNOUNCEMENTS_KEY;
			String announcementText = sb.toString();

			memcacheService.put(announcementKey, announcementText);
		}
		// reponse status set to 204
		// request successful, no data sent back
		// browser stays on same page if request came from browser
		response.setStatus(204);
	}
}
