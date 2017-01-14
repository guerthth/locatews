package amtc.gue.ws.base.persistence;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Class that holds the EntityManagerFactory instance
 * for non alloweg xg transactions
 * 
 * @author Thomas
 *
 */
public class NonXGTransactionsEMF implements EMF{

	/** EntityManagerFactory for gae-jpa persistence unit */
	private static final EntityManagerFactory emfInstance = Persistence
			.createEntityManagerFactory("gae-jpa-XGTransactions-not-enabled");
	
	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return emfInstance;
	}

}
