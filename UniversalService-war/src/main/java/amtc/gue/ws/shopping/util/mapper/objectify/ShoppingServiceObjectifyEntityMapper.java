package amtc.gue.ws.shopping.util.mapper.objectify;

import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.shopping.inout.Bill;
import amtc.gue.ws.shopping.inout.Billinggroup;
import amtc.gue.ws.shopping.inout.Shop;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyShopEntity;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

/**
 * Class responsible for of Objectify Entities relevant for the ShopService
 * 
 * @author Thomas
 *
 */
public class ShoppingServiceObjectifyEntityMapper extends ShoppingServiceEntityMapper {

	@Override
	public GAEShopEntity mapShopToEntity(Shop shop, DelegatorTypeEnum type) {
		GAEShopEntity shopEntity = new GAEObjectifyShopEntity();
		if (shop.getShopId() != null && !type.equals(DelegatorTypeEnum.ADD)) {
			shopEntity.setKey(shop.getShopId());
		}
		if (shop.getShopName() != null) {
			shopEntity.setShopName(shop.getShopName());
		}
		return shopEntity;
	}

	@Override
	public GAEBillinggroupEntity mapBillinggroupToEntity(Billinggroup billinggroup, DelegatorTypeEnum type) {
		GAEBillinggroupEntity billinggroupEntity = new GAEObjectifyBillinggroupEntity();
		if (billinggroup.getBillinggroupId() != null && !type.equals(DelegatorTypeEnum.ADD)) {
			billinggroupEntity.setKey(billinggroup.getBillinggroupId());
		}
		if (billinggroup.getDescription() != null) {
			billinggroupEntity.setDescription(billinggroup.getDescription());
		}
		if (billinggroup.getUsers() != null && !billinggroup.getUsers().isEmpty()) {
			for (User user : billinggroup.getUsers()) {
				GAEObjectifyUserEntity userEntity = new GAEObjectifyUserEntity();
				userEntity.setKey(user.getId());
				billinggroupEntity.addToUsersOnly(userEntity);
			}
		}
		// TODO add shops here?
		return billinggroupEntity;
	}

	@Override
	public GAEBillEntity mapBillToEntity(Bill bill, DelegatorTypeEnum type) {
		GAEBillEntity billEntity = new GAEObjectifyBillEntity();
		if (bill.getBillId() != null && !type.equals(DelegatorTypeEnum.ADD)) {
			billEntity.setKey(bill.getBillId());
		}
		if (bill.getDate() != null) {
			billEntity.setDate(bill.getDate());
		}
		if (bill.getAmount() != null) {
			billEntity.setAmount(bill.getAmount());
		}
		if (bill.getShop() != null) {
			billEntity.setShop(mapShopToEntity(bill.getShop(), type));
		}
		if (bill.getBillinggroup() != null) {
			billEntity.setBillinggroup(mapBillinggroupToEntity(bill.getBillinggroup(), type));
		}
		return billEntity;
	}
}
