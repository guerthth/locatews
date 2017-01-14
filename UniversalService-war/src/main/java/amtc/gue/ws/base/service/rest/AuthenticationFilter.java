package amtc.gue.ws.base.service.rest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;

import amtc.gue.ws.base.delegate.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.UserPersistenceDelegator;
import amtc.gue.ws.base.exception.CSVReaderException;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.util.CSVMapper;
import amtc.gue.ws.base.util.EncryptionMapper;
import amtc.gue.ws.base.util.PersistenceTypeEnum;
import amtc.gue.ws.base.util.SpringContext;

/**
 * Authenticationfilter used to check if caller is allowed to call service
 * methods
 * 
 * @author Thomas
 *
 */
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {

	private static final Logger log = Logger
			.getLogger(AuthenticationFilter.class.getName());
	private User currentUser = (User) SpringContext.context.getBean("user");

	@Context
	private ResourceInfo resourceInfo;

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final String ACCESS_DENIED = "You cannot access this resource";
	private static final String ACCESS_FORBIDDEN = "Access blocked for all users !!";

	private static final String SERVICEUSERCSVFile = "/serviceUsers.csv";

	private AbstractPersistenceDelegator userDelegator;

	@Override
	public void filter(ContainerRequestContext requestContext)
			throws IOException {

		// rest userdata for each request
		resetUserData();

		Method method = resourceInfo.getResourceMethod();

		// Access permitted for all
		boolean permittedForAll = false;
		if (method.isAnnotationPresent(PermitAll.class)) {
			permittedForAll = true;
		} else if (method.isAnnotationPresent(DenyAll.class)) {
			// Access denied for all
			throw new NotAuthorizedException(ACCESS_FORBIDDEN);
		}

		// Get request headers
		final MultivaluedMap<String, String> headers = requestContext
				.getHeaders();

		// Fetch authorization header
		final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

		// check if authorization information is present
		if (authorization != null && !authorization.isEmpty()) {
			// Get encoded username and password
			final String encodedUserPassword = authorization.get(0)
					.replaceFirst(AUTHENTICATION_SCHEME + " ", "");

			// Decode username and password
			String usernameAndPassword = new String(
					Base64.decode(encodedUserPassword.getBytes()));

			// Split username and password tokens
			final StringTokenizer tokenizer = new StringTokenizer(
					usernameAndPassword, ":");
			final String username = tokenizer.nextToken();
			final String password = tokenizer.nextToken();

			log.info("User " + username + " trying to access servicemethod "
					+ method.getName());
			log.info("password used to authenticate: " + password);
			this.currentUser.setId(username);

			// Access allowed for all
			if (!permittedForAll) {
				// Verify user access
				if (method.isAnnotationPresent(RolesAllowed.class)) {
					RolesAllowed rolesAnnotation = method
							.getAnnotation(RolesAllowed.class);
					Set<String> rolesSet = new HashSet<String>(
							Arrays.asList(rolesAnnotation.value()));

					// Is user valid?
					if (!isUserAllowed(username, password, rolesSet)) {
						throw new NotAuthorizedException(ACCESS_DENIED);
					}
				}
			}
		} else {
			// If no authorization information present and method access is NOT
			// permitted for all; block access
			if (!permittedForAll) {
				throw new NotAuthorizedException(ACCESS_DENIED);
			}
		}
	}

	/**
	 * Method that resets all data for the current user trying to access the
	 * service
	 */
	private void resetUserData() {
		this.currentUser.setId(null);
		this.currentUser.setRoles(null);
	}

	/**
	 * Method that checks if the accessing user is allowed to call the specific
	 * method
	 * 
	 * @param username
	 *            the username of the accessing user
	 * @param password
	 *            the password of the accessing user
	 * @param rolesSet
	 *            the roles specified on the service method
	 * @return true if allowed, false if not allowed
	 */
	private boolean isUserAllowed(final String username, final String password,
			final Set<String> rolesSet) {
		boolean isAllowed = false;
		String encryptedPW = EncryptionMapper.encryptStringMD5(password);

		// create Roles object containing all allowed roles and initialize Users
		Roles allowedRoles = transformRolesSetToRoles(rolesSet);
		Users usersWithRole = retrieveUsers(allowedRoles);

		// check of user calling the service is allowed
		if (usersWithRole != null && usersWithRole.getUsers() != null
				&& !usersWithRole.getUsers().isEmpty()) {
			for (User user : usersWithRole.getUsers()) {
				if (user.getId().equals(username)
						&& user.getPassword().equals(encryptedPW)) {
					isAllowed = true;
					this.currentUser.setRoles(user.getRoles());
					break;
				}
			}
		}

		return isAllowed;
	}

	/**
	 * Method loading user roles from DB or the local CSV File
	 * 
	 * @return the users that were found
	 */

	/**
	 * Method loading user roles from DB or the local CSV File
	 * 
	 * @param allowedRoles
	 *            the roles a User sould possess
	 * @return the users that were found
	 */
	private Users retrieveUsers(Roles allowedRoles) {
		Users foundUsers = null;

		// call UserPersistenceDelegator to get all Users having at least
		// one of those Roles
		this.userDelegator = (UserPersistenceDelegator) SpringContext.context
				.getBean("userPersistenceDelegator");
		this.userDelegator.buildAndInitializePersistenceDelegator(
				PersistenceTypeEnum.READ, allowedRoles);
		IDelegatorOutput bpdOutput = this.userDelegator.delegate();
		if (bpdOutput.getOutputObject() instanceof Users) {
			foundUsers = (Users) bpdOutput.getOutputObject();
		}

		// if no users were found, try to retrieve them from the local CSV File
		if (foundUsers == null || foundUsers.getUsers() == null
				|| foundUsers.getUsers().isEmpty()) {
			try {
				foundUsers = CSVMapper.mapCSVToUsersByRole(SERVICEUSERCSVFile,
						allowedRoles);
			} catch (CSVReaderException e) {
				log.log(Level.SEVERE, e.getMessage(), e);
			}
		}

		return foundUsers;
	}

	/**
	 * Method creating a Roles object for the allowed roles defined on the
	 * webservice method
	 * 
	 * @param rolesSet
	 *            roles that are allowed to access the method
	 * @return Roles object containing the allowed roles
	 */
	private Roles transformRolesSetToRoles(Set<String> rolesSet) {
		Roles allowedMethodRoles = new Roles();
		if (rolesSet != null && !rolesSet.isEmpty()) {
			for (String role : rolesSet) {
				allowedMethodRoles.getRoles().add(role);
			}
		}
		return allowedMethodRoles;
	}
}
