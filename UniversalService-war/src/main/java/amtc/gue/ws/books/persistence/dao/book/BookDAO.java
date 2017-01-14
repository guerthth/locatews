package amtc.gue.ws.books.persistence.dao.book;

import java.util.List;

import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.dao.DAO;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;

/**
 * Specific interface for BookEntities
 * 
 * @author Thomas
 *
 */
public interface BookDAO extends DAO<GAEJPABookEntity, String> {

	/**
	 * Finding BookEntities that possess a specific tag
	 * 
	 * @param tags
	 *            the tags that are searched for
	 * @return list of bookentities posessing the tags
	 * @throws EntityRetrievalException
	 */
	List<GAEJPABookEntity> getBookEntityByTag(Tags tags)
			throws EntityRetrievalException;

	/**
	 * Finding all BookEntities for a specific user
	 * 
	 * @return all BookEntities of a specific user
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve entities
	 */
	List<GAEJPABookEntity> findAllBookEntitiesForUser()
			throws EntityRetrievalException;

	/**
	 * Finding a specific BookEntity for a specific user
	 * 
	 * @param bookEntity
	 *            the BookEntity that should be retrieved
	 * @return all bookentities matching the search criteria
	 * @throws EntityRetrievalException
	 *             when issue occurs while trying to retrieve entities
	 */
	List<GAEJPABookEntity> findSpecificBookEntityForUser(
			GAEJPABookEntity bookEntity) throws EntityRetrievalException;
}
