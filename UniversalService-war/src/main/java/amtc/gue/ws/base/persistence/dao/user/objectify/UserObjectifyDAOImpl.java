package amtc.gue.ws.base.persistence.dao.user.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.persistence.dao.ObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.role.objectify.GAEObjectifyRoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.objectify.GAEObjectifyBookEntity;
import amtc.gue.ws.books.persistence.model.tag.objectify.GAEObjectifyTagEntity;
import amtc.gue.ws.tournament.persistence.model.player.objectify.GAEObjectifyPlayerEntity;

/**
 * Implementation for the Objectify UserDAO
 * 
 * @author Thomas
 *
 */
public class UserObjectifyDAOImpl extends ObjectifyDAOImpl<GAEUserEntity, GAEObjectifyUserEntity, String>
		implements UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> {
	static {
		ObjectifyService.register(GAEObjectifyUserEntity.class);
		ObjectifyService.register(GAEObjectifyRoleEntity.class);
		ObjectifyService.register(GAEObjectifyBookEntity.class);
		ObjectifyService.register(GAEObjectifyTagEntity.class);
		ObjectifyService.register(GAEObjectifyPlayerEntity.class);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<GAEUserEntity> findSpecificEntity(GAEUserEntity entity) throws EntityRetrievalException {
		GAEObjectifyUserEntity specificEntity = (GAEObjectifyUserEntity) entity;
		List<GAEUserEntity> foundEntities = new ArrayList<>();
		if (specificEntity != null && specificEntity.getKey() != null) {
			// if entity has an ID, search by ID
			Key key = Key.create(entityClass, (String) specificEntity.getKey());
			GAEObjectifyUserEntity foundEntity = (GAEObjectifyUserEntity) ofy().load().key(key).now();
			if (foundEntity != null) {
				foundEntities.add(foundEntity);
			}
		} else {
			// if not, search for similar attributes
			Query<GAEObjectifyUserEntity> query = ofy().load().type(GAEObjectifyUserEntity.class);
			if (specificEntity.getUserName() != null) {
				query = query.filter("userName", specificEntity.getUserName());
			}
			if (specificEntity.getPassword() != null) {
				query = query.filter("password", specificEntity.getPassword());
			}
			if (specificEntity.getRoles() != null) {
				for (GAERoleEntity role : specificEntity.getRoles()) {
					Ref<GAEObjectifyRoleEntity> roleReferenceToQuery = Ref
							.create(Key.create(GAEObjectifyRoleEntity.class, role.getKey()));
					query = query.filter("roles", roleReferenceToQuery);
				}
			}
			if (specificEntity.getBooks() != null) {
				for (GAEBookEntity book : specificEntity.getBooks()) {
					Ref<GAEObjectifyBookEntity> bookReferenceToQuery = Ref
							.create(Key.create(GAEObjectifyBookEntity.class, Long.valueOf(book.getKey()).longValue()));
					query = query.filter("books", bookReferenceToQuery);
				}
			}
			List<GAEObjectifyUserEntity> userEntities = query.list();
			for (GAEObjectifyUserEntity userEntity : userEntities) {
				foundEntities.add(userEntity);
			}
		}
		return foundEntities;
	}

	@Override
	public GAEUserEntity updateEntity(GAEUserEntity entity) throws EntityPersistenceException {
		GAEObjectifyUserEntity specificEntity;
		try {
			specificEntity = (GAEObjectifyUserEntity) entity;
			ofy().save().entity(specificEntity).now();
		} catch (Exception e) {
			throw new EntityPersistenceException("Updating UserEntity for email " + entity.getKey() + " failed", e);
		}
		return specificEntity;
	}

	@Override
	public List<GAEUserEntity> getUserEntitiesByRoles(Roles roles) throws EntityRetrievalException {
		GAEObjectifyUserEntity searchUser = new GAEObjectifyUserEntity();
		if (roles != null) {
			for (String role : roles.getRoles()) {
				GAEObjectifyRoleEntity roleEntity = new GAEObjectifyRoleEntity();
				roleEntity.setKey(role);
				searchUser.addToRolesOnly(roleEntity);
			}
		}
		return this.findSpecificEntity(searchUser);
	}
}
