package amtc.gue.ws.shopping.delegate.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
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
				delegatorOutput.setStatusMessage(ShopPersistenceDelegatorUtils.buildPersistShopSuccessStatusMessage(
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
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateEntities() {
		// TODO Auto-generated method stub

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
	 *            the ShopinigEntityMapper instance used by the delegator
	 */
	public void setShopEntityMapper(ShoppingServiceEntityMapper shoppingEntityMapper) {
		this.shoppingEntityMapper = shoppingEntityMapper;
	}
}
