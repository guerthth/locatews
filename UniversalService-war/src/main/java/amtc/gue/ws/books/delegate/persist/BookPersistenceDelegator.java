package amtc.gue.ws.books.delegate.persist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.input.IDelegatorInput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.base.util.UserServiceEntityMapper;
import amtc.gue.ws.books.inout.Books;
import amtc.gue.ws.books.inout.Tags;
import amtc.gue.ws.books.persistence.dao.book.BookDAO;
import amtc.gue.ws.books.persistence.dao.book.impl.BookDAOImpl;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.dao.tag.impl.TagDAOImpl;
import amtc.gue.ws.books.persistence.model.GAEJPABookEntity;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.util.BookPersistenceDelegatorUtils;
import amtc.gue.ws.books.util.BookServiceEntityMapper;
import amtc.gue.ws.books.util.BookServiceErrorConstants;

/**
 * Persistence Delegator that handles all database actions for Book resources
 * 
 * @author Thomas
 *
 */
public class BookPersistenceDelegator extends AbstractPersistenceDelegator {

	private static final Logger log = Logger
			.getLogger(BookPersistenceDelegator.class.getName());

	/** DAOImplementations used by the delegator */
	private BookDAO bookDAOImpl;
	private TagDAO tagDAOImpl;

	@Override
	public void initialize(IDelegatorInput input) {
		super.initialize(input);
		tagDAOImpl = (TagDAOImpl) SpringContext.context.getBean("tagDAOImpl");
		bookDAOImpl = (BookDAOImpl) SpringContext.context
				.getBean("bookDAOImpl");
	}

