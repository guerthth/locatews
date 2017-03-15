package amtc.gue.ws.test.base;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import amtc.gue.ws.test.base.delegate.mail.UserMailDelegatorTest;
import amtc.gue.ws.test.base.delegate.persist.UserPersistenceDelegatorTest;
import amtc.gue.ws.test.base.persistence.dao.RoleDAOJPATest;
import amtc.gue.ws.test.base.persistence.dao.UserDAOJPATest;
import amtc.gue.ws.test.base.util.CSVMapperTest;
import amtc.gue.ws.test.base.util.EncryptionMapperTest;
import amtc.gue.ws.test.base.util.HtmlMapperTest;
import amtc.gue.ws.test.base.util.StatusMapperTest;
import amtc.gue.ws.test.base.util.UserPersistenceDelegatorUtilTest;
import amtc.gue.ws.test.base.util.UserServiceEntityMapperTest;
import amtc.gue.ws.test.base.util.dao.RoleDAOImplUtilTest;
import amtc.gue.ws.test.base.util.dao.UserDAOImplUtilTest;

@RunWith(Suite.class)
@SuiteClasses({ CSVMapperTest.class, HtmlMapperTest.class, EncryptionMapperTest.class, StatusMapperTest.class,
		UserServiceTest.class, UserPersistenceDelegatorTest.class, UserPersistenceDelegatorUtilTest.class,
		UserServiceEntityMapperTest.class, UserDAOJPATest.class, UserDAOImplUtilTest.class, RoleDAOJPATest.class,
		RoleDAOImplUtilTest.class, UserMailDelegatorTest.class })
public class BaseTestSuite {

}
