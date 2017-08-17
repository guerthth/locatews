package amtc.gue.ws.base.util.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.response.UserServiceResponse;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.StatusMapper;

/**
 * Class responsible for mapping of UserService related objects. Use Case
 * examples: - building up UserServiceResponse objects - mapping objects from
 * one type to another - creating JSON Strings for specific objects
 * 
 * @author Thomas
 *
 */
public abstract class UserServiceEntityMapper {
	/**
	 * Method mapping User object to GAEUserEntity
	 * 
	 * @param user
	 *            the user object that be transformed
	 * @param type
	 *            the database action type
	 * @return the mapped GAEUserEntity
	 */
	public abstract GAEUserEntity mapUserToEntity(User user, DelegatorTypeEnum type);

	/**
	 * Method mapping Users to a list of GAEUserEntities
	 * 
	 * @param users
	 *            the Users input object that should be mapped
	 * @param type
	 *            the database action type
	 * @return the mapped list of GAEUserEntities
	 */
	public List<GAEUserEntity> transformUsersToUserEntities(Users users, DelegatorTypeEnum type) {
		List<GAEUserEntity> userEntityList = new ArrayList<>();
		if (users != null) {
			for (User user : users.getUsers()) {
				userEntityList.add(mapUserToEntity(user, type));
			}
		}
		return userEntityList;
	}

	/**
	 * Method mapping a GAEUserEntity to a User object
	 * 
	 * @param userEntity
	 *            the GAEUserEntity that should be mapped
	 * @return the mapped User object
	 */
	public User mapUserEntityToUser(GAEUserEntity userEntity) {
		User user = new User();
		if (userEntity != null) {
			user.setId(userEntity.getKey());
			user.setUserName(userEntity.getUserName());
			user.setPassword(userEntity.getPassword());
			user.setRoles(mapRoleEntityListToRoles(userEntity.getRoles()));
		}
		return user;
	}

	/**
	 * Method mapping a list of GAEUserEntities to a Users object
	 * 
	 * @param userEntityList
	 *            a list of GAEUserEntities
	 * @return a Users object
	 */
	public Users transformUserEntitiesToUsers(List<GAEUserEntity> userEntityList) {
		Users users = new Users();
		List<User> userList = new ArrayList<>();
		if (userEntityList != null) {
			for (GAEUserEntity userEntity : userEntityList) {
				userList.add(mapUserEntityToUser(userEntity));
			}
		}
		users.setUsers(userList);
		return users;
	}

	/**
	 * Method mapping a list of String roles to a collection of GAERoleEntities
	 * 
	 * @param roles
	 *            a list of String roles
	 * @return a collection of GAERoleEntities
	 */
	public abstract Set<GAERoleEntity> mapRolesToRoleEntityList(List<String> roles);

	/**
	 * Method mapping a collection of GAERoleEntities to a list of String roles
	 * 
	 * @param roles
	 *            Collection of GAERoleEntities
	 * @return list of String roles
	 */
	public static List<String> mapRoleEntityListToRoles(Collection<GAERoleEntity> roleEntityCollection) {
		List<String> roleList = new ArrayList<String>();
		if (roleEntityCollection != null) {
			for (GAERoleEntity roleEntity : roleEntityCollection) {
				String role = (roleEntity != null) ? roleEntity.getKey() : null;
				roleList.add(role);
			}
		}
		return roleList;
	}

	/**
	 * Method mapping a GAEUserEntity to a JSON String
	 * 
	 * @param userEntity
	 *            the GAEUserEntity that should be mapped
	 * @return the created GAEUserEntity JSON String
	 */
	public static String mapUserEntityToJSONString(GAEUserEntity userEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (userEntity != null) {
			sb.append("id: ");
			String id = userEntity.getKey() != null ? userEntity.getKey() + ", " : "null, ";
			sb.append(id);
			sb.append("userName: ");
			String userName = userEntity.getUserName() != null ? userEntity.getUserName() + ", " : "null, ";
			sb.append(userName);
			sb.append("password: ");
			String password = userEntity.getPassword() != null ? userEntity.getPassword() + ", " : "null, ";
			sb.append(password);
			sb.append("userroles: ");
			sb.append("[");
			if (userEntity.getRoles() != null) {
				for (int i = 0; i < userEntity.getRoles().size(); i++) {
					GAERoleEntity roleEntity = (new ArrayList<GAERoleEntity>(userEntity.getRoles())).get(i);
					String role = roleEntity != null ? roleEntity.getKey() : "null";
					sb.append(role);
					if (i != userEntity.getRoles().size() - 1) {
						sb.append(", ");
					}
				}
			}
			sb.append("], ");
			sb.append("books: ");
			sb.append("[");
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Method mapping a list of UserEntities to one consolidated JSON String
	 * 
	 * @param userEntities
	 *            the list of UserEntities that should be mapped to a JSON
	 *            String
	 * @return the consolidated JSON String
	 */
	public static String mapUserEntityListToConsolidatedJSONString(List<GAEUserEntity> userEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (userEntities != null && !userEntities.isEmpty()) {
			int listSize = userEntities.size();
			for (int i = 0; i < listSize; i++) {
				sb.append(mapUserEntityToJSONString(userEntities.get(i)));
				if (i != listSize - 1) {
					sb.append(", ");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Method mapping a delegatorOutput to a UserResponse
	 * 
	 * @param dOutput
	 *            delegatoroOutput that should be included in the response
	 * @return mapped UserServiceReponse
	 */
	@SuppressWarnings("unchecked")
	public static UserServiceResponse mapBdOutputToUserServiceResponse(IDelegatorOutput dOutput) {
		UserServiceResponse userServiceResponse = null;
		if (dOutput != null) {
			userServiceResponse = new UserServiceResponse();
			userServiceResponse.setStatus(StatusMapper.buildStatusForDelegatorOutput(dOutput));
			if (dOutput.getOutputObject() instanceof List<?>) {
				List<GAEUserEntity> users = (List<GAEUserEntity>) dOutput.getOutputObject();
				userServiceResponse.setUsers(users);
			} else {
				userServiceResponse.setUsers(null);
			}
		}
		return userServiceResponse;
	}

	/**
	 * Method mapping a RoleEntity to a JSON String
	 * 
	 * @param roleEntity
	 *            the RoleEntity object that should be mapped
	 * @return the JSOn String
	 */
	public static String mapRoleEntityToJSONString(GAERoleEntity roleEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (roleEntity != null) {
			sb.append("role: ");
			String role = roleEntity.getKey() != null ? roleEntity.getKey() + ", " : "null, ";
			sb.append(role);
			sb.append("description: ");
			String description = roleEntity.getDescription() != null ? roleEntity.getDescription() + ", " : "null, ";
			sb.append(description);
			sb.append("users: ").append("[");
			// users don't need to be represented here
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}
}
