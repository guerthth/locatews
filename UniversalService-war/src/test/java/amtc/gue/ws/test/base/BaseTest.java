package amtc.gue.ws.test.base;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.dev.HighRepJobPolicy;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalUserServiceTestConfig;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cache.AsyncCacheFilter;
import com.googlecode.objectify.util.Closeable;

import amtc.gue.ws.base.delegate.input.DelegatorInput;
import amtc.gue.ws.base.util.DelegatorTypeEnum;

/**
 * Class holding common data for all Tests
 * 
 * @author Thomas
 *
 */
public abstract class BaseTest {
	protected static DelegatorInput unrecognizedDelegatorInput;
	protected static DelegatorInput nullDelegatorInput;
	protected static DelegatorInput invalidAddDelegatorInput;
	protected static DelegatorInput invalidDeleteDelegatorInput;
	protected static DelegatorInput invalidReadDelegatorInput;
	protected static DelegatorInput invalidUpdateDelegatorInput;

	static final class ConsistentHighRepPolicy implements HighRepJobPolicy {
		@Override
		public boolean shouldRollForwardExistingJob(Key key) {
			return true;
		}

		@Override
		public boolean shouldApplyNewJob(Key key) {
			return true;
		}
	}

	// top-level point configuration for all local services that might
	// be accessed. set with high replication
	// private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
	// new LocalDatastoreServiceTestConfig()
	// .setDefaultHighRepJobPolicyUnappliedJobPercentage(100));
	// private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
	// new LocalDatastoreServiceTestConfig());
	// see
	// http://codeover.org/questions/21185922/app-engine-cloud-endpoints-unit-testing-issues-with-data-nucleus-is-it-possibl
	protected final LocalServiceTestHelper helper = new LocalServiceTestHelper(
			new LocalDatastoreServiceTestConfig().setAlternateHighRepJobPolicyClass(ConsistentHighRepPolicy.class),
			new LocalUserServiceTestConfig()).setEnvIsAdmin(false).setEnvIsLoggedIn(true).setEnvEmail("test@test.com");
	protected Closeable session;

	/**
	 * Method setting up helper objects for persistence testing
	 */
	protected void setupDBHelpers() {
		session = ObjectifyService.begin();
		helper.setUp();
	}

	/**
	 * Method tearing down persistence helper objects
	 */
	protected void tearDownDBHelpers() {
		AsyncCacheFilter.complete();
		session.close();
		helper.tearDown();
	}

	/**
	 * Method setting up delegator inputs
	 */
	protected static void setUpDelegatorInputs() {
		// DelegatorInput with null input
		nullDelegatorInput = null;

		// DelegatorInput with unrecognized input type
		unrecognizedDelegatorInput = new DelegatorInput();
		unrecognizedDelegatorInput.setInputObject(null);
		unrecognizedDelegatorInput.setType(DelegatorTypeEnum.UNRECOGNIZED);

		// DelegatorInput with invalid Add input
		invalidAddDelegatorInput = new DelegatorInput();
		invalidAddDelegatorInput.setInputObject(null);
		invalidAddDelegatorInput.setType(DelegatorTypeEnum.ADD);

		// DelegatorInput with invalid Delete input
		invalidDeleteDelegatorInput = new DelegatorInput();
		invalidDeleteDelegatorInput.setInputObject(null);
		invalidDeleteDelegatorInput.setType(DelegatorTypeEnum.DELETE);

		// DelegatorInput with invalid Read input
		invalidReadDelegatorInput = new DelegatorInput();
		invalidReadDelegatorInput.setInputObject(null);
		invalidReadDelegatorInput.setType(DelegatorTypeEnum.READ);

		// DelegatorInput with invalid Update input
		invalidUpdateDelegatorInput = new DelegatorInput();
		invalidUpdateDelegatorInput.setInputObject(null);
		invalidUpdateDelegatorInput.setType(DelegatorTypeEnum.UPDATE);
	}
}
