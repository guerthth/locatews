package amtc.gue.ws.base.util.mapper.jpa;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.role.jpa.GAEJPARoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.jpa.GAEJPAUserEntity;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.EncryptionMapper;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;

/**
 * Class responsible for mapping of JPA Entities relevant for the UserService
 * 
 * @author Thomas
 *
 */
public class UserServiceJPAEntityMapper extends UserServiceEntityMapper {
	@Override
	public GAEUserEntity mapUserToEntity(User user, DelegatorTypeEnum type) {
		GAEUserEntity userEntity = new GAEJPAUserEntity();
		if (user.getId() != null)
			userEntity.setKey(user.getId());
		if (user.getUserName() != null) {
			userEntity.setUserName(user.getUserName());
		} else {
			userEntity.setUserName("");
		}
		userEntity.setPassword(EncryptionMapper.encryptStringMD5(user.getPassword()));
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
				GAEJPARoleEntity roleEntity = new GAEJPARoleEntity();
				roleEntity.setKey(role);
				roleEntityList.add(roleEntity);
			}
		}
		return roleEntityList;
	}

}
