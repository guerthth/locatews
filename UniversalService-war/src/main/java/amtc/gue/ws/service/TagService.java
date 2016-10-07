package amtc.gue.ws.service;

import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import amtc.gue.ws.books.delegate.IDelegatorOutput;
import amtc.gue.ws.books.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.TagPersistenceDelegator;
import amtc.gue.ws.books.service.inout.output.TagServiceResponse;
import amtc.gue.ws.books.utils.EntityMapper;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
public class TagService extends Service {
	protected static final Logger log = Logger.getLogger(TagService.class
			.getName());
	private AbstractPersistenceDelegator tagDelegator = (TagPersistenceDelegator) SpringContext.context
			.getBean("tagPersistenceDelegator");

	public TagService() {

	}

	public TagService(AbstractPersistenceDelegator delegator) {
		this.tagDelegator = delegator;
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public TagServiceResponse getTags() {
		// set up the pesistence delegator
		buildAndInitializePersistenceDelegator(tagDelegator,
				PersistenceTypeEnum.READ, null);

		// call TagPersistenceDelegators delegate method to handle retrieval of
		// existing tags
		IDelegatorOutput bpdOutput = tagDelegator.delegate();

		// return delegator output mapped to serviceresponse
		return EntityMapper.mapBdOutputToTagServiceResponse(bpdOutput);
	}
}
