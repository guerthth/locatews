package amtc.gue.ws.service.soap;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.response.BookServiceResponse;
import amtc.gue.ws.books.response.TagServiceResponse;

/**
 * SOAP Webservice interface that can be implemented by several Grabber services
 * 
 * @author Thomas
 *
 */
@WebService
@SOAPBinding(style = Style.DOCUMENT, use = Use.LITERAL)
public interface IBookGrabber {

	/**
	 * Method adding Items to the store
	 * 
	 * @param items
	 *            list of items that shall be added to the store
	 * @return serviceresponse object
	 * 
	 */
	@WebMethod(operationName = "addBooks")
	public BookServiceResponse addBooks(@WebParam(name = "books") Books items);

	/**
	 * Method retrieving books possessing specific tags
	 * 
	 * @param tags
	 *            list of tags the books should possess
	 * @return list of books possessing the tags
	 */
	@WebMethod(operationName = "getBooksByTag")
	public BookServiceResponse getBooksByTag(@WebParam(name = "searchTags") Tags tags);

	/**
	 * Method removing specific books
	 * 
	 * @param booksToRemove
	 *            a list of books that should be removed
	 * @return the books that have been removed
	 */
	@WebMethod(operationName = "removeBooks")
	public BookServiceResponse removeBooks(@WebParam(name = "booksToRemove") Books booksToRemove);

	/**
	 * Method retriebing all existing tags from the datastore
	 * 
	 * @return the found tags
	 */
	@WebMethod(operationName = "getTags")
	public TagServiceResponse getTags();
}
