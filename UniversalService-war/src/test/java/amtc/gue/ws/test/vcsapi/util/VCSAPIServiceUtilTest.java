package amtc.gue.ws.test.vcsapi.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.RepositoryIssue;
import org.junit.Before;

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.vcsapi.inout.VCSIssue;
import amtc.gue.ws.vcsapi.inout.VCSIssues;

/**
 * Class holding setups for helper object setups for testing the VCSAPIService
 * Utility Classes
 *
 * @author Thomas
 *
 */
public abstract class VCSAPIServiceUtilTest {
	protected static final String ISSUETITLEA = "issueTitleA";
	protected static final String ISSUETITLEB = "issueTitleB";
	protected static final String ISSUEDETAILA = "issueDetailA";
	protected static final String ISSUEDETAILB = "issueDetailB";

	protected IDelegatorOutput unrecognizedVCSDelegatorOutput;
	protected IDelegatorOutput VCSDelegatorOutput;

	protected VCSIssue vcsIssue;
	protected VCSIssues vcsIssues;

	protected Issue gitHubIssue;
	protected List<Issue> gitHubIssueList;
	protected List<Issue> gitHubIssueNullList;

	protected RepositoryIssue gitHubRepositoryIssue;
	protected List<RepositoryIssue> gitHubRepositoryIssueList;
	protected List<RepositoryIssue> gitHubRepositoryIssueNullList;

	@Before
	public void setUp() {
		setUpVCSIssues();
		setUpGitHubIssues();
		setupBdOutputs();
	}

	/**
	 * Method intitializing some VCSIssues
	 */
	private void setUpVCSIssues() {
		vcsIssue = new VCSIssue();
		vcsIssue.setIssueTitle(ISSUETITLEA);
		vcsIssue.setIssueDetail(ISSUEDETAILA);

		vcsIssues = new VCSIssues();
		vcsIssues.getIssues().add(vcsIssue);
	}

	/**
	 * Method initializing some Issues
	 */
	private void setUpGitHubIssues() {
		gitHubIssue = new Issue();
		gitHubIssue.setTitle(ISSUETITLEB);
		gitHubIssue.setBody(ISSUEDETAILB);

		gitHubIssueList = new ArrayList<>();
		gitHubIssueList.add(gitHubIssue);

		gitHubIssueNullList = new ArrayList<>();
		gitHubIssueNullList.add(null);

		gitHubRepositoryIssue = new RepositoryIssue();
		gitHubRepositoryIssue.setTitle(ISSUETITLEB);
		gitHubRepositoryIssue.setBody(ISSUEDETAILB);

		gitHubRepositoryIssueList = new ArrayList<>();
		gitHubRepositoryIssueList.add(gitHubRepositoryIssue);

		gitHubRepositoryIssueNullList = new ArrayList<>();
		gitHubRepositoryIssueNullList.add(null);
	}

	private void setupBdOutputs() {
		unrecognizedVCSDelegatorOutput = new DelegatorOutput();
		unrecognizedVCSDelegatorOutput.setOutputObject(null);
		VCSDelegatorOutput = new DelegatorOutput();
		VCSDelegatorOutput.setOutputObject(vcsIssue);
	}
}
