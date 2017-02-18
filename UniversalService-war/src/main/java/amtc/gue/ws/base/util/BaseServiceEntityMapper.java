package amtc.gue.ws.base.util;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.response.ServiceResponse;

/**
 * Class responsible for mapping of general Service related objects examples: -
 * generating ServiceResponse objects
 * 
 * @author Thomas
 *
 */
public class BaseServiceEntityMapper {

	/**
	 * Method mapping a delegatorOutput to a general ServiceResponse
	 * 
	 * @param dOutput
	 *            delegatoroOutput that should be included in the response
	 * @return mapped UserServiceReponse
	 */
	public static ServiceResponse mapBdOutputToServiceResponse(IDelegatorOutput dOutput) {
		ServiceResponse serviceResponse = null;
		if (dOutput != null) {
			serviceResponse = new ServiceResponse();
			serviceResponse.setStatus(StatusMapper.buildStatusForDelegatorOutput(dOutput));
		}
		return serviceResponse;
	}

}
