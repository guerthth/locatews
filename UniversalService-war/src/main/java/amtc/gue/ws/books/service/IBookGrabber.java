package amtc.gue.ws.books.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.service.inout.output.BookServiceResponse;

/**
 * Webservice interface that can be implemented by several 
 * Grabber services
 * @author Thomas
 *
 */
@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public interface IBookGrabber {
	
	/**
	 * Method adding Items to the store
	 * @param items list of items that shall be added to the store
	 * @return serviceresponse object
	 * 
	 */
	@WebMethod(operationName = "addBooks")
	public BookServiceResponse addBooks(@WebParam(name = "inputItems") Books items);
	
	/**
	 * Method retrieving items by criteria
	 * @param items
	 * @return list of items
	 */
	@WebMethod(operationName = "getBooksByTag")
	public BookServiceResponse getBooksByTag(@WebParam(name = "searchTags") Tags tags);
	
}
