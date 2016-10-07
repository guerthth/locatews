package amtc.gue.ws.test.service;

import static org.junit.Assert.*;

import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.easymock.EasyMock;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.output.PersistenceDelegatorOutput;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.output.BookServiceResponse;
import amtc.gue.ws.service.BookService;

/**
 * Testclass for the Book REST Service
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookServiceTest extends JerseyTest {

	private static IDelegatorOutput delegatorOutput;
	private static AbstractPersistenceDelegator bookDelegator;

	@Override
	protected Application configure() {
		return new ResourceConfig().register(new BookService(bookDelegator));
	}

	@BeforeClass
	public static void inititalSetup() {
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void checkMocks() {
		EasyMock.verify(bookDelegator);
	}

	@Test(expected = NotFoundException.class)
	public void testServiceUsingIncorrectURL() {
		target("incorrectUrl").request().get(BookServiceResponse.class);
	}

	@Test
	public void testAddBooks() {
		Books booksToAdd = new Books();
		final Response resp = target("/books").request().post(
				Entity.json(booksToAdd));
		assertNotNull(resp);
	}

	@Test
	public void testGetBooksUsingNoTagsearch() {
		final BookServiceResponse resp = target("/books").request().get(
				BookServiceResponse.class);
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus()
				.getStatusMessage());
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus()
				.getStatusCode());
	}

	@Test
	public void testGetBooksUsingTagSearch() {
		final BookServiceResponse resp = target("/books")
				.queryParam("searchTag", "testTag").request()
				.get(BookServiceResponse.class);
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus()
				.getStatusMessage());
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus()
				.getStatusCode());
	}

	@Test(expected = NotAllowedException.class)
	public void testRemoveBookUsingIncorrectCall() {
		final BookServiceResponse resp = target("/books").request().delete(
				BookServiceResponse.class);
		assertNotNull(resp);
	}

	@Test
	public void testRemoveBookUsingCorrectCall() {
		final BookServiceResponse resp = target("/books/1").request().delete(
				BookServiceResponse.class);
		assertNotNull(resp);
	}

	// Helper methods
	private static void setUpDelegatorOutputs() {
		delegatorOutput = new PersistenceDelegatorOutput();
	}

	private static void setUpDelegatorMocks() {
		bookDelegator = EasyMock.createNiceMock(BookPersistenceDelegator.class);
		EasyMock.expect(bookDelegator.delegate()).andReturn(delegatorOutput)
				.times(4);
		EasyMock.replay(bookDelegator);
	}
}
