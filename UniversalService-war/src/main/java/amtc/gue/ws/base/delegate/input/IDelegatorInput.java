package amtc.gue.ws.base.delegate.input;

import amtc.gue.ws.base.util.DelegatorTypeEnum;

/**
 * Delegator Input Object interface
 * 
 * @author Thomas
 *
 */
public interface IDelegatorInput {

	/**
	 * Getter for the passed InputObject
	 * 
	 * @return used InputObject
	 */
	public Object getInputObject();

	/**
	 * Setter for the passed InputObject
	 * 
	 * @param inputObject
	 *            the passed inputObject
	 */
	public void setInputObject(Object inputObject);

	/**
	 * Getter for input type
	 * 
	 * @return the getter type
	 */
	public DelegatorTypeEnum getType();

	/**
	 * Setter for the input type
	 * 
	 * @param type
	 *            the getter type
	 */
	public void setType(DelegatorTypeEnum type);

}
