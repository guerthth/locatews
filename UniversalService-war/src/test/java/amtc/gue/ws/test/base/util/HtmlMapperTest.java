package amtc.gue.ws.test.base.util;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.HtmlReaderException;
import amtc.gue.ws.base.persistence.model.GAEJPAUserEntity;
import amtc.gue.ws.base.util.HtmlMapper;

/**
 * Class testing the Html Mapper Class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HtmlMapperTest {

	private static GAEJPAUserEntity userEntity;
	private static final String USERNAME = "userA";
	private static final String HTMLFile = "/testPwMail.html";
	private static final String NONEXISTINGFile = "test.csv";

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setupUsers();
	}

	@Test
	public void testMapHtmlToString() throws HtmlReaderException {
		String mappedString = HtmlMapper.mapHtmlToString(userEntity, HTMLFile);
		assertFalse(mappedString.contains("{userId}"));
		assertTrue(mappedString.contains(userEntity.getKey()));
	}
	
	@Test(expected = HtmlReaderException.class)
	public void testMapHtmlToStringUsingNonExistingInputFile() throws HtmlReaderException{
		HtmlMapper.mapHtmlToString(userEntity, NONEXISTINGFile);
	}

	@Test
	public void testParseHtmlUsingSimpleHtmlFile() throws HtmlReaderException {
		String parsedHtml = HtmlMapper.parseHtml(userEntity, HTMLFile);
		assertFalse(parsedHtml.contains("{userId}"));
		assertTrue(parsedHtml.contains(userEntity.getKey()));
	}

	@Test(expected = HtmlReaderException.class)
	public void testParseHtmlUsingNonExistingInputFile() throws HtmlReaderException {
		HtmlMapper.parseHtml(userEntity, NONEXISTINGFile);
	}

	/**
	 * Setting up user entities
	 */
	private static void setupUsers() {
		userEntity = new GAEJPAUserEntity();
		userEntity.setKey(USERNAME);
	}
}
