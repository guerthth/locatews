package amtc.gue.ws.vcsapi.util;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.egit.github.core.Issue;
import org.eclipse.egit.github.core.RepositoryIssue;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.util.StatusMapper;
import amtc.gue.ws.vcsapi.inout.VCSIssue;
import amtc.gue.ws.vcsapi.inout.VCSIssues;
import amtc.gue.ws.vcsapi.response.VCSAPIServiceResponse;

/**
 * Class responsible for mapping of GitHubAPIService related objects. User Case
 * examples: - building up GitHubApiServiceResponse objects
 * 
 * @author Thomas
 *
 */
public class VCSAPIServiceEntityMapper {
	/**
	 * Method mapping GitHubIssues to VCSIssues
	 * 
	 * @param gitHubIssue
	 *            the Issue that should be transformed
	 * @return mapped VCSIssue
	 */
	public static VCSIssue mapGitHubIssueToVCSIssue(Issue gitHubIssue) {
		VCSIssue vcsIssue = new VCSIssue();
		if (gitHubIssue != null) {
			vcsIssue.setIssueId(gitHubIssue.getNumber());
			vcsIssue.setIssueTitle(gitHubIssue.getTitle());
			vcsIssue.setIssueDetail(gitHubIssue.getBody());
			vcsIssue.setIssueState(gitHubIssue.getState());
		}
		return vcsIssue;
	}

	/**
	 * Method that maps a list of Github Issues to a VCSIssues object
	 * 
	 * @param githubIssues
	 *            the list of Github Issues
	 * @return a VCSIssues object
	 */
	public static VCSIssues transformGitHubIssuesToVCSIssues(List<Issue> githubIssues) {
		VCSIssues vcsIssues = new VCSIssues();
		List<VCSIssue> vcsIssueList = new ArrayList<>();
		if (githubIssues != null) {
			for (Issue issue : githubIssues) {
				if (issue != null)
					vcsIssueList.add(mapGitHubIssueToVCSIssue(issue));
			}
		}
		vcsIssues.setIssues(vcsIssueList);
		return vcsIssues;
	}

	/**
	 * method that maps a list of Github Repository Issues to a VCSIssues object
	 * 
	 * @param githubIssues
	 *            the list of Github RepositoryIssues
	 * @return a VCSIssues object
	 */
	public static VCSIssues transformGitHubRepositoryIssuesToVCSIssues(List<RepositoryIssue> githubIssues) {
		VCSIssues vcsIssues = new VCSIssues();
		List<VCSIssue> vcsIssueList = new ArrayList<>();
		if (githubIssues != null) {
			for (Issue issue : githubIssues) {
				if (issue != null)
					vcsIssueList.add(mapGitHubIssueToVCSIssue(issue));
			}
		}
		vcsIssues.setIssues(vcsIssueList);
		return vcsIssues;
	}

	/**
	 * Method mapping VCSIssues to GitHubIssues
	 * 
	 * @param vcsIssue
	 *            the VCSIssue that should be transformed
	 * @return mapped Issue
	 */
	public static Issue mapVCSIssueToGitHubIssue(VCSIssue vcsIssue) {
		Issue gitHubIssue = new Issue();
		if (vcsIssue != null) {
			gitHubIssue.setNumber(vcsIssue.getIssueId());
			gitHubIssue.setTitle(vcsIssue.getIssueTitle());
			gitHubIssue.setBody(vcsIssue.getIssueDetail());
			gitHubIssue.setState(vcsIssue.getIssueState());
		}
		return gitHubIssue;
	}

	public static VCSAPIServiceResponse mapBdOutputToUserServiceResponse(IDelegatorOutput dOutput) {
		VCSAPIServiceResponse vcsAPIServiceResponse = null;
		if (dOutput != null) {
			vcsAPIServiceResponse = new VCSAPIServiceResponse();
			vcsAPIServiceResponse.setStatus(StatusMapper.buildStatusForDelegatorOutput(dOutput));
			if (dOutput.getOutputObject() instanceof VCSIssue) {
				VCSIssue vcsIssue = (VCSIssue) dOutput.getOutputObject();
				vcsAPIServiceResponse.setIssue(vcsIssue);
			} else {
				vcsAPIServiceResponse.setIssue(null);
			}
		}
		return vcsAPIServiceResponse;
	}
}
