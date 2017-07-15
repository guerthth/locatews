package amtc.gue.ws.shopping.inout;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * JAXB object for the Shop complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "shop", propOrder = { "shopId", "shopName" })
public class Shop {
	@XmlElement(name = "shopId", required = false, nillable = true)
	private String shopId;
	@XmlElement(name = "shopName", required = false, nillable = true)
	private String shopName;

	// Getters and Setters
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}
