package amtc.gue.ws.base.delegate.input;

import amtc.gue.ws.base.util.DelegatorTypeEnum;

/**
 * Inputobject for Persistencedelegators
 * 
 * @author Thomas
 *
 */
public class DelegatorInput implements IDelegatorInput {

	private DelegatorTypeEnum type;
	private Object inputObject;

	@Override
	public DelegatorTypeEnum getType() {
		return this.type;
	}

	@Override
	public void setType(DelegatorTypeEnum type) {
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
