package amtc.gue.ws.base.persistence.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import com.googlecode.objectify.Key;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.model.PersistenceEntity;

/**
 * Objectify DAO Implementation. Includes common method codes for persisting,
 * remove, findAll, and find by ID
 * 
 * @author Thomas
 *
 * @param <E>
 *            type of the entity
 * @param <K>
 *            type of the id
 */
public abstract class ObjectifyDAOImpl<S extends PersistenceEntity, E extends S, K> implements DAO<S, E, K> {

	@SuppressWarnings("rawtypes")
	protected Class entityClass;
	protected User currentUser;

	/**
	 * Constructor
	 */
	@SuppressWarnings("rawtypes")
	public ObjectifyDAOImpl() {
		// set entityClass to 2nd typeargument of generic superclass
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		entityClass = (Class) genericSuperclass.getActualTypeArguments()[1];
	}

	@SuppressWarnings("unchecked")
	@Override
	public S persistEntity(S entity) throws EntityPersistenceException {
		E specificEntity;
		try {
			specificEntity = (E) entity;
			if (specificEntity.getKey() != null) {
				E foundEntity = ofy().load().entity(specificEntity).now();
				if (foundEntity != null) {
					throw new EntityPersistenceException();
				}
			}
			ofy().save().entity(entity).now();
		} catch (Exception e) {
			throw new EntityPersistenceException("Persisting " + entityClass.getName() + " failed. ", e);
		}
		return specificEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<S> findAllEntities() throws EntityRetrievalException {
		List<S> entities;
		try {
			entities = ofy().load().type(entityClass).list();
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieval of all existing " + entityClass.getName() + " failed.", e);
		}
		return entities;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public S findEntityById(K id) throws EntityRetrievalException {
		E foundEntity;
		Object foundObject;
		try {
			Key key;
			key = Key.create((String) id);
			foundObject = ofy().load().key(key).now();
			// TODO check
			// long longID;
			// key = Key.create(entityClass, (String) id);
			// foundObject = ofy().load().key(key).now();
			// if (foundObject == null) {
			// longID = Long.valueOf((String) id).longValue();
			// key = Key.create(entityClass, longID);
			// foundObject = ofy().load().key(key).now();
			// }
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieval of " + entityClass.getName() + " with id: " + id + " failed.",
					e);
		}
		foundEntity = (E) foundObject;
		return foundEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public S removeEntity(S entity) throws EntityRemovalException {
		E specificEntity;
		try {
			specificEntity = (E) entity;
			Key<E> key = Key.create(entity.getWebsafeKey());
			ofy().delete().key(key).now();
		} catch (Exception e) {
			throw new EntityRemovalException(
					"Deleting " + entityClass.getName() + " for id " + entity.getKey() + " failed", e);
		}
		return specificEntity;
	}

	@Override
	public S mergeEntity(S entity) {
		// Not implemented for Objectify DAO
		return null;
	}
}
