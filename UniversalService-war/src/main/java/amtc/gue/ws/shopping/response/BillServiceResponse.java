package amtc.gue.ws.shopping.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import amtc.gue.ws.base.response.ServiceResponse;
import amtc.gue.ws.shopping.inout.Bill;
import io.swagger.annotations.ApiModel;

/**
 * JAXB object for the BillServiceResponse complex type
 * 
 * @author Thomas
 *
 */
@ApiModel
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class BillServiceResponse extends ServiceResponse {
	@XmlElement
	private List<Bill> bills;

	// Getters and Setters
	public List<Bill> getBills() {
		if (bills == null) {
			bills = new ArrayList<>();
		}
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
}
