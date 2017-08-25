package amtc.gue.ws.shopping.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import amtc.gue.ws.base.response.ServiceResponse;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

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
	@XmlElement(name = "bills")
	private List<GAEBillEntity> bills;

	// Getters and Setters
	public List<GAEBillEntity> getBills() {
		return bills;
	}

	@ApiModelProperty(position = 1, required = true, value = "List of bills")
	public void setBills(List<GAEBillEntity> bills) {
		this.bills = bills;
	}
}
