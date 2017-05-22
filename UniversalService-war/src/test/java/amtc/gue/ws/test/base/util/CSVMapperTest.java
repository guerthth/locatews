package amtc.gue.ws.test.base.util;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.CSVReaderException;
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.util.CSVMapper;
import amtc.gue.ws.test.base.UserTest;

/**
 * Class testing the CSV Mapper Class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CSVMapperTest extends UserTest {
	private static final String CSVFile = "/testcsv.csv";
	private static final String TXTFile = "/test.txt";
	private static final String NONEXISTINGCSVFILE = "test.csv";

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicEnvironment();
	}

	@Test
	public void testmapCSVToUserListUsingSimpleRoles() throws CSVReaderException {
		List<GAEUserEntity> mappedCSV = CSVMapper.mapCSVToUserListByRole(CSVFile, roles);
		assertNotNull(mappedCSV);
		assertEquals(1, mappedCSV.size());
	}

	@Test
	public void testMapCSVToUserListUsingSimpleRolesDetails() throws CSVReaderException {
		List<GAEUserEntity> mappedCSV = CSVMapper.mapCSVToUserListByRole(CSVFile, roles);
		assertEquals(1, mappedCSV.size());
		assertEquals("userD", mappedCSV.get(0).getKey());
	}

	@Test
	public void testMapCSVToUserListUsingNullRoles() throws CSVReaderException {
		List<GAEUserEntity> mappedCSV = CSVMapper.mapCSVToUserListByRole(CSVFile, null);
		assertNotNull(mappedCSV);
		assertEquals(0, mappedCSV.size());
	}

	@Test(expected = CSVReaderException.class)
	public void testMapCSVToUserListWhenFileNotFound() throws CSVReaderException {
		CSVMapper.mapCSVToUserListByRole(NONEXISTINGCSVFILE, roles);
	}

	@Test(expected = CSVReaderException.class)
	public void testMapCSVToUserListUsingTxtFileAsInput() throws CSVReaderException {
		CSVMapper.mapCSVToUserListByRole(TXTFile, roles);
	}

	@Test
	public void testMapCSVToUsersUsingSimpleRoles() throws CSVReaderException {
		Users mappedUsers = CSVMapper.mapCSVToUsersByRole(CSVFile, rolesWithNullContent);
		assertNotNull(mappedUsers);
		assertEquals(0, mappedUsers.getUsers().size());
	}

	@Test
	public void testMapCSVToUsersUsingSimpleRolesDetails() throws CSVReaderException {
		Users mappedUsers = CSVMapper.mapCSVToUsersByRole(CSVFile, roles);
		assertEquals("userD", mappedUsers.getUsers().get(0).getId());
	}

	@Test
	public void testMapCSVToUsersUsingNullRoles() throws CSVReaderException {
		Users mappedUsers = CSVMapper.mapCSVToUsersByRole(CSVFile, null);
		assertNotNull(mappedUsers);
		assertEquals(0, mappedUsers.getUsers().size());
	}

	@Test(expected = CSVReaderException.class)
	public void testMapCSVToUsersWhenFileNotFound() throws CSVReaderException {
		CSVMapper.mapCSVToUsersByRole(NONEXISTINGCSVFILE, roles);
	}

	@Test(expected = CSVReaderException.class)
	public void testMapCSVToUsersUsingTxtFileAsInput() throws CSVReaderException {
		CSVMapper.mapCSVToUsersByRole(TXTFile, roles);
	}
}
