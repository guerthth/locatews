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
import amtc.gue.ws.test.vcsapi.VCSAPITest;
import amtc.gue.ws.vcsapi.delegate.GithubAPIDelegator;
import amtc.gue.ws.vcsapi.util.VCSAPIServiceErrorConstants;

/**
 * Testclass for the GithubAPIDelegator class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GithubAPIDelegatorTest extends VCSAPITest implements IVCSAPIDelegatorTest {
	private static DelegatorInput addGithubIssueDelegatorInput;
	private static DelegatorInput getGithubIssuesDelegatorInput;
	private static DelegatorInput unknownGithubIssueDelegatorInput;

	private static GithubAPIDelegator gitHubAPIDelegator;

	private static IssueService issueService;
	private static IssueService issueFailureService;

	@BeforeClass
	public static void oneTimeInitialSetup() throws IOException {
		setUpBasicEnvironment();
		setUpDelegatorInputs();
		setUpGithubAPIDelegatorInputs();
		setUpGithubAPIDelegators();
		setUpVCSMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(issueService);
		EasyMock.verify(issueFailureService);
	}

	@Override
	@Test
	public void testDelegateUsingNullInput() {
		gitHubAPIDelegator.initialize(nullDelegatorInput);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testDelegateUsingUnrecognizedInputType() {
		gitHubAPIDelegator.initialize(unrecognizedDelegatorInput);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG, delegatorOutput.getStatusMessage());
	}

	@Override
	@Test
	public void testAddIssueSucess() {
		gitHubAPIDelegator.initialize(addGithubIssueDelegatorInput);
		gitHubAPIDelegator.setIssueService(issueService);
		gitHubAPIDelegator.setGithubRepository(repository);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(VCSAPIServiceErrorConstants.ADD_ISSUE_SUCCESS_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testAddIssueFailure() {
		gitHubAPIDelegator.initialize(addGithubIssueDelegatorInput);
		gitHubAPIDelegator.setIssueService(issueFailureService);
		gitHubAPIDelegator.setGithubRepository(repository);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(VCSAPIServiceErrorConstants.ADD_ISSUE_FAILURE_CODE, delegatorOutput.getStatusCode());
	}
	
	@Override
	@Test
	public void testAddIssueUsingUnrecognizedInputObject() {
		gitHubAPIDelegator.initialize(unknownGithubIssueDelegatorInput);
		gitHubAPIDelegator.setIssueService(issueFailureService);
		gitHubAPIDelegator.setGithubRepository(repository);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE, delegatorOutput.getStatusCode());
	}

	@Override
	@Test
	public void testGetIssueSuccess() {
		gitHubAPIDelegator.initialize(getGithubIssuesDelegatorInput);
		gitHubAPIDelegator.setIssueService(issueService);
		gitHubAPIDelegator.setGithubRepository(repository);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(VCSAPIServiceErrorConstants.RETRIEVE_ISSUES_SUCCESS_CODE, delegatorOutput.getStatusCode());
		assertNotNull(delegatorOutput);
	}

	@Override
	@Test
	public void testGetIssueFailure() {
		gitHubAPIDelegator.initialize(getGithubIssuesDelegatorInput);
		gitHubAPIDelegator.setIssueService(issueFailureService);
		gitHubAPIDelegator.setGithubRepository(repository);
		IDelegatorOutput delegatorOutput = gitHubAPIDelegator.delegate();
		assertEquals(VCSAPIServiceErrorConstants.RETRIEVE_ISSUES_FAILURE_CODE, delegatorOutput.getStatusCode());
	}

	/**
	 * Method setting up GithubAPIDelegatorInputs
	 */
	private static void setUpGithubAPIDelegatorInputs() {
		addGithubIssueDelegatorInput = new DelegatorInput();
		addGithubIssueDelegatorInput.setInputObject(vcsIssue);
		addGithubIssueDelegatorInput.setType(DelegatorTypeEnum.ADD);

		getGithubIssuesDelegatorInput = new DelegatorInput();
		getGithubIssuesDelegatorInput.setInputObject(null);
		getGithubIssuesDelegatorInput.setType(DelegatorTypeEnum.READ);

		unknownGithubIssueDelegatorInput = new DelegatorInput();
		unknownGithubIssueDelegatorInput.setInputObject(issueService);
		unknownGithubIssueDelegatorInput.setType(DelegatorTypeEnum.ADD);
	}

	/**
	 * Method setting up GithubAPIDelegators
	 */
	private static void setUpGithubAPIDelegators() {
		gitHubAPIDelegator = new GithubAPIDelegator();
	}

	/**
	 * Method setting up some VCS API Mocks
	 * 
	 * @throws IOException
	 *             when issue occurs
	 */
	private static void setUpVCSMocks() throws IOException {
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
