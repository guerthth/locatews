package amtc.gue.ws.shopping.persistence.model;

import amtc.gue.ws.base.persistence.model.PersistenceEntity;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

/**
 * Model for Objectify Shop stored to the datastore
 * 
 * @author thomas
 *
 */
public abstract class GAEShopEntity extends PersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Getter for the name of the ShopEntity
	 * 
	 * @return name of the ShopEntity
	 */
	public abstract String getShopName();

	/**
	 * Setter for the name of the ShopEntity
	 * 
	 * @param name
	 *            of the ShopEntity
	 */
	public abstract void setShopName(String shopName);

	@Override
	public String toString() {
		return ShoppingServiceEntityMapper.mapShopEntityToJSONString(this);
	}
}
