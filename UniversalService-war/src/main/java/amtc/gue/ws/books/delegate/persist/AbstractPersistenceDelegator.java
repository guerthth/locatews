package amtc.gue.ws.books.delegate.persist;

import java.util.logging.Logger;

import amtc.gue.ws.books.delegate.IDelegator;
import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.utils.ErrorConstants;

/**
 * Abstract Persistance Delegator class
 * @author Thomas
 *
 */
public abstract class AbstractPersistenceDelegator implements IDelegator{

	// Logger
	protected static final Logger log = 
			Logger.getLogger(AbstractPersistenceDelegator.class.getName());
	
	/** PersistenceDelegatorInput */
	protected PersistenceDelegatorInput persistenceInput;
	
	/** DAOImplementation used by the deletator */
	protected BookDAO daoImpl;
	
	/** DelegatorOutput */
	protected IDelegatorOutput delegatorOutput;
	
	/**
	 * initialize EntitiyManagerFactory Instance
	 * 
	 * @param input the input object
	 * @param daoImpl the used DAO Implementation
	 */
	public void initialize(PersistenceDelegatorInput input, BookDAO daoImpl){
		
		// set persistenceDelegatorInput and DAO Implementation
		this.persistenceInput = input;
		this.daoImpl = daoImpl;
			
	}
	
	/**
	 * Delegate method persisting object to the underlying DB
	 * @return 
	 * @throws EntityPersistenceException 
	 * @throws EntityRetrievalException 
	 */
	public abstract IDelegatorOutput delegate();
	
	/**
	 * Method setting the delegator output due to unrecognized input type
	 */
	protected void setUnrecognizedInputDelegatorOutput() {
		log.severe(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG);
		delegatorOutput
				.setStatusCode(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE);
		delegatorOutput
				.setStatusMessage(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG);
	}

}
