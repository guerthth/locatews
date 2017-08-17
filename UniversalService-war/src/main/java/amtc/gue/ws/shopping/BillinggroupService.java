package amtc.gue.ws.shopping;

import java.util.ArrayList;
import java.util.List;

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
import amtc.gue.ws.base.inout.Users;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.shopping.delegate.persist.BillinggroupPersistenceDelegator;
import amtc.gue.ws.shopping.delegate.persist.BillPersistenceDelegator;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.ErrorConstants;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.base.util.StatusMapper;
import amtc.gue.ws.base.util.mapper.UserServiceEntityMapper;
import amtc.gue.ws.base.util.mapper.objectify.UserServiceObjectifyEntityMapper;
import amtc.gue.ws.books.util.mapper.BookServiceEntityMapper;
import amtc.gue.ws.shopping.inout.Bill;
import amtc.gue.ws.shopping.inout.Billinggroup;
import amtc.gue.ws.shopping.inout.Billinggroups;
import amtc.gue.ws.shopping.inout.Bills;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillinggroupEntity;
import amtc.gue.ws.shopping.response.BillinggroupServiceResponse;
import amtc.gue.ws.shopping.util.BillinggroupPersistenceDelegatorUtils;
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

		// check if user is already registered to billinggroup
		for (GAEUserEntity existingUser : billinggroupEntity.getUsers()) {
			if (existingUser.getKey().equals(userEntity.getKey())) {
				BillinggroupServiceResponse response = new BillinggroupServiceResponse();
				response.setBillinggroups(null);
				// TODO build better response
				Status status = new Status();
				status.setStatusMessage("User '" + userEntity.getKey() + "' already exists in billinggroup.");
				return response;
			}
		}

		// if user isn't contained in billinggroup yet, add it
		billinggroupEntity.addToUsersAndBillinggroups(userEntity);

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

	// TODO this method has to be tested extensively
	@ApiMethod(name = "addBillsToBillinggroup", path = "billinggroup/{billinggroupId}/bill")
	public BillinggroupServiceResponse addBillsToBillinggroup(final User user, @Named("billinggroupId") final String id,
			Bills bills) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}
		// TODO Retrieve billinggroup, add to all bills and afterwards add the
		// bills
		billinggroupDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, id);
		IDelegatorOutput bdOutput = billinggroupDelegator.delegate();
		if (bdOutput.getOutputObject() instanceof List<?>) {
			List<GAEObjectifyBillinggroupEntity> foundBillinggroupEntities = (List<GAEObjectifyBillinggroupEntity>) bdOutput
					.getOutputObject();
			if (foundBillinggroupEntities.size() == 1) {
				// TODO GAEObjectifyBillEntity billEntity =
				// ShoppingServiceEntityMapper.transformBill
			}
		}
		billDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.ADD, bills);
		bdOutput = billDelegator.delegate();
		BillinggroupServiceResponse response = ShoppingServiceEntityMapper
				.mapBdOutputToBillinggroupServiceResponse(bdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}
}
