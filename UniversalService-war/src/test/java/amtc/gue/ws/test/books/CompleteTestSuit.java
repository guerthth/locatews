package amtc.gue.ws.test.books;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import amtc.gue.ws.test.books.delegate.persist.BookPersistenceDelegatorTest;
import amtc.gue.ws.test.books.delegate.persist.TagPersistenceDelegatorTest;
import amtc.gue.ws.test.books.persistence.dao.BookDAOTest;
import amtc.gue.ws.test.books.persistence.dao.TagDAOTest;
import amtc.gue.ws.test.books.service.BookGrabberTest;
import amtc.gue.ws.test.books.utils.BookPersistenceDelegatorUtilsTest;
import amtc.gue.ws.test.books.utils.EntityMapperTest;
import amtc.gue.ws.test.books.utils.TagPersistenceDelegatorUtilsTest;
import amtc.gue.ws.test.books.utils.dao.BookDAOImplUtilsTest;
import amtc.gue.ws.test.books.utils.dao.TagDAOImplUtilsTest;

@RunWith(Suite.class)
@SuiteClasses({ EntityMapperTest.class, BookDAOTest.class, TagDAOTest.class,
		BookPersistenceDelegatorTest.class, TagPersistenceDelegatorTest.class,
		BookGrabberTest.class, BookDAOImplUtilsTest.class,
		TagDAOImplUtilsTest.class, BookPersistenceDelegatorUtilsTest.class,
		TagPersistenceDelegatorUtilsTest.class })
public class CompleteTestSuit {

}
