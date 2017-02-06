package amtc.gue.ws.test.base.delegate.persist;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.input.DelegatorInput;
import amtc.gue.ws.base.util.DelegatorTypeEnum;

/**
 * Abstract Class holding all methods that should be used for testing
 * persistence delegators implementation happens in the specific testclasses
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class BasePersistenceDelegatorTest {

	protected static DelegatorInput unrecognizedDelegatorInput;
	protected static DelegatorInput nullDelegatorInput;
	protected static DelegatorInput invalidAddDelegatorInput;
	protected static DelegatorInput invalidDeleteDelegatorInput;
	protected static DelegatorInput invalidReadDelegatorInput;
	protected static final String TESTKEY = "testKey";

	/**
	 * Testing persistence delegator functionality with null input type
	 */
	@Test
	public abstract void testDelegateUsingNullInput();

	/**
	 * Testing persistence delegator functionality with unrecognized input type
	 */
	@Test
	public abstract void testDelegateUsingUnrecognizedInputType();

	/**
	 * Testing persistence delegator ADD functionality using correct input
	 * values (Success Scenario)
	 */
	@Test
	public abstract void testDelegateAddUsingCorrectInput();

	/**
	 * Testing persistence delegator ADD functionality using incorrect DAO setup
	 * (DAO method throws exception)
	 */
	@Test
	public abstract void testDelegateAddUsingIncorrectDAOSetup();

	/**
	 * Testing persistence delegator ADD functionality with invalid input type
	 * (Input object for persist method has incorrect type)
	 */
	@Test
	public abstract void testDelegateAddUsingInvalidInput();

	/**
	 * Testing persistence delegator DELETE functionality using correct input
	 * values (Success Scenario using entitiyId to find the entity that should
	 * be removed)
	 */
	@Test
	public abstract void testDelegateDeleteUsingCorrectIdInput();

	/**
	 * Testing persistence delegator DELETE functionality using objects that are
	 * not persisted and therefore can not be deleted
	 */
	@Test
	public abstract void testDelegateDeleteUsingNonExistingObjects();

	/**
	 * Testing persistence delegator DELETE functionality when null is returned
	 * after retrieving
	 */
	@Test
	public abstract void testDelegateDeleteUsingNullObjects();

	/**
	 * Testing persistence delegator DELETE functionality using incorrect DAO
	 * setup (DAO method throws exception)
	 */
	@Test
	public abstract void testDelegateDeleteUsingIncorrectDAOSetup();

	/**
	 * Testing persistence delegator DELETE functionality using correct input
	 * values (Negative Scenario. Exception on entity deletion)
	 */
	@Test
	public abstract void testDelegateDeleteDeletionFail();
	
	/**
	 * Testing persistence delegator DELETE functionality using correct input
	 * values (Negative Scenario. Exception on entity retrieval)
	 */
	@Test
	public abstract void testDelegateDeleteRetrievalFail();

	/**
	 * Testing persistence delegator DELETE functionality with invalid input
	 * type (Input object for remove method has incorrect type)
	 */
	@Test
	public abstract void testDelegateDeleteUsingInvalidInput();

	/**
	 * Testing persistence delegator READ functionality using correct input
	 * values (Success Scenario)
	 */
	@Test
	public abstract void testDelegateReadUsingCorrectInput();

	/**
	 * Testing persistence delegator READ functionality using incorrect DAO
	 * setup (DAO method throws exception)
	 */
	@Test
	public abstract void testDelegateReadUsingIncorrectDAOSetup();

	/**
	 * Testing persistence delegator READ functionality with invalid (or
	 * unrecognized) inputtype
	 */
	@Test
	public abstract void testDelegateReadUsingInvalidInput();

	/**
	 * Method setting up delegator inputs used by all persistence delegator
	 * testclasses
	 */
	protected static void setUpBaseDelegatorInputs() {
		// DelegatorInput with null input
		nullDelegatorInput = null;

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

		// DelegatorInput with unrecognized input type
		unrecognizedDelegatorInput = new DelegatorInput();
		unrecognizedDelegatorInput.setInputObject(null);
		unrecognizedDelegatorInput.setType(DelegatorTypeEnum.MAIL);
	}
}
