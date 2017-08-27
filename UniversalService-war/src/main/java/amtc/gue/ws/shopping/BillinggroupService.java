package amtc.gue.ws.shopping;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.Named;
import com.google.api.server.spi.config.ApiMethod.HttpMethod;
import com.google.api.server.spi.response.UnauthorizedException;
import com.google.appengine.api.users.User;

import amtc.gue.ws.Constants;
import amtc.gue.ws.base.Service;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.delegate.persist.AbstractPersistenceDelegator;
import amtc.gue.ws.base.inout.Status;
import amtc.gue.ws.base.persistence.model.role.GAERoleEntity;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.shopping.delegate.persist.BillinggroupPersistenceDelegator;
import amtc.gue.ws.shopping.delegate.persist.BillPersistenceDelegator;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.base.util.StatusMapper;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;
import amtc.gue.ws.base.util.mapper.objectify.UserServiceObjectifyEntityMapper;
import amtc.gue.ws.shopping.inout.Bill;
import amtc.gue.ws.shopping.inout.Billinggroup;
import amtc.gue.ws.shopping.inout.Billinggroups;
import amtc.gue.ws.shopping.inout.Bills;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.response.BillinggroupServiceResponse;
import amtc.gue.ws.shopping.util.ShoppingServiceErrorConstants;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

@Api(name = "shopping", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID,
		Constants.API_EXPLORER_CLIENT_ID }, description = "API for the Shopping backend application")
public class BillinggroupService extends Service {
	private static final Logger log = Logger.getLogger(BillinggroupService.class.getName());
	private static final String SCOPE = "shopping";
	private AbstractPersistenceDelegator billinggroupDelegator;
	private AbstractPersistenceDelegator billDelegator;

	public BillinggroupService() {
		super();
		billinggroupDelegator = (BillinggroupPersistenceDelegator) SpringContext.context
				.getBean("billinggroupPersistenceDelegator");
		billDelegator = (BillPersistenceDelegator) SpringContext.context.getBean("billPersistenceDelegator");
	}

	public BillinggroupService(AbstractPersistenceDelegator userDelegator,
			AbstractPersistenceDelegator billinggroupDelegator, AbstractPersistenceDelegator billDelegator) {
		super(userDelegator);
		this.billinggroupDelegator = billinggroupDelegator;
		this.billDelegator = billDelegator;
	}

