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

	/** BookEntity Selection Query for tag selection */
	private final String BOOKENTITY_SELECTION_QUERY = "select be from BookEntity be";

	public BookDAOImpl(EMF emfInstance) {
		if (emfInstance != null) {
			this.entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BookEntity> getBookEntityByTag(Tags tags)
			throws EntityRetrievalException {

		List<BookEntity> books = new ArrayList<BookEntity>();

		try {
			books = new ArrayList<BookEntity>();

			// set entitymanager
			entityManager = entityManagerFactory.createEntityManager();

			// read bookentites from DB that possess specific tags
			Query q = entityManager.createQuery(BOOKENTITY_SELECTION_QUERY,
					BookEntity.class);
			books = q.getResultList();

		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Retrieval of BookEntity for tags: "
							+ tags.getTags().toString() + " failed.", e);
		} finally {
			closeEntityManager();
		}

		return BookDAOImplUtils.retrieveBookEntitiesWithSpecificTags(books, tags);
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
