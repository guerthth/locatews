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
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.dao.user.objectify.UserObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.model.role.objectify.GAEObjectifyRoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.test.base.UserTest;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;

/**
 * Testclass for the User Objectify DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOObjectifyTest extends UserTest implements IBaseDAOTest {

	private static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> failureUserObjectifyDAO;

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		setUpBasicEnvironment();
		setUpDAOMocks();
	}

	@AfterClass
	public static void finalTearDown() {
		EasyMock.verify(failureUserObjectifyDAO);
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
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
	}

	/**
	 * Test adding complex user entity with single role
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testAddUserEntityWithSingleRole() throws EntityPersistenceException, EntityRetrievalException {
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		objectifyUserEntity1.addToRolesAndUsers(objectifyRoleEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		assertEquals(1, roleObjectifyDAO.findAllEntities().size());
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
		assertEquals(1, userObjectifyDAO.findEntityById(objectifyUserEntity1.getKey()).getRoles().size());
		assertEquals(1, roleObjectifyDAO.findEntityById(objectifyRoleEntity1.getKey()).getUsers().size());
	}

	/**
	 * Test adding complex user entity with multiple roles
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testAddUserEntityWithMultipleRoles() throws EntityPersistenceException, EntityRetrievalException {
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity2);
		objectifyUserEntity1.addToRolesAndUsers(objectifyRoleEntity1);
		objectifyUserEntity1.addToRolesAndUsers(objectifyRoleEntity2);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		assertEquals(2, roleObjectifyDAO.findAllEntities().size());
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
		assertEquals(2, userObjectifyDAO.findEntityById(objectifyUserEntity1.getKey()).getRoles().size());
		assertEquals(1, roleObjectifyDAO.findEntityById(objectifyRoleEntity1.getKey()).getUsers().size());
		assertEquals(1, roleObjectifyDAO.findEntityById(objectifyRoleEntity2.getKey()).getUsers().size());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureUserObjectifyDAO.persistEntity(objectifyUserEntity3);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		List<GAEUserEntity> foundUsers = userObjectifyDAO.findAllEntities();
		assertEquals(0, foundUsers.size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		List<GAEUserEntity> foundUsers = userObjectifyDAO.findAllEntities();
		assertEquals(2, foundUsers.size());
	}

	/**
	 * Test retrieving complex entities (with roles)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to retrieving an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testGetUserEntitiesAfterAddingComplexEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		objectifyUserEntity1.addToRolesAndUsers(objectifyRoleEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		List<GAEUserEntity> foundUsers = userObjectifyDAO.findAllEntities();
		assertEquals(1, foundUsers.size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureUserObjectifyDAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		GAEUserEntity userEntity = userObjectifyDAO.findEntityById(objectifyUserEntity1.getKey());
		assertNotNull(userEntity);
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
	public void testGetComplexUserEntityById() throws EntityPersistenceException, EntityRetrievalException {
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		objectifyUserEntity1.addToRolesAndUsers(objectifyRoleEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		GAEUserEntity userEntity = userObjectifyDAO.findEntityById(objectifyUserEntity1.getKey());
		assertNotNull(userEntity);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		userObjectifyDAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failureUserObjectifyDAO.findEntityById(objectifyUserEntity1.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		assertEquals(2, userObjectifyDAO.findAllEntities().size());
		userObjectifyDAO.removeEntity(objectifyUserEntity1);
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
	}

	/**
	 * Test deleting complex entity (with roles)
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
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		objectifyUserEntity1.addToRolesAndUsers(objectifyRoleEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		assertEquals(1, userObjectifyDAO.findAllEntities().size());
		assertEquals(1, roleObjectifyDAO.findAllEntities().size());
		userObjectifyDAO.removeEntity(objectifyUserEntity1);
		assertEquals(0, userObjectifyDAO.findAllEntities().size());
		assertEquals(1, roleObjectifyDAO.findAllEntities().size());
	}

	@Override
	@Test
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		try {
			assertEquals(0, userObjectifyDAO.findAllEntities().size());
			userObjectifyDAO.removeEntity(objectifyUserEntity1);
			assertEquals(0, userObjectifyDAO.findAllEntities().size());
		} catch (EntityRetrievalException e) {
			fail();
		}
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		userObjectifyDAO.removeEntity(objectifyUserEntity3);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureUserObjectifyDAO.removeEntity(objectifyUserEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		assertNull(userObjectifyDAO.findEntityById(objectifyUserEntity1.getKey()).getEmail());
		objectifyUserEntity1.setEmail(EMAIL);
		userObjectifyDAO.updateEntity(objectifyUserEntity1);
		assertEquals(EMAIL, userObjectifyDAO.findEntityById(objectifyUserEntity1.getKey()).getEmail());
	}

	/**
	 * Test updating complex entity (with roles)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testUpdateComplexEntity() throws EntityPersistenceException, EntityRetrievalException {
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		objectifyUserEntity1.addToRolesAndUsers(objectifyRoleEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		assertEquals(1, userObjectifyDAO.findEntityById(objectifyUserEntity1.getKey()).getRoles().size());
		objectifyUserEntity1.setRoles(null, false);
		userObjectifyDAO.updateEntity(objectifyUserEntity1);
		assertEquals(0, userObjectifyDAO.findEntityById(objectifyUserEntity1.getKey()).getRoles().size());
	}

	@Override
	@Test
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		// objectify update without adding equals persisting
		userObjectifyDAO.updateEntity(objectifyUserEntity1);
		assertNotNull(objectifyUserEntity1.getKey());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failureUserObjectifyDAO.updateEntity(objectifyUserEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		GAEObjectifyUserEntity searchUserEntity = new GAEObjectifyUserEntity();
		searchUserEntity.setEmail(EMAIL);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		assertEquals(2, userObjectifyDAO.findAllEntities().size());
		assertEquals(1, userObjectifyDAO.findSpecificEntity(objectifyUserEntity1).size());
		assertEquals(1, userObjectifyDAO.findSpecificEntity(searchUserEntity).size());
	}

	/**
	 * Test retrieving specific complex entity (with roles)
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testFindSpecificComplexUserEntity() throws EntityPersistenceException, EntityRetrievalException {
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		objectifyUserEntity1.addToRolesAndUsers(objectifyRoleEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		assertEquals(1, userObjectifyDAO.findSpecificEntity(objectifyUserEntity1).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		objectifyUserEntity1.addToRolesAndUsers(objectifyRoleEntity1);
		objectifyUserEntity2.addToRolesAndUsers(objectifyRoleEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		assertEquals(2, userObjectifyDAO.findAllEntities().size());
		assertEquals(1, roleObjectifyDAO.findAllEntities().size());
		GAEObjectifyUserEntity searchUser = new GAEObjectifyUserEntity();
		searchUser.addToRolesOnly(objectifyRoleEntity1);
		GAEObjectifyRoleEntity searchRole = new GAEObjectifyRoleEntity();
		searchRole.addToUsersOnly(objectifyUserEntity1);
		assertEquals(2, userObjectifyDAO.findSpecificEntity(searchUser).size());
		assertEquals(1, roleObjectifyDAO.findSpecificEntity(searchRole).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, userObjectifyDAO.findSpecificEntity(objectifyUserEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failureUserObjectifyDAO.findSpecificEntity(objectifyUserEntity1);
	}

	/**
	 * Method retrieving users by roles with multiple results
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testGetEntityByRoleMultipleResults()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		objectifyUserEntity1.addToRolesAndUsers(objectifyRoleEntity1);
		objectifyUserEntity2.addToRolesAndUsers(objectifyRoleEntity1);
		objectifyUserEntity1.addToRolesAndUsers(objectifyRoleEntity2);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity2);
		assertEquals(2, userObjectifyDAO.findAllEntities().size());
		assertEquals(2, roleObjectifyDAO.findAllEntities().size());
		assertEquals(2, userObjectifyDAO.getUserEntitiesByRoles(roles).size());
		assertEquals(1, userObjectifyDAO.getUserEntitiesByRoles(rolesAB).size());
	}

	/**
	 * Method retrieving users by roles with single result
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persisting an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test
	public void testGetEntityByRoleSingleResult() throws EntityPersistenceException, EntityRetrievalException {
		objectifyUserEntity1.addToRolesAndUsers(objectifyRoleEntity1);
		objectifyUserEntity2.addToRolesAndUsers(objectifyRoleEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity1);
		userObjectifyDAO.persistEntity(objectifyUserEntity2);
		roleObjectifyDAO.persistEntity(objectifyRoleEntity1);
		assertEquals(1, roleObjectifyDAO.findAllEntities().size());
		assertEquals(2, userObjectifyDAO.findAllEntities().size());
		assertEquals(2, userObjectifyDAO.getUserEntitiesByRoles(roles).size());
	}

	/**
	 * Method retrieving users by role causing an EntityRetrievalException
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByRoleUsingInvalidEM() throws EntityRetrievalException {
		failureUserObjectifyDAO.getUserEntitiesByRoles(roles);
	}

	/**
	 * Method setting up DAO Mocks for testing purpose
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to remove an entity
	 */
	private static void setUpDAOMocks()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		failureUserObjectifyDAO = EasyMock.createNiceMock(UserObjectifyDAOImpl.class);
		EasyMock.expect(failureUserObjectifyDAO.findAllEntities()).andThrow(new EntityRetrievalException());
		EasyMock.expect(failureUserObjectifyDAO.findEntityById(EasyMock.isA(String.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failureUserObjectifyDAO.findSpecificEntity(EasyMock.isA(GAEObjectifyUserEntity.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failureUserObjectifyDAO.getUserEntitiesByRoles(EasyMock.isA(Roles.class)))
				.andThrow(new EntityRetrievalException());
		EasyMock.expect(failureUserObjectifyDAO.persistEntity(EasyMock.isA(GAEObjectifyUserEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.expect(failureUserObjectifyDAO.removeEntity(EasyMock.isA(GAEObjectifyUserEntity.class)))
				.andThrow(new EntityRemovalException());
		EasyMock.expect(failureUserObjectifyDAO.updateEntity(EasyMock.isA(GAEObjectifyUserEntity.class)))
				.andThrow(new EntityPersistenceException());
		EasyMock.replay(failureUserObjectifyDAO);
	}
}
