package amtc.gue.ws.base.service.rest;

import java.io.UnsupportedEncodingException;
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

import amtc.gue.ws.base.exception.HtmlReaderException;
import amtc.gue.ws.base.util.HtmlMapper;

/**
 * Servlet sending a notification email when a user is added to a billinggroup
 * 
 * @author Thomas
 *
 */
public class SendNewUserEmailServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(SendNewUserEmailServlet.class.getName());
	private static final String EMAIL = "noreply@theuniversalwebservice" + ".appspotmail.com";
	private static final String USERADDINGNOTIFICATIONHTMLFile = "/userAddingNotificationMail.html";

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		String receiverMail = request.getParameter("receiverMail");
		String addedUser = request.getParameter("addedUser");
		String billinggroupDescription = request.getParameter("billinggroupDescription");
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		Message msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(EMAIL, "Admin"));
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(receiverMail, ""));
			msg.setSubject("New user added to your billinggroup");
			msg.setContent(
					HtmlMapper.parseUserAddingHtml(addedUser, billinggroupDescription, USERADDINGNOTIFICATIONHTMLFile),
					"text/html");
			Transport.send(msg);
		} catch (MessagingException | HtmlReaderException | UnsupportedEncodingException e) {
			log.log(Level.WARNING, String.format("Failed to send an email to %s", receiverMail), e);
		}
	}
}
