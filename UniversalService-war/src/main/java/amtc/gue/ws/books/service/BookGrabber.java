package amtc.gue.ws.books.service;

import javax.jws.WebService;
import javax.persistence.EntityManagerFactory;

import amtc.gue.ws.books.delegate.persist.AbstractPersistanceDelegator;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.persistence.EMF;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

@WebService(endpointInterface = "amtc.gue.ws.books.service.IBookGrabber")
public class BookGrabber implements IBookGrabber{

	// delegator instance
	// TODO: Spring verified
	private AbstractPersistanceDelegator bpd = 
			(BookPersistenceDelegator) SpringContext.context.getBean("bookPersistenceDelegator");
	
	// EntityManagerFactoy
	private EntityManagerFactory emf = EMF.getEntityManagerFactory();
	
	/**
	 * Method adding books to the bookstore
	 */
	@Override
	public String addBooks(Books items) {

		// setup input object
		// TODO: Spring improval
		//PersistenceDelegatorInput input = new PersistenceDelegatorInput();
		PersistenceDelegatorInput input = 
				(PersistenceDelegatorInput) SpringContext.context.getBean("persistenceDelegatorInput");
		
		input.setEmf(emf);
		input.setType(PersistenceTypeEnum.ADD);
		input.setInputObject(items);
		
		// intialize BookPersistenceDelegator
		bpd.initialize(input);
		
		// call BookPersistenceDelegators delegate method to handle persist
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
