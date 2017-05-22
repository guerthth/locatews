package amtc.gue.ws.base.persistence.dao.user;

import java.util.List;

import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.persistence.dao.DAO;

/**
 * Specific interface for UserEntities
 * 
 * @author Thomas
 *
 */
public interface UserDAO<S, E extends S, K> extends DAO<S, E, K> {

	/**
	 * Retrieving UserEntities that have specific roles
	 * 
	 * @param roles
	 *            the roles the users should possess
	 * @return list of UserEntities possessing the roles
	 * @throws EntityRetrievalException
	 *             when issue while trying to retrieve UserEntities by role
	 */
	List<S> getUserEntitiesByRoles(Roles roles)
			throws EntityRetrievalException;
}
