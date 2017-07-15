package amtc.gue.ws.shopping.util;

import java.util.List;

import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

/**
 * Utility class for the ShopPersistenceDelegator
 * 
 * @author Thomas
 *
 */
public class ShopPersistenceDelegatorUtils {
	/**
	 * Method building the String status message for successful removal of Shop
	 * entities
	 * 
	 * @param successfullyAddedShopEntities
	 *            a list of successfully added ShopEntities
	 * @param unsuccessfullyAddedShopEntities
	 *            a list of unsuccessfully added ShopEntities
	 * @return success status message for shop entity removal
	 */
	public static String buildPersistShopSuccessStatusMessage(List<GAEShopEntity> successfullyAddedShopEntities,
			List<GAEShopEntity> unsuccessfullyAddedShopEntities) {
		int numberOfSuccessfullyAddedEntities = (successfullyAddedShopEntities != null)
				? successfullyAddedShopEntities.size() : 0;
		int numberOfUnsuccessfullyAddedEntities = (successfullyAddedShopEntities != null)
				? unsuccessfullyAddedShopEntities.size() : 0;
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.ADD_SHOP_SUCCESS_MSG);
		sb.append(" '");
		sb.append(ShoppingServiceEntityMapper.mapShopEntityListToConsolidatedJSONString(successfullyAddedShopEntities));
		sb.append("'. ").append(numberOfSuccessfullyAddedEntities).append(" shops were successfully added.");
		if (numberOfUnsuccessfullyAddedEntities > 0) {
			sb.append(System.getProperty("line.separator"));
			sb.append(ShoppingServiceErrorConstants.ADD_SHOP_FAILURE_MSG);
			sb.append(" '");
			sb.append(ShoppingServiceEntityMapper
					.mapShopEntityListToConsolidatedJSONString(unsuccessfullyAddedShopEntities));
			sb.append("'. ").append(numberOfUnsuccessfullyAddedEntities).append(" shops were not added successfully.");
		}
		return sb.toString();
	}

	/**
	 * Method building the String status message used in the response when
	 * removing ShopEntity from the datastore
	 * 
	 * @param removed
	 *            ShopEntities a list of removed ShopEntities
	 * @return the status message that can be used in the response as String
	 */
	public static String buildRemoveShopsSuccessStatusMessage(List<GAEShopEntity> removedShopEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.DELETE_SHOP_SUCCESS_MSG);
		sb.append(" '");
		sb.append(ShoppingServiceEntityMapper.mapShopEntityListToConsolidatedJSONString(removedShopEntities));
		sb.append("'. ");
		sb.append(removedShopEntities.size());
		sb.append(" Entities were removed.");
		return sb.toString();
	}

	/**
	 * Method building the String status message for successful retrieval of a
	 * specific ShopEntity from the datastore
	 * 
	 * @param foundShop
	 *            the shop that was found
	 * @return the created status message
	 */
	public static String buildGetShopsByIdSuccessStatusMessage(String shopId, GAEShopEntity foundShop) {
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.RETRIEVE_SHOP_BY_ID_SUCCESS_MSG);
		sb.append(" '");
		sb.append(shopId);
		sb.append("': '");
		sb.append(ShoppingServiceEntityMapper.mapShopEntityToJSONString(foundShop));
		sb.append("'.");
		return sb.toString();
	}
}
