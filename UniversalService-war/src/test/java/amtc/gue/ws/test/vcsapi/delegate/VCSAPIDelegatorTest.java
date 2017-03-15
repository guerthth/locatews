package amtc.gue.ws.test.vcsapi.delegate;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryIssue;
import org.eclipse.egit.github.core.client.GitHubClient;

import amtc.gue.ws.test.base.delegate.BaseDelegatorTest;
import amtc.gue.ws.vcsapi.inout.VCSIssue;

public abstract class VCSAPIDelegatorTest extends BaseDelegatorTest {
	protected static Repository repository;
	
	protected static VCSIssue issue;
	protected static Issue githubIssue;
	protected static List<Issue> githubIssueList;

	protected static RepositoryIssue githubRepositoryIssue;
	protected static List<RepositoryIssue> githubRepositoryIssueList;
	
	protected static GitHubClient githubClient;

	protected static final String ISSUEDETAIL = "IssueDetail";
	protected static final String ISSUETITLE = "IssueTitle";
	protected static final String ISSUESTATE = "IssueState";

	/**
	 * Method building the initial setup for delegatortests of the UserService
	 */
	protected static void oneTimeInitialSetup() {
		setUpRepositories();
		setUpGithubClients();
		setUpIssues();
		setUpBaseDelegatorInputs();
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
	 * Method setting up Issues
	 */
	private static void setUpIssues() {
		issue = new VCSIssue();
		issue.setIssueTitle(ISSUETITLE);
		issue.setIssueDetail(ISSUEDETAIL);
		issue.setIssueState(ISSUESTATE);

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
	}
}
