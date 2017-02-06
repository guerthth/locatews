package amtc.gue.ws.base;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import amtc.gue.ws.base.delegate.AbstractDelegator;
import amtc.gue.ws.base.delegate.mail.AbstractMailDelegator;
import amtc.gue.ws.base.delegate.mail.UserMailDelegator;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.UserPersistenceDelegator;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.response.UserServiceResponse;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.base.util.UserServiceEntityMapper;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService {
	protected static final Logger log = Logger.getLogger(UserService.class
			.getName());
	private AbstractPersistenceDelegator userDelegator;
	private AbstractDelegator mailDelegator;

	public UserService() {
		userDelegator = (UserPersistenceDelegator) SpringContext.context
				.getBean("userPersistenceDelegator");
		mailDelegator = (UserMailDelegator) SpringContext.context
				.getBean("userMailDelegator");
	}

	public UserService(AbstractPersistenceDelegator userDelegator,
			AbstractMailDelegator mailDelegator) {
		this.userDelegator = userDelegator;
		this.mailDelegator = mailDelegator;
	}

	@PermitAll
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public UserServiceResponse addUsers(Users users) {
		userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.ADD, users);
		IDelegatorOutput dOutput = userDelegator.delegate();
		return UserServiceEntityMapper
				.mapBdOutputToUserServiceResponse(dOutput);
	}

	@PermitAll
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public UserServiceResponse getUsers() {
		Roles roles = new Roles();
		userDelegator
				.buildAndInitializeDelegator(DelegatorTypeEnum.READ, roles);
		IDelegatorOutput dOutput = userDelegator.delegate();
		return UserServiceEntityMapper
				.mapBdOutputToUserServiceResponse(dOutput);
	}

	@PermitAll
	@DELETE
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public UserServiceResponse removeUser(@PathParam("id") String id) {
		Users usersToRemove = new Users();
		User userToRemove = new User();
		List<User> userListToRemove = new ArrayList<>();
		userListToRemove.add(userToRemove);
		userToRemove.setId(id);
		usersToRemove.setUsers(userListToRemove);

		userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.DELETE,
				usersToRemove);
		IDelegatorOutput dOutput = userDelegator.delegate();
		return UserServiceEntityMapper
				.mapBdOutputToUserServiceResponse(dOutput);
	}

	@PermitAll
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}/password")
	public UserServiceResponse sendUserPasswordMail(@PathParam("id") String id) {
		mailDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.MAIL, id);
		IDelegatorOutput dOutput = mailDelegator.delegate();
		return UserServiceEntityMapper
				.mapBdOutputToUserServiceResponse(dOutput);
	}
}
