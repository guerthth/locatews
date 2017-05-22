package amtc.gue.ws.test.base.persistence.dao.objectify;

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
import amtc.gue.ws.base.persistence.dao.role.RoleDAO;
import amtc.gue.ws.base.persistence.dao.role.objectify.RoleObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.role.objectify.GAEObjectifyRoleEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.test.base.UserTest;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;

/**
 * Testclass for the Role Objectify DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RoleDAOObjectifyTest extends UserTest implements IBaseDAOTest {
	private static RoleDAO<GAERoleEntity, GAEObjectifyRoleEntity, String> failureRoleObjectifyDAO;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		setUpBasicEnvironment();
		setUpDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(failureRoleObjectifyDAO);
	}

	@Override
	@Test
	public void testDAOSetup() {
		assertNotNull(userObjectifyDAO);
		assertNotNull(roleObjectifyDAO);
	}

	@Override
	@Test
	public void testAddEntity() throws EntityPersistenceException {
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
	}

	/**
	 * Test adding complex role entity with single user
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testAddRoleEntityWithSingleUser() throws EntityPersistenceException, EntityRetrievalException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		objectifyRoleEntity1.addToUsersAndRoles(objectifyUserEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
		assertEquals(1, roleObjectifyDAO.findAllEntities().size());
		assertEquals(1, roleObjectifyDAO.findEntityById(objectifyRoleEntity1.getKey()).getUsers().size());
		assertEquals(1, userObjectifyDAO.findEntityById(objectifyUserEntity1.getKey()).getRoles().size());
	}

	/**
	 * Test adding complex role entity with multiple users
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testAddRoleEntityWithMultipleUsers() throws EntityPersistenceException, EntityRetrievalException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		objectifyRoleEntity1.addToUsersAndRoles(objectifyUserEntity1);
		objectifyRoleEntity1.addToUsersAndRoles(objectifyUserEntity2);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		assertEquals(2, userObjectifyDAO.findAllEntities().size());
		assertEquals(1, roleObjectifyDAO.findAllEntities().size());
		assertEquals(2, roleObjectifyDAO.findEntityById(objectifyRoleEntity1.getKey()).getUsers().size());
		assertEquals(1, userObjectifyDAO.findEntityById(objectifyUserEntity1.getKey()).getRoles().size());
		assertEquals(1, userObjectifyDAO.findEntityById(objectifyUserEntity2.getKey()).getRoles().size());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureRoleObjectifyDAO.persistEntity(objectifyRoleEntity1);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		List<GAERoleEntity> foundRoles = roleObjectifyDAO.findAllEntities();
		assertEquals(0, foundRoles.size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity2);
		List<GAERoleEntity> foundRoles = roleObjectifyDAO.findAllEntities();
		assertEquals(2, foundRoles.size());
	}

	/**
	 * Test retrieving complex entities (with users)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testGetRoleEntitiesAfterAddingComplexEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		objectifyRoleEntity1.addToUsersAndRoles(objectifyUserEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		List<GAERoleEntity> foundRoles = roleObjectifyDAO.findAllEntities();
		assertEquals(1, foundRoles.size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureRoleObjectifyDAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		GAERoleEntity roleEntity = roleObjectifyDAO.findEntityById(objectifyRoleEntity1.getKey());
		assertNotNull(roleEntity);
	}

	/**
	 * Test retrieving specific complex entity by id
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testGetComplexRoleEntityById() throws EntityPersistenceException, EntityRetrievalException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		objectifyRoleEntity1.addToUsersAndRoles(objectifyUserEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		GAERoleEntity roleEntity = roleObjectifyDAO.findEntityById(objectifyRoleEntity1.getKey());
		assertNotNull(roleEntity);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		roleObjectifyDAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failureRoleObjectifyDAO.findEntityById(objectifyRoleEntity1.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity2);
		assertEquals(2, roleObjectifyDAO.findAllEntities().size());
		roleObjectifyDAO.removeEntity(objectifyRoleEntity2);
		assertEquals(1, roleObjectifyDAO.findAllEntities().size());
	}

	/**
	 * Test deleting complex entity (with users)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to removing an entity
	 */
	@Test
	public void testDeleteComplexEntity()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		objectifyRoleEntity1.addToUsersAndRoles(objectifyUserEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		assertEquals(1, roleObjectifyDAO.findAllEntities().size());
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
		roleObjectifyDAO.removeEntity(objectifyRoleEntity1);
		assertEquals(0, roleObjectifyDAO.findAllEntities().size());
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		try {
			assertEquals(0, roleObjectifyDAO.findAllEntities().size());
			roleObjectifyDAO.removeEntity(objectifyRoleEntity1);
			assertEquals(0, roleObjectifyDAO.findAllEntities().size());
		} catch (EntityRetrievalException e) {
			fail();
		}
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		roleObjectifyDAO.removeEntity(objectifyRoleEntity3);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureRoleObjectifyDAO.removeEntity(objectifyRoleEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		assertNull(roleObjectifyDAO.findEntityById(objectifyRoleEntity1.getKey()).getDescription());
		objectifyRoleEntity1.setDescription(DESCRIPTION);
		roleObjectifyDAO.updateEntity(objectifyRoleEntity1);
		assertEquals(DESCRIPTION, roleObjectifyDAO.findEntityById(objectifyRoleEntity1.getKey()).getDescription());
	}

	/**
	 * Test updating complex entity (with users)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testUpdatecomplexEntity() throws EntityPersistenceException, EntityRetrievalException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		objectifyRoleEntity1.addToUsersAndRoles(objectifyUserEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		assertEquals(1, roleObjectifyDAO.findEntityById(objectifyRoleEntity1.getKey()).getUsers().size());
		objectifyRoleEntity1.setUsers(null, false);
		roleObjectifyDAO.updateEntity(objectifyRoleEntity1);
		assertEquals(0, roleObjectifyDAO.findEntityById(objectifyRoleEntity1.getKey()).getUsers().size());
	}

	@Override
	@Test
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		// objectify update without adding equals persisting
		roleObjectifyDAO.updateEntity(objectifyRoleEntity1);
		assertNotNull(objectifyRoleEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failureRoleObjectifyDAO.updateEntity(objectifyRoleEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		GAEObjectifyRoleEntity searchRoleEntity = new GAEObjectifyRoleEntity();
		searchRoleEntity.setDescription(DESCRIPTION);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity2);
		assertEquals(2, roleObjectifyDAO.findAllEntities().size());
		assertEquals(1, roleObjectifyDAO.findSpecificEntity(objectifyRoleEntity1).size());
		assertEquals(0, roleObjectifyDAO.findSpecificEntity(searchRoleEntity).size());
	}

	/**
	 * Test retrieving complex entity (with users)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testFindSpecificComplexRoleEntity() throws EntityPersistenceException, EntityRetrievalException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		objectifyRoleEntity1.addToUsersAndRoles(objectifyUserEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity2);
		assertEquals(2, roleObjectifyDAO.findAllEntities().size());
		assertEquals(1, roleObjectifyDAO.findSpecificEntity(objectifyRoleEntity1).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		objectifyRoleEntity1.addToUsersAndRoles(objectifyUserEntity1);
		objectifyRoleEntity2.addToUsersAndRoles(objectifyUserEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity2);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		assertEquals(2, roleObjectifyDAO.findAllEntities().size());
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
		GAEObjectifyRoleEntity searchRole = new GAEObjectifyRoleEntity();
		searchRole.addToUsersOnly(objectifyUserEntity1);
		GAEObjectifyUserEntity searchUser = new GAEObjectifyUserEntity();
		searchUser.addToRolesOnly(objectifyRoleEntity1);
		assertEquals(2, roleObjectifyDAO.findSpecificEntity(searchRole).size());
		assertEquals(1, userObjectifyDAO.findSpecificEntity(searchUser).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, roleObjectifyDAO.findSpecificEntity(objectifyRoleEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failureRoleObjectifyDAO.findSpecificEntity(objectifyRoleEntity1);
	}

	/**
	 * Method setting up DAO Mocks for testing purpose
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRemovalException
	 *              when issue occurs while trying to removal an entity
	 */
	private static void setUpDAOMocks()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		failureRoleObjectifyDAO = EasyMock.createNiceMock(RoleObjectifyDAOImpl.class);
		EasyMock.expect(failureRoleObjectifyDAO.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.expect(failureRoleObjectifyDAO.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failureRoleObjectifyDAO.findSpecificEntity(EasyMock.isA(GAEObjectifyRoleEntity.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failureRoleObjectifyDAO.persistEntity(EasyMock.isA(GAEObjectifyRoleEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(failureRoleObjectifyDAO.removeEntity(EasyMock.isA(GAEObjectifyRoleEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.expect(failureRoleObjectifyDAO.updateEntity(EasyMock.isA(GAEObjectifyRoleEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.replay(failureRoleObjectifyDAO);
	}
}
