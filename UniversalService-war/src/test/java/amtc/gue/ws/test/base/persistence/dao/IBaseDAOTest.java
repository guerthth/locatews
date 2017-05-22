package amtc.gue.ws.test.base.persistence.dao;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;

/**
 * Interface holding all methods that should be applied for DAO testing
 * 
 * @author Thomas
 *
 */
public interface IBaseDAOTest {
	/**
	 * Testing basic DAO Setup
	 */
	public void testDAOSetup();

	/**
	 * Test adding simple Entity
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	public void testAddEntity() throws EntityPersistenceException;

	/**
	 * Test adding same entity twice
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	public void testAddSameEntityTwice() throws EntityPersistenceException;

	/**
	 * Test adding an entity using an invalid EM
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException;

	/**
	 * Test retrieval of entities without adding any
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException;

	/**
	 * Test retrieval of entities after adding some
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException;

	/**
	 * Testing retrieval of entities using an invalid EM
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException;

	/**
	 * Test retrieval of specific entity by ID
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException;

	/**
	 * Test retrieval of specific entity by ID using null ID as input
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException;

	/**
	 * Test retrieval of specific entity by ID using invalid EM
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException;

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
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException;

	/**
	 * Test removal of entity without adding this entity
	 * 
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 */
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException;
	
	/**
	 * Test removal of entity using a null ID object
	 * 
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 */
	public void testDeleteEntityWithoutId() throws EntityRemovalException;

	/**
	 * Test removal of entity using an invalid EM
	 * 
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 */
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException;

	/**
	 * Test updating a simple entity
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException;

	/**
	 * Test updating entity without adding this entity
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException;

	/**
	 * Test updating entity using an invalid EM
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException;

	/**
	 * Test finding a specific entity
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException;

	/**
	 * Test finding a specific entity using common search criteria
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException;

	/**
	 * Test finding a specific entity before adding that entity
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException;

	/**
	 * Test finding a specific entity using an invalid EM
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException;
}
