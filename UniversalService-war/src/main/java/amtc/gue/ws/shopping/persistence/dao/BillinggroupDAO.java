package amtc.gue.ws.shopping.persistence.dao;

import java.util.List;

import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.dao.DAO;

/**
 * Specific interface for BillinggroupEntities
 * 
 * @author Thomas
 *
 * @param <S>
 *            Entity Superclass
 * @param <E>
 *            Entity Class
 * @param <K>
 *            Type of the key
 */
public interface BillinggroupDAO<S, E extends S, K> extends DAO<S, E, K> {

	/**
	 * Method retrieving all billinggroups for a specific user
	 * @param userId the user for whom the billinggroups should be removed
	 * @return the found billinggroups of the user
	 * @throws EntityRetrievalException if an issue occurs during entity retrieval
	 */
	public List<S> findAllBillinggroupsForUser(String userId) throws EntityRetrievalException;
}
