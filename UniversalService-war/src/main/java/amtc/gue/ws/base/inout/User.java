package amtc.gue.ws.base.inout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * JAXB object for the User complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "user", propOrder = {"id","userName","password","roles"})
public class User extends Item {
	@XmlElement(name = "userName", required = true, nillable = false)
	private String userName;
	@XmlElement(name = "password", required = true, nillable = false)
	private String password;
	@XmlElement(name = "roles")
	private List<String> roles;

	// Getters and Setters
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<String> getRoles() {
		if(this.roles == null){
			this.roles = new ArrayList<String>();
		}
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
