package amtc.gue.ws.books.persistence.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRemovalException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.model.PersistenceEntity;

/**
 * General DAO Implementation. 
 * Includes common method codes for persisting, remove, findAll, and find by ID
 * 
 * @author Thomas
 *
 * @param <E> type of the entity
 * @param <K> type of the id
 */
public abstract class DAOImpl<E extends PersistenceEntity, K> implements DAO<E, K> {

	@SuppressWarnings("rawtypes")
	protected Class entityClass;

	protected EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;
	
	protected final String ENTITY_SELECTION_QUERY;

	@SuppressWarnings("rawtypes")
	public DAOImpl() {
		// set entityClass to 2nd typeargument of generic superclass
		ParameterizedType genericSuperclass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		this.entityClass = (Class) genericSuperclass
				.getActualTypeArguments()[0];
		this.ENTITY_SELECTION_QUERY = "select e from "
				+ entityClass.getSimpleName() + " e";
	}

	@Override
	public E persistEntity(E entity) throws EntityPersistenceException {

		try {
			// set entitymanager
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(entity);
			entityManager.getTransaction().commit();
		} catch (PersistenceException e) {
			// rollback
			entityManager.getTransaction().rollback();
			throw new EntityPersistenceException("Persisting "
					+ entityClass.getName() + " failed. ", e);
		} finally {
			closeEntityManager();
		}

		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> findAllEntities() {
		
		// set entitymanager
		entityManager = entityManagerFactory.createEntityManager();
		Query q = entityManager.createQuery(ENTITY_SELECTION_QUERY,
				entityClass);
		List<E> entities = q.getResultList();
		closeEntityManager();

		return entities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E findEntityById(K id) throws EntityRetrievalException {
		
		E foundEntity;

		try {
			entityManager = entityManagerFactory.createEntityManager();
			foundEntity = (E) entityManager.find(entityClass, id); // retrieve book by id
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieval of " 
					+ entityClass.getName() + " with id: "
					+ id + " failed.", e);
		} finally {
			closeEntityManager();
		}

		return foundEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public E removeEntity(E entity) throws EntityRemovalException {
		E entityToRemove;
		try {
			// set entitymanager and delete
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			entityToRemove = (E) entityManager.find(entityClass,entity.getId());
			entityManager.remove(entityToRemove);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			// rollback
			entityManager.getTransaction().rollback();
			throw new EntityRemovalException("Deleting " + entityClass.getName() + 
					" for id " + entity.getId() +" failed", e);
		} finally {
			closeEntityManager();
		}
		
		return entityToRemove;
	}

	@Override
	public abstract E updateEntity(E entity) throws EntityPersistenceException;
	
	/**
	 * Close the entityManager instance if it is not null
	 * 
	 * @param entityManager
	 *            the EntityManager instance
	 */
	protected void closeEntityManager() {
		if (this.entityManager != null) {
			this.entityManager.close();
		}
	}

}
