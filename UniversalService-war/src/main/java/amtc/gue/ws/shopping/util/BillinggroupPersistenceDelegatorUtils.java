package amtc.gue.ws.shopping.util;

import java.util.List;

import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

/**
 * Utility class for the BillinggroupPersistenceDelegator
 * 
 * @author Thomas
 *
 */
public class BillinggroupPersistenceDelegatorUtils {
	/**
	 * Method building the String status message for successful removal of
	 * Billinggroup entities
	 * 
	 * @param successfullyAddedBillingroupEntities
	 *            a list of successfully added BillinggroupEntities
	 * @param unsuccessfullyAddedBillingroupEntities
	 *            a list of unsuccessfully added BillinggroupEntities
	 * @return success status message for billinggroup entity removal
	 */
	public static String buildPersistBillinggroupSuccessStatusMessage(
			List<GAEBillinggroupEntity> successfullyAddedBillinggroupEntities,
			List<GAEBillinggroupEntity> unsuccessfullyAddedBillinggroupEntities) {
		int numberOfSuccessfullyAddedEntities = (successfullyAddedBillinggroupEntities != null)
				? successfullyAddedBillinggroupEntities.size() : 0;
		int numberOfUnsuccessfullyAddedEntities = (successfullyAddedBillinggroupEntities != null)
				? unsuccessfullyAddedBillinggroupEntities.size() : 0;
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.ADD_BILLINGGROUP_SUCCESS_MSG);
		sb.append(" '");
		sb.append(ShoppingServiceEntityMapper
				.mapBillinggroupEntityListToConsolidatedJSONString(successfullyAddedBillinggroupEntities));
		sb.append("'. ").append(numberOfSuccessfullyAddedEntities).append(" billinggroups were successfully added.");
		if (numberOfUnsuccessfullyAddedEntities > 0) {
			sb.append(System.getProperty("line.separator"));
			sb.append(ShoppingServiceErrorConstants.ADD_BILLINGGROUP_FAILURE_MSG);
			sb.append(" '");
			sb.append(ShoppingServiceEntityMapper
					.mapBillinggroupEntityListToConsolidatedJSONString(unsuccessfullyAddedBillinggroupEntities));
			sb.append("'. ").append(numberOfUnsuccessfullyAddedEntities)
					.append(" billinggroups were not added successfully.");
		}
		return sb.toString();
	}

	/**
	 * Method building the String status message used in the response when
	 * removing BillinggroupEntity from the datastore
	 * 
	 * @param removedBillinggroupEntities
	 *            a list of removed BillinggroupEntities
	 * @return the status message that can be used in the response as String
	 */
	public static String buildRemoveBillinggroupsSuccessStatusMessage(
			List<GAEBillinggroupEntity> removedBillinggroupEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_SUCCESS_MSG);
		sb.append(" '");
		sb.append(ShoppingServiceEntityMapper
				.mapBillinggroupEntityListToConsolidatedJSONString(removedBillinggroupEntities));
		sb.append("'. ");
		sb.append(removedBillinggroupEntities.size());
		sb.append(" Entities were removed.");
		return sb.toString();
	}

	/**
	 * Method building the String status message for successful retrieval of a
	 * specific BillinggroupEntity from the datastore
	 * 
	 * @param foundBillinggroups
	 *            the billinggroup that was found
	 * @return the created status message
	 */
	public static String buildGetBillinggroupsSuccessStatusMessage(List<GAEBillinggroupEntity> foundBillinggroups) {
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_SUCCESS_MSG);
		sb.append(" : '");
		sb.append(ShoppingServiceEntityMapper.mapBillinggroupEntityListToConsolidatedJSONString(foundBillinggroups));
		sb.append("'.");
		return sb.toString();
	}

	/**
	 * Method building the String status message for successful update
	 * BillinggroupEntities
	 * 
	 * @param updatedBillinggroups
	 *            the billinggroups that were successfully updated
	 * @return the created status message
	 */
	public static String buildUpdateBillinggroupsSuccessStatusMessage(
			List<GAEBillinggroupEntity> updatedBillinggroups) {
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_SUCCESS_MSG);
		sb.append(" : '");
		sb.append(ShoppingServiceEntityMapper.mapBillinggroupEntityListToConsolidatedJSONString(updatedBillinggroups));
		sb.append("'.");
		return sb.toString();
	}

	/**
	 * Method building the String status message for successful retrieval of a
	 * Billinggroup by ID
	 * 
	 * @param billinggroupKey
	 *            the Key of the Billinggroup that is searched for
	 * @param foundBillinggroupEntity
	 *            the BillinggroupEntity that was found
	 * @return the created status message
	 */
	public static String buildGetBillinggroupsByIdSuccessStatusMessage(String billinggroupKey,
			GAEBillinggroupEntity foundBillinggroupEntity) {
		// TODO test
		StringBuilder sb = new StringBuilder();
		sb.append(ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_BY_ID_SUCCESS_MSG);
		sb.append(" : '");
		sb.append(ShoppingServiceEntityMapper.mapBillinggroupEntityToJSONString(foundBillinggroupEntity));
		sb.append("'.");
		return sb.toString();
	}
}
