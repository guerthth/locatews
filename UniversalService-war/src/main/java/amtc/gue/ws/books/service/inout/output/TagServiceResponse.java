package amtc.gue.ws.books.service.inout.output;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import amtc.gue.ws.books.service.inout.Tags;

/**
 * JAXB object for the TagServiceResponse complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class TagServiceResponse extends ServiceResponse {
	
	@XmlElement(name = "tags", nillable = true, required = false)
	public Tags tags;

	// Getters and Setters
	public Tags getTags() {
		return tags;
	}

	public void setTags(Tags tags) {
		this.tags = tags;
	}
	
}
