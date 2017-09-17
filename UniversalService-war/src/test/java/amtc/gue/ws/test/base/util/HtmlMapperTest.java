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
	private static final String USERADDINGHTMLFile = "/testUserAddingMail.html";
	private static final String BILLADDINGHTMLFile = "/testBillAddingMail.html";
	private static final String BILLINGGROUPREPORTHTMLFile = "/testBillinggroupReport.html";
	private static final String PWRESETMAIL = "/testPwMail.html";
	private static final String NONEXISTINGFile = "test.csv";
	private static final String DESCRIPTION = "testDescription";
	private static final String AMOUNT = "testAmount";
	private static final String BILLINGGROUPREPORT = "testReport";

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
	public void testMapHtmlToStringUsingNonExistingInputFile() throws HtmlReaderException {
		HtmlMapper.mapHtmlToString(JPAUserEntity1, NONEXISTINGFile);
	}

	@Test
	public void testParsePwRestHtmlUsingSimpleHtmlFile() throws HtmlReaderException {
		String parsedHtml = HtmlMapper.parsePwResetHtml(JPAUserEntity1, HTMLFile);
		assertFalse(parsedHtml.contains("{userId}"));
		assertTrue(parsedHtml.contains(JPAUserEntity1.getKey()));
	}

	@Test(expected = HtmlReaderException.class)
	public void testParsePwRestHtmlUsingNonExistingInputFile() throws HtmlReaderException {
		HtmlMapper.parsePwResetHtml(JPAUserEntity1, NONEXISTINGFile);
	}

	@Test
	public void testParseUserAddingHtmlUsingSimpleFile() throws HtmlReaderException {
		String parsedHtml = HtmlMapper.parseUserAddingHtml(JPAUserEntity1.getKey(), DESCRIPTION, USERADDINGHTMLFile);
		assertFalse(parsedHtml.contains("{userName}"));
		assertTrue(parsedHtml.contains(JPAUserEntity1.getKey()));
	}

	@Test
	public void testParseUserAddingHtmlUsingSimpleFileNullInputs() throws HtmlReaderException {
		String parsedHtml = HtmlMapper.parseUserAddingHtml(null, null, USERADDINGHTMLFile);
		assertTrue(parsedHtml.contains("{userName}"));
	}

	@Test
	public void testParseUserAddingHtmlUsingSimpleFileNoFindings() throws HtmlReaderException {
		String parsedHtml = HtmlMapper.parseUserAddingHtml(null, null, PWRESETMAIL);
		assertFalse(parsedHtml.contains("{userName}"));
		assertFalse(parsedHtml.contains(JPAUserEntity1.getKey()));
	}

	@Test(expected = HtmlReaderException.class)
	public void testParseUserAddingHtmlUsingNonExistingInputFile() throws HtmlReaderException {
		HtmlMapper.parseUserAddingHtml(JPAUserEntity1.getKey(), DESCRIPTION, NONEXISTINGFile);
	}

	@Test
	public void testParseBillAddingHtmlUsingSimpleFile() throws HtmlReaderException {
		String parsedHtml = HtmlMapper.parseBillAddingHtml(JPAUserEntity1.getKey(), AMOUNT, DESCRIPTION,
				BILLADDINGHTMLFile);
		assertFalse(parsedHtml.contains("{userName}"));
		assertTrue(parsedHtml.contains(JPAUserEntity1.getKey()));
	}

	@Test
	public void testParseBillAddingHtmlUsingSimpleFileNullInputs() throws HtmlReaderException {
		String parsedHtml = HtmlMapper.parseBillAddingHtml(null, null, null, BILLADDINGHTMLFile);
		assertTrue(parsedHtml.contains("{userName}"));
	}

	@Test
	public void testParseBillAddingHtmlUsingSimpleFileNoFindings() throws HtmlReaderException {
		String parsedHtml = HtmlMapper.parseBillAddingHtml(null, null, null, PWRESETMAIL);
		assertFalse(parsedHtml.contains("{userName}"));
		assertFalse(parsedHtml.contains(JPAUserEntity1.getKey()));
	}

	@Test(expected = HtmlReaderException.class)
	public void testParseBillAddingHtmlUsingNonExistingInputFile() throws HtmlReaderException {
		HtmlMapper.parseBillAddingHtml(JPAUserEntity1.getKey(), AMOUNT, DESCRIPTION, NONEXISTINGFile);
	}

	@Test
	public void testParseBillinggroupReportHtmlUsingSimpleFile() throws HtmlReaderException {
		String parsedHtml = HtmlMapper.parseBillinggroupResportHtml(DESCRIPTION, BILLINGGROUPREPORT,
				BILLINGGROUPREPORTHTMLFile);
		assertFalse(parsedHtml.contains("{billinggroupReport}"));
		assertTrue(parsedHtml.contains(BILLINGGROUPREPORT));
	}

	@Test
	public void testParseBillinggroupReportHtmlUsingSimpleFileNullInputs() throws HtmlReaderException {
		String parsedHtml = HtmlMapper.parseBillinggroupResportHtml(null, null, BILLINGGROUPREPORTHTMLFile);
		assertTrue(parsedHtml.contains("{billinggroupReport}"));
	}

	@Test
	public void testParseBillinggroupReportHtmlUsingSimpleFileNoFindings() throws HtmlReaderException {
		String parsedHtml = HtmlMapper.parseBillinggroupResportHtml(null, null, PWRESETMAIL);
		assertFalse(parsedHtml.contains("{billinggroupReport}"));
		assertFalse(parsedHtml.contains(BILLINGGROUPREPORT));
	}

	@Test(expected = HtmlReaderException.class)
	public void testParseBillinggroupReportHtmlUsingNonExistingInputFile() throws HtmlReaderException {
		HtmlMapper.parseBillinggroupResportHtml(DESCRIPTION, BILLINGGROUPREPORT, NONEXISTINGFile);
	}
}
