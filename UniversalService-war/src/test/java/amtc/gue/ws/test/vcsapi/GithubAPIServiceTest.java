package amtc.gue.ws.test.vcsapi;

import static org.junit.Assert.*;

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

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.response.ServiceResponse;
import amtc.gue.ws.vcsapi.GithubAPIService;
import amtc.gue.ws.vcsapi.delegate.AbstractAPIDelegator;
import amtc.gue.ws.vcsapi.delegate.GithubAPIDelegator;
import amtc.gue.ws.vcsapi.inout.VCSIssue;

/**
 * Testclass for the GitHubAPI REST Service
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GithubAPIServiceTest extends JerseyTest {
	private static IDelegatorOutput githubAPIDelegatorOutput;
	private static AbstractAPIDelegator githubAPIDelegator;

	@Override
	protected Application configure() {
		return new ResourceConfig().register(new GithubAPIService(githubAPIDelegator));
	}

	@BeforeClass
	public static void initialSetup() {
		setUpDelegatorOutputs();
		setUpDelegatorMocks();
	}

	@AfterClass
	public static void checkMocks() {
		EasyMock.verify(githubAPIDelegator);
	}

	@Test(expected = NotFoundException.class)
	public void testServiceUsingIncorrectURL() {
		target("incorrectUrl").request().get(ServiceResponse.class);
	}

	@Test
	public void testAddIssueToProject() {
		VCSIssue issueToAdd = new VCSIssue();
		final Response resp = target("/gitapi").request().post(Entity.json(issueToAdd));
		assertNotNull(resp);
	}

	// Helper methods
	private static void setUpDelegatorOutputs() {
		githubAPIDelegatorOutput = new DelegatorOutput();
	}

	private static void setUpDelegatorMocks() {
		githubAPIDelegator = EasyMock.createNiceMock(GithubAPIDelegator.class);
		EasyMock.expect(githubAPIDelegator.delegate()).andReturn(githubAPIDelegatorOutput);
		EasyMock.replay(githubAPIDelegator);
	}
}
