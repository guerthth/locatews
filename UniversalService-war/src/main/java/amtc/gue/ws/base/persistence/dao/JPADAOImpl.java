package amtc.gue.ws.base.persistence.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.model.PersistenceEntity;

/**
 * General DAO Implementation. Includes common method codes for persisting,
 * remove, findAll, and find by ID
 * 
 * @author Thomas
 *
 * @param <E>
 *            type of the entity
 * @param <K>
 *            type of the id
 */
public abstract class JPADAOImpl<S extends PersistenceEntity, E extends S, K> implements DAO<S, E, K> {

	@SuppressWarnings("rawtypes")
	protected Class entityClass;

	protected EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;
	protected User currentUser;

	protected final String ENTITY_SELECTION_QUERY;

	/**
	 * Constructor initializes the ENTITYSELECTIONQUERY
	 */
	@SuppressWarnings("rawtypes")
	public JPADAOImpl() {
		// set entityClass to 2nd typeargument of generic superclass
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		entityClass = (Class) genericSuperclass.getActualTypeArguments()[1];
		ENTITY_SELECTION_QUERY = "select e from " + this.entityClass.getSimpleName() + " e";
	}

	@SuppressWarnings("unchecked")
	@Override
	public S persistEntity(S entity) throws EntityPersistenceException {
		E specificEntity;
		try {
			specificEntity = (E) entity;

			// set entitymanager
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(specificEntity);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			// rollback
			if (entityManager != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityPersistenceException("Persisting " + entityClass.getName() + " failed. ", e);
		} finally {
			closeEntityManager();
		}
		return specificEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<S> findAllEntities() throws EntityRetrievalException {
		List<S> entities;
		try {
			// set entitymanager
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager.createQuery(ENTITY_SELECTION_QUERY);
			entities = q.getResultList();
		} catch (Exception e) {
			if (entityManager != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityRetrievalException("Retrieval of all existing " + entityClass.getName() + " failed.", e);
		} finally {
			closeEntityManager();
		}
		return entities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S findEntityById(K id) throws EntityRetrievalException {

		E foundEntity;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			foundEntity = (E) entityManager.find(entityClass, id);
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieval of " + entityClass.getName() + " with id: " + id + " failed.",
					e);
		} finally {
			closeEntityManager();
		}
		return foundEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S removeEntity(S entity) throws EntityRemovalException {
		E entityToRemove;
		try {
			// set entitymanager and delete
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			entityToRemove = (E) entityManager.find(entityClass, entity.getKey());
			if (entityToRemove == null)
				throw new EntityRetrievalException("Entity with key '" + entity.getKey() + "' not found.");
			entityManager.remove(entityToRemove);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			// rollback
			if (entityManager != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityRemovalException(
					"Deleting " + entityClass.getName() + " for id " + entity.getKey() + " failed", e);
		} finally {
			closeEntityManager();
		}
		return entityToRemove;
	}

	@Override
	public S mergeEntity(E entity) {
		entityManager = entityManagerFactory.createEntityManager();
		entityManager.merge(entity);
		closeEntityManager();
		return entity;
	}

	/**
	 * Close the entityManager instance if it is not null
	 * 
	 * @param entityManager
	 *            the EntityManager instance
	 */
	protected void closeEntityManager() {
		if (this.entityManager != null && this.entityManager.isOpen()) {
			this.entityManager.close();
		}
	}

	/**
	 * Clearing the EntityManagerFactory Cache
	 */
	protected void clearCache() {
		if (entityManagerFactory.getCache() != null) {
			entityManagerFactory.getCache().evictAll();
		}
	}

}
