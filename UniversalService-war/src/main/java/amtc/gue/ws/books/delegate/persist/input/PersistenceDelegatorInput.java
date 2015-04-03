package amtc.gue.ws.books.delegate.persist.input;

import amtc.gue.ws.books.delegate.IDelegatorInput;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;

/**
 * Inputobject for Persistencedelegators
 * 
 * @author Thomas
 *
 */
public class PersistenceDelegatorInput implements IDelegatorInput{

	// TODO: type of inputObject could already be Books here. Check
	
	/**
	 * type of the delegatorinput
	 * possible types: create, read, update, delete
	 */
	private PersistenceTypeEnum type;
	
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

	@Override
	public Object getInputObject() {
		
		return inputObject;
	}

	@Override
	public void setInputObject(Object inputObject) {
		
		this.inputObject = inputObject;
	}
	
	
	
}
