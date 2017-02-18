package amtc.gue.ws.vcsapi.delegate;

import org.apache.log4j.Logger;

import amtc.gue.ws.vcsapi.inout.Issue;
import amtc.gue.ws.vcsapi.util.GitHubAPIServiceErrorConstants;

/**
 * Delegator that handles all the communication with the Github API
 * 
 * @author Thomas
 *
 */
public class GithubAPIDelegator extends AbstractAPIDelegator {

	private static final Logger log = Logger.getLogger(GithubAPIDelegator.class.getName());

	@Override
	protected void addIssue() {
		log.info("ADD Issue using Github API action triggered");
		if(delegatorInput.getInputObject() instanceof Issue){
			Issue issue = (Issue) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(GitHubAPIServiceErrorConstants.ADD_ISSUE_SUCCESS_CODE);
			// TODO Call Github API adding the issue
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}
}
