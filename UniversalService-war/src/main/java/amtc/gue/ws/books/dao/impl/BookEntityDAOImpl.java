package amtc.gue.ws.books.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import amtc.gue.ws.books.dao.IBookEntityDAO;
import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.persistence.ProductiveEMF;
import amtc.gue.ws.books.persistence.model.BookEntity;

/**
 * <p>Implementation of the BookEntityDAO</p>
 * 
 * <p>
 * Inludes DB handling operations like:
 * <ul>
 * 	<li>create</li>
 * 	<li>read</li>
 * 	<li>update</li>
 * 	<li>delete</li>
 * </ul>
 * </p>
 * 
 * @author Thomas
 *
 */
public class BookEntityDAOImpl implements IBookEntityDAO {

	/** EntityManagerFactory instance */
	private EntityManagerFactory emf;
	
	/** EntityManager instance */
	private EntityManager em; 
	
	/** BookEntitySelection QueryString */
	private static final String BOOKENTITY_SELECTION_QUERY = 
			"select be from BookEntity be";
	
	/** BookEntity Selection Query for tag selection */
	private static final String BOOKENTITY_TAG_SELECTION_QUERY = 
			"select DISTINCT be from BookEntity be WHERE be.tags LIKE '%?1%'";
	
	/**
	 * Constructor 
	 * 
	 * @param emf EntityManagerFactory
	 * @param input input from the DelegatorInput
	 */
	public BookEntityDAOImpl(ProductiveEMF emfInstance) {

		this.emf = emfInstance.getEntityManagerFactory();
		
	}
	
	@Override
	public List<BookEntity> getAllBookEntities() {
		
		// set entitymanager
		em = emf.createEntityManager();
		
		// read the existing BookEntities from the DB
		Query q = em.createQuery(BOOKENTITY_SELECTION_QUERY,BookEntity.class);
		@SuppressWarnings("unchecked")
		List<BookEntity> books = q.getResultList();
		
		// close entitymanager
		em.close();
		
		return books;
	}

	@Override
	public BookEntity getBookEntity(String ISBN) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BookEntity> getBookEntityByTag(String tag) throws EntityRetrievalException {

		List<BookEntity> books = new ArrayList<BookEntity>();
		
		try{
			books = new ArrayList<BookEntity>();
			
			// set entitymanager
			em = emf.createEntityManager();
			
			// read bookentites from DB that possess specific tags
			Query q = em.createQuery(BOOKENTITY_TAG_SELECTION_QUERY, BookEntity.class).setParameter(1, tag);
			books = q.getResultList();
			
		} catch (Exception e){
			
			throw new EntityRetrievalException("Retrieval of entity for tag: " + tag + " failed.", e);
			
		} finally {
			em.close();
		}
		
		return books;
	}


	@Override
	public BookEntity updateBookEntity(BookEntity book) {
		
		// set entitymanager
		em = emf.createEntityManager();
		
		// begin transaction
		em.getTransaction().begin();
		
		// find existing bookentity
		BookEntity updatedBookEntity = em.find(BookEntity.class, book.getId());
		
		// return the updated BookEntity
		return updatedBookEntity;

	}

	@Override
	public void deleteBookEntity(BookEntity book) {
		// TODO Auto-generated method stub

	}

	@Override
	public BookEntity addBookEntity(BookEntity book) throws EntityPersistenceException {
		
		try{
			// set entitymanager
			em = emf.createEntityManager();
			
			// begin transaction
			em.getTransaction().begin();
			
			// perists in DB
			em.persist(book);
			
			// commit transaction
			em.getTransaction().commit();
			
			return book;
			
		} catch(PersistenceException e) {
			
			// rollback
			em.getTransaction().rollback();
			throw new EntityPersistenceException("Persisting BookEntity for ISBN " + book.getISBN() +
					" failed.", e);
			
		} finally {
			
			// close entitymanager
			em.close();
		}
		
	}

}
