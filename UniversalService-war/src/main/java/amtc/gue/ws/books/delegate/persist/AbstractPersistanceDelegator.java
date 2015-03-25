package amtc.gue.ws.books.delegate.persist;

import javax.persistence.EntityManagerFactory;

import amtc.gue.ws.books.delegate.IDelegator;
import amtc.gue.ws.books.persistence.EMF;

/**
 * Abstract Persistance Delegator class
 * @author Thomas
 *
 */
public abstract class AbstractPersistanceDelegator implements IDelegator{

	// EntityManagerFactoy
	protected EntityManagerFactory emf;
	
	// Object to persist
	protected Object obj; 
	
	/**
	 * initialize EntitiyManagerFactory Instance
	 */
	public void initialize(Object obj){
		
		this.emf = EMF.getEntityManagerFactory();
		this.obj = obj; 
	}
	
	/**
	 * Delegate method persisting object to the underlying DB
	 */
	public abstract void delegate();

}
