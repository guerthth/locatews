package amtc.gue.ws.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import amtc.gue.ws.base.exception.HtmlReaderException;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;

/**
 * Utility class for the UserMailDelegator
 * 
 * @author Thomas
 *
 */
public class HtmlMapper {
	private static BufferedReader br = null;

	/**
	 * Method mapping an HTML File to a String using a simple InputStream
	 * 
	 * @param user
	 *            a specific UserEntity
	 * @param htmlFile
	 *            the htmlFile that should be mapped to a String
	 * @return the String representing the HTML file content. The placeholder
	 *         {userId} is replaced by the actual userName
	 * @throws HtmlReaderException
	 *             when an issue occures while trying to map the HTML File to a
	 *             String
	 */
	public static String mapHtmlToString(GAEUserEntity user, String htmlFile) throws HtmlReaderException {
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			InputStream is = HtmlMapper.class.getResourceAsStream(htmlFile);
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line.replace("{userId}", user.getKey()));
			}
			br.close();
		} catch (Exception e) {
			throw new HtmlReaderException(htmlFile, e);
		}
		return sb.toString();
	}

	/**
	 * Method parsing an HTML File using JSoup
	 * 
	 * @param user
	 *            a specific UserEntity
	 * @param htmlFile
	 *            the htmlFile that should be mapped to a String
	 * @return the String representing the parsed HTML. Only the content of the
	 *         <body> tag is returned. The placeholder {userId} is replaced by
	 *         the actual userName
	 * @throws HtmlReaderException
	 *             when an issue occures while trying to map the HTML File to a
	 *             String
	 */
	public static String parseHtml(GAEUserEntity user, String htmlFile) throws HtmlReaderException {
		String parsedHtml;
		try {
			URI htmlFileURI = HtmlMapper.class.getResource(htmlFile).toURI();
			File inputFile = new File(htmlFileURI);
			Document doc = Jsoup.parse(inputFile, null);
			Elements elements = doc.getElementsByTag("body");
			parsedHtml = elements.html();
			if (parsedHtml.contains("{userId}") && user.getKey() != null) {
				parsedHtml = parsedHtml.replace("{userId}", user.getKey());
			}
			if (parsedHtml.contains("{userName}") && user.getUserName() != null) {
				parsedHtml = parsedHtml.replace("{userName}", user.getUserName());
			}
			if (parsedHtml.contains("{userPassword}") && user.getPassword() != null) {
				parsedHtml = parsedHtml.replace("{userPassword}", user.getPassword());
			}
			if (parsedHtml.contains("{userRoles}") && user.getRoles() != null) {
				parsedHtml = parsedHtml.replace("{userRoles}", "" + user.getRoles());
			}
		} catch (Exception e) {
			throw new HtmlReaderException(htmlFile, e);
		}
		return parsedHtml;
	}
}
