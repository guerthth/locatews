package amtc.gue.ws.test.books;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import amtc.gue.ws.test.books.delegate.persist.BookPersistenceDelegatorTest;
import amtc.gue.ws.test.books.persistence.dao.BookDAOTest;
import amtc.gue.ws.test.books.service.BookGrabberTest;
import amtc.gue.ws.test.books.utils.BookDAOImplUtilsTest;
import amtc.gue.ws.test.books.utils.EntityMapperTest;

@RunWith(Suite.class)
@SuiteClasses({ EntityMapperTest.class, BookDAOTest.class,
		BookPersistenceDelegatorTest.class, BookGrabberTest.class,
		BookDAOImplUtilsTest.class })
public class CompleteTestSuit {

}
