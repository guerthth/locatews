package amtc.gue.ws.test.shopping.util.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import amtc.gue.ws.base.delegate.output.DelegatorOutput;
import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.exception.EntityPersistenceException;
import amtc.gue.ws.base.exception.EntityRemovalException;
import amtc.gue.ws.base.exception.EntityRetrievalException;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.shopping.inout.Shop;
import amtc.gue.ws.shopping.inout.Shops;
import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;
import amtc.gue.ws.shopping.response.ShopServiceResponse;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;
import amtc.gue.ws.shopping.util.mapper.objectify.ShoppingServiceObjectifyEntityMapper;
import amtc.gue.ws.test.shopping.ShoppingTest;

/**
 * Testclass for the ShopServiceEntityMapper
 * 
 * @author Thomas
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ShoppingServiceEntityMapperTest extends ShoppingTest {

	private static String SHOP_ENTITY_JSON;
	private static String SHOP_NULL_ENTITY_JSON;
	private static String SHOP_ENTITY_LIST_JSON;
	private static String NULL_SHOP_ENTITY_JSON = "{}";
	private static String NULL_SHOP_ENTITY_LIST_JSON = "[]";
	private static IDelegatorOutput shopDelegatorOutput;
	private static IDelegatorOutput unrecognizedShopDelegatorOutput;

	private ShoppingServiceEntityMapper objectifyShopEntityMapper = new ShoppingServiceObjectifyEntityMapper();

	@BeforeClass
	public static void oneTimeInitialSetup()
			throws EntityPersistenceException, EntityRetrievalException, EntityRemovalException {
		setUpBasicShoppingEnvironment();
		setUpJSONStrings();
		setUpBdOutputs();
	}

	@Test
	public void testMapShopToEntityForDeleteType() {
		GAEShopEntity shopEntity = objectifyShopEntityMapper.mapShopToEntity(shop1, DelegatorTypeEnum.DELETE);
		assertEquals(shop1.getShopId(), shopEntity.getKey());
		assertEquals(shop1.getShopName(), shopEntity.getShopName());
	}

	@Test
	public void testMapShopToEntityForAddType() {
		GAEShopEntity shopEntity = objectifyShopEntityMapper.mapShopToEntity(shop1, DelegatorTypeEnum.ADD);
		assertEquals(shop1.getShopId(), shopEntity.getKey());
		assertEquals(shop1.getShopName(), shopEntity.getShopName());
	}

	@Test
	public void testMapShopToEntityForUpdateType() {
		GAEShopEntity shopEntity = objectifyShopEntityMapper.mapShopToEntity(shop1, DelegatorTypeEnum.UPDATE);
		assertEquals(shop1.getShopId(), shopEntity.getKey());
		assertEquals(shop1.getShopName(), shopEntity.getShopName());
	}

	@Test
	public void testMapShopToEntityForAddTypeUsingId() {
		GAEShopEntity shopEntity = objectifyShopEntityMapper.mapShopToEntity(shop1, DelegatorTypeEnum.UPDATE);
		assertEquals(shop1.getShopId(), shopEntity.getKey());
		assertEquals(shop1.getShopName(), shopEntity.getShopName());
	}

	@Test
	public void testTransformShopsToShopEntitiesUsingSimpleShops() {
		List<GAEShopEntity> shopEntityList = objectifyShopEntityMapper.transformShopsToShopEntities(shops,
				DelegatorTypeEnum.ADD);
		assertNotNull(shopEntityList);
		assertEquals(1, shopEntityList.size());
	}

	@Test
	public void testTransformShopsToShopEntitiesUsingEmptyShops() {
		List<GAEShopEntity> shopEntityList = objectifyShopEntityMapper.transformShopsToShopEntities(shopsWithoutContent,
				DelegatorTypeEnum.ADD);
		assertNotNull(shopEntityList);
		assertEquals(0, shopEntityList.size());
	}

	@Test
	public void testTransformShopsToShopEntitiesUsingNullShops() {
		List<GAEShopEntity> shopEntityList = objectifyShopEntityMapper.transformShopsToShopEntities(null,
				DelegatorTypeEnum.ADD);
		assertNotNull(shopEntityList);
		assertEquals(0, shopEntityList.size());
	}

	@Test
	public void testMapShopEntityToShopUsingSimpleShopEntity() {
		Shop shop = ShoppingServiceEntityMapper.mapShopEntityToShop(objectifyShopEntity1);
		assertEquals(shop.getShopId(), objectifyShopEntity1.getKey());
		assertEquals(shop.getShopName(), objectifyShopEntity1.getShopName());
	}

	@Test
	public void testMapShopEntityToShopUsingNullShopEntity() {
		Shop shop = ShoppingServiceEntityMapper.mapShopEntityToShop(null);
		assertNull(shop.getShopId());
		assertNull(shop.getShopName());
	}

	@Test
	public void testTransformShopEntitiesToShopsUsingSimpleShops() {
		Shops shops = ShoppingServiceEntityMapper.transformShopEntitiesToShops(objectifyShopEntityList);
		assertNotNull(shops);
		assertEquals(1, shops.getShops().size());
	}

	@Test
	public void testTransformShopEntitiesToShopsUsingNullShops() {
		Shops shops = ShoppingServiceEntityMapper.transformShopEntitiesToShops(null);
		assertNotNull(shops);
		assertEquals(0, shops.getShops().size());
	}

	@Test
	public void testMapShopEntityToJSONStringUsingNullShopEntity() {
		String JSONString = ShoppingServiceEntityMapper.mapShopEntityToJSONString(null);
		assertEquals(NULL_SHOP_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapShopEntityToJSONStringUsingNullShopEntityEntities() {
		String JSONString = ShoppingServiceEntityMapper.mapShopEntityToJSONString(objectifyShopEntity1);
		assertEquals(SHOP_NULL_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapShopEntityToJSONStringUsingSimpleShopEntity() {
		String JSONString = ShoppingServiceEntityMapper.mapShopEntityToJSONString(objectifyShopEntity3);
		assertEquals(SHOP_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapShopEntityListToConsolidatedJSONStringUsingSimpleList() {
		String JSONString = ShoppingServiceEntityMapper
				.mapShopEntityListToConsolidatedJSONString(objectifyShopEntityList);
		assertEquals(SHOP_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapShopEntityListToConsolidatedJSONStringUsingNullList() {
		String JSONString = ShoppingServiceEntityMapper.mapShopEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_SHOP_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapShopEntityListToConsolidatedJSONStringUsingEmptyList() {
		String JSONString = ShoppingServiceEntityMapper
				.mapShopEntityListToConsolidatedJSONString(objectifyShopEntityEmptyList);
		assertEquals(NULL_SHOP_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapBdOutputToShopServiceResponseUsingShopsOutputObject() {
		ShopServiceResponse shopResponse = ShoppingServiceEntityMapper
				.mapBdOutputToShopServiceResponse(shopDelegatorOutput);
		assertNotNull(shopResponse.getShops());
	}

	@Test
	public void testMapBdOutputToShopServiceResponseUsingUnrecognizedOutputObject() {
		ShopServiceResponse shopResponse = ShoppingServiceEntityMapper
				.mapBdOutputToShopServiceResponse(unrecognizedShopDelegatorOutput);
		assertNull(shopResponse.getShops());
	}

	@Test
	public void testMapBdOutputToShopServiceResponseUsingNullInput() {
		ShopServiceResponse shopResponse = ShoppingServiceEntityMapper.mapBdOutputToShopServiceResponse(null);
		assertNull(shopResponse);
	}

	/**
	 * Method initializing some JSON Strings
	 */
	private static void setUpJSONStrings() {
		StringBuilder sb = new StringBuilder();

		// SHOP_ENTITY_JSON
		sb.append("{");
		sb.append("shopId: ").append(SHOPID);
		sb.append(", shopName: ").append(SHOPNAME);
		sb.append("}");
		SHOP_ENTITY_JSON = sb.toString();
		sb.setLength(0);
		sb.append("{");
		sb.append("shopId: null");
		sb.append(", shopName: null");
		sb.append("}");
		SHOP_NULL_ENTITY_JSON = sb.toString();
		sb.setLength(0);
		sb.append("[").append(SHOP_ENTITY_JSON).append("]");
		SHOP_ENTITY_LIST_JSON = sb.toString();
	}

	/**
	 * Method initializing some DelegatorOutputs
	 */
	private static void setUpBdOutputs() {
		shopDelegatorOutput = new DelegatorOutput();
		shopDelegatorOutput.setOutputObject(shops);
		unrecognizedShopDelegatorOutput = new DelegatorOutput();
		unrecognizedShopDelegatorOutput.setOutputObject(null);
	}
}
