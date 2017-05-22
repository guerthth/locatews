package amtc.gue.ws.test.vcsapi.util;

import static org.junit.Assert.*;

import java.io.IOException;

import org.eclipse.egit.github.core.Issue;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.test.vcsapi.VCSAPITest;
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
public class VCSAPIServiceEntityMapperTest extends VCSAPITest {
	@BeforeClass
	public static void oneTimeInitialSetup() throws IOException {
		setUpBasicEnvironment();
	}

	/**
	 * Method testing mapping VCSIssue to GitHubIssue using null value
	 */
	@Test
	public void testMapVCSIssueToGitHubIssueUsingNullValue() {
		Issue issue = VCSAPIServiceEntityMapper.mapVCSIssueToGitHubIssue(null);
		assertNotNull(issue);
	}

	/**
	 * Method testing mapping VCSIssue to GitHubIssue positive scenario
	 */
	@Test
	public void testMapVCSIssueToGitHubIssue() {
		Issue issue = VCSAPIServiceEntityMapper.mapVCSIssueToGitHubIssue(vcsIssue);
		assertNotNull(issue);
		assertEquals(issue.getTitle(), vcsIssue.getIssueTitle());
		assertEquals(issue.getBody(), vcsIssue.getIssueDetail());
	}

	/**
	 * Method testing mapping GitHUbIssue to VCSIssue using null value
	 */
	@Test
	public void testMapGitHubIssueToVCSIssueUsingNullValue() {
		VCSIssue vcsIssue = VCSAPIServiceEntityMapper.mapGitHubIssueToVCSIssue(null);
		assertNotNull(vcsIssue);
	}

	/**
	 * Method testing mapping GitHubIssue to VCSIssue positive scenario
	 */
	@Test
	public void testMapGitHubIssueToVCSIssue() {
		VCSIssue vcsIssue = VCSAPIServiceEntityMapper.mapGitHubIssueToVCSIssue(githubIssue);
		assertNotNull(vcsIssue);
		assertEquals(vcsIssue.getIssueTitle(), githubIssue.getTitle());
		assertEquals(vcsIssue.getIssueDetail(), githubIssue.getBody());
	}

	/**
	 * Method testing transformation of GitHubIssues to VCSIssus using simple
	 * list
	 */
	@Test
	public void testTransformGitHubIssuesToVCSIssuesUsingSimpleIssueList() {
		VCSIssues vcsIssues = VCSAPIServiceEntityMapper.transformGitHubIssuesToVCSIssues(githubIssueList);
		assertNotNull(vcsIssues);
		assertEquals(githubIssueList.size(), vcsIssues.getIssues().size());
	}

	/**
	 * Method testing transformation of GitHubIssues to VCSIssues using null
	 * list
	 */
	@Test
	public void testTransformGitHubIssuesToVCSIssuesUsingIssueNullList() {
		VCSIssues vcsIssues = VCSAPIServiceEntityMapper.transformGitHubIssuesToVCSIssues(githubIssueNullList);
		assertNotNull(vcsIssues);
		assertEquals(0, vcsIssues.getIssues().size());
	}

	/**
	 * Method testing transforation of GitHubIssues to VCSIssues using null
	 * input
	 */
	@Test
	public void testTransformGitHubIssuesToVCSIssueUsingNullInput() {
		VCSIssues vcsIssues = VCSAPIServiceEntityMapper.transformGitHubIssuesToVCSIssues(null);
		assertEquals(0, vcsIssues.getIssues().size());
	}

	/**
	 * Method testing transformation of GitHubRepositoryIssues to VCSIssues
	 * using simple list
	 */
	@Test
	public void testTransformGitHubRepositoryIssuesToVCSIssuesUsingSimpleList() {
		VCSIssues vcsIssues = VCSAPIServiceEntityMapper
				.transformGitHubRepositoryIssuesToVCSIssues(githubRepositoryIssueList);
		assertNotNull(vcsIssues);
		assertEquals(githubRepositoryIssueList.size(), vcsIssues.getIssues().size());
	}

	/**
	 * Method testing transformation of GitHubRepositoryIssues to VCSIssues
	 * using null list
	 */
	@Test
	public void testTransformGitHubRepositoryIssuesToVCSIssuesUsingIssueNullList() {
		VCSIssues vcsIssues = VCSAPIServiceEntityMapper
				.transformGitHubRepositoryIssuesToVCSIssues(githubRepositoryIssueNullList);
		assertNotNull(vcsIssues);
		assertEquals(0, vcsIssues.getIssues().size());
	}

	/**
	 * Method testing transformation of GitubRepositoryIssues to VCSIssues using
	 * null input
	 */
	@Test
	public void testTransformGitHubRepositoryIssuesToVCSIssuesUsingNullInput() {
		VCSIssues vcsIssues = VCSAPIServiceEntityMapper.transformGitHubRepositoryIssuesToVCSIssues(null);
		assertEquals(0, vcsIssues.getIssues().size());
	}

	/**
	 * Method testing mapping of delegator output to VCSAPIServiceResponse using
	 * VCSIssueOutput
	 */
	@Test
	public void testMapBdOutputToVCSAPIServiceResponseUsingVCSIssueOutput() {
		VCSAPIServiceResponse serviceResponse = VCSAPIServiceEntityMapper
				.mapBdOutputToUserServiceResponse(VCSDelegatorOutput);
		assertNotNull(serviceResponse.getIssue());
	}

	/**
	 * Method testing mapping delegator output to VCSAPOServiceResponse using
	 * unrecognized output
	 */
	@Test
	public void testMapBdOutputToVCSAPIServiceResponseUsingUnrecognizedOutputObject() {
		VCSAPIServiceResponse serviceResponse = VCSAPIServiceEntityMapper
				.mapBdOutputToUserServiceResponse(unrecognizedVCSDelegatorOutput);
		assertNull(serviceResponse.getIssue());
	}

	/**
	 * Method testing mappin delegator output to VCSAPIServiceResponse using
	 * null input
	 */
	@Test
	public void testMapBdOutputToVCSAPIServiceResponseUsingNullInput() {
		VCSAPIServiceResponse serviceResponse = VCSAPIServiceEntityMapper.mapBdOutputToUserServiceResponse(null);
		assertNull(serviceResponse);
	}
}
