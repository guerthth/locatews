package amtc.gue.ws.test.books;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import amtc.gue.ws.test.books.delegate.persist.BookPersistenceDelegatorTest;
import amtc.gue.ws.test.books.delegate.persist.TagPersistenceDelegatorTest;
import amtc.gue.ws.test.books.persistence.dao.jpa.BookDAOJPATest;
import amtc.gue.ws.test.books.persistence.dao.jpa.TagDAOJPATest;
import amtc.gue.ws.test.books.persistence.dao.objectify.BookDAOObjectifyTest;
import amtc.gue.ws.test.books.persistence.dao.objectify.TagDAOObjectifyTest;
import amtc.gue.ws.test.books.util.BookPersistenceDelegatorUtilTest;
import amtc.gue.ws.test.books.util.TagPersistenceDelegatorUtilTest;
import amtc.gue.ws.test.books.util.dao.BookDAOImplUtilTest;
import amtc.gue.ws.test.books.util.dao.TagDAOImplUtilTest;
import amtc.gue.ws.test.books.util.mapper.BookServiceEntityMapperTest;
import amtc.gue.ws.test.service.soap.BookGrabberTest;

@RunWith(Suite.class)
@SuiteClasses({ BookGrabberTest.class, BookServiceTest.class, BookPersistenceDelegatorTest.class,
		BookPersistenceDelegatorUtilTest.class, BookServiceEntityMapperTest.class, BookDAOJPATest.class,
		BookDAOObjectifyTest.class, BookDAOImplUtilTest.class, TagServiceTest.class, TagPersistenceDelegatorTest.class,
		TagPersistenceDelegatorUtilTest.class, TagDAOJPATest.class, TagDAOObjectifyTest.class,
		TagDAOImplUtilTest.class })
public class BookTestSuite {

}
