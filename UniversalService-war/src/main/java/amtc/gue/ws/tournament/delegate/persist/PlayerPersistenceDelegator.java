package amtc.gue.ws.tournament.delegate.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.input.IDelegatorInput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.tournament.inout.Players;
import amtc.gue.ws.tournament.persistence.dao.player.PlayerDAO;
import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;
import amtc.gue.ws.tournament.util.PlayerPersistenceDelegatorUtils;
import amtc.gue.ws.tournament.util.TournamentServiceErrorConstants;
import amtc.gue.ws.tournament.util.mapper.TournamentServiceEntityMapper;

/**
 * Persistence Delegator that handles all database actions for Player resources
 * 
 * @author Thomas
 *
 */
public class PlayerPersistenceDelegator extends AbstractPersistenceDelegator {

	private static final Logger log = Logger.getLogger(PlayerPersistenceDelegator.class.getName());

	/** DAOImplementations used by the delegator */
	private PlayerDAO<GAEPlayerEntity, GAEPlayerEntity, String> playerDAOImpl;

	/** EntityMapper user by the delegator */
	private TournamentServiceEntityMapper tournamentEntityMapper;

	@Override
	public void initialize(IDelegatorInput input) {
		super.initialize(input);
	}

	@Override
	protected void persistEntities() {
		log.info("ADD Player action triggered");
		if (delegatorInput.getInputObject() instanceof Players) {
			Players players = (Players) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(TournamentServiceErrorConstants.ADD_PLAYER_SUCCESS_CODE);
			List<GAEPlayerEntity> playerEntityList = tournamentEntityMapper.transformPlayersToPlayerEntities(players,
					DelegatorTypeEnum.ADD);
			List<GAEPlayerEntity> successfullyAddedPlayerEntities = new ArrayList<>();
			List<GAEPlayerEntity> unsuccessfullyAddedPlayerEntities = new ArrayList<>();

			// persist all PlayerEntities to the DB
			for (GAEPlayerEntity playerEntity : playerEntityList) {
				String playerEntityJSON;
				GAEPlayerEntity persistedPlayerEntity;
				try {
					persistedPlayerEntity = playerDAOImpl.persistEntity(playerEntity);
					playerEntityJSON = TournamentServiceEntityMapper.mapPlayerEntityToJSONString(persistedPlayerEntity);
					successfullyAddedPlayerEntities.add(persistedPlayerEntity);
					log.info(playerEntityJSON + " added to DB");
				} catch (EntityPersistenceException e) {
					playerEntityJSON = TournamentServiceEntityMapper.mapPlayerEntityToJSONString(playerEntity);
					unsuccessfullyAddedPlayerEntities.add(playerEntity);
					log.log(Level.SEVERE, "Error while trying to persist: " + playerEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyAddedPlayerEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(PlayerPersistenceDelegatorUtils.buildPersistPlayerSuccessStatusMessage(
						successfullyAddedPlayerEntities, successfullyAddedPlayerEntities));
				delegatorOutput.setOutputObject(TournamentServiceEntityMapper
						.transformPlayerEntitiesToPlayers(successfullyAddedPlayerEntities));
			} else {
				delegatorOutput.setStatusCode(TournamentServiceErrorConstants.ADD_PLAYER_FAILURE_CODE);
				delegatorOutput.setStatusMessage(TournamentServiceErrorConstants.ADD_PLAYER_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	@Override
	protected void removeEntities() {
		log.info("DELETE Player action triggered");

		if (delegatorInput.getInputObject() instanceof Players) {
			List<GAEPlayerEntity> removedPlayerEntities = new ArrayList<>();
			Players playersToRemove = (Players) delegatorInput.getInputObject();

			delegatorOutput.setStatusCode(TournamentServiceErrorConstants.DELETE_PLAYER_SUCCESS_CODE);
			// transform input object to playerentities and remove
			List<GAEPlayerEntity> playerEntities = tournamentEntityMapper
					.transformPlayersToPlayerEntities(playersToRemove, DelegatorTypeEnum.DELETE);
			for (GAEPlayerEntity playerEntity : playerEntities) {
				List<GAEPlayerEntity> playerEntitiesToRemove;
				String playerEntityJSON = TournamentServiceEntityMapper.mapPlayerEntityToJSONString(playerEntity);
				try {
					if (playerEntity.getKey() != null) {
						playerEntitiesToRemove = new ArrayList<>();
						playerEntitiesToRemove.add(playerDAOImpl.findEntityById(playerEntity.getKey()));
					} else {
						playerEntitiesToRemove = playerDAOImpl.findSpecificEntity(playerEntity);
					}

					if (playerEntitiesToRemove != null && !playerEntitiesToRemove.isEmpty()) {
						for (GAEPlayerEntity playerEntityToRemove : playerEntitiesToRemove) {
							String playerEntityToRemoveJSON = TournamentServiceEntityMapper
									.mapPlayerEntityToJSONString(playerEntityToRemove);
							try {
								GAEPlayerEntity removedPlayerEntity = playerDAOImpl.removeEntity(playerEntityToRemove);
								log.info("PlayerEntity " + playerEntityToRemoveJSON + " was successfully removed");
								removedPlayerEntities.add(removedPlayerEntity);
							} catch (EntityRemovalException e) {
								log.log(Level.SEVERE, "Error while trying to remove: " + playerEntityToRemoveJSON, e);
							}
						}
					} else {
						log.warning(playerEntityJSON + " was not found.");
					}

				} catch (EntityRetrievalException e) {
					log.log(Level.SEVERE, "Error while trying to retrieve: " + playerEntityJSON, e);
				}
			}

			// set delegator output
			if (!removedPlayerEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(
						PlayerPersistenceDelegatorUtils.buildRemovePlayersSuccessStatusMessage(removedPlayerEntities));
				delegatorOutput.setOutputObject(
						TournamentServiceEntityMapper.transformPlayerEntitiesToPlayers(removedPlayerEntities));
			} else {
				delegatorOutput.setStatusCode(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_CODE);
				delegatorOutput.setStatusMessage(TournamentServiceErrorConstants.DELETE_PLAYER_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	@Override
	protected void retrieveEntities() {
		log.info("READ Player action triggered");
		delegatorOutput.setStatusCode(TournamentServiceErrorConstants.RETRIEVE_PLAYER_SUCCESS_CODE);

		try {
			List<GAEPlayerEntity> foundPlayers = playerDAOImpl.findAllEntities();
			delegatorOutput.setStatusMessage(
					PlayerPersistenceDelegatorUtils.buildRetrievePlayersSuccessStatusMessage(foundPlayers));
			delegatorOutput
					.setOutputObject(TournamentServiceEntityMapper.transformPlayerEntitiesToPlayers(foundPlayers));
		} catch (EntityRetrievalException e) {
			delegatorOutput.setStatusCode(TournamentServiceErrorConstants.RETRIEVE_PLAYER_FAILURE_CODE);
			delegatorOutput.setStatusMessage(TournamentServiceErrorConstants.RETRIEVE_PLAYER_FAILURE_MSG);
			delegatorOutput.setOutputObject(null);
			log.log(Level.SEVERE, "Error while trying to retrieve players", e);
		}
	}

	@Override
	protected void updateEntities() {
		// not implemented
		setUnrecognizedDelegatorOutput();
	}

	/**
	 * Setter for the used playerDAOImpl
	 * 
	 * @param playerDAOImpl
	 *            the PlayerDAOImpl object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setPlayerDAO(PlayerDAO playerDAOImpl) {
		this.playerDAOImpl = playerDAOImpl;
	}

	/**
	 * Setter for the entityMapper
	 * 
	 * @param entityMapper
	 *            the TournamentEntityMapper instance used by the delegator
	 */
	public void setTournamentEntityMapper(TournamentServiceEntityMapper entityMapper) {
		tournamentEntityMapper = entityMapper;
	}
}
