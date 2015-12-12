package amtc.gue.ws.books.persistence.dao.book.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.EMF;
import amtc.gue.ws.books.persistence.dao.DAOImpl;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.utils.BookDAOImplUtils;

/**
 * Book DAO Implementation Includes methods specifically for bookentities
 * 
 * @author Thomas
 *
 */
public class BookDAOImpl extends DAOImpl<BookEntity, Long> implements BookDAO {

	/** Select specific book query */
	private final String BASIC_BOOK_SPECIFIC_QUERY = "select be from "
			+ this.entityClass.getSimpleName() + " be";

	public BookDAOImpl(EMF emfInstance) {
		if (emfInstance != null) {
			this.entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookEntity> findSpecificEntity(BookEntity bookEntity)
			throws EntityRetrievalException {
		List<BookEntity> foundBooks = new ArrayList<BookEntity>();
		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager.createQuery(BookDAOImplUtils
					.buildSpecificBookQuery(BASIC_BOOK_SPECIFIC_QUERY,
							bookEntity));
			if (bookEntity.getId() != null) q.setParameter("id", bookEntity.getId());
			if (bookEntity.getTitle() != null) q.setParameter("title", bookEntity.getTitle());
			if (bookEntity.getAuthor() != null)	q.setParameter("author", bookEntity.getAuthor());
			if (bookEntity.getPrice() != null) q.setParameter("price", bookEntity.getPrice());
			if (bookEntity.getISBN() != null) q.setParameter("ISBN", bookEntity.getISBN());
			if (bookEntity.getTags() != null) q.setParameter("tags", bookEntity.getTags());
			if (bookEntity.getDescription() != null) q.setParameter("description", bookEntity.getDescription());
			foundBooks = q.getResultList();
		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Retrieval of specific BookEntity failed.", e);
		} finally {
			closeEntityManager();
		}

		return foundBooks;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookEntity> getBookEntityByTag(Tags tags)
			throws EntityRetrievalException {

		List<BookEntity> foundBooks = new ArrayList<BookEntity>();

		try {
			foundBooks = new ArrayList<BookEntity>();

			// set entitymanager
			entityManager = entityManagerFactory.createEntityManager();

			// read bookentites from DB that possess specific tags
			Query q = entityManager.createQuery(ENTITY_SELECTION_QUERY,
					BookEntity.class);
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
	public BookEntity updateEntity(BookEntity entity)
			throws EntityPersistenceException {

		BookEntity updatedBookEntity;

		try {
			// set entitymanager
			entityManager = entityManagerFactory.createEntityManager();

			// find existing bookentity and update
			entityManager.getTransaction().begin();

			updatedBookEntity = entityManager.find(BookEntity.class,
					entity.getId());
			updatedBookEntity.setAuthor(entity.getAuthor());
			updatedBookEntity.setDescription(entity.getDescription());
			updatedBookEntity.setISBN(entity.getISBN());
			updatedBookEntity.setPrice(entity.getPrice());
			updatedBookEntity.setTags(entity.getTags());
			updatedBookEntity.setTitle(entity.getTitle());

			entityManager.getTransaction().commit();
		} catch (Exception e) {
			// rollback
			entityManager.getTransaction().rollback();
			throw new EntityPersistenceException(
					"Updating BookEntity for ISBN " + entity.getISBN()
							+ " and ID " + entity.getId() + " failed", e);
		} finally {
			closeEntityManager();
		}

		// return the updated BookEntity
		return updatedBookEntity;
	}
}
