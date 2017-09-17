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
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
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

	public GAEObjectifyBillEntity() {
	}

	public GAEObjectifyBillEntity(final String websafeUserEmail, final String websafeShopId,
			final String websafeBillinggroupId) {
		if (websafeUserEmail != null) {
			Key<GAEObjectifyUserEntity> userKey = Key.create(websafeUserEmail);
			user = Ref.create(userKey);
		}
		if (websafeShopId != null) {
			Key<GAEObjectifyShopEntity> shopKey = Key.create(websafeShopId);
			shop = Ref.create(shopKey);
		}
		if (websafeBillinggroupId != null) {
			Key<GAEObjectifyBillinggroupEntity> billinggroupKey = Key.create(websafeBillinggroupId);
			billinggroup = Ref.create(billinggroupKey);
		}
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
	public void setUser(GAEUserEntity userEntity) {
		if (userEntity != null) {
			user = Ref.create(Key.create(GAEObjectifyUserEntity.class, userEntity.getKey()));
		}
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

	@Override
	public void setShop(GAEShopEntity shopEntity) {
		if (shopEntity.getKey() != null) {
			shop = Ref.create(Key.create(GAEObjectifyShopEntity.class, Long.valueOf(shopEntity.getKey()).longValue()));
		}
	}

	@Override
	public GAEBillinggroupEntity getBillinggroup() {
		GAEBillinggroupEntity billinggoupEntity = null;
		if (billinggroup != null) {
			billinggoupEntity = new GAEObjectifyBillinggroupEntity();
			billinggoupEntity = billinggroup.get();
		}
		return billinggoupEntity;
	}

	@Override
	public void setBillinggroup(GAEBillinggroupEntity billinggroupEntity) {
		if (billinggroupEntity != null) {
			billinggroup = Ref.create(Key.create(GAEObjectifyBillinggroupEntity.class,
					Long.valueOf(billinggroupEntity.getKey()).longValue()));
		}
	}

	@Override
	public String getWebsafeKey() {
		return Key.create(user.getKey(), GAEObjectifyBillEntity.class, billId).getString();
	}
}
