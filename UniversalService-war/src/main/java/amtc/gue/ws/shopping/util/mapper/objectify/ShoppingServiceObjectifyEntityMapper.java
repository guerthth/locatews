package amtc.gue.ws.shopping.util.mapper.objectify;

import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.shopping.inout.Shop;
import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyShopEntity;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

/**
 * Class responsible for of Objectify Entities relevant for the ShoppingService
 * 
 * @author Thomas
 *
 */
public class ShoppingServiceObjectifyEntityMapper extends ShoppingServiceEntityMapper {

	@Override
	public GAEShopEntity mapShopToEntity(Shop shop, DelegatorTypeEnum type) {
		GAEShopEntity shopEntity = new GAEObjectifyShopEntity();
		if (shop.getShopId() != null) {
			shopEntity.setKey(shop.getShopId());
		}
		if (shop.getShopName() != null) {
			shopEntity.setShopName(shop.getShopName());
		}
		return shopEntity;
	}
}
