package amtc.gue.ws.books.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Ckass that holds the EntityManagerFactory instance
 * @author Thomas
 *
 */
public class EMF {
	
	private static final EntityManagerFactory emfInstance = 
			Persistence.createEntityManagerFactory("gae-jpa");
	
	/**
	 * Constructor
	 */
	public EMF(){
		
	}
	
	/**
	 * Method returning the instance of the EntityManagerFactory
	 * @return emfInstance
	 */
	public EntityManagerFactory getEntityManagerFactory(){
		return emfInstance;
	}
	
}
