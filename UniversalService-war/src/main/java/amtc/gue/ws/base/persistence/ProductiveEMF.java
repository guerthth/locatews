package amtc.gue.ws.base.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class that holds the EntityManagerFactory instance
 * 
 * @author Thomas
 *
 */
public class ProductiveEMF implements EMF {

	/** EntityManagerFactory for gae-jpa persistence unit */
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("gae-jpa");

	public ProductiveEMF() {
		// default constructor
	}

	/**
	 * Method returning the instance of the productive EntitiyManagerFactory
	 * 
	 * @return the EntityManagerFactory Instance
	 */
	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return emfInstance;
	}

}
