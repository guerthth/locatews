package amtc.gue.ws.books.delegate;

import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.output.IDelegatorOutput;

/**
 * Delegator Interface
 * @author Thomas
 *
 */
public interface IDelegator {
	
	/**
	 * Delegate method persisting object to the underlying DB
	 * @return 
	 * @throws EntityPersistenceException 
	 */
	public IDelegatorOutput delegate() throws EntityPersistenceException;
	
}
