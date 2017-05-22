package amtc.gue.ws.tournament.persistence.model.player;

import amtc.gue.ws.base.persistence.model.PersistenceEntity;

/**
 * Player Persistence Entity for GAE datastore
 * 
 * @author Thomas
 *
 */
public abstract class GAEPlayerEntity extends PersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Getter for the description of the PlayerEntity
	 * 
	 * @return description of the PlayerEntity
	 */
	public abstract String getDescription();

	/**
	 * Setter for the description of the PlayerEntity
	 * 
	 * @param description
	 *            of the PlayerEntity
	 */
	public abstract void setDescription(String description);
}
