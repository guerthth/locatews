package amtc.gue.ws.books.persistence.dao.book.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.EMF;
import amtc.gue.ws.books.persistence.dao.DAOImpl;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.dao.BookDAOImplUtils;

/**
 * Book DAO Implementation Includes methods specifically for bookentities
 * 
 * @author Thomas
 *
 */
public class BookDAOImpl extends DAOImpl<GAEJPABookEntity, String> implements
		BookDAO {

	/** Select queries */
	private final String BASIC_BOOK_SPECIFIC_QUERY = "select be from "
			+ this.entityClass.getSimpleName() + " be";

	/**
	 * Constructor initializing entitiymanagerfactory
	 * 
	 * @param emfInstance
	 *            the EMF instance used for EntityManagerFactory initialization
	 */
	public BookDAOImpl(EMF emfInstance) {
		if (emfInstance != null) {
			this.entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
	}

	@Override
	public GAEJPABookEntity persistEntity(GAEJPABookEntity bookEntity)
			throws EntityPersistenceException {
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			entityManager.persist(bookEntity);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager != null) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityPersistenceException("Persisting "
					+ entityClass.getName() + " failed. ", e);

		} finally {
			closeEntityManager();
		}
		return bookEntity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEJPABookEntity> findSpecificEntity(GAEJPABookEntity bookEntity)
			throws EntityRetrievalException {
		List<GAEJPABookEntity> foundBooks = new ArrayList<>();
		Set<GAEJPATagEntity> tagEntities = bookEntity.getTags();
		Tags tags = new Tags();
		tags.setTags(EntityMapper.mapTagEntityListToTags(tagEntities));
		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager.createQuery(BookDAOImplUtils
					.buildSpecificBookQuery(BASIC_BOOK_SPECIFIC_QUERY,
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

		return updatedBookEntity;
	}
}
