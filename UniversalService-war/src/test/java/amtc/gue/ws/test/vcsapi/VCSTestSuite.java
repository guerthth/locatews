package amtc.gue.ws.test.vcsapi;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import amtc.gue.ws.test.vcsapi.delegate.GithubAPIDelegatorTest;
import amtc.gue.ws.test.vcsapi.util.VCSAPIServiceEntityMapperTest;

@RunWith(Suite.class)
@SuiteClasses({ GithubAPIServiceTest.class, GithubAPIDelegatorTest.class, VCSAPIServiceEntityMapperTest.class })
public class VCSTestSuite {

}
