package amtc.gue.ws.shopping.persistence.model.objectify;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;

/**
 * Model for Objectify Shops stored to the datastore
 * 
 * @author thomas
 *
 */
@Entity
public class GAEObjectifyShopEntity extends GAEShopEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long shopId;
	@Index
	private String shopName;

	@Override
	public String getKey() {
		if (shopId != null) {
			return String.valueOf(shopId);
		} else {
			return null;
		}
	}

	@Override
	public void setKey(String shopId) {
		if (shopId != null) {
			this.shopId = Long.valueOf(shopId);
		} else {
			this.shopId = null;
		}
	}

	@Override
	public String getShopName() {
		return shopName;
	}

	@Override
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	@Override
	public String getWebsafeKey() {
		return Key.create(GAEObjectifyShopEntity.class, shopId).getString();
	}
}
