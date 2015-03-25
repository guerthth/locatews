package amtc.gue.ws.books.service;

import javax.jws.WebService;

import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.inout.Books;

@WebService(endpointInterface = "amtc.gue.ws.books.service.IBookGrabber")
public class BookGrabber implements IBookGrabber{

	// delegator instance
	private final BookPersistenceDelegator bpd = new BookPersistenceDelegator();
	
	/**
	 * Method adding books to the bookstore
	 */
	@Override
	public String addBooks(Books items) {
		
		// Add book to the underlying DB
		bpd.initialize(items);
		bpd.delegate();
		
		return "OK";
	}

	@Override
	public Books getBooksByTag(String[] tags) {
		
		/**
		 * Method retrieving book from the book store
		 * searchcriteria: 1 to n tags
		 */
		// TODO: improve
		return null;
	}	
	
}
