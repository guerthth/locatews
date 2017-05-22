package amtc.gue.ws.tournament.persistence.model.player.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;

/**
 * Model for Players stored to the datastore
 * 
 * @author Thomas
 *
 */
@Entity
@Table(name = "player")
public class GAEJPAPlayerEntity extends GAEPlayerEntity {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "playerName")
	private String playerName;
	@Column(name = "description")
	private String description;

	@Override
	public String getKey() {
		return playerName;
	}

	@Override
	public void setKey(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}
}
