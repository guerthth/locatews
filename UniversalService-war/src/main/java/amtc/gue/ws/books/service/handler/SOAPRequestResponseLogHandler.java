package amtc.gue.ws.books.service.handler;

import java.util.Collections;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import amtc.gue.ws.books.service.BookGrabber;

/**
 * Handler class responsible for logging all SOAP requests received by and all
 * SOAP responses returned by the webservice
 * 
 * @author Thomas
 *
 */
public class SOAPRequestResponseLogHandler implements
		SOAPHandler<SOAPMessageContext> {

	public static final Logger log = Logger.getLogger(BookGrabber.class
			.getName());
	private static final String INCOMING_SOAP_REQUEST = "Incoming SOAP Request: ";
	private static final String OUTGOING_SOAP_RESPONSE = "Outgoing SOAP Response: ";

	@Override
	public void close(MessageContext context) {

	}

	@Override
	public boolean handleFault(SOAPMessageContext context) {
		return true;
	}

	@Override
	public boolean handleMessage(SOAPMessageContext context) {
		Boolean outboundProperty = (Boolean) context
				.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
		StringBuilder soapLogBuilder = new StringBuilder();
		if (outboundProperty.booleanValue()) {
			soapLogBuilder.append(OUTGOING_SOAP_RESPONSE);
		} else {
			soapLogBuilder.append(INCOMING_SOAP_REQUEST);
		}
		soapLogBuilder.append(context.getMessage());
		log.info(soapLogBuilder.toString());
		return true;
	}

	@Override
	public Set<QName> getHeaders() {
		return Collections.emptySet();
	}

}
