package amtc.gue.ws.books.persistence.dao.book;

import java.util.List;

import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.dao.DAO;
import amtc.gue.ws.books.inout.Tags;

/**
 * Specific interface for BookEntities
 * 
 * @author Thomas
 *
 */
public interface BookDAO<S, E extends S, K> extends DAO<S, E, K> {

	/**
	 * Finding BookEntities that possess a specific tag
	 * 
	 * @param tags
	 *            the tags that are searched for
	 * @return list of bookentities posessing the tags
	 * @throws EntityRetrievalException
	 */
	List<S> getBookEntityByTag(Tags tags) throws EntityRetrievalException;

	/**
	 * Finding all BookEntities for a specific user
	 * 
	 * @return all BookEntities of a specific user
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve entities
	 */
	List<S> getBookEntityForUserByTag(Tags tags) throws EntityRetrievalException;

	/**
	 * Finding a specific BookEntity for a specific user
	 * 
	 * @param bookEntity
	 *            the BookEntity that should be retrieved
	 * @return all bookentities matching the search criteria
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve entities
	 */
	List<S> findSpecificBookEntityForUser(S bookEntity) throws EntityRetrievalException;
}
