package amtc.gue.ws.service;

import amtc.gue.ws.books.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.books.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.books.persistence.dao.DAOs;
import amtc.gue.ws.books.utils.PersistenceTypeEnum;
import amtc.gue.ws.books.utils.SpringContext;

/**
 * Abstract Service Class
 * 
 * @author Thomas
 *
 */
public abstract class Service {
	protected PersistenceDelegatorInput input;
	protected DAOs daos;

	/**
	 * Method setting up the respective delegator
	 * 
	 * @param persistenceDelegator
	 *            the delegator object that should be intitialized
	 * @param type
	 *            the input type
	 * @param inputObject
	 *            the input object
	 */
	protected void buildAndInitializePersistenceDelegator(
			AbstractPersistenceDelegator persistenceDelegator,
			PersistenceTypeEnum type, Object inputObject) {
		// setup input object and DAO implementations
		input = (PersistenceDelegatorInput) SpringContext.context
				.getBean("persistenceDelegatorInput");
		daos = (DAOs) SpringContext.context.getBean("daos");

		input.setType(type);
		input.setInputObject(inputObject);

		// intialize PersistenceDelegator
		persistenceDelegator.initialize(input, daos);
	}

}
