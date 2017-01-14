package amtc.gue.ws.test.tournament.persistence.dao;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.tournament.persistence.model.GAEJPAPlayerEntity;

/**
 * Testclass for the Player DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerDAOJPATest extends TournamentServiceJPATest {

	@Override
	public void testDAOSetup() {
		assertNotNull(playerEntityDAO);
		assertNotNull(failurePlayerEntityDAO);
	}

	@Override
	public void testAddEntity() throws EntityPersistenceException {
		assertNull(playerEntity1.getKey());
		assertNull(playerEntity2.getKey());
		playerEntityDAO.persistEntity(playerEntity1);
		playerEntityDAO.persistEntity(playerEntity2);
		assertNotNull(playerEntity1.getKey());
		assertNotNull(playerEntity2.getKey());
	}

	@Override
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		playerEntityDAO.persistEntity(playerEntity1);
		playerEntityDAO.persistEntity(playerEntity1);
	}

	@Override
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failurePlayerEntityDAO.persistEntity(playerEntity1);
	}

	@Override
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		assertEquals(0, playerEntityDAO.findAllEntities().size());
	}

	@Override
	public void testGetEntitiesAfterAddingSimpleEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		playerEntityDAO.persistEntity(playerEntity1);
		playerEntityDAO.persistEntity(playerEntity2);
		assertEquals(2, playerEntityDAO.findAllEntities().size());
	}

	@Override
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failurePlayerEntityDAO.findAllEntities();
	}

	@Override
	public void testGetEntityById() throws EntityRetrievalException,
			EntityPersistenceException {
		playerEntityDAO.persistEntity(playerEntity1);
		GAEJPAPlayerEntity retrievedEntity = playerEntityDAO
				.findEntityById(playerEntity1.getKey());
		assertEquals(playerEntity1.getKey(), retrievedEntity.getKey());
	}

	@Override
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		playerEntityDAO.findEntityById(null);
	}

	@Override
	public void testGetEntityByIdUsingInvalidEM()
			throws EntityPersistenceException, EntityRetrievalException {
		playerEntityDAO.persistEntity(playerEntity1);
		failurePlayerEntityDAO.findEntityById(playerEntity1.getKey());
	}

	@Override
	public void testDeleteEntity() throws EntityRetrievalException,
			EntityRemovalException, EntityPersistenceException {
		playerEntityDAO.persistEntity(playerEntity1);
		playerEntityDAO.persistEntity(playerEntity2);
		assertEquals(2, playerEntityDAO.findAllEntities().size());
		playerEntityDAO.removeEntity(playerEntity1);
		assertEquals(1, playerEntityDAO.findAllEntities().size());
	}

	@Override
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		playerEntityDAO.removeEntity(playerEntity1);
	}

	@Override
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failurePlayerEntityDAO.removeEntity(playerEntity1);
	}

	@Override
	public void testUpdateSimpleEntity() throws EntityRetrievalException,
			EntityPersistenceException {
		playerEntityDAO.persistEntity(playerEntity1);
		assertEquals(playerEntity1.getPlayerName(), PLAYER_NAME_A);
		String entityKey = playerEntity1.getKey();
		playerEntity1.setPlayerName(PLAYER_NAME_B);
		playerEntityDAO.updateEntity(playerEntity1);
		assertEquals(entityKey, playerEntity1.getKey());
		GAEJPAPlayerEntity retrievedUpdatedEntity = playerEntityDAO
				.findEntityById(playerEntity1.getKey());
		assertEquals(PLAYER_NAME_B, retrievedUpdatedEntity.getPlayerName());
	}

	@Override
	public void testUpdateEntityWithoutAdding()
			throws EntityPersistenceException {
		playerEntityDAO.updateEntity(playerEntity1);
	}

	@Override
	public void testUpdateEntityUsingInvalidEM()
			throws EntityPersistenceException {
		failurePlayerEntityDAO.updateEntity(playerEntity1);
	}

	@Override
	public void testFindSpecificEntity() throws EntityRetrievalException,
			EntityPersistenceException {
		playerEntityDAO.persistEntity(playerEntity1);
		assertEquals(1, playerEntityDAO.findSpecificEntity(playerEntity1).size());
	}

	@Override
	public void testFindSpecificEntityBySearchCriteria()
			throws EntityRetrievalException, EntityPersistenceException {
		playerEntityDAO.persistEntity(playerEntity1);
		playerEntityDAO.persistEntity(playerEntity2);

		GAEJPAPlayerEntity searchPlayer = new GAEJPAPlayerEntity();
		searchPlayer.setPlayerName(PLAYER_NAME_A);
		assertEquals(2, playerEntityDAO.findSpecificEntity(searchPlayer).size());
		searchPlayer.setPlayerName(PLAYER_NAME_B);
		assertEquals(0, playerEntityDAO.findSpecificEntity(searchPlayer).size());
	}

	@Override
	public void testFindSpecificEntityBeforeAdding()
			throws EntityRetrievalException {
		assertEquals(0, playerEntityDAO.findSpecificEntity(playerEntity1).size());
	}

	@Override
	public void testFindSpecificEntityUsingInvalidEM()
			throws EntityRetrievalException {
		failurePlayerEntityDAO.findSpecificEntity(playerEntity1);
	}
}
