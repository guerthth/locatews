package amtc.gue.ws.base.inout;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * JAXB Object for Item type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id" })
public class Item {

	// id of the item
	@XmlElement(name = "id", required = false, nillable = true)
	private String id;

	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
