package amtc.gue.ws.books.delegate.persist.input;

import javax.persistence.EntityManagerFactory;

import amtc.gue.ws.books.utils.PersistenceTypeEnum;

/**
 * Inputobject for Persistencedelegators
 * 
 * @author Thomas
 *
 */
public class PersistenceDelegatorInput implements IDelegatorInput{

	/**
	 * type of the delegatorinput
	 * possible types: create, read, update, delete
	 */
	private PersistenceTypeEnum type;
	
	/** emf instance */
	private EntityManagerFactory emf;
	
	/** input object */
	private Object inputObject; 

	/**
	 * Getter for input type
	 * 
	 * @return the getter type
	 */
	public PersistenceTypeEnum getType() {
		return type;
	}

	/**
	 * Setter for the input type
	 * 
	 * @param type the getter type
	 */
	public void setType(PersistenceTypeEnum type) {
		this.type = type;
	}
	
	/**
	 * Getter for the EntityManagerFactory instance
	 * @return instance of the EntityManagerFactory
	 */
	public EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Setter for the EntityManagerFactory instance
	 * 
	 * @param emf instance of the EntityManagerFactory
	 */
	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	@Override
	public Object getInputObject() {
		
		return inputObject;
	}

	@Override
	public void setInputObject(Object inputObject) {
		
		this.inputObject = inputObject;
	}
	
	
	
}
