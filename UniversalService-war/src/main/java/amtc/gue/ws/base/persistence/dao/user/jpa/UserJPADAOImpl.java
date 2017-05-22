package amtc.gue.ws.base.persistence.dao.user.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.dao.JPADAOImpl;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.jpa.GAEJPAUserEntity;
import amtc.gue.ws.base.util.dao.UserDAOImplUtils;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;

/**
 * Implementation for the JPA UserDAO
 * 
 * @author Thomas
 *
 */
public class UserJPADAOImpl extends JPADAOImpl<GAEUserEntity, GAEJPAUserEntity, String> implements
		UserDAO<GAEUserEntity, GAEJPAUserEntity, String> {

	/** Select specific user query **/
	private final String BASIC_USER_SPECIFIC_QUERY = "select u from "
			+ this.entityClass.getSimpleName() + " u";

	/**
	 * Constructor initializing entitymanagerfactory
	 * 
	 * @param emfInstance
	 *            the EMF instance used for EntityManagerFactory initialization
	 */
	public UserJPADAOImpl(EMF emfInstance) {
		if (emfInstance != null) {
			this.entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEUserEntity> findSpecificEntity(GAEUserEntity userEntity)
			throws EntityRetrievalException {
		List<GAEUserEntity> foundUsers = new ArrayList<>();
		Set<GAERoleEntity> roleEntities = userEntity.getRoles();
		Roles roles = new Roles();
		roles.setRoles(UserServiceEntityMapper
				.mapRoleEntityListToRoles(roleEntities));
		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager.createQuery(UserDAOImplUtils
					.buildSpecificUserQuery(BASIC_USER_SPECIFIC_QUERY,
							userEntity));
			if (userEntity.getKey() != null)
				q.setParameter("id", userEntity.getKey());
			if (userEntity.getPassword() != null)
				q.setParameter("password", userEntity.getPassword());
			if (userEntity.getEmail() != null) {
				q.setParameter("email", userEntity.getEmail());
			}
			foundUsers = q.getResultList();
		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Retrieval of specific UserEntity failed", e);
		} finally {
			closeEntityManager();
		}
		return UserDAOImplUtils.retrieveUserEntitiesWithSpecificRolesOnly(
				foundUsers, roles);
	}

	@Override
	public GAEJPAUserEntity updateEntity(GAEUserEntity userEntity)
			throws EntityPersistenceException {
		GAEJPAUserEntity updatedUserEntity;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			updatedUserEntity = entityManager.find(GAEJPAUserEntity.class,
					userEntity.getKey());
			updatedUserEntity.setPassword(userEntity.getPassword());
			updatedUserEntity.setEmail(userEntity.getEmail());
			updatedUserEntity.setRoles(userEntity.getRoles(), true);
			updatedUserEntity.setBooks(userEntity.getBooks(), true);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager != null) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityPersistenceException(
					"Updating UserEntity for userName " + userEntity.getKey()
							+ " failed", e);
		} finally {
			closeEntityManager();
		}
		return updatedUserEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEUserEntity> getUserEntitiesByRoles(Roles roles)
			throws EntityRetrievalException {

		List<GAEUserEntity> foundUserEntities = new ArrayList<>();
		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager.createQuery(ENTITY_SELECTION_QUERY,
					GAEJPAUserEntity.class);
			foundUserEntities = q.getResultList();
		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Retrieval of UserEntity for roles: "
							+ roles.getRoles().toString() + " failed.", e);
		} finally {
			closeEntityManager();
		}
		return UserDAOImplUtils.retrieveUserEntitiesWithSpecificRoles(
				foundUserEntities, roles);
	}
}
