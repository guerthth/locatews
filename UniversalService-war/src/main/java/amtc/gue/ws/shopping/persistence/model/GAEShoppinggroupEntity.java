package amtc.gue.ws.shopping.persistence.model;

import java.util.Set;

import amtc.gue.ws.base.persistence.model.PersistenceEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;

/**
 * Shoppinggroup Persistence Entity for GAE datastore
 * 
 * @author thomas
 *
 */
public abstract class GAEShoppinggroupEntity extends PersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// TODO implement

	/**
	 * Getter for the UserEntities of the BookEntity
	 * 
	 * @return UserEntities of the ShoppinggroupEntity
	 */
	public abstract Set<GAEUserEntity> getUsers();

	/**
	 * Setter for the UserEntities of the ShoppinggroupEntity
	 * 
	 * @param users
	 *            UserEntities of the ShoppinggroupEntity
	 * @param alsoSetShoppinggroups
	 *            flag depicting if the respective Shoppinggroups should be set
	 *            in the UserEntities
	 */
	public abstract void setUsers(Set<GAEUserEntity> users, boolean alsoSetShoppinggroups);

	/**
	 * Method only adding a UserEntity to the ShoppinggroupEntity
	 * 
	 * @param user
	 *            the UserEntity that should be added to the ShoppinggroupEntity
	 */
	public abstract void addToUsersOnly(GAEUserEntity user);

	/**
	 * Method adding a UserEntitiy to the ShoppinggroupEntity. The
	 * ShoppinggroupEntity is also added to the UserEntity
	 * 
	 * @param user
	 *            the UserEntity that should be added to the ShoppinggroupEntity
	 */
	public abstract void addToUsersAndShoppinggroups(GAEUserEntity user);

	/**
	 * Method removing a UserEntity from the ShoppinggroupEntity
	 * 
	 * @param user
	 *            the UserEntity that should be removed from the ShoppinggroupEntity
	 */
	public abstract void removeUser(GAEUserEntity user);

	@Override
	public String toString() {
		// TODO implement
		return null;
	}
}
