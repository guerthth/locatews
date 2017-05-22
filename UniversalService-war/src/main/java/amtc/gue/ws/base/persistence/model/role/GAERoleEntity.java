package amtc.gue.ws.base.persistence.model.role;

import java.util.Set;

import amtc.gue.ws.base.persistence.model.PersistenceEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;

/**
 * Role Persistence Entity for GAE datastore
 * 
 * @author Thomas
 *
 */
public abstract class GAERoleEntity extends PersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Getter for the description of the RoleEntity
	 * 
	 * @return description of the RoleEntity
	 */
	public abstract String getDescription();

	/**
	 * Setter for the description of the RoleEntity
	 * 
	 * @param description
	 *            of the RoleEntity
	 */
	public abstract void setDescription(String description);

	/**
	 * Getter for the UserEntities of the RoleEntity
	 * 
	 * @return UserEntities of the RoleEntity
	 */
	public abstract Set<GAEUserEntity> getUsers();

	/**
	 * Setter for the UserEntities of the RoleEntity
	 * 
	 * @param users
	 *            UserEntities of the RoleEntity
	 * @param alsoSetRoles
	 *            flag depicting if the respective RoleEntities should be set in
	 *            the UserEntities
	 */
	public abstract void setUsers(Set<GAEUserEntity> users, boolean alsoSetRoles);

	/**
	 * Method only adding a UserEntity to the RoleEntity
	 * 
	 * @param user
	 *            the UserEntity that should be added to the RoleEntity
	 */
	public abstract void addToUsersOnly(GAEUserEntity user);

	/**
	 * Method adding a UserEntity to the RoleEntity. The RoleEntity is also
	 * added to the UserEntity
	 * 
	 * @param user
	 *            the UserEntity that should be added to the RoleEntity
	 */
	public abstract void addToUsersAndRoles(GAEUserEntity user);
	
	/**
	 * Method removing a UserEntity from the RoleEntity
	 * 
	 * @param user
	 *            the UserEntity that should be removed
	 */
	public abstract void removeUser(GAEUserEntity user);
	
	@Override
	public String toString() {
		return UserServiceEntityMapper.mapRoleEntityToJSONString(this);
	}
}
