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
import amtc.gue.ws.books.utils.BookPersistenceDelegatorUtils;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.ErrorConstants;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

public class BookPersistenceDelegator extends AbstractPersistenceDelegator {

	private static final Logger log = Logger
			.getLogger(BookPersistenceDelegator.class.getName());

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

			// transfor inputobject to bookentities bookentities
			List<BookEntity> bookList = EntityMapper
					.transformBooksToBookEntities(books,
							PersistenceTypeEnum.ADD);

			// list of books
			List<BookEntity> successfullyAddedBookEntities = new ArrayList<BookEntity>();
			List<BookEntity> unsuccessfullyAddedBookEntities = new ArrayList<BookEntity>();

			// add every BookEntity to the DB
			for (BookEntity bookEntity : bookList) {
				String bookEntityJSON;
				BookEntity persistedBook;
				try {
					persistedBook = daoImpl.persistEntity(bookEntity);
					bookEntityJSON = EntityMapper
							.mapBookEntityToJSONString(persistedBook);
					successfullyAddedBookEntities.add(persistedBook);
					log.info(bookEntityJSON + " added to DB");
				} catch (EntityPersistenceException e) {
					bookEntityJSON = EntityMapper
							.mapBookEntityToJSONString(bookEntity);
					unsuccessfullyAddedBookEntities.add(bookEntity);
					log.severe("Error while trying to persist: "
							+ bookEntityJSON);
				}
			}

			// set delegatorOutput
			if (successfullyAddedBookEntities.size() > 0) {
				delegatorOutput.setStatusMessage(BookPersistenceDelegatorUtils
						.buildPersistBookSuccessStatusMessage(
								successfullyAddedBookEntities,
								unsuccessfullyAddedBookEntities));
				delegatorOutput
						.setOutputObject(EntityMapper
								.transformBookEntitiesToBooks(successfullyAddedBookEntities));
			} else {
				delegatorOutput
						.setStatusCode(ErrorConstants.ADD_BOOK_FAILURE_CODE);
				delegatorOutput
						.setStatusMessage(ErrorConstants.ADD_BOOK_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
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

			List<BookEntity> foundBooks = new ArrayList<BookEntity>();
			try {
				foundBooks = daoImpl.getBookEntityByTag(searchTags);

				String statusMessage = BookPersistenceDelegatorUtils
						.buildGetBooksByTagSuccessStatusMessage(searchTags,
								foundBooks);
				log.info(statusMessage);

				// set delegator output
				delegatorOutput.setStatusMessage(statusMessage);
				delegatorOutput.setOutputObject(EntityMapper
						.transformBookEntitiesToBooks(foundBooks));
			} catch (EntityRetrievalException e) {
				log.severe("Error while trying to retrieve book with tag: '"
						+ searchTags.getTags().toString() + "'");
				delegatorOutput
						.setStatusCode(ErrorConstants.RETRIEVE_BOOK_FAILURE_CODE);
				delegatorOutput.setOutputObject(null);
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
			List<BookEntity> removedBookEntities = new ArrayList<BookEntity>();
			Books booksToRemove = (Books) persistenceInput.getInputObject();

			// initialize delegatoroutput status
			delegatorOutput
					.setStatusCode(ErrorConstants.DELETE_BOOK_SUCCESS_CODE);

			// transfor inputobejct to bookentities bookentities and remove
			List<BookEntity> bookEntities = EntityMapper
					.transformBooksToBookEntities(booksToRemove,
							PersistenceTypeEnum.DELETE);
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
								BookEntity removedBookEntity = daoImpl
										.removeEntity(bookEntityToRemove);
								log.info("BookEntity " + bookEntityToRemoveJSON
										+ " was successfully removed");
								removedBookEntities.add(removedBookEntity);
							} catch (EntityRemovalException e) {
								log.severe("Error while trying to remove: "
										+ bookEntityToRemoveJSON);
							}
						}
					} else {
						log.warning(bookEntityJSON + " was not found.");
					}
				} catch (EntityRetrievalException e1) {
					log.severe("Error while trying to retrieve: "
							+ bookEntityJSON);
				}
			}

			// set delegator output
			if (removedBookEntities.size() > 0) {
				delegatorOutput
						.setStatusMessage(BookPersistenceDelegatorUtils
								.buildRemoveBooksSuccessStatusMessage(removedBookEntities));
				delegatorOutput.setOutputObject(EntityMapper
						.transformBookEntitiesToBooks(removedBookEntities));
			} else {
				delegatorOutput
						.setStatusCode(ErrorConstants.DELETE_BOOK_FAILURE_CODE);
				delegatorOutput
						.setStatusMessage(ErrorConstants.DELETE_BOOK_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedInputDelegatorOutput();
		}
	}
}
