package amtc.gue.ws.test.base.delegate.persist;

import amtc.gue.ws.test.base.delegate.IBaseDelegatorTest;

/**
 * Interface holding all methods that should be applied for persistence
 * delegator testing
 * 
 * @author Thomas
 *
 */
public interface IJPAPersistenceDelegatorTest extends IBaseDelegatorTest {
	/**
	 * Testing persistence delegator ADD functionality using correct input
	 * values (Success Scenario) using JPA DAO
	 */
	public void testDelegateJPAAddUsingCorrectInput();

	/**
	 * Testing persistence delegator ADD functionality using incorrect DAO setup
	 * (DAO method throws exception) using JPA DAO
	 */
	public void testDelegateJPAAddUsingIncorrectDAOSetup();

	/**
	 * Testing persistence delegator ADD functionality with invalid input type
	 * (Input object for persist method has incorrect type) using JPA DAO
	 */
	public void testDelegateJPAAddUsingInvalidInput();

	/**
	 * Testing persistence delegator DELETE functionality using correct input
	 * values (Success Scenario using entitiyId to find the entity that should
	 * be removed) using JPA DAO
	 */
	public void testDelegateJPADeleteUsingCorrectIdInput();

	/**
	 * Testing persistence delegator DELETE functionality using objects that are
	 * not persisted and therefore can not be deleted using JPA DAO
	 */
	public void testDelegateJPADeleteUsingNonExistingObjects();

	/**
	 * Testing persistence delegator DELETE functionality when null is returned
	 * after retrieving using JPA DAO
	 */
	public void testDelegateJPADeleteUsingNullObjects();

	/**
	 * Testing persistence delegator DELETE functionality using incorrect DAO
	 * setup (DAO method throws exception) using JPA DAO
	 */
	public void testDelegateJPADeleteUsingIncorrectDAOSetup();

	/**
	 * Testing persistence delegator DELETE functionality using correct input
	 * values (Negative Scenario. Exception on entity deletion) using JPA DAO
	 */
	public void testDelegateJPADeleteDeletionFail();

	/**
	 * Testing persistence delegator DELETE functionality using correct input
	 * values (Negative Scenario. Exception on entity retrieval) using JPA DAO
	 */
	public void testDelegateJPADeleteRetrievalFail();

	/**
	 * Testing persistence delegator DELETE functionality with invalid input
	 * type (Input object for remove method has incorrect type) using JPA DAO
	 */
	public void testDelegateJPADeleteUsingInvalidInput();

	/**
	 * Testing persistence delegator READ functionality using correct input
	 * values (Success Scenario) using JPA DAO
	 */
	public void testDelegateJPAReadUsingCorrectInput();

	/**
	 * Testing persistence delegator READ functionality using incorrect DAO
	 * setup (DAO method throws exception) using JPA DAO
	 */
	public void testDelegateJPAReadUsingIncorrectDAOSetup();

	/**
	 * Testing persistence delegator READ functionality with invalid (or
	 * unrecognized) inputtype using JPA DAO
	 */
	public void testDelegateJPAReadUsingInvalidInput();

	/**
	 * Testing persistence delegator UPDATE functionality using correct input
	 * values (Success Scenario) using JPA DAO
	 */
	public void testDelegateJPAUpdateUsingCorrectInput();

	/**
	 * Testing persistence delegator UPDATE functionality using incorrect DAO
	 * setup (DAO method throws exception) using JPA DAO
	 */
	public void testDelegateJPAUpdateUsingIncorrectDAOSetup();

	/**
	 * Testing persistence delegator UPDATE functionality with invalid (or
	 * unrecognized) inputtype using JPA DAO
	 */
	public void testDelegateJPAUpdateUsingInvalidInput();
}
