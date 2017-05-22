package amtc.gue.ws.test.base.util;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.util.EncryptionMapper;

/**
 * Test Class for EncryptionMapper
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EncryptionMapperTest {
	private static String stringToEncrypt = "passwordToEncrypt";

	@Test
	public void testEncryptStringMD5() {
		String encryptedString = EncryptionMapper.encryptStringMD5(stringToEncrypt);
		assertNotNull(encryptedString);
		assertFalse(stringToEncrypt.equals(encryptedString));
	}

	@Test
	public void testEncryptStringMD5HashingTwice() {
		String firstEncryptedString = EncryptionMapper.encryptStringMD5(stringToEncrypt);
		String secondEncryptedString = EncryptionMapper.encryptStringMD5(stringToEncrypt);
		assertNotNull(firstEncryptedString);
		assertTrue(firstEncryptedString.equals(secondEncryptedString));
	}
}
