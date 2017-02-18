package amtc.gue.ws.base.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.persistence.model.GAEJPARoleEntity;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.base.response.UserServiceResponse;

/**
 * Class responsible for mapping of UserService related objects Use Case
 * examples: - building up UserServiceResponse objects - mapping objects from
 * one type to another - creating JSON Strings for specific objects
 * 
 * @author Thomas
 *
 */
public class UserServiceEntityMapper {
	/**
	 * Method mapping User object to GAEJPAUserEntity
	 * 
	 * @param user
	 *            the user element that be transformed
	 * @param type
	 *            the database action type
	 * @return the mapped GAEJPAUserEntity
	 */
	public static GAEJPAUserEntity mapUserToEntity(User user,
			DelegatorTypeEnum type) {
		GAEJPAUserEntity userEntity = new GAEJPAUserEntity();
		if (user.getId() != null)
			userEntity.setKey(user.getId());
		userEntity.setPassword(EncryptionMapper.encryptStringMD5(user
				.getPassword()));
		if (user.getEmail() != null) {
			userEntity.setEmail(user.getEmail());
		} else {
			userEntity.setEmail("");
		}
		if (type != DelegatorTypeEnum.ADD) {
			userEntity
					.setRoles(mapRolesToRoleEntityList(user.getRoles()), true);
		} else {
			userEntity.setRoles(mapRolesToRoleEntityList(user.getRoles()),
					false);
		}
		return userEntity;
	}

	/**
	 * Method mapping Users to a list of GAEJPAUserEntities
	 * 
	 * @param users
	 *            the Users input object that should be mapped
	 * @param type
	 *            the database action type
	 * @return the mapped list of GAEJPAUserEntities
	 */
	public static List<GAEJPAUserEntity> transformUsersToUserEntities(
			Users users, DelegatorTypeEnum type) {
		List<GAEJPAUserEntity> userEntityList = new ArrayList<GAEJPAUserEntity>();
		if (users != null) {
			for (User user : users.getUsers()) {
				userEntityList.add(mapUserToEntity(user, type));
			}
		}
		return userEntityList;
	}

	/**
	 * Method mapping a GAEJPAUserEntity to a User object
	 * 
	 * @param userEntity
	 *            the GAEJPAUserEntity that should be mapped
	 * @return the mapped User object
	 */
	public static User mapUserEntityToUser(GAEJPAUserEntity userEntity) {
		User user = new User();
		if (userEntity != null) {
			user.setId(userEntity.getKey());
			user.setPassword(userEntity.getPassword());
			user.setEmail(userEntity.getEmail());
			user.setRoles(mapRoleEntityListToRoles(userEntity.getRoles()));
		}
		return user;
	}

	/**
	 * Method mapping a list of GAEJPAUserEntities to a Users object
	 * 
	 * @param userEntityList
	 *            a list of GAEJPAUserEntities
	 * @return a Users object
	 */
	public static Users transformUserEntitiesToUsers(
			List<GAEJPAUserEntity> userEntityList) {
		Users users = new Users();
		List<User> userList = new ArrayList<>();
		if (userEntityList != null) {
			for (GAEJPAUserEntity userEntity : userEntityList) {
				userList.add(mapUserEntityToUser(userEntity));
			}
		}
		users.setUsers(userList);
		return users;
	}

	/**
	 * Method mapping a list of String roles to a collection of
	 * GAEJPARoleEntities
	 * 
	 * @param roles
	 *            a list of String roles
	 * @return a collection of GAEJPARoleEntities
	 */
	public static Set<GAEJPARoleEntity> mapRolesToRoleEntityList(
			List<String> roles) {
		Set<GAEJPARoleEntity> roleEntityList = new HashSet<GAEJPARoleEntity>();
		if (roles != null) {
			for (String role : roles) {
				GAEJPARoleEntity roleEntity = new GAEJPARoleEntity();
				roleEntity.setKey(role);
				roleEntityList.add(roleEntity);
			}
		}
		return roleEntityList;
	}

	/**
	 * Method mapping a collection of RoleEntities to Roles
	 * 
	 * @param roles
	 * @return
	 */
	public static List<String> mapRoleEntityListToRoles(
			Collection<GAEJPARoleEntity> roleEntityCollection) {
		List<String> roleList = new ArrayList<String>();
		if (roleEntityCollection != null) {
			for (GAEJPARoleEntity roleEntity : roleEntityCollection) {
				String role = (roleEntity != null) ? roleEntity.getKey() : null;
				roleList.add(role);
			}
		}
		return roleList;
	}

	/**
	 * Method mapping a GAEJPAUserEntity to a JSON String
	 * 
	 * @param userEntity
	 *            the GAEJPAUserEntity that should be mapped
	 * @return the created GAEJPAUserEntity JSON String
	 */
	public static String mapUserEntityToJSONString(GAEJPAUserEntity userEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (userEntity != null) {
			sb.append("id: ");
			String id = userEntity.getKey() != null ? userEntity.getKey()
					+ ", " : "null, ";
			sb.append(id);
			sb.append("password: ");
			String password = userEntity.getPassword() != null ? userEntity
					.getPassword() + ", " : "null, ";
			sb.append(password);
			sb.append("email: ");
			String email = userEntity.getEmail() != null ? userEntity
					.getEmail() + ", " : "null, ";
			sb.append(email);
			sb.append("userroles: ");
			sb.append("[");
			if (userEntity.getRoles() != null) {
				for (int i = 0; i < userEntity.getRoles().size(); i++) {
					GAEJPARoleEntity roleEntity = (new ArrayList<GAEJPARoleEntity>(
							userEntity.getRoles())).get(i);
					String role = roleEntity != null ? roleEntity.getKey()
							: "null";
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
	 * Method mapping a list of UserEntities to one consolicated JSON String
	 * 
	 * @param userEntities
	 *            the list of UserEntities that should be mapped to a JSON
	 *            String
	 * @return the consolidated JSON String
	 */
	public static String mapUserEntityListToConsolidatedJSONString(
			List<GAEJPAUserEntity> userEntities) {
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
	public static UserServiceResponse mapBdOutputToUserServiceResponse(
			IDelegatorOutput dOutput) {
		UserServiceResponse userServiceResponse = null;
		if (dOutput != null) {
			userServiceResponse = new UserServiceResponse();
			userServiceResponse.setStatus(StatusMapper
					.buildStatusForDelegatorOutput(dOutput));
			if (dOutput.getOutputObject() instanceof Users) {
				List<User> userList = ((Users) dOutput.getOutputObject())
						.getUsers();
				userServiceResponse.setUsers(userList);
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
	public static String mapRoleEntityToJSONString(GAEJPARoleEntity roleEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (roleEntity != null) {
			sb.append("role: ");
			String role = roleEntity.getKey() != null ? roleEntity.getKey()
					+ ", " : "null, ";
			sb.append(role);
			sb.append("description: ");
			String description = roleEntity.getDescription() != null ? roleEntity
					.getDescription() + ", "
					: "null, ";
			sb.append(description);
			sb.append("users: ").append("[");
			// users don't need to be represented here
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}
}
