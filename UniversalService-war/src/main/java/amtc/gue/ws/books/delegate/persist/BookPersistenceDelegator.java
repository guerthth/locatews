package amtc.gue.ws.books.delegate.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import amtc.gue.ws.books.dao.IBookEntityDAO;
import amtc.gue.ws.books.dao.impl.BookEntityDAOImpl;
import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.delegate.persist.output.PersistenceDelegatorOutput;
import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.ErrorConstants;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

public class BookPersistenceDelegator extends AbstractPersistanceDelegator{

	// logger
	private static final Logger log = Logger.getLogger(
			BookPersistenceDelegator.class.getName());
	
	// BookEntityDAOImpl instance
	private IBookEntityDAO bookEntityDAO;
	
	// PersistenceDelegatorOutput instance
	private IDelegatorOutput delegatorOutput;
	
	@Override
	/**
	 * delegate method persisting books to the underlying DB
	 */
	public IDelegatorOutput delegate() {
		
		// create DAO instance
		bookEntityDAO = (BookEntityDAOImpl) SpringContext.context.getBean("bookEntityDAOImpl");
		
		// create the output object
		delegatorOutput = (PersistenceDelegatorOutput) SpringContext.context.getBean("persistenceDelegatorOutput");
		
		// determine type of persistence action
		if(persistenceInput.getType().equals(PersistenceTypeEnum.ADD)){
			
			log.info("ADD action triggered");
	
			// transfor inputobejct to bookentities bookentities
			List<BookEntity> bookList = transformBookEntities(persistenceInput.getInputObject());
			
			// list of bookIds
			List<String> bookIds = new ArrayList<String>();

			// initialize delegatoroutput status
			delegatorOutput.setStatusCode(ErrorConstants.ADD_BOOK_SUCCESS_CODE);
			
			// add every BookEntity to the DB
			for(BookEntity bookEntity : bookList){
				BookEntity persistedBook;
				try {
					
					persistedBook = bookEntityDAO.addBookEntity(bookEntity);
					bookIds.add("" + persistedBook.getId() + "");
					log.info("Book added to DB with id: " + persistedBook.getId());
					
				} catch (EntityPersistenceException e) {
					log.severe("Error while trying to persist: '" + bookEntity.getTitle() + "'");
					delegatorOutput.setStatusCode(ErrorConstants.ADD_BOOK_FAILURE_CODE);
				}
			}
			
			// set delegatorOutput
			delegatorOutput.setStatusMessage(ErrorConstants.ADD_BOOK_SUCCESS_MSG + ": " + bookIds.toString());
			
		} else if (persistenceInput.getType().equals(PersistenceTypeEnum.READ)){
			
			// detemine dao action to be called by input object type
			if(persistenceInput.getInputObject() instanceof String){
				
				log.info("READ by tagname action triggered");
				
				// initialize delegatoroutput status
				delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_BOOK_SUCCESS_CODE);
				
				List<BookEntity> books = new ArrayList<BookEntity>();
				try {
					books = bookEntityDAO.getBookEntityByTag((String) persistenceInput.getInputObject());
				} catch (EntityRetrievalException e) {
					log.severe("Error while trying to retrieve book with tag: '" + persistenceInput.getInputObject() + "'");
					delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_BOOK_FAILURE_CODE);
				}
				
				log.info("Books retrieved for tag '" + persistenceInput.getInputObject() + "': " + books.toString());
				
				// set delegator output 
				delegatorOutput.setStatusMessage("Books retrieved for tag '" + persistenceInput.getInputObject() + "': " + books.toString());
				delegatorOutput.setOutputObject(books.toString());
				
			} else if (persistenceInput.getInputObject() instanceof Long) {
				
				log.info("READ by ID action triggered");
			
			} else {
				
				log.severe("unknow READ action");
			}
			
		}
		
		// TODO: build output object and return
		
		return delegatorOutput;
	}
	
	/**
	 * Method returning a list of BookEntities based on a provided Books object
	 * @param inputObject the Books object provided as input
	 * @return list of BookEntities
	 */
	private List<BookEntity> transformBookEntities(Object inputObject){
		
		//Books object that will be mapped to BookEntities
		Books books;
		// Booklist object 
		List<BookEntity> bookList = new ArrayList<BookEntity> ();
		
		// check if the input object of the delegatorInput are a list of BookEntities
		if(inputObject instanceof Books){
				
			books = (Books) inputObject;
			bookList = EntityMapper.mapBooksToBookList(books, bookList);
		}
		return bookList;
	}

}
