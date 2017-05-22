package amtc.gue.ws.tournament.inout;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import amtc.gue.ws.base.inout.Item;

/**
 * JAXB object for the Player complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "player", propOrder = { "playerDescription" })
public class Player extends Item {

	@XmlElement(name = "playerDescription", required = true, nillable = false)
	private String playerDescription;

	// Getters and Setters
	public String getDescription() {
		return this.playerDescription;
	}

	public void setDescription(String playerDescription) {
		this.playerDescription = playerDescription;
	}

}
