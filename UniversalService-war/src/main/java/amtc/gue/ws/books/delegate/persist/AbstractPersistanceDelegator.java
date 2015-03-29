package amtc.gue.ws.books.delegate.persist;

import java.util.logging.Logger;

import amtc.gue.ws.books.delegate.IDelegator;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;

/**
 * Abstract Persistance Delegator class
 * @author Thomas
 *
 */
public abstract class AbstractPersistanceDelegator implements IDelegator{

	// Logger
	protected static final Logger log = 
			Logger.getLogger(AbstractPersistanceDelegator.class.getName());
	
	/** PersistenceDelegatorInput */
	protected PersistenceDelegatorInput persistenceInput;
	
	/**
	 * initialize EntitiyManagerFactory Instance
	 */
	public void initialize(PersistenceDelegatorInput input){
		
		// set persistenceDelegatorInput
		this.persistenceInput = input;
			
	}
	
	/**
	 * Delegate method persisting object to the underlying DB
	 */
	public abstract void delegate();

}
