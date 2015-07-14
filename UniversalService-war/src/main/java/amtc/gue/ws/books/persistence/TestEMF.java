package amtc.gue.ws.books.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class that holds the EntitManagerFactory instance for testing
 * @author Thomas
 *
 */
public class TestEMF implements EMF {

	/** EntityManagerFactory for gae-jpa-test (testing) persistence unit */
	private static final EntityManagerFactory testEmfInstance = 
			Persistence.createEntityManagerFactory("gae-jpa-test");
	
	/**
	 * constructor
	 */
	public TestEMF(){
		
	}
	
	/**
	 * Method returning the instance of the testing EntitiyManagerFactory
	 * 
	 * @return the EntityManagerFactory Instance
	 */
	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return testEmfInstance;
	}

}
