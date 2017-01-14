package amtc.gue.ws.test.books;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Application;

import org.easymock.EasyMock;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.output.PersistenceDelegatorOutput;
import amtc.gue.ws.books.TagService;
import amtc.gue.ws.books.delegate.persist.TagPersistenceDelegator;
import amtc.gue.ws.books.response.TagServiceResponse;

/**
 * Testclass for the Tag REST Service
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagServiceTest extends JerseyTest {

	private static IDelegatorOutput delegatorOutput;
	private static AbstractPersistenceDelegator tagDelegator;

	@Override
	protected Application configure() {
		return new ResourceConfig().register(new TagService(tagDelegator));
	}

	@BeforeClass
	public static void initialSetup() {
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void checkMocks() {
		EasyMock.verify(tagDelegator);
	}

	@Test
	public void testGetTagsUsingCorrectURL() {
		final TagServiceResponse resp = target("/tags").request().get(
				TagServiceResponse.class);
		assertEquals(delegatorOutput.getStatusMessage(), resp.getStatus()
				.getStatusMessage());
		assertEquals(delegatorOutput.getStatusCode(), resp.getStatus()
				.getStatusCode());
	}

	@Test(expected = NotFoundException.class)
	public void testGetTagsUsingIncorrectURL() {
		target("/tags/wrong").request().get(TagServiceResponse.class);
	}

	// Helper Methods
	private static void setUpDelegatorOutputs() {
		delegatorOutput = new PersistenceDelegatorOutput();
	}

	private static void setUpDelegatorMocks() {
		tagDelegator = EasyMock.createNiceMock(TagPersistenceDelegator.class);
		EasyMock.expect(tagDelegator.delegate()).andReturn(delegatorOutput)
				.times(1);
		EasyMock.replay(tagDelegator);
	}
}
