package amtc.gue.ws.test.books;

import static org.junit.Assert.assertEquals;

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
import amtc.gue.ws.books.TagService;
import amtc.gue.ws.books.delegate.persist.BookPersistenceDelegator;
import amtc.gue.ws.books.response.TagServiceResponse;

/**
 * Testclass for the Tag REST Service
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagServiceTest extends BookTest {
	private static IDelegatorOutput tagDelegatorOutput;
	private static IDelegatorOutput userDelegatorOutput;
	private static AbstractPersistenceDelegator tagDelegator;
	private static AbstractPersistenceDelegator userDelegator;

	@BeforeClass
	public static void initialSetup() {
		setUpBasicBookEnvironment();
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void checkMocks() {
		EasyMock.verify(tagDelegator);
		EasyMock.verify(userDelegator);
	}
	
	@Test(expected = UnauthorizedException.class)
	public void testGetTagsUsingUnauthorizedUser() throws UnauthorizedException {
		new TagService().getTags(null);
	}

	@Test
	public void testGetTags() throws UnauthorizedException {
		TagServiceResponse resp = new TagService(userDelegator, tagDelegator).getTags(user);
		assertEquals(tagDelegatorOutput.getStatusCode(), resp.getStatus().getStatusCode());
		assertEquals(tagDelegatorOutput.getStatusMessage(), resp.getStatus().getStatusMessage());
	}

	// Helper Methods
	private static void setUpDelegatorOutputs() {
		tagDelegatorOutput = new DelegatorOutput();
		userDelegatorOutput = new DelegatorOutput();
		userDelegatorOutput.setOutputObject(serviceUsers);
		userDelegatorOutput.setOutputObject(invalidServiceUsers);
	}

	private static void setUpDelegatorMocks() {
		tagDelegator = EasyMock.createNiceMock(BookPersistenceDelegator.class);
		EasyMock.expect(tagDelegator.delegate()).andReturn(tagDelegatorOutput).times(1);
		EasyMock.replay(tagDelegator);

		userDelegator = EasyMock.createNiceMock(UserPersistenceDelegator.class);
		EasyMock.expect(userDelegator.delegate()).andReturn(userDelegatorOutput).times(1);
		EasyMock.replay(userDelegator);
	}
}
