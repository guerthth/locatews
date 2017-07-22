package amtc.gue.ws.shopping.inout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB object for the Billinggroups complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Billinggroups {
	@XmlElement
	private List<Billinggroup> billinggroups;

	// Getters and Setters
	public List<Billinggroup> getBillinggroups() {
		if (billinggroups == null) {
			billinggroups = new ArrayList<>();
		}
		return billinggroups;
	}

	public void setBillinggroups(List<Billinggroup> billinggroups) {
		this.billinggroups = billinggroups;
	}
}
