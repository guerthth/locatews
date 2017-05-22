package amtc.gue.ws.books.persistence.dao.tag.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.dao.JPADAOImpl;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.model.tag.GAETagEntity;
import amtc.gue.ws.books.persistence.model.tag.jpa.GAEJPATagEntity;
import amtc.gue.ws.books.util.dao.TagDAOImplUtils;

/**
 * Tag DAO Implementation Includes methods specifically for tag entities
 * 
 * @author Thomas
 *
 */
public class TagJPADAOImpl extends JPADAOImpl<GAETagEntity, GAEJPATagEntity, String>
		implements TagDAO<GAETagEntity, GAEJPATagEntity, String> {

	/** Select specific tag query */
	private final String BASIC_TAG_SPECIFIC_QUERY = "select t from " + this.entityClass.getSimpleName() + " t";

	/**
	 * Constructor initializing entitymanagerfactory
	 * 
	 * @param emfInstance
	 *            the EMF instance used for EntityManagerFactory initialization
	 */
	public TagJPADAOImpl(EMF emfInstance) {
		if (emfInstance != null) {
			this.entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAETagEntity> findSpecificEntity(GAETagEntity tagEntity) throws EntityRetrievalException {
		List<GAETagEntity> foundTags = new ArrayList<>();
		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager
					.createQuery(TagDAOImplUtils.buildSpecificTagQuery(BASIC_TAG_SPECIFIC_QUERY, tagEntity));
			if (tagEntity.getKey() != null)
				q.setParameter("id", tagEntity.getKey());
			foundTags = q.getResultList();
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieval of specific TagEntity failed.", e);
		} finally {
			closeEntityManager();
		}

		return foundTags;
	}

	@Override
	public GAETagEntity updateEntity(GAETagEntity tagEntity) throws EntityPersistenceException {
		GAETagEntity updatedTagEntity;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			updatedTagEntity = entityManager.find(GAEJPATagEntity.class, tagEntity.getKey());
			updatedTagEntity.setBooks(tagEntity.getBooks(), true);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager != null) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityPersistenceException("Updating TagEntity for tagName " + tagEntity.getKey() + " failed", e);
		} finally {
			closeEntityManager();
		}

		return updatedTagEntity;
	}

}
