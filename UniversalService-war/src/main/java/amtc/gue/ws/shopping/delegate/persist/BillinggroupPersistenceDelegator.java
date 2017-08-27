package amtc.gue.ws.shopping.delegate.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.shopping.inout.Bill;
import amtc.gue.ws.shopping.inout.Billinggroup;
import amtc.gue.ws.shopping.inout.Billinggroups;
import amtc.gue.ws.shopping.persistence.dao.BillDAO;
import amtc.gue.ws.shopping.persistence.dao.BillinggroupDAO;
import amtc.gue.ws.shopping.persistence.dao.ShopDAO;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;
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
	private BillDAO<GAEBillEntity, GAEBillEntity, String> billDAOImpl;
	private ShopDAO<GAEShopEntity, GAEShopEntity, String> shopDAOImpl;

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

			// remove Billinggroup Entities from DB
			for (Billinggroup billinggroup : billinggroupsToRemove.getBillinggroups()) {
				GAEBillinggroupEntity billinggroupEntity = shoppingEntityMapper.mapBillinggroupToEntity(billinggroup,
						DelegatorTypeEnum.DELETE);
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
					log.log(Level.SEVERE, "Error while remove to persist: " + billinggroupEntityJSON, e);
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
		try {
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_SUCCESS_CODE);
			if (delegatorInput.getInputObject() instanceof String) {
				String billinggroupKey = (String) delegatorInput.getInputObject();
				GAEBillinggroupEntity foundBillinggroupEntity = retrieveBillinggroupsByBillinggroupKey(billinggroupKey);
				String statusMessage = BillinggroupPersistenceDelegatorUtils
						.buildGetBillinggroupsByIdSuccessStatusMessage(billinggroupKey, foundBillinggroupEntity);
				log.info(statusMessage);
				delegatorOutput.setStatusMessage(statusMessage);
				delegatorOutput.setOutputObject(foundBillinggroupEntity);
			} else {
				List<GAEBillinggroupEntity> foundBillinggroupEntities = retrieveAllBillinggroups();
				String statusMessage = BillinggroupPersistenceDelegatorUtils
						.buildGetBillinggroupsSuccessStatusMessage(foundBillinggroupEntities);
				log.info(statusMessage);
				delegatorOutput.setStatusMessage(statusMessage);
				delegatorOutput.setOutputObject(foundBillinggroupEntities);
			}
		} catch (EntityRetrievalException e) {
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_FAILURE_CODE);
			delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_FAILURE_MSG);
			delegatorOutput.setStatusReason(e.getMessage());
			delegatorOutput.setOutputObject(null);
		}
	}

	/**
	 * Method retrieving a BillinggroupEntity from the database by billinggroupKey
	 * 
	 * @param billinggroupKey
	 *            the billinggroupKey that is searched for
	 * @return the found BillinggroupEntity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve BillinggroupEntity
	 */
	private GAEBillinggroupEntity retrieveBillinggroupsByBillinggroupKey(String billinggroupKey)
			throws EntityRetrievalException {
		log.info("READ Billinggroup by Key action triggered");
		GAEBillinggroupEntity foundBillinggroupEntity = null;
		try {
			foundBillinggroupEntity = billinggroupDAOImpl.findEntityById(billinggroupKey);
		} catch (EntityRetrievalException e) {
			log.log(Level.SEVERE,
					"Error while trying to retrieve billinggroup with billinggroupKey: '" + billinggroupKey + "'", e);
			throw e;
		}
		return foundBillinggroupEntity;
	}

	/**
	 * Method retrieving all Billinggroups from the database
	 * 
	 * @return the found BillinggroupEntities
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve BillinggroupEntity
	 */
	private List<GAEBillinggroupEntity> retrieveAllBillinggroups() throws EntityRetrievalException {
		log.info("READ Billinggroups action triggered");
		List<GAEBillinggroupEntity> foundBillinggroups = null;
		try {
			foundBillinggroups = billinggroupDAOImpl.findAllEntities();
		} catch (EntityRetrievalException e) {
			log.log(Level.SEVERE, "Error while trying to retrieve billinggroups", e);
			throw e;
		}
		return foundBillinggroups;
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

			// remove Billinggroup Entities from DB
			for (Billinggroup billinggroupToUpdate : billinggroupsToUpdate.getBillinggroups()) {
				GAEBillinggroupEntity billinggroupEntity = shoppingEntityMapper
						.mapBillinggroupToEntity(billinggroupToUpdate, DelegatorTypeEnum.UPDATE);
				String billinggroupEntityJSON = ShoppingServiceEntityMapper
						.mapBillinggroupEntityToJSONString(billinggroupEntity);
				// differ between adding users to billinggroups and adding bills
				// to billinggroups
				if (billinggroupToUpdate.getUsers() != null && billinggroupToUpdate.getUsers().size() == 1) {
					// TODO add user to billinggroup functionality
				} else if (billinggroupToUpdate.getBills() != null && billinggroupToUpdate.getBills().size() == 1) {
					try {
						// load billinggroup
						GAEBillinggroupEntity foundBillinggroup = retrieveBillinggroupsByBillinggroupKey(
								billinggroupToUpdate.getBillinggroupId());
						// check if user adding the bill is included in the
						// billinggroup
						Bill billToCreate = billinggroupToUpdate.getBills().get(0);
						User billCreationUser = billToCreate.getUser();
						GAEUserEntity billCreationUserEntity = userEntityMapper.mapUserToEntity(billCreationUser,
								DelegatorTypeEnum.UPDATE);
						if (isUserRegisteredToBillinggroup(foundBillinggroup, billCreationUserEntity)) {
							// persist bill
							GAEShopEntity shopEntityToPersist = (billToCreate.getShop() != null) ? shoppingEntityMapper
									.mapShopToEntity(billToCreate.getShop(), DelegatorTypeEnum.ADD) : null;
							GAEBillEntity billEntityToPersist = shoppingEntityMapper.mapBillToEntity(billToCreate,
									DelegatorTypeEnum.ADD);
							handleShopPersistenceForBillEntity(billEntityToPersist, shopEntityToPersist);
							GAEBillEntity persistedBillEntity = billDAOImpl.persistEntity(billEntityToPersist);
							String billEntityJSON = ShoppingServiceEntityMapper
									.mapBillEntityToJSONString(persistedBillEntity);
							log.info(billEntityJSON + " added to DB");
							// add bill to billinggroup
							foundBillinggroup.addToBillsOnly(persistedBillEntity);
							// update bill and billinggroup
							billDAOImpl.updateEntity(persistedBillEntity);
							GAEBillinggroupEntity updatedBillinggroupEntity = billinggroupDAOImpl
									.updateEntity(foundBillinggroup);
							successfullyUpdatedBillinggroupEntities.add(updatedBillinggroupEntity);
							log.info("Bill succesfully added to billinggroup '" + updatedBillinggroupEntity.getKey()
									+ "'");
						} else {
							unsuccessfullyUpdatedBillinggroupEntities.add(billinggroupEntity);
							String statusMessage = "User with key '" + billCreationUserEntity.getKey()
									+ "' is not registered in billinggroup with key '" + foundBillinggroup.getKey()
									+ "'.";
							sb.append(statusMessage);
							log.log(Level.SEVERE, statusMessage);
						}
					} catch (EntityRetrievalException e) {
						unsuccessfullyUpdatedBillinggroupEntities.add(billinggroupEntity);
						sb.append(e.getMessage());
						log.log(Level.SEVERE, "Billinggroup with key'" + billinggroupToUpdate.getBillinggroupId()
								+ "' was not found.");
					} catch (EntityPersistenceException e) {
						unsuccessfullyUpdatedBillinggroupEntities.add(billinggroupEntity);
						sb.append(e.getMessage());
						log.log(Level.SEVERE, "Failed to update billinggroup with key'"
								+ billinggroupToUpdate.getBillinggroupId() + "'.");
					}
				} else {
					// update BillinggroupEntity
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
	 * Method checking if a specific userEntity is already registered to the
	 * billinggroupEntity
	 * 
	 * @param billinggroupEntity
	 *            the billinggroupEntity that is checked for registered users
	 * @param user
	 *            the user that is searched for in the billinggroupEntity
	 * @return true if the user is registered to the billinggroupEntity, false if
	 *         not
	 */
	private boolean isUserRegisteredToBillinggroup(GAEBillinggroupEntity billinggroupEntity, GAEUserEntity userEntity) {
		if (billinggroupEntity.getUsers().contains(userEntity)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method that checks if the shop associated with the BillEntity already exists.
	 * If not, the ShopEntity is added
	 * 
	 * @param billEntity
	 *            the BillEntity whose shop is checked
	 * @param shopEntity
	 *            the ShopEntity
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve ShopEntity
	 * @throws EntityPersistenceException
	 *             when issue occurs while trying to persist ShopEntity
	 */
	private void handleShopPersistenceForBillEntity(GAEBillEntity billEntity, GAEShopEntity shopEntity)
			throws EntityRetrievalException, EntityPersistenceException {
		String shopEntityJSON = ShoppingServiceEntityMapper.mapShopEntityToJSONString(shopEntity);
		GAEShopEntity persistedShopEntity;
		try {
			List<GAEShopEntity> foundShopEntities = shopDAOImpl.findSpecificEntity(shopEntity);
			if (foundShopEntities.isEmpty()) {
				persistedShopEntity = shopDAOImpl.persistEntity(shopEntity);
			} else {
				persistedShopEntity = foundShopEntities.get(0);
			}
			billEntity.setShop(persistedShopEntity);
		} catch (EntityRetrievalException e) {
			log.log(Level.SEVERE, "Error while trying to retrieve shopEntity: " + shopEntityJSON, e);
			throw new EntityRetrievalException(e.getMessage(), e);
		} catch (EntityPersistenceException e) {
			log.log(Level.SEVERE, "Error while trying to persist shopEntity: " + shopEntityJSON, e);
			throw new EntityPersistenceException(e.getMessage(), e);
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
	 * Setter for the used shopDAOImpl
	 * 
	 * @param shopDAOimpl
	 *            the ShopDAOImpl object
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void setShopDAO(ShopDAO shopDAOimpl) {
		this.shopDAOImpl = shopDAOimpl;
	}

	/**
	 * Setter for the User entity mapper
	 * 
	 * @param shoppingEntityMapper
	 *            the ShoppingEntityMapper instance used by the delegator
	 */
	public void setShoppingEntityMapper(ShoppingServiceEntityMapper shoppingEntityMapper) {
		this.shoppingEntityMapper = shoppingEntityMapper;
	}
}
