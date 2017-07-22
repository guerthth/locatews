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
import amtc.gue.ws.shopping.delegate.persist.BillinggroupPersistenceDelegator;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.shopping.inout.Billinggroup;
import amtc.gue.ws.shopping.inout.Billinggroups;
import amtc.gue.ws.shopping.response.BillinggroupServiceResponse;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

@Api(name = "shopping", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID,
		Constants.API_EXPLORER_CLIENT_ID }, description = "API for the Shopping backend application")
public class BillinggroupService extends Service {
	private static final Logger log = Logger.getLogger(BillinggroupService.class.getName());
	private static final String SCOPE = "shopping";
	private AbstractPersistenceDelegator billinggroupDelegator;

	public BillinggroupService() {
		super();
		billinggroupDelegator = (BillinggroupPersistenceDelegator) SpringContext.context
				.getBean("billinggroupPersistenceDelegator");
	}

	public BillinggroupService(AbstractPersistenceDelegator userDelegator,
			AbstractPersistenceDelegator billinggroupDelegator) {
		super(userDelegator);
		this.billinggroupDelegator = billinggroupDelegator;
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

	@ApiMethod(name = "removeBillinggroup", path = "billinggroup/{id}", httpMethod = HttpMethod.DELETE)
	public BillinggroupServiceResponse removeBillinggroup(final User user, @Named("id") final String id)
			throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		Billinggroups billinggroupsToRemove = new Billinggroups();
		Billinggroup billinggroupToRemove = new Billinggroup();
		billinggroupToRemove.setBillinggroupId(id);
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
}
