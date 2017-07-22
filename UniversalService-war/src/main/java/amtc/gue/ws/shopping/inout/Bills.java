package amtc.gue.ws.shopping.inout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB object for the Bills complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Bills {
	@XmlElement
	private List<Bill> bills;

	// Getters and Setters
	public List<Bill> getBills() {
		if (this.bills == null) {
			this.bills = new ArrayList<>();
		}
		return this.bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
}
