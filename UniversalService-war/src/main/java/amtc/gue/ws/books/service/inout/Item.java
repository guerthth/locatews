package amtc.gue.ws.books.service.inout;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "description"})
public class Item {

	// id of the item
	@XmlElement(name = "id", required = false, nillable = true)
	private Long id;

	// textual description of the item
	@XmlElement(name = "description", required = false, nillable = true)
	protected String description;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
