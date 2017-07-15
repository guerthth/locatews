package amtc.gue.ws.shopping.util.mapper;

import java.util.ArrayList;
import java.util.List;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.StatusMapper;
import amtc.gue.ws.shopping.inout.Shop;
import amtc.gue.ws.shopping.inout.Shops;
import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;
import amtc.gue.ws.shopping.response.ShopServiceResponse;

/**
 * Class responsible for mapping of ShoppingService related objects Use Case
 * examples: - building up ShoppingServiceResponse objects - mapping objects
 * from one type to another - creating JSON Strings for specific objects
 * 
 * @author Thomas
 *
 */
public abstract class ShoppingServiceEntityMapper {
	/**
	 * Method mapping Shop object to GAEShopEntity
	 * 
	 * @param shop
	 *            the shop element that should be transformed
	 * @param type
	 *            the database action type
	 * @return the mapped GAEShopEntity
	 */
	public abstract GAEShopEntity mapShopToEntity(Shop shop, DelegatorTypeEnum type);

	/**
	 * Method mapping Shops to a list of GAEShopEntities
	 * 
	 * @param shops
	 *            the Shops input object that should be mapped
	 * @param type
	 *            the database action type
	 * @return the mapped list of GAEShopEntities
	 */
	public List<GAEShopEntity> transformShopsToShopEntities(Shops shops, DelegatorTypeEnum type) {
		List<GAEShopEntity> shopEntityList = new ArrayList<>();
		if (shops != null) {
			for (Shop shop : shops.getShops()) {
				shopEntityList.add(mapShopToEntity(shop, type));
			}
		}
		return shopEntityList;
	}

	/**
	 * Method mapping a GAEShopEntity to a Shop object
	 * 
	 * @param shopEntity
	 *            the GAEShopEntity that should be mapped
	 * @return the Shop object
	 */
	public static Shop mapShopEntityToShop(GAEShopEntity shopEntity) {
		Shop shop = new Shop();
		if (shopEntity != null) {
			shop.setShopId(shopEntity.getKey());
			shop.setShopName(shopEntity.getShopName());
		}
		return shop;
	}

	/**
	 * Method mapping a list of GAEShopEntities to a Shops object
	 * 
	 * @param shopEntityList
	 *            a list of GAEShopEntities
	 * @return a Shops object
	 */
	public static Shops transformShopEntitiesToShops(List<GAEShopEntity> shopEntityList) {
		Shops shops = new Shops();
		List<Shop> shopList = new ArrayList<>();
		if (shopEntityList != null) {
			for (GAEShopEntity shopEntity : shopEntityList) {
				shopList.add(mapShopEntityToShop(shopEntity));
			}
		}
		shops.setShops(shopList);
		return shops;
	}

	/**
	 * Method mapping a GAEShopEntity to a JSON String
	 * 
	 * @param shopEntity
	 *            the GAEShopEntity that should be mapped
	 * @return the created GAEShopEntity JSON String
	 */
	public static String mapShopEntityToJSONString(GAEShopEntity shopEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (shopEntity != null) {
			sb.append("shopId: ");
			String id = shopEntity.getKey() != null ? shopEntity.getKey() + ", " : "null, ";
			sb.append(id);
			sb.append("shopName: ");
			String playerName = shopEntity.getShopName() != null ? shopEntity.getShopName() : "null";
			sb.append(playerName);
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Method mapping a list of ShopEntities to one consolidated JSON String
	 * 
	 * @param shopEntities
	 *            the list of ShopEntities that should be mapped to a JSON
	 *            String
	 * @return the consolidated JSON String
	 */
	public static String mapShopEntityListToConsolidatedJSONString(List<GAEShopEntity> shopEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (shopEntities != null && !shopEntities.isEmpty()) {
			int listSize = shopEntities.size();
			for (int i = 0; i < listSize; i++) {
				sb.append(mapShopEntityToJSONString(shopEntities.get(i)));
				if (i != listSize - 1) {
					sb.append(", ");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Method mapping a delegatoroutput to a ShopServiceResponse
	 * 
	 * @param bdOutput
	 *            delegatoroutput that should be included in the response
	 * @return mapped ShopServiceResponse
	 */
	public static ShopServiceResponse mapBdOutputToShopServiceResponse(IDelegatorOutput bdOutput) {
		ShopServiceResponse shopServiceResponse = null;

		if (bdOutput != null) {
			shopServiceResponse = new ShopServiceResponse();
			shopServiceResponse.setStatus(StatusMapper.buildStatusForDelegatorOutput(bdOutput));
			if (bdOutput.getOutputObject() instanceof Shops) {
				List<Shop> shopList = ((Shops) bdOutput.getOutputObject()).getShops();
				shopServiceResponse.setShops(shopList);
			} else {
				shopServiceResponse.setShops(null);
			}
		}
		return shopServiceResponse;
	}
}
