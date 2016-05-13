package amtc.gue.ws.books.persistence.dao.book;

import java.util.List;

import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.dao.DAO;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.service.inout.Tags;

/**
 * Specific interface for BookEntities
 * 
 * @author Thomas
 *
 */
public interface BookDAO extends DAO<GAEJPABookEntity,String> {
	
	/**
	 * Finding BookEntities that possess a specific tag
	 * 
	 * @param tags the tags that are searched for
	 * @return list of bookentities posessing the tags
	 * @throws EntityRetrievalException 
	 */
	List<GAEJPABookEntity> getBookEntityByTag(Tags tags) throws EntityRetrievalException;
	
}
