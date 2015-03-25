package amtc.gue.ws.books.inout;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Item {
	
	// textual description of the item
	@XmlElement(name = "description", required = false, nillable = true)
	protected String description;

	/**
	 * Getter for the textual descriptn
	 * @return textual description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter for the textual description
	 * @param description texual description of the item
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
