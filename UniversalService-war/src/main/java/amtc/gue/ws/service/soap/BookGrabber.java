package amtc.gue.ws.service.soap;

import java.util.logging.Logger;

import javax.jws.WebService;

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
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;

/**
 * SOAP Webservice implementation
 * 
 * @author Thomas
 *
 */
@WebService(endpointInterface = "amtc.gue.ws.books.service.soap.IBookGrabber")
public class BookGrabber implements IBookGrabber {
	protected static final Logger log = Logger.getLogger(BookGrabber.class.getName());
	private AbstractPersistenceDelegator bookDelegator;
	private AbstractPersistenceDelegator tagDelegator;

	public BookGrabber() {
		bookDelegator = (BookPersistenceDelegator) SpringContext.context.getBean("bookPersistenceDelegator");
		tagDelegator = (TagPersistenceDelegator) SpringContext.context.getBean("tagPersistenceDelegator");
	}

	public BookGrabber(AbstractPersistenceDelegator bookDelegator, AbstractPersistenceDelegator tagDelegator) {
		this.bookDelegator = bookDelegator;
		this.tagDelegator = tagDelegator;
	}

	@Override
	public BookServiceResponse addBooks(Books items) {
		// set up the pesistence delegator
		bookDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.ADD, items);

		// call BookPersistenceDelegators delegate method to handle persist
		IDelegatorOutput bpdOutput = bookDelegator.delegate();

		// return delegator output mapped to serviceresponse
		return BookServiceEntityMapper.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@Override
	public BookServiceResponse getBooksByTag(Tags tags) {
		// set up the pesistence delegator
		bookDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, tags);

		// call BookPersistenceDelegators delegate method to handle retrieval of
		// existing books
		IDelegatorOutput bpdOutput = bookDelegator.delegate();

		// return delegator output mapped to serviceresponse
		return BookServiceEntityMapper.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@Override
	public BookServiceResponse removeBooks(Books booksToRemove) {
		// set up the pesistence delegator
		bookDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.DELETE, booksToRemove);

		// call BookPersistenceDelegators delegate method to handle removal of
		// existing books
		IDelegatorOutput bpdOutput = bookDelegator.delegate();

		// return delegator output mapped to serviceresponse
		return BookServiceEntityMapper.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@Override
	public TagServiceResponse getTags() {
		// set up the pesistence delegator
		tagDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, null);

		// call TagPersistenceDelegators delegate method to handle retrieval of
		// existing tags
		IDelegatorOutput bpdOutput = tagDelegator.delegate();

		// return delegator output mapped to serviceresponse
		return BookServiceEntityMapper.mapBdOutputToTagServiceResponse(bpdOutput);
	}
}
