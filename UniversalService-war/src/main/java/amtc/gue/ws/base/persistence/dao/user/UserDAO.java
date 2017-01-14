package amtc.gue.ws.base.persistence.dao.user;

import java.util.List;

import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.persistence.dao.DAO;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;

/**
 * Specific interface for UserEntities
 * 
 * @author Thomas
 *
 */
public interface UserDAO extends DAO<GAEJPAUserEntity, String> {

	/**
	 * Retrieving UserEntities that have specific roles
	 * 
	 * @param roles
	 *            the roles the users should possess
	 * @return list of UserEntities possessing the roles
	 * @throws EntityRetrievalException
	 *             when issue while trying to retrieve UserEntities by role
	 */
	List<GAEJPAUserEntity> getUserEntitiesByRoles(Roles roles)
			throws EntityRetrievalException;
}
