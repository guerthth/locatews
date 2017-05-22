package amtc.gue.ws.base.util.mapper.objectify;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.role.objectify.GAEObjectifyRoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.EncryptionMapper;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;

/**
 * Class responsible for of Objectify Entities relevant for the UserService
 * 
 * @author Thomas
 *
 */
public class UserServiceObjectifyEntityMapper extends UserServiceEntityMapper {
	@Override
	public GAEUserEntity mapUserToEntity(User user, DelegatorTypeEnum type) {
		GAEUserEntity userEntity = new GAEObjectifyUserEntity();
		if (user.getId() != null)
			userEntity.setKey(user.getId());
		userEntity.setPassword(EncryptionMapper.encryptStringMD5(user.getPassword()));
		if (user.getEmail() != null) {
			userEntity.setEmail(user.getEmail());
		} else {
			userEntity.setEmail("");
		}
		if (type != DelegatorTypeEnum.ADD) {
			userEntity.setRoles(mapRolesToRoleEntityList(user.getRoles()), true);
		} else {
			userEntity.setRoles(mapRolesToRoleEntityList(user.getRoles()), true);
		}
		return userEntity;
	}

	@Override
	public Set<GAERoleEntity> mapRolesToRoleEntityList(List<String> roles) {
		Set<GAERoleEntity> roleEntityList = new HashSet<>();
		if (roles != null) {
			for (String role : roles) {
				GAEObjectifyRoleEntity roleEntity = new GAEObjectifyRoleEntity();
				roleEntity.setKey(role);
				roleEntityList.add(roleEntity);
			}
		}
		return roleEntityList;
	}
}
