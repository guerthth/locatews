package dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import amtc.gue.ws.books.dao.impl.BookEntityDAOImpl;
import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.utils.SpringContext;

/**
 * Testclass for the BookEntity DAO Implementation
 * 
 * @author Thomas
 *
 */
public class BookEntityDAOTest {

	// BookEntityDAOImpl instance for testing
	private static BookEntityDAOImpl bookEntityDAO;
	
	/**
	 * Setup test objects
	 */
	@BeforeClass
	public static void setUp(){
		
		bookEntityDAO = (BookEntityDAOImpl) SpringContext.context.getBean("bookEntityDAOImpl");
		
		
	}
	
	@Test
	public void testGetAllBookEntries(){
		
		List<BookEntity> bookList = bookEntityDAO.getAllBookEntities();
		
		assertEquals(0, bookList.size());
		
	}
	
	@Test
	public void testTest(){
		assertEquals(1,1);
	}
}
