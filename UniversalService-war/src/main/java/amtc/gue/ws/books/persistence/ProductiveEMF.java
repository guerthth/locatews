package amtc.gue.ws.books.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class that holds the EntityManagerFactory instance
 * 
 * @author Thomas
 *
 */
public class ProductiveEMF implements EMF {
	
	/**	EntityManagerFactory for gae-jpa persistence unit */
	private static final EntityManagerFactory emfInstance = 
			Persistence.createEntityManagerFactory("gae-jpa");
	
	/**
	 * Constructor
	 */
	public ProductiveEMF(){
		
	}
	
	@Override
	public EntityManagerFactory getEntityManagerFactory(){
		return emfInstance;
	}
	
}
