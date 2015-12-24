package amtc.gue.ws.test.books;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import amtc.gue.ws.test.books.persistence.dao.BookDAOTest;
import amtc.gue.ws.test.books.utils.BookPersistenceDelegatorUtilsTest;
import amtc.gue.ws.test.books.utils.EntityMapperTest;
import amtc.gue.ws.test.books.utils.dao.BookDAOImplUtilsTest;

@RunWith(Suite.class)
@SuiteClasses({ EntityMapperTest.class, BookDAOTest.class,
		/*BookPersistenceDelegatorTest.class, BookGrabberTest.class,*/
		BookDAOImplUtilsTest.class, BookPersistenceDelegatorUtilsTest.class })
public class CompleteTestSuit {

}
