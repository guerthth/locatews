package amtc.gue.ws.test.base.delegate.persist;

import amtc.gue.ws.test.base.delegate.IBaseDelegatorTest;

public interface IObjectifyPersistenceDelegatorTest extends IBaseDelegatorTest {

	/**
	 * Testing persistence delegator ADD functionality using correct input
	 * values (Success Scenario) using Objectify DAO
	 */
	public void testDelegateObjectifyAddUsingCorrectInput();

	/**
	 * Testing persistence delegator ADD functionality using incorrect DAO setup
	 * (DAO method throws exception) using Objectify DAO
	 */
	public void testDelegateObjectifyAddUsingIncorrectDAOSetup();

	/**
	 * Testing persistence delegator ADD functionality with invalid input type
	 * (Input object for persist method has incorrect type) using Objectify DAO
	 */
	public void testDelegateObjectifyAddUsingInvalidInput();

	/**
	 * Testing persistence delegator DELETE functionality using correct input
	 * values (Success Scenario using entitiyId to find the entity that should
	 * be removed) using Objectify DAO
	 */
	public void testDelegateObjectifyDeleteUsingCorrectIdInput();

	/**
	 * Testing persistence delegator DELETE functionality using objects that are
	 * not persisted and therefore can not be deleted using Objectify DAO
	 */
	public void testDelegateObjectifyDeleteUsingNonExistingObjects();

	/**
	 * Testing persistence delegator DELETE functionality when null is returned
	 * after retrieving using Objectify DAO
	 */
	public void testDelegateObjectifyDeleteUsingNullObjects();

	/**
	 * Testing persistence delegator DELETE functionality using incorrect DAO
	 * setup (DAO method throws exception) using Objectify DAO
	 */
	public void testDelegateObjectifyDeleteUsingIncorrectDAOSetup();

	/**
	 * Testing persistence delegator DELETE functionality using correct input
	 * values (Negative Scenario. Exception on entity deletion) using Objectify
	 * DAO
	 */
	public void testDelegateObjectifyDeleteDeletionFail();

	/**
	 * Testing persistence delegator DELETE functionality using correct input
	 * values (Negative Scenario. Exception on entity retrieval) using Objectify
	 * DAO
	 */
	public void testDelegateObjectifyDeleteRetrievalFail();

	/**
	 * Testing persistence delegator DELETE functionality with invalid input
	 * type (Input object for remove method has incorrect type) using Objectify
	 * DAO
	 */
	public void testDelegateObjectifyDeleteUsingInvalidInput();

	/**
	 * Testing persistence delegator READ functionality using correct input
	 * values (Success Scenario) using Objectify DAO
	 */
	public void testDelegateObjectifyReadUsingCorrectInput();

	/**
	 * Testing persistence delegator READ functionality using incorrect DAO
	 * setup (DAO method throws exception) using Objectify DAO
	 */
	public void testDelegateObjectifyReadUsingIncorrectDAOSetup();

	/**
	 * Testing persistence delegator READ functionality with invalid (or
	 * unrecognized) inputtype using Objectify DAO
	 */
	public void testDelegateObjectifyReadUsingInvalidInput();

	/**
	 * Testing persistence delegator UPDATE functionality using correct input
	 * values (Success Scenario) using Objectify DAO
	 */
	public void testDelegateObjectifyUpdateUsingCorrectInput();

	/**
	 * Testing persistence delegator UPDATE functionality using incorrect DAO
	 * setup (DAO method throws exception) using Objectify DAO
	 */
	public void testDelegateObjectifyUpdateUsingIncorrectDAOSetup();

	/**
	 * Testing persistence delegator UPDATE functionality with invalid (or
	 * unrecognized) inputtype using Objectify DAO
	 */
	public void testDelegateObjectifyUpdateUsingInvalidInput();
}
