package amtc.gue.ws.books;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.Nullable;
import com.google.appengine.api.users.User;

import amtc.gue.ws.Constants;
import amtc.gue.ws.base.Service;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.response.BookServiceResponse;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;

@Api(name = "books", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID,
		Constants.API_EXPLORER_CLIENT_ID }, description = "API for the Book backend application")
public class BookService extends Service {
	protected static final Logger log = Logger.getLogger(BookService.class.getName());
	private static final String SCOPE = "books";
	private AbstractPersistenceDelegator bookDelegator;

	public BookService() {
		super();
		bookDelegator = (BookPersistenceDelegator) SpringContext.context.getBean("bookPersistenceDelegator");
	}

	public BookService(AbstractPersistenceDelegator userDelegator, AbstractPersistenceDelegator bookDelegator) {
		super(userDelegator);
		this.bookDelegator = bookDelegator;
	}

	@ApiMethod(name = "addBooks", path = "book", httpMethod = HttpMethod.POST)
	public BookServiceResponse addBooks(final User user, Books books) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		bookDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.ADD, books);
		IDelegatorOutput dOutput = bookDelegator.delegate();
		BookServiceResponse response = BookServiceEntityMapper.mapBdOutputToBookServiceResponse(dOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "getBooks", path = "book", httpMethod = HttpMethod.GET)
	public BookServiceResponse getBooks(final User user, @Named("searchTag") @Nullable String searchTag)
			throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		Tags tags = new Tags();
		List<String> searchTags = new ArrayList<>();
		if (searchTag != null) {
			searchTags.add(searchTag);
			tags.setTags(searchTags);
		}

		bookDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, tags);
		IDelegatorOutput dOutput = bookDelegator.delegate();
		BookServiceResponse response = BookServiceEntityMapper.mapBdOutputToBookServiceResponse(dOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "removeBook", path = "book/{id}", httpMethod = HttpMethod.DELETE)
	public BookServiceResponse removeBook(final User user, @Named("id") String id) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		Books booksToRemove = new Books();
		Book bookToRemove = new Book();
		bookToRemove.setId(id);
		List<Book> bookListToRemove = new ArrayList<>();
		bookListToRemove.add(bookToRemove);
		booksToRemove.setBooks(bookListToRemove);
		bookDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.DELETE, booksToRemove);
		IDelegatorOutput dOutput = bookDelegator.delegate();
		BookServiceResponse response = BookServiceEntityMapper.mapBdOutputToBookServiceResponse(dOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}
}
