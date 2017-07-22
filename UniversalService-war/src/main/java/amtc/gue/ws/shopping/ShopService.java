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
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.SpringContext;
import amtc.gue.ws.shopping.delegate.persist.ShopPersistenceDelegator;
import amtc.gue.ws.shopping.inout.Shop;
import amtc.gue.ws.shopping.inout.Shops;
import amtc.gue.ws.shopping.response.ShopServiceResponse;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;

@Api(name = "shopping", version = "v1", scopes = { Constants.EMAIL_SCOPE }, clientIds = { Constants.WEB_CLIENT_ID,
		Constants.API_EXPLORER_CLIENT_ID }, description = "API for the Shopping backend application")
public class ShopService extends Service {
	private static final Logger log = Logger.getLogger(ShopService.class.getName());
	private static final String SCOPE = "shopping";
	private AbstractPersistenceDelegator shopDelegator;

	public ShopService() {
		super();
		shopDelegator = (ShopPersistenceDelegator) SpringContext.context.getBean("shopPersistenceDelegator");
	}

	public ShopService(AbstractPersistenceDelegator userDelegator, AbstractPersistenceDelegator shopDelegator) {
		super(userDelegator);
		this.shopDelegator = shopDelegator;
	}

	@ApiMethod(name = "addShops", path = "shop", httpMethod = HttpMethod.POST)
	public ShopServiceResponse addShops(final User user, Shops shops) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		shopDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.ADD, shops);
		IDelegatorOutput bdOutput = shopDelegator.delegate();
		ShopServiceResponse response = ShoppingServiceEntityMapper.mapBdOutputToShopServiceResponse(bdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "getShops", path = "shop", httpMethod = HttpMethod.GET)
	public ShopServiceResponse getShops(final User user) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		shopDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.READ, null);
		IDelegatorOutput bdOutput = shopDelegator.delegate();
		ShopServiceResponse response = ShoppingServiceEntityMapper.mapBdOutputToShopServiceResponse(bdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "removeShop", path = "shop/{id}", httpMethod = HttpMethod.DELETE)
	public ShopServiceResponse removeShop(final User user, @Named("id") final String id) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		Shops shopsToRemove = new Shops();
		Shop shopToRemove = new Shop();
		shopToRemove.setShopId(id);
		List<Shop> shopListToRemove = new ArrayList<>();
		shopListToRemove.add(shopToRemove);
		shopsToRemove.setShops(shopListToRemove);

		shopDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.DELETE, shopsToRemove);
		IDelegatorOutput bdOutput = shopDelegator.delegate();
		ShopServiceResponse response = ShoppingServiceEntityMapper.mapBdOutputToShopServiceResponse(bdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}

	@ApiMethod(name = "updateShops", path = "shop", httpMethod = HttpMethod.PUT)
	public ShopServiceResponse updateShops(final User user, Shops shops) throws UnauthorizedException {
		if (user == null || !isAuthorized(user, SCOPE)) {
			throw new UnauthorizedException(UNAUTHORIZEDMESSAGE);
		}

		shopDelegator.buildAndInitializeDelegator(DelegatorTypeEnum.UPDATE, shops);
		IDelegatorOutput bdOutput = shopDelegator.delegate();
		ShopServiceResponse response = ShoppingServiceEntityMapper.mapBdOutputToShopServiceResponse(bdOutput);
		log.info(response.getStatus().getStatusMessage());
		return response;
	}
}
