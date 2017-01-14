package amtc.gue.ws.base.persistence.dao.user.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.dao.DAOImpl;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.model.GAEJPARoleEntity;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.base.util.UserServiceEntityMapper;
import amtc.gue.ws.base.util.dao.UserDAOImplUtils;

/**
 * Implementation for the UserDAO
 * 
 * @author Thomas
 *
 */
public class UserDAOImpl extends DAOImpl<GAEJPAUserEntity, String> implements
		UserDAO {

	/** Select specific user query **/
	private final String BASIC_USER_SPECIFIC_QUERY = "select u from "
			+ this.entityClass.getSimpleName() + " u";

	/**
	 * Constructor initializing entitymanagerfactory
	 * 
	 * @param emfInstance
	 *            the EMF instance used for EntityManagerFactory initialization
	 */
	public UserDAOImpl(EMF emfInstance) {
		if (emfInstance != null) {
			this.entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEJPAUserEntity> findSpecificEntity(GAEJPAUserEntity userEntity)
			throws EntityRetrievalException {
		List<GAEJPAUserEntity> foundUsers = new ArrayList<>();
		Set<GAEJPARoleEntity> roleEntities = userEntity.getRoles();
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
	public GAEJPAUserEntity updateEntity(GAEJPAUserEntity userEntity)
			throws EntityPersistenceException {
		GAEJPAUserEntity updatedUserEntity;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			updatedUserEntity = entityManager.find(GAEJPAUserEntity.class,
					userEntity.getKey());
			updatedUserEntity.setPassword(userEntity.getPassword());
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
	public List<GAEJPAUserEntity> getUserEntitiesByRoles(Roles roles)
			throws EntityRetrievalException {

		List<GAEJPAUserEntity> foundUserEntities = new ArrayList<>();
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
