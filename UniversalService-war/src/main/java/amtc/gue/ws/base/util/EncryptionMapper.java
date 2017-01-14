package amtc.gue.ws.base.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used for encryption and decryption of specific inputs
 * 
 * @author Thomas
 *
 */
public class EncryptionMapper {

	private static final Logger log = Logger.getLogger(EncryptionMapper.class
			.getName());
	private static final String MD5 = "MD5";
	private static final String ALGORITHM_NOT_FOUND = "The specified algorithm wasn't found: ";

	private static MessageDigest digesterMD5;

	static {
		try {
			digesterMD5 = MessageDigest.getInstance(MD5);
		} catch (NoSuchAlgorithmException e) {
			log.log(Level.SEVERE, ALGORITHM_NOT_FOUND, e);
		}
	}

	/**
	 * Method taking an input string, encrypting it using MD5
	 * 
	 * @param stringToEncrypt
	 *            the input string that should be encrypted
	 * @return encrypted string
	 */
	public static String encryptStringMD5(String stringToEncrypt) {
		String encryptedString = null;
		digesterMD5.update(stringToEncrypt.getBytes());
		byte[] bytes = digesterMD5.digest();
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < bytes.length; i++){
			sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		encryptedString = sb.toString();

		return encryptedString;
	}
}
