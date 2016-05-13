package amtc.gue.ws.books.servlet;

import java.io.ByteArrayOutputStream;
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

import amtc.gue.ws.books.service.BookGrabber;
import amtc.gue.ws.books.service.IBookGrabber;
import amtc.gue.ws.books.service.jaxws.AddBooks;
import amtc.gue.ws.books.service.jaxws.AddBooksResponse;
import amtc.gue.ws.books.service.jaxws.GetBooksByTag;
import amtc.gue.ws.books.service.jaxws.GetBooksByTagResponse;
import amtc.gue.ws.books.service.jaxws.GetTags;
import amtc.gue.ws.books.service.jaxws.GetTagsResponse;
import amtc.gue.ws.books.service.jaxws.RemoveBooks;
import amtc.gue.ws.books.service.jaxws.RemoveBooksResponse;

public class BookCalcServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// logger
	private static final Logger log = Logger.getLogger(BookCalcServlet.class
			.getName());

	/** namespace for request elements */
	public static final String NS = "http://service.books.ws.gue.amtc/";
	/** qualified name for sayHello operation request element */
	public static final QName QNAME_ADD_BOOKS = new QName(NS, "addBooks");
	public static final QName QNAME_GET_BOOKS_BY_TAG = new QName(NS,
			"getBooksByTag");
	public static final QName QNAME_REMOVE_BOOKS = new QName(NS, "removeBooks");
	public static final QName QNAME_GET_TAGS = new QName(NS, "getTags");

	private static final String INCOMING_SOAP_REQUEST = "Incoming SOAP Request: ";
	private static final String OUTGOING_SOAP_RESPONSE = "Outgoing SOAP Response: ";

	private final IBookGrabber serviceImpl = new BookGrabber();

	private static final MessageFactory messageFactory;

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			MimeHeaders headers = getHeaders(request);
			InputStream in = request.getInputStream();
			SOAPMessage soapReq = messageFactory.createMessage(headers, in);
			logMessage(soapReq, true);
			SOAPMessage soapResp = handleSOAPRequest(soapReq);
			logMessage(soapResp, false);
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("text/xml;charset=\"UTF-8\"");
			addCORSHeaders(response);
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
		@SuppressWarnings("unchecked")
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
		@SuppressWarnings("rawtypes")
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
		if (QNAME_ADD_BOOKS.equals(reqName)) {
			log.info("Adding books webservice method called.");
			return handleAddBooks(JAXB.unmarshal(new DOMSource(reqElem),
					AddBooks.class));
		} else if (QNAME_GET_BOOKS_BY_TAG.equals(reqName)) {
			log.info("getting books by tags webservice method called.");
			return handleGetBooksByTags(JAXB.unmarshal(new DOMSource(reqElem),
					GetBooksByTag.class));
		} else if (QNAME_REMOVE_BOOKS.equals(reqName)) {
			return handleRemoveBooks(JAXB.unmarshal(new DOMSource(reqElem),
					RemoveBooks.class));
		} else if (QNAME_GET_TAGS.equals(reqName)) {
			return handleGetTags(JAXB.unmarshal(new DOMSource(reqElem),
					GetTags.class));
		}

		return null;
	}

	/**
	 * Method handling a AddBooks request
	 * 
	 * @param addBooksRequest
	 *            the AddBooks request that should be handled
	 * @return the AddBooks response
	 */
	protected AddBooksResponse handleAddBooks(AddBooks addBooksRequest) {
		AddBooksResponse addBooksReesponse = new AddBooksResponse();
		addBooksReesponse.setReturn(serviceImpl.addBooks(addBooksRequest
				.getBooks()));
		return addBooksReesponse;
	}

	/**
	 * Method handling a GetBooksByTag request
	 * 
	 * @param getBooksByTagRequest
	 *            the GetBooksByTag request that should be handled
	 * @return the GetBooksByTag response
	 */
	protected GetBooksByTagResponse handleGetBooksByTags(
			GetBooksByTag getBooksByTagRequest) {
		GetBooksByTagResponse getBooksByTagResponse = new GetBooksByTagResponse();
		getBooksByTagResponse.setReturn(serviceImpl
				.getBooksByTag(getBooksByTagRequest.getSearchTags()));
		return getBooksByTagResponse;
	}

	/**
	 * Method handling a RemoveBooks request
	 * 
	 * @param removeBooksRequest
	 *            the RemoveBooks request that should be handled
	 * @return the RemoveBooks response
	 */
	protected RemoveBooksResponse handleRemoveBooks(
			RemoveBooks removeBooksRequest) {
		RemoveBooksResponse removeBooksResponse = new RemoveBooksResponse();
		removeBooksResponse.setReturn(serviceImpl
				.removeBooks(removeBooksRequest.getBooksToRemove()));
		return removeBooksResponse;
	}

	/**
	 * Method handling a GetTags request
	 * 
	 * @param getTagsRequest
	 *            the GetTags request that should be handled
	 * @return the GetTags response
	 */
	protected GetTagsResponse handleGetTags(GetTags getTagsRequest) {
		GetTagsResponse getTagsResponse = new GetTagsResponse();
		getTagsResponse.setReturn(serviceImpl.getTags());
		return getTagsResponse;
	}

	/**
	 * Method logging a soap message
	 * 
	 * @param soapMessage
	 *            the soapMessage (request or response that should be logged)
	 * @param isRequest
	 *            boolean flag specifying if the soapmessage is a request or
	 *            response
	 */
	private void logMessage(SOAPMessage soapMessage, boolean isRequest) {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		StringBuilder soapMessageStringBuilder = new StringBuilder();
		try {
			soapMessage.writeTo(bout);
			if (isRequest) {
				soapMessageStringBuilder.append(INCOMING_SOAP_REQUEST);
			} else {
				soapMessageStringBuilder.append(OUTGOING_SOAP_RESPONSE);
			}
			soapMessageStringBuilder.append(bout.toString("UTF-8"));
		} catch (SOAPException | IOException e) {
			soapMessageStringBuilder = new StringBuilder();
			soapMessageStringBuilder
					.append("Error while processing SOAPMessage");
		}
		log.info(soapMessageStringBuilder.toString());
	}

	@Override
	public void doOptions(HttpServletRequest req, HttpServletResponse resp) {
		// The following are CORS headers. Max age informs the
		// browser to keep the results of this call for 1 day.
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST");
		resp.setHeader("Access-Control-Allow-Headers",
				"Content-Type,soapaction");
		resp.setHeader("Access-Control-Max-Age", "86400");
		// Tell the browser what requests we allow.
		resp.setHeader("Allow", "GET, HEAD, POST, TRACE, OPTIONS");
	}

	/**
	 * MEthod adding headers to enable cross origin resource sharing
	 * 
	 * @param response
	 *            the intitial response
	 * @return the response with CORS headers
	 */
	protected HttpServletResponse addCORSHeaders(HttpServletResponse response) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		// response.addHeader("Access-Control-Allow-Headers",
		// "Content-Type, Authorization, Accept, soapaction");
		response.addHeader("Access-Control-Allow-Headers",
				"Content-Type, soapaction");
		response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
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
