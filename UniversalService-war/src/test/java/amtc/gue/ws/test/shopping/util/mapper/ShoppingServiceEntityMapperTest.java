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
import amtc.gue.ws.shopping.inout.Bill;
import amtc.gue.ws.shopping.inout.Billinggroup;
import amtc.gue.ws.shopping.inout.Billinggroups;
import amtc.gue.ws.shopping.inout.Bills;
import amtc.gue.ws.shopping.inout.Shop;
import amtc.gue.ws.shopping.inout.Shops;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;
import amtc.gue.ws.shopping.response.BillServiceResponse;
import amtc.gue.ws.shopping.response.BillinggroupServiceResponse;
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

	private static String BILLINGGROUP_ENTITY_JSON;
	private static String BILLINGGROUP_NULL_ENTITY_JSON;
	private static String BILLINGGROUP_ENTITY_LIST_JSON;
	private static String NULL_BILLINGGROUP_ENTITY_JSON = "{}";
	private static String NULL_BILLINGGROUP_ENTITY_LIST_JSON = "[]";
	private static IDelegatorOutput billinggroupDelegatorOutput;
	private static IDelegatorOutput unrecognizedBillinggroupDelegatorOutput;

	private static String BILL_ENTITY_JSON;
	private static String BILL_NULL_ENTITY_JSON;
	private static String BILL_ENTITY_LIST_JSON;
	private static String NULL_BILL_ENTITY_JSON = "{}";
	private static String NULL_BILL_ENTITY_LIST_JSON = "[]";
	private static IDelegatorOutput billDelegatorOutput;
	private static IDelegatorOutput unrecognizedBillDelegatorOutput;

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
		assertNull(shopEntity.getKey());
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
		assertNotNull(BILLINGGROUP_NULL_ENTITY_JSON);
		assertNotNull(BILLINGGROUP_ENTITY_LIST_JSON);
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
		assertEquals(0, shopResponse.getShops().size());
	}

	@Test
	public void testMapBdOutputToShopServiceResponseUsingNullInput() {
		ShopServiceResponse shopResponse = ShoppingServiceEntityMapper.mapBdOutputToShopServiceResponse(null);
		assertNull(shopResponse);
	}

	@Test
	public void testMapBillinggroupToEntityForDeleteType() {
		GAEBillinggroupEntity billinggroupEntity = objectifyShopEntityMapper.mapBillinggroupToEntity(billinggroup1,
				DelegatorTypeEnum.DELETE);
		assertEquals(billinggroup1.getBillinggroupId(), billinggroupEntity.getKey());
		assertEquals(billinggroup1.getDescription(), billinggroupEntity.getDescription());
	}

	@Test
	public void testMapBillinggroupToEntityForAddType() {
		GAEBillinggroupEntity billinggroupEntity = objectifyShopEntityMapper.mapBillinggroupToEntity(billinggroup1,
				DelegatorTypeEnum.ADD);
		assertEquals(billinggroup1.getBillinggroupId(), billinggroupEntity.getKey());
		assertEquals(billinggroup1.getDescription(), billinggroupEntity.getDescription());
	}

	@Test
	public void testMapBillinggroupToEntityForUpdateType() {
		GAEBillinggroupEntity billinggroupEntity = objectifyShopEntityMapper.mapBillinggroupToEntity(billinggroup1,
				DelegatorTypeEnum.UPDATE);
		assertEquals(billinggroup1.getBillinggroupId(), billinggroupEntity.getKey());
		assertEquals(billinggroup1.getDescription(), billinggroupEntity.getDescription());
	}

	@Test
	public void testMapBillinggroupToEntityForAddTypeUsingId() {
		GAEBillinggroupEntity billinggroupEntity = objectifyShopEntityMapper.mapBillinggroupToEntity(billinggroup2,
				DelegatorTypeEnum.ADD);
		assertNull(billinggroupEntity.getKey());
		assertEquals(billinggroup2.getDescription(), billinggroupEntity.getDescription());
	}

	@Test
	public void testTransformBillinggroupsToBillinggroupEntitiesUsingSimpleBillinggroups() {
		List<GAEBillinggroupEntity> billinggroupEntityList = objectifyShopEntityMapper
				.transformBillinggroupsToBillinggroupEntities(billinggroups, DelegatorTypeEnum.ADD);
		assertNotNull(billinggroupEntityList);
		assertEquals(1, billinggroupEntityList.size());
	}

	@Test
	public void testTransformBillinggroupsToBIllinggroupEntitiesUsingEmptyBillinggroups() {
		List<GAEBillinggroupEntity> billinggroupEntityList = objectifyShopEntityMapper
				.transformBillinggroupsToBillinggroupEntities(billinggroupsWithoutContent, DelegatorTypeEnum.ADD);
		assertNotNull(billinggroupEntityList);
		assertEquals(0, billinggroupEntityList.size());
	}

	@Test
	public void testTransformBillinggroupsToBillinggroupEntitiesUsingNullBillinggroups() {
		List<GAEBillinggroupEntity> billinggroupEntityList = objectifyShopEntityMapper
				.transformBillinggroupsToBillinggroupEntities(null, DelegatorTypeEnum.ADD);
		assertNotNull(billinggroupEntityList);
		assertEquals(0, billinggroupEntityList.size());
	}

	@Test
	public void testMapBillinggroupEntityToBillinggroupUsingSimpleBillinggroupEntity() {
		Billinggroup billinggroup = ShoppingServiceEntityMapper
				.mapBillinggroupEntityToBillinggroup(objectifyBillinggroupEntity1);
		assertEquals(billinggroup.getBillinggroupId(), objectifyBillinggroupEntity1.getKey());
		assertEquals(billinggroup.getDescription(), objectifyBillinggroupEntity1.getDescription());
	}

	@Test
	public void testMapBillinggroupEntityToBillinggroupUsingNullBillinggroupEntity() {
		Billinggroup billinggroup = ShoppingServiceEntityMapper.mapBillinggroupEntityToBillinggroup(null);
		assertNull(billinggroup.getBillinggroupId());
		assertNull(billinggroup.getDescription());
	}

	@Test
	public void testTransformBillinggroupEntitiesToBillinggroupsUsingSimpleBillinggroups() {
		Billinggroups billinggroups = ShoppingServiceEntityMapper
				.transformBillinggroupEntitiesToBillinggroups(objectifyBillinggroupEntityList);
		assertNotNull(billinggroups);
		assertEquals(1, billinggroups.getBillinggroups().size());
	}

	@Test
	public void testTransformBillinggroupEntitiesToBillinggroupsUsingNullBillinggroups() {
		Billinggroups billinggroups = ShoppingServiceEntityMapper.transformBillinggroupEntitiesToBillinggroups(null);
		assertNotNull(billinggroups);
		assertEquals(0, billinggroups.getBillinggroups().size());
	}

	@Test
	public void testMapBillinggroupEntityToJSONStringUsingNullBillinggroupEntity() {
		String JSONString = ShoppingServiceEntityMapper.mapBillinggroupEntityToJSONString(null);
		assertEquals(NULL_BILLINGGROUP_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapBillinggroupEntityToJSONStringUsingNullBillinggroupEntityEntities() {
		String JSONString = ShoppingServiceEntityMapper.mapBillinggroupEntityToJSONString(objectifyBillinggroupEntity2);
		assertNotNull(JSONString);
	}

	@Test
	public void testMapBillinggroupEntityToJSONStringUsingSimpleBillinggroupEntity() {
		String JSONString = ShoppingServiceEntityMapper.mapBillinggroupEntityToJSONString(objectifyBillinggroupEntity1);
		assertNotNull(JSONString);
	}

	@Test
	public void testMapBillinggroupEntityListToConsolidatedJSONStringUsingSimpleList() {
		String JSONString = ShoppingServiceEntityMapper
				.mapBillinggroupEntityListToConsolidatedJSONString(objectifyBillinggroupEntityList);
		assertNotNull(JSONString);
	}

	@Test
	public void testMapBillinggroupEntityListToConsolidatedJSONStringUsingNullList() {
		String JSONString = ShoppingServiceEntityMapper.mapBillinggroupEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_BILLINGGROUP_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapBillinggroupEntityListToConsolidatedJSONStringUsingEmptyList() {
		String JSONString = ShoppingServiceEntityMapper
				.mapBillinggroupEntityListToConsolidatedJSONString(objectifyBillinggroupEntityEmptyList);
		assertEquals(NULL_SHOP_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapBdOutputToBillinggroupServiceResponseUsingBillinggroupsOutputObject() {
		BillinggroupServiceResponse billinggroupResponse = ShoppingServiceEntityMapper
				.mapBdOutputToBillinggroupServiceResponse(billinggroupDelegatorOutput);
		assertNotNull(billinggroupResponse.getBillinggroups());
	}

	@Test
	public void testMapBdOutputToBillinggroupServiceResponseUsingUnrecognizedOutputObject() {
		BillinggroupServiceResponse billinggroupResponse = ShoppingServiceEntityMapper
				.mapBdOutputToBillinggroupServiceResponse(unrecognizedBillinggroupDelegatorOutput);
		assertNull(billinggroupResponse.getBillinggroups());
	}

	@Test
	public void testMapBdOutputToBillinggroupServiceResponseUsingNullInput() {
		BillinggroupServiceResponse billinggroupResponse = ShoppingServiceEntityMapper
				.mapBdOutputToBillinggroupServiceResponse(null);
		assertNull(billinggroupResponse);
	}

	@Test
	public void testMapBillToEntityForDeleteType() {
		GAEBillEntity billEntity = objectifyShopEntityMapper.mapBillToEntity(bill1, DelegatorTypeEnum.DELETE);
		assertEquals(bill1.getBillId(), billEntity.getKey());
		assertEquals(bill1.getAmount(), billEntity.getAmount());
	}

	@Test
	public void testMapBillToEntityForAddType() {
		GAEBillEntity billEntity = objectifyShopEntityMapper.mapBillToEntity(bill1, DelegatorTypeEnum.ADD);
		assertEquals(bill1.getBillId(), billEntity.getKey());
		assertEquals(bill1.getAmount(), billEntity.getAmount());
	}

	@Test
	public void testMapBillToEntityForUpdateType() {
		GAEBillEntity billEntity = objectifyShopEntityMapper.mapBillToEntity(bill1, DelegatorTypeEnum.UPDATE);
		assertEquals(bill1.getBillId(), billEntity.getKey());
		assertEquals(bill1.getAmount(), billEntity.getAmount());
	}

	@Test
	public void testMapBillToEntityForAddTypeUsingId() {
		GAEBillEntity billEntity = objectifyShopEntityMapper.mapBillToEntity(bill2, DelegatorTypeEnum.ADD);
		assertEquals(bill2.getBillId(), billEntity.getKey());
		assertEquals(bill2.getAmount(), billEntity.getAmount());
	}

	@Test
	public void testTransformBillsToBillEntitiesUsingSimpleBills() {
		List<GAEBillEntity> billEntityList = objectifyShopEntityMapper.transformBillsToBillEntities(bills,
				DelegatorTypeEnum.ADD);
		assertNotNull(billEntityList);
		assertEquals(1, billEntityList.size());
	}

	@Test
	public void testTransformBillsToBIllinggroupEntitiesUsingEmptyBills() {
		List<GAEBillEntity> billEntityList = objectifyShopEntityMapper.transformBillsToBillEntities(billsWithoutContent,
				DelegatorTypeEnum.ADD);
		assertNotNull(billEntityList);
		assertEquals(0, billEntityList.size());
	}

	@Test
	public void testTransformBillsToBillEntitiesUsingNullBills() {
		List<GAEBillEntity> billEntityList = objectifyShopEntityMapper.transformBillsToBillEntities(null,
				DelegatorTypeEnum.ADD);
		assertNotNull(billEntityList);
		assertEquals(0, billEntityList.size());
	}

	@Test
	public void testMapBillEntityToBillUsingSimpleBillEntity() {
		Bill bill = ShoppingServiceEntityMapper.mapBillEntityToBill(objectifyBillEntity1);
		assertEquals(bill.getBillId(), objectifyBillEntity1.getKey());
		assertEquals(bill.getAmount(), objectifyBillEntity1.getAmount());
	}

	@Test
	public void testMapBillEntityToBillUsingNullBillEntity() {
		Bill bill = ShoppingServiceEntityMapper.mapBillEntityToBill(null);
		assertNull(bill.getBillId());
		assertNull(bill.getAmount());
	}

	@Test
	public void testTransformBillEntitiesToBillsUsingSimpleBills() {
		Bills bills = ShoppingServiceEntityMapper.transformBillEntitiesToBills(objectifyBillEntityList);
		assertNotNull(bills);
		assertEquals(1, bills.getBills().size());
	}

	@Test
	public void testTransformBillEntitiesToBillsUsingNullBills() {
		Bills bills = ShoppingServiceEntityMapper.transformBillEntitiesToBills(null);
		assertNotNull(bills);
		assertEquals(0, bills.getBills().size());
	}

	@Test
	public void testMapBillEntityToJSONStringUsingNullBillEntity() {
		String JSONString = ShoppingServiceEntityMapper.mapBillEntityToJSONString(null);
		assertEquals(NULL_BILL_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapBillEntityToJSONStringUsingNullBillEntityEntities() {
		String JSONString = ShoppingServiceEntityMapper.mapBillEntityToJSONString(objectifyBillEntity2);
		assertEquals(BILL_NULL_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapBillEntityToJSONStringUsingSimpleBillEntity() {
		String JSONString = ShoppingServiceEntityMapper.mapBillEntityToJSONString(objectifyBillEntity1);
		assertEquals(BILL_ENTITY_JSON, JSONString);
	}

	@Test
	public void testMapBillEntityListToConsolidatedJSONStringUsingSimpleList() {
		String JSONString = ShoppingServiceEntityMapper
				.mapBillEntityListToConsolidatedJSONString(objectifyBillEntityList);
		assertEquals(BILL_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapBillEntityListToConsolidatedJSONStringUsingNullList() {
		String JSONString = ShoppingServiceEntityMapper.mapBillEntityListToConsolidatedJSONString(null);
		assertEquals(NULL_BILL_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapBillEntityListToConsolidatedJSONStringUsingEmptyList() {
		String JSONString = ShoppingServiceEntityMapper
				.mapBillEntityListToConsolidatedJSONString(objectifyBillEntityEmptyList);
		assertEquals(NULL_BILL_ENTITY_LIST_JSON, JSONString);
	}

	@Test
	public void testMapBdOutputToBillServiceResponseUsingBillsOutputObject() {
		BillServiceResponse billResponse = ShoppingServiceEntityMapper
				.mapBdOutputToBillServiceResponse(billDelegatorOutput);
		assertNotNull(billResponse.getBills());
	}

	@Test
	public void testMapBdOutputToBillServiceResponseUsingUnrecognizedOutputObject() {
		BillServiceResponse billResponse = ShoppingServiceEntityMapper
				.mapBdOutputToBillServiceResponse(unrecognizedBillDelegatorOutput);
		assertNull(billResponse.getBills());
	}

	@Test
	public void testMapBdOutputToBillServiceResponseUsingNullInput() {
		BillServiceResponse billResponse = ShoppingServiceEntityMapper.mapBdOutputToBillServiceResponse(null);
		assertNull(billResponse);
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
		sb.setLength(0);

		// BILLINGGROUP_ENTITY_JSON
		sb.append("{");
		sb.append("billinggroupId: ").append("null");
		sb.append(", description: ").append(DESCRIPTION);
		sb.append("}");
		BILLINGGROUP_ENTITY_JSON = sb.toString();
		sb.setLength(0);
		sb.append("{");
		sb.append("billinggroupId: null");
		sb.append(", description: null");
		sb.append("}");
		BILLINGGROUP_NULL_ENTITY_JSON = sb.toString();
		sb.setLength(0);
		sb.append("[").append(BILLINGGROUP_ENTITY_JSON).append("]");
		BILLINGGROUP_ENTITY_LIST_JSON = sb.toString();
		sb.setLength(0);

		// BILL_ENTITY_JSON
		sb.append("{");
		sb.append("billId: ").append("null");
		sb.append(", date: ").append("null");
		sb.append(", amount: ").append("null");
		sb.append(", user: ").append("null");
		sb.append(", shop: ").append("null");
		sb.append(", billinggroup: ").append("null");
		sb.append("}");
		BILL_ENTITY_JSON = sb.toString();
		sb.setLength(0);
		sb.append("{");
		sb.append("billId: ").append("null");
		sb.append(", date: ").append("null");
		sb.append(", amount: ").append("null");
		sb.append(", user: ").append("null");
		sb.append(", shop: ").append("null");
		sb.append(", billinggroup: ").append("null");
		sb.append("}");
		BILL_NULL_ENTITY_JSON = sb.toString();
		sb.setLength(0);
		sb.append("[").append(BILL_ENTITY_JSON).append("]");
		BILL_ENTITY_LIST_JSON = sb.toString();
	}

	/**
	 * Method initializing some DelegatorOutputs
	 */
	private static void setUpBdOutputs() {
		shopDelegatorOutput = new DelegatorOutput();
		shopDelegatorOutput.setOutputObject(shops);
		unrecognizedShopDelegatorOutput = new DelegatorOutput();
		unrecognizedShopDelegatorOutput.setOutputObject(null);

		billinggroupDelegatorOutput = new DelegatorOutput();
		billinggroupDelegatorOutput.setOutputObject(objectifyBillEntityList);
		unrecognizedBillinggroupDelegatorOutput = new DelegatorOutput();
		unrecognizedBillinggroupDelegatorOutput.setOutputObject(null);

		billDelegatorOutput = new DelegatorOutput();
		billDelegatorOutput.setOutputObject(bills);
		unrecognizedBillDelegatorOutput = new DelegatorOutput();
		unrecognizedBillDelegatorOutput.setOutputObject(null);
	}
}
