package amtc.gue.ws.shopping.inout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB object for the Shops complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Shops {
	@XmlElement
	private List<Shop> shops;

	// Getters and Setters
	public List<Shop> getShops() {
		if (shops == null) {
			shops = new ArrayList<>();
		}
		return shops;
	}

	public void setShops(List<Shop> shops) {
		this.shops = shops;
	}
}
