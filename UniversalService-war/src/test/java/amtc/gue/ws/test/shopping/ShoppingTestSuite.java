package amtc.gue.ws.test.shopping;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import amtc.gue.ws.test.shopping.persistence.dao.BillDAOObjectifyTest;
import amtc.gue.ws.test.shopping.persistence.dao.BillinggoupDAOObjectifyTest;
import amtc.gue.ws.test.shopping.persistence.dao.ShopDAOObjectifyTest;
import amtc.gue.ws.test.shopping.util.ShopPersistenceDelegatorUtilTest;
import amtc.gue.ws.test.shopping.util.mapper.ShoppingServiceEntityMapperTest;

@RunWith(Suite.class)
@SuiteClasses({ BillinggoupDAOObjectifyTest.class, BillDAOObjectifyTest.class, ShopDAOObjectifyTest.class,
		ShoppingServiceEntityMapperTest.class, ShopPersistenceDelegatorUtilTest.class })
public class ShoppingTestSuite {

}
