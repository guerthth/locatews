package amtc.gue.ws.base.inout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Roles {
	@XmlElement(name = "roles")
	private List<String> roles;

	/**
	 * Getter for the list of roles
	 * 
	 * @return the list of roles
	 */
	public List<String> getRoles() {
		if (this.roles == null) {
			this.roles = new ArrayList<String>();
		}
		return this.roles;
	}

	/**
	 * Setter for the list list of roles
	 * 
	 * @param roles
	 *            the list of roles
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

}