	@Override
	protected void persistEntities() {
		log.info("ADD Book action triggered");
		if (delegatorInput.getInputObject() instanceof Books) {
			Books books = (Books) delegatorInput.getInputObject();
			// initialize delegatoroutput status
			delegatorOutput
					.setStatusCode(BookServiceErrorConstants.ADD_BOOK_SUCCESS_CODE);

			// transform inputobject to bookentities bookentities
			List<GAEJPABookEntity> bookEntityList = BookServiceEntityMapper
					.transformBooksToBookEntities(books, DelegatorTypeEnum.ADD);

			// list of books
			List<GAEJPABookEntity> successfullyAddedBookEntities = new ArrayList<>();
			List<GAEJPABookEntity> unsuccessfullyAddedBookEntities = new ArrayList<>();

			// JSON Representation of the BookEntity that should be persisted
			String bookEntityJSON = null;

			// the persisted BookEntity
			GAEJPABookEntity persistedBookEntity;

			// add every BookEntity to the DB
			for (GAEJPABookEntity bookEntity : bookEntityList) {
				try {
					bookEntityJSON = BookServiceEntityMapper
							.mapBookEntityToJSONString(bookEntity);
					// try to retrieve bookEntity
					GAEJPABookEntity bookEntityToPersist = retrieveBookEntityIfAlreadyExisting(bookEntity);
					// handle the persistence of tags specified for the
					// bookEntity
					handleTagPersistenceForBookEntity(bookEntityToPersist);
					// persist or update entity (if bookentity already exists)
					if (bookEntityToPersist.getKey() != null) {
						persistedBookEntity = bookDAOImpl
								.updateEntity(bookEntityToPersist);
					} else {
						persistedBookEntity = bookDAOImpl
								.persistEntity(bookEntityToPersist);
					}
					bookEntityJSON = BookServiceEntityMapper
							.mapBookEntityToJSONString(persistedBookEntity);

					// add book to successfully added list
					// this also includes updates on existing books (linking an
					// additional user
					successfullyAddedBookEntities.add(persistedBookEntity);
					log.info(bookEntityJSON + " added to DB");

				} catch (EntityRetrievalException e) {
					unsuccessfullyAddedBookEntities.add(bookEntity);
					log.log(Level.SEVERE,
							"Error while trying to retrieve existing entities for: "
									+ bookEntityJSON, e);
				} catch (EntityPersistenceException e) {
					unsuccessfullyAddedBookEntities.add(bookEntity);
					log.log(Level.SEVERE,
							"Error while trying to presist entity: "
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
						.setOutputObject(BookServiceEntityMapper
								.transformBookEntitiesToBooks(successfullyAddedBookEntities));
			} else {
				delegatorOutput
						.setStatusCode(BookServiceErrorConstants.ADD_BOOK_FAILURE_CODE);
				delegatorOutput
						.setStatusMessage(BookServiceErrorConstants.ADD_BOOK_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	@Override
	protected void removeEntities() {
		log.info("DELETE Book action triggered");

		// check input object
		if (delegatorInput.getInputObject() instanceof Books) {
			List<GAEJPABookEntity> removedBookEntities = new ArrayList<>();
			Books booksToRemove = (Books) delegatorInput.getInputObject();

			// initialize delegatoroutput status
			delegatorOutput
					.setStatusCode(BookServiceErrorConstants.DELETE_BOOK_SUCCESS_CODE);

			// transform inputobject to bookentities bookentities and remove
			List<GAEJPABookEntity> bookEntities = BookServiceEntityMapper
					.transformBooksToBookEntities(booksToRemove,
							DelegatorTypeEnum.DELETE);
			for (GAEJPABookEntity bookEntity : bookEntities) {
				List<GAEJPABookEntity> bookEntitiesToRemove;
				String bookEntityJSON = BookServiceEntityMapper
						.mapBookEntityToJSONString(bookEntity);
				try {
					if (bookEntity.getKey() != null) {
						bookEntitiesToRemove = new ArrayList<>();
						GAEJPABookEntity foundBook = bookDAOImpl
								.findEntityById(bookEntity.getKey());
						if (foundBook != null) {
							bookEntitiesToRemove.add(foundBook);
						}
					} else {
						if (userExists()) {
							bookEntitiesToRemove = bookDAOImpl
									.findSpecificBookEntityForUser(bookEntity);
						} else {
							bookEntitiesToRemove = bookDAOImpl
									.findSpecificEntity(bookEntity);
						}
					}

					if (bookEntitiesToRemove != null
							&& !bookEntitiesToRemove.isEmpty()) {
						for (GAEJPABookEntity bookEntityToRemove : bookEntitiesToRemove) {
							String bookEntityToRemoveJSON = BookServiceEntityMapper
									.mapBookEntityToJSONString(bookEntityToRemove);
							try {
								GAEJPABookEntity removedBookEntity = null;
								if (userExists()) {
									bookEntityToRemove
											.removeUser(UserServiceEntityMapper
													.mapUserToEntity(
															currentUser,
															DelegatorTypeEnum.DELETE));
									removedBookEntity = bookDAOImpl
											.updateEntity(bookEntityToRemove);
									log.info("BookEntity "
											+ bookEntityToRemoveJSON
											+ " was successfully removed for user "
											+ currentUser.getId());
								} else {
									removedBookEntity = bookDAOImpl
											.removeEntity(bookEntityToRemove);
									log.info("BookEntity "
											+ bookEntityToRemoveJSON
											+ " was successfully removed");
								}
								removedBookEntity.setTags(
										bookEntityToRemove.getTags(), false);
								removedBookEntities.add(removedBookEntity);
							} catch (EntityRemovalException
									| EntityPersistenceException e) {
								log.log(Level.SEVERE,
										"Error while trying to remove: "
												+ bookEntityToRemoveJSON, e);
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
				delegatorOutput.setOutputObject(BookServiceEntityMapper
						.transformBookEntitiesToBooks(removedBookEntities));
			} else {
				delegatorOutput
						.setStatusCode(BookServiceErrorConstants.DELETE_BOOK_FAILURE_CODE);
				delegatorOutput
						.setStatusMessage(BookServiceErrorConstants.DELETE_BOOK_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	@Override
	protected void retrieveEntities() {
		log.info("READ Book by tag action triggered");
		// determine dao action to be called by input object type
		if (delegatorInput.getInputObject() instanceof Tags) {

			Tags searchTags = (Tags) delegatorInput.getInputObject();

			// initialize delegatoroutput status
			delegatorOutput
					.setStatusCode(BookServiceErrorConstants.RETRIEVE_BOOK_SUCCESS_CODE);

			List<GAEJPABookEntity> foundBooks = new ArrayList<>();
			try {
				if (userExists()) {
					foundBooks = bookDAOImpl.getBookEntityByTag(searchTags);
				} else {
					foundBooks = bookDAOImpl.getBookEntityByTag(searchTags);
				}
				// set delegator output
				String statusMessage = BookPersistenceDelegatorUtils
						.buildGetBooksByTagSuccessStatusMessage(searchTags,
								foundBooks);
				log.info(statusMessage);
				delegatorOutput.setStatusMessage(statusMessage);
				delegatorOutput.setOutputObject(BookServiceEntityMapper
						.transformBookEntitiesToBooks(foundBooks));
			} catch (EntityRetrievalException e) {
				delegatorOutput
						.setStatusCode(BookServiceErrorConstants.RETRIEVE_BOOK_FAILURE_CODE);
				delegatorOutput
						.setStatusMessage(BookServiceErrorConstants.RETRIEVE_BOOK_FAILURE_MSG);
				delegatorOutput.setOutputObject(null);
				log.log(Level.SEVERE,
						"Error while trying to retrieve book with tag: '"
								+ searchTags.getTags().toString() + "'", e);
			}
		} else {
			setUnrecognizedDelegatorOutput();
		}
	}

	/**
	 * Method that retrieves a specific BookEntity if it is already persisted
	 * 
	 * @param bookEntity
	 *            the bookEntity that should be retrieved
	 * @return the retrieved bookEntity or null if the entity was not found
	 * @throws EntityRetrievalException
	 */
	private GAEJPABookEntity retrieveBookEntityIfAlreadyExisting(
			GAEJPABookEntity bookEntity) throws EntityRetrievalException {
		// copy bookEntity
		// set tags to null since not relevant
		// for bookEntity search
		GAEJPABookEntity bookEntityCopy = BookPersistenceDelegatorUtils
				.copyBookEntity(bookEntity);
		List<GAEJPABookEntity> persistedBookEntities = null;
		GAEJPAUserEntity currentUserEntity = null;

		// if a specific user is accessing the service
		// try retrieving the bookEntity for the user
		if (userExists()) {
			currentUserEntity = userDAOImpl.findEntityById(currentUser.getId());
			bookEntity.addToUsersAndBooks(currentUserEntity);
			persistedBookEntities = bookDAOImpl
					.findSpecificBookEntityForUser(bookEntityCopy);
		}

		// request is done for no specific user (or no entity was found for the
		// user) try to retrieve entity in general
		if (persistedBookEntities == null || persistedBookEntities.isEmpty()) {
			persistedBookEntities = bookDAOImpl
					.findSpecificEntity(bookEntityCopy);
			if (userExists() && persistedBookEntities != null
					&& persistedBookEntities.size() == 1) {
				persistedBookEntities.get(0).addToUsersAndBooks(
						currentUserEntity);
			}
		}

		// if bookEntity was not found for specific user neither in general
		// return initial entity that should be persisted
		if (persistedBookEntities != null && persistedBookEntities.size() == 1) {
			GAEJPABookEntity persistedBookEntity = persistedBookEntities.get(0);
			return BookPersistenceDelegatorUtils
					.addTagsToBookEntityIfNotAlreadyExisting(
							persistedBookEntity, bookEntity.getTags());
		} else {
			bookEntity.setKey(null);
			return bookEntity;
		}
	}

	/**
	 * This method adds TagEntities to the DB. The Entities are only added, if
	 * they are not existing so far.
	 * 
	 * @param bookEntity
	 *            the GAEJPABookEntity whose tag should be added
	 * @throws EntityRetrievalException
	 *             Exception when issue occurred while trying to retrieve
	 *             TagEntity
	 * @throws EntityPersistenceException
	 *             Exception when issue occurred while trying to persist
	 *             TagEntity
	 */
	private void handleTagPersistenceForBookEntity(GAEJPABookEntity bookEntity)
			throws EntityRetrievalException, EntityPersistenceException {
		Set<GAEJPATagEntity> tagEntities = Collections
				.synchronizedSet(new HashSet<GAEJPATagEntity>(bookEntity
						.getTags()));
		String tagEntityJSON;
		GAEJPATagEntity persistedTagEntity;

		bookEntity.getTags().clear();

		synchronized (tagEntities) {
			for (GAEJPATagEntity tagEntity : tagEntities) {
				tagEntityJSON = BookServiceEntityMapper
						.mapTagEntityToJSONString(tagEntity);
				try {
					List<GAEJPATagEntity> foundTagEntities = tagDAOImpl
							.findSpecificEntity(tagEntity);
					if (foundTagEntities.isEmpty()) {
						tagEntity.getBooks().clear();
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
					log.log(Level.SEVERE,
							"Error while trying to retrieve tagEntity: "
									+ tagEntityJSON, e);
					throw new EntityRetrievalException(e.getMessage(), e);
				} catch (EntityPersistenceException e) {
					log.log(Level.SEVERE,
							"Error while trying to persist tagEntity: "
									+ tagEntityJSON, e);
					throw new EntityPersistenceException(e.getMessage(), e);
				}
			}
		}
	}

	/**
	 * Setter for the used bookDAOImpl
	 * 
	 * @param bookDAOImpl
	 *            the BookDAOImpl object
	 */
	public void setBookDAO(BookDAO bookDAOImpl) {
		this.bookDAOImpl = bookDAOImpl;
	}

	/**
	 * Setter for the used tagDAOImpl
	 * 
	 * @param tagDAOImpl
	 *            the TagDAOImpl object
	 */
	public void setTagDAO(TagDAO tagDAOImpl) {
		this.tagDAOImpl = tagDAOImpl;
	}
}
