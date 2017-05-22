package amtc.gue.ws.test.vcsapi;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryIssue;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.junit.Before;

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.test.base.BaseTest;
import amtc.gue.ws.vcsapi.inout.VCSIssue;

/**
 * Class holding data for all VCSAPIService Tests
 * 
 * @author Thomas
 *
 */
public class VCSAPITest extends BaseTest {
protected static Repository repository;
	protected static VCSIssue vcsIssue;
	protected static Issue githubIssue;
	protected static List<Issue> githubIssueList;
	protected static List<Issue> githubIssueNullList;

	protected static RepositoryIssue githubRepositoryIssue;
	protected static List<RepositoryIssue> githubRepositoryIssueList;
	protected static List<RepositoryIssue> githubRepositoryIssueNullList;
	
	protected static GitHubClient githubClient;

	protected static final String ISSUEDETAIL = "IssueDetail";
	protected static final String ISSUETITLE = "IssueTitle";
	protected static final String ISSUESTATE = "IssueState";
	
	protected static IDelegatorOutput unrecognizedVCSDelegatorOutput;
	protected static IDelegatorOutput VCSDelegatorOutput;
	
	@Before
	public void setUp() {
		setUpIssues();
	}
	
	/**
	 * Setting up basic environment
	 */
	protected static void setUpBasicEnvironment() {
		setUpRepositories();
		setUpGithubClients();
		setupBdOutputs();
		setUpIssues();
	}

	/**
	 * Method setting up repositories
	 */
	private static void setUpRepositories() {
		repository = new Repository();
	}
	
	/**
	 * Method setting up Githubclients
	 */
	private static void setUpGithubClients() {
		githubClient = new GitHubClient();
	}
	
	/**
	 * Method setting up issues
	 */
	private static void setUpIssues() {
		vcsIssue = new VCSIssue();
		vcsIssue.setIssueTitle(ISSUETITLE);
		vcsIssue.setIssueDetail(ISSUEDETAIL);
		vcsIssue.setIssueState(ISSUESTATE);

		githubIssue = new Issue();
		githubIssue.setTitle(ISSUETITLE);
		githubIssue.setBody(ISSUEDETAIL);
		githubIssue.setState(ISSUESTATE);

		githubIssueList = new ArrayList<>();
		githubIssueList.add(githubIssue);

		githubRepositoryIssue = new RepositoryIssue();
		githubRepositoryIssue.setTitle(ISSUETITLE);
		githubRepositoryIssue.setBody(ISSUEDETAIL);
		githubRepositoryIssue.setState(ISSUESTATE);

		githubRepositoryIssueList = new ArrayList<>();
		githubRepositoryIssueList.add(githubRepositoryIssue);
		
		githubRepositoryIssueNullList = new ArrayList<>();
		githubRepositoryIssueNullList.add(null);
		
		githubIssueNullList = new ArrayList<>();
		githubIssueNullList.add(null);
	}
	
	/**
	 * Method setting up VCS Delegator inputs
	 */
	private static void setupBdOutputs() {
		unrecognizedVCSDelegatorOutput = new DelegatorOutput();
		unrecognizedVCSDelegatorOutput.setOutputObject(null);
		VCSDelegatorOutput = new DelegatorOutput();
		VCSDelegatorOutput.setOutputObject(vcsIssue);
	}
}
