package amtc.gue.ws.base.inout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB object for the Users complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Users {
	@XmlElement
	private List<User> users;

	// Getters and Setters
	public List<User> getUsers() {
		if (this.users == null) {
			this.users = new ArrayList<>();
		}
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}
