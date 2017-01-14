package amtc.gue.ws.base.delegate.persist;

import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.IDelegator;
import amtc.gue.ws.base.delegate.IDelegatorInput;
import amtc.gue.ws.base.delegate.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.base.delegate.persist.output.PersistenceDelegatorOutput;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.base.util.PersistenceTypeEnum;
import amtc.gue.ws.base.util.SpringContext;

/**
 * Abstract Persistance Delegator class
 * 
 * @author Thomas
 *
 */
public abstract class AbstractPersistenceDelegator implements IDelegator {
	protected static final Logger log = Logger
			.getLogger(AbstractPersistenceDelegator.class.getName());
	protected IDelegatorOutput delegatorOutput;
	protected PersistenceDelegatorInput persistenceInput;
	protected User currentUser;

	/**
	 * Method setting up the respective delegator
	 * 
	 * @param type
	 *            the input type
	 * @param inputObject
	 *            the input object
	 */
	public void buildAndInitializePersistenceDelegator(
			PersistenceTypeEnum type, Object inputObject) {
		// setup input object and DAO implementations
		persistenceInput = (PersistenceDelegatorInput) SpringContext.context
				.getBean("persistenceDelegatorInput");

		persistenceInput.setType(type);
		persistenceInput.setInputObject(inputObject);

		// intialize PersistenceDelegator
		initialize(persistenceInput);
	}

	@Override
	public void initialize(IDelegatorInput input) {
		if (input instanceof PersistenceDelegatorInput) {
			persistenceInput = (PersistenceDelegatorInput) input;
		} else {
			persistenceInput = null;
		}
	}

	@Override
	public IDelegatorOutput delegate() {
		delegatorOutput = (PersistenceDelegatorOutput) SpringContext.context
				.getBean("persistenceDelegatorOutput");
		if (persistenceInput != null) {
			if (persistenceInput.getType().equals(PersistenceTypeEnum.ADD)) {
				persistEntities();
			} else if (persistenceInput.getType().equals(
					PersistenceTypeEnum.DELETE)) {
				removeEntities();
			} else if (persistenceInput.getType().equals(
					PersistenceTypeEnum.READ)) {
				retrieveEntities();
			} else {
				setUnrecognizedInputDelegatorOutput();
			}
		} else {
			setUnrecognizedInputDelegatorOutput();
		}

		return delegatorOutput;
	}

	/**
	 * Method persisting entities to the DB
	 */
	protected abstract void persistEntities();

	/**
	 * Method removing entities from the DB
	 */
	protected abstract void removeEntities();

	/**
	 * Method retrieving entities from the DB
	 */
	protected abstract void retrieveEntities();

	@Override
	public void setUnrecognizedInputDelegatorOutput() {
		log.severe(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG);
		delegatorOutput
				.setStatusCode(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE);
		delegatorOutput
				.setStatusMessage(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG);
	}
	
	/**
	 * Setter for the current user
	 */
	public void setCurrentUser(User currentUser){
		this.currentUser = currentUser;
	}

}
