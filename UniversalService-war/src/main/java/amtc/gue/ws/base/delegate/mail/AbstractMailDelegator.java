package amtc.gue.ws.base.delegate.mail;

import amtc.gue.ws.base.delegate.AbstractDelegator;
import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.base.util.SpringContext;

/**
 * Abstract Mail Delegator Class
 * 
 * @author Thomas
 *
 */
public abstract class AbstractMailDelegator extends AbstractDelegator {

	@Override
	public IDelegatorOutput delegate() {
		delegatorOutput = (DelegatorOutput) SpringContext.context
				.getBean("delegatorOutput");
		if (delegatorInput != null) {
			if (delegatorInput.getType().equals(DelegatorTypeEnum.MAIL)
					&& delegatorInput.getInputObject() instanceof String) {
				sendMailTo((String) delegatorInput.getInputObject());
			} else {
				setUnrecognizedDelegatorOutput();
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}

		return delegatorOutput;
	}

	/**
	 * Method sending an email to persisted email address for user
	 */
	protected abstract void sendMailTo(String email);

	/**
	 * Method setting the delegator output due to mail sending failure
	 * 
	 * @param userName
	 *            the userName that was used as mail recipient
	 */
	protected void setMailSendFailDelegatorOutput(String userName) {
		delegatorOutput.setStatusCode(ErrorConstants.SEND_MAIL_FAILURE_CODE);
		delegatorOutput.setStatusMessage(ErrorConstants.SEND_MAIL_FAILURE_MSG
				+ " '" + userName + "'");
	}
}
