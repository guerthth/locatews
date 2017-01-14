package amtc.gue.ws.books;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
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

import amtc.gue.ws.base.delegate.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.util.PersistenceTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.response.BookServiceResponse;
import amtc.gue.ws.books.util.BookServiceEntityMapper;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
public class BookService {
	protected static final Logger log = Logger.getLogger(BookService.class
			.getName());
	private AbstractPersistenceDelegator bookDelegator;

	public BookService() {
		this.bookDelegator = (BookPersistenceDelegator) SpringContext.context
				.getBean("bookPersistenceDelegator");
	}

	public BookService(AbstractPersistenceDelegator delegator) {
		this.bookDelegator = delegator;
	}

	@PermitAll
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public BookServiceResponse addBooks(Books items) {
		// set up the pesistence delegator
		bookDelegator.buildAndInitializePersistenceDelegator(
				PersistenceTypeEnum.ADD, items);

		// call BookPersistenceDelegators delegate method to handle persist
		IDelegatorOutput bpdOutput = bookDelegator.delegate();

		// return delegator output mapped to serviceresponse
		return BookServiceEntityMapper
				.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@PermitAll
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
		bookDelegator.buildAndInitializePersistenceDelegator(
				PersistenceTypeEnum.READ, tags);

		// call BookPersistenceDelegators delegate method to handle retrieval of
		// existing books
		IDelegatorOutput bpdOutput = bookDelegator.delegate();

		// return delegator output mapped to serviceresponse
		return BookServiceEntityMapper
				.mapBdOutputToBookServiceResponse(bpdOutput);
	}

	@PermitAll
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public BookServiceResponse removeBook(@PathParam("id") String id) {
		// set up the pesistence delegator
		Books booksToRemove = new Books();
		Book bookToRemove = new Book();
		List<Book> bookListToRemove = new ArrayList<>();
		bookListToRemove.add(bookToRemove);
		bookToRemove.setId(id);
		booksToRemove.setBooks(bookListToRemove);

		bookDelegator.buildAndInitializePersistenceDelegator(
				PersistenceTypeEnum.DELETE, booksToRemove);

		// call BookPersistenceDelegators delegate method to handle removal of
		// existing books
		IDelegatorOutput bpdOutput = bookDelegator.delegate();

		// return delegator output mapped to serviceresponse
		return BookServiceEntityMapper
				.mapBdOutputToBookServiceResponse(bpdOutput);
	}
}
