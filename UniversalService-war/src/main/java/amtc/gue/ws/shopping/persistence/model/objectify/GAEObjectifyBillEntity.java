package amtc.gue.ws.shopping.persistence.model.objectify;

import java.util.Date;

import com.google.api.server.spi.config.AnnotationBoolean;
import com.google.api.server.spi.config.ApiResourceProperty;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;

/**
 * Model for Objectify Bills stored to the datastore
 * 
 * @author thomas
 *
 */
@Entity
public class GAEObjectifyBillEntity extends GAEBillEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private Long billId;
	@Index
	private Date date;
	@Index
	private Double amount;
	@Parent
	@ApiResourceProperty(ignored = AnnotationBoolean.TRUE)
	private Ref<GAEObjectifyUserEntity> user;
	@Index
	private Ref<GAEObjectifyShopEntity> shop;
	@Index
	private Ref<GAEObjectifyBillinggroupEntity> billinggroup;

	// TODO bill form?

	public GAEObjectifyBillEntity() {
	}

	public GAEObjectifyBillEntity(final String userEmail, final Long shopId, final Long billinggroupId) {
		if (userEmail != null)
			user = Ref.create(Key.create(GAEObjectifyUserEntity.class, userEmail));
		if (shopId != null)
			shop = Ref.create(Key.create(GAEObjectifyShopEntity.class, shopId));
		if (billinggroupId != null)
			billinggroup = Ref.create(Key.create(GAEObjectifyBillinggroupEntity.class, billinggroupId));
	}

	@Override
	public String getKey() {
		if (billId != null) {
			return String.valueOf(billId);
		} else {
			return null;
		}
	}

	@Override
	public void setKey(String billId) {
		if (billId != null) {
			this.billId = Long.valueOf(billId);
		} else {
			this.billId = null;
		}
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public Double getAmount() {
		return amount;
	}

	@Override
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Override
	public GAEUserEntity getUser() {
		GAEUserEntity userEntity = null;
		if (user != null) {
			userEntity = new GAEObjectifyUserEntity();
			userEntity = user.get();
		}
		return userEntity;
	}

	@Override
	public GAEShopEntity getShop() {
		GAEShopEntity shopEntity = null;
		if (shop != null) {
			shopEntity = new GAEObjectifyShopEntity();
			shopEntity = shop.get();
		}
		return shopEntity;
	}

	/**
	 * Method returning a websafe representation of the GAEObjectifyBillEntity
	 * 
	 * @return websafe representation of the GAEObjectifyBillEntity
	 */
	public String getWebSafeKey() {
		return Key.create(user.getKey(), GAEObjectifyBillEntity.class, billId).toString();
	}
}
