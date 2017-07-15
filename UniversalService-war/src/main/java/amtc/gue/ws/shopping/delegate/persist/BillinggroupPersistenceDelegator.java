package amtc.gue.ws.shopping.delegate.persist;

import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.shopping.persistence.dao.BillinggroupDAO;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;

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

	@Override
	protected void persistEntities() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void removeEntities() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void retrieveEntities() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void updateEntities() {
		// TODO Auto-generated method stub

	}

}
