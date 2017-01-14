package amtc.gue.ws.tournament.persistence.dao.player.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.dao.DAOImpl;
import amtc.gue.ws.tournament.persistence.dao.player.PlayerDAO;
import amtc.gue.ws.tournament.persistence.model.GAEJPAPlayerEntity;
import amtc.gue.ws.tournament.util.dao.PlayerDAOImplUtils;

/**
 * Implementation for the PlayerDAO
 * 
 * @author Thomas
 *
 */
public class PlayerDAOImpl extends DAOImpl<GAEJPAPlayerEntity, String>
		implements PlayerDAO {

	/** Select specific tag query */
	private final String BASIC_PLAYER_SPECIFIC_QUERY = "select p from "
			+ this.entityClass.getSimpleName() + " p";

	/**
	 * Constructor initializing entitymanagerfactory
	 * 
	 * @param emfInstance
	 *            the EMF instance used for EntityManagerFactory initialization
	 */
	public PlayerDAOImpl(EMF emfInstance) {
		if (emfInstance != null) {
			this.entityManagerFactory = emfInstance.getEntityManagerFactory();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GAEJPAPlayerEntity> findSpecificEntity(
			GAEJPAPlayerEntity playerEntity) throws EntityRetrievalException {
		List<GAEJPAPlayerEntity> foundPlayers = new ArrayList<>();
		try {
			entityManager = entityManagerFactory.createEntityManager();
			Query q = entityManager.createQuery(PlayerDAOImplUtils
					.buildSpecificPlayerQuery(BASIC_PLAYER_SPECIFIC_QUERY,
							playerEntity));
			if (playerEntity.getKey() != null)
				q.setParameter("id", playerEntity.getKey());
			if (playerEntity.getPlayerName() != null)
				q.setParameter("playerName", playerEntity.getPlayerName());
			foundPlayers = q.getResultList();
		} catch (Exception e) {
			throw new EntityRetrievalException(
					"Retrieval of specific PlayerEntity failed.", e);
		} finally {
			closeEntityManager();
		}
		return foundPlayers;
	}

	@Override
	public GAEJPAPlayerEntity updateEntity(GAEJPAPlayerEntity playerEntity)
			throws EntityPersistenceException {
		GAEJPAPlayerEntity updatedPlayerEntity;
		try {
			entityManager = entityManagerFactory.createEntityManager();
			entityManager.getTransaction().begin();
			updatedPlayerEntity = entityManager.find(GAEJPAPlayerEntity.class,
					playerEntity.getKey());
			updatedPlayerEntity.setPlayerName(playerEntity.getPlayerName());
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			if (entityManager != null) {
				entityManager.getTransaction().rollback();
			}
			throw new EntityPersistenceException(
					"Updating PlayerEntity for playerName "
							+ playerEntity.getPlayerName() + " and ID "
							+ playerEntity.getKey() + " failed", e);
		} finally {
			closeEntityManager();
		}
		return updatedPlayerEntity;
	}

}
