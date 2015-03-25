package amtc.gue.ws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "person", propOrder = {
		"firstName",
		"lastName"
})
public class Person {

	protected String firstName; 
	protected String lastName;
	
	/**
	 * Getter for firstName
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * Setter for firstName
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * Getter for lastName
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * Setter for lastName
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
}
