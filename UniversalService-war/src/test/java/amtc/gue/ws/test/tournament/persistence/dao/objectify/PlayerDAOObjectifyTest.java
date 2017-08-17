package amtc.gue.ws.test.tournament.persistence.dao.objectify;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;
import amtc.gue.ws.test.tournament.TournamentTest;
import amtc.gue.ws.tournament.persistence.dao.player.PlayerDAO;
import amtc.gue.ws.tournament.persistence.dao.player.objectify.PlayerObjectifyDAOImpl;
import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;
import amtc.gue.ws.tournament.persistence.model.player.objectify.GAEObjectifyPlayerEntity;

/**
 * Testclass for the Player Objectify DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlayerDAOObjectifyTest extends TournamentTest implements IBaseDAOTest {
	private static PlayerDAO<GAEPlayerEntity, GAEObjectifyPlayerEntity, String> failurePlayerObjectifyDAO;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		setUpBasicTournamentEnvironment();
		setUpDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(failurePlayerObjectifyDAO);
	}

	@Override
	@Test
	public void testDAOSetup() {
		assertNotNull(playerObjectifyDAO);
	}

	@Override
	@Test
	public void testAddEntity() throws EntityPersistenceException {
		playerObjectifyDAO.persistEntity(objectifyPlayerEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		playerObjectifyDAO.persistEntity(objectifyPlayerEntity1);
		playerObjectifyDAO.persistEntity(objectifyPlayerEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failurePlayerObjectifyDAO.persistEntity(objectifyPlayerEntity1);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		List<GAEPlayerEntity> foundPlayers = playerObjectifyDAO.findAllEntities();
		assertEquals(0, foundPlayers.size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		playerObjectifyDAO.persistEntity(objectifyPlayerEntity1);
		playerObjectifyDAO.persistEntity(objectifyPlayerEntity2);
		List<GAEPlayerEntity> foundPlayers = playerObjectifyDAO.findAllEntities();
		assertEquals(2, foundPlayers.size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failurePlayerObjectifyDAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		playerObjectifyDAO.persistEntity(objectifyPlayerEntity1);
		GAEPlayerEntity playerEntity = playerObjectifyDAO.findEntityById(objectifyPlayerEntity1.getWebsafeKey());
		assertNotNull(playerEntity);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		playerObjectifyDAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failurePlayerObjectifyDAO.findEntityById(objectifyPlayerEntity1.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		playerObjectifyDAO.persistEntity(objectifyPlayerEntity1);
		playerObjectifyDAO.persistEntity(objectifyPlayerEntity2);
		assertEquals(2, playerObjectifyDAO.findAllEntities().size());
		playerObjectifyDAO.removeEntity(objectifyPlayerEntity2);
		assertEquals(1, playerObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		try {
			assertEquals(0, playerObjectifyDAO.findAllEntities().size());
			playerObjectifyDAO.removeEntity(objectifyPlayerEntity1);
			assertEquals(0, playerObjectifyDAO.findAllEntities().size());
		} catch (EntityRetrievalException e) {
			fail();
		}
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		playerObjectifyDAO.removeEntity(objectifyPlayerEntity3);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failurePlayerObjectifyDAO.removeEntity(objectifyPlayerEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		playerObjectifyDAO.persistEntity(objectifyPlayerEntity1);
		assertNull(playerObjectifyDAO.findEntityById(objectifyPlayerEntity1.getWebsafeKey()).getDescription());
		objectifyPlayerEntity1.setDescription(DESCRIPTION);
		playerObjectifyDAO.updateEntity(objectifyPlayerEntity1);
		assertEquals(DESCRIPTION,
				playerObjectifyDAO.findEntityById(objectifyPlayerEntity1.getWebsafeKey()).getDescription());
	}

	@Override
	@Test
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		// objectify update without adding equals persisting
		playerObjectifyDAO.updateEntity(objectifyPlayerEntity1);
		assertNotNull(objectifyPlayerEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failurePlayerObjectifyDAO.updateEntity(objectifyPlayerEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		GAEObjectifyPlayerEntity searchPlayerEntity = new GAEObjectifyPlayerEntity();
		searchPlayerEntity.setDescription(DESCRIPTION);
		playerObjectifyDAO.persistEntity(objectifyPlayerEntity1);
		playerObjectifyDAO.persistEntity(objectifyPlayerEntity2);
		assertEquals(2, playerObjectifyDAO.findAllEntities().size());
		assertEquals(1, playerObjectifyDAO.findSpecificEntity(objectifyPlayerEntity1).size());
		assertEquals(0, playerObjectifyDAO.findSpecificEntity(searchPlayerEntity).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		playerObjectifyDAO.persistEntity(objectifyPlayerEntity1);
		assertEquals(1, playerObjectifyDAO.findAllEntities().size());
		GAEObjectifyPlayerEntity searchPlayer = new GAEObjectifyPlayerEntity();
		searchPlayer.setKey(objectifyPlayerEntity1.getKey());
		assertEquals(1, playerObjectifyDAO.findSpecificEntity(searchPlayer).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, playerObjectifyDAO.findSpecificEntity(objectifyPlayerEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failurePlayerObjectifyDAO.findSpecificEntity(objectifyPlayerEntity1);
	}

	/**
	 * Method setting up DAO Mocks for testing purpose
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to removal an entity
	 */
	private static void setUpDAOMocks()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		failurePlayerObjectifyDAO = EasyMock.createNiceMock(PlayerObjectifyDAOImpl.class);
		EasyMock.expect(failurePlayerObjectifyDAO.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.expect(failurePlayerObjectifyDAO.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failurePlayerObjectifyDAO.findSpecificEntity(EasyMock.isA(GAEObjectifyPlayerEntity.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failurePlayerObjectifyDAO.persistEntity(EasyMock.isA(GAEObjectifyPlayerEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(failurePlayerObjectifyDAO.removeEntity(EasyMock.isA(GAEObjectifyPlayerEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.expect(failurePlayerObjectifyDAO.updateEntity(EasyMock.isA(GAEObjectifyPlayerEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.replay(failurePlayerObjectifyDAO);
	}
}
