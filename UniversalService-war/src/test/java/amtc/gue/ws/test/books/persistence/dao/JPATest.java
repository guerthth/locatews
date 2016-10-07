package amtc.gue.ws.test.books.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.dev.HighRepJobPolicy;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import amtc.gue.ws.books.persistence.EMF;
import amtc.gue.ws.books.persistence.ProductiveEMF;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.dao.book.impl.BookDAOImpl;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.dao.tag.impl.TagDAOImpl;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.service.inout.Tags;
/**
 * Super Testclass for all JPA Testcases
 * 
 * @author Thomas
 *
 */
public abstract class JPATest {

	protected static BookDAO bookEntityDAO;
	protected static BookDAO failureBookEntityDAO;
	protected static TagDAO tagEntityDAO;
	protected static TagDAO failureTagEntityDAO;

	protected GAEJPABookEntity bookEntity1;
	protected GAEJPABookEntity bookEntity2;
	protected GAEJPABookEntity bookEntity3;
	protected GAEJPABookEntity bookEntity4;
	protected GAEJPABookEntity bookEntity5;

	protected static final String TEST_TITLE_A = "testTitleA";
	protected static final String TEST_PRICE_A = "testPriceA";
	protected static final String TEST_ISBN_A = "testISBNA";
	protected static final String TEST_DESCRIPTION_A = "testDescriptionA";
	protected static final String TEST_AUTHOR_A = "testAuthorA";
	protected static final String TEST_TITLE_B = "testTitleB";
	protected static final String TEST_PRICE_B = "testPriceB";
	protected static final String TEST_ISBN_B = "testISBNB";
	protected static final String TEST_DESCRIPTION_B = "testDescriptionB";
	protected static final String TEST_AUTHOR_B = "testAuthorB";

	protected GAEJPATagEntity tagEntity1;
	protected GAEJPATagEntity tagEntity2;
	protected GAEJPATagEntity tagEntity3;
	protected GAEJPATagEntity tagEntity4;

	protected static final String TAG_NAME_A = "tagNameA";
	protected static final String TAG_NAME_B = "tagNameB";
	protected static final String TAG_NAME_C = "tagNameC";
	
	protected Tags tagsAB;
	protected Tags tagsA;
	protected Tags tagsB;

	static final class ConsistentHighRepPolicy implements HighRepJobPolicy {
		@Override
		public boolean shouldRollForwardExistingJob(Key key) {
			return true;
		}

		@Override
		public boolean shouldApplyNewJob(Key key) {
			return true;
		}
	}

	// top-level point configuration for all local services that might
	// be accessed. set with high replication
	// private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
	// new LocalDatastoreServiceTestConfig()
	// .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));
	// private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
	// new LocalDatastoreServiceTestConfig());
	// see http://codeover.org/questions/21185922/app-engine-cloud-endpoints-unit-testing-issues-with-data-nucleus-is-it-possibl
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig()
					.setAlternateHighRepJobPolicyClass(ConsistentHighRepPolicy.class));

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setupBasicEnvironment();
	}

	@Before
	public void setUp() {
		helper.setUp();
		setupBookEntities();
		setupTagEntities();
		setupTags();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	/**
	 * Setting up Basic Environment
	 */
	private static void setupBasicEnvironment() {
		EMF emf = new ProductiveEMF();
		tagEntityDAO = new TagDAOImpl(emf);
		failureTagEntityDAO = new TagDAOImpl(null);
		bookEntityDAO = new BookDAOImpl(emf);
		failureBookEntityDAO = new BookDAOImpl(null);
	}

	/**
	 * Setting up BookEntities
	 */
	private void setupBookEntities() {
		bookEntity1 = new GAEJPABookEntity();
		bookEntity1.setAuthor(TEST_AUTHOR_A);
		bookEntity1.setDescription(TEST_DESCRIPTION_A);
		bookEntity1.setISBN(TEST_ISBN_A);
		bookEntity1.setPrice(TEST_PRICE_A);
		bookEntity1.setTitle(TEST_TITLE_A);

		bookEntity2 = new GAEJPABookEntity();
		bookEntity2.setAuthor(TEST_AUTHOR_B);
		bookEntity2.setDescription(TEST_DESCRIPTION_B);
		bookEntity2.setISBN(TEST_ISBN_B);
		bookEntity2.setPrice(TEST_PRICE_B);
		bookEntity2.setTitle(TEST_TITLE_B);

		bookEntity3 = new GAEJPABookEntity();
		bookEntity4 = new GAEJPABookEntity();
		bookEntity5 = new GAEJPABookEntity();
	}

	/**
	 * Setting up TagEntities
	 */
	private void setupTagEntities() {
		tagEntity1 = new GAEJPATagEntity();
		tagEntity1.setTagName(TAG_NAME_A);
		tagEntity2 = new GAEJPATagEntity();
		tagEntity2.setTagName(TAG_NAME_B);
		tagEntity3 = new GAEJPATagEntity();
		tagEntity3.setTagName(TAG_NAME_B);
		tagEntity4 = new GAEJPATagEntity();
		tagEntity4.setTagName(TAG_NAME_C);
	}
	
	/**
	 * Setting up Tags object
	 */
	private void setupTags() {
		tagsAB = new Tags();
		tagsAB.getTags().add(TAG_NAME_A);
		tagsAB.getTags().add(TAG_NAME_B);
		tagsA = new Tags();
		tagsA.getTags().add(TAG_NAME_A);
		tagsB = new Tags();
		tagsB.getTags().add(TAG_NAME_B);
	}
}
