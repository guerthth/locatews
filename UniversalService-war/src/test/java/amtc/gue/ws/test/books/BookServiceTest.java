package amtc.gue.ws.test.books;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.api.server.spi.response.UnauthorizedException;

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.UserPersistenceDelegator;
import amtc.gue.ws.books.BookService;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.response.BookServiceResponse;

/**
 * Testclass for the Book REST Service
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookServiceTest extends BookTest {
	private static IDelegatorOutput bookDelegatorOutput;
	private static IDelegatorOutput userDelegatorOutput;
	private static AbstractPersistenceDelegator bookDelegator;
	private static AbstractPersistenceDelegator userDelegator;

	@BeforeClass
	public static void initialSetup() {
		setUpBasicBookEnvironment();
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void checkMocks() {
		EasyMock.verify(bookDelegator);
		EasyMock.verify(userDelegator);
	}

	@Test(expected = UnauthorizedException.class)
	public void testAddBooksUsingUnauthorizedUser() throws UnauthorizedException {
		new BookService().addBooks(null, books);
	}

	@Test
	public void testAddBooks() throws UnauthorizedException {
		BookServiceResponse resp = new BookService(userDelegator, bookDelegator).addBooks(user, books);
		assertEquals(bookDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(bookDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testGetBooksUsingUnauthorizedUser() throws UnauthorizedException {
		new BookService().getBooks(null, TAG);
	}

	@Test
	public void testGetBooksUsingNullSearchTag() throws UnauthorizedException {
		BookServiceResponse resp = new BookService(userDelegator, bookDelegator).getBooks(user, null);
		assertEquals(bookDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(bookDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test
	public void testGetBooksUsingSimpleSearchTag() throws UnauthorizedException {
		BookServiceResponse resp = new BookService(userDelegator, bookDelegator).getBooks(user, TAG);
		assertEquals(bookDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(bookDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test(expected = UnauthorizedException.class)
	public void testRemoveBookUsingUnauthorizedUser() throws UnauthorizedException {
		BookServiceResponse resp = new BookService(userDelegator, bookDelegator).removeBook(null, BOOKKEY);
		assertEquals(bookDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(bookDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	@Test
	public void testRemoveBook() throws UnauthorizedException {
		BookServiceResponse resp = new BookService(userDelegator, bookDelegator).removeBook(user, BOOKKEY);
		assertEquals(bookDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(bookDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	// Helper methods
	private static void setUpDelegatorOutputs() {
		bookDelegatorOutput = new DelegatorOutput();
		userDelegatorOutput = new DelegatorOutput();
		userDelegatorOutput.setOutputObject(serviceUser);
		userDelegatorOutput.setOutputObject(invalidServiceUser);
	}

	private static void setUpDelegatorMocks() {
		bookDelegator = EasyMock.createNiceMock(BookPersistenceDelegator.class);
		EasyMock.expect(bookDelegator.delegate()).andReturn(bookDelegatorOutput).times(4);
		EasyMock.replay(bookDelegator);

		userDelegator = EasyMock.createNiceMock(UserPersistenceDelegator.class);
		EasyMock.expect(userDelegator.delegate()).andReturn(userDelegatorOutput).times(4);
		EasyMock.replay(userDelegator);
	}
}
