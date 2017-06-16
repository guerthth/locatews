package amtc.gue.ws.tournament.persistence.dao.player.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.dao.ObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.model.role.objectify.GAEObjectifyRoleEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.books.persistence.model.book.objectify.GAEObjectifyBookEntity;
import amtc.gue.ws.books.persistence.model.tag.objectify.GAEObjectifyTagEntity;
import amtc.gue.ws.tournament.persistence.dao.player.PlayerDAO;
import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;
import amtc.gue.ws.tournament.persistence.model.player.objectify.GAEObjectifyPlayerEntity;

/**
 * Implementation for the Objectify PlayerDAO
 * 
 * @author Thomas
 *
 */
public class PlayerObjectifyDAOImpl extends ObjectifyDAOImpl<GAEPlayerEntity, GAEObjectifyPlayerEntity, String>
		implements PlayerDAO<GAEPlayerEntity, GAEObjectifyPlayerEntity, String> {
	static {
		ObjectifyService.register(GAEObjectifyUserEntity.class);
		ObjectifyService.register(GAEObjectifyRoleEntity.class);
		ObjectifyService.register(GAEObjectifyBookEntity.class);
		ObjectifyService.register(GAEObjectifyTagEntity.class);
		ObjectifyService.register(GAEObjectifyPlayerEntity.class);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<GAEPlayerEntity> findSpecificEntity(GAEPlayerEntity entity) throws EntityRetrievalException {
		GAEObjectifyPlayerEntity specificEntity = (GAEObjectifyPlayerEntity) entity;
		List<GAEPlayerEntity> foundEntities = new ArrayList<>();
		if (specificEntity != null && specificEntity.getKey() != null) {
			// if entity has an ID, search by ID
			Key key = Key.create(entityClass, (String) specificEntity.getKey());
			GAEObjectifyPlayerEntity foundEntity = (GAEObjectifyPlayerEntity) ofy().load().key(key).now();
			if (foundEntity != null) {
				foundEntities.add(foundEntity);
			}
		}
		return foundEntities;
	}

	@Override
	public GAEPlayerEntity updateEntity(GAEPlayerEntity entity) throws EntityPersistenceException {
		GAEObjectifyPlayerEntity specificEntity;
		try {
			specificEntity = (GAEObjectifyPlayerEntity) entity;
			ofy().save().entity(specificEntity).now();
		} catch (Exception e) {
			throw new EntityPersistenceException("Updating PlayerEntity for player " + entity.getKey() + " failed", e);
		}
		return specificEntity;
	}
}
