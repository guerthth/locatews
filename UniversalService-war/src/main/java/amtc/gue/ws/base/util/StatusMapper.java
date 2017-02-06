package amtc.gue.ws.base.util;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.inout.Status;

/**
 * Class mapping various input objects to Status objects
 * 
 * @author Thomas
 *
 */
public class StatusMapper {

	/**
	 * Method mapping a delegatoroutput to a Status object
	 * 
	 * @param bdOutput
	 *            the delegatoroutput that should be mapped
	 * @return the created Status object
	 * 
	 */
	public static Status buildStatusForDelegatorOutput(IDelegatorOutput bdOutput) {
		Status status = null;
		if (bdOutput != null) {
			status = new Status();
			status.setStatusMessage(bdOutput.getStatusMessage());
			status.setStatusCode(bdOutput.getStatusCode());
		}
		return status;
	}
}
