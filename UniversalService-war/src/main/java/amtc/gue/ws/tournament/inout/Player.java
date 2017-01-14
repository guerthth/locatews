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
@XmlType(name = "player", propOrder = { "playerName" })
public class Player extends Item {

	@XmlElement(name = "playerName", required = true, nillable = false)
	private String playerName;

	// Getters and Setters
	public String getPlayerName() {
		return this.playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

}
