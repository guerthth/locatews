package amtc.gue.ws.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.service.inout.Book;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.service.inout.output.BookServiceResponse;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookService extends Service{
	protected static final Logger log = Logger.getLogger(BookService.class
			.getName());
	private AbstractPersistenceDelegator bookDelegator = (BookPersistenceDelegator) SpringContext.context
			.getBean("bookPersistenceDelegator");

	public BookService() {

	}

	public BookService(AbstractPersistenceDelegator delegator) {
		this.bookDelegator = delegator;
	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public BookServiceResponse addBooks(Books items) {
		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(bookDelegator,
				PersistenceTypeEnum.ADD, items);

		// call BookPersistenceDelegators delegate method to handle persist
		IDelegatorOutput bpdOutput = bookDelegator.delegate();

		// return delegator output mapped to serviceresponse
		return EntityMapper.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public BookServiceResponse getBooks(@Context UriInfo info) {
		// check for queryparams and propery set tags
		Tags tags = new Tags();
		List<String> searchTag = info.getQueryParameters().get("searchTag");
		if (searchTag != null) {
			tags.setTags(searchTag);
		}

		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(bookDelegator,
				PersistenceTypeEnum.READ, tags);

		// call BookPersistenceDelegators delegate method to handle retrieval of
		// existing books
		IDelegatorOutput bpdOutput = bookDelegator.delegate();

		// return delegator output mapped to serviceresponse
		return EntityMapper.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public BookServiceResponse removeBook(@PathParam("id") String id) {
		// set up the pesistence delegator
		Books booksToRemove = new Books();
		Book bookToRemove = new Book();
		List<Book> bookListToRemove = new ArrayList<Book>();
		bookListToRemove.add(bookToRemove);
		bookToRemove.setId(id);
		booksToRemove.setBooks(bookListToRemove);

		buildAndInitializePersistenceDelegator(bookDelegator,
				PersistenceTypeEnum.DELETE, booksToRemove);

		// call BookPersistenceDelegators delegate method to handle removal of
		// existing books
		IDelegatorOutput bpdOutput = bookDelegator.delegate();

		// return delegator output mapped to serviceresponse
		return EntityMapper.mapBdOutputToBookServiceResponse(bpdOutput);
	}
}
