package amtc.gue.ws.tournament.persistence.model.player.objectify;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;

/**
 * Model for Objectify Players stored to the datastore
 * 
 * @author Thomas
 *
 */
@Entity
public class GAEObjectifyPlayerEntity extends GAEPlayerEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	private String playerName;
	@Index
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
