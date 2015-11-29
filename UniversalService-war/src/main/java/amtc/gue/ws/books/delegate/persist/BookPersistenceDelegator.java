package amtc.gue.ws.books.delegate.persist;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRemovalException;
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

	private static final Logger log = Logger
			.getLogger(BookPersistenceDelegator.class.getName());

	// PersistenceDelegatorOutput instance
	private IDelegatorOutput delegatorOutput;

	@Override
	/**
	 * delegate method persisting books to the underlying DB
	 */
	public IDelegatorOutput delegate() {
		// create the output object
		delegatorOutput = (PersistenceDelegatorOutput) SpringContext.context
				.getBean("persistenceDelegatorOutput");
		// determine type of persistence action
		if (persistenceInput.getType().equals(PersistenceTypeEnum.ADD)) {
			persistBook();
		} else if (persistenceInput.getType()
				.equals(PersistenceTypeEnum.DELETE)) {
			removeBook();
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

		if (persistenceInput.getInputObject() instanceof Books) {
			Books books = (Books) persistenceInput.getInputObject();
			// initialize delegatoroutput status
			delegatorOutput.setStatusCode(ErrorConstants.ADD_BOOK_SUCCESS_CODE);
			// transfor inputobejct to bookentities bookentities
			List<BookEntity> bookList = EntityMapper
					.transformBooksToBookEntities(books);

			// list of bookIds
			List<String> bookIds = new ArrayList<String>();

			// add every BookEntity to the DB
			for (BookEntity bookEntity : bookList) {
				BookEntity persistedBook;
				try {
					persistedBook = daoImpl.persistEntity(bookEntity);
					bookIds.add("" + persistedBook.getId() + "");
					log.info("Book added to DB with id: "
							+ persistedBook.getId());
				} catch (EntityPersistenceException e) {
					log.severe("Error while trying to persist: '"
							+ bookEntity.getTitle() + "'");
					delegatorOutput
							.setStatusCode(ErrorConstants.ADD_BOOK_FAILURE_CODE);
					delegatorOutput
							.setStatusMessage(ErrorConstants.ADD_BOOK_FAILURE_MSG);
				}

				// set delegatorOutput
				if (delegatorOutput.getStatusCode() == ErrorConstants.ADD_BOOK_SUCCESS_CODE) {
					delegatorOutput
							.setStatusMessage(ErrorConstants.ADD_BOOK_SUCCESS_MSG
									+ ": " + bookIds.toString());
					delegatorOutput.setOutputObject(persistenceInput
							.getInputObject());
				}
			}
		} else {
			setUnrecognizedInputDelegatorOutput();
		}
	}

	/**
	 * Retrieve BookEntities by tag
	 */
	private void retrieveBooksByTag() {
		log.info("READ by tag action triggered");
		// detemine dao action to be called by input object type
		if (persistenceInput.getInputObject() instanceof Tags) {

			Tags searchTags = (Tags) persistenceInput.getInputObject();

			// initialize delegatoroutput status
			delegatorOutput
					.setStatusCode(ErrorConstants.RETRIEVE_BOOK_SUCCESS_CODE);

			List<BookEntity> books = new ArrayList<BookEntity>();
			try {
				books = daoImpl.getBookEntityByTag(searchTags);

				String outputMessage = "Books retrieved for tag '"
						+ persistenceInput.getInputObject() + "': "
						+ books.toString() + ". " + books.size()
						+ " Entities were found.";
				log.info(outputMessage);

				// set delegator output
				delegatorOutput.setStatusMessage(outputMessage);
				delegatorOutput.setOutputObject(EntityMapper
						.transformBookEntitiesToBooks(books));
			} catch (EntityRetrievalException e) {
				log.severe("Error while trying to retrieve book with tag: '"
						+ persistenceInput.getInputObject() + "'");
				delegatorOutput
						.setStatusCode(ErrorConstants.RETRIEVE_BOOK_FAILURE_CODE);
			}
		} else {
			setUnrecognizedInputDelegatorOutput();
		}
	}

	/**
	 * Remove book from the Book Store
	 */
	private void removeBook() {
		log.info("DELETE action triggered");

		// check input object
		if (persistenceInput.getInputObject() instanceof Books) {
			List<String> removedBookEntitiesJSON = new ArrayList<String>();
			Books booksToRemove = (Books) persistenceInput.getInputObject();

			// initialize delegatoroutput status
			delegatorOutput
					.setStatusCode(ErrorConstants.DELETE_BOOK_SUCCESS_CODE);

			// transfor inputobejct to bookentities bookentities and remove
			List<BookEntity> bookEntities = EntityMapper
					.transformBooksToBookEntities(booksToRemove);
			for (BookEntity bookEntity : bookEntities) {
				List<BookEntity> bookEntitiesToRemove;
				String bookEntityJSON = EntityMapper
						.mapBookEntityToJSONString(bookEntity);
				try {
					bookEntitiesToRemove = daoImpl
							.findSpecificEntity(bookEntity);
					if (bookEntitiesToRemove != null
							&& bookEntitiesToRemove.size() != 0) {
						for (BookEntity bookEntityToRemove : bookEntitiesToRemove) {
							String bookEntityToRemoveJSON = EntityMapper
									.mapBookEntityToJSONString(bookEntityToRemove);
							try {
								daoImpl.removeEntity(bookEntityToRemove);
								log.info("BookEntity "
										+ bookEntityToRemoveJSON
										+ " was successfully removed");
								removedBookEntitiesJSON.add(bookEntityToRemoveJSON);
							} catch (EntityRemovalException e) {
								log.severe("Error while trying to remove: "
										+ bookEntityToRemoveJSON);
							}
						}
					} else {
						log.warning(bookEntityJSON
								+ " was not found.");
					}
				} catch (EntityRetrievalException e1) {
					log.severe("Error while trying to retrieve: "
							+ bookEntityJSON);
				}
			}
			
			if(removedBookEntitiesJSON.size() > 0){
				// TODO: Continue
				delegatorOutput.setStatusMessage(ErrorConstants.DELETE_BOOK_SUCCESS_MSG );
			}else {
				delegatorOutput.setStatusCode(ErrorConstants.DELETE_BOOK_FAILURE_CODE);
				delegatorOutput.setStatusMessage(ErrorConstants.DELETE_BOOK_FAILURE_MSG);
			}
		} else {
			setUnrecognizedInputDelegatorOutput();
		}
	}

	/**
	 * Method setting the delegator output due to unrecognized input type
	 */
	private void setUnrecognizedInputDelegatorOutput() {
		log.severe(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG);
		delegatorOutput
				.setStatusCode(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_CODE);
		delegatorOutput
				.setStatusMessage(ErrorConstants.UNRECOGNIZED_INPUT_OBJECT_MSG);
	}
}
