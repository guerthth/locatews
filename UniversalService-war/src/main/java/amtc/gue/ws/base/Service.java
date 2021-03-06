package amtc.gue.ws.base;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.api.server.spi.auth.common.User;
import com.google.appengine.api.oauth.OAuthServiceFactory;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import amtc.gue.ws.Constants;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.UserPersistenceDelegator;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;

public class Service {
	private static final Logger log = Logger.getLogger(Service.class.getName());
	private static UserService userService = UserServiceFactory.getUserService();
	protected AbstractPersistenceDelegator userDelegator;
	protected static final String UNAUTHORIZEDMESSAGE = "Authorization required";

	public Service() {
		userDelegator = (UserPersistenceDelegator) SpringContext.context.getBean("userPersistenceDelegator");
	}

	public Service(AbstractPersistenceDelegator userDelegator) {
		this.userDelegator = userDelegator;
	}

	protected boolean isAuthorized(com.google.api.server.spi.auth.common.User user, String scope) {
		if (isUserAdmin()) {
			return true;
		} else {
			log.info("Trying to retrieve User with Id '" + user.getEmail() + "'");
			amtc.gue.ws.base.inout.User searchUser = UserServiceEntityMapper.mapAuthUserToUser(user);
			userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, searchUser);
			IDelegatorOutput dOutput = userDelegator.delegate();
			if (dOutput.getOutputObject() instanceof amtc.gue.ws.base.inout.Users) {
				amtc.gue.ws.base.inout.User foundUser = 
						((amtc.gue.ws.base.inout.Users) dOutput.getOutputObject()).getUsers().get(0);
				if (foundUser.getRoles().contains(scope)) {
					return true;
				}
			}
		}
		return false;
	}

	protected boolean isExclusivelyAuthorized(User user, String id, String scope) {
		if (isUserAdmin()) {
			return true;
		} else {
			log.info("Trying to retrieve User with Id '" + user.getEmail() + "'");
			amtc.gue.ws.base.inout.User searchUser = UserServiceEntityMapper.mapAuthUserToUser(user);
			userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, searchUser);
			IDelegatorOutput dOutput = userDelegator.delegate();
			if (dOutput.getOutputObject() instanceof amtc.gue.ws.base.inout.Users) {
				amtc.gue.ws.base.inout.User foundUser = 
						((amtc.gue.ws.base.inout.Users) dOutput.getOutputObject()).getUsers().get(0);
				if (user.getId().equals(id) && foundUser.getRoles().contains(scope)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isUserAdmin() {
		boolean isAdmin = false;
		try {
			if (userService.isUserAdmin()) {
				isAdmin = true;
			}
		} catch (IllegalStateException e) {
			try {
				isAdmin = OAuthServiceFactory.getOAuthService().isUserAdmin(Constants.EMAIL_SCOPE);
			} catch (Exception e2) {
				log.log(Level.SEVERE, e2.getMessage(), e2);
			}
		}
		return isAdmin;
	}

	protected IDelegatorOutput getCurrentUser(User user) {
		log.info("Trying to retrieve User with Id '" + user.getEmail() + "'");
		amtc.gue.ws.base.inout.User searchUser = UserServiceEntityMapper.mapAuthUserToUser(user);
		userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, searchUser);
		IDelegatorOutput dOutput = userDelegator.delegate();

		return dOutput;
	}
}
