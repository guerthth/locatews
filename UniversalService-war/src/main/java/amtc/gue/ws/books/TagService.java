package amtc.gue.ws.books;

import java.util.logging.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import amtc.gue.ws.Constants;
import amtc.gue.ws.base.Service;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.books.delegate.persist.TagPersistenceDelegator;
import amtc.gue.ws.books.response.TagServiceResponse;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;

@Api(name = "tags", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID,
		Constants.API_EXPLORER_CLIENT_ID }, description = "API for the Tag backend application")
public class TagService extends Service {
	protected static final Logger log = Logger.getLogger(TagService.class.getName());
	private static final String SCOPE = "tags";
	private AbstractPersistenceDelegator tagDelegator;

	public TagService() {
		super();
		tagDelegator = (TagPersistenceDelegator) SpringContext.context.getBean("tagPersistenceDelegator");
	}

	public TagService(AbstractPersistenceDelegator userDelegator, AbstractPersistenceDelegator tagDelegator) {
		super(userDelegator);
		this.tagDelegator = tagDelegator;
	}

	@ApiMethod(name = "getTags", path = "tag", httpMethod = HttpMethod.GET)
	public TagServiceResponse getTags(final User user) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		tagDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, null);
		IDelegatorOutput dOutput = tagDelegator.delegate();
		TagServiceResponse response = BookServiceEntityMapper.mapBdOutputToTagServiceResponse(dOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}
}
