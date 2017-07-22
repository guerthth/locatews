package amtc.gue.ws.shopping.util;

import java.util.List;

import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

/**
 * Utility class for the BillPersistenceDelegator
 * 
 * @author Thomas
 *
 */
public class BillPersistenceDelegatorUtils {
	/**
	 * Method building the String status message for successful removal of Bill
	 * entities
	 * 
	 * @param successfullyAddedBillEntities
	 *            a list of successfully added BillEntities
	 * @param unsuccessfullyAddedBillEntities
	 *            a list of unsuccessfully added BillEntities
	 * @return success status message for Bill entity removal
	 */
	public static String buildPersistBillsSuccessStatusMessage(List<GAEBillEntity> successfullyAddedBillEntities,
			List<GAEBillEntity> unsuccessfullyAddedBillEntities) {
		int numberOfSuccessfullyAddedEntities = (successfullyAddedBillEntities != null)
				? successfullyAddedBillEntities.size() : 0;
		int numberOfUnsuccessfullyAddedEntities = (successfullyAddedBillEntities != null)
				? unsuccessfullyAddedBillEntities.size() : 0;
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.ADD_BILL_SUCCESS_MSG);
		sb.append(" '");
		sb.append(ShoppingServiceEntityMapper.mapBillEntityListToConsolidatedJSONString(successfullyAddedBillEntities));
		sb.append("'. ").append(numberOfSuccessfullyAddedEntities).append(" bills were successfully added.");
		if (numberOfUnsuccessfullyAddedEntities > 0) {
			sb.append(System.getProperty("line.separator"));
			sb.append(ShoppingServiceErrorConstants.ADD_BILL_FAILURE_MSG);
			sb.append(" '");
			sb.append(ShoppingServiceEntityMapper
					.mapBillEntityListToConsolidatedJSONString(unsuccessfullyAddedBillEntities));
			sb.append("'. ").append(numberOfUnsuccessfullyAddedEntities).append(" bills were not added successfully.");
		}
		return sb.toString();
	}

	/**
	 * Method building the String status message used in the response when
	 * removing BillEntity from the datastore
	 * 
	 * @param removed
	 *            BillEntities a list of removed BillEntities
	 * @return the status message that can be used in the response as String
	 */
	public static String buildRemoveBillsSuccessStatusMessage(List<GAEBillEntity> removedBillEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.DELETE_BILL_SUCCESS_MSG);
		sb.append(" '");
		sb.append(ShoppingServiceEntityMapper.mapBillEntityListToConsolidatedJSONString(removedBillEntities));
		sb.append("'. ");
		sb.append(removedBillEntities.size());
		sb.append(" Entities were removed.");
		return sb.toString();
	}

	/**
	 * Method building the String status message for successful retrieval of a
	 * specific BillEntity from the datastore
	 * 
	 * @param foundBills
	 *            the Bill that was found
	 * @return the created status message
	 */
	public static String buildGetBillsSuccessStatusMessage(List<GAEBillEntity> foundBills) {
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.RETRIEVE_BILL_SUCCESS_MSG);
		sb.append(" : '");
		sb.append(ShoppingServiceEntityMapper.mapBillEntityListToConsolidatedJSONString(foundBills));
		sb.append("'.");
		return sb.toString();
	}

	/**
	 * Method building the String status message for successful update
	 * BillEntities
	 * 
	 * @param updatedBills
	 *            the Bills that were successfully updated
	 * @return the created status message
	 */
	public static String buildUpdateBillsSuccessStatusMessage(List<GAEBillEntity> updatedBills) {
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.UPDATE_BILL_SUCCESS_MSG);
		sb.append(" : '");
		sb.append(ShoppingServiceEntityMapper.mapBillEntityListToConsolidatedJSONString(updatedBills));
		sb.append("'.");
		return sb.toString();
	}
}
