package amtc.gue.ws.shopping.inout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import amtc.gue.ws.base.inout.User;

/**
 * JAXB object for the Billinggroup complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "billinggroup", propOrder = { "billinggroupId", "description", "websafeKey", "users", "bills" })
public class Billinggroup {
	@XmlElement(name = "billinggroupId", required = false, nillable = true)
	private String billinggroupId;
	@XmlElement(name = "description", required = false, nillable = true)
	private String description;
	@XmlElement(name="websafeKey", required = false, nillable = true)
	private String websafeKey;
	@XmlElement(name = "users", required = false, nillable = true)
	private List<User> users;
	@XmlElement(name = "bills", required = false, nillable = true)
	private List<Bill> bills;

	public Billinggroup() {

	}

	public Billinggroup(final String billinggroupId, final String userId) {
		this.billinggroupId = billinggroupId;
		if (userId != null) {
			amtc.gue.ws.base.inout.User user = new amtc.gue.ws.base.inout.User();
			user.setId(userId);
			users = new ArrayList<>();
			users.add(user);
		}
	}

	// Getters and Setters
	public String getBillinggroupId() {
		return billinggroupId;
	}

	public void setBillinggroupId(String billinggroupId) {
		this.billinggroupId = billinggroupId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebsafeKey() {
		return websafeKey;
	}

	public void setWebsafeKey(String websafeKey) {
		this.websafeKey = websafeKey;
	}

	public List<User> getUsers() {
		if (users == null) {
			users = new ArrayList<>();
		}
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public List<Bill> getBills() {
		if (bills == null) {
			bills = new ArrayList<>();
		}
		return bills;
	}

	public void setBills(List<Bill> bills) {
		this.bills = bills;
	}
}
