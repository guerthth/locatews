package amtc.gue.ws.test.books.persistence.dao;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.EMF;
import amtc.gue.ws.books.persistence.ProductiveEMF;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.dao.tag.impl.TagDAOImpl;
import amtc.gue.ws.books.persistence.model.TagEntity;

/**
 * Testclass for the Tag DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TagDAOTest {

	private static TagDAO tagEntityDAO;
	private static TagDAO failureTagEntityDAO;

	private static TagEntity tagEntity1;
	private static TagEntity tagEntity2;
	private static TagEntity tagEntity3;
	private static TagEntity tagEntity4;

	private static final String TAG_NAME_A = "tagNameA";
	private static final String TAG_NAME_B = "tagNameB";
	private static final String TAG_NAME_C = "tagNameC";

	// top-level point configuration for all local services that might
	// be accessed. set with high replication
	private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(100));

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setupBasicEnvironment();
	}

	@Before
	public void setUp() {
		helper.setUp();
		setupTagEntities();
	}

	@After
	public void tearDown() {
		helper.tearDown();
	}

	@Test
	public void testDAOSetup() {
		assertNotNull(tagEntityDAO);
		assertNotNull(failureTagEntityDAO);
	}

	@Test
	public void testPersistTagEntity1() throws EntityPersistenceException,
			EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		tagEntityDAO.persistEntity(tagEntity3);
		tagEntityDAO.persistEntity(tagEntity4);
		assertNotNull(tagEntity1.getId());
		assertNotNull(tagEntity2.getId());
		assertNotNull(tagEntity3.getId());
		assertNotNull(tagEntity4.getId());
	}

	@Test(expected = EntityPersistenceException.class)
	public void testPersistTagEntity2() throws EntityPersistenceException,
			EntityRetrievalException {
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity1);
	}

	@Test(expected = EntityPersistenceException.class)
	public void testPersistTagEntity3() throws EntityPersistenceException,
			EntityRetrievalException {
		failureTagEntityDAO.persistEntity(tagEntity1);
	}

	@Test
	public void testFindAllEntities1() throws EntityRetrievalException {
		// no entities added so far
		int foundTagEntities = tagEntityDAO.findAllEntities().size();
		assertEquals(0, foundTagEntities);
	}

	@Test
	public void testFindAllEntities2() throws EntityPersistenceException,
			EntityRetrievalException {
		// add 4 entities
		tagEntityDAO.persistEntity(tagEntity1);
		tagEntityDAO.persistEntity(tagEntity2);
		tagEntityDAO.persistEntity(tagEntity3);
		tagEntityDAO.persistEntity(tagEntity4);
		int foundTagEntities = tagEntityDAO.findAllEntities().size();
		assertEquals(4, foundTagEntities);
	}

	@Test(expected = EntityRetrievalException.class)
	public void testFindAllEntities3() throws EntityPersistenceException,
			EntityRetrievalException {
		// add entity and try retrieving when entitymanager is null
		tagEntityDAO.persistEntity(tagEntity1);
		failureTagEntityDAO.findAllEntities().size();
	}

	@Test
	public void testFindSpecificTagEntity1() throws EntityPersistenceException,
			EntityRetrievalException {
		// add entities and search for entity1
		tagEntityDAO.persistEntity(tagEntity1);
		int foundEntities = tagEntityDAO.findSpecificEntity(tagEntity1).size();
		assertEquals(1, foundEntities);
	}

	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificTagEntity2() throws EntityPersistenceException,
			EntityRetrievalException {
		// add entity and search for entity when entitymanager is null
		tagEntityDAO.persistEntity(tagEntity1);
		failureTagEntityDAO.findSpecificEntity(tagEntity1).size();
	}

	/**
	 * Setting up Basic Environment
	 */
	private static void setupBasicEnvironment() {
		EMF emf = new ProductiveEMF();
		tagEntityDAO = new TagDAOImpl(emf);
		failureTagEntityDAO = new TagDAOImpl(null);
	}

	/**
	 * Setting up TagEntities
	 */
	private void setupTagEntities() {
		tagEntity1 = new TagEntity();
		tagEntity1.setTagName(TAG_NAME_A);
		tagEntity2 = new TagEntity();
		tagEntity2.setTagName(TAG_NAME_B);
		tagEntity3 = new TagEntity();
		tagEntity3.setTagName(TAG_NAME_B);
		tagEntity4 = new TagEntity();
		tagEntity4.setTagName(TAG_NAME_C);
	}
}
