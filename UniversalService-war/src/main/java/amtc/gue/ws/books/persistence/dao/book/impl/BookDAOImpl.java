package amtc.gue.ws.books.persistence.dao.book.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.dao.DAOImpl;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.util.BookServiceEntityMapper;
import amtc.gue.ws.books.util.dao.BookDAOImplUtils;

/**
 * Book DAO Implementation Includes methods specifically for bookentities
 * 
 * @author Thomas
 *
 */
public class BookDAOImpl extends DAOImpl<GAEJPABookEntity, String> implements
		BookDAO {

	/** Select queries */
	private final String BOOK_SPECIFIC_USER_QUERY = ENTITY_SELECTION_QUERY
			+ " join e.users user where user.userId = :user";

	/**
	 * Constructor initializing entitiymanagerfactory
	 * 
	 * @param emfInstance
	 *            the EMF instance used for EntityManagerFactory initialization
	 */
	public BookDAOImpl(EMF emfInstance, User user) {
		if (emfInstance != null) {
			this.entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
		this.currentUser = user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEJPABookEntity> findSpecificEntity(GAEJPABookEntity bookEntity)
			throws EntityRetrievalException {
		List<GAEJPABookEntity> foundBooks = new ArrayList<>();
		Set<GAEJPATagEntity> tagEntities = bookEntity.getTags();
		Tags tags = new Tags();
		tags.setTags(BookServiceEntityMapper
				.mapTagEntityListToTags(tagEntities));

		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager
					.createQuery(BookDAOImplUtils.buildSpecificBookQuery(
							ENTITY_SELECTION_QUERY, bookEntity));
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
			throw new EntityRetrievalException(
					"Retrieval of specific BookEntity failed.", e);
		} finally {
			closeEntityManager();
		}

		return BookDAOImplUtils.retrieveBookEntitiesWithSpecificTagsOnly(
				foundBooks, tags);

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEJPABookEntity> getBookEntityByTag(Tags tags)
			throws EntityRetrievalException {

		List<GAEJPABookEntity> foundBooks = new ArrayList<>();

		try {

			// set entitymanager
			entityManager = entityManagerFactory.createEntityManager();

			// read bookentites from DB that possess specific tags
			Query q = entityManager.createQuery(ENTITY_SELECTION_QUERY,
					GAEJPABookEntity.class);
			foundBooks = q.getResultList();

		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Retrieval of BookEntity for tags: "
							+ tags.getTags().toString() + " failed.", e);
		} finally {
			closeEntityManager();
		}
		if (currentUser != null)
			System.out.println(currentUser.getId());
		return BookDAOImplUtils.retrieveBookEntitiesWithSpecificTags(
				foundBooks, tags);
	}

	@Override
	public GAEJPABookEntity updateEntity(GAEJPABookEntity entity)
			throws EntityPersistenceException {
		GAEJPABookEntity updatedBookEntity;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();

			updatedBookEntity = (GAEJPABookEntity) entityManager.find(
					GAEJPABookEntity.class, entity.getKey());
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
					"Updating BookEntity for ISBN " + entity.getISBN()
							+ " and ID " + entity.getKey() + " failed", e);
		} finally {
			closeEntityManager();
		}
		if (currentUser != null)
			System.out.println(currentUser.getId());
		return updatedBookEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEJPABookEntity> findAllBookEntitiesForUser()
			throws EntityRetrievalException {
		List<GAEJPABookEntity> bookEntitiesForUser;
		try {
			// set entitymanager
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager.createQuery(BOOK_SPECIFIC_USER_QUERY);
			if (this.currentUser != null) {
				q.setParameter("user", this.currentUser.getId());
			} else {
				q.setParameter("user", "");
			}
			bookEntitiesForUser = q.getResultList();
		} catch (Exception e) {
			if (entityManager != null
					&& entityManager.getTransaction().isActive()) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityRetrievalException("Retrieval of all existing "
					+ entityClass.getName() + " objects for user '"
					+ this.currentUser + "' failed.", e);
		} finally {
			closeEntityManager();
		}

		return bookEntitiesForUser;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEJPABookEntity> findSpecificBookEntityForUser(
			GAEJPABookEntity bookEntity) throws EntityRetrievalException {
		List<GAEJPABookEntity> foundBooks = new ArrayList<>();
		Set<GAEJPATagEntity> tagEntities = bookEntity.getTags();

		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager.createQuery(BookDAOImplUtils
					.buildSpecificBookQuery(BOOK_SPECIFIC_USER_QUERY,
							bookEntity));
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
			if (this.currentUser != null) {
				q.setParameter("user", this.currentUser.getId());
			} else {
				q.setParameter("user", "");
			}
			foundBooks = q.getResultList();
		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Retrieval of specific BookEntity for user '"
							+ this.currentUser + "'failed.", e);
		} finally {
			closeEntityManager();
		}

		if (tagEntities != null && !tagEntities.isEmpty()) {
			Tags tags = new Tags();
			tags.setTags(BookServiceEntityMapper
					.mapTagEntityListToTags(tagEntities));
			return BookDAOImplUtils.retrieveBookEntitiesWithSpecificTagsOnly(
					foundBooks, tags);
		} else {
			return foundBooks;
		}
	}
}