	@ApiMethod(name = "addBillinggroups", path = "billinggroup", httpMethod = HttpMethod.POST)
	public BillinggroupServiceResponse addBillinggroups(final User user, Billinggroups billinggroups)
			throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		billinggroupDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.ADD, billinggroups);
		IDelegatorOutput bdOutput = billinggroupDelegator.delegate();
		BillinggroupServiceResponse response = ShoppingServiceEntityMapper
				.mapBdOutputToBillinggroupServiceResponse(bdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "getBillinggroups", path = "billinggoup", httpMethod = HttpMethod.GET)
	public BillinggroupServiceResponse getBillinggroups(final User user) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		billinggroupDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, null);
		IDelegatorOutput bdOutput = billinggroupDelegator.delegate();
		BillinggroupServiceResponse response = ShoppingServiceEntityMapper
				.mapBdOutputToBillinggroupServiceResponse(bdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "removeBillinggroup", path = "billinggroup/{billinggroupId}", httpMethod = HttpMethod.DELETE)
	public BillinggroupServiceResponse removeBillinggroup(final User user,
			@Named("billinggroupId") final String billinggroupId) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		Billinggroups billinggroupsToRemove = new Billinggroups();
		Billinggroup billinggroupToRemove = new Billinggroup();
		billinggroupToRemove.setBillinggroupId(billinggroupId);
		List<Billinggroup> billinggroupListToRemove = new ArrayList<>();
		billinggroupListToRemove.add(billinggroupToRemove);
		billinggroupsToRemove.setBillinggroups(billinggroupListToRemove);

		billinggroupDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.DELETE, billinggroupsToRemove);
		IDelegatorOutput bdOutput = billinggroupDelegator.delegate();
		BillinggroupServiceResponse response = ShoppingServiceEntityMapper
				.mapBdOutputToBillinggroupServiceResponse(bdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "updateBillinggroups", path = "billinggroup", httpMethod = HttpMethod.PUT)
	public BillinggroupServiceResponse updateBillinggroups(final User user, Billinggroups billinggroups)
			throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		billinggroupDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.UPDATE, billinggroups);
		IDelegatorOutput bdOutput = billinggroupDelegator.delegate();
		BillinggroupServiceResponse response = ShoppingServiceEntityMapper
				.mapBdOutputToBillinggroupServiceResponse(bdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "addUserToBillinggroup", path = "billinggroup/{billinggroupId}/user", httpMethod = HttpMethod.POST)
	public BillinggroupServiceResponse addUserToBillinggroup(final User user,
			@Named("billinggroupId") final String billinggroupId, amtc.gue.ws.base.inout.User userToAdd)
			throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		// load billinggroup (websafekey needed for objectify)
		billinggroupDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, billinggroupId);
		IDelegatorOutput billinggroupBdOutput = billinggroupDelegator.delegate();
		if (billinggroupBdOutput.getStatusCode() != ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_SUCCESS_CODE) {
			BillinggroupServiceResponse response = new BillinggroupServiceResponse();
			response.setStatus(StatusMapper.buildStatusForDelegatorOutput(billinggroupBdOutput));
			response.setBillinggroups(null);
			log.info(response.getStatus().getStatusMessage());
			return response;
		}
		GAEBillinggroupEntity billinggroupEntity = (GAEBillinggroupEntity) billinggroupBdOutput.getOutputObject();

		// load user (websafekey needed for objectify)
		userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, userToAdd.getId());
		IDelegatorOutput userBdOutput = userDelegator.delegate();
		if (userBdOutput.getStatusCode() != ErrorConstants.RETRIEVE_USER_SUCCESS_CODE) {
			BillinggroupServiceResponse response = new BillinggroupServiceResponse();
			response.setStatus(StatusMapper.buildStatusForDelegatorOutput(userBdOutput));
			response.setBillinggroups(null);
			log.info(response.getStatus().getStatusMessage());
			return response;
		}
		GAEUserEntity userEntity = (GAEUserEntity) userBdOutput.getOutputObject();

		// check if user is already registered to billinggroup
		if (isUserRegisteredToBillinggroup(billinggroupEntity, userToAdd)) {
			BillinggroupServiceResponse response = new BillinggroupServiceResponse();
			response.setBillinggroups(null);
			Status status = new Status();
			status.setStatusCode(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_FAILURE_CODE);
			status.setStatusMessage("User '" + userToAdd.getId() + "' already exists in billinggroup.");
			return response;
		}

		// if user isn't contained in billinggroup yet, add it
		billinggroupEntity.addToUsersOnly(userEntity);

		userDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.UPDATE, userEntity);
		IDelegatorOutput userUpdateBdOutput = userDelegator.delegate();
		if (userUpdateBdOutput.getStatusCode() != ErrorConstants.UPDATE_USER_SUCCESS_CODE) {
			BillinggroupServiceResponse response = new BillinggroupServiceResponse();
			response.setStatus(StatusMapper.buildStatusForDelegatorOutput(userUpdateBdOutput));
			response.setBillinggroups(null);
			log.info(response.getStatus().getStatusMessage());
			return response;
		}

		billinggroupDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.UPDATE, billinggroupEntity);
		IDelegatorOutput billinggroupUpdateBdOutput = billinggroupDelegator.delegate();
		if (billinggroupUpdateBdOutput
				.getStatusCode() != ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_SUCCESS_CODE) {
			BillinggroupServiceResponse response = new BillinggroupServiceResponse();
			response.setStatus(StatusMapper.buildStatusForDelegatorOutput(billinggroupUpdateBdOutput));
			response.setBillinggroups(null);
			log.info(response.getStatus().getStatusMessage());
			return response;
		}

		BillinggroupServiceResponse response = ShoppingServiceEntityMapper
				.mapBdOutputToBillinggroupServiceResponse(billinggroupUpdateBdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	// TODO this logic should be moved to the delegator
	@ApiMethod(name = "addBillToBillinggroup", path = "billinggroup/{billinggroupId}/bill")
	public BillinggroupServiceResponse addBillToBillinggroup(final User user,
			@Named("billinggroupId") final String billinggroupId, Bill billToAdd) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		// TODO Check if this works
		// TODO Billinggroup has websafekey Id
		// TODO Billinggroup has Bill with user sending the request
		Billinggroup billinggroupToUpdate = new Billinggroup();
		billinggroupToUpdate.setBillinggroupId(billinggroupId);
		billToAdd.setUser(UserServiceEntityMapper.mapAuthUserToUser(user));
		billinggroupToUpdate.getBills().add(billToAdd);

		// Call to persistence delegator only issues an update operation on an
		// existing billinggroup
		billinggroupDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.UPDATE, billinggroupToUpdate);
		IDelegatorOutput billinggroupBdOutput = billinggroupDelegator.delegate();
		
		// TODO Continue here
		
		/*
		// load billinggroup (websafekey needed for objectify)
		billinggroupDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, billinggroupId);
		IDelegatorOutput billinggroupBdOutput = billinggroupDelegator.delegate();
		if (billinggroupBdOutput.getStatusCode() != ShoppingServiceErrorConstants.RETRIEVE_BILLINGGROUP_SUCCESS_CODE) {
			BillinggroupServiceResponse response = new BillinggroupServiceResponse();
			response.setStatus(StatusMapper.buildStatusForDelegatorOutput(billinggroupBdOutput));
			response.setBillinggroups(null);
			log.info(response.getStatus().getStatusMessage());
			return response;
		}

		GAEBillinggroupEntity billinggroupEntity = (GAEBillinggroupEntity) billinggroupBdOutput.getOutputObject();

		// check if user creating the bill actually is included in the
		// billinggroup
		amtc.gue.ws.base.inout.User userToCheck = UserServiceEntityMapper.mapAuthUserToUser(user);
		if (!isUserRegisteredToBillinggroup(billinggroupEntity, userToCheck)) {
			BillinggroupServiceResponse response = new BillinggroupServiceResponse();
			Status status = new Status();
			status.setStatusCode(ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_FAILURE_CODE);
			status.setStatusMessage("User '" + userToCheck.getId() + "' is not registered in billinggroup.");
			response.setStatus(status);
			response.setBillinggroups(null);
			log.info(response.getStatus().getStatusMessage());
			return response;
		}

		// add bill
		Bills billsToAdd = new Bills();
		billsToAdd.getBills().add(billToAdd);
		billDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.ADD, billsToAdd);
		IDelegatorOutput billBdOutput = billDelegator.delegate();
		if (billBdOutput.getStatusCode() != ShoppingServiceErrorConstants.ADD_BILL_SUCCESS_CODE) {
			BillinggroupServiceResponse response = new BillinggroupServiceResponse();
			response.setStatus(StatusMapper.buildStatusForDelegatorOutput(billBdOutput));
			response.setBillinggroups(null);
			log.info(response.getStatus().getStatusMessage());
			return response;
		}

		GAEBillEntity billEntity = (GAEBillEntity) billBdOutput.getOutputObject();

		// check if bill is already included in billinggroup
		for (GAEBillEntity existingBill : billinggroupEntity.getBills()) {
			if (existingBill.getKey().equals(billEntity.getKey())) {
				BillinggroupServiceResponse response = new BillinggroupServiceResponse();
				response.setBillinggroups(null);
				Status status = new Status();
				status.setStatusMessage("Bill '" + billEntity.getKey() + "' already exists in billinggroup.");
				return response;
			}
		}

		billinggroupEntity.addToBillsOnly(billEntity);

		billDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.UPDATE, billEntity);
		IDelegatorOutput billUpdateBdOutput = billDelegator.delegate();
		if (billUpdateBdOutput.getStatusCode() != ShoppingServiceErrorConstants.UPDATE_BILL_SUCCESS_CODE) {
			BillinggroupServiceResponse response = new BillinggroupServiceResponse();
			response.setStatus(StatusMapper.buildStatusForDelegatorOutput(billUpdateBdOutput));
			response.setBillinggroups(null);
			log.info(response.getStatus().getStatusMessage());
			return response;
		}

		billinggroupDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.UPDATE, billinggroupEntity);
		billinggroupBdOutput = billinggroupDelegator.delegate();
		if (billinggroupBdOutput.getStatusCode() != ShoppingServiceErrorConstants.UPDATE_BILLINGGROUP_SUCCESS_CODE) {
			BillinggroupServiceResponse response = new BillinggroupServiceResponse();
			response.setStatus(StatusMapper.buildStatusForDelegatorOutput(billinggroupBdOutput));
			response.setBillinggroups(null);
			log.info(response.getStatus().getStatusMessage());
			return response;
		}
		*/

		BillinggroupServiceResponse response = ShoppingServiceEntityMapper
				.mapBdOutputToBillinggroupServiceResponse(billinggroupBdOutput);
		log.info(response.getStatus().getStatusMessage());
		
		return response;
	}

	/**
	 * Method checking if a specific userEntity is already registered to the
	 * billinggroupEntity
	 * 
	 * @param billinggroupEntity
	 *            the billinggroupEntity that is checked for registered users
	 * @param user
	 *            the user that is searched for in the billinggroupEntity
	 * @return true if the user is registered to the billinggroupEntity, false
	 *         if not
	 */
	private boolean isUserRegisteredToBillinggroup(GAEBillinggroupEntity billinggroupEntity,
			amtc.gue.ws.base.inout.User user) {
		for (GAEUserEntity existingUserEntity : billinggroupEntity.getUsers()) {
			if (existingUserEntity.getKey().equals(user.getId())) {
				return true;
			}
		}
		return false;
	}
}
