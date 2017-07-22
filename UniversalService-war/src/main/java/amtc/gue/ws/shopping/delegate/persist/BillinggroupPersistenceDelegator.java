package amtc.gue.ws.shopping.delegate.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.shopping.inout.Billinggroup;
import amtc.gue.ws.shopping.inout.Billinggroups;
import amtc.gue.ws.shopping.persistence.dao.BillinggroupDAO;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.util.BillinggroupPersistenceDelegatorUtils;
import amtc.gue.ws.shopping.util.ShoppingServiceErrorConstants;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

/**
 * Persistence Delegator that handles all database action for Billinggroup
 * resources
 * 
 * @author Thomas
 *
 */
public class BillinggroupPersistenceDelegator extends AbstractPersistenceDelegator {

	private static final Logger log = Logger.getLogger(BillinggroupPersistenceDelegator.class.getName());

	/** DAOImplementations used by the delegator */
	private BillinggroupDAO<GAEBillinggroupEntity, GAEBillinggroupEntity, String> billinggroupDAOImpl;

	/** EntityMapper user by the delegator */
	private ShoppingServiceEntityMapper shoppingEntityMapper;

	@Override
	protected void persistEntities() {
		log.info("ADD Billinggroup action triggered");
		if (delegatorInput.getInputObject() instanceof Billinggroups) {
			Billinggroups billinggroupsToPersist = (Billinggroups) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.ADD_BILLINGGROUP_SUCCESS_CODE);
			List<GAEBillinggroupEntity> successfullyAddedBillinggroupEntities = new ArrayList<>();
			List<GAEBillinggroupEntity> unsuccessfullyAddedBillinggroupEntities = new ArrayList<>();
			StringBuilder sb = new StringBuilder();

			// persist all UserEntities to the DB
			for (Billinggroup billinggroup : billinggroupsToPersist.getBillinggroups()) {
				GAEBillinggroupEntity billinggroupEntity = shoppingEntityMapper.mapBillinggroupToEntity(billinggroup,
						DelegatorTypeEnum.ADD);
				String billinggroupEntityJSON = ShoppingServiceEntityMapper
						.mapBillinggroupEntityToJSONString(billinggroupEntity);
				try {
					GAEBillinggroupEntity persistedBillinggroupEntity = billinggroupDAOImpl
							.persistEntity(billinggroupEntity);
					successfullyAddedBillinggroupEntities.add(persistedBillinggroupEntity);
					log.info(billinggroupEntityJSON + " added to DB");
				} catch (Exception e) {
					unsuccessfullyAddedBillinggroupEntities.add(billinggroupEntity);
					sb.append(e.getMessage());
					log.log(Level.SEVERE, "Error while trying to persist: " + billinggroupEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyAddedBillinggroupEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(
						BillinggroupPersistenceDelegatorUtils.buildPersistBillinggroupSuccessStatusMessage(
								successfullyAddedBillinggroupEntities, unsuccessfullyAddedBillinggroupEntities));
				delegatorOutput.setOutputObject(ShoppingServiceEntityMapper
						.transformBillinggroupEntitiesToBillinggroups(successfullyAddedBillinggroupEntities));
			} else {
				delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.ADD_BILLINGGROUP_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.ADD_BILLINGGROUP_FAILURE_MSG);
				delegatorOutput.setStatusReason(sb.toString());
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	@Override
	protected void removeEntities() {
		log.info("DELETE Billinggroup action triggered");
		if (delegatorInput.getInputObject() instanceof Billinggroups) {
			Billinggroups billinggroupsToRemove = (Billinggroups) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_SUCCESS_CODE);
			List<GAEBillinggroupEntity> successfullyRemovedBillinggroupEntities = new ArrayList<>();
			List<GAEBillinggroupEntity> unsuccessfullyRemovedBillinggroupEntities = new ArrayList<>();
			StringBuilder sb = new StringBuilder();

			// remove ShopEntities from DB
			for (Billinggroup billinggroup : billinggroupsToRemove.getBillinggroups()) {
				GAEBillinggroupEntity billinggroupEntity = shoppingEntityMapper.mapBillinggroupToEntity(billinggroup,
						DelegatorTypeEnum.ADD);
				String billinggroupEntityJSON = ShoppingServiceEntityMapper
						.mapBillinggroupEntityToJSONString(billinggroupEntity);
				try {
					GAEBillinggroupEntity removedBillinggroupEntity = billinggroupDAOImpl
							.removeEntity(billinggroupEntity);
					successfullyRemovedBillinggroupEntities.add(removedBillinggroupEntity);
					log.info(billinggroupEntityJSON + " added to DB");
				} catch (Exception e) {
					unsuccessfullyRemovedBillinggroupEntities.add(billinggroupEntity);
					sb.append(e.getMessage());
					log.log(Level.SEVERE, "Error while trying to persist: " + billinggroupEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyRemovedBillinggroupEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(BillinggroupPersistenceDelegatorUtils
						.buildRemoveBillinggroupsSuccessStatusMessage(successfullyRemovedBillinggroupEntities));
				delegatorOutput.setOutputObject(ShoppingServiceEntityMapper
						.transformBillinggroupEntitiesToBillinggroups(successfullyRemovedBillinggroupEntities));
			} else {
				delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.DELETE_BILLINGGROUP_FAILURE_MSG);
				delegatorOutput.setStatusReason(sb.toString());
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	@Override
	protected void retrieveEntities() {
		log.info("READ Billinggroup action triggered");
		delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_SUCCESS_CODE);
		try {
			List<GAEBillinggroupEntity> foundBillinggroups = billinggroupDAOImpl.findAllEntities();
			delegatorOutput.setStatusMessage(BillinggroupPersistenceDelegatorUtils
					.buildGetBillinggroupsSuccessStatusMessage(foundBillinggroups));
			delegatorOutput.setOutputObject(
					ShoppingServiceEntityMapper.transformBillinggroupEntitiesToBillinggroups(foundBillinggroups));
		} catch (EntityRetrievalException e) {
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_FAILURE_CODE);
			delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_FAILURE_MSG);
			delegatorOutput.setStatusReason(e.getMessage());
			delegatorOutput.setOutputObject(null);
			log.log(Level.SEVERE, "Error while trying to retrieve billinggroups", e);
		}
	}

	@Override
	protected void updateEntities() {
		log.info("UPDATE Billinggroup action triggered");
		if (delegatorInput.getInputObject() instanceof Billinggroups) {
			Billinggroups billinggroupsToUpdate = (Billinggroups) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_SUCCESS_CODE);
			List<GAEBillinggroupEntity> successfullyUpdatedBillinggroupEntities = new ArrayList<>();
			List<GAEBillinggroupEntity> unsuccessfullyUpdatedBillinggroupEntities = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			// update ShopEntities
			for (Billinggroup billinggroup : billinggroupsToUpdate.getBillinggroups()) {
				GAEBillinggroupEntity billinggroupEntity = shoppingEntityMapper.mapBillinggroupToEntity(billinggroup,
						DelegatorTypeEnum.UPDATE);
				String billinggroupEntityJSON = ShoppingServiceEntityMapper
						.mapBillinggroupEntityToJSONString(billinggroupEntity);
				try {
					GAEBillinggroupEntity updatedBillinggroupEntity = billinggroupDAOImpl
							.updateEntity(billinggroupEntity);
					successfullyUpdatedBillinggroupEntities.add(updatedBillinggroupEntity);
					log.info(billinggroupEntityJSON + " added to DB");
				} catch (Exception e) {
					unsuccessfullyUpdatedBillinggroupEntities.add(billinggroupEntity);
					sb.append(e.getMessage());
					log.log(Level.SEVERE, "Error while trying to update: " + billinggroupEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyUpdatedBillinggroupEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(BillinggroupPersistenceDelegatorUtils
						.buildUpdateBillinggroupsSuccessStatusMessage(successfullyUpdatedBillinggroupEntities));
				delegatorOutput.setOutputObject(ShoppingServiceEntityMapper
						.transformBillinggroupEntitiesToBillinggroups(successfullyUpdatedBillinggroupEntities));
			} else {
				delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_FAILURE_MSG);
				delegatorOutput.setStatusReason(sb.toString());
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	/**
	 * Setter for the used billinggroupDAOImpl
	 * 
	 * @param billinggroupDAOImpl
	 *            the BillinggroupDAOImpl object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setBillinggroupDAO(BillinggroupDAO billinggroupDAOImpl) {
		this.billinggroupDAOImpl = billinggroupDAOImpl;
	}

	/**
	 * Setter for the Shopping entity mapper
	 * 
	 * @param shoppingEntityMapper
	 *            the ShoppingEntityMapper instance used by the delegator
	 */
	public void setShoppingEntityMapper(ShoppingServiceEntityMapper shoppingEntityMapper) {
		this.shoppingEntityMapper = shoppingEntityMapper;
	}
}
