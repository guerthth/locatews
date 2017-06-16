package amtc.gue.ws.base.persistence.dao.role.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.dao.ObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.dao.role.RoleDAO;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.role.objectify.GAEObjectifyRoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.books.persistence.model.book.objectify.GAEObjectifyBookEntity;
import amtc.gue.ws.books.persistence.model.tag.objectify.GAEObjectifyTagEntity;
import amtc.gue.ws.tournament.persistence.model.player.objectify.GAEObjectifyPlayerEntity;

/**
 * Implementation for the Objectify RoleDAO
 * 
 * @author Thomas
 *
 */
public class RoleObjectifyDAOImpl extends ObjectifyDAOImpl<GAERoleEntity, GAEObjectifyRoleEntity, String>
		implements RoleDAO<GAERoleEntity, GAEObjectifyRoleEntity, String> {
	static {
		ObjectifyService.register(GAEObjectifyUserEntity.class);
		ObjectifyService.register(GAEObjectifyRoleEntity.class);
		ObjectifyService.register(GAEObjectifyBookEntity.class);
		ObjectifyService.register(GAEObjectifyTagEntity.class);
		ObjectifyService.register(GAEObjectifyPlayerEntity.class);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<GAERoleEntity> findSpecificEntity(GAERoleEntity entity) throws EntityRetrievalException {
		GAEObjectifyRoleEntity specificEntity = (GAEObjectifyRoleEntity) entity;
		List<GAERoleEntity> foundEntities = new ArrayList<>();
		if (specificEntity != null && specificEntity.getKey() != null) {
			// if entity has an ID, search by ID
			Key key = Key.create(entityClass, (String) specificEntity.getKey());
			GAEObjectifyRoleEntity foundEntity = (GAEObjectifyRoleEntity) ofy().load().key(key).now();
			if (foundEntity != null) {
				foundEntities.add(foundEntity);
			}
		} else {
			Query<GAEObjectifyRoleEntity> query = ofy().load().type(GAEObjectifyRoleEntity.class);
			if (specificEntity.getDescription() != null) {
				query = query.filter("description", specificEntity.getDescription());
			}
			if (specificEntity.getUsers() != null) {
				for (GAEUserEntity user : specificEntity.getUsers()) {
					Ref<GAEObjectifyUserEntity> userReferenceToQuery = Ref
							.create(Key.create(GAEObjectifyUserEntity.class, user.getKey()));
					query = query.filter("users", userReferenceToQuery);
				}
			}
			List<GAEObjectifyRoleEntity> roleEntities = query.list();
			for (GAEObjectifyRoleEntity roleEntitiy : roleEntities) {
				foundEntities.add(roleEntitiy);
			}
		}
		return foundEntities;
	}

	@Override
	public GAERoleEntity updateEntity(GAERoleEntity entity) throws EntityPersistenceException {
		GAEObjectifyRoleEntity specificEntity;
		try {
			specificEntity = (GAEObjectifyRoleEntity) entity;
			ofy().save().entity(specificEntity).now();
		} catch (Exception e) {
			throw new EntityPersistenceException("Updating RoleEntity for role " + entity.getKey() + " failed", e);
		}
		return specificEntity;
	}
}
