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
import amtc.gue.ws.base.persistence.dao.ObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.model.role.objectify.GAEObjectifyRoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.shopping.persistence.dao.BillinggroupDAO;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillinggroupEntity;

/**
 * Implementation for the Objectify BillinggroupDAO
 * 
 * @author Thomas
 *
 */
public class BillinggroupObjectifyDAOImpl
		extends ObjectifyDAOImpl<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String>
		implements BillinggroupDAO<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String> {
	static {
		ObjectifyService.register(GAEObjectifyUserEntity.class);
		ObjectifyService.register(GAEObjectifyRoleEntity.class);
		ObjectifyService.register(GAEObjectifyBillEntity.class);
		ObjectifyService.register(GAEObjectifyBillinggroupEntity.class);
	}

	@Override
	public List<GAEBillinggroupEntity> findSpecificEntity(GAEBillinggroupEntity entity)
			throws EntityRetrievalException {
		GAEObjectifyBillinggroupEntity specificEntity = (GAEObjectifyBillinggroupEntity) entity;
		List<GAEBillinggroupEntity> foundEntities = new ArrayList<>();
		if(specificEntity != null){
			if (specificEntity.getKey() != null) {
				// if entity has an ID, search by ID
				GAEObjectifyBillinggroupEntity foundEntity = (GAEObjectifyBillinggroupEntity) ofy().load()
						.entity(specificEntity).now();
				if (foundEntity != null) {
					foundEntities.add(foundEntity);
				}
			} else {
				// if not, search for similar attributes
				Query<GAEObjectifyBillinggroupEntity> query = ofy().load().type(GAEObjectifyBillinggroupEntity.class);
				if (specificEntity.getUsers() != null) {
					for (GAEUserEntity user : specificEntity.getUsers()) {
						Ref<GAEObjectifyUserEntity> userReferenceToQuery = Ref
								.create(Key.create(GAEObjectifyUserEntity.class, user.getKey()));
						query = query.filter("users", userReferenceToQuery);
					}
				}
				List<GAEObjectifyBillinggroupEntity> billinggroupEntities = query.list();
				for (GAEObjectifyBillinggroupEntity billinggroupEntity : billinggroupEntities) {
					foundEntities.add(billinggroupEntity);
				}
			}
		}	
		return foundEntities;
	}

	@Override
	public GAEBillinggroupEntity updateEntity(GAEBillinggroupEntity entity) throws EntityPersistenceException {
		GAEObjectifyBillinggroupEntity specificEntity;
		try {
			specificEntity = (GAEObjectifyBillinggroupEntity) entity;
			ofy().save().entity(specificEntity).now();
		} catch (Exception e) {
			throw new EntityPersistenceException("Updating BillinggroupEntity " + entity.getKey() + " failed", e);
		}
		return specificEntity;
	}

	@Override
	public List<GAEBillinggroupEntity> findAllBillinggroupsForUser(String userId) throws EntityRetrievalException {
		List<GAEBillinggroupEntity> foundEntities = new ArrayList<>();
		try{
			Query<GAEObjectifyBillinggroupEntity> query = 
					ofy().load().type(GAEObjectifyBillinggroupEntity.class);
			Ref<GAEObjectifyUserEntity> userReferenceToQuery = Ref
					.create(Key.create(userId));
			query = query.filter("users", userReferenceToQuery);
			
			List<GAEObjectifyBillinggroupEntity> billinggroupEntities = query.list();
			for (GAEObjectifyBillinggroupEntity billinggroupEntity : billinggroupEntities) {
				foundEntities.add(billinggroupEntity);
			}
			
		} catch(Exception e){
			throw new EntityRetrievalException("Retrieving BillinggroupEntities for user'" + userId + "' failed",e);
		}
		
		return foundEntities;
	}
	
	
}
