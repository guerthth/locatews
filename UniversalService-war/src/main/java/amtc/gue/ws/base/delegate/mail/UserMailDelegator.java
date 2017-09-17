package amtc.gue.ws.base.delegate.mail;

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

import amtc.gue.ws.base.delegate.input.IDelegatorInput;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.exception.HtmlReaderException;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.base.util.HtmlMapper;

/**
 * Mail Delegator that sends emails to specific users
 * 
 * @author Thomas
 *
 */
public class UserMailDelegator extends AbstractMailDelegator {

	private static final Logger log = Logger.getLogger(UserMailDelegator.class.getName());
	private static final String EMAIL = "noreply@theuniversalwebservice" + ".appspotmail.com";
	private static final String HTMLFile = "/pwMail.html";

	@Override
	public void initialize(IDelegatorInput input) {
		super.initialize(input);
	}

	@Override
	public void sendMailTo(String userName) {
		log.info("MAIL Sending action triggered for User Password");
		try {
			GAEUserEntity foundUser = userDAOImpl.findEntityById(userName);
			if (foundUser != null) {
				Properties props = new Properties();
				Session session = Session.getDefaultInstance(props);
				Message msg = new MimeMessage(session);
				msg.setFrom(new InternetAddress(EMAIL, "Admin"));
				msg.addRecipient(Message.RecipientType.TO, new InternetAddress(foundUser.getKey(), foundUser.getUserName()));
				msg.setSubject("Your userdata found for the universalservice");
				msg.setContent(HtmlMapper.parsePwResetHtml(foundUser, HTMLFile), "text/html");
				Transport.send(msg);
				delegatorOutput.setStatusCode(ErrorConstants.SEND_MAIL_SUCESS_CODE);
				delegatorOutput.setStatusMessage(ErrorConstants.SEND_MAIL_SUCCESS_MSG + " '" + userName + "'");
				delegatorOutput.setOutputObject(null);
				log.log(Level.INFO, ErrorConstants.SEND_MAIL_SUCCESS_MSG + " '" + userName + "'");
			} else {
				delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_USER_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ErrorConstants.SEND_MAIL_USER_NOT_FOUND + " '" + userName + "'");
				delegatorOutput.setOutputObject(null);
				log.log(Level.SEVERE, ErrorConstants.SEND_MAIL_USER_NOT_FOUND + " '" + userName + "'");
			}
		} catch (EntityRetrievalException e) {
			delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_USER_FAILURE_CODE);
			delegatorOutput.setStatusMessage(ErrorConstants.RETRIEVE_USER_FAILURE_MSG);
			delegatorOutput.setOutputObject(null);
			log.log(Level.SEVERE, "Error while trying to retrieve user '" + userName + "'", e);
		} catch (UnsupportedEncodingException | MessagingException | HtmlReaderException e) {
			setMailSendFailDelegatorOutput(userName);
			log.log(Level.SEVERE, "Error while sending mail to user '" + userName + "' from sender '" + EMAIL + "'", e);
		}
	}
}
