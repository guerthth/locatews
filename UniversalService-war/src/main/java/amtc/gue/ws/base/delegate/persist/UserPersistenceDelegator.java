package amtc.gue.ws.base.delegate.persist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.InternetAddress;

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
			StringBuilder sb = new StringBuilder();

			// persist all UserEntities to the DB
			for (User user : users.getUsers()) {
				Set<GAERoleEntity> roleEntityList = userEntityMapper.mapRolesToRoleEntityList(user.getRoles());
				GAEUserEntity userEntity = userEntityMapper.mapUserToEntity(user, DelegatorTypeEnum.ADD);
				String userEntityJSON = UserServiceEntityMapper.mapUserEntityToJSONString(userEntity);
				try {
					// check if userId is an email format
					if (user.getId() != null) {
						new InternetAddress(user.getId()).validate();
					}
					handleRolePersistenceForUserEntity(userEntity, roleEntityList);
					persistedUserEntity = userDAOImpl.persistEntity(userEntity);
					userEntityJSON = UserServiceEntityMapper.mapUserEntityToJSONString(persistedUserEntity);
					successfullyAddedUserEntities.add(persistedUserEntity);
					log.info(userEntityJSON + " added to DB");
				} catch (Exception e) {
					userEntityJSON = UserServiceEntityMapper.mapUserEntityToJSONString(userEntity);
					unsuccessfullyAddedUserEntities.add(userEntity);
					sb.append(e.getMessage());
					log.log(Level.SEVERE, "Error while trying to persist: " + userEntityJSON, e);
				}
			}

			String statusReason = sb.toString();

			// set delegatorOutput
			if (!successfullyAddedUserEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(UserPersistenceDelegatorUtils.buildPersistUserSuccessStatusMessage(
						successfullyAddedUserEntities, unsuccessfullyAddedUserEntities));
				delegatorOutput.setStatusReason(statusReason);
				delegatorOutput
						.setOutputObject(userEntityMapper.transformUserEntitiesToUsers(successfullyAddedUserEntities));
			} else {
				delegatorOutput.setStatusCode(ErrorConstants.ADD_USER_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ErrorConstants.ADD_USER_FAILURE_MSG);
				delegatorOutput.setStatusReason(statusReason);
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
		} else if (delegatorInput.getInputObject() instanceof User) {
			retrieveUserByUserMail((User) delegatorInput.getInputObject());
		} else if (delegatorInput.getInputObject() instanceof String) {
			retrieveUserByUserKey((String) delegatorInput.getInputObject());
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
		try {
			List<GAEUserEntity> foundUserEntities = userDAOImpl.getUserEntitiesByRoles(roles);
			String statusMessage = UserPersistenceDelegatorUtils.buildGetUsersByRoleSuccessStatusMessage(roles,
					foundUserEntities);
			log.info(statusMessage);
			delegatorOutput.setStatusMessage(statusMessage);
			delegatorOutput.setOutputObject(userEntityMapper.transformUserEntitiesToUsers(foundUserEntities));
		} catch (EntityRetrievalException e) {
			log.log(Level.SEVERE,
					"Error while trying to retrieve users with role: '" + roles.getRoles().toString() + "'", e);
			delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_USER_FAILURE_CODE);
			delegatorOutput.setStatusMessage(ErrorConstants.RETRIEVE_USER_FAILURE_MSG);
			delegatorOutput.setStatusMessage(e.getMessage());
			delegatorOutput.setOutputObject(null);
		}
	}

	/**
	 * Method retrieving an userEntity from the database using User object and
	 * the email address that is associated with the User
	 * 
	 * @param inputObject
	 *            the user object that is searched for
	 */
	private void retrieveUserByUserMail(User user) {
		log.info("READ User by User email triggered");
		delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_USER_SUCCESS_CODE);
		try {
			GAEUserEntity searchUser = userEntityMapper.mapUserToEntity(user, DelegatorTypeEnum.READ);
			List<GAEUserEntity> foundUserEntities = userDAOImpl.findSpecificEntity(searchUser);
			if (foundUserEntities != null && foundUserEntities.size() == 1) {			
				delegatorOutput.setStatusMessage(
						ErrorConstants.RETRIEVE_USER_BY_EMAIL_SUCCESS_MSG + " '" + foundUserEntities.get(0).getUserName() + "'");
				delegatorOutput.setOutputObject(userEntityMapper.transformUserEntitiesToUsers(foundUserEntities));
			} else {
				throw new EntityRetrievalException();
			}
		} catch (EntityRetrievalException e) {
			log.log(Level.SEVERE, "Error while trying to retrieve users with userMail: '" + user.getId() + "'", e);
			delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_USER_FAILURE_CODE);
			delegatorOutput.setStatusMessage(ErrorConstants.RETRIEVE_USER_FAILURE_MSG);
			delegatorOutput.setStatusReason(e.getMessage());
			delegatorOutput.setOutputObject(null);
		}
	}

	/**
	 * Method retrieving an userEntity from the database by userName
	 * 
	 * @param userName
	 *            the userName that is searched for
	 */
	private void retrieveUserByUserKey(String userKey) {
		log.info("READ User by userName action triggered");
		delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_USER_SUCCESS_CODE);
		GAEUserEntity foundUserEntity = null;
		List<GAEUserEntity> foundUserEntities = new ArrayList<>();
		try {
			foundUserEntity = userDAOImpl.findEntityById(userKey);
			foundUserEntities.add(foundUserEntity);
			String statusMessage = UserPersistenceDelegatorUtils.buildGetUsersByIdSuccessStatusMessage(userKey,
					foundUserEntity);
			log.info(statusMessage);
			delegatorOutput.setStatusMessage(statusMessage);
			delegatorOutput.setOutputObject(userEntityMapper.transformUserEntitiesToUsers(foundUserEntities));
		} catch (EntityRetrievalException e) {
			log.log(Level.SEVERE, "Error while trying to retrieve users with userKey: '" + userKey + "'", e);
			delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_USER_FAILURE_CODE);
			delegatorOutput.setStatusMessage(ErrorConstants.RETRIEVE_USER_FAILURE_MSG);
			delegatorOutput.setStatusReason(e.getMessage());
			delegatorOutput.setOutputObject(null);
		}
	}

	/**
	 * Method that checks if the roles of the UserEntity already exists. If not,
	 * the role is added
	 * 
	 * @param userEntity
	 *            the userEntity whose roles are checked
	 * @param roleEntityList
	 *            the Set of roleEntities
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve RoleEntity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist RoleEntity
	 */
	private void handleRolePersistenceForUserEntity(GAEUserEntity userEntity, Set<GAERoleEntity> roleEntityList)
			throws EntityRetrievalException, EntityPersistenceException {
		Set<GAERoleEntity> roleEntities = Collections.synchronizedSet(new HashSet<GAERoleEntity>(roleEntityList));
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
		if (delegatorInput.getInputObject() instanceof GAEUserEntity) {
			GAEUserEntity userEntityToUpdate = (GAEUserEntity) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ErrorConstants.UPDATE_USER_SUCCESS_CODE);
			List<GAEUserEntity> successfullyUpdatedUserEntities = new ArrayList<>();
			List<GAEUserEntity> unsuccessfullyUpdatedUserEntities = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			String userEntityJSON = UserServiceEntityMapper.mapUserEntityToJSONString(userEntityToUpdate);
			try {
				GAEUserEntity updatedUserEntity = userDAOImpl.updateEntity(userEntityToUpdate);
				successfullyUpdatedUserEntities.add(updatedUserEntity);
				log.info(userEntityJSON + " added to DB");
			} catch (Exception e) {
				unsuccessfullyUpdatedUserEntities.add(userEntityToUpdate);
				sb.append(e.getMessage());
				log.log(Level.SEVERE, "Error while trying to update: " + userEntityJSON, e);
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
