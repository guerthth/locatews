package amtc.gue.ws.test.vcsapi.util;

import static org.junit.Assert.*;

import org.eclipse.egit.github.core.Issue;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.vcsapi.inout.VCSIssue;
import amtc.gue.ws.vcsapi.inout.VCSIssues;
import amtc.gue.ws.vcsapi.response.VCSAPIServiceResponse;
import amtc.gue.ws.vcsapi.util.VCSAPIServiceEntityMapper;

/**
 * EntityMapper Testclass
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VCSAPIServiceEntityMapperTest extends VCSAPIServiceUtilTest {
	@Test
	public void testMapVCSIssueToGitHubIssueUsingNullValue() {
		Issue issue = VCSAPIServiceEntityMapper.mapVCSIssueToGitHubIssue(null);
		assertNotNull(issue);
	}

	@Test
	public void testMapVCSIssueToGitHubIssue() {
		Issue issue = VCSAPIServiceEntityMapper.mapVCSIssueToGitHubIssue(vcsIssue);
		assertNotNull(issue);
		assertEquals(issue.getTitle(), vcsIssue.getIssueTitle());
		assertEquals(issue.getBody(), vcsIssue.getIssueDetail());
	}

	@Test
	public void testMapGitHubIssueToVCSIssueUsingNullValue() {
		VCSIssue vcsIssue = VCSAPIServiceEntityMapper.mapGitHubIssueToVCSIssue(null);
		assertNotNull(vcsIssue);
	}

	@Test
	public void testMapGitHubIssueToVCSIssue() {
		VCSIssue vcsIssue = VCSAPIServiceEntityMapper.mapGitHubIssueToVCSIssue(gitHubIssue);
		assertNotNull(vcsIssue);
		assertEquals(vcsIssue.getIssueTitle(), gitHubIssue.getTitle());
		assertEquals(vcsIssue.getIssueDetail(), gitHubIssue.getBody());
	}

	// transformGitHubIssuesToVCSIssues
	@Test
	public void testTransformGitHubIssuesToVCSIssuesUsingSimpleIssueList() {
		VCSIssues vcsIssues = VCSAPIServiceEntityMapper.transformGitHubIssuesToVCSIssues(gitHubIssueList);
		assertNotNull(vcsIssues);
		assertEquals(gitHubIssueList.size(), vcsIssues.getIssues().size());
	}

	@Test
	public void testTransformGitHubIssuesToVCSIssuesUsingIssueNullList() {
		VCSIssues vcsIssues = VCSAPIServiceEntityMapper.transformGitHubIssuesToVCSIssues(gitHubIssueNullList);
		assertNotNull(vcsIssues);
		assertEquals(0, vcsIssues.getIssues().size());
	}

	@Test
	public void testTransformGitHubIssuesToVCSIssueUsingNullInput() {
		VCSIssues vcsIssues = VCSAPIServiceEntityMapper.transformGitHubIssuesToVCSIssues(null);
		assertEquals(0, vcsIssues.getIssues().size());
	}

	@Test
	public void testTransformGitHubRepositoryIssuesToVCSIssuesUsingSimpleList() {
		VCSIssues vcsIssues = VCSAPIServiceEntityMapper
				.transformGitHubRepositoryIssuesToVCSIssues(gitHubRepositoryIssueList);
		assertNotNull(vcsIssues);
		assertEquals(gitHubRepositoryIssueList.size(), vcsIssues.getIssues().size());
	}

	@Test
	public void testTransformGitHubRepositoryIssuesToVCSIssuesUsingIssueNullList() {
		VCSIssues vcsIssues = VCSAPIServiceEntityMapper
				.transformGitHubRepositoryIssuesToVCSIssues(gitHubRepositoryIssueNullList);
		assertNotNull(vcsIssues);
		assertEquals(0, vcsIssues.getIssues().size());
	}
	
	@Test
	public void testTransformGitHubRepositoryIssuesToVCSIssuesUsingNullInput() {
		VCSIssues vcsIssues = VCSAPIServiceEntityMapper
				.transformGitHubRepositoryIssuesToVCSIssues(null);
		assertEquals(0, vcsIssues.getIssues().size());
	}

	@Test
	public void testMapBdOutputToVCSAPIServiceResponseUsingVCSIssueOutput() {
		VCSAPIServiceResponse serviceResponse = VCSAPIServiceEntityMapper
				.mapBdOutputToUserServiceResponse(VCSDelegatorOutput);
		assertNotNull(serviceResponse.getIssue());
	}

	@Test
	public void testMapBdOutputToVCSAPIServiceResponseUsingUnrecognizedOutputObject() {
		VCSAPIServiceResponse serviceResponse = VCSAPIServiceEntityMapper
				.mapBdOutputToUserServiceResponse(unrecognizedVCSDelegatorOutput);
		assertNull(serviceResponse.getIssue());
	}

	@Test
	public void testMapBdOutputToVCSAPIServiceResponseUsingNullInput() {
		VCSAPIServiceResponse serviceResponse = VCSAPIServiceEntityMapper.mapBdOutputToUserServiceResponse(null);
		assertNull(serviceResponse);
	}
}
