package amtc.gue.ws.books.persistence.dao.book.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.dao.JPADAOImpl;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.model.book.GAEBookEntity;
import amtc.gue.ws.books.persistence.model.book.jpa.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.util.dao.BookDAOImplUtils;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;

/**
 * Implementation for the JPA BookDAO
 * 
 * @author Thomas
 *
 */
public class BookJPADAOImpl extends JPADAOImpl<GAEBookEntity, GAEJPABookEntity, String>
		implements BookDAO<GAEBookEntity, GAEJPABookEntity, String> {

	/** Select queries */
	private final String BOOK_SPECIFIC_USER_QUERY = ENTITY_SELECTION_QUERY
			+ " join e.users user where user.userId = :user";

	/**
	 * Constructor initializing entitiymanagerfactory
	 * 
	 * @param emfInstance
	 *            the EMF instance used for EntityManagerFactory initialization
	 * @param currentUser
	 *            the user that is using the service
	 */
	public BookJPADAOImpl(EMF emfInstance, User currentUser) {
		if (emfInstance != null) {
			entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
		this.currentUser = currentUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEBookEntity> findSpecificEntity(GAEBookEntity bookEntity) throws EntityRetrievalException {
		List<GAEBookEntity> foundBooks = new ArrayList<>();
		Set<GAETagEntity> tagEntities = bookEntity.getTags();
		Tags tags = new Tags();
		tags.setTags(BookServiceEntityMapper.mapTagEntityListToTags(tagEntities));

		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager
					.createQuery(BookDAOImplUtils.buildSpecificBookQuery(ENTITY_SELECTION_QUERY, bookEntity));
			if (bookEntity.getKey() != null && bookEntity.getKey().length() > 0)
				q.setParameter("id", bookEntity.getKey());
			if (bookEntity.getTitle() != null)
				q.setParameter("title", bookEntity.getTitle());
			if (bookEntity.getAuthor() != null)
				q.setParameter("author", bookEntity.getAuthor());
			if (bookEntity.getPrice() != null)
				q.setParameter("price", bookEntity.getPrice());
			if (bookEntity.getISBN() != null)
				q.setParameter("ISBN", bookEntity.getISBN());
			if (bookEntity.getDescription() != null)
				q.setParameter("description", bookEntity.getDescription());
			foundBooks = q.getResultList();
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieval of specific BookEntity failed.", e);
		} finally {
			closeEntityManager();
		}

		return BookDAOImplUtils.retrieveBookEntitiesWithSpecificTagsOnly(foundBooks, tags);
	}

	@Override
	public GAEBookEntity updateEntity(GAEBookEntity entity) throws EntityPersistenceException {
		GAEJPABookEntity updatedBookEntity;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();

			updatedBookEntity = (GAEJPABookEntity) entityManager.find(GAEJPABookEntity.class, entity.getKey());
			updatedBookEntity.setAuthor(entity.getAuthor());
			updatedBookEntity.setDescription(entity.getDescription());
			updatedBookEntity.setISBN(entity.getISBN());
			updatedBookEntity.setPrice(entity.getPrice());
			updatedBookEntity.setTags(entity.getTags(), true);
			updatedBookEntity.setTitle(entity.getTitle());
			updatedBookEntity.setUsers(entity.getUsers(), true);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager != null) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityPersistenceException(
					"Updating BookEntity for ISBN " + entity.getISBN() + " and ID " + entity.getKey() + " failed", e);
		} finally {
			closeEntityManager();
		}
		return updatedBookEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEBookEntity> getBookEntityByTag(Tags tags) throws EntityRetrievalException {
		List<GAEBookEntity> foundBooks = new ArrayList<>();
		try {
			// set entitymanager
			entityManager = entityManagerFactory.createEntityManager();
			// read bookentites from DB that possess specific tags
			Query q = entityManager.createQuery(ENTITY_SELECTION_QUERY, GAEJPABookEntity.class);
			foundBooks = q.getResultList();
		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Retrieval of BookEntity for tags: " + tags.getTags().toString() + " failed.", e);
		} finally {
			closeEntityManager();
		}
		return BookDAOImplUtils.retrieveBookEntitiesWithSpecificTags(foundBooks, tags);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEBookEntity> getBookEntityForUserByTag(Tags tags) throws EntityRetrievalException {
		List<GAEBookEntity> foundBooksForUser = new ArrayList<>();
		try {
			// set entitymanager
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager.createQuery(BOOK_SPECIFIC_USER_QUERY);
			if (currentUser != null) {
				q.setParameter("user", currentUser.getId());
			} else {
				q.setParameter("user", "");
			}

			foundBooksForUser = q.getResultList();
		} catch (Exception e) {
			if (entityManager != null && entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityRetrievalException("Retrieval of all existing " + entityClass.getName()
					+ " objects for user '" + currentUser + "' failed.", e);
		} finally {
			closeEntityManager();
		}

		return BookDAOImplUtils.retrieveBookEntitiesWithSpecificTags(foundBooksForUser, tags);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEBookEntity> findSpecificBookEntityForUser(GAEBookEntity bookEntity) throws EntityRetrievalException {
		List<GAEBookEntity> foundBooks = new ArrayList<>();
		Set<GAETagEntity> tagEntities = bookEntity.getTags();

		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager
					.createQuery(BookDAOImplUtils.buildSpecificBookQuery(BOOK_SPECIFIC_USER_QUERY, bookEntity));
			if (bookEntity.getKey() != null && bookEntity.getKey().length() > 0)
				q.setParameter("id", bookEntity.getKey());
			if (bookEntity.getTitle() != null)
				q.setParameter("title", bookEntity.getTitle());
			if (bookEntity.getAuthor() != null)
				q.setParameter("author", bookEntity.getAuthor());
			if (bookEntity.getPrice() != null)
				q.setParameter("price", bookEntity.getPrice());
			if (bookEntity.getISBN() != null)
				q.setParameter("ISBN", bookEntity.getISBN());
			if (bookEntity.getDescription() != null)
				q.setParameter("description", bookEntity.getDescription());
			if (currentUser != null) {
				q.setParameter("user", currentUser.getId());
			} else {
				q.setParameter("user", "");
			}
			foundBooks = q.getResultList();
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieval of specific BookEntity for user '" + currentUser + "'failed.",
					e);
		} finally {
			closeEntityManager();
		}

		if (tagEntities != null && !tagEntities.isEmpty()) {
			Tags tags = new Tags();
			tags.setTags(BookServiceEntityMapper.mapTagEntityListToTags(tagEntities));
			return BookDAOImplUtils.retrieveBookEntitiesWithSpecificTagsOnly(foundBooks, tags);
		} else {
			return foundBooks;
		}
	}

}
