package amtc.gue.ws.test.service.soap;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.TagPersistenceDelegator;
import amtc.gue.ws.service.soap.BookGrabber;
import amtc.gue.ws.test.books.BookTest;

/**
 * Testclass for the BookGrabber Service class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class BookGrabberTest extends BookTest{
	private static BookGrabber bookGrabber;
	private static IDelegatorOutput delegatorOutput;
	private static AbstractPersistenceDelegator bookDelegator;
	private static AbstractPersistenceDelegator tagDelegator;

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
		setUpBookGrabber();
	}
	
	@AfterClass
	public static void checkMocks() {
		EasyMock.verify(bookDelegator);
		EasyMock.verify(tagDelegator);
	}

	@Test
	public void testAddBooks() {
		assertNotNull(bookGrabber.addBooks(books));
	}

	@Test
	public void testGetBooksByTags() {
		assertNotNull(bookGrabber.getBooksByTag(tagsA));
	}

	@Test
	public void testRemoveBooks() {
		assertNotNull(bookGrabber.removeBooks(books));
	}

	@Test
	public void testGetTags() {
		assertNotNull(bookGrabber.getTags());
	}

	// Helper methods	
	private static void setUpDelegatorOutputs() {
		delegatorOutput = new DelegatorOutput();
	}

	private static void setUpDelegatorMocks() {
		bookDelegator = EasyMock.createNiceMock(BookPersistenceDelegator.class);
		EasyMock.expect(bookDelegator.delegate()).andReturn(delegatorOutput).times(3);
		EasyMock.replay(bookDelegator);
		
		tagDelegator = EasyMock.createNiceMock(TagPersistenceDelegator.class);
		EasyMock.expect(tagDelegator.delegate()).andReturn(delegatorOutput);
		EasyMock.replay(tagDelegator);
	}
	
	private static void setUpBookGrabber() {
		bookGrabber = new BookGrabber(bookDelegator, tagDelegator);
	}
}
