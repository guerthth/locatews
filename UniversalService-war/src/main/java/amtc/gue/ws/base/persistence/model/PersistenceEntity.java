package amtc.gue.ws.base.persistence.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * PersistenceEntity representing entities that can be stored to a DB
 * 
 * @author Thomas
 *
 */
public abstract class PersistenceEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Getter for the ID of the PersistenceEntity
	 * 
	 * @return the ID of the PersistenceEntity
	 */
	public abstract String getKey();

	/**
	 * Setter for the ID of the PersistenceEntity
	 * 
	 * @param key
	 *            the ID of the PersistenceEntity
	 */
	public abstract void setKey(String key);

	/**
	 * Method returning a websafe representation of the
	 * GAEObjectifyBillinggroupEntity
	 * 
	 * @return websafe representation of the GAEObjectifyBillinggroupEntity
	 */
	public abstract String getWebsafeKey();
	
	/**
	 * Method clearing a set
	 * 
	 * @param set
	 *            the Set that should be cleared
	 */
	public void clearSet(Set<?> set) {
		if (set != null) {
			set.clear();
		} else {
			set = new HashSet<>();
		}
	}
}
