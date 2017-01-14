package amtc.gue.ws.tournament.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import amtc.gue.ws.base.response.ServiceResponse;
import amtc.gue.ws.tournament.inout.Player;

/**
 * JAXB object for the PlayerServiceResponse complex type
 * 
 * @author Thomas
 *
 */
@ApiModel
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class PlayerServiceResponse extends ServiceResponse {
	
	@XmlElement(name = "players")
	private List<Player> players;

	// Getters and Setters
	public List<Player> getPlayers() {
		return this.players;
	}

	@ApiModelProperty(position=1, required=true,value="List of players")
	public void setPlayers(List<Player> players) {
		this.players = players;
	}

}
