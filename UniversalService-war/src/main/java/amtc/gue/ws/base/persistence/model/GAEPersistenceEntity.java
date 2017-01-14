package amtc.gue.ws.base.persistence.model;


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

	public abstract void setKey(String key);
}
