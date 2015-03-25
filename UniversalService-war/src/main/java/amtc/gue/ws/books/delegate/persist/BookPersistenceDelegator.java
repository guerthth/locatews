package amtc.gue.ws.books.delegate.persist;

import java.util.logging.Logger;

import javax.persistence.EntityManager;

import amtc.gue.ws.books.inout.Book;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.utils.EntityMapper;

public class BookPersistenceDelegator extends AbstractPersistanceDelegator{

	// logger
	private static final Logger log = Logger.getLogger(
			BookPersistenceDelegator.class.getName());
	
	// EntityManager
	private EntityManager em;
	
	@Override
	/**
	 * delegate method persisting books to the underlying DB
	 */
	public void delegate() {
		
		// get entityManager
		em = emf.createEntityManager();
		em.getTransaction().begin();
		// persist the books
		if(obj instanceof Books){
			
			Books books = (Books) obj;
			for(Book book : books.getBooks()){
				em.persist(EntityMapper.mapBookToEntity(book));
				log.info( "'" + book.getTitle() + "' persisted in DB");
			}
		}
		
		// commiting the transaction
		em.getTransaction().commit();
		
		// close the entitymanager
		em.close();
		
	}

}
