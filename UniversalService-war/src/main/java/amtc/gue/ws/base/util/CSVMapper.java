package amtc.gue.ws.base.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import amtc.gue.ws.base.exception.CSVReaderException;
import amtc.gue.ws.base.inout.Roles;
import amtc.gue.ws.base.inout.User;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.persistence.model.GAEJPARoleEntity;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;

/**
 * Class mapping a CSV file to Java objects
 * 
 * @author Thomas
 *
 */
public class CSVMapper {
	private static BufferedReader br = null;
	private static String line = "";
	private static String csvSplitBy = ";";
	private static String rolesSplitBy = ",";

	/**
	 * Method mapping the userrole config file to a UserEntityList. Only those
	 * UserEntities which possess specific Roles are included in the list
	 * 
	 * @param csvFile
	 *            the CSV File that is used to retrieve the user data
	 * @param roles
	 *            the roles a user should possess
	 * @return the list of userEntities from the config files
	 * @throws CSVReaderException
	 *             when an issue occured while trying to build list of
	 *             GAEJPAUserEntities from CSV File
	 */
	public static List<GAEJPAUserEntity> mapCSVToUserListByRole(String csvFile,
			Roles roles) throws CSVReaderException {
		List<GAEJPAUserEntity> userEntityList = new ArrayList<>();

		try {
			InputStream is = CSVMapper.class.getResourceAsStream(csvFile);
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				String[] userData = line.split(csvSplitBy);
				String configUsername = userData[0];
				String configPassword = userData[1];
				String configRoles = userData[2];
				Set<GAEJPARoleEntity> configRoleSet = new HashSet<>();
				boolean userHasRole = false;
				for (String role : configRoles.split(rolesSplitBy)) {
					GAEJPARoleEntity roleEntity = new GAEJPARoleEntity();
					roleEntity.setKey(role);
					configRoleSet.add(roleEntity);
					if (!userHasRole) {
						userHasRole = checkIfUserHasRole(role, roles);
					}
				}

				GAEJPAUserEntity userEntity = new GAEJPAUserEntity();
				userEntity.setKey(configUsername);
				userEntity.setPassword(configPassword);
				userEntity.setRoles(configRoleSet, true);

				if (userHasRole)
					userEntityList.add(userEntity);
			}
		} catch (Exception e) {
			throw new CSVReaderException(csvFile, e);
		}

		return userEntityList;
	}

	/**
	 * Method mapping the userrole config file to a Users object. Only those
	 * Users which possess specific Roles are included in the list
	 * 
	 * @param csvFile
	 *            the CSV File that is used to retrieve the user data
	 * @param roles
	 *            the roles a user should possess
	 * @return Users object created from the config files
	 * @throws CSVReaderException
	 *             when an issue occured while trying to build Users from CSV
	 *             File
	 */
	public static Users mapCSVToUsersByRole(String csvFile, Roles roles)
			throws CSVReaderException {
		Users users = new Users();

		try {
			InputStream is = CSVMapper.class.getResourceAsStream(csvFile);
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				String[] userData = line.split(csvSplitBy);
				String configUsername = userData[0];
				String configPassword = userData[1];
				String configRoles = userData[2];
				List<String> configRoleList = new ArrayList<>();
				boolean userHasRole = false;
				for (String role : configRoles.split(rolesSplitBy)) {
					configRoleList.add(role);
					if (!userHasRole) {
						userHasRole = checkIfUserHasRole(role, roles);
					}
				}

				User user = new User();
				user.setId(configUsername);
				user.setPassword(configPassword);
				user.setRoles(configRoleList);

				if (userHasRole)
					users.getUsers().add(user);
			}
		} catch (Exception e) {
			throw new CSVReaderException(csvFile, e);
		}

		return users;
	}

	/**
	 * Method checking if a specific userRole matches one of the intended roles
	 * from the Roles object
	 * 
	 * @param userRole
	 *            the specific role of the user
	 * @param roles
	 *            the Roles including all roles that are allowed
	 * @return true if userRole matches one of the allowed roles, false if not
	 */
	private static boolean checkIfUserHasRole(String userRole, Roles roles) {
		boolean userHasRole = false;
		if (userRole != null && roles != null && roles.getRoles() != null) {
			for (String role : roles.getRoles()) {
				if (userRole.equals(role)) {
					userHasRole = true;
					break;
				}
			}
		}
		return userHasRole;
	}
}
