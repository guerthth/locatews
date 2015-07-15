//package amtc.gue.ws.test.books.persistence.dao;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertNull;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//import java.util.List;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;
//
//import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
//import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
//
//import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
//import amtc.gue.ws.books.delegate.persist.exception.EntityRemovalException;
//import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
//import amtc.gue.ws.books.persistence.dao.impl.BookEntityDAOImpl;
//import amtc.gue.ws.books.persistence.model.BookEntity;
//import amtc.gue.ws.books.utils.SpringContext;
//
///**
// * Testclass for the Book DAO
// * 
// * @author Thomas
// *
// */
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class BookEntityDAOImplTest {
//
//	private static BookEntityDAOImpl bookEntityDAO;
//
//	private BookEntity be1;
//	private BookEntity be2;
//
//	private final String searchTag1 = "testtag";
//	
//	// top-level point configuration for all local services that might
//	// be accessed
//	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
//
//	@BeforeClass
//	public static void oneTimeSetUp() {
//
//		// create DAO instance
//		bookEntityDAO = (BookEntityDAOImpl) SpringContext.context.getBean("bookEntityDAOImpl");
//
//	}
//
//	@Before
//	public void setUp() {
//		helper.setUp();
//
//		// intitialize some book entities
//		be1 = new BookEntity();
//		be1.setAuthor("Testauthor1");
//		be1.setDescription("Testdescription1");
//		be1.setISBN("TestISBN");
//		be1.setPrice("100");
//		be1.setTags(searchTag1);
//		be1.setTitle("Testtitle1");
//
//		be2 = new BookEntity();
//		be2.setAuthor("Testauthor2");
//		be2.setDescription("Testdescription2");
//		be2.setISBN("TestISBN");
//		be2.setPrice("100");
//		be2.setTags(searchTag1);
//		be2.setTitle("Testtitle2");
//	}
//
//	@After
//	public void tearDown() {
//		helper.tearDown();
//	}
//
//	@AfterClass
//	public static void oneTimeTearDown() {
//
//	}
//
//	@Test
//	public void testDAOSetUp() {
//		assertNotNull(bookEntityDAO);
//	}
//
//	@Test
//	public void testAddBookEntity1() {
//
//		try {
//			// add be1 to datastore should work
//			BookEntity result = new BookEntity();
//			assertNull(result.getId());
//			result = bookEntityDAO.addBookEntity(be1);
//			assertNotNull(result);
//			assertNotNull(result.getId());
//			assertEquals(1, bookEntityDAO.getAllBookEntities().size());
//		} catch (EntityPersistenceException e) {
//			// let testcase fail if exception is thrown
//			fail(e.getMessage());
//		}
//	}
//
//	@Test(expected = EntityPersistenceException.class)
//	public void testAddBookEntity2() throws EntityPersistenceException {
//
//		// add be1 to the datastore two times
//		// EntityPersistenceException is expected
//		bookEntityDAO.addBookEntity(be1);
//		bookEntityDAO.addBookEntity(be1);
//
//	}
//
//	@Test
//	public void testGetAllBookEntities1() {
//		assertEquals(0, bookEntityDAO.getAllBookEntities().size());
//	}
//
//	@Test
//	public void testGetBookEntity1() {
//
//		try {
//			// add be1 to datastore
//			BookEntity result = bookEntityDAO.addBookEntity(be1);
//			Long id = result.getId();
//			assertNotNull(id);
//			// retrieve persisted item and compare
//			BookEntity foundEntity = bookEntityDAO.getBookEntity(id);
//			assertEquals(result.getId(), foundEntity.getId());
//			assertEquals(result.getTitle(), foundEntity.getTitle());
//			assertEquals(result.getISBN(), foundEntity.getISBN());
//			assertEquals(result.getPrice(), foundEntity.getPrice());
//			assertEquals(result.getTags(), foundEntity.getTags());
//		} catch (Exception e) {
//			// let testcase fail if exception is thrown
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void testGetBookEntity2() {
//
//		try {
//			// add be1 and be2 to datastore
//			bookEntityDAO.addBookEntity(be1);
//			bookEntityDAO.addBookEntity(be2);
//			assertEquals(2, bookEntityDAO.getAllBookEntities().size());
//			// retrieve persisted item and compare
//			BookEntity foundEntity = bookEntityDAO.getBookEntity(be2.getId());
//			assertEquals(be2.getId(), foundEntity.getId());
//			assertEquals(be2.getTitle(), foundEntity.getTitle());
//			assertEquals(be2.getISBN(), foundEntity.getISBN());
//			assertEquals(be2.getPrice(), foundEntity.getPrice());
//			assertEquals(be2.getTags(), foundEntity.getTags());
//		} catch (Exception e) {
//			// let testcase fail if exception is thrown
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void testDeleteBookEntity1() {
//		try {
//			// add be1 to datastore
//			bookEntityDAO.addBookEntity(be1);
//			assertEquals(1, bookEntityDAO.getAllBookEntities().size());
//			// delete be1
//			bookEntityDAO.deleteBookEntity(be1);
//			assertEquals(0, bookEntityDAO.getAllBookEntities().size());
//		} catch (Exception e) {
//			// let testcase fail if exception is thrown
//			fail(e.getMessage());
//		}
//	}
//
//	@Test
//	public void testDeleteBookEntity2() {
//		try {
//			// add be1 and be2 to datastore
//			bookEntityDAO.addBookEntity(be1);
//			bookEntityDAO.addBookEntity(be2);
//			assertEquals(2, bookEntityDAO.getAllBookEntities().size());
//			// delete be2
//			bookEntityDAO.deleteBookEntity(be2);
//			assertEquals(1, bookEntityDAO.getAllBookEntities().size());
//		} catch (Exception e) {
//			fail(e.getMessage());
//		}
//	}
//
//	@Test(expected = EntityRemovalException.class)
//	public void testDeleteBookEntity3() throws EntityRemovalException {
//		// remove be1 without adding it before
//		bookEntityDAO.deleteBookEntity(be1);
//
//	}
//	
//	@Test
//	public void testUpdateBookEntity1(){
//		try {
//			// add be1
//			BookEntity addedEntity = bookEntityDAO.addBookEntity(be1);
//			assertEquals(1, bookEntityDAO.getAllBookEntities().size());
//			
//			// update be1
//			addedEntity.setAuthor("UpdatedTestauthor1");
//			addedEntity.setDescription("UpdatedTestdescription1");
//			addedEntity.setISBN("UpdatedTestISBN");
//			addedEntity.setPrice("200");
//			addedEntity.setTags("Updatedtesttag");
//			addedEntity.setTitle("UpdatedTesttitle1");
//			bookEntityDAO.updateBookEntity(addedEntity);
//			assertEquals(1, bookEntityDAO.getAllBookEntities().size());
//			
//			// retrieve that item from DB and check some values
//			BookEntity retrievedEntity = bookEntityDAO.getBookEntity(addedEntity.getId());
//			assertTrue(addedEntity.getAuthor().equals(retrievedEntity.getAuthor()));
//			assertTrue(addedEntity.getTitle().equals(retrievedEntity.getTitle()));
//			
//		} catch (Exception e) {
//			fail(e.getMessage());
//		} 
//	}
//	
//	@Test(expected = EntityPersistenceException.class)
//	public void testUpdatedBookEntity2() throws EntityPersistenceException{
//		// try updating be1 without initially adding
//		bookEntityDAO.updateBookEntity(be1);
//	}
//	
//	@Test
//	public void testGetEntityByTag1(){
//		try {
//			// add be1 and be2 and search for searchTag1
//			bookEntityDAO.addBookEntity(be1);
//			bookEntityDAO.addBookEntity(be2);
//			List<BookEntity> foundBooks = bookEntityDAO.getBookEntityByTag(searchTag1);
//			assertEquals(2,foundBooks.size());
//		} catch (Exception e) {
//			e.printStackTrace();
//			fail(e.getMessage());
//		} 
//		
//	}
//	
//	@Test
//	public void testGetEntityByTag2() throws EntityRetrievalException{
//		// search for searchTag1 without adding anything to the store
//		List<BookEntity> foundBooks = bookEntityDAO.getBookEntityByTag(searchTag1);
//		assertEquals(0, foundBooks.size());
//	}
//
//}
