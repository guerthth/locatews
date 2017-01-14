package amtc.gue.ws.tournament.inout;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * JAXB object for the Players complex type
 * 
 * @author Thomas
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Players {
	@XmlElement
	private List<Player> players;

	// Getters and Setters
	public List<Player> getPlayers() {
		if (this.players == null) {
			this.players = new ArrayList<>();
		}
		return this.players;
	}

	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
