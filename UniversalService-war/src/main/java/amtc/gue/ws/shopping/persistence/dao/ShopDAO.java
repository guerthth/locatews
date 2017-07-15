package amtc.gue.ws.shopping.persistence.dao;

import amtc.gue.ws.base.persistence.dao.DAO;

/**
 * Specific interface for ShopEntities
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
public interface ShopDAO<S, E extends S, K> extends DAO<S, E, K> {
	// no specific implementations yet
}
