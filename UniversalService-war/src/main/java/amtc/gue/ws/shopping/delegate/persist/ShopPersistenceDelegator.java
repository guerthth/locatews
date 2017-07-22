package amtc.gue.ws.shopping.delegate.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.shopping.inout.Shop;
import amtc.gue.ws.shopping.inout.Shops;
import amtc.gue.ws.shopping.persistence.dao.ShopDAO;
import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;
import amtc.gue.ws.shopping.util.ShopPersistenceDelegatorUtils;
import amtc.gue.ws.shopping.util.ShoppingServiceErrorConstants;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

/**
 * Persistence Delegator that handles all database actions for Shop resources
 * 
 * @author Thomas
 *
 */
public class ShopPersistenceDelegator extends AbstractPersistenceDelegator {
	private static final Logger log = Logger.getLogger(ShopPersistenceDelegator.class.getName());

	/** DAOImplementations used by the delegator */
	private ShopDAO<GAEShopEntity, GAEShopEntity, String> shopDAOImpl;

	/** EntityMapper user by the delegator */
	private ShoppingServiceEntityMapper shoppingEntityMapper;

	@Override
	protected void persistEntities() {
		log.info("ADD Shop action triggered");
		if (delegatorInput.getInputObject() instanceof Shops) {
			Shops shopsToPersist = (Shops) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.ADD_SHOP_SUCCESS_CODE);
			List<GAEShopEntity> successfullyAddedShopEntities = new ArrayList<>();
			List<GAEShopEntity> unsuccessfullyAddedShopEntities = new ArrayList<>();
			StringBuilder sb = new StringBuilder();

			// persist all UserEntities to the DB
			for (Shop shop : shopsToPersist.getShops()) {
				GAEShopEntity shopEntity = shoppingEntityMapper.mapShopToEntity(shop, DelegatorTypeEnum.ADD);
				String shopEntityJSON = ShoppingServiceEntityMapper.mapShopEntityToJSONString(shopEntity);
				try {
					GAEShopEntity persistedShopEntity = shopDAOImpl.persistEntity(shopEntity);
					successfullyAddedShopEntities.add(persistedShopEntity);
					log.info(shopEntityJSON + " added to DB");
				} catch (Exception e) {
					unsuccessfullyAddedShopEntities.add(shopEntity);
					sb.append(e.getMessage());
					log.log(Level.SEVERE, "Error while trying to persist: " + shopEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyAddedShopEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(ShopPersistenceDelegatorUtils.buildPersistShopsSuccessStatusMessage(
						successfullyAddedShopEntities, unsuccessfullyAddedShopEntities));
				delegatorOutput.setOutputObject(
						ShoppingServiceEntityMapper.transformShopEntitiesToShops(successfullyAddedShopEntities));
			} else {
				delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.ADD_SHOP_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.ADD_SHOP_FAILURE_MSG);
				delegatorOutput.setStatusReason(sb.toString());
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	@Override
	protected void removeEntities() {
		log.info("DELETE Shop action triggered");
		if (delegatorInput.getInputObject() instanceof Shops) {
			Shops shopsToRemove = (Shops) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.DELETE_SHOP_SUCCESS_CODE);
			List<GAEShopEntity> successfullyRemovedShopEntities = new ArrayList<>();
			List<GAEShopEntity> unsuccessfullyRemovedShopEntities = new ArrayList<>();
			StringBuilder sb = new StringBuilder();

			// remove ShopEntities from DB
			for (Shop shop : shopsToRemove.getShops()) {
				GAEShopEntity shopEntity = shoppingEntityMapper.mapShopToEntity(shop, DelegatorTypeEnum.ADD);
				String shopEntityJSON = ShoppingServiceEntityMapper.mapShopEntityToJSONString(shopEntity);
				try {
					GAEShopEntity removedShopEntity = shopDAOImpl.removeEntity(shopEntity);
					successfullyRemovedShopEntities.add(removedShopEntity);
					log.info(shopEntityJSON + " added to DB");
				} catch (Exception e) {
					unsuccessfullyRemovedShopEntities.add(shopEntity);
					sb.append(e.getMessage());
					log.log(Level.SEVERE, "Error while trying to persist: " + shopEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyRemovedShopEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(ShopPersistenceDelegatorUtils
						.buildRemoveShopsSuccessStatusMessage(successfullyRemovedShopEntities));
				delegatorOutput.setOutputObject(
						ShoppingServiceEntityMapper.transformShopEntitiesToShops(successfullyRemovedShopEntities));
			} else {
				delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.DELETE_SHOP_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.DELETE_SHOP_FAILURE_MSG);
				delegatorOutput.setStatusReason(sb.toString());
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	@Override
	protected void retrieveEntities() {
		log.info("READ Shop action triggered");
		delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.RETRIEVE_SHOP_SUCCESS_CODE);
		try {
			List<GAEShopEntity> foundShops = shopDAOImpl.findAllEntities();
			delegatorOutput
					.setStatusMessage(ShopPersistenceDelegatorUtils.buildGetShopsSuccessStatusMessage(foundShops));
			delegatorOutput.setOutputObject(ShoppingServiceEntityMapper.transformShopEntitiesToShops(foundShops));
		} catch (EntityRetrievalException e) {
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.RETRIEVE_SHOP_FAILURE_CODE);
			delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.RETRIEVE_SHOP_FAILURE_MSG);
			delegatorOutput.setStatusReason(e.getMessage());
			delegatorOutput.setOutputObject(null);
			log.log(Level.SEVERE, "Error while trying to retrieve shops", e);
		}
	}

	@Override
	protected void updateEntities() {
		log.info("UPDATE Shop action triggered");
		if (delegatorInput.getInputObject() instanceof Shops) {
			Shops shopsToUpdate = (Shops) delegatorInput.getInputObject();
			delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.UPDATE_SHOP_SUCCESS_CODE);
			List<GAEShopEntity> successfullyUpdatedShopEntities = new ArrayList<>();
			List<GAEShopEntity> unsuccessfullyUpdatedShopEntities = new ArrayList<>();
			StringBuilder sb = new StringBuilder();
			// update ShopEntities
			for (Shop shop : shopsToUpdate.getShops()) {
				GAEShopEntity shopEntity = shoppingEntityMapper.mapShopToEntity(shop, DelegatorTypeEnum.UPDATE);
				String shopEntityJSON = ShoppingServiceEntityMapper.mapShopEntityToJSONString(shopEntity);
				try {
					GAEShopEntity updatedShopEntity = shopDAOImpl.updateEntity(shopEntity);
					successfullyUpdatedShopEntities.add(updatedShopEntity);
					log.info(shopEntityJSON + " added to DB");
				} catch (Exception e) {
					unsuccessfullyUpdatedShopEntities.add(shopEntity);
					sb.append(e.getMessage());
					log.log(Level.SEVERE, "Error while trying to update: " + shopEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyUpdatedShopEntities.isEmpty()) {
				delegatorOutput.setStatusMessage(ShopPersistenceDelegatorUtils
						.buildUpdateShopsSuccessStatusMessage(successfullyUpdatedShopEntities));
				delegatorOutput.setOutputObject(
						ShoppingServiceEntityMapper.transformShopEntitiesToShops(successfullyUpdatedShopEntities));
			} else {
				delegatorOutput.setStatusCode(ShoppingServiceErrorConstants.UPDATE_SHOP_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ShoppingServiceErrorConstants.UPDATE_SHOP_FAILURE_MSG);
				delegatorOutput.setStatusReason(sb.toString());
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	/**
	 * Setter for the used shopDAOImpl
	 * 
	 * @param shopDAOImpl
	 *            the ShopDAOImpl object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setShopDAO(ShopDAO shopDAOImpl) {
		this.shopDAOImpl = shopDAOImpl;
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
