package amtc.gue.ws.test.base.util;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.exception.HtmlReaderException;
import amtc.gue.ws.base.util.HtmlMapper;
import amtc.gue.ws.test.base.UserTest;

/**
 * Class testing the Html Mapper Class
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HtmlMapperTest extends UserTest {

	private static final String HTMLFile = "/testUserDataMail.html";
	private static final String NONEXISTINGFile = "test.csv";

	@BeforeClass
	public static void oneTimeInitialSetup() {
		setUpBasicEnvironment();
	}

	@Test
	public void testMapHtmlToString() throws HtmlReaderException {
		String mappedString = HtmlMapper.mapHtmlToString(JPAUserEntity1, HTMLFile);
		assertFalse(mappedString.contains("{userId}"));
		assertTrue(mappedString.contains(JPAUserEntity1.getKey()));
	}
	
	@Test(expected = HtmlReaderException.class)
	public void testMapHtmlToStringUsingNonExistingInputFile() throws HtmlReaderException{
		HtmlMapper.mapHtmlToString(JPAUserEntity1, NONEXISTINGFile);
	}

	@Test
	public void testParseHtmlUsingSimpleHtmlFile() throws HtmlReaderException {
		String parsedHtml = HtmlMapper.parseHtml(JPAUserEntity1, HTMLFile);
		assertFalse(parsedHtml.contains("{userId}"));
		assertTrue(parsedHtml.contains(JPAUserEntity1.getKey()));
	}

	@Test(expected = HtmlReaderException.class)
	public void testParseHtmlUsingNonExistingInputFile() throws HtmlReaderException {
		HtmlMapper.parseHtml(JPAUserEntity1, NONEXISTINGFile);
	}
}
