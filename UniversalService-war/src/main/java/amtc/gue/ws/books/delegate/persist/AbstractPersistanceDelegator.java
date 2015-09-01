package amtc.gue.ws.books.delegate.persist;

import java.util.logging.Logger;

import amtc.gue.ws.books.delegate.IDelegator;
import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;

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
	
	/** DAOImplementation used by the deletator */
	protected BookDAO daoImpl;
	
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

}
