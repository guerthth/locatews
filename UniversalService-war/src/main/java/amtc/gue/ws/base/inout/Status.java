package amtc.gue.ws.base.inout;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * JAXB object for the Status complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "status", propOrder = { "statusCode", "statusMessage", "statusReason" })
public class Status {

	// code of the responded status
	@XmlElement(name = "statusCode", required = true, nillable = false)
	private int statusCode;

	// message of the responded status
	@XmlElement(name = "statusMessage", required = true, nillable = false)
	private String statusMessage;

	// message representing reason of occured issues
	@XmlElement(name = "statusReason", required = false, nillable = true)
	private String statusReason;

	// Getters and Setters
	public String getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
}
