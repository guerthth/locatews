package amtc.gue.ws.test.shopping;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import amtc.gue.ws.test.shopping.delegate.persist.BillPersistenceDelegatorTest;
import amtc.gue.ws.test.shopping.delegate.persist.BillinggroupPersistenceDelegatorTest;
import amtc.gue.ws.test.shopping.delegate.persist.ShopPersistenceDelegatorTest;
import amtc.gue.ws.test.shopping.persistence.dao.BillDAOObjectifyTest;
import amtc.gue.ws.test.shopping.persistence.dao.BillinggoupDAOObjectifyTest;
import amtc.gue.ws.test.shopping.persistence.dao.ShopDAOObjectifyTest;
import amtc.gue.ws.test.shopping.util.BillPersistenceDelegatorUtilTest;
import amtc.gue.ws.test.shopping.util.BillinggroupPersistenceDelegatorUtilTest;
import amtc.gue.ws.test.shopping.util.ShopPersistenceDelegatorUtilTest;
import amtc.gue.ws.test.shopping.util.mapper.ShoppingServiceEntityMapperTest;

@RunWith(Suite.class)
@SuiteClasses({ BillinggoupDAOObjectifyTest.class, BillinggroupPersistenceDelegatorUtilTest.class,
		BillinggroupPersistenceDelegatorTest.class, BillinggroupServiceTest.class, BillDAOObjectifyTest.class,
		BillPersistenceDelegatorUtilTest.class, BillPersistenceDelegatorTest.class, ShopDAOObjectifyTest.class,
		ShopPersistenceDelegatorUtilTest.class, ShopPersistenceDelegatorTest.class, ShopServiceTest.class,
		ShoppingServiceEntityMapperTest.class })
public class ShoppingTestSuite {

}
