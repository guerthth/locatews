package amtc.gue.ws.shopping.persistence.model.objectify;

import java.util.Set;

import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.shopping.persistence.model.GAEShoppinggroupEntity;

/**
 * Model for Objectify Shoppinggroups stored to the datastore
 * 
 * @author thomas
 *
 */
public class GAEObjectifyShoppinggroupEntity extends GAEShoppinggroupEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKey(String key) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<GAEUserEntity> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUsers(Set<GAEUserEntity> users, boolean alsoSetShoppinggroups) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToUsersOnly(GAEUserEntity user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addToUsersAndShoppinggroups(GAEUserEntity user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeUser(GAEUserEntity user) {
		// TODO Auto-generated method stub
		
	}
}
