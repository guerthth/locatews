package amtc.gue.ws.shopping.persistence.dao;

import java.util.List;

import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.persistence.dao.DAO;

/**
 * Specific interface for BillEntities
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
public interface BillDAO<S, E extends S, K> extends DAO<S, E, K> {

	/**
	 * Finding Bills for specific User
	 * 
	 * @param user
	 *            the User instance
	 * @return all bills for the user
	 * @throws EntityRetrievalException
	 *             when issue occurs trying to retrieve entity
	 */
	List<S> getBillsForUser(User user) throws EntityRetrievalException;
}
