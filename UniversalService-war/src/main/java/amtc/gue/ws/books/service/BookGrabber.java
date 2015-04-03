package amtc.gue.ws.books.service;

import java.util.logging.Logger;

import javax.jws.WebService;












import amtc.gue.ws.books.delegate.persist.AbstractPersistanceDelegator;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

@WebService(endpointInterface = "amtc.gue.ws.books.service.IBookGrabber")
public class BookGrabber implements IBookGrabber{

	// Logger
	protected static final Logger log = 
			Logger.getLogger(BookGrabber.class.getName());
	
	// delegator instance
	private AbstractPersistanceDelegator bpd = (BookPersistenceDelegator) SpringContext.context.getBean("bookPersistenceDelegator");
			
	
	/**
	 * Method adding books to the bookstore
	 */
	@Override
	public String addBooks(Books items) {
		
		// setup input object
		PersistenceDelegatorInput input = 
				(PersistenceDelegatorInput) SpringContext.context.getBean("persistenceDelegatorInput");
		
		input.setType(PersistenceTypeEnum.ADD);
		input.setInputObject(items);
		
		// intialize BookPersistenceDelegator
		bpd.initialize(input);
		
		try {
			
			// call BookPersistenceDelegators delegate method to handle persist
			bpd.delegate();
			
		} catch (EntityPersistenceException e) {
			
			log.severe(e.getMessage());
			log.severe(e.getStackTrace().toString());
			e.printStackTrace();
		}
		
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
