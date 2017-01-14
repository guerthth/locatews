package amtc.gue.ws.test.base.persistence.dao;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.model.GAEJPARoleEntity;

/**
 * Testclass for the Role DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleDAOJPATest extends UserServiceJPATest {

	@Override
	public void testDAOSetup() {
		assertNotNull(roleEntityDAO);
		assertNotNull(failureRoleEntityDAO);
	}

	@Override
	public void testAddEntity() throws EntityPersistenceException {
		assertNotNull(roleEntity1.getKey());
		roleEntityDAO.persistEntity(roleEntity1);
		assertNotNull(roleEntity1.getKey());
	}

	@Override
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		roleEntityDAO.persistEntity(roleEntity1);
		roleEntityDAO.persistEntity(roleEntity1);
	}

	@Override
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureRoleEntityDAO.persistEntity(roleEntity1);
	}

	@Override
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		assertEquals(0, roleEntityDAO.findAllEntities().size());
	}

	@Override
	public void testGetEntitiesAfterAddingSimpleEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		roleEntityDAO.persistEntity(roleEntity1);
		roleEntityDAO.persistEntity(roleEntity2);
		assertEquals(2, roleEntityDAO.findAllEntities().size());
	}

	@Override
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureRoleEntityDAO.findAllEntities();
	}

	@Override
	public void testGetEntityById() throws EntityRetrievalException,
			EntityPersistenceException {
		roleEntityDAO.persistEntity(roleEntity1);
		GAEJPARoleEntity retrievedEntity = roleEntityDAO
				.findEntityById(roleEntity1.getKey());
		assertEquals(roleEntity1.getKey(), retrievedEntity.getKey());
	}

	@Override
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		roleEntityDAO.findEntityById(null);
	}

	@Override
	public void testGetEntityByIdUsingInvalidEM()
			throws EntityPersistenceException, EntityRetrievalException {
		roleEntityDAO.persistEntity(roleEntity1);
		failureRoleEntityDAO.findEntityById(roleEntity1.getKey());
	}

	@Override
	public void testDeleteEntity() throws EntityRetrievalException,
			EntityRemovalException, EntityPersistenceException {
		roleEntityDAO.persistEntity(roleEntity1);
		roleEntityDAO.persistEntity(roleEntity2);
		assertEquals(2, roleEntityDAO.findAllEntities().size());
		roleEntityDAO.removeEntity(roleEntity1);
		assertEquals(1, roleEntityDAO.findAllEntities().size());
	}

	@Override
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		roleEntityDAO.removeEntity(roleEntity1);
	}

	@Override
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureRoleEntityDAO.removeEntity(roleEntity1);
	}

	@Override
	public void testUpdateSimpleEntity() throws EntityRetrievalException,
			EntityPersistenceException {
		roleEntityDAO.persistEntity(roleEntity1);
		assertNull(roleEntity1.getDescription());
		String entityKey = roleEntity1.getKey();

		roleEntity1.setDescription(DESCRIPTION);
		roleEntityDAO.updateEntity(roleEntity1);
		assertEquals(entityKey, roleEntity1.getKey());

		GAEJPARoleEntity retrievedEntity = roleEntityDAO
				.findEntityById(roleEntity1.getKey());
		assertEquals(DESCRIPTION, retrievedEntity.getDescription());
	}

	@Override
	public void testUpdateEntityWithoutAdding()
			throws EntityPersistenceException {
		roleEntityDAO.persistEntity(roleEntity1);
		try {
			roleEntityDAO.removeEntity(roleEntity1);
		} catch (EntityRemovalException e) {
			fail();
		}
		roleEntityDAO.updateEntity(roleEntity1);
	}

	@Override
	public void testUpdateEntityUsingInvalidEM()
			throws EntityPersistenceException {
		failureRoleEntityDAO.updateEntity(roleEntity1);
	}

	@Override
	public void testFindSpecificEntity() throws EntityRetrievalException,
			EntityPersistenceException {
		assertEquals(0, roleEntityDAO.findSpecificEntity(roleEntity3).size());
	}

	@Override
	public void testFindSpecificEntityBySearchCriteria()
			throws EntityRetrievalException, EntityPersistenceException {
		roleEntityDAO.persistEntity(roleEntity1);
		roleEntityDAO.persistEntity(roleEntity2);
		GAEJPARoleEntity searchRole = new GAEJPARoleEntity();
		searchRole.setKey(ROLE_A);
		assertEquals(1, roleEntityDAO.findSpecificEntity(searchRole).size());
		searchRole.setKey(ROLE_B);
		assertEquals(1, roleEntityDAO.findSpecificEntity(searchRole).size());
	}

	@Override
	public void testFindSpecificEntityBeforeAdding()
			throws EntityRetrievalException {
		assertEquals(0, roleEntityDAO.findSpecificEntity(roleEntity1).size());
	}

	@Override
	public void testFindSpecificEntityUsingInvalidEM()
			throws EntityRetrievalException {
		failureRoleEntityDAO.findSpecificEntity(roleEntity1);
	}

}
