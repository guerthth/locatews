package amtc.gue.ws.shopping.persistence.dao.objectify;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.ArrayList;
import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.cmd.Query;

import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.dao.ObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.model.role.objectify.GAEObjectifyRoleEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.shopping.persistence.dao.BillDAO;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyShopEntity;

/**
 * Implementation for the Objectify BillDAO
 * 
 * @author Thomas
 *
 */
public class BillObjectifyDAOImpl extends ObjectifyDAOImpl<GAEBillEntity, GAEObjectifyBillEntity, String>
		implements BillDAO<GAEBillEntity, GAEObjectifyBillEntity, String> {
	static {
		ObjectifyService.register(GAEObjectifyUserEntity.class);
		ObjectifyService.register(GAEObjectifyRoleEntity.class);
		ObjectifyService.register(GAEObjectifyBillinggroupEntity.class);
		ObjectifyService.register(GAEObjectifyShopEntity.class);
		ObjectifyService.register(GAEObjectifyBillEntity.class);
	}

	@Override
	public List<GAEBillEntity> findSpecificEntity(GAEBillEntity entity) throws EntityRetrievalException {
		GAEObjectifyBillEntity specificEntity = (GAEObjectifyBillEntity) entity;
		List<GAEBillEntity> foundEntities = new ArrayList<>();
		if (specificEntity != null && specificEntity.getKey() != null) {
			// if entity has an ID, search by ID
			GAEObjectifyBillEntity foundEntity = (GAEObjectifyBillEntity) ofy().load().entity(specificEntity).now();
			if (foundEntity != null) {
				foundEntities.add(foundEntity);
			}
		} else {
			// if not, search for similar attributes
			Query<GAEObjectifyBillEntity> query;
			if (specificEntity.getUser() != null && specificEntity.getUser().getKey() != null) {
				query = ofy().load().type(GAEObjectifyBillEntity.class)
						.ancestor(Key.create(GAEObjectifyUserEntity.class, specificEntity.getUser().getKey()));
			} else {
				query = ofy().load().type(GAEObjectifyBillEntity.class);
			}
			if (specificEntity.getShop() != null && specificEntity.getShop().getKey() != null) {
				Ref<GAEObjectifyShopEntity> shopReferenceToQuery = Ref
						.create(Key.create(GAEObjectifyShopEntity.class, specificEntity.getShop().getKey()));
				query = query.filter("shop", shopReferenceToQuery);
			}
			if (specificEntity.getDate() != null) {
				query = query.filter("date", specificEntity.getDate());
			}
			if (specificEntity.getAmount() != null) {
				query = query.filter("amount", specificEntity.getAmount());
			}
			List<GAEObjectifyBillEntity> billEntities = query.list();
			for (GAEObjectifyBillEntity billEntity : billEntities) {
				foundEntities.add(billEntity);
			}
		}
		return foundEntities;
	}

	@Override
	public GAEBillEntity updateEntity(GAEBillEntity entity) throws EntityPersistenceException {
		GAEObjectifyBillEntity specificEntity;
		try {
			specificEntity = (GAEObjectifyBillEntity) entity;
			ofy().save().entity(specificEntity).now();
		} catch (Exception e) {
			throw new EntityPersistenceException("Updating BillEntity " + entity.getKey() + " failed", e);
		}
		return specificEntity;
	}

	@Override
	public List<GAEBillEntity> getBillsForUser(User user) throws EntityRetrievalException {
		List<GAEBillEntity> foundEntities = new ArrayList<>();
		try {
			List<GAEObjectifyBillEntity> billEntities = ofy().load().type(GAEObjectifyBillEntity.class)
					.ancestor(Key.create(GAEObjectifyUserEntity.class, user.getId())).list();
			for (GAEObjectifyBillEntity billEntity : billEntities) {
				foundEntities.add(billEntity);
			}
		} catch (Exception e) {
			throw new EntityRetrievalException("Retrieving BillEntities for user " + user.getUserName() + " failed");
		}
		return foundEntities;
	}
}
