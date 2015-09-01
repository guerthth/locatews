package amtc.gue.ws.books.delegate.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.delegate.persist.output.PersistenceDelegatorOutput;
import amtc.gue.ws.books.persistence.model.BookEntity;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.ErrorConstants;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

public class BookPersistenceDelegator extends AbstractPersistanceDelegator {

	// logger
	private static final Logger log = Logger.getLogger(BookPersistenceDelegator.class.getName());

	// PersistenceDelegatorOutput instance
	private IDelegatorOutput delegatorOutput;

	@Override
	/**
	 * delegate method persisting books to the underlying DB
	 */
	public IDelegatorOutput delegate() {

		// create the output object
		delegatorOutput = (PersistenceDelegatorOutput) SpringContext.context.getBean("persistenceDelegatorOutput");

		// determine type of persistence action
		if (persistenceInput.getType().equals(PersistenceTypeEnum.ADD)) {

			persistBook(); 
		} else if (persistenceInput.getType().equals(PersistenceTypeEnum.READ)) {

			retrieveBooksByTag();
		} else {
			setUnrecognizedInputDelegatorOutput();
		}

		return delegatorOutput;
	}

	/**
	 * Persist the bookentity
	 */
	private void persistBook() {
		
		log.info("ADD action triggered");

		// initialize delegatoroutput status
		delegatorOutput.setStatusCode(ErrorConstants.ADD_BOOK_SUCCESS_CODE);
		
		// transfor inputobejct to bookentities bookentities
		List<BookEntity> bookList = transformBooksToBookEntities(persistenceInput.getInputObject());

		// list of bookIds
		List<String> bookIds = new ArrayList<String>();

		// add every BookEntity to the DB
		for (BookEntity bookEntity : bookList) {
			BookEntity persistedBook;
			try {

				persistedBook = daoImpl.persistEntity(bookEntity);
				bookIds.add("" + persistedBook.getId() + "");
				log.info("Book added to DB with id: " + persistedBook.getId());

			} catch (EntityPersistenceException e) {
				log.severe("Error while trying to persist: '" + bookEntity.getTitle() + "'");
				delegatorOutput.setStatusCode(ErrorConstants.ADD_BOOK_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ErrorConstants.ADD_BOOK_FAILURE_MSG);
			}
		}

		// set delegatorOutput
		if(delegatorOutput.getStatusCode() == ErrorConstants.ADD_BOOK_SUCCESS_CODE){
			delegatorOutput.setStatusMessage(ErrorConstants.ADD_BOOK_SUCCESS_MSG + ": " + bookIds.toString());
			delegatorOutput.setOutputObject(persistenceInput.getInputObject());
		}
	}
	
	/**
	 * Retrieve BookEntities by tag
	 */
	private void retrieveBooksByTag() {
		// detemine dao action to be called by input object type
		if (persistenceInput.getInputObject() instanceof Tags) {

			log.info("READ by tag action triggered");

			Tags searchTags = (Tags) persistenceInput.getInputObject();

			// initialize delegatoroutput status
			delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_BOOK_SUCCESS_CODE);

			List<BookEntity> books = new ArrayList<BookEntity>();
			try {
				for (String tagName : searchTags.getTags()) {
					books.addAll(daoImpl.getBookEntityByTag(tagName));
				}

				String outputMessage = "Books retrieved for tag '" + persistenceInput.getInputObject() + "': " + books.toString();
				log.info(outputMessage);

				// set delegator output
				delegatorOutput.setStatusMessage(outputMessage);
				delegatorOutput.setOutputObject(books);
			} catch (EntityRetrievalException e) {
				log.severe("Error while trying to retrieve book with tag: '" + persistenceInput.getInputObject()
						+ "'");
				delegatorOutput.setStatusCode(ErrorConstants.RETRIEVE_BOOK_FAILURE_CODE);
			}
		} else {
			setUnrecognizedInputDelegatorOutput();
		}
	}

	/**
	 * Method returning a list of BookEntities based on a provided Books object
	 * 
	 * @param inputObject
	 *            the Books object provided as input
	 * @return list of BookEntities
	 */
	private List<BookEntity> transformBooksToBookEntities(Object inputObject) {

		// Books object that will be mapped to BookEntities
		Books books;
		// Booklist object
		List<BookEntity> bookEntityList = new ArrayList<BookEntity>();

		// check if the input object of the delegatorInput are a list of
		// BookEntities
		if (inputObject instanceof Books) {

			books = (Books) inputObject;
			bookEntityList = EntityMapper.transformBooksToBookEntities(books);
		} else {
			setUnrecognizedInputDelegatorOutput();
		}
		return bookEntityList;
	}
	
	/**
	 * Method setting the delegator output due to unrecognized
	 * input type
	 */
	private void setUnrecognizedInputDelegatorOutput() {
		log.severe(ErrorConstants.UNRECOGNIZED_PERSISTENCE_OBJECT_MSG);
		delegatorOutput.setStatusCode(ErrorConstants.UNRECOGNIZED_PERSISTENCE_OBJECT_CODE);
		delegatorOutput.setStatusMessage(ErrorConstants.UNRECOGNIZED_PERSISTENCE_OBJECT_MSG);
	}

}
