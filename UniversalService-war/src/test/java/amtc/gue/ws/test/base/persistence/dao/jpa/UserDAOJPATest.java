package amtc.gue.ws.test.base.persistence.dao.jpa;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.jpa.GAEJPAUserEntity;
import amtc.gue.ws.test.base.UserTest;
import amtc.gue.ws.test.base.persistence.dao.IBaseDAOTest;

/**
 * Testclass for the User JPA DAO
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserDAOJPATest extends UserTest implements IBaseDAOTest {
	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityRetrievalException, EntityPersistenceException, EntityRemovalException {
		setUpBasicEnvironment();
	}

	@Override
	@Test
	public void testDAOSetup() {
		assertNotNull(userJPADAO);
		assertNotNull(failureUserJPADAO);
		assertNotNull(roleJPADAO);
	}

	@Override
	@Test
	public void testAddEntity() throws EntityPersistenceException {
		assertNotNull(JPAUserEntity1.getKey());
		userJPADAO.persistEntity(JPAUserEntity1);
		assertNotNull(JPAUserEntity1.getKey());
	}

	/**
	 * Test adding complex entity with single role
	 * 
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist an entity
	 */
	@Test
	public void testAddUserEntityWithSingleRole() throws EntityPersistenceException {
		GAERoleEntity addedRoleEntity = roleJPADAO.persistEntity(JPARoleEntity1);
		JPAUserEntity1.addToRolesAndUsers(addedRoleEntity);
		userJPADAO.persistEntity(JPAUserEntity1);
		assertEquals(1, JPAUserEntity1.getRoles().size());
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
	public void testAddUserEntityWithMultipleRoles() throws EntityPersistenceException, EntityRetrievalException {
		roleJPADAO.persistEntity(JPARoleEntity1);
		roleJPADAO.persistEntity(JPARoleEntity2);
		JPAUserEntity1.addToRolesAndUsers(JPARoleEntity1);
		JPAUserEntity1.addToRolesAndUsers(JPARoleEntity2);
		userJPADAO.persistEntity(JPAUserEntity1);
		assertEquals(2, JPAUserEntity1.getRoles().size());
		assertEquals(2, roleJPADAO.findAllEntities().size());
		assertEquals(1, userJPADAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddSameEntityTwice() throws EntityPersistenceException {
		userJPADAO.persistEntity(JPAUserEntity1);
		userJPADAO.persistEntity(JPAUserEntity1);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testAddEntityUsingInvalidEM() throws EntityPersistenceException {
		failureUserJPADAO.persistEntity(JPAUserEntity1);
	}

	@Override
	@Test
	public void testGetEntitiesWithoutAdding() throws EntityRetrievalException {
		assertEquals(0, userJPADAO.findAllEntities().size());
	}

	@Override
	@Test
	public void testGetEntitiesAfterAddingSimpleEntities() throws EntityPersistenceException, EntityRetrievalException {
		userJPADAO.persistEntity(JPAUserEntity1);
		userJPADAO.persistEntity(JPAUserEntity2);
		assertEquals(2, userJPADAO.findAllEntities().size());
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
		roleJPADAO.persistEntity(JPARoleEntity1);
		roleJPADAO.persistEntity(JPARoleEntity2);
		JPAUserEntity1.addToRolesAndUsers(JPARoleEntity1);
		JPAUserEntity1.addToRolesAndUsers(JPARoleEntity2);
		userJPADAO.persistEntity(JPAUserEntity1);
		List<GAEUserEntity> foundUsers = userJPADAO.findAllEntities();
		List<GAERoleEntity> foundRoles = roleJPADAO.findAllEntities();
		assertEquals(1, foundUsers.size());
		assertEquals(2, foundRoles.size());
		List<GAERoleEntity> userRoles = new ArrayList<>(foundUsers.get(0).getRoles());
		assertEquals(2, userRoles.size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntitiesUsingInvalidEM() throws EntityRetrievalException {
		failureUserJPADAO.findAllEntities();
	}

	@Override
	@Test
	public void testGetEntityById() throws EntityRetrievalException, EntityPersistenceException {
		userJPADAO.persistEntity(JPAUserEntity1);
		GAEUserEntity retrievedEntity = userJPADAO.findEntityById(JPAUserEntity1.getKey());
		assertEquals(JPAUserEntity1.getKey(), retrievedEntity.getKey());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingNull() throws EntityRetrievalException {
		userJPADAO.findEntityById(null);
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByIdUsingInvalidEM() throws EntityRetrievalException {
		failureUserJPADAO.findEntityById(JPAUserEntity1.getKey());
	}

	@Override
	@Test
	public void testDeleteEntity() throws EntityRetrievalException, EntityRemovalException, EntityPersistenceException {
		userJPADAO.persistEntity(JPAUserEntity1);
		userJPADAO.persistEntity(JPAUserEntity2);
		assertEquals(2, userJPADAO.findAllEntities().size());
		userJPADAO.removeEntity(JPAUserEntity1);
		assertEquals(1, userJPADAO.findAllEntities().size());
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
		roleJPADAO.persistEntity(JPARoleEntity1);
		JPAUserEntity1.addToRolesAndUsers(JPARoleEntity1);
		userJPADAO.persistEntity(JPAUserEntity1);
		assertEquals(1, roleJPADAO.findAllEntities().size());
		assertEquals(1, userJPADAO.findAllEntities().size());

		userJPADAO.removeEntity(JPAUserEntity1);
		assertEquals(1, roleJPADAO.findAllEntities().size());
		assertEquals(0, userJPADAO.findAllEntities().size());
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutAdding() throws EntityRemovalException {
		userJPADAO.removeEntity(JPAUserEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityWithoutId() throws EntityRemovalException {
		userJPADAO.removeEntity(JPAUserEntity1);
	}

	@Override
	@Test(expected = EntityRemovalException.class)
	public void testDeleteEntityUsingInvalidEM() throws EntityRemovalException {
		failureUserJPADAO.removeEntity(JPAUserEntity1);
	}

	@Override
	@Test
	public void testUpdateSimpleEntity() throws EntityRetrievalException, EntityPersistenceException {
		userJPADAO.persistEntity(JPAUserEntity1);
		assertEquals(JPAUserEntity1.getPassword(), PASSWORD);
		String entityKey = JPAUserEntity1.getKey();
		JPAUserEntity1.setPassword(PASSWORD_B);
		userJPADAO.updateEntity(JPAUserEntity1);
		assertEquals(entityKey, JPAUserEntity1.getKey());
		GAEUserEntity retrievedUdatedEntity = userJPADAO.findEntityById(JPAUserEntity1.getKey());
		assertEquals(PASSWORD_B, retrievedUdatedEntity.getPassword());
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
	public void testUpdateComplexUserEntity() throws EntityPersistenceException, EntityRetrievalException {
		roleJPADAO.persistEntity(JPARoleEntity4);
		JPAUserEntity1.addToRolesAndUsers(JPARoleEntity4);
		userJPADAO.persistEntity(JPAUserEntity1);
		assertEquals(1, userJPADAO.findAllEntities().size());
		assertEquals(1, roleJPADAO.findAllEntities().size());

		GAEUserEntity foundUserEntity = userJPADAO.findEntityById(JPAUserEntity1.getKey());
		ArrayList<GAERoleEntity> roles = new ArrayList<>(foundUserEntity.getRoles());
		assertNotNull(roles);
		GAERoleEntity firstRole = roles.get(0);
		assertEquals(DESCRIPTION, firstRole.getDescription());

		// update
		JPARoleEntity4.setDescription(ROLE_B);
		roleJPADAO.updateEntity(JPARoleEntity4);
		userJPADAO.updateEntity(JPAUserEntity1);

		// check updated entity
		GAEUserEntity retrievedEntity = userJPADAO.findEntityById(JPAUserEntity1.getKey());
		roles = new ArrayList<>(retrievedEntity.getRoles());
		firstRole = roles.get(0);
		assertEquals(ROLE_B, firstRole.getDescription());
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityWithoutAdding() throws EntityPersistenceException {
		GAEJPAUserEntity updatedEntity = new GAEJPAUserEntity();
		userJPADAO.updateEntity(updatedEntity);
	}

	@Override
	@Test(expected = EntityPersistenceException.class)
	public void testUpdateEntityUsingInvalidEM() throws EntityPersistenceException {
		failureUserJPADAO.updateEntity(JPAUserEntity1);
	}

	@Override
	@Test
	public void testFindSpecificEntity() throws EntityRetrievalException, EntityPersistenceException {
		userJPADAO.persistEntity(JPAUserEntity1);
		assertEquals(1, userJPADAO.findSpecificEntity(JPAUserEntity1).size());
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
		roleJPADAO.persistEntity(JPARoleEntity1);
		JPAUserEntity1.addToRolesAndUsers(JPARoleEntity1);
		userJPADAO.persistEntity(JPAUserEntity1);
		userJPADAO.persistEntity(JPAUserEntity2);
		List<GAEUserEntity> foundUsers = userJPADAO.findSpecificEntity(JPAUserEntity1);
		assertEquals(1, foundUsers.size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBySearchCriteria() throws EntityRetrievalException, EntityPersistenceException {
		assertEquals(0, userJPADAO.findSpecificEntity(JPAUserEntity3).size());
	}

	@Override
	@Test
	public void testFindSpecificEntityBeforeAdding() throws EntityRetrievalException {
		assertEquals(0, userJPADAO.findSpecificEntity(JPAUserEntity1).size());
	}

	@Override
	@Test(expected = EntityRetrievalException.class)
	public void testFindSpecificEntityUsingInvalidEM() throws EntityRetrievalException {
		failureUserJPADAO.findSpecificEntity(JPAUserEntity1);
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
	public void testGetEntityByRoleMultipleResults() throws EntityPersistenceException, EntityRetrievalException {
		roleJPADAO.persistEntity(JPARoleEntity1);
		roleJPADAO.persistEntity(JPARoleEntity2);
		JPAUserEntity1.addToRolesAndUsers(JPARoleEntity1);
		JPAUserEntity1.addToRolesAndUsers(JPARoleEntity2);
		userJPADAO.persistEntity(JPAUserEntity1);
		JPAUserEntity2.addToRolesAndUsers(JPARoleEntity2);
		userJPADAO.persistEntity(JPAUserEntity2);
		List<GAEUserEntity> foundUsersHavingRoleAorB = userJPADAO.getUserEntitiesByRoles(rolesAB);
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
	public void testGetEntityByRoleSingleResult() throws EntityPersistenceException, EntityRetrievalException {
		roleJPADAO.persistEntity(JPARoleEntity1);
		roleJPADAO.persistEntity(JPARoleEntity2);
		JPAUserEntity1.addToRolesAndUsers(JPARoleEntity1);
		JPAUserEntity1.addToRolesAndUsers(JPARoleEntity2);
		userJPADAO.persistEntity(JPAUserEntity1);
		JPAUserEntity2.addToRolesAndUsers(JPARoleEntity2);
		userJPADAO.persistEntity(JPAUserEntity2);
		List<GAEUserEntity> foundUsersHavingRoleA = userJPADAO.getUserEntitiesByRoles(roles);
		assertEquals(1, foundUsersHavingRoleA.size());
	}

	/**
	 * Method retrieving users by roles using invalid EM
	 * 
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve an entity
	 */
	@Test(expected = EntityRetrievalException.class)
	public void testGetEntityByRoleUsingInvalidEM() throws EntityRetrievalException {
		failureUserJPADAO.getUserEntitiesByRoles(rolesAB);
	}
}
