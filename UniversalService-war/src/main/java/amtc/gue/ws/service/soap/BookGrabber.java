package amtc.gue.ws.service.soap;

import java.util.logging.Logger;

import javax.jws.WebService;

import amtc.gue.ws.base.delegate.input.DelegatorInput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.TagPersistenceDelegator;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.response.BookServiceResponse;
import amtc.gue.ws.books.response.TagServiceResponse;
import amtc.gue.ws.books.util.BookServiceEntityMapper;

/**
 * SOAP Webservice implementation
 * 
 * @author Thomas
 *
 */
@WebService(endpointInterface = "amtc.gue.ws.books.service.IBookGrabber")
public class BookGrabber implements IBookGrabber {

	// Logger
	protected static final Logger log = Logger.getLogger(BookGrabber.class
			.getName());

	// delegator instances
	private final AbstractPersistenceDelegator bpd = (BookPersistenceDelegator) SpringContext.context
			.getBean("bookPersistenceDelegator");
	private final AbstractPersistenceDelegator tpd = (TagPersistenceDelegator) SpringContext.context
			.getBean("tagPersistenceDelegator");

	private DelegatorInput input;

	@Override
	public BookServiceResponse addBooks(Books items) {
		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(bpd, DelegatorTypeEnum.ADD,
				items);

		// call BookPersistenceDelegators delegate method to handle persist
		IDelegatorOutput bpdOutput = bpd.delegate();

		// return delegator output mapped to serviceresponse
		return BookServiceEntityMapper
				.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@Override
	public BookServiceResponse getBooksByTag(Tags tags) {
		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(bpd, DelegatorTypeEnum.READ,
				tags);

		// call BookPersistenceDelegators delegate method to handle retrieval of
		// existing books
		IDelegatorOutput bpdOutput = bpd.delegate();

		// return delegator output mapped to serviceresponse
		return BookServiceEntityMapper
				.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@Override
	public BookServiceResponse removeBooks(Books booksToRemove) {
		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(bpd, DelegatorTypeEnum.DELETE,
				booksToRemove);

		// call BookPersistenceDelegators delegate method to handle removal of
		// existing books
		IDelegatorOutput bpdOutput = bpd.delegate();

		// return delegator output mapped to serviceresponse
		return BookServiceEntityMapper
				.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@Override
	public TagServiceResponse getTags() {
		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(tpd, DelegatorTypeEnum.READ,
				null);

		// call TagPersistenceDelegators delegate method to handle retrieval of
		// existing tags
		IDelegatorOutput bpdOutput = tpd.delegate();

		// return delegator output mapped to serviceresponse
		return BookServiceEntityMapper
				.mapBdOutputToTagServiceResponse(bpdOutput);
	}

	/**
	 * Method setting up the respective delegator
	 * 
	 * @param apd
	 *            the delegator object that should be intitialized
	 * @param type
	 *            the input type
	 * @param inputObject
	 *            the input object
	 */
	private void buildAndInitializePersistenceDelegator(
			AbstractPersistenceDelegator apd, DelegatorTypeEnum type,
			Object inputObject) {
		// setup input object and DAO implementations
		input = (DelegatorInput) SpringContext.context
				.getBean("delegatorInput");

		input.setType(type);
		input.setInputObject(inputObject);

		// intialize BookPersistenceDelegator
		apd.initialize(input);
	}

}
