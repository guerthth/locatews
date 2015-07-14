package amtc.gue.ws.books.service.inout.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB object for the ServiceResponse complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceResponse {
	
	@XmlElement(name = "status", required = true, nillable = true)
	public Status status;

	/**
	 * Getter for the status
	 * @return response status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * Setter for the status
	 * @param status responsestatus
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
