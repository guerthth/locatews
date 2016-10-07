package amtc.gue.ws.books.service.inout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB object for the Tags complex type
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Tags {
	
	@XmlElement(name = "tags")
	private List<String> tags;

	/**
	 * Setter for list Tags
	 * @param Tags list
	 */
	public List<String> getTags() {
		
		if(this.tags == null){
			this.tags = new ArrayList<>();
		}	
		return this.tags;
	}

	/**
	 * Setter for the Tags list
	 * @return Tags list
	 */
	public void setTags(List<String> tags) {
		
		this.tags = tags;
	}
}
