package amtc.gue.ws.test.base.persistence.dao;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.dev.HighRepJobPolicy;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/**
 * Abstract Class holding all methods that should be used for testing DAOs. The
 * implementation is done in the specific testclasses
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class BaseDAOTest {
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
	// see
	// http://codeover.org/questions/21185922/app-engine-cloud-endpoints-unit-testing-issues-with-data-nucleus-is-it-possibl
	protected final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig()
					.setAlternateHighRepJobPolicyClass(ConsistentHighRepPolicy.class));

	/**
	 * Testing basic DAO Setup
	 */
	@Test
	public abstract void testDAOSetup();

	/**
	 * Test adding simple Entity
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public abstract void testAddEntity() throws EntityPersistenceException;

	/**
	 * Test adding same entity twice
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test(expected = EntityPersistenceException.class)
	public abstract void testAddSameEntityTwice()
			throws EntityPersistenceException;

	/**
	 * Test adding an entity using an invalid EM
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test(expected = EntityPersistenceException.class)
	public abstract void testAddEntityUsingInvalidEM()
			throws EntityPersistenceException;

	/**
	 * Test retrieval of entities without adding any
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public abstract void testGetEntitiesWithoutAdding()
			throws EntityRetrievalException;

	/**
	 * Test retrieval of entities after adding some
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public abstract void testGetEntitiesAfterAddingSimpleEntities()
			throws EntityPersistenceException, EntityRetrievalException;

	/**
	 * Testing retrieval of entities using an invalid EM
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public abstract void testGetEntitiesUsingInvalidEM()
			throws EntityRetrievalException;

	/**
	 * Test retrieval of specific entity by ID
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public abstract void testGetEntityById() throws EntityRetrievalException,
			EntityPersistenceException;

	/**
	 * Test retrieval of specific entity by ID using null ID as input
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public abstract void testGetEntityByIdUsingNull()
			throws EntityRetrievalException;

	/**
	 * Test retrieval of specific entity by ID using invalid EM
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public abstract void testGetEntityByIdUsingInvalidEM()
			throws EntityPersistenceException, EntityRetrievalException;

	/**
	 * Test removal of entity
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public abstract void testDeleteEntity() throws EntityRetrievalException,
			EntityRemovalException, EntityPersistenceException;

	/**
	 * Test removal of entity without adding this entity
	 * 
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 */
	@Test(expected = EntityRemovalException.class)
	public abstract void testDeleteEntityWithoutAdding()
			throws EntityRemovalException;

	/**
	 * Test removal of entity using an invalid EM
	 * 
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 */
	@Test(expected = EntityRemovalException.class)
	public abstract void testDeleteEntityUsingInvalidEM()
			throws EntityRemovalException;

	/**
	 * Test updating a simple entity
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public abstract void testUpdateSimpleEntity()
			throws EntityRetrievalException, EntityPersistenceException;

	/**
	 * Test updating entity without adding this entity
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test(expected = EntityPersistenceException.class)
	public abstract void testUpdateEntityWithoutAdding()
			throws EntityPersistenceException;

	/**
	 * Test updating entity using an invalid EM
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test(expected = EntityPersistenceException.class)
	public abstract void testUpdateEntityUsingInvalidEM()
			throws EntityPersistenceException;

	/**
	 * Test finding a specific entity
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public abstract void testFindSpecificEntity()
			throws EntityRetrievalException, EntityPersistenceException;

	/**
	 * Test finding a specific entity using common search criteria
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public abstract void testFindSpecificEntityBySearchCriteria()
			throws EntityRetrievalException, EntityPersistenceException;

	/**
	 * Test finding a specific entity before adding that entity
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public abstract void testFindSpecificEntityBeforeAdding()
			throws EntityRetrievalException;

	/**
	 * Test finding a specific entity using an invalid EM
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public abstract void testFindSpecificEntityUsingInvalidEM()
			throws EntityRetrievalException;
}
