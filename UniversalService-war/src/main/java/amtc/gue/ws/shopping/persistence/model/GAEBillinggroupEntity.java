package amtc.gue.ws.shopping.persistence.model;

import java.util.Set;

import amtc.gue.ws.base.persistence.model.PersistenceEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

/**
 * Billinggroup Persistence Entity for GAE datastore
 * 
 * @author thomas
 *
 */
public abstract class GAEBillinggroupEntity extends PersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Getter for the Description of the BillinggroupEntity
	 * 
	 * @return Description of the BillinggroupEntity
	 */
	public abstract String getDescription();

	/**
	 * Setter for the Description of the BillinggroupEntity
	 * 
	 * @param description
	 *            Description of the BillinggroupEntity
	 */
	public abstract void setDescription(String description);

	/**
	 * Getter for the UserEntities of the BillinggroupEntity
	 * 
	 * @return UserEntities of the BillinggroupEntity
	 */
	public abstract Set<GAEUserEntity> getUsers();

	/**
	 * Setter for the UserEntities of the BillinggroupEntity
	 * 
	 * @param users
	 *            UserEntities of the BillinggroupEntity
	 * @param alsoSetBillinggroups
	 *            flag depicting if the respective Billinggroups should be set
	 *            in the UserEntities
	 */
	public abstract void setUsers(Set<GAEUserEntity> users, boolean alsoSetBillinggroups);

	/**
	 * Method only adding a UserEntity to the BillinggroupEntity
	 * 
	 * @param user
	 *            the UserEntity that should be added to the BillinggroupEntity
	 */
	public abstract void addToUsersOnly(GAEUserEntity user);

	/**
	 * Method adding a UserEntitiy to the BillinggroupEntity. The
	 * BillinggroupEntity is also added to the UserEntity
	 * 
	 * @param user
	 *            the UserEntity that should be added to the BillinggroupEntity
	 */
	public abstract void addToUsersAndBillinggroups(GAEUserEntity user);

	/**
	 * Method removing a UserEntity from the BillinggroupEntity
	 * 
	 * @param user
	 *            the UserEntity that should be removed from the
	 *            BillinggroupEntity
	 */
	public abstract void removeUser(GAEUserEntity user);

	/**
	 * Getter for the BillEntities of the BillinggroupEntity
	 * 
	 * @return BillEntities of the BillinggroupEntity
	 */
	public abstract Set<GAEBillEntity> getBills();

	/**
	 * Setter for the BillEntities of the BillinggroupEntity
	 * 
	 * @param bills
	 *            BillEntities of the BillinggroupEntity
	 * @param alsoSetBillinggroups
	 *            flag depicting if the respective Billinggroups should be set
	 *            in the BillEntities
	 */
	public abstract void setBills(Set<GAEBillEntity> bills, boolean alsoSetBillinggroups);

	/**
	 * Method only adding a BillEntity to the BillinggroupEntity
	 * 
	 * @param bill
	 *            the BillEntity that should be added to the BillinggroupEntity
	 */
	public abstract void addToBillsOnly(GAEBillEntity bill);

	/**
	 * Method adding a BillEntitiy to the BillinggroupEntity. The
	 * BillinggroupEntity is also added to the BillEntity
	 * 
	 * @param bill
	 *            the BillEntity that should be added to the BillinggroupEntity
	 */
	public abstract void addToBillsAndBillinggroups(GAEBillEntity bill);

	/**
	 * Method removing a BillEntity from the BillinggroupEntity
	 * 
	 * @param bill
	 *            the BillEntity that should be removed from the
	 *            BillinggroupEntity
	 */
	public abstract void removeBill(GAEBillEntity bill);

	@Override
	public String toString() {
		return ShoppingServiceEntityMapper.mapBillinggroupEntityToJSONString(this);
	}
}
