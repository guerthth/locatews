package amtc.gue.ws.books.persistence.dao.tag.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.dao.DAOImpl;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.util.dao.TagDAOImplUtils;

/**
 * Tag DAO Implementation Includes methods specifically for tag entities
 * 
 * @author Thomas
 *
 */
public class TagDAOImpl extends DAOImpl<GAEJPATagEntity, String> implements
		TagDAO {

	/** Select specific tag query */
	private final String BASIC_TAG_SPECIFIC_QUERY = "select t from "
			+ this.entityClass.getSimpleName() + " t";

	/**
	 * Constructor initializing entitymanagerfactory
	 * 
	 * @param emfInstance
	 *            the EMF instance used for EntityManagerFactory initialization
	 */
	public TagDAOImpl(EMF emfInstance) {
		if (emfInstance != null) {
			this.entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEJPATagEntity> findSpecificEntity(GAEJPATagEntity tagEntity)
			throws EntityRetrievalException {
		List<GAEJPATagEntity> foundTags = new ArrayList<>();
		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager.createQuery(TagDAOImplUtils
					.buildSpecificTagQuery(BASIC_TAG_SPECIFIC_QUERY, tagEntity));
			if (tagEntity.getKey() != null)
				q.setParameter("id", tagEntity.getKey());
			if (tagEntity.getTagName() != null)
				q.setParameter("tagname", tagEntity.getTagName());
			foundTags = q.getResultList();
		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Retrieval of specific TagEntity failed.", e);
		} finally {
			closeEntityManager();
		}

		return foundTags;
	}

	@Override
	public GAEJPATagEntity updateEntity(GAEJPATagEntity tagEntity)
			throws EntityPersistenceException {
		GAEJPATagEntity updatedTagEntity;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			updatedTagEntity = entityManager.find(GAEJPATagEntity.class,
					tagEntity.getKey());
			updatedTagEntity.setTagName(tagEntity.getTagName());
			updatedTagEntity.setBooks(tagEntity.getBooks());
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager != null) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityPersistenceException(
					"Updating TagEntity for tagName " + tagEntity.getTagName()
							+ " and ID " + tagEntity.getKey() + " failed", e);
		} finally {
			closeEntityManager();
		}

		return updatedTagEntity;
	}

}
