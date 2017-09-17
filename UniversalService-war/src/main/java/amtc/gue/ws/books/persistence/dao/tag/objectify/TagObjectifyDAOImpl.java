package amtc.gue.ws.books.persistence.dao.tag.objectify;

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
import amtc.gue.ws.base.persistence.model.role.objectify.GAEObjectifyRoleEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.objectify.GAEObjectifyBookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.persistence.model.tag.objectify.GAEObjectifyTagEntity;
import amtc.gue.ws.tournament.persistence.model.player.objectify.GAEObjectifyPlayerEntity;

/**
 * Implementation for the Objectify TagDAO
 * 
 * @author Thomas
 *
 */
public class TagObjectifyDAOImpl extends ObjectifyDAOImpl<GAETagEntity, GAEObjectifyTagEntity, String>
		implements TagDAO<GAETagEntity, GAEObjectifyTagEntity, String> {
	static {
		ObjectifyService.register(GAEObjectifyUserEntity.class);
		ObjectifyService.register(GAEObjectifyRoleEntity.class);
		ObjectifyService.register(GAEObjectifyBookEntity.class);
		ObjectifyService.register(GAEObjectifyTagEntity.class);
		ObjectifyService.register(GAEObjectifyPlayerEntity.class);
	}
	
	@Override
	public List<GAETagEntity> findSpecificEntity(GAETagEntity entity) throws EntityRetrievalException {
		GAEObjectifyTagEntity specificEntity = (GAEObjectifyTagEntity) entity;
		List<GAETagEntity> foundEntities = new ArrayList<>();
		try {
			if(specificEntity != null){
				if (specificEntity.getKey() != null) {
					// if entity has an ID, search by ID
					GAEObjectifyTagEntity foundEntity = (GAEObjectifyTagEntity) ofy().load().entity(specificEntity).now();
					if (foundEntity != null) {
						foundEntities.add(foundEntity);
					}
				} else {
					Query<GAEObjectifyTagEntity> query = ofy().load().type(GAEObjectifyTagEntity.class);
					if (specificEntity.getBooks() != null) {
						for (GAEBookEntity book : specificEntity.getBooks()) {
							Ref<GAEObjectifyBookEntity> bookReferenceToQuery = Ref.create(
									Key.create(GAEObjectifyBookEntity.class, Long.valueOf(book.getKey()).longValue()));
							query = query.filter("books", bookReferenceToQuery);
						}
					}
					List<GAEObjectifyTagEntity> tagEntities = query.list();
					for (GAEObjectifyTagEntity tagEntity : tagEntities) {
						foundEntities.add(tagEntity);
					}
				}
			}		
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieval of specific TagEntity failed.", e);
		}
		return foundEntities;
	}

	@Override
	public GAETagEntity updateEntity(GAETagEntity entity) throws EntityPersistenceException {
		GAEObjectifyTagEntity specificEntity;
		try {
			specificEntity = (GAEObjectifyTagEntity) entity;
			ofy().save().entity(specificEntity).now();
		} catch (Exception e) {
			throw new EntityPersistenceException("Updating TagEntity " + entity.getKey() + " failed", e);
		}
		return specificEntity;
	}

}
