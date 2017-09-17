package amtc.gue.ws.base.service.rest;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import amtc.gue.ws.base.exception.HtmlReaderException;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.util.HtmlMapper;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillinggroupEntity;

/**
 * Servlet creating reports for all existing Billinggroups
 * 
 * @author Thomas
 *
 */
public class CreateBillinggroupReportsServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(SendNewUserEmailServlet.class.getName());
	private static final String EMAIL = "noreply@theuniversalwebservice" + ".appspotmail.com";
	private static final String BILLINGGROUPREPORTHTMLFile = "/billinggroupReport.html";

	static {
		ObjectifyService.register(GAEObjectifyBillinggroupEntity.class);
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		// load all billinggroups
		String billinggroupReport = null;
		Iterable<GAEObjectifyBillinggroupEntity> billinggroups = ofy().load()
				.type(GAEObjectifyBillinggroupEntity.class);

		for (GAEObjectifyBillinggroupEntity billinggroup : billinggroups) {
			billinggroupReport = buildBillinggroupReport(billinggroup);

			// send email to all billinggroup users
			for (GAEUserEntity user : billinggroup.getUsers()) {
				String receiverMail = user.getKey();
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props, null);
				Message msg = new MimeMessage(session);
				try {
					msg.setFrom(new InternetAddress(EMAIL, "Admin"));
					msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverMail, ""));
					msg.setSubject("Billinggroupreport");
					msg.setContent(HtmlMapper.parseBillinggroupResportHtml(billinggroup.getDescription(),
							billinggroupReport, BILLINGGROUPREPORTHTMLFile), "text/html");
					Transport.send(msg);
				} catch (MessagingException | HtmlReaderException | UnsupportedEncodingException e) {
					log.log(Level.WARNING, String.format("Failed to send an report email to %s", receiverMail), e);
				}
			}
		}

		// reponse status set to 204
		// request successful, no data sent back
		// browser stays on same page if request came from browser
		response.setStatus(204);
	}

	/**
	 * Method creating a report for a specific billinfgroup
	 * 
	 * @param billinggroup
	 *            the billinggroup
	 * @return the HTML Report as String
	 */
	private String buildBillinggroupReport(GAEObjectifyBillinggroupEntity billinggroup) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table>");
		sb.append("<tr><th>User</th><th>Spent Amount in Month</th></tr>");
		for (GAEUserEntity user : billinggroup.getUsers()) {
			// get bills for user
			List<GAEObjectifyBillEntity> billList = ofy().load().type(GAEObjectifyBillEntity.class)
					.ancestor(Key.create(user.getWebsafeKey())).filter("billinggroup", billinggroup.getKey()).list();
			Double sumAmount = 0.0;
			for (GAEObjectifyBillEntity bill : billList) {
				sumAmount += bill.getAmount();
			}
			sb.append("<tr><td>").append(user.getKey()).append("</td><td>").append(sumAmount).append("</td></tr>");
		}
		sb.append("</table>");
		String billinggroupReport = sb.toString();
		return billinggroupReport;
	}
}
