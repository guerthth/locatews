package amtc.gue.ws.books.delegate.persist;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import amtc.gue.ws.base.delegate.IDelegatorInput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.books.persistence.dao.tag.TagDAO;
import amtc.gue.ws.books.persistence.dao.tag.impl.TagDAOImpl;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.util.BookServiceEntityMapper;
import amtc.gue.ws.books.util.BookServiceErrorConstants;
import amtc.gue.ws.books.util.TagPersistenceDelegatorUtils;

/**
 * Persistence Delegator that handles all database actions for Tag resources
 * 
 * @author Thomas
 *
 */
public class TagPersistenceDelegator extends AbstractPersistenceDelegator {

	private static final Logger log = Logger
			.getLogger(TagPersistenceDelegator.class.getName());

	/** DAOImplementations used by the delegator */
	private TagDAO tagDAOImpl;

	@Override
	public void initialize(IDelegatorInput input) {
		super.initialize(input);
		tagDAOImpl = (TagDAOImpl) SpringContext.context.getBean("tagDAOImpl");
	}

	@Override
	protected void persistEntities() {
		// not implemented
		setUnrecognizedInputDelegatorOutput();
	}

	@Override
	protected void removeEntities() {
		// not implemented
		setUnrecognizedInputDelegatorOutput();
	}

	@Override
	protected void retrieveEntities() {
		log.info("READ Tag action triggered");

		delegatorOutput
				.setStatusCode(BookServiceErrorConstants.RETRIEVE_TAGS_SUCCESS_CODE);

		try {
			List<GAEJPATagEntity> foundTags = tagDAOImpl.findAllEntities();
			delegatorOutput.setStatusMessage(TagPersistenceDelegatorUtils
					.buildRetrieveTagsSuccessStatusMessage(foundTags));
			delegatorOutput.setOutputObject(BookServiceEntityMapper
					.mapTagEntityListToTags(foundTags));
		} catch (EntityRetrievalException e) {
			delegatorOutput
					.setStatusCode(BookServiceErrorConstants.RETRIEVE_TAGS_FAILURE_CODE);
			delegatorOutput
					.setStatusMessage(BookServiceErrorConstants.RETRIEVE_TAGS_FAILURE_MSG);
			delegatorOutput.setOutputObject(null);
			log.log(Level.SEVERE, "Error while trying to retrieve tags", e);
		}
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
