package persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import amtc.gue.ws.books.persistence.EMF;

/**
 * Class that holds the EntityManagerFactory for testing puposes
 * 
 * @author Thomas
 *
 */
public class TestEMF implements EMF {
	
	/**	EntityManagerFactory for gae-jpa-test persistence unit */
	private static final EntityManagerFactory testEmfInstance = 
			Persistence.createEntityManagerFactory("gae-jpa-test");
	
	/**
	 * Constructor
	 */
	public TestEMF() {
		
	}
	
	@Override
	public EntityManagerFactory getEntityManagerFactory(){
		return testEmfInstance;
	}
}
