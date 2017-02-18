package amtc.gue.ws.vcsapi.delegate;

import amtc.gue.ws.base.delegate.AbstractDelegator;
import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;

/**
 * Abstract API Delegator Class
 * 
 * @author Thomas
 *
 */
public abstract class AbstractAPIDelegator extends AbstractDelegator {

	@Override
	public IDelegatorOutput delegate() {
		delegatorOutput = (DelegatorOutput) SpringContext.context.getBean("delegatorOutput");
		if (delegatorInput != null) {
			if (delegatorInput.getType().equals(DelegatorTypeEnum.ADD)) {
				addIssue();
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}

		return delegatorOutput;
	}

	/**
	 * Method adding an issue using a specific API
	 * 
	 */
	protected abstract void addIssue();
}
