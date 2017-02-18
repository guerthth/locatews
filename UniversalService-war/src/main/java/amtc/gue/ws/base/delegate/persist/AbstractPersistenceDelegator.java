package amtc.gue.ws.base.delegate.persist;

import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.AbstractDelegator;
import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;

/**
 * Abstract Persistance Delegator class
 * 
 * @author Thomas
 *
 */
public abstract class AbstractPersistenceDelegator extends AbstractDelegator {
	protected static final Logger log = Logger.getLogger(AbstractPersistenceDelegator.class.getName());

	@Override
	public IDelegatorOutput delegate() {
		delegatorOutput = (DelegatorOutput) SpringContext.context.getBean("delegatorOutput");
		if (delegatorInput != null) {
			if (delegatorInput.getType().equals(DelegatorTypeEnum.ADD)) {
				persistEntities();
			} else if (delegatorInput.getType().equals(DelegatorTypeEnum.DELETE)) {
				removeEntities();
			} else if (delegatorInput.getType().equals(DelegatorTypeEnum.READ)) {
				retrieveEntities();
			} else {
				setUnrecognizedDelegatorOutput();
			}
		} else {
			setUnrecognizedDelegatorOutput();
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
}
