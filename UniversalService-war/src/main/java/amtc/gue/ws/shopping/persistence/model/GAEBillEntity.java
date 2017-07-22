package amtc.gue.ws.shopping.persistence.model;

import java.util.Date;

import amtc.gue.ws.base.persistence.model.PersistenceEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

/**
 * Bill Persistence Entity for GAE datastore
 * 
 * @author thomas
 *
 */
public abstract class GAEBillEntity extends PersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Getter for the Date of the GAEBillEntity
	 * 
	 * @return the Date of the GAEBillEntity
	 */
	public abstract Date getDate();

	/**
	 * Setter for the Date of the GAEBillEntity
	 * 
	 * @param key
	 *            the Date of the GAEBillEntity
	 */
	public abstract void setDate(Date date);

	/**
	 * Getter for the Amount of the GAEBillEntity
	 * 
	 * @return the Amount of the GAEBillEntity
	 */
	public abstract Double getAmount();

	/**
	 * Setter for the Amount of the GAEBillEntity
	 * 
	 * @param key
	 *            the Amount of the GAEBillEntity
	 */
	public abstract void setAmount(Double amount);

	/**
	 * Getter for the User Entity
	 * 
	 * @return user the bill belongs to
	 */
	public abstract GAEUserEntity getUser();

	/**
	 * Setter for the User Entity
	 * 
	 * @param userEntity
	 *            user the bill belongs to
	 */
	public abstract void setUser(GAEUserEntity userEntity);

	/**
	 * Getter for the Shop Entity
	 * 
	 * @return shop that is associated with the bill
	 */
	public abstract GAEShopEntity getShop();

	/**
	 * Setter for the Shop Entity
	 * 
	 * @param shopEntity
	 *            shop that is associated with the bill
	 */
	public abstract void setShop(GAEShopEntity shopEntity);

	/**
	 * Getter for the Billinggroup Entity
	 * 
	 * @return billinggroup that is associated with the bill
	 */
	public abstract GAEBillinggroupEntity getBillinggroup();

	/**
	 * Setter for the Billinggroup Entity
	 * 
	 * @param billinggroupEntity
	 *            that is associated with the bill
	 */
	public abstract void setBillinggroup(GAEBillinggroupEntity billinggroupEntity);

	@Override
	public String toString() {
		return ShoppingServiceEntityMapper.mapBillEntityToJSONString(this);
	}
}
