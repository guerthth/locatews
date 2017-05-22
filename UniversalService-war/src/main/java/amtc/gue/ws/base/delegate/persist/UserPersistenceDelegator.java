package amtc.gue.ws.base.delegate.persist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.input.IDelegatorInput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.persistence.dao.role.RoleDAO;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.UserPersistenceDelegatorUtils;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;

/**
 * Persistence Delegator that handles all database actions for User resources
 * 
 * @author Thomas
 *
 */
public class UserPersistenceDelegator extends AbstractPersistenceDelegator {

	private static final Logger log = Logger.getLogger(UserPersistenceDelegator.class.getName());

	/** DAOImplementations used by the delegator */
	private RoleDAO<GAERoleEntity, GAERoleEntity, String> roleDAOImpl;

	@Override
	public void initialize(IDelegatorInput input) {
		super.initialize(input);
	}

	@Override
	protected void persistEntities() {
		log.info("ADD User action triggered");
		if (delegatorInput.getInputObject() instanceof Users) {
			Users users = (Users) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ErrorConstants.ADD_USER_SUCCESS_CODE);
			List<GAEUserEntity> successfullyAddedUserEntities = new ArrayList<>();
			List<GAEUserEntity> unsuccessfullyAddedUserEntities = new ArrayList<>();
			GAEUserEntity persistedUserEntity;

			// persist all UserEntities to the DB
			for (User user : users.getUsers()) {
				Set<GAERoleEntity> roleEntityList = userEntityMapper.mapRolesToRoleEntityList(user.getRoles());
				GAEUserEntity userEntity = userEntityMapper.mapUserToEntity(user, DelegatorTypeEnum.ADD);
				String userEntityJSON = UserServiceEntityMapper.mapUserEntityToJSONString(userEntity);
				try {
					handleRolePersistenceForUserEntity(userEntity, roleEntityList);
					persistedUserEntity = userDAOImpl.persistEntity(userEntity);
					userEntityJSON = UserServiceEntityMapper.mapUserEntityToJSONString(persistedUserEntity);
					successfullyAddedUserEntities.add(persistedUserEntity);
					log.info(userEntityJSON + " added to DB");
				} catch (Exception e) {
					userEntityJSON = UserServiceEntityMapper.mapUserEntityToJSONString(userEntity);
					unsuccessfullyAddedUserEntities.add(userEntity);
					log.log(Level.SEVERE, "Error while trying to persist: " + userEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyAddedUserEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(UserPersistenceDelegatorUtils.buildPersistUserSuccessStatusMessage(
						successfullyAddedUserEntities, unsuccessfullyAddedUserEntities));
				delegatorOutput
						.setOutputObject(userEntityMapper.transformUserEntitiesToUsers(successfullyAddedUserEntities));
			} else {
				delegatorOutput.setStatusCode(ErrorConstants.ADD_USER_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ErrorConstants.ADD_USER_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}

	}

	@Override
	protected void removeEntities() {
		log.info("DELETE User action triggered");

		if (delegatorInput.getInputObject() instanceof Users) {
			List<GAEUserEntity> removedUserEntities = new ArrayList<>();
			Users usersToRemove = (Users) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ErrorConstants.DELETE_USER_SUCCESS_CODE);

			// transform input object to userentities and remove
			List<GAEUserEntity> userEntities = userEntityMapper.transformUsersToUserEntities(usersToRemove,
					DelegatorTypeEnum.DELETE);
			for (GAEUserEntity userEntity : userEntities) {
				List<GAEUserEntity> userEntitiesToRemove;
				String userEntityJSON = UserServiceEntityMapper.mapUserEntityToJSONString(userEntity);
				try {
					if (userEntity.getKey() != null) {
						userEntitiesToRemove = new ArrayList<>();
						GAEUserEntity foundUser = userDAOImpl.findEntityById(userEntity.getKey());
						if (foundUser != null) {
							userEntitiesToRemove.add(foundUser);
						}
					} else {
						userEntitiesToRemove = userDAOImpl.findSpecificEntity(userEntity);
					}

					if (userEntitiesToRemove != null && !userEntitiesToRemove.isEmpty()) {
						for (GAEUserEntity userEntityToRemove : userEntitiesToRemove) {
							String userEntityToRemoveJSON = UserServiceEntityMapper
									.mapUserEntityToJSONString(userEntityToRemove);
							try {
								GAEUserEntity removedUserEntity = userDAOImpl.removeEntity(userEntityToRemove);
								log.info("UserEntity " + userEntityToRemoveJSON + " was successfully removed");
								removedUserEntity.setRoles(userEntityToRemove.getRoles(), false);
								removedUserEntities.add(removedUserEntity);
							} catch (EntityRemovalException e) {
								log.log(Level.SEVERE, "Error while trying to remove: " + userEntityToRemoveJSON, e);
							}
						}
					} else {
						log.warning(userEntityJSON + " was not found.");
					}

				} catch (EntityRetrievalException e) {
					log.log(Level.SEVERE, "Error while trying to retrieve: " + userEntityJSON, e);
				}
			}

			// set delegator output
			if (!removedUserEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(
						UserPersistenceDelegatorUtils.buildRemoveUsersSuccessStatusMessage(removedUserEntities));
				delegatorOutput.setOutputObject(userEntityMapper.transformUserEntitiesToUsers(removedUserEntities));
			} else {
				delegatorOutput.setStatusCode(ErrorConstants.DELETE_USER_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ErrorConstants.DELETE_USER_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	@Override
	protected void retrieveEntities() {
		if (delegatorInput.getInputObject() instanceof Roles) {
			retrieveUsersByRoles((Roles) delegatorInput.getInputObject());
		} else if (delegatorInput.getInputObject() instanceof String) {
			retrieveUserByUsername((String) delegatorInput.getInputObject());
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	/**
	 * Method retrieving all users from the database possess at least one
	 * element of a specified role set
	 * 
	 * @param roles
	 *            the set of roles.
	 */
	private void retrieveUsersByRoles(Roles roles) {
		log.info("READ User by Roles action triggered");
		delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_USER_SUCCESS_CODE);
		List<GAEUserEntity> foundUsers = new ArrayList<>();
		try {
			foundUsers = userDAOImpl.getUserEntitiesByRoles(roles);
			String statusMessage = UserPersistenceDelegatorUtils.buildGetUsersByRoleSuccessStatusMessage(roles,
					foundUsers);
			log.info(statusMessage);
			delegatorOutput.setStatusMessage(statusMessage);
			delegatorOutput.setOutputObject(userEntityMapper.transformUserEntitiesToUsers(foundUsers));
		} catch (EntityRetrievalException e) {
			log.log(Level.SEVERE,
					"Error while trying to retrieve users with role: '" + roles.getRoles().toString() + "'", e);
			delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_USER_FAILURE_CODE);
			delegatorOutput.setStatusMessage(ErrorConstants.RETRIEVE_USER_FAILURE_MSG);
			delegatorOutput.setOutputObject(userEntityMapper.transformUserEntitiesToUsers(foundUsers));
		}
	}

	/**
	 * Method retrieving a userEntity from the database by userName
	 * 
	 * @param userName
	 *            the userName that is searched for
	 */
	private void retrieveUserByUsername(String userName) {
		log.info("READ User by userName action triggered");
		delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_USER_SUCCESS_CODE);
		GAEUserEntity foundUser = null;
		try {
			foundUser = userDAOImpl.findEntityById(userName);
			String statusMessage = UserPersistenceDelegatorUtils.buildGetUsersByIdSuccessStatusMessage(userName,
					foundUser);
			log.info(statusMessage);
			delegatorOutput.setStatusMessage(statusMessage);
			delegatorOutput.setOutputObject(userEntityMapper.mapUserEntityToUser(foundUser));
		} catch (EntityRetrievalException e) {
			log.log(Level.SEVERE, "Error while trying to retrieve users with userName: '" + userName + "'", e);
			delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_USER_FAILURE_CODE);
			delegatorOutput.setStatusMessage(ErrorConstants.RETRIEVE_USER_FAILURE_MSG);
			delegatorOutput.setOutputObject(userEntityMapper.mapUserEntityToUser(foundUser));
		}
	}

	/**
	 * Method that checks if the roles of the UserEntity already exists. If not,
	 * the role is added
	 * 
	 * @param userEntity
	 *            the userEntity whose roles are checked
	 * @param roleEntityList
	 *            the Set of roleentities
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve RoleEntity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist RoleEntity
	 */
	private void handleRolePersistenceForUserEntity(GAEUserEntity userEntity, Set<GAERoleEntity> roleEntityList)
			throws EntityRetrievalException, EntityPersistenceException {
		Set<GAERoleEntity> roleEntities = Collections
				.synchronizedSet(new HashSet<GAERoleEntity>(roleEntityList));
		String roleEntityJSON;
		GAERoleEntity persistedRoleEntity;
		userEntity.getRoles().clear();

		synchronized (roleEntities) {
			for (GAERoleEntity roleEntity : roleEntities) {
				roleEntityJSON = roleEntity.toString();
				try {
					roleEntity.getUsers().clear();
					List<GAERoleEntity> foundRoleEntities = roleDAOImpl.findSpecificEntity(roleEntity);
					if (foundRoleEntities.isEmpty()) {
						persistedRoleEntity = roleDAOImpl.persistEntity(roleEntity);
					} else {
						persistedRoleEntity = foundRoleEntities.get(0);
						String foundKey = persistedRoleEntity.getKey();
						persistedRoleEntity = roleDAOImpl.findEntityById(foundKey);
					}
					userEntity.addToRolesAndUsers(persistedRoleEntity);
				} catch (EntityRetrievalException e) {
					log.log(Level.SEVERE, "Error while trying to retrieve roleEntity: " + roleEntityJSON, e);
					throw new EntityRetrievalException(e.getMessage(), e);
				} catch (EntityPersistenceException e) {
					log.log(Level.SEVERE, "Error while trying to persist roleEntity: " + roleEntityJSON, e);
					throw new EntityPersistenceException(e.getMessage(), e);
				}
			}
		}
	}

	@Override
	protected void updateEntities() {
		// for users this method just updates the users password
		log.info("UPDATE User action triggered");
		if (delegatorInput.getInputObject() instanceof Users) {
			Users users = (Users) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ErrorConstants.UPDATE_USER_SUCCESS_CODE);
			List<GAEUserEntity> userEntityList = userEntityMapper.transformUsersToUserEntities(users,
					DelegatorTypeEnum.UPDATE);
			List<GAEUserEntity> successfullyUpdatedUserEntities = new ArrayList<>();
			List<GAEUserEntity> unsuccessfullyUpdatedUserEntities = new ArrayList<>();

			// reset password for the UserEntities
			for (GAEUserEntity userEntity : userEntityList) {
				String userEntityJSON;
				GAEUserEntity updatedUserEntitiy = null;
				GAEUserEntity foundUser = null;
				try {
					foundUser = userDAOImpl.findEntityById(userEntity.getKey());
					if (foundUser != null) {
						if (userEntity.getPassword() != null)
							foundUser.setPassword(userEntity.getPassword());
						updatedUserEntitiy = userDAOImpl.updateEntity(foundUser);
						userEntityJSON = UserServiceEntityMapper.mapUserEntityToJSONString(updatedUserEntitiy);
						successfullyUpdatedUserEntities.add(updatedUserEntitiy);
						log.log(Level.INFO, ErrorConstants.UPDATE_USER_SUCCESS_MSG + " '" + userEntityJSON + "'");
					} else {
						unsuccessfullyUpdatedUserEntities.add(userEntity);
						log.log(Level.SEVERE, ErrorConstants.RETRIEVE_USER_FAILURE_MSG);
					}
				} catch (Exception e) {
					userEntityJSON = UserServiceEntityMapper.mapUserEntityToJSONString(updatedUserEntitiy);
					unsuccessfullyUpdatedUserEntities.add(updatedUserEntitiy);
					log.log(Level.SEVERE, "Error while trying to update password for: " + userEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyUpdatedUserEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(UserPersistenceDelegatorUtils.buildResetPwSuccessStatusMessage(
						successfullyUpdatedUserEntities, unsuccessfullyUpdatedUserEntities));
				delegatorOutput.setOutputObject(
						userEntityMapper.transformUserEntitiesToUsers(successfullyUpdatedUserEntities));
			} else {
				delegatorOutput.setStatusCode(ErrorConstants.UPDATE_USER_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ErrorConstants.UPDATE_USER_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	/**
	 * Setter for the used roleDAOImpl
	 * 
	 * @param roleDAOImpl
	 *            the RoleJPADAOImpl object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setRoleDAO(RoleDAO roleDAOImpl) {
		this.roleDAOImpl = roleDAOImpl;
	}
}
