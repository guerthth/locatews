package amtc.gue.ws.books.service;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import amtc.gue.ws.books.service.inout.Books;

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
	 * @return success or error message
	 */
	@WebMethod(operationName = "addBooks")
	public String addBooks(@WebParam(name = "inputItems") Books items);
	
	/**
	 * Method retrieving items by criterie
	 * @param items
	 * @return list of items
	 */
	@WebMethod(operationName = "getBooksByTag")
	public Books getBooksByTag(@WebParam(name = "searchTags") String[] tags);
	
}
