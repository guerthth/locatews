package amtc.gue.ws.books.persistence.dao.tag.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.EMF;
import amtc.gue.ws.books.persistence.dao.DAOImpl;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.model.TagEntity;
import amtc.gue.ws.books.utils.dao.TagDAOImplUtils;

public class TagDAOImpl extends DAOImpl<TagEntity, Long> implements TagDAO {

	/** Select specific tag query */
	private final String BASIC_TAG_SPECIFIC_QUERY = "select t from "
			+ this.entityClass.getSimpleName() + " t";

	public TagDAOImpl(EMF emfInstance) {
		if (emfInstance != null) {
			this.entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TagEntity> findSpecificEntity(TagEntity tagEntity)
			throws EntityRetrievalException {
		List<TagEntity> foundTags = new ArrayList<TagEntity>();
		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager
					.createQuery(TagDAOImplUtils.buildSpecificTagQuery(
							BASIC_TAG_SPECIFIC_QUERY, tagEntity));
			if (tagEntity.getId() != null)
				q.setParameter("id", tagEntity.getId());
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
	public TagEntity updateEntity(TagEntity tagEntity)
			throws EntityPersistenceException {
		TagEntity updatedTagEntity;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			updatedTagEntity = entityManager.find(TagEntity.class,
					tagEntity.getId());
			updatedTagEntity.setTagName(tagEntity.getTagName());
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new EntityPersistenceException(
					"Updating TagEntity for tagName " + tagEntity.getTagName()
							+ " and ID " + tagEntity.getId() + " failed", e);
		} finally {
			closeEntityManager();
		}

		return updatedTagEntity;
	}

}
