package amtc.gue.ws.shopping.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import amtc.gue.ws.base.response.ServiceResponse;
import amtc.gue.ws.shopping.inout.Shop;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * JAXB object for the PlayerServiceResponse complex type
 * 
 * @author Thomas
 *
 */
@ApiModel
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class ShopServiceResponse extends ServiceResponse {
	@XmlElement(name = "shops")
	private List<Shop> shops;

	// Getters and Setters
	public List<Shop> getShops() {
		return shops;
	}

	@ApiModelProperty(position = 1, required = true, value = "List of shops")
	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}
}
