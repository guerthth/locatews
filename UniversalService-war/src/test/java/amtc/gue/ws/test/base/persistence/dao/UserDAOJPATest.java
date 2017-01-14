package amtc.gue.ws.test.base.persistence.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.model.GAEJPARoleEntity;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;

/**
 * Testclass for the User DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOJPATest extends UserServiceJPATest {

	@Override
	public void testDAOSetup() {
		assertNotNull(userEntityDAO);
		assertNotNull(failureUserEntityDAO);
		assertNotNull(roleEntityDAO);
		assertNotNull(failureRoleEntityDAO);
	}

	@Override
	public void testAddEntity() throws EntityPersistenceException {
		assertNotNull(userEntity1.getKey());
		userEntityDAO.persistEntity(userEntity1);
		assertNotNull(userEntity1.getKey());
	}

	/**
	 * Test adding complex entity with single role
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public void testAddUserEntityWithSingleRole()
			throws EntityPersistenceException {
		roleEntityDAO.persistEntity(roleEntity1);
		userEntity1.addToRolesAndUsers(roleEntity1);
		userEntityDAO.persistEntity(userEntity1);
		assertEquals(1, userEntity1.getRoles().size());
	}

	/**
	 * Test adding complex entity with multiple roles
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieving an entity
	 */
	@Test
	public void testAddUserEntityWithMultipleRoles()
			throws EntityPersistenceException, EntityRetrievalException {
		roleEntityDAO.persistEntity(roleEntity1);
		roleEntityDAO.persistEntity(roleEntity2);
		userEntity1.addToRolesAndUsers(roleEntity1);
		userEntity1.addToRolesAndUsers(roleEntity2);
		userEntityDAO.persistEntity(userEntity1);
		assertEquals(2, userEntity1.getRoles().size());
		assertEquals(2, roleEntityDAO.findAllEntities().size());
		assertEquals(1, userEntityDAO.findAllEntities().size());
	}

	@Override
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		userEntityDAO.persistEntity(userEntity1);
		userEntityDAO.persistEntity(userEntity1);
	}

	@Override
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureUserEntityDAO.persistEntity(userEntity1);
	}

	@Override
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		assertEquals(0, userEntityDAO.findAllEntities().size());
	}

	@Override
	public void testGetEntitiesAfterAddingSimpleEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		userEntityDAO.persistEntity(userEntity1);
		userEntityDAO.persistEntity(userEntity2);
		assertEquals(2, userEntityDAO.findAllEntities().size());
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
	public void testGetAllUserEntitiesAfterAddingComplexRoleEntities()
			throws EntityPersistenceException, EntityRetrievalException {
		roleEntityDAO.persistEntity(roleEntity1);
		roleEntityDAO.persistEntity(roleEntity2);
		userEntity1.addToRolesAndUsers(roleEntity1);
		userEntity1.addToRolesAndUsers(roleEntity2);
		userEntityDAO.persistEntity(userEntity1);
		List<GAEJPAUserEntity> foundUsers = userEntityDAO.findAllEntities();
		List<GAEJPARoleEntity> foundRoles = roleEntityDAO.findAllEntities();
		assertEquals(1, foundUsers.size());
		assertEquals(2, foundRoles.size());
		List<GAEJPARoleEntity> userRoles = new ArrayList<>(foundUsers.get(0)
				.getRoles());
		assertEquals(2, userRoles.size());
	}

	@Override
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureUserEntityDAO.findAllEntities();
	}

	@Override
	public void testGetEntityById() throws EntityRetrievalException,
			EntityPersistenceException {
		userEntityDAO.persistEntity(userEntity1);
		GAEJPAUserEntity retrievedEntity = userEntityDAO
				.findEntityById(userEntity1.getKey());
		assertEquals(userEntity1.getKey(), retrievedEntity.getKey());
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
	public void testGetComplexUserEntityById()
			throws EntityPersistenceException, EntityRetrievalException {
		roleEntityDAO.persistEntity(roleEntity1);
		roleEntityDAO.persistEntity(roleEntity2);
		userEntity1.addToRolesAndUsers(roleEntity1);
		userEntity1.addToRolesAndUsers(roleEntity2);
		userEntityDAO.persistEntity(userEntity1);

		GAEJPAUserEntity retrievedEntity = userEntityDAO
				.findEntityById(userEntity1.getKey());
		assertNotNull(retrievedEntity);
		assertEquals(userEntity1.getKey(), retrievedEntity.getKey());
		assertNotNull(retrievedEntity.getRoles());
	}

	@Override
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		userEntityDAO.findEntityById(null);
	}

	@Override
	public void testGetEntityByIdUsingInvalidEM()
			throws EntityPersistenceException, EntityRetrievalException {
		userEntityDAO.persistEntity(userEntity1);
		failureUserEntityDAO.findEntityById(userEntity1.getKey());
	}

	// Resume with deletion test of compelx entity

	@Override
	public void testDeleteEntity() throws EntityRetrievalException,
			EntityRemovalException, EntityPersistenceException {
		userEntityDAO.persistEntity(userEntity1);
		userEntityDAO.persistEntity(userEntity2);
		assertEquals(2, userEntityDAO.findAllEntities().size());
		userEntityDAO.removeEntity(userEntity1);
		assertEquals(1, userEntityDAO.findAllEntities().size());
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
	public void testDeleteComplexEntity() throws EntityPersistenceException,
			EntityRetrievalException, EntityRemovalException {
		roleEntityDAO.persistEntity(roleEntity1);
		userEntity1.addToRolesAndUsers(roleEntity1);
		userEntityDAO.persistEntity(userEntity1);
		assertEquals(1, roleEntityDAO.findAllEntities().size());
		assertEquals(1, userEntityDAO.findAllEntities().size());

		userEntityDAO.removeEntity(userEntity1);
		assertEquals(1, roleEntityDAO.findAllEntities().size());
		assertEquals(0, userEntityDAO.findAllEntities().size());
	}

	@Override
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		userEntityDAO.removeEntity(userEntity1);
	}

	/**
	 * Test removing entity without id
	 * 
	 * @throws EntityRemovalException
	 *             when issue occurs while trying to removing an entity
	 */
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		userEntityDAO.removeEntity(userEntity3);
	}

	@Override
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureUserEntityDAO.removeEntity(userEntity1);
	}

	@Override
	public void testUpdateSimpleEntity() throws EntityRetrievalException,
			EntityPersistenceException {
		userEntityDAO.persistEntity(userEntity1);
		assertEquals(userEntity1.getPassword(), USER_PASSWORD_A);
		String entityKey = userEntity1.getKey();
		userEntity1.setPassword(USER_PASSWORD_B);
		userEntityDAO.updateEntity(userEntity1);
		assertEquals(entityKey, userEntity1.getKey());
		GAEJPAUserEntity retrievedUdatedEntity = userEntityDAO
				.findEntityById(userEntity1.getKey());
		assertEquals(USER_PASSWORD_B, retrievedUdatedEntity.getPassword());
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
	public void testUpdateComplexUserEntity()
			throws EntityPersistenceException, EntityRetrievalException {
		roleEntityDAO.persistEntity(roleEntity4);
		userEntity1.addToRolesAndUsers(roleEntity4);
		userEntityDAO.persistEntity(userEntity1);
		assertEquals(1, userEntityDAO.findAllEntities().size());
		assertEquals(1, roleEntityDAO.findAllEntities().size());

		GAEJPAUserEntity foundUserEntity = userEntityDAO
				.findEntityById(userEntity1.getKey());
		ArrayList<GAEJPARoleEntity> roles = new ArrayList<>(
				foundUserEntity.getRoles());
		assertNotNull(roles);
		GAEJPARoleEntity firstRole = roles.get(0);
		assertEquals(DESCRIPTION, firstRole.getDescription());

		// update
		roleEntity4.setDescription(ROLE_B);
		roleEntityDAO.updateEntity(roleEntity4);
		userEntityDAO.updateEntity(userEntity1);

		// check updated entity
		GAEJPAUserEntity retrievedEntity = userEntityDAO
				.findEntityById(userEntity1.getKey());
		roles = new ArrayList<>(retrievedEntity.getRoles());
		firstRole = roles.get(0);
		assertEquals(ROLE_B, firstRole.getDescription());
	}

	@Override
	public void testUpdateEntityWithoutAdding()
			throws EntityPersistenceException {
		GAEJPAUserEntity updatedEntity = new GAEJPAUserEntity();
		userEntityDAO.updateEntity(updatedEntity);
	}

	@Override
	public void testUpdateEntityUsingInvalidEM()
			throws EntityPersistenceException {
		failureUserEntityDAO.updateEntity(userEntity1);
	}

	@Override
	public void testFindSpecificEntity() throws EntityRetrievalException,
			EntityPersistenceException {
		userEntityDAO.persistEntity(userEntity1);
		assertEquals(1, userEntityDAO.findSpecificEntity(userEntity1).size());
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
	public void testFindSpecificComplexUserEntity()
			throws EntityPersistenceException, EntityRetrievalException {
		roleEntityDAO.persistEntity(roleEntity1);
		userEntity1.addToRolesAndUsers(roleEntity1);
		userEntityDAO.persistEntity(userEntity1);
		userEntityDAO.persistEntity(userEntity2);
		List<GAEJPAUserEntity> foundUsers = userEntityDAO
				.findSpecificEntity(userEntity1);
		assertEquals(1, foundUsers.size());
	}

	@Override
	public void testFindSpecificEntityBySearchCriteria()
			throws EntityRetrievalException, EntityPersistenceException {
		userEntityDAO.persistEntity(userEntity1);
		userEntityDAO.persistEntity(userEntity2);
		GAEJPAUserEntity searchUser = new GAEJPAUserEntity();
		searchUser.setKey(USER_NAME_A);
		assertEquals(1, userEntityDAO.findSpecificEntity(searchUser).size());
		searchUser.setKey(USER_NAME_B);
		assertEquals(1, userEntityDAO.findSpecificEntity(searchUser).size());
		searchUser.setKey(null);
		searchUser.setPassword(USER_PASSWORD_A);
		assertEquals(1, userEntityDAO.findSpecificEntity(searchUser).size());
	}

	@Override
	public void testFindSpecificEntityBeforeAdding()
			throws EntityRetrievalException {
		assertEquals(0, userEntityDAO.findSpecificEntity(userEntity1).size());
	}

	@Override
	public void testFindSpecificEntityUsingInvalidEM()
			throws EntityRetrievalException {
		failureUserEntityDAO.findSpecificEntity(userEntity1);
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
			throws EntityPersistenceException, EntityRetrievalException {
		roleEntityDAO.persistEntity(roleEntity1);
		roleEntityDAO.persistEntity(roleEntity2);
		userEntity1.addToRolesAndUsers(roleEntity1);
		userEntity1.addToRolesAndUsers(roleEntity2);
		userEntityDAO.persistEntity(userEntity1);
		userEntity2.addToRolesAndUsers(roleEntity2);
		userEntityDAO.persistEntity(userEntity2);
		List<GAEJPAUserEntity> foundUsersHavingRoleAorB = userEntityDAO
				.getUserEntitiesByRoles(rolesAB);
		assertEquals(2, foundUsersHavingRoleAorB.size());
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
	public void testGetEntityByRoleSingleResult()
			throws EntityPersistenceException, EntityRetrievalException {
		roleEntityDAO.persistEntity(roleEntity1);
		roleEntityDAO.persistEntity(roleEntity2);
		userEntity1.addToRolesAndUsers(roleEntity1);
		userEntity1.addToRolesAndUsers(roleEntity2);
		userEntityDAO.persistEntity(userEntity1);
		userEntity2.addToRolesAndUsers(roleEntity2);
		userEntityDAO.persistEntity(userEntity2);
		List<GAEJPAUserEntity> foundUsersHavingRoleA = userEntityDAO
				.getUserEntitiesByRoles(rolesA);
		assertEquals(1, foundUsersHavingRoleA.size());
	}
}
