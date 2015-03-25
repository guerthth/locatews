package amtc.gue.ws.books.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Ckass that holds the EntityManagerFactory instance
 * @author Thomas
 *
 */
public final class EMF {
	
	private static final EntityManagerFactory emfInstance = 
			Persistence.createEntityManagerFactory("gae-jpa");
	
	private EMF(){}
	
	/**
	 * Method returning the instance of the EntityManagerFactory
	 * @return emfInstance
	 */
	public static EntityManagerFactory getEntityManagerFactory(){
		return emfInstance;
	}
}
