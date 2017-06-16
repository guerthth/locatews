package amtc.gue.ws.base;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.users.User;

import amtc.gue.ws.Constants;
import amtc.gue.ws.base.delegate.mail.AbstractMailDelegator;
import amtc.gue.ws.base.delegate.mail.UserMailDelegator;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.response.UserServiceResponse;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;

@Api(name = "users", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID,
		Constants.API_EXPLORER_CLIENT_ID }, description = "API for the User backend application")
public class UserService extends Service {
	private static final Logger log = Logger.getLogger(UserService.class.getName());
	private static final String SCOPE = "users";
	private AbstractMailDelegator mailDelegator;

	public UserService() {
		super();
		mailDelegator = (UserMailDelegator) SpringContext.context.getBean("userMailDelegator");
	}

	public UserService(AbstractPersistenceDelegator userDelegator, AbstractMailDelegator mailDelegator) {
		super(userDelegator);
		this.mailDelegator = mailDelegator;
	}

	@ApiMethod(name = "addUsers", path = "user", httpMethod = HttpMethod.POST)
	public UserServiceResponse addUsers(final User user, Users users) throws UnauthorizedException {
		userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.ADD, users);
		IDelegatorOutput dOutput = userDelegator.delegate();
		UserServiceResponse response = UserServiceEntityMapper.mapBdOutputToUserServiceResponse(dOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "getUsers", path = "user", httpMethod = HttpMethod.GET)
	public UserServiceResponse getUsers(final User user) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		Roles roles = new Roles();
		userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, roles);
		IDelegatorOutput dOutput = userDelegator.delegate();
		UserServiceResponse response = UserServiceEntityMapper.mapBdOutputToUserServiceResponse(dOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "removeUser", path = "user/{id}", httpMethod = HttpMethod.DELETE)
	public UserServiceResponse removeUser(final User user, @Named("id") final String id) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		Users usersToRemove = new Users();
		amtc.gue.ws.base.inout.User userToRemove = new amtc.gue.ws.base.inout.User();
		userToRemove.setId(id);
		List<amtc.gue.ws.base.inout.User> userListToRemove = new ArrayList<>();
		userListToRemove.add(userToRemove);
		usersToRemove.setUsers(userListToRemove);
		userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.DELETE, usersToRemove);
		IDelegatorOutput dOutput = userDelegator.delegate();
		UserServiceResponse response = UserServiceEntityMapper.mapBdOutputToUserServiceResponse(dOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "sendUserMail", path = "user/{id}/password", httpMethod = HttpMethod.GET)
	public UserServiceResponse sendUserMail(final User user, @Named("id") final String id)
			throws UnauthorizedException {
		if (user == null || !isExclusivelyAuthorized(user, id, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		mailDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.MAIL, id);
		IDelegatorOutput dOutput = mailDelegator.delegate();
		UserServiceResponse response = UserServiceEntityMapper.mapBdOutputToUserServiceResponse(dOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}
}
