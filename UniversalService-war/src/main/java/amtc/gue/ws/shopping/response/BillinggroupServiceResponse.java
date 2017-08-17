package amtc.gue.ws.shopping.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import amtc.gue.ws.base.response.ServiceResponse;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * JAXB object for the BillinggroupServiceResponse complex type
 * 
 * @author Thomas
 *
 */
@ApiModel
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class BillinggroupServiceResponse extends ServiceResponse {
	@XmlElement(name = "billinggroups")
	private List<GAEBillinggroupEntity> billinggroups;

	// Getters and Setter
	public List<GAEBillinggroupEntity> getBillinggroups() {
		return billinggroups;
	}

	@ApiModelProperty(position = 1, required = true, value = "List of billinggoups")
	public void setBillinggroups(List<GAEBillinggroupEntity> billinggroups) {
		this.billinggroups = billinggroups;
	}
}
