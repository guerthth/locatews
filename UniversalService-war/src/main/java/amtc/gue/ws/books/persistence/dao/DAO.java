package amtc.gue.ws.books.persistence.dao;

import java.util.List;

import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRemovalException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;

/**
 * Generic DAO interface
 * 
 * @author Thomas
 *
 * @param <E>
 *            Entity to save, remove, search for
 * @param <K>
 *            id Type
 */
public interface DAO<E, K> {

	/**
	 * Persisting an entity in the DB
	 * 
	 * @param entity
	 *            the entity that should be persisted
	 * @return persisted entity
	 * @throws EntityPersistenceException
	 */
	E persistEntity(E entity) throws EntityPersistenceException;

	/**
	 * Finding all the Entities of a specific type in the DB
	 * 
	 * @return all entities of a specific type
	 * @throws EntityRetrievalException
	 */
	List<E> findAllEntities() throws EntityRetrievalException;

	/**
	 * Finding specific Entities in the DB
	 * 
	 * @param entity
	 *            the entity that is searched for
	 * @return all entities matching the search entity criteria
	 */
	List<E> findSpecificEntity(E entity) throws EntityRetrievalException;

	/**
	 * Finding specific entity by id
	 * 
	 * @param id
	 *            the id that is searched for
	 * @return entity if specific type with id
	 * @throws EntityRetrievalException
	 */
	E findEntityById(K id) throws EntityRetrievalException;

	/**
	 * Removing entity from the DB
	 * 
	 * @param entity
	 *            the entity that should be removed
	 * @return the removed entity
	 * @throws EntityRemovalException
	 */
	E removeEntity(E entity) throws EntityRemovalException;

	/**
	 * Updating an entity in the DB
	 * 
	 * @param entity
	 *            the entity that should be updated
	 * 
	 * @return updated entity
	 * @throws EntityPersistenceException
	 */
	E updateEntity(E entity) throws EntityPersistenceException;

}
