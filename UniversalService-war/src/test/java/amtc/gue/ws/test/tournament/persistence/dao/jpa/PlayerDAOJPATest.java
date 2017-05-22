package amtc.gue.ws.test.tournament.persistence.dao.jpa;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;
import amtc.gue.ws.test.tournament.TournamentTest;
import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;
import amtc.gue.ws.tournament.persistence.model.player.jpa.GAEJPAPlayerEntity;

/**
 * Testclass for the Player JPA DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerDAOJPATest extends TournamentTest implements IBaseDAOTest {
	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicTournamentEnvironment();
	}

	@Override
	@Test
	public void testDAOSetup() {
		assertNotNull(playerJPADAO);
		assertNotNull(failurePlayerJPADAO);
	}

	@Override
	@Test
	public void testAddEntity() throws EntityPersistenceException {
		assertNotNull(JPAPlayerEntity1.getKey());
		playerJPADAO.persistEntity(JPAPlayerEntity1);
		assertNotNull(JPAPlayerEntity1.getKey());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		playerJPADAO.persistEntity(JPAPlayerEntity1);
		playerJPADAO.persistEntity(JPAPlayerEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failurePlayerJPADAO.persistEntity(JPAPlayerEntity1);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		assertEquals(0, playerJPADAO.findAllEntities().size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		playerJPADAO.persistEntity(JPAPlayerEntity1);
		playerJPADAO.persistEntity(JPAPlayerEntity2);
		assertEquals(2, playerJPADAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failurePlayerJPADAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		playerJPADAO.persistEntity(JPAPlayerEntity1);
		GAEPlayerEntity retrievedEntity = playerJPADAO.findEntityById(JPAPlayerEntity1.getKey());
		assertEquals(JPAPlayerEntity1.getKey(), retrievedEntity.getKey());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		playerJPADAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failurePlayerJPADAO.findEntityById(JPAPlayerEntity1.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		playerJPADAO.persistEntity(JPAPlayerEntity1);
		playerJPADAO.persistEntity(JPAPlayerEntity2);
		assertEquals(2, playerJPADAO.findAllEntities().size());
		playerJPADAO.removeEntity(JPAPlayerEntity1);
		assertEquals(1, playerJPADAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		playerJPADAO.removeEntity(JPAPlayerEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		playerJPADAO.removeEntity(JPAPlayerEntity4);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failurePlayerJPADAO.removeEntity(JPAPlayerEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		playerJPADAO.persistEntity(JPAPlayerEntity1);
		assertNull(JPAPlayerEntity1.getDescription());
		String entityKey = JPAPlayerEntity1.getKey();

		JPAPlayerEntity1.setDescription(DESCRIPTION);
		playerJPADAO.updateEntity(JPAPlayerEntity1);
		assertEquals(entityKey, JPAPlayerEntity1.getKey());

		GAEPlayerEntity retrievedEntity = playerJPADAO.findEntityById(JPAPlayerEntity1.getKey());
		assertEquals(DESCRIPTION, retrievedEntity.getDescription());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		playerJPADAO.persistEntity(JPAPlayerEntity1);
		try {
			playerJPADAO.removeEntity(JPAPlayerEntity1);
		} catch (EntityRemovalException e) {
			fail();
		}
		playerJPADAO.updateEntity(JPAPlayerEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failurePlayerJPADAO.updateEntity(JPAPlayerEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		assertEquals(0, playerJPADAO.findSpecificEntity(JPAPlayerEntity3).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		playerJPADAO.persistEntity(JPAPlayerEntity1);
		playerJPADAO.persistEntity(JPAPlayerEntity2);
		GAEJPAPlayerEntity searchRole = new GAEJPAPlayerEntity();
		searchRole.setKey(PLAYERKEY);
		assertEquals(1, playerJPADAO.findSpecificEntity(searchRole).size());
		searchRole.setKey(PLAYERKEY_2);
		assertEquals(1, playerJPADAO.findSpecificEntity(searchRole).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, playerJPADAO.findSpecificEntity(JPAPlayerEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failurePlayerJPADAO.findSpecificEntity(JPAPlayerEntity1);
	}
}
