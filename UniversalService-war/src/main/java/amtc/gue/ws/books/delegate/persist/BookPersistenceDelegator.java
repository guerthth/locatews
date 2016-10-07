package amtc.gue.ws.books.delegate.persist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.exception.EntityPersistenceException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRemovalException;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.books.delegate.persist.output.PersistenceDelegatorOutput;
import amtc.gue.ws.books.persistence.dao.DAOs;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.service.inout.Books;
import amtc.gue.ws.books.service.inout.Tags;
import amtc.gue.ws.books.utils.BookPersistenceDelegatorUtils;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.ErrorConstants;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

/**
 * Persistence Delegator that
 * handles all database actions for Book resources
 * 
 * @author Thomas
 *
 */
public class BookPersistenceDelegator extends AbstractPersistenceDelegator {

	private static final Logger log = Logger
			.getLogger(BookPersistenceDelegator.class.getName());

	@Override
	public void initialize(PersistenceDelegatorInput input,
			DAOs daoImplementations) {
		persistenceInput = input;
		tagDAOImpl = daoImplementations.getTagDAO();
		daoImpl = daoImplementations.getBookDAO();
	}

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

			// transform inputobject to bookentities bookentities
			List<GAEJPABookEntity> bookEntityList = EntityMapper
					.transformBooksToBookEntities(books,
							PersistenceTypeEnum.ADD);

			// list of books
			List<GAEJPABookEntity> successfullyAddedBookEntities = new ArrayList<>();
			List<GAEJPABookEntity> unsuccessfullyAddedBookEntities = new ArrayList<>();

			// add every BookEntity to the DB
			for (GAEJPABookEntity bookEntity : bookEntityList) {
				// check for existing TagEntities. if not existing then add
				String bookEntityJSON;
				GAEJPABookEntity persistedBookEntity;
				try {
					handleTagPersistenceForBookEntity(bookEntity);
					persistedBookEntity = daoImpl.persistEntity(bookEntity);
					bookEntityJSON = EntityMapper
							.mapBookEntityToJSONString(persistedBookEntity);
					successfullyAddedBookEntities.add(persistedBookEntity);
					log.info(bookEntityJSON + " added to DB");
				} catch (EntityPersistenceException e) {
					bookEntityJSON = EntityMapper
							.mapBookEntityToJSONString(bookEntity);
					unsuccessfullyAddedBookEntities.add(bookEntity);
					log.log(Level.SEVERE, "Error while trying to persist: "
							+ bookEntityJSON, e);
				}
			}

			// set delegatorOutput
			if (!successfullyAddedBookEntities.isEmpty()) {
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
	 * This method adds TagEntities to the DB. The Entities are only added, if
	 * they are not existing so far.
	 * 
	 * @param bookEntity
	 *            the GAEJPABookEntity whose tag should be added
	 */
	private void handleTagPersistenceForBookEntity(GAEJPABookEntity bookEntity) {
		Set<GAEJPATagEntity> tagEntities = Collections
				.synchronizedSet(new HashSet<GAEJPATagEntity>(bookEntity
						.getTags()));
		String tagEntityJSON;
		GAEJPATagEntity persistedTagEntity;
		bookEntity.getTags().clear();

		synchronized (tagEntities) {
			for (GAEJPATagEntity tagEntity : tagEntities) {
				tagEntityJSON = EntityMapper
						.mapTagEntityToJSONString(tagEntity);
				try {
					tagEntity.getBooks().clear();
					List<GAEJPATagEntity> foundTagEntities = tagDAOImpl
							.findSpecificEntity(tagEntity);
					if (foundTagEntities.isEmpty()) {
						persistedTagEntity = tagDAOImpl
								.persistEntity(tagEntity);
					} else {
						persistedTagEntity = foundTagEntities.get(0);
						String foundKey = persistedTagEntity.getKey();
						persistedTagEntity = tagDAOImpl
								.findEntityById(foundKey);
					}
					bookEntity.addToTagsAndBooks(persistedTagEntity);
				} catch (EntityRetrievalException e) {
					log.log(Level.SEVERE, "Error while trying to retrieve tagEntity: "
							+ tagEntityJSON, e);
				} catch (EntityPersistenceException e) {
					log.log(Level.SEVERE, "Error while trying to persist tagEntity: "
							+ tagEntityJSON, e);
				}
			}
		}
	}

	/**
	 * Retrieve BookEntities by tag
	 */
	private void retrieveBooksByTag() {
		log.info("READ by tag action triggered");
		// determine dao action to be called by input object type
		if (persistenceInput.getInputObject() instanceof Tags) {

			Tags searchTags = (Tags) persistenceInput.getInputObject();

			// initialize delegatoroutput status
			delegatorOutput
					.setStatusCode(ErrorConstants.RETRIEVE_BOOK_SUCCESS_CODE);

			List<GAEJPABookEntity> foundBooks = new ArrayList<>();
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
				delegatorOutput
						.setStatusCode(ErrorConstants.RETRIEVE_BOOK_FAILURE_CODE);
				delegatorOutput.setOutputObject(null);
				log.log(Level.SEVERE, "Error while trying to retrieve book with tag: '"
						+ searchTags.getTags().toString() + "'", e);
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
			List<GAEJPABookEntity> removedBookEntities = new ArrayList<>();
			Books booksToRemove = (Books) persistenceInput.getInputObject();

			// initialize delegatoroutput status
			delegatorOutput
					.setStatusCode(ErrorConstants.DELETE_BOOK_SUCCESS_CODE);

			// transfor inputobejct to bookentities bookentities and remove
			List<GAEJPABookEntity> bookEntities = EntityMapper
					.transformBooksToBookEntities(booksToRemove,
							PersistenceTypeEnum.DELETE);
			for (GAEJPABookEntity bookEntity : bookEntities) {
				List<GAEJPABookEntity> bookEntitiesToRemove;
				String bookEntityJSON = EntityMapper
						.mapBookEntityToJSONString(bookEntity);
				try {
					if (bookEntity.getKey() != null) {
						bookEntitiesToRemove = new ArrayList<>();
						bookEntitiesToRemove.add(daoImpl
								.findEntityById(bookEntity.getKey()));
					} else {
						bookEntitiesToRemove = daoImpl
								.findSpecificEntity(bookEntity);
					}

					if (bookEntitiesToRemove != null
							&& !bookEntitiesToRemove.isEmpty()) {
						for (GAEJPABookEntity bookEntityToRemove : bookEntitiesToRemove) {
							String bookEntityToRemoveJSON = EntityMapper
									.mapBookEntityToJSONString(bookEntityToRemove);
							try {
								GAEJPABookEntity removedBookEntity = daoImpl
										.removeEntity(bookEntityToRemove);
								log.info("BookEntity " + bookEntityToRemoveJSON
										+ " was successfully removed");
								removedBookEntity.setTags(
										bookEntityToRemove.getTags(), false);
								removedBookEntities.add(removedBookEntity);
							} catch (EntityRemovalException e) {
								log.log(Level.SEVERE,"Error while trying to remove: "
										+ bookEntityToRemoveJSON,e);
							}
						}
					} else {
						log.warning(bookEntityJSON + " was not found.");
					}
				} catch (EntityRetrievalException e) {
					log.log(Level.SEVERE, "Error while trying to retrieve: "
							+ bookEntityJSON, e);
				}
			}

			// set delegator output
			if (!removedBookEntities.isEmpty()) {
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
