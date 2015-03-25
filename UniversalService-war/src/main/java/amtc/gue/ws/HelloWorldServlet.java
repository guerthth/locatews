package amtc.gue.ws;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SAAJResult;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;

import amtc.gue.ws.jaxws.SayHello;
import amtc.gue.ws.jaxws.SayHelloResponse;

public class HelloWorldServlet extends HttpServlet {
	
	// logger
	private static final Logger log = Logger.getLogger(HelloWorldServlet.class.getName());
	
	/** namespace for request elements */
	public static final String NS = "http://ws.gue.amtc/";
	/** qualified name for sayHello operation request element */
	public static final QName QNAME_SAY_HELLO = new QName(NS, "sayHello");
	public static final QName QNAME_OTHER = new QName(NS, "otherOperation");

	private final HelloWorld serviceImpl = new HelloWorld();

	private static final MessageFactory messageFactory;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		log.info("Request in HelloWorldServlet received.");
		
		try {
			MimeHeaders headers = getHeaders(request);
			InputStream in = request.getInputStream();
			SOAPMessage soapReq = messageFactory.createMessage(headers, in);
			SOAPMessage soapResp = handleSOAPRequest(soapReq);
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/xml;charset=\"UTF-8\"");
			OutputStream out = response.getOutputStream();
			soapResp.writeTo(out);
			out.flush();
		} catch (SOAPException e) {
			throw new IOException("exception while creating SOAP message", e);
		} catch (JAXBException e) {
			throw new IOException("exception while creating SOAP message", e);
		}
	}

	public MimeHeaders getHeaders(HttpServletRequest request) {
		MimeHeaders retval = new MimeHeaders();
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			String value = request.getHeader(name);
			StringTokenizer values = new StringTokenizer(value, ",");
			while (values.hasMoreTokens()) {
				retval.addHeader(name, values.nextToken().trim());
			}
		}
		return retval;
	}

	protected SOAPMessage handleSOAPRequest(SOAPMessage request)
			throws SOAPException, JAXBException {
		Iterator iter = request.getSOAPBody().getChildElements();
		Object respPojo = null;
		while (iter.hasNext()) {
			
			log.info("Iterating through child elements.");
			
			// find first Element child
			Object child = iter.next();
			if (child instanceof SOAPElement) {
				log.info("SOAP element found!: " + child);
				respPojo = handleSOAPRequestElement((SOAPElement) child);
				break;
			}
		}
		SOAPMessage soapResp = messageFactory.createMessage();
		SOAPBody respBody = soapResp.getSOAPBody();
		if (respPojo != null) {
			JAXB.marshal(respPojo, new SAAJResult(respBody));
		} else {
			SOAPFault fault = respBody.addFault();
			fault.setFaultString("Unknown SOAP request");
		}
		return soapResp;
	}

	protected Object handleSOAPRequestElement(SOAPElement reqElem)
			throws JAXBException {
		
		QName reqName = reqElem.getElementQName();
		if (QNAME_SAY_HELLO.equals(reqName)) {
			
			log.info("SOAP element is of type SayHello. Start building SayHelloResponse.");
			
			return handleSayHello(JAXB.unmarshal(new DOMSource(reqElem),
					SayHello.class));
		} else if (QNAME_OTHER.equals(reqName)) {
			// implement other WS operations like this
		}
		return null;
	}

	protected SayHelloResponse handleSayHello(SayHello request) {
		
		log.info("Building SayHelloResponse. With: " + request);
		
		SayHelloResponse response = new SayHelloResponse();
		response.setReturn(serviceImpl.sayHello(request.getPerson()));
		return response;
	}

	static {
		try {
			messageFactory = MessageFactory.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
