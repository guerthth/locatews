package amtc.gue.ws.test.vcsapi.delegate;

import static org.junit.Assert.*;

import java.io.IOException;
import org.easymock.EasyMock;
import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.service.IssueService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.input.DelegatorInput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.vcsapi.delegate.GithubAPIDelegator;
import amtc.gue.ws.vcsapi.util.VCSAPIServiceErrorConstants;

/**
 * Testclass for the GithubAPIDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GithubAPIDelegatorTest extends VCSAPIDelegatorTest {
	protected static DelegatorInput addGithubIssueDelegatorInput;
	protected static DelegatorInput getGithubIssuesDelegatorInput;
	protected static DelegatorInput unknownGithubIssueDelegatorInput;

	protected static GithubAPIDelegator gitHubAPIDelegator;

	protected static IssueService issueService;
	protected static IssueService issueFailureService;

	@BeforeClass
	public static void initialSetup() throws IOException {
		oneTimeInitialSetup();
		setUpGithubAPIDelegatorInputs();
		setUpGithubAPIDelegators();
		setUpGithubAPIMocks();
	}

	@AfterClass
	public static void tearDown() {
		EasyMock.verify(issueService);
		EasyMock.verify(issueFailureService);
	}

	@Override
	public void testDelegateUsingNullInput() {
		gitHubAPIDelegator.initialize(nullDelegatorInput);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	public void testDelegateUsingUnrecognizedInputType() {
		gitHubAPIDelegator.initialize(unrecognizedDelegatorInput);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Test
	public void testAddIssueUsingUnrecognizedInput() {
		gitHubAPIDelegator.initialize(unknownGithubIssueDelegatorInput);
		gitHubAPIDelegator.setIssueService(issueService);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Test
	public void testAddIssueSuccess() {
		gitHubAPIDelegator.initialize(addGithubIssueDelegatorInput);
		gitHubAPIDelegator.setIssueService(issueService);
		gitHubAPIDelegator.setGithubRepository(repository);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(VCSAPIServiceErrorConstants.ADD_ISSUE_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Test
	public void testAddIssueFailure() {
		gitHubAPIDelegator.initialize(addGithubIssueDelegatorInput);
		gitHubAPIDelegator.setIssueService(issueFailureService);
		gitHubAPIDelegator.setGithubRepository(repository);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(VCSAPIServiceErrorConstants.ADD_ISSUE_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	@Test
	public void testGetIssuesSuccess() {
		gitHubAPIDelegator.initialize(getGithubIssuesDelegatorInput);
		gitHubAPIDelegator.setIssueService(issueService);
		gitHubAPIDelegator.setGithubRepository(repository);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(VCSAPIServiceErrorConstants.RETRIEVE_ISSUES_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertNotNull(delegatorOutput);
	}

	@Test
	public void testGetIssuesFailure() {
		gitHubAPIDelegator.initialize(getGithubIssuesDelegatorInput);
		gitHubAPIDelegator.setIssueService(issueFailureService);
		gitHubAPIDelegator.setGithubRepository(repository);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(VCSAPIServiceErrorConstants.RETRIEVE_ISSUES_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	// helper classes
	private static void setUpGithubAPIDelegatorInputs() {
		addGithubIssueDelegatorInput = new DelegatorInput();
		addGithubIssueDelegatorInput.setInputObject(issue);
		addGithubIssueDelegatorInput.setType(DelegatorTypeEnum.ADD);

		getGithubIssuesDelegatorInput = new DelegatorInput();
		getGithubIssuesDelegatorInput.setInputObject(null);
		getGithubIssuesDelegatorInput.setType(DelegatorTypeEnum.READ);

		unknownGithubIssueDelegatorInput = new DelegatorInput();
		unknownGithubIssueDelegatorInput.setInputObject(issueService);
		unknownGithubIssueDelegatorInput.setType(DelegatorTypeEnum.ADD);
	}

	private static void setUpGithubAPIDelegators() {
		gitHubAPIDelegator = new GithubAPIDelegator();
	}

	private static void setUpGithubAPIMocks() throws IOException {
		issueService = EasyMock.createNiceMock(IssueService.class);
		EasyMock.expect(issueService.getClient()).andReturn(githubClient).times(2);
		EasyMock.expect(issueService.createIssue(EasyMock.isA(Repository.class), EasyMock.isA(Issue.class)))
				.andReturn(githubIssue);
		EasyMock.expect(issueService.getIssues()).andReturn(githubRepositoryIssueList);
		EasyMock.replay(issueService);

		issueFailureService = EasyMock.createNiceMock(IssueService.class);
		EasyMock.expect(issueFailureService.getClient()).andReturn(githubClient).times(2);
		EasyMock.expect(issueFailureService.createIssue(EasyMock.isA(Repository.class), EasyMock.isA(Issue.class)))
				.andThrow(new IOException());
		EasyMock.expect(issueFailureService.getIssues()).andThrow(new IOException());
		EasyMock.replay(issueFailureService);
	}
}
