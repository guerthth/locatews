package amtc.gue.ws.books.persistence.dao.book.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.dao.ObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.model.role.objectify.GAEObjectifyRoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.objectify.GAEObjectifyBookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.persistence.model.tag.objectify.GAEObjectifyTagEntity;
import amtc.gue.ws.tournament.persistence.model.player.objectify.GAEObjectifyPlayerEntity;

/**
 * Implementation for the Objectify BookDAO
 * 
 * @author Thomas
 *
 */
public class BookObjectifyDAOImpl extends ObjectifyDAOImpl<GAEBookEntity, GAEObjectifyBookEntity, String>
		implements BookDAO<GAEBookEntity, GAEObjectifyBookEntity, String> {
	static {
		ObjectifyService.register(GAEObjectifyUserEntity.class);
		ObjectifyService.register(GAEObjectifyRoleEntity.class);
		ObjectifyService.register(GAEObjectifyBookEntity.class);
		ObjectifyService.register(GAEObjectifyTagEntity.class);
		ObjectifyService.register(GAEObjectifyPlayerEntity.class);
	}
	
	/**
	 * Constructor initializing currentUser
	 * 
	 * @param currentUser
	 *            the user that is using the service
	 */
	public BookObjectifyDAOImpl(User currentUser) {
		this.currentUser = currentUser;
	}

	@Override
	public List<GAEBookEntity> findSpecificEntity(GAEBookEntity entity) throws EntityRetrievalException {
		GAEObjectifyBookEntity specificEntity = (GAEObjectifyBookEntity) entity;
		List<GAEBookEntity> foundEntities = new ArrayList<>();
		try {
			if (specificEntity != null && specificEntity.getKey() != null) {
				// if entity has an ID, search by ID
				GAEObjectifyBookEntity foundEntity = (GAEObjectifyBookEntity) ofy().load().entity(specificEntity).now();
				if (foundEntity != null) {
					foundEntities.add(foundEntity);
				}
			} else {
				Query<GAEObjectifyBookEntity> query = ofy().load().type(GAEObjectifyBookEntity.class);
				if (specificEntity.getTitle() != null) {
					query = query.filter("title", specificEntity.getTitle());
				}
				if (specificEntity.getAuthor() != null) {
					query = query.filter("author", specificEntity.getAuthor());
				}
				if (specificEntity.getPrice() != null) {
					query = query.filter("price", specificEntity.getPrice());
				}
				if (specificEntity.getISBN() != null) {
					query = query.filter("ISBN", specificEntity.getISBN());
				}
				if (specificEntity.getDescription() != null) {
					query = query.filter("description", specificEntity.getDescription());
				}
				if (specificEntity.getTags() != null) {
					for (GAETagEntity tag : specificEntity.getTags()) {
						Ref<GAEObjectifyTagEntity> tagReferenceToQuery = Ref
								.create(Key.create(GAEObjectifyTagEntity.class, tag.getKey()));
						query = query.filter("tags", tagReferenceToQuery);
					}
				}
				if (specificEntity.getUsers() != null) {
					for (GAEUserEntity user : specificEntity.getUsers()) {
						Ref<GAEObjectifyUserEntity> userReferenceToQuery = Ref
								.create(Key.create(GAEObjectifyUserEntity.class, user.getKey()));
						query = query.filter("users", userReferenceToQuery);
					}
				}
				List<GAEObjectifyBookEntity> bookEntities = query.list();
				for (GAEObjectifyBookEntity bookEntity : bookEntities) {
					foundEntities.add(bookEntity);
				}
			}
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieval of specific BookEntity failed.", e);
		}
		return foundEntities;
	}

	@Override
	public GAEBookEntity updateEntity(GAEBookEntity entity) throws EntityPersistenceException {
		GAEObjectifyBookEntity specificEntity;
		try {
			specificEntity = (GAEObjectifyBookEntity) entity;
			ofy().save().entity(specificEntity).now();
		} catch (Exception e) {
			throw new EntityPersistenceException("Updating BookEntity " + entity.getKey() + " failed", e);
		}
		return specificEntity;
	}

	@Override
	public List<GAEBookEntity> getBookEntityByTag(Tags tags) throws EntityRetrievalException {
		List<GAEBookEntity> foundEntities = new ArrayList<>();
		try {
			Query<GAEObjectifyBookEntity> query = ofy().load().type(GAEObjectifyBookEntity.class);
			if (tags.getTags() != null) {
				for (String tag : tags.getTags()) {
					Ref<GAEObjectifyTagEntity> tagReferenceToQuery = Ref
							.create(Key.create(GAEObjectifyTagEntity.class, tag));
					query = query.filter("tags", tagReferenceToQuery);
				}
			}
			List<GAEObjectifyBookEntity> bookEntities = query.list();
			for (GAEObjectifyBookEntity bookEntity : bookEntities) {
				foundEntities.add(bookEntity);
			}
		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Retrieval of BookEntity for tags: " + tags.getTags().toString() + " failed.", e);
		}
		return foundEntities;
	}

	@Override
	public List<GAEBookEntity> getBookEntityForUserByTag(Tags tags) throws EntityRetrievalException {
		List<GAEBookEntity> foundEntities = new ArrayList<>();
		try {
			Query<GAEObjectifyBookEntity> query = ofy().load().type(GAEObjectifyBookEntity.class);
			if (tags.getTags() != null) {
				for (String tag : tags.getTags()) {
					Ref<GAEObjectifyTagEntity> tagReferenceToQuery = Ref
							.create(Key.create(GAEObjectifyTagEntity.class, tag));
					query = query.filter("tags", tagReferenceToQuery);
				}
			}
			if (currentUser != null) {
				Ref<GAEObjectifyUserEntity> userReferenceToQuery = Ref
						.create(Key.create(GAEObjectifyUserEntity.class, currentUser.getId()));
				query = query.filter("users", userReferenceToQuery);
			} else {
				query = query.filter("users", "");
			}
			List<GAEObjectifyBookEntity> bookEntities = query.list();
			for (GAEObjectifyBookEntity bookEntity : bookEntities) {
				foundEntities.add(bookEntity);
			}
		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Retrieval of BookEntity for tags: " + tags.getTags().toString() + " failed.", e);
		}
		return foundEntities;
	}

	@Override
	public List<GAEBookEntity> findSpecificBookEntityForUser(GAEBookEntity entity) throws EntityRetrievalException {
		GAEObjectifyBookEntity specificEntity = (GAEObjectifyBookEntity) entity;
		List<GAEBookEntity> foundEntities = new ArrayList<>();
		try {
			Query<GAEObjectifyBookEntity> query = ofy().load().type(GAEObjectifyBookEntity.class);
			if (specificEntity.getTitle() != null) {
				query = query.filter("title", specificEntity.getTitle());
			}
			if (specificEntity.getAuthor() != null) {
				query = query.filter("author", specificEntity.getAuthor());
			}
			if (specificEntity.getPrice() != null) {
				query = query.filter("price", specificEntity.getPrice());
			}
			if (specificEntity.getISBN() != null) {
				query = query.filter("ISBN", specificEntity.getISBN());
			}
			if (specificEntity.getDescription() != null) {
				query = query.filter("description", specificEntity.getDescription());
			}
			if (currentUser != null) {
				Ref<GAEObjectifyUserEntity> userReferenceToQuery = Ref
						.create(Key.create(GAEObjectifyUserEntity.class, currentUser.getId()));
				query = query.filter("users", userReferenceToQuery);
			} else {
				query = query.filter("users", "");
			}
			List<GAEObjectifyBookEntity> bookEntities = query.list();
			for (GAEObjectifyBookEntity bookEntity : bookEntities) {
				foundEntities.add(bookEntity);
			}
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieval of specific BookEntity for user '" + currentUser + "'failed.",
					e);
		}
		return foundEntities;
	}

}
