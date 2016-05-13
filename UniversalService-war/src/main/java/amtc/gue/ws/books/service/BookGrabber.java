package amtc.gue.ws.books.service;

import java.util.logging.Logger;

import javax.jws.WebService;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.TagPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.books.persistence.dao.DAOs;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.service.inout.output.BookServiceResponse;
import amtc.gue.ws.books.service.inout.output.TagServiceResponse;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

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

	private PersistenceDelegatorInput input;
	private DAOs daos;

	@Override
	public BookServiceResponse addBooks(Books items) {
		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(bpd, PersistenceTypeEnum.ADD,
				items);

		// call BookPersistenceDelegators delegate method to handle persist
		IDelegatorOutput bpdOutput = bpd.delegate();

		// return delegator output mapped to serviceresponse
		return EntityMapper.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@Override
	public BookServiceResponse getBooksByTag(Tags tags) {
		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(bpd, PersistenceTypeEnum.READ,
				tags);

		// call BookPersistenceDelegators delegate method to handle retrieval of
		// existing books
		IDelegatorOutput bpdOutput = bpd.delegate();

		// return delegator output mapped to serviceresponse
		return EntityMapper.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@Override
	public BookServiceResponse removeBooks(Books booksToRemove) {
		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(bpd, PersistenceTypeEnum.DELETE,
				booksToRemove);

		// call BookPersistenceDelegators delegate method to handle removal of
		// existing books
		IDelegatorOutput bpdOutput = bpd.delegate();

		// return delegator output mapped to serviceresponse
		return EntityMapper.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@Override
	public TagServiceResponse getTags() {
		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(tpd, PersistenceTypeEnum.READ,
				null);

		// call TagPersistenceDelegators delegate method to handle retrieval of
		// existing tags
		IDelegatorOutput bpdOutput = tpd.delegate();

		// return delegator output mapped to serviceresponse
		return EntityMapper.mapBdOutputToTagServiceResponse(bpdOutput);
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
			AbstractPersistenceDelegator apd, PersistenceTypeEnum type,
			Object inputObject) {
		// setup input object and DAO implementations
		input = (PersistenceDelegatorInput) SpringContext.context
				.getBean("persistenceDelegatorInput");
		daos = (DAOs) SpringContext.context.getBean("daos");

		input.setType(type);
		input.setInputObject(inputObject);

		// intialize BookPersistenceDelegator
		apd.initialize(input, daos);
	}

}
