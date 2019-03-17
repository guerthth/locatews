package amtc.gue.ws.base;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.google.api.server.spi.auth.EspAuthenticator;
import com.google.api.server.spi.auth.GoogleOAuth2Authenticator;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiIssuer;
import com.google.api.server.spi.config.ApiIssuerAudience;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.api.server.spi.config.Named;

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

@Api( 
		name = "users",
		version = "v2", 
		scopes = { Constants.EMAIL_SCOPE }, 
		clientIds = { 
				Constants.WEB_CLIENT_ID,
				Constants.API_EXPLORER_CLIENT_ID 
		},
		authenticators = {GoogleOAuth2Authenticator.class, EspAuthenticator.class},
		issuers = {
			@ApiIssuer(
					name = "firebase",
		            issuer = "https://securetoken.google.com/universalservice-dcafd",
		            jwksUri = "https://www.googleapis.com/service_accounts/v1/metadata/x509/securetoken@system.gserviceaccount.com"
			)	
		},
		issuerAudiences = {
				@ApiIssuerAudience(name = "firebase", audiences = {"1017704499337-8s3a0grnio4emiura8673l33qrst7nu2.apps.googleusercontent.com",
						"1017704499337-eqts400689c87qvdcf71um5mncah57h0.apps.googleusercontent.com","universalservice-dcafd"})
		},
		description = "API for the User backend application"
)
public class UserService extends Service {
	private static final Logger log = Logger.getLogger(UserService.class.getName());
	private static final String SCOPE = "users";
	private static final String CURRENTUSER = "current";
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
	
	@ApiMethod(name = "getUserById", path="user/{id}", httpMethod=HttpMethod.GET)
	public UserServiceResponse getUserById(final User user, @Named("id") final String id) throws UnauthorizedException {
		if(user == null){
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		} 
		
		// if id is defined as CURRENTUSER, the logged in user is returned
		IDelegatorOutput dOutput = null;
		if(id.equals(CURRENTUSER)){
			amtc.gue.ws.base.inout.User userToRetrieve = UserServiceEntityMapper.mapAuthUserToUser(user);
			userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, userToRetrieve);
			dOutput = userDelegator.delegate();
		} else {
			// if id represents the userID, the user is retrieved by its id
			userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, id);
			dOutput = userDelegator.delegate();
		}
		
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
