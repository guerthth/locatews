package amtc.gue.ws.vcsapi.delegate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryIssue;
import org.eclipse.egit.github.core.service.IssueService;
import org.eclipse.egit.github.core.service.RepositoryService;

import amtc.gue.ws.base.delegate.input.IDelegatorInput;
import amtc.gue.ws.vcsapi.inout.VCSIssue;
import amtc.gue.ws.vcsapi.util.VCSAPIServiceEntityMapper;
import amtc.gue.ws.vcsapi.util.VCSAPIServiceErrorConstants;

/**
 * Delegator that handles all the communication with the Github API
 * 
 * @author Thomas
 *
 */
public class GithubAPIDelegator extends AbstractAPIDelegator {

	private static final Logger log = Logger.getLogger(GithubAPIDelegator.class.getName());
	private static final String OAUTH2TOKEN = "c12b617448d3d831c553fca0a5c85579e2fd671b";
	private Repository githubRepository;

	private IssueService issueService;

	@Override
	public void initialize(IDelegatorInput input) {
		super.initialize(input);
		RepositoryService repositoryService = new RepositoryService();
		issueService = new IssueService();
		try {
			githubRepository = repositoryService.getRepository("guerthth", "locatews");
		} catch (IOException e) {
			log.log(Level.SEVERE, e.getMessage(), e);
		}
	}

	@Override
	public void addIssue() {
		log.info("ADD Issue using Github API action triggered");
		if (delegatorInput.getInputObject() instanceof VCSIssue) {
			VCSIssue issue = (VCSIssue) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(VCSAPIServiceErrorConstants.ADD_ISSUE_SUCCESS_CODE);
			Issue githubIssue = VCSAPIServiceEntityMapper.mapVCSIssueToGitHubIssue(issue);

			issueService.getClient().setOAuth2Token(OAUTH2TOKEN);
			try {
				githubIssue = issueService.createIssue(githubRepository, githubIssue);
				String statusMessage = VCSAPIServiceErrorConstants.ADD_ISSUE_SUCCESS_MSG + " to '"
						+ githubRepository.getName() + "'";
				log.info(statusMessage);
				delegatorOutput.setStatusMessage(statusMessage);
				delegatorOutput.setOutputObject(VCSAPIServiceEntityMapper.mapGitHubIssueToVCSIssue(githubIssue));
			} catch (IOException e) {
				delegatorOutput.setStatusCode(VCSAPIServiceErrorConstants.ADD_ISSUE_FAILURE_CODE);
				delegatorOutput.setOutputObject(null);
				log.log(Level.SEVERE, "Error while trying to add issue to '" + githubRepository.getName() + "'");
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	@Override
	protected void getIssues() {
		log.info("GET Issues using Github API action triggered");
		delegatorOutput.setStatusCode(VCSAPIServiceErrorConstants.RETRIEVE_ISSUES_SUCCESS_CODE);
		List<RepositoryIssue> foundIssues = new ArrayList<>();
		issueService.getClient().setOAuth2Token(OAUTH2TOKEN);
		try {
			// foundIssues = issueService.getIssues(githubRepository, null);
			foundIssues = issueService.getIssues();
			String statusMessage = VCSAPIServiceErrorConstants.RETRIEVE_ISSUES_SUCCESS_MSG + " from '"
					+ githubRepository.getName() + "'";
			log.info(statusMessage);
			delegatorOutput.setStatusMessage(statusMessage);
			delegatorOutput
					.setOutputObject(VCSAPIServiceEntityMapper.transformGitHubRepositoryIssuesToVCSIssues(foundIssues));
		} catch (IOException e) {
			delegatorOutput.setStatusCode(VCSAPIServiceErrorConstants.RETRIEVE_ISSUES_FAILURE_CODE);
			delegatorOutput.setOutputObject(null);
			log.log(Level.SEVERE, "Error while trying to retrieve issues from '" + githubRepository.getName() + "'");
		}
	}

	// Getters and Setters
	public IssueService getIssueService() {
		return issueService;
	}

	public void setIssueService(IssueService issueService) {
		this.issueService = issueService;
	}
	
	public Repository getGithubRepository() {
		return githubRepository;
	}

	public void setGithubRepository(Repository githubRepository) {
		this.githubRepository = githubRepository;
	}
}
