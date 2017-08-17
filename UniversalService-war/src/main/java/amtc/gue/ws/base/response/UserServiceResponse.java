package amtc.gue.ws.base.response;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;

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
	private List<GAEUserEntity> users;

	// Getters and Setters
	public List<GAEUserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<GAEUserEntity> users) {
		this.users = users;
	}
}
