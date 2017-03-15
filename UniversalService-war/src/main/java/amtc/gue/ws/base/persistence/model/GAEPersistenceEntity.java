package amtc.gue.ws.base.persistence.model;

import java.util.HashSet;
import java.util.Set;

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
