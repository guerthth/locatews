package amtc.gue.ws.shopping.inout;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * JAXB object for the Billinggroup complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "billinggroup", propOrder = { "billinggroupId", "" })
public class Billinggroup {
	@XmlElement(name = "billinggroupId", required = false, nillable = true)
	private String billinggroupId;
	@XmlElement(name = "description", required = false, nillable = true)
	private String description;

	// Getters and Setters
	public String getBillinggroupId() {
		return billinggroupId;
	}

	public void setBillinggroupId(String billinggroupId) {
		this.billinggroupId = billinggroupId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
