package amtc.gue.ws.shopping.util.mapper;

import java.util.ArrayList;
import java.util.List;

import amtc.gue.ws.base.delegate.output.IDelegatorOutput;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.util.DelegatorTypeEnum;
import amtc.gue.ws.base.util.StatusMapper;
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

/**
 * Class responsible for mapping of ShopService related objects Use Case
 * examples: - building up ShoppingServiceResponse objects - mapping objects
 * from one type to another - creating JSON Strings for specific objects
 * 
 * @author Thomas
 *
 */
public abstract class ShoppingServiceEntityMapper {
	/**
	 * Method mapping Shop object to GAEShopEntity
	 * 
	 * @param shop
	 *            the shop element that should be transformed
	 * @param type
	 *            the database action type
	 * @return the mapped GAEShopEntity
	 */
	public abstract GAEShopEntity mapShopToEntity(Shop shop, DelegatorTypeEnum type);

	/**
	 * Method mapping Shops to a list of GAEShopEntities
	 * 
	 * @param shops
	 *            the Shops input object that should be mapped
	 * @param type
	 *            the database action type
	 * @return the mapped list of GAEShopEntities
	 */
	public List<GAEShopEntity> transformShopsToShopEntities(Shops shops, DelegatorTypeEnum type) {
		List<GAEShopEntity> shopEntityList = new ArrayList<>();
		if (shops != null) {
			for (Shop shop : shops.getShops()) {
				shopEntityList.add(mapShopToEntity(shop, type));
			}
		}
		return shopEntityList;
	}

	/**
	 * Method mapping a GAEShopEntity to a Shop object
	 * 
	 * @param shopEntity
	 *            the GAEShopEntity that should be mapped
	 * @return the Shop object
	 */
	public static Shop mapShopEntityToShop(GAEShopEntity shopEntity) {
		Shop shop = new Shop();
		if (shopEntity != null) {
			shop.setShopId(shopEntity.getKey());
			shop.setShopName(shopEntity.getShopName());
		}
		return shop;
	}

	/**
	 * Method mapping a list of GAEShopEntities to a Shops object
	 * 
	 * @param shopEntityList
	 *            a list of GAEShopEntities
	 * @return a Shops object
	 */
	public static Shops transformShopEntitiesToShops(List<GAEShopEntity> shopEntityList) {
		Shops shops = new Shops();
		List<Shop> shopList = new ArrayList<>();
		if (shopEntityList != null) {
			for (GAEShopEntity shopEntity : shopEntityList) {
				shopList.add(mapShopEntityToShop(shopEntity));
			}
		}
		shops.setShops(shopList);
		return shops;
	}

