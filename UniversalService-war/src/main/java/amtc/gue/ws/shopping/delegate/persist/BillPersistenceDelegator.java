package amtc.gue.ws.shopping.delegate.persist;

import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.shopping.persistence.dao.BillDAO;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.tournament.delegate.persist.PlayerPersistenceDelegator;
import amtc.gue.ws.tournament.persistence.dao.player.PlayerDAO;
import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;
import amtc.gue.ws.tournament.persistence.model.player.jpa.GAEJPAPlayerEntity;
import amtc.gue.ws.tournament.util.mapper.TournamentServiceEntityMapper;

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
