package amtc.gue.ws.books.delegate.persist;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.exception.EntityRetrievalException;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.books.delegate.persist.output.PersistenceDelegatorOutput;
import amtc.gue.ws.books.persistence.dao.DAOs;
import amtc.gue.ws.books.persistence.model.GAEJPATagEntity;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.ErrorConstants;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;
import amtc.gue.ws.books.utils.TagPersistenceDelegatorUtils;

/**
 * Persistence Delegator that
 * handles all database actions for Tag resources
 * 
 * @author Thomas
 *
 */
public class TagPersistenceDelegator extends AbstractPersistenceDelegator {

	private static final Logger log = Logger
			.getLogger(TagPersistenceDelegator.class.getName());

	@Override
	public void initialize(PersistenceDelegatorInput input,
			DAOs daoImplementations) {
		persistenceInput = input;
		tagDAOImpl = daoImplementations.getTagDAO();
	}

	@Override
	public IDelegatorOutput delegate() {
		delegatorOutput = (PersistenceDelegatorOutput) SpringContext.context
				.getBean("persistenceDelegatorOutput");
		if (persistenceInput.getType().equals(PersistenceTypeEnum.READ)) {
			retrieveTags();
		} else {
			setUnrecognizedInputDelegatorOutput();
		}
		return delegatorOutput;
	}

	/**
	 * Retrieve all tagentities
	 */
	private void retrieveTags() {
		log.info("READ all tags action triggered");

		delegatorOutput
				.setStatusCode(ErrorConstants.RETRIEVE_TAGS_SUCCESS_CODE);

		try {
			List<GAEJPATagEntity> foundTags = tagDAOImpl.findAllEntities();
			delegatorOutput.setStatusMessage(TagPersistenceDelegatorUtils
					.buildRetrieveTagsSuccessStatusMessage(foundTags));
			delegatorOutput.setOutputObject(EntityMapper
					.mapTagEntityListToTags(foundTags));
		} catch (EntityRetrievalException e) {
			delegatorOutput
					.setStatusCode(ErrorConstants.RETRIEVE_TAGS_FAILURE_CODE);
			delegatorOutput
					.setStatusMessage(ErrorConstants.RETRIEVE_TAGS_FAILURE_MSG);
			delegatorOutput.setOutputObject(null);
			log.log(Level.SEVERE, "Error while trying to retrieve tags", e);
		}
	}

}
