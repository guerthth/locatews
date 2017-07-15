package amtc.gue.ws.shopping.persistence.dao.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.dao.ObjectifyDAOImpl;
import amtc.gue.ws.shopping.persistence.dao.ShopDAO;
import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyShopEntity;

/**
 * Implementation for the Objectify ShopDAO
 * 
 * @author Thomas
 *
 */
public class ShopObjectifyDAOImpl extends ObjectifyDAOImpl<GAEShopEntity, GAEObjectifyShopEntity, String>
		implements ShopDAO<GAEShopEntity, GAEObjectifyShopEntity, String> {
	static {
		ObjectifyService.register(GAEObjectifyShopEntity.class);
	}

	@Override
	public List<GAEShopEntity> findSpecificEntity(GAEShopEntity entity) throws EntityRetrievalException {
		GAEObjectifyShopEntity specificEntity = (GAEObjectifyShopEntity) entity;
		List<GAEShopEntity> foundEntities = new ArrayList<>();
		try {
			if (specificEntity != null && specificEntity.getKey() != null) {
				// if entity has an ID, search by ID
				GAEObjectifyShopEntity foundEntity = (GAEObjectifyShopEntity) ofy().load().entity(specificEntity).now();
				if (foundEntity != null) {
					foundEntities.add(foundEntity);
				}
			} else {
				Query<GAEObjectifyShopEntity> query = ofy().load().type(GAEObjectifyShopEntity.class);
				if (specificEntity.getShopName() != null) {
					query = query.filter("shopName", specificEntity.getShopName());
				}
				List<GAEObjectifyShopEntity> shopEntities = query.list();
				for (GAEObjectifyShopEntity shopEntity : shopEntities) {
					foundEntities.add(shopEntity);
				}
			}
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieval of specific ShopEntity failed.", e);
		}
		return foundEntities;
	}

	@Override
	public GAEShopEntity updateEntity(GAEShopEntity entity) throws EntityPersistenceException {
		GAEObjectifyShopEntity specificEntity;
		try {
			specificEntity = (GAEObjectifyShopEntity) entity;
			ofy().save().entity(specificEntity).now();
		} catch (Exception e) {
			throw new EntityPersistenceException("Updating ShopEntity " + entity.getKey() + " failed", e);
		}
		return specificEntity;
	}
}
