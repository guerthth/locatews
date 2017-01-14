package amtc.gue.ws.books.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import amtc.gue.ws.base.response.ServiceResponse;

/**
 * JAXB object for the TagServiceResponse complex type
 * 
 * @author Thomas
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class TagServiceResponse extends ServiceResponse {

	@XmlElement(name = "tags", nillable = true, required = false)
	private List<String> tags;

	// Getters and Setters
	public List<String> getTags() {
		return this.tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

}
