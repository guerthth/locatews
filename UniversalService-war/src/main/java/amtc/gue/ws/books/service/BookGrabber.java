package amtc.gue.ws.books.service;

import java.util.logging.Logger;

import javax.jws.WebService;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.dao.book.impl.BookDAOImpl;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.service.inout.output.BookServiceResponse;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

@WebService(endpointInterface = "amtc.gue.ws.books.service.IBookGrabber")
// @HandlerChain(file="handler-chain.xml")
public class BookGrabber implements IBookGrabber {

	// Logger
	protected static final Logger log = Logger.getLogger(BookGrabber.class
			.getName());

	// delegator instance
	private final AbstractPersistenceDelegator bpd = (BookPersistenceDelegator) SpringContext.context
			.getBean("bookPersistenceDelegator");

	private PersistenceDelegatorInput input;
	private BookDAO bookEntityDAO;

	/**
	 * Method adding books to the bookstore
	 */
	@Override
	public BookServiceResponse addBooks(Books items) {

		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(PersistenceTypeEnum.ADD, items);

		// call BookPersistenceDelegators delegate method to handle persist
		IDelegatorOutput bpdOutput = bpd.delegate();

		// return delegator output mapped to serviceresponse
		return EntityMapper.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	/**
	 * Method retrieving books from the book store searchcriteria: 1 to n tags
	 */
	@Override
	public BookServiceResponse getBooksByTag(Tags tags) {

		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(PersistenceTypeEnum.READ, tags);

		// call BookPersistenceDelegators delegate method to handle retrieval of
		// existing books
		IDelegatorOutput bpdOutput = bpd.delegate();

		// return delegator output mapped to serviceresponse
		return EntityMapper.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	/**
	 * Method removing specified books from the book store
	 */
	@Override
	public BookServiceResponse removeBooks(Books booksToRemove) {

		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(PersistenceTypeEnum.DELETE,
				booksToRemove);

		// call BookPersistenceDelegators delegate method to handle removal of
		// existing books
		IDelegatorOutput bpdOutput = bpd.delegate();

		// return delegator output mapped to serviceresponse
		return EntityMapper.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	/**
	 * Method setting up the respective delegator
	 * 
	 * @param type
	 *            the input type
	 * @param input
	 *            the input object
	 */
	private void buildAndInitializePersistenceDelegator(
			PersistenceTypeEnum type, Object inputObject) {
		// setup input object and DAO implementation
		input = (PersistenceDelegatorInput) SpringContext.context
				.getBean("persistenceDelegatorInput");
		bookEntityDAO = (BookDAOImpl) SpringContext.context
				.getBean("bookDAOImpl");

		input.setType(type);
		input.setInputObject(inputObject);

		// intialize BookPersistenceDelegator
		bpd.initialize(input, bookEntityDAO);
	}

}
