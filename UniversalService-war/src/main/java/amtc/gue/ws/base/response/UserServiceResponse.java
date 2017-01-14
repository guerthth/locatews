package amtc.gue.ws.base.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import amtc.gue.ws.base.inout.User;

/**
 * JAXB object for the UserServiceResponse complex type
 * 
 * @author Thomas
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class UserServiceResponse extends ServiceResponse {
	@XmlElement(name = "users")
	private List<User> users;

	// Getters and Setters
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
