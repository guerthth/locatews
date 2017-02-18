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
	 * @param dOutput
	 *            the delegatoroutput that should be mapped
	 * @return the created Status object
	 * 
	 */
	public static Status buildStatusForDelegatorOutput(IDelegatorOutput dOutput) {
		Status status = null;
		if (dOutput != null) {
			status = new Status();
			status.setStatusMessage(dOutput.getStatusMessage());
			status.setStatusCode(dOutput.getStatusCode());
		}
		return status;
	}
}
