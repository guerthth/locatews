package amtc.gue.ws.base.delegate.persist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.IDelegatorInput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.persistence.dao.role.RoleDAO;
import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.model.GAEJPARoleEntity;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.base.util.PersistenceTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.base.util.UserPersistenceDelegatorUtils;
import amtc.gue.ws.base.util.UserServiceEntityMapper;

/**
 * Persistence Delegator that handles al database actions for User resources
 * 
 * @author Thomas
 *
 */
public class UserPersistenceDelegator extends AbstractPersistenceDelegator {

	private static final Logger log = Logger
			.getLogger(UserPersistenceDelegator.class.getName());

	/** DAOImplementations used by the delegator */
	private UserDAO userDAOImpl;
	private RoleDAO roleDAOImpl;

	@Override
	public void initialize(IDelegatorInput input) {
		super.initialize(input);
		userDAOImpl = (UserDAO) SpringContext.context.getBean("userDAOImpl");
		roleDAOImpl = (RoleDAO) SpringContext.context.getBean("roleDAOImpl");
	}

	@Override
	protected void persistEntities() {
		log.info("ADD User action triggered");
		if (persistenceInput.getInputObject() instanceof Users) {
			Users users = (Users) persistenceInput.getInputObject();
			delegatorOutput.setStatusCode(ErrorConstants.ADD_USER_SUCCESS_CODE);
			List<GAEJPAUserEntity> userEntityList = UserServiceEntityMapper
					.transformUsersToUserEntities(users,
							PersistenceTypeEnum.ADD);
			List<GAEJPAUserEntity> successfullyAddedUserEntities = new ArrayList<>();
			List<GAEJPAUserEntity> unsuccessfullyAddedUserEntities = new ArrayList<>();

			// persist all UserEntities to the DB
			for (GAEJPAUserEntity userEntity : userEntityList) {
				String userEntityJSON;
				GAEJPAUserEntity persistedUserEntity;
				try {
					handleRolePersistenceForUserEntity(userEntity);
					persistedUserEntity = userDAOImpl.persistEntity(userEntity);
					userEntityJSON = UserServiceEntityMapper
							.mapUserEntityToJSONString(persistedUserEntity);
					successfullyAddedUserEntities.add(persistedUserEntity);
					log.info(userEntityJSON + " added to DB");
				} catch (Exception e) {
					userEntityJSON = UserServiceEntityMapper
							.mapUserEntityToJSONString(userEntity);
					unsuccessfullyAddedUserEntities.add(userEntity);
					log.log(Level.SEVERE, "Error while trying to persist: "
							+ userEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyAddedUserEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(UserPersistenceDelegatorUtils
						.buildPersistUserSuccessStatusMessage(
								successfullyAddedUserEntities,
								unsuccessfullyAddedUserEntities));
				delegatorOutput
						.setOutputObject(UserServiceEntityMapper
								.transformUserEntitiesToUsers(successfullyAddedUserEntities));
			} else {
				delegatorOutput
						.setStatusCode(ErrorConstants.ADD_USER_FAILURE_CODE);
				delegatorOutput
						.setStatusMessage(ErrorConstants.ADD_USER_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedInputDelegatorOutput();
		}

	}

	@Override
	protected void removeEntities() {
		log.info("DELETE User action triggered");

		if (persistenceInput.getInputObject() instanceof Users) {
			List<GAEJPAUserEntity> removedUserEntities = new ArrayList<>();
			Users usersToRemove = (Users) persistenceInput.getInputObject();
			delegatorOutput
					.setStatusCode(ErrorConstants.DELETE_USER_SUCCESS_CODE);

			// transform input object to userentities and remove
			List<GAEJPAUserEntity> userEntities = UserServiceEntityMapper
					.transformUsersToUserEntities(usersToRemove,
							PersistenceTypeEnum.DELETE);
			for (GAEJPAUserEntity userEntity : userEntities) {
				List<GAEJPAUserEntity> userEntitiesToRemove;
				String userEntityJSON = UserServiceEntityMapper
						.mapUserEntityToJSONString(userEntity);
				try {
					if (userEntity.getKey() != null) {
						userEntitiesToRemove = new ArrayList<>();
						GAEJPAUserEntity foundUser = userDAOImpl
								.findEntityById(userEntity.getKey());
						if (foundUser != null) {
							userEntitiesToRemove.add(foundUser);
						}
					} else {
						userEntitiesToRemove = userDAOImpl
								.findSpecificEntity(userEntity);
					}

					if (userEntitiesToRemove != null
							&& !userEntitiesToRemove.isEmpty()) {
						for (GAEJPAUserEntity userEntityToRemove : userEntitiesToRemove) {
							String userEntityToRemoveJSON = UserServiceEntityMapper
									.mapUserEntityToJSONString(userEntityToRemove);
							try {
								GAEJPAUserEntity removedUserEntity = userDAOImpl
										.removeEntity(userEntityToRemove);
								log.info("UserEntity " + userEntityToRemoveJSON
										+ " was successfully removed");
								removedUserEntity.setRoles(
										userEntityToRemove.getRoles(), false);
								removedUserEntities.add(removedUserEntity);
							} catch (EntityRemovalException e) {
								log.log(Level.SEVERE,
										"Error while trying to remove: "
												+ userEntityToRemoveJSON, e);
							}
						}
					} else {
						log.warning(userEntityJSON + " was not found.");
					}

				} catch (EntityRetrievalException e) {
					log.log(Level.SEVERE, "Error while trying to retrieve: "
							+ userEntityJSON, e);
				}
			}

			// set delegator output
			if (!removedUserEntities.isEmpty()) {
				delegatorOutput
						.setStatusMessage(UserPersistenceDelegatorUtils
								.buildRemoveUsersSuccessStatusMessage(removedUserEntities));
				delegatorOutput.setOutputObject(UserServiceEntityMapper
						.transformUserEntitiesToUsers(removedUserEntities));
			} else {
				delegatorOutput
						.setStatusCode(ErrorConstants.DELETE_USER_FAILURE_CODE);
				delegatorOutput
						.setStatusMessage(ErrorConstants.DELETE_USER_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedInputDelegatorOutput();
		}
	}

	@Override
	protected void retrieveEntities() {
		log.info("READ User by role action triggered");

		if (persistenceInput.getInputObject() instanceof Roles) {
			Roles roles = (Roles) persistenceInput.getInputObject();
			delegatorOutput
					.setStatusCode(ErrorConstants.RETRIEVE_USER_SUCCESS_CODE);
			List<GAEJPAUserEntity> foundUsers = new ArrayList<>();
			try {
				foundUsers = userDAOImpl.getUserEntitiesByRoles(roles);
				String statusMessage = UserPersistenceDelegatorUtils
						.buildGetUsersByRoleSuccessStatusMessage(roles,
								foundUsers);
				log.info(statusMessage);

				// set delegator output
				delegatorOutput.setStatusMessage(statusMessage);
				delegatorOutput.setOutputObject(UserServiceEntityMapper
						.transformUserEntitiesToUsers(foundUsers));
			} catch (EntityRetrievalException e) {
				delegatorOutput
						.setStatusCode(ErrorConstants.RETRIEVE_USER_FAILURE_CODE);
				delegatorOutput
						.setStatusMessage(ErrorConstants.RETRIEVE_USER_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
				log.log(Level.SEVERE,
						"Error while trying to retrieve users with role: '"
								+ roles.getRoles().toString() + "'", e);
			}
		} else {
			setUnrecognizedInputDelegatorOutput();
		}
	}

	/**
	 * Method that checks if the roles of the UserEntity already exists. If not,
	 * the role is added
	 * 
	 * @param userEntity
	 *            the userEntity whose roles are checked
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve RoleEntity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist RoleEntity
	 */
	private void handleRolePersistenceForUserEntity(GAEJPAUserEntity userEntity)
			throws EntityRetrievalException, EntityPersistenceException {
		Set<GAEJPARoleEntity> roleEntities = Collections
				.synchronizedSet(new HashSet<GAEJPARoleEntity>(userEntity
						.getRoles()));
		String roleEntityJSON;
		GAEJPARoleEntity persistedRoleEntity;
		userEntity.getRoles().clear();

		synchronized (roleEntities) {
			for (GAEJPARoleEntity roleEntity : roleEntities) {
				roleEntityJSON = UserServiceEntityMapper
						.mapRoleEntityToJSONString(roleEntity);
				try {
					roleEntity.getUsers().clear();
					List<GAEJPARoleEntity> foundRoleEntities = roleDAOImpl
							.findSpecificEntity(roleEntity);
					if (foundRoleEntities.isEmpty()) {
						persistedRoleEntity = roleDAOImpl
								.persistEntity(roleEntity);
					} else {
						persistedRoleEntity = foundRoleEntities.get(0);
						String foundKey = persistedRoleEntity.getKey();
						persistedRoleEntity = roleDAOImpl
								.findEntityById(foundKey);
					}
					userEntity.addToRolesAndUsers(persistedRoleEntity);
				} catch (EntityRetrievalException e) {
					log.log(Level.SEVERE,
							"Error while trying to retrieve roleEntity: "
									+ roleEntityJSON, e);
					throw new EntityRetrievalException(e.getMessage(), e);
				} catch (EntityPersistenceException e) {
					log.log(Level.SEVERE,
							"Error while trying to persist roleEntity: "
									+ roleEntityJSON, e);
					throw new EntityPersistenceException(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * Setter for the used userDAOImpl
	 * 
	 * @param userDAOImpl
	 *            the UserDAOImpl object
	 */
	public void setUserDAO(UserDAO userDAOImpl) {
		this.userDAOImpl = userDAOImpl;
	}

	/**
	 * Setter for the used roleDAOImpl
	 * 
	 * @param roleDAOImpl
	 *            the RoleDAOImpl object
	 */
	public void setRoleDAO(RoleDAO roleDAOImpl) {
		this.roleDAOImpl = roleDAOImpl;
	}

}
