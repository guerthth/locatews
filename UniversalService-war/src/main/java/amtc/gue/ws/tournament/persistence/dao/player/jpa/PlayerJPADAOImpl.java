package amtc.gue.ws.tournament.persistence.dao.player.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.dao.JPADAOImpl;
import amtc.gue.ws.tournament.persistence.dao.player.PlayerDAO;
import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;
import amtc.gue.ws.tournament.persistence.model.player.jpa.GAEJPAPlayerEntity;
import amtc.gue.ws.tournament.util.dao.PlayerDAOImplUtils;

/**
 * Implementation for the JPA PlayerDAO
 * 
 * @author Thomas
 *
 */
public class PlayerJPADAOImpl extends JPADAOImpl<GAEPlayerEntity, GAEJPAPlayerEntity, String>
		implements PlayerDAO<GAEPlayerEntity, GAEJPAPlayerEntity, String> {

	/** Select specific tag query */
	private final String BASIC_PLAYER_SPECIFIC_QUERY = "select p from " + this.entityClass.getSimpleName() + " p";

	/**
	 * Constructor initializing entitymanagerfactory
	 * 
	 * @param emfInstance
	 *            the EMF instance used for EntityManagerFactory initialization
	 */
	public PlayerJPADAOImpl(EMF emfInstance) {
		if (emfInstance != null) {
			this.entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEPlayerEntity> findSpecificEntity(GAEPlayerEntity playerEntity) throws EntityRetrievalException {
		List<GAEPlayerEntity> foundPlayers = new ArrayList<>();
		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager.createQuery(
					PlayerDAOImplUtils.buildSpecificPlayerQuery(BASIC_PLAYER_SPECIFIC_QUERY, playerEntity));
			if (playerEntity.getKey() != null)
				q.setParameter("id", playerEntity.getKey());
			if (playerEntity.getDescription() != null)
				q.setParameter("description", playerEntity.getDescription());
			foundPlayers = q.getResultList();
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieval of specific PlayerEntity failed.", e);
		} finally {
			closeEntityManager();
		}
		return foundPlayers;
	}

	@Override
	public GAEJPAPlayerEntity updateEntity(GAEPlayerEntity playerEntity) throws EntityPersistenceException {
		GAEJPAPlayerEntity updatedPlayerEntity;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			updatedPlayerEntity = entityManager.find(GAEJPAPlayerEntity.class, playerEntity.getKey());
			updatedPlayerEntity.setDescription(playerEntity.getDescription());
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager != null) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityPersistenceException(
					"Updating PlayerEntity for playerName " + playerEntity.getKey() + " failed", e);
		} finally {
			closeEntityManager();
		}
		return updatedPlayerEntity;
	}

}
