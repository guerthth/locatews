package amtc.gue.ws.base.service.rest;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
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

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.delegate.persist.UserPersistenceDelegator;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.util.EncryptionMapper;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
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

	private static final Logger log = Logger.getLogger(AuthenticationFilter.class.getName());
	private User currentUser = (User) SpringContext.context.getBean("user");
	private String[] allowedHttpMethods = { "GET", "POST", "PUT", "DELETE" };

	@Context
	private ResourceInfo resourceInfo;

	private static final String AUTHORIZATION_PROPERTY = "Authorization";
	private static final String AUTHENTICATION_SCHEME = "Basic";
	private static final String ACCESS_DENIED = "You cannot access this resource";
	private static final String ACCESS_FORBIDDEN = "Access blocked for all users !!";

	private AbstractPersistenceDelegator userDelegator;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {

		// the authentication check should only happen for GET, POST, PUT, and
		// DELETE requests
		if (Arrays.asList(allowedHttpMethods).contains(requestContext.getMethod())) {
			resetCurrentUser();

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
			final MultivaluedMap<String, String> headers = requestContext.getHeaders();

			// Fetch authorization header
			final List<String> authorization = headers.get(AUTHORIZATION_PROPERTY);

			// check if authorization information is present
			if (authorization != null && !authorization.isEmpty()) {
				// Get encoded username and password
				final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");

				// Decode username and password
				String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));

				// Split username and password tokens
				final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
				final String username = tokenizer.nextToken();
				final String password = tokenizer.nextToken();

				log.info("User " + username + " trying to access servicemethod " + method.getName());
				log.info("password used to authenticate: " + password);

				// try retrieving the user with the used userName from the DB
				setCurrentUser(retrieveUser(username));

				// Access allowed for all
				if (!permittedForAll) {
					// Verify user access
					if (method.isAnnotationPresent(RolesAllowed.class)) {
						RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
						Set<String> allowedRoles = new HashSet<String>(Arrays.asList(rolesAnnotation.value()));

						// Is user valid?
						if (!isUserAuthorized(username, password, allowedRoles)) {
							throw new NotAuthorizedException(ACCESS_DENIED);
						}
					} else {
						throw new NotAuthorizedException(ACCESS_DENIED);
					}
				}
			} else {
				// If no authorization information present and method access is
				// NOT
				// permitted for all; block access
				if (!permittedForAll) {
					throw new NotAuthorizedException(ACCESS_DENIED);
				}
			}
		} else {
			log.info(requestContext.getMethod() + " call reveived. No Authentication check needed.");
		}
	}

	/**
	 * Method resetting the data of the currentUser
	 */
	private void resetCurrentUser() {
		currentUser.setId(null);
		currentUser.setPassword(null);
		currentUser.setEmail(null);
		currentUser.setRoles(null);
	}

	/**
	 * Method setting the data of the currentUser with valued from a User that
	 * was retriebed from the datastore
	 * 
	 * @param retrievedUser
	 *            a User retrieved from the datastore
	 */
	private void setCurrentUser(User retrievedUser) {
		currentUser.setId(retrievedUser.getId());
		currentUser.setPassword(retrievedUser.getPassword());
		currentUser.setEmail(retrievedUser.getEmail());
		currentUser.setRoles(retrievedUser.getRoles());
	}

	/**
	 * 
	 * @param username
	 *            the username of the user accessing the webservice method
	 * @param password
	 *            the password of the user accessing the webservice method
	 * @param allowedRoles
	 *            the roles that are allowed to access the service method
	 * @return true if allowed, false if not allowed
	 */
	private boolean isUserAuthorized(final String username, final String password, final Set<String> allowedRoles) {
		boolean isAllowed = false;
		String encryptedPW = EncryptionMapper.encryptStringMD5(password);

		if (currentUser != null && currentUser.getId().equals(username)
				&& currentUser.getPassword().equals(encryptedPW)) {
			for (String userRole : currentUser.getRoles()) {
				if (allowedRoles.contains(userRole)) {
					isAllowed = true;
					break;
				}
			}

		}

		return isAllowed;
	}

	/**
	 * Method retrieving an UserEntity from the database by userName
	 * 
	 * @param userName
	 *            the userName the UserEntity shall possess
	 * @return the found UserEntity from the DB
	 */
	private User retrieveUser(String userName) {
		User foundUser = null;
		// call UserPersistenceDelegator to search for user by username
		userDelegator = (UserPersistenceDelegator) SpringContext.context.getBean("userPersistenceDelegator");
		userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, userName);
		IDelegatorOutput bpdOutput = userDelegator.delegate();

		if (bpdOutput.getOutputObject() instanceof User) {
			foundUser = (User) bpdOutput.getOutputObject();
		}

		return foundUser;
	}
}
