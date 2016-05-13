package amtc.gue.ws.books.persistence.model.gae;

import amtc.gue.ws.books.persistence.model.PersistenceEntity;

/**
 * Persistence Entity for GAE datastores
 * 
 * @author Thomas
 *
 */
public abstract class GAEPersistenceEntity extends PersistenceEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public abstract String getKey();
	//public abstract Key getKey();

	public abstract void setKey(String key);
	//public abstract void setKey(Key key);
}
