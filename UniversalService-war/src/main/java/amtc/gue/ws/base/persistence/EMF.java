package amtc.gue.ws.base.persistence;

import javax.persistence.EntityManagerFactory;

/**
 * Persistence Interface
 * 
 * @author Thomas
 *
 */
public interface EMF {
	
	/**
	 * Method returning the instance of the EntitiyManagerFactory
	 * 
	 * @return the EntityManagerFactory Instance
	 */
	EntityManagerFactory getEntityManagerFactory();

}
