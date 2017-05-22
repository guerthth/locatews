package amtc.gue.ws.base.persistence.dao.role.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.dao.JPADAOImpl;
import amtc.gue.ws.base.persistence.dao.role.RoleDAO;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.role.jpa.GAEJPARoleEntity;
import amtc.gue.ws.base.util.dao.RoleDAOImplUtils;

/**
 * Implementation for the JPA RoleDAO
 * 
 * @author Thomas
 *
 */
public class RoleJPADAOImpl extends JPADAOImpl<GAERoleEntity, GAEJPARoleEntity, String> implements
		RoleDAO<GAERoleEntity, GAEJPARoleEntity, String> {

	/** Select specific role query */
	private final String BASIC_ROLE_SPECIFIC_QUERY = "select r from "
			+ this.entityClass.getSimpleName() + " r";

	/**
	 * Constructor initializing entitymanagerfactory
	 * 
	 * @param emfInstance
	 *            the EMF instance used for EntityManagerFactory initialization
	 */
	public RoleJPADAOImpl(EMF emfInstance) {
		if (emfInstance != null) {
			this.entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAERoleEntity> findSpecificEntity(GAERoleEntity roleEntity)
			throws EntityRetrievalException {
		List<GAERoleEntity> foundRoles = new ArrayList<>();
		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager.createQuery(RoleDAOImplUtils
					.buildSpecificRoleQuery(BASIC_ROLE_SPECIFIC_QUERY,
							roleEntity));
			if (roleEntity.getKey() != null)
				q.setParameter("role", roleEntity.getKey());
			if (roleEntity.getDescription() != null)
				q.setParameter("description", roleEntity.getDescription());
			foundRoles = q.getResultList();
		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Retrieval of specific RoleEntity failed.", e);
		} finally {
			closeEntityManager();
		}

		return foundRoles;
	}

	@Override
	public GAEJPARoleEntity updateEntity(GAERoleEntity roleEntity)
			throws EntityPersistenceException {
		GAEJPARoleEntity updatedRoleEntity;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			updatedRoleEntity = entityManager.find(GAEJPARoleEntity.class,
					roleEntity.getKey());
			updatedRoleEntity.setDescription(roleEntity.getDescription());
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager != null) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityPersistenceException(
					"Updating description of roleEntity " + roleEntity.getKey()
							+ " failed", e);
		} finally {
			closeEntityManager();
		}

		return updatedRoleEntity;
	}
}
