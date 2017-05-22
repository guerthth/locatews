package amtc.gue.ws.books;

import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.books.delegate.persist.TagPersistenceDelegator;
import amtc.gue.ws.books.response.TagServiceResponse;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;

@Path("/tags")
@Produces(MediaType.APPLICATION_JSON)
public class TagService {
	protected static final Logger log = Logger.getLogger(TagService.class
			.getName());
	private AbstractPersistenceDelegator tagDelegator;

	public TagService() {
		this.tagDelegator = (TagPersistenceDelegator) SpringContext.context
				.getBean("tagPersistenceDelegator");
	}

	public TagService(AbstractPersistenceDelegator delegator) {
		this.tagDelegator = delegator;
	}

	@PermitAll
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public TagServiceResponse getTags() {
		// set up the pesistence delegator
		tagDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, null);

		// call TagPersistenceDelegators delegate method to handle retrieval of
		// existing tags
		IDelegatorOutput bpdOutput = tagDelegator.delegate();

		// return delegator output mapped to serviceresponse
		return BookServiceEntityMapper
				.mapBdOutputToTagServiceResponse(bpdOutput);
	}
}
