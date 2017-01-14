package amtc.gue.ws.tournament.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.datanucleus.api.jpa.annotations.Extension;

import amtc.gue.ws.base.persistence.model.GAEPersistenceEntity;

/**
 * Model for Players stored to the datastore
 * 
 * @author Thomas
 *
 */
@Entity
@Table(name = "player")
public class GAEJPAPlayerEntity extends GAEPersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Extension(vendorName = "datanucleus", key = "gae.encoded-pk", value = "true")
	@Column(name = "playerId")
	private String playerId;
	@Column(name = "playerName")
	private String playerName;

	@Override
	public String getKey() {
		return this.playerId;
	}

	@Override
	public void setKey(String playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
}
