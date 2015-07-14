package amtc.gue.ws.books.service;

import java.util.logging.Logger;

import javax.jws.WebService;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.AbstractPersistanceDelegator;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.service.inout.output.BookServiceResponse;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

@WebService(endpointInterface = "amtc.gue.ws.books.service.IBookGrabber")
public class BookGrabber implements IBookGrabber{

	// Logger
	protected static final Logger log = 
			Logger.getLogger(BookGrabber.class.getName());
	
	// delegator instance
	private final AbstractPersistanceDelegator bpd = 
			(BookPersistenceDelegator) SpringContext.context.getBean("bookPersistenceDelegator");
			
	
	/**
	 * Method adding books to the bookstore
	 */
	@Override
	public BookServiceResponse addBooks(Books items) {
		
		// setup input object
		PersistenceDelegatorInput input = 
				(PersistenceDelegatorInput) SpringContext.context.getBean("persistenceDelegatorInput");
		
		input.setType(PersistenceTypeEnum.ADD);
		input.setInputObject(items);
		
		// intialize BookPersistenceDelegator
		bpd.initialize(input);
		
		// call BookPersistenceDelegators delegate method to handle persist
		IDelegatorOutput bpdOutput = bpd.delegate();
		
		// map delegator output to ServiceResponse
		BookServiceResponse serviceResponse = EntityMapper.mapBdOutputToServiceResponse(bpdOutput);
		
		return serviceResponse;
	}

	/**
	 * Method retrieving books from the book store
	 * searchcriteria: 1 to n tags
	 */
	@Override
	public BookServiceResponse getBooksByTag(Tags tags) {
		
		// setup input object
		PersistenceDelegatorInput input = 
				(PersistenceDelegatorInput) SpringContext.context.getBean("persistenceDelegatorInput");
		
		input.setType(PersistenceTypeEnum.READ);
		input.setInputObject(tags);
		
		// initialize BookPersistenceDelegator
		bpd.initialize(input);
		
		// call BookPersistencedelegators delegate method to handle retrieval of existing books
		IDelegatorOutput bpdOutput = bpd.delegate();
		
		// map delegator output to ServiceResponse
		BookServiceResponse serviceResponse = EntityMapper.mapBdOutputToServiceResponse(bpdOutput);
		
		return serviceResponse;
	}	
	
}
