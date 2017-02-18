package amtc.gue.ws.base.delegate;

import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.input.DelegatorInput;
import amtc.gue.ws.base.delegate.input.IDelegatorInput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.base.util.SpringContext;

/**
 * Abstract delegator Class
 * 
 * @author Thomas
 *
 */
public abstract class AbstractDelegator {

	protected static final Logger log = Logger.getLogger(AbstractDelegator.class.getName());
	protected IDelegatorInput delegatorInput;
	protected IDelegatorOutput delegatorOutput;
	protected User currentUser;
	protected UserDAO userDAOImpl;

	/**
	 * Delegate method persisting object to the underlying DB
	 * 
	 * @return the created IDelegatorOutput
	 */
	public abstract IDelegatorOutput delegate();

	/**
	 * Method initializing the delegatorInput
	 * 
	 * @param input
	 *            the used Input for the delegator
	 */
	public void initialize(IDelegatorInput input) {
		userDAOImpl = (UserDAO) SpringContext.context.getBean("userDAOImpl");
		if (input instanceof DelegatorInput) {
			delegatorInput = input;
		} else {
			delegatorInput = null;
		}
	}

	/**
	 * Delegate method persisting object to the underlying DB
	 * 
	 * @param type
	 *            the input type used for the delegator
	 * @param inputObject
	 *            the input object used in the delegator
	 * @return the created IDelegatorOutput
	 */
	public void buildAndInitializeDelegator(DelegatorTypeEnum type, Object inputObject) {
		delegatorInput = (DelegatorInput) SpringContext.context.getBean("delegatorInput");
		delegatorInput.setType(type);
		delegatorInput.setInputObject(inputObject);
		initialize(delegatorInput);
	}

	/**
	 * Method setting the delegator output due to unrecognized input type
	 */
	public void setUnrecognizedDelegatorOutput() {
		log.severe(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG);
		delegatorOutput.setStatusCode(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE);
		delegatorOutput.setStatusMessage(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG);
	}

	/**
	 * Setter for the current user
	 */
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * Method checking if the currentUser object is set
	 * 
	 * @return true, if the currentUser is not null and possesses an ID or false
	 *         if not
	 */
	public boolean userExists() {
		if (currentUser != null && currentUser.getId() != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Getter for the used userDAOImpl
	 * 
	 * @return the UserDAOImpl object
	 */
	public UserDAO getUserDAO() {
		return userDAOImpl;
	}

	/**
	 * Setter for the used userDAOImpl
	 * 
	 * @param userDAOImpl
	 *            the UserDAOImpl object
	 */
	public void setUserDAO(UserDAO userDAOImpl) {
		this.userDAOImpl = userDAOImpl;
	}
}
