package amtc.gue.ws.books.persistence.dao.book;

import java.util.List;

import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.dao.DAO;
import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.service.inout.Tags;

/**
 * Specific interface for BookEntities
 * 
 * @author Thomas
 *
 */
public interface BookDAO extends DAO<BookEntity,Long> {
	
	/**
	 * Finding BookEntities that posess a specific tag
	 * 
	 * @param tag the tag that is searhed for
	 * @return list of bookentities posessing the tag
	 * @throws EntityRetrievalException 
	 */
	List<BookEntity> getBookEntityByTag(Tags tags) throws EntityRetrievalException;
}
