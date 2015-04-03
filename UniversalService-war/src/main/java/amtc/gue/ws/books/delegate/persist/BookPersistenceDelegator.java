package amtc.gue.ws.books.delegate.persist;

import java.util.List;
import java.util.logging.Logger;

import amtc.gue.ws.books.dao.impl.BookEntityDAOImpl;
import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.output.IDelegatorOutput;
import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

public class BookPersistenceDelegator extends AbstractPersistanceDelegator{

	// logger
	private static final Logger log = Logger.getLogger(
			BookPersistenceDelegator.class.getName());
	
	// BookEntityDAOImpl instance
	// TODO: Spring inprovement
	private BookEntityDAOImpl bookEntityDAO;
	
	@Override
	/**
	 * delegate method persisting books to the underlying DB
	 */
	public IDelegatorOutput delegate() throws EntityPersistenceException {
		
		// create DAO instance
		bookEntityDAO = (BookEntityDAOImpl) SpringContext.context.getBean("bookEntityDAOImpl");
		
		// determine type of persistence action
		if(persistenceInput.getType().equals(PersistenceTypeEnum.ADD)){
			
			log.info("ADD action triggered");
	
			// retrieve bookentities
			List<BookEntity> bookList = getBookEntities(persistenceInput.getInputObject());

			// add every BookEntity to the DB
			for(BookEntity bookEntity : bookList){
				BookEntity persistedBook = bookEntityDAO.addBookEntity(bookEntity);
				log.info("Book added to DB with id: " + persistedBook.getId());
			}
		}
		
		// TODO: build output object and return
		
		return null;
	}
	
	/**
	 * Method returning a list of BookEntities based on a provided Books object
	 * @param inputObject the Books object provided as input
	 * @return list of BookEntities
	 */
	private List<BookEntity> getBookEntities(Object inputObject){
		
		//Books object that will be mapped to BookEntities
		Books books;
		// Booklist object 
		List<BookEntity> bookList = null;
		
		// check if the input object of the delegatorInput are a list of BookEntities
		if(inputObject instanceof Books){
				
			books = (Books) persistenceInput.getInputObject();
			bookList = EntityMapper.mapBooksToBookList(books);
		}
		return bookList;
	}

}
