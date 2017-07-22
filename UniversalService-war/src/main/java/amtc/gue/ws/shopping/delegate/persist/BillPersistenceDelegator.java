package amtc.gue.ws.shopping.delegate.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.shopping.inout.Bill;
import amtc.gue.ws.shopping.inout.Bills;
import amtc.gue.ws.shopping.persistence.dao.BillDAO;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.util.BillPersistenceDelegatorUtils;
import amtc.gue.ws.shopping.util.ShoppingServiceErrorConstants;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

/**
 * Persistence Delegator that handles all database actions for Bill resources
 * 
 * @author Thomas
 *
 */
public class BillPersistenceDelegator extends AbstractPersistenceDelegator {

	private static final Logger log = Logger.getLogger(BillPersistenceDelegator.class.getName());

	/** DAOImplementations used by the delegator */
	private BillDAO<GAEBillEntity, GAEBillEntity, String> billDAOImpl;

	/** EntityMapper user by the delegator */
	private ShoppingServiceEntityMapper shoppingEntityMapper;

	@Override
	protected void persistEntities() {
		log.info("ADD Bill action triggered");
		if (delegatorInput.getInputObject() instanceof Bills) {
			Bills billsToPersist = (Bills) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.ADD_BILL_SUCCESS_CODE);
			List<GAEBillEntity> successfullyAddedBillEntities = new ArrayList<>();
			List<GAEBillEntity> unsuccessfullyAddedBillEntities = new ArrayList<>();
			StringBuilder sb = new StringBuilder();

			// persist all UserEntities to the DB
			for (Bill bill : billsToPersist.getBills()) {
				GAEBillEntity billEntity = shoppingEntityMapper.mapBillToEntity(bill, DelegatorTypeEnum.ADD);
				String billEntityJSON = ShoppingServiceEntityMapper.mapBillEntityToJSONString(billEntity);
				try {
					GAEBillEntity persistedBillEntity = billDAOImpl.persistEntity(billEntity);
					successfullyAddedBillEntities.add(persistedBillEntity);
					log.info(billEntityJSON + " added to DB");
				} catch (Exception e) {
					unsuccessfullyAddedBillEntities.add(billEntity);
					sb.append(e.getMessage());
					log.log(Level.SEVERE, "Error while trying to persist: " + billEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyAddedBillEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(BillPersistenceDelegatorUtils.buildPersistBillsSuccessStatusMessage(
						successfullyAddedBillEntities, unsuccessfullyAddedBillEntities));
				delegatorOutput.setOutputObject(
						ShoppingServiceEntityMapper.transformBillEntitiesToBills(successfullyAddedBillEntities));
			} else {
				delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.ADD_BILL_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.ADD_BILL_FAILURE_MSG);
				delegatorOutput.setStatusReason(sb.toString());
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	@Override
	protected void removeEntities() {
		log.info("DELETE Bill action triggered");
		if (delegatorInput.getInputObject() instanceof Bills) {
			Bills billsToRemove = (Bills) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.DELETE_BILL_SUCCESS_CODE);
			List<GAEBillEntity> successfullyRemovedBillEntities = new ArrayList<>();
			List<GAEBillEntity> unsuccessfullyRemovedBillEntities = new ArrayList<>();
			StringBuilder sb = new StringBuilder();

			// remove ShopEntities from DB
			for (Bill bill : billsToRemove.getBills()) {
				GAEBillEntity billEntity = shoppingEntityMapper.mapBillToEntity(bill, DelegatorTypeEnum.ADD);
				String billEntityJSON = ShoppingServiceEntityMapper.mapBillEntityToJSONString(billEntity);
				try {
					GAEBillEntity removedBillEntity = billDAOImpl.removeEntity(billEntity);
					successfullyRemovedBillEntities.add(removedBillEntity);
					log.info(billEntityJSON + " added to DB");
				} catch (Exception e) {
					unsuccessfullyRemovedBillEntities.add(billEntity);
					sb.append(e.getMessage());
					log.log(Level.SEVERE, "Error while trying to persist: " + billEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyRemovedBillEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(BillPersistenceDelegatorUtils
						.buildRemoveBillsSuccessStatusMessage(successfullyRemovedBillEntities));
				delegatorOutput.setOutputObject(
						ShoppingServiceEntityMapper.transformBillEntitiesToBills(successfullyRemovedBillEntities));
			} else {
				delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.DELETE_BILL_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.DELETE_BILL_FAILURE_MSG);
				delegatorOutput.setStatusReason(sb.toString());
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	@Override
	protected void retrieveEntities() {
		log.info("READ Bill action triggered");
		delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.RETRIEVE_BILL_SUCCESS_CODE);
		try {
			List<GAEBillEntity> foundBills = billDAOImpl.findAllEntities();
			delegatorOutput
					.setStatusMessage(BillPersistenceDelegatorUtils.buildGetBillsSuccessStatusMessage(foundBills));
			delegatorOutput.setOutputObject(ShoppingServiceEntityMapper.transformBillEntitiesToBills(foundBills));
		} catch (EntityRetrievalException e) {
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.RETRIEVE_BILL_FAILURE_CODE);
			delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.RETRIEVE_BILL_FAILURE_MSG);
			delegatorOutput.setStatusReason(e.getMessage());
			delegatorOutput.setOutputObject(null);
			log.log(Level.SEVERE, "Error while trying to retrieve bills", e);
		}
	}

	@Override
	protected void updateEntities() {
		log.info("UPDATE Bill action triggered");
		if (delegatorInput.getInputObject() instanceof Bills) {
			Bills billsToUpdate = (Bills) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.UPDATE_BILL_SUCCESS_CODE);
			List<GAEBillEntity> successfullyUpdatedBillEntities = new ArrayList<>();
			List<GAEBillEntity> unsuccessfullyUpdatedBillEntities = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			// update ShopEntities
			for (Bill bill : billsToUpdate.getBills()) {
				GAEBillEntity billEntity = shoppingEntityMapper.mapBillToEntity(bill, DelegatorTypeEnum.UPDATE);
				String billEntityJSON = ShoppingServiceEntityMapper.mapBillEntityToJSONString(billEntity);
				try {
					GAEBillEntity updatedBillEntity = billDAOImpl.updateEntity(billEntity);
					successfullyUpdatedBillEntities.add(updatedBillEntity);
					log.info(billEntityJSON + " added to DB");
				} catch (Exception e) {
					unsuccessfullyUpdatedBillEntities.add(billEntity);
					sb.append(e.getMessage());
					log.log(Level.SEVERE, "Error while trying to update: " + billEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyUpdatedBillEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(BillPersistenceDelegatorUtils
						.buildUpdateBillsSuccessStatusMessage(successfullyUpdatedBillEntities));
				delegatorOutput.setOutputObject(
						ShoppingServiceEntityMapper.transformBillEntitiesToBills(successfullyUpdatedBillEntities));
			} else {
				delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.UPDATE_BILL_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.UPDATE_BILL_FAILURE_MSG);
				delegatorOutput.setStatusReason(sb.toString());
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	/**
	 * Setter for the used billDAOImpl
	 * 
	 * @param billDAOImpl
	 *            the BillDAOImpl object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setBillDAO(BillDAO billDAOImpl) {
		this.billDAOImpl = billDAOImpl;
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