	/**
	 * Method mapping a GAEShopEntity to a JSON String
	 * 
	 * @param shopEntity
	 *            the GAEShopEntity that should be mapped
	 * @return the created GAEShopEntity JSON String
	 */
	public static String mapShopEntityToJSONString(GAEShopEntity shopEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (shopEntity != null) {
			sb.append("shopId: ");
			String id = shopEntity.getKey() != null ? shopEntity.getKey() + ", " : "null, ";
			sb.append(id);
			sb.append("shopName: ");
			String playerName = shopEntity.getShopName() != null ? shopEntity.getShopName() : "null";
			sb.append(playerName);
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Method mapping a list of ShopEntities to one consolidated JSON String
	 * 
	 * @param shopEntities
	 *            the list of ShopEntities that should be mapped to a JSON
	 *            String
	 * @return the consolidated JSON String
	 */
	public static String mapShopEntityListToConsolidatedJSONString(List<GAEShopEntity> shopEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (shopEntities != null && !shopEntities.isEmpty()) {
			int listSize = shopEntities.size();
			for (int i = 0; i < listSize; i++) {
				sb.append(mapShopEntityToJSONString(shopEntities.get(i)));
				if (i != listSize - 1) {
					sb.append(", ");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Method mapping a delegatoroutput to a ShopServiceResponse
	 * 
	 * @param bdOutput
	 *            delegatoroutput that should be included in the response
	 * @return mapped ShopServiceResponse
	 */
	public static ShopServiceResponse mapBdOutputToShopServiceResponse(IDelegatorOutput bdOutput) {
		ShopServiceResponse shopServiceResponse = null;

		if (bdOutput != null) {
			shopServiceResponse = new ShopServiceResponse();
			shopServiceResponse.setStatus(StatusMapper.buildStatusForDelegatorOutput(bdOutput));
			if (bdOutput.getOutputObject() instanceof Shops) {
				List<Shop> shopList = ((Shops) bdOutput.getOutputObject()).getShops();
				shopServiceResponse.setShops(shopList);
			} else {
				shopServiceResponse.setShops(null);
			}
		}
		return shopServiceResponse;
	}

	/**
	 * Method mapping Billinggroup object to GAEBillinggroupEntity
	 * 
	 * @param billinggroup
	 *            the billinggroup element that should be transformed
	 * @param type
	 *            the database action type
	 * @return the mapped GAEBillinggroupEntity
	 */
	public abstract GAEBillinggroupEntity mapBillinggroupToEntity(Billinggroup billinggroup, DelegatorTypeEnum type);

	/**
	 * Method mapping Billinggroups to a list of GAEBillinggroupEntities
	 * 
	 * @param billinggroups
	 *            the Billinggroups input object that should be mapped
	 * @param type
	 *            the database action type
	 * @return the mapped list of GAEBillinggroupEntities
	 */
	public List<GAEBillinggroupEntity> transformBillinggroupsToBillinggroupEntities(Billinggroups billinggroups,
			DelegatorTypeEnum type) {
		List<GAEBillinggroupEntity> billgroupEntityList = new ArrayList<>();
		if (billinggroups != null) {
			for (Billinggroup billinggroup : billinggroups.getBillinggroups()) {
				billgroupEntityList.add(mapBillinggroupToEntity(billinggroup, type));
			}
		}
		return billgroupEntityList;
	}

	/**
	 * Method mapping a GAEBillinggroupEntity to a Billinggroup object
	 * 
	 * @param billinggroupEntity
	 *            the GAEBillinggroupEntity that should be mapped
	 * @return the Billinggroup object
	 */
	public static Billinggroup mapBillinggroupEntityToBillinggroup(GAEBillinggroupEntity billinggroupEntity) {
		Billinggroup billinggroup = new Billinggroup();
		if (billinggroupEntity != null) {
			billinggroup.setBillinggroupId(billinggroupEntity.getKey());
			billinggroup.setDescription(billinggroupEntity.getDescription());
		}
		return billinggroup;
	}

	/**
	 * Method mapping a list of GAEBillinggroupEntities to a Billinggroups
	 * object
	 * 
	 * @param billinggroupEntityList
	 *            a list of GAEBillinggroupEntities
	 * @return a Billinggroups object
	 */
	public static Billinggroups transformBillinggroupEntitiesToBillinggroups(
			List<GAEBillinggroupEntity> billinggroupEntityList) {
		Billinggroups billinggroups = new Billinggroups();
		List<Billinggroup> billinggroupList = new ArrayList<>();
		if (billinggroupEntityList != null) {
			for (GAEBillinggroupEntity billinggroupEntity : billinggroupEntityList) {
				billinggroupList.add(mapBillinggroupEntityToBillinggroup(billinggroupEntity));
			}
		}
		billinggroups.setBillinggroups(billinggroupList);
		return billinggroups;
	}

	/**
	 * Method mapping a GAEBillinggroupEntity to a JSON String
	 * 
	 * @param billinggroupEntity
	 *            the GAEBillinggroupEntity that should be mapped
	 * @return the created GAEBillinggroupEntity JSON String
	 */
	public static String mapBillinggroupEntityToJSONString(GAEBillinggroupEntity billinggroupEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (billinggroupEntity != null) {
			sb.append("billinggroupId: ");
			String id = billinggroupEntity.getKey() != null ? billinggroupEntity.getKey() + ", " : "null";
			sb.append(id).append(", ");
			sb.append("description: ");
			String billinggroupDescription = billinggroupEntity.getDescription() != null
					? billinggroupEntity.getDescription() : "null";
			sb.append(billinggroupDescription).append(", ");
			sb.append("users: ");
			sb.append("[");
			if (billinggroupEntity.getUsers() != null) {
				for (int i = 0; i < billinggroupEntity.getUsers().size(); i++) {
					GAEUserEntity userEntity = (new ArrayList<>(billinggroupEntity.getUsers())).get(i);
					String user = userEntity != null ? userEntity.getKey() : "null";
					sb.append(user);
					if (i != billinggroupEntity.getUsers().size() - 1) {
						sb.append(", ");
					}
				}
			}
			sb.append("]");
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Method mapping a list of BillinggroupEntities to one consolidated JSON
	 * String
	 * 
	 * @param billinggroupEntities
	 *            the list of BillinggroupEntities that should be mapped to a
	 *            JSON String
	 * @return the consolidated JSON String
	 */
	public static String mapBillinggroupEntityListToConsolidatedJSONString(
			List<GAEBillinggroupEntity> billinggroupEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (billinggroupEntities != null && !billinggroupEntities.isEmpty()) {
			int listSize = billinggroupEntities.size();
			for (int i = 0; i < listSize; i++) {
				sb.append(mapBillinggroupEntityToJSONString(billinggroupEntities.get(i)));
				if (i != listSize - 1) {
					sb.append(", ");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Method mapping a delegatoroutput to a BillinggroupServiceResponse
	 * 
	 * @param bdOutput7
	 *            delegatoroutput that should be included in the response
	 * @return mapped BillinggroupServiceResponse
	 */
	@SuppressWarnings("unchecked")
	public static BillinggroupServiceResponse mapBdOutputToBillinggroupServiceResponse(IDelegatorOutput bdOutput) {
		BillinggroupServiceResponse billinggroupServiceResponse = null;

		if (bdOutput != null) {
			billinggroupServiceResponse = new BillinggroupServiceResponse();
			billinggroupServiceResponse.setStatus(StatusMapper.buildStatusForDelegatorOutput(bdOutput));
			if (bdOutput.getOutputObject() instanceof List<?>) {
				List<GAEBillinggroupEntity> billinggroups = (List<GAEBillinggroupEntity>) bdOutput.getOutputObject();
				billinggroupServiceResponse.setBillinggroups(billinggroups);
			} else {
				billinggroupServiceResponse.setBillinggroups(null);
			}
		}
		return billinggroupServiceResponse;
	}

	/**
	 * Method mapping Bill object to GAEBillEntity
	 * 
	 * @param bill
	 *            the bill element that should be transformed
	 * @param type
	 *            the database action type
	 * @return the mapped GAEBillEntity
	 */
	public abstract GAEBillEntity mapBillToEntity(Bill bill, DelegatorTypeEnum type);

	/**
	 * Method mapping Bills to a list of GAEBillEntities
	 * 
	 * @param bills
	 *            the Bills input object that should be mapped
	 * @param type
	 *            the database action type
	 * @return the mapped list of GAEBillEntities
	 */
	public List<GAEBillEntity> transformBillsToBillEntities(Bills bills, DelegatorTypeEnum type) {
		List<GAEBillEntity> billEntityList = new ArrayList<>();
		if (bills != null) {
			for (Bill bill : bills.getBills()) {
				billEntityList.add(mapBillToEntity(bill, type));
			}
		}
		return billEntityList;
	}

	/**
	 * Method mapping a GAEBillEntity to a Bill object
	 * 
	 * @param billEntity
	 *            the GAEBillEntity that should be mapped
	 * @return the Bill object
	 */
	public static Bill mapBillEntityToBill(GAEBillEntity billEntity) {
		Bill bill = new Bill();
		if (billEntity != null) {
			bill.setBillId(billEntity.getKey());
			bill.setDate(billEntity.getDate());
			bill.setAmount(billEntity.getAmount());
			bill.setShop(mapShopEntityToShop(billEntity.getShop()));
			bill.setBillinggroup(mapBillinggroupEntityToBillinggroup(billEntity.getBillinggroup()));
		}
		return bill;
	}

	/**
	 * Method mapping a list of GAEBillEntities to a Bills object
	 * 
	 * @param billEntityList
	 *            a list of GAEBillEntities
	 * @return a Bills object
	 */
	public static Bills transformBillEntitiesToBills(List<GAEBillEntity> billEntityList) {
		Bills bills = new Bills();
		List<Bill> billList = new ArrayList<>();
		if (billEntityList != null) {
			for (GAEBillEntity billEntity : billEntityList) {
				billList.add(mapBillEntityToBill(billEntity));
			}
		}
		bills.setBills(billList);
		return bills;
	}

	/**
	 * Method mapping a GAEBillEntity to a JSON String
	 * 
	 * @param billEntity
	 *            the GAEBillEntity that should be mapped
	 * @return the created GAEBillEntity JSON String
	 */
	public static String mapBillEntityToJSONString(GAEBillEntity billEntity) {
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		if (billEntity != null) {
			sb.append("billId: ");
			String id = billEntity.getKey() != null ? billEntity.getKey() + ", " : "null";
			sb.append(id).append(", ");
			sb.append("date: ");
			String billDate = billEntity.getDate() != null ? billEntity.getDate().toString() : "null";
			sb.append(billDate).append(", ");
			sb.append("amount: ");
			String amount = billEntity.getAmount() != null ? billEntity.getAmount().toString() : "null";
			sb.append(amount).append(", ");
			sb.append("user: ");
			String user = billEntity.getUser() != null ? billEntity.getUser().toString() : "null";
			sb.append(user).append(", ");
			sb.append("shop: ");
			String shop = billEntity.getShop() != null ? billEntity.getShop().toString() : "null";
			sb.append(shop).append(", ");
			sb.append("billinggroup: ");
			String billinggroup = billEntity.getBillinggroup() != null ? billEntity.getBillinggroup().toString()
					: "null";
			sb.append(billinggroup);
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Method mapping a list of BillEntities to one consolidated JSON String
	 * 
	 * @param billEntities
	 *            the list of BillEntities that should be mapped to a JSON
	 *            String
	 * @return the consolidated JSON String
	 */
	public static String mapBillEntityListToConsolidatedJSONString(List<GAEBillEntity> billEntities) {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		if (billEntities != null && !billEntities.isEmpty()) {
			int listSize = billEntities.size();
			for (int i = 0; i < listSize; i++) {
				sb.append(mapBillEntityToJSONString(billEntities.get(i)));
				if (i != listSize - 1) {
					sb.append(", ");
				}
			}
		}
		sb.append("]");
		return sb.toString();
	}

	/**
	 * Method mapping a delegatoroutput to a BillServiceResponse
	 * 
	 * @param bdOutput
	 *            delegatoroutput that should be included in the response
	 * @return mapped BillServiceResponse
	 */
	@SuppressWarnings("unchecked")
	public static BillServiceResponse mapBdOutputToBillServiceResponse(IDelegatorOutput bdOutput) {
		BillServiceResponse billServiceResponse = null;

		if (bdOutput != null) {
			billServiceResponse = new BillServiceResponse();
			billServiceResponse.setStatus(StatusMapper.buildStatusForDelegatorOutput(bdOutput));
			if (bdOutput.getOutputObject() instanceof List<?>) {
				List<GAEBillEntity> bills = (List<GAEBillEntity>) bdOutput.getOutputObject();
				billServiceResponse.setBills(bills);
			} else {
				billServiceResponse.setBills(null);
			}
		}
		return billServiceResponse;
	}
}
