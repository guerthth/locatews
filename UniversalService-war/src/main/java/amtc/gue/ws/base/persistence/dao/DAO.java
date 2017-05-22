package amtc.gue.ws.base.persistence.dao;

import java.util.List;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;

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
public interface DAO<S, E extends S, K> {

	/**
	 * Persisting an entity in the DB
	 * 
	 * @param entity
	 *            the entity that should be persisted
	 * @return persisted entity
	 * @throws EntityPersistenceException
	 */
	S persistEntity(S entity) throws EntityPersistenceException;

	/**
	 * Finding all the Entities of a specific type in the DB
	 * 
	 * @return all entities of a specific type
	 * @throws EntityRetrievalException
	 */
	List<S> findAllEntities() throws EntityRetrievalException;

	/**
	 * Finding specific Entities in the DB
	 * 
	 * @param entity
	 *            the entity that is searched for
	 * @return all entities matching the search entity criteria
	 */
	List<S> findSpecificEntity(S entity) throws EntityRetrievalException;

	/**
	 * Finding specific entity by id
	 * 
	 * @param id
	 *            the id that is searched for
	 * @return entity if specific type with id
	 * @throws EntityRetrievalException
	 */
	S findEntityById(K id) throws EntityRetrievalException;

	/**
	 * Removing entity from the DB
	 * 
	 * @param entity
	 *            the entity that should be removed
	 * @return the removed entity
	 * @throws EntityRemovalException
	 */
	S removeEntity(S entity) throws EntityRemovalException;

	/**
	 * Updating an entity in the DB
	 * 
	 * @param entity
	 *            the entity that should be updated
	 * 
	 * @return updated entity
	 * @throws EntityPersistenceException
	 */
	S updateEntity(S entity) throws EntityPersistenceException;

	/**
	 * Merging an an entity to the DB
	 * 
	 * @param entity
	 *            the entity that should be merged
	 * @return the entity that was merged to the DB
	 */
	S mergeEntity(S entity);

}
