package amtc.gue.ws.test.base.persistence.dao.jpa;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.role.jpa.GAEJPARoleEntity;
import amtc.gue.ws.test.base.UserTest;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;

/**
 * Testclass for the Role JPA DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleDAOJPATest extends UserTest implements IBaseDAOTest {
	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicEnvironment();
	}

	@Override
	@Test
	public void testDAOSetup() {
		assertNotNull(userJPADAO);
		assertNotNull(failureRoleJPADAO);
		assertNotNull(roleJPADAO);
	}

	@Override
	@Test
	public void testAddEntity() throws EntityPersistenceException {
		assertNotNull(JPARoleEntity1.getKey());
		roleJPADAO.persistEntity(JPARoleEntity1);
		assertNotNull(JPARoleEntity1.getKey());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		roleJPADAO.persistEntity(JPARoleEntity1);
		roleJPADAO.persistEntity(JPARoleEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureRoleJPADAO.persistEntity(JPARoleEntity1);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		assertEquals(0, roleJPADAO.findAllEntities().size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		roleJPADAO.persistEntity(JPARoleEntity1);
		roleJPADAO.persistEntity(JPARoleEntity2);
		assertEquals(2, roleJPADAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureRoleJPADAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		roleJPADAO.persistEntity(JPARoleEntity1);
		GAERoleEntity retrievedEntity = roleJPADAO.findEntityById(JPARoleEntity1.getKey());
		assertEquals(JPARoleEntity1.getKey(), retrievedEntity.getKey());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		roleJPADAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failureRoleJPADAO.findEntityById(JPARoleEntity1.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		roleJPADAO.persistEntity(JPARoleEntity1);
		roleJPADAO.persistEntity(JPARoleEntity2);
		assertEquals(2, roleJPADAO.findAllEntities().size());
		roleJPADAO.removeEntity(JPARoleEntity1);
		assertEquals(1, roleJPADAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		roleJPADAO.removeEntity(JPARoleEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		roleJPADAO.removeEntity(JPARoleEntity3);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureRoleJPADAO.removeEntity(JPARoleEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		roleJPADAO.persistEntity(JPARoleEntity1);
		assertNull(JPARoleEntity1.getDescription());
		String entityKey = JPARoleEntity1.getKey();

		JPARoleEntity1.setDescription(DESCRIPTION);
		roleJPADAO.updateEntity(JPARoleEntity1);
		assertEquals(entityKey, JPARoleEntity1.getKey());

		GAERoleEntity retrievedEntity = roleJPADAO.findEntityById(JPARoleEntity1.getKey());
		assertEquals(DESCRIPTION, retrievedEntity.getDescription());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		roleJPADAO.persistEntity(JPARoleEntity1);
		try {
			roleJPADAO.removeEntity(JPARoleEntity1);
		} catch (EntityRemovalException e) {
			fail();
		}
		roleJPADAO.updateEntity(JPARoleEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failureRoleJPADAO.updateEntity(JPARoleEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		assertEquals(0, roleJPADAO.findSpecificEntity(JPARoleEntity3).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		roleJPADAO.persistEntity(JPARoleEntity1);
		roleJPADAO.persistEntity(JPARoleEntity2);
		GAEJPARoleEntity searchRole = new GAEJPARoleEntity();
		searchRole.setKey(ROLE);
		assertEquals(1, roleJPADAO.findSpecificEntity(searchRole).size());
		searchRole.setKey(ROLE_B);
		assertEquals(1, roleJPADAO.findSpecificEntity(searchRole).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, roleJPADAO.findSpecificEntity(JPARoleEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failureRoleJPADAO.findSpecificEntity(JPARoleEntity1);
	}
}