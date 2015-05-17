package amtc.gue.ws.books.service.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * Abstract class for webservice responses
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class AbstractResponse {
	
	/** Response status */
	@XmlElement(name = "responseStatus", required = true, nillable = false)
	protected int responseStatus;
	
	/** Response message */
	@XmlElement(name = "responseMessage", required = true, nillable = false)
	protected String reponseMessage;
	
	/**
	 * Getter for status
	 * 
	 * @return the status code
	 */
	public int getResponseStatus() {
		return responseStatus;
	}

	/**
	 * Setter for status
	 * 
	 * @param responseStatus the status code
	 */
	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	/**
	 * Getter for the response message
	 * 
	 * @return the message
	 */
	public String getReponseMessage() {
		return reponseMessage;
	}

	/**
	 * Setter for the response message
	 * 
	 * @param reponseMessage the message
	 */
	public void setReponseMessage(String reponseMessage) {
		this.reponseMessage = reponseMessage;
	}
	
	
}
