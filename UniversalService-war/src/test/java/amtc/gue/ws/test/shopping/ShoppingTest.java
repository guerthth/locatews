package amtc.gue.ws.test.shopping;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.api.users.User;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import amtc.gue.ws.base.persistence.dao.user.UserDAO;
import amtc.gue.ws.base.persistence.dao.user.objectify.UserObjectifyDAOImpl;
import amtc.gue.ws.base.persistence.model.user.GAEUserEntity;
import amtc.gue.ws.base.persistence.model.user.objectify.GAEObjectifyUserEntity;
import amtc.gue.ws.books.persistence.model.book.objectify.GAEObjectifyBookEntity;
import amtc.gue.ws.books.persistence.model.tag.objectify.GAEObjectifyTagEntity;
import amtc.gue.ws.shopping.inout.Bill;
import amtc.gue.ws.shopping.inout.Billinggroup;
import amtc.gue.ws.shopping.inout.Billinggroups;
import amtc.gue.ws.shopping.inout.Bills;
import amtc.gue.ws.shopping.inout.Shop;
import amtc.gue.ws.shopping.inout.Shops;
import amtc.gue.ws.shopping.persistence.dao.BillDAO;
import amtc.gue.ws.shopping.persistence.dao.BillinggroupDAO;
import amtc.gue.ws.shopping.persistence.dao.ShopDAO;
import amtc.gue.ws.shopping.persistence.dao.objectify.BillObjectifyDAOImpl;
import amtc.gue.ws.shopping.persistence.dao.objectify.BillinggroupObjectifyDAOImpl;
import amtc.gue.ws.shopping.persistence.dao.objectify.ShopObjectifyDAOImpl;
import amtc.gue.ws.shopping.persistence.model.GAEBillEntity;
import amtc.gue.ws.shopping.persistence.model.GAEBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.GAEShopEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyBillinggroupEntity;
import amtc.gue.ws.shopping.persistence.model.objectify.GAEObjectifyShopEntity;
import amtc.gue.ws.shopping.util.mapper.ShoppingServiceEntityMapper;
import amtc.gue.ws.shopping.util.mapper.objectify.ShoppingServiceObjectifyEntityMapper;
import amtc.gue.ws.test.base.BaseTest;

/**
 * Class holding common data for all ShopService Tests
 * 
 * @author Thomas
 *
 */
public class ShoppingTest extends BaseTest {
	protected static UserDAO<GAEUserEntity, GAEObjectifyUserEntity, String> userObjectifyDAO;
	protected static BillinggroupDAO<GAEBillinggroupEntity, GAEObjectifyBillinggroupEntity, String> billinggroupObjectifyDAO;
	protected static BillDAO<GAEBillEntity, GAEObjectifyBillEntity, String> billObjectifyDAO;
	protected static ShopDAO<GAEShopEntity, GAEObjectifyShopEntity, String> shopObjectifyDAO;

	protected static User user;
	protected static amtc.gue.ws.base.inout.User serviceUser;
	protected static amtc.gue.ws.base.inout.User invalidServiceUser;

	protected static Shop shop1;
	protected static Shops shops;
	protected static Shops shopsWithoutContent;

	protected static Billinggroup billinggroup1;
	protected static Billinggroup billinggroup2;
	protected static Billinggroups billinggroups;
	protected static Billinggroups billinggroupsWithoutContent;

	protected static Bill bill1;
	protected static Bill bill2;
	protected static Bills bills;
	protected static Bills billsWithoutContent;

	protected static GAEObjectifyBillinggroupEntity objectifyBillinggroupEntity1;
	protected static GAEObjectifyBillinggroupEntity objectifyBillinggroupEntity2;
	protected static GAEObjectifyUserEntity objectifyUserEntity1;
	protected static GAEObjectifyUserEntity objectifyUserEntity2;
	protected static GAEObjectifyShopEntity objectifyShopEntity1;
	protected static GAEObjectifyShopEntity objectifyShopEntity2;
	protected static GAEObjectifyShopEntity objectifyShopEntity3;
	protected static GAEObjectifyBillEntity objectifyBillEntity1;
	protected static GAEObjectifyBillEntity objectifyBillEntity2;

	protected static List<GAEShopEntity> objectifyShopEntityList;
	protected static List<GAEShopEntity> objectifyShopEntityEmptyList;
	protected static List<GAEBillinggroupEntity> objectifyBillinggroupEntityList;
	protected static List<GAEBillinggroupEntity> objectifyBillinggroupEntityEmptyList;
	protected static List<GAEBillEntity> objectifyBillEntityList;
	protected static List<GAEBillEntity> objectifyBillEntityEmptyList;

	protected static final String EMAIL = "test@test.com";
	protected static final String EMAIL_B = "testB@test.com";
	protected static final String SHOPID = "1";
	protected static final String SHOPNAME = "ShopName";
	protected static final String DESCRIPTION = "description";
	protected static final String BILLID = "1";
	protected static final String BILLINGGROUPID = "1";

	protected static ShoppingServiceEntityMapper objectifyShopEntityMapper;
	protected static ShoppingServiceEntityMapper objectifyBillEntityMapper;

	@Before
	public void setUp() {
		setupDBHelpers();
		setupUserEntities();
		setupBillinggroupEntities();
		setupShopEntities();
		setupBillEntities();
	}

	@After
	public void tearDown() {
		tearDownDBHelpers();
	}

	/**
	 * Setting up basic shopping environment
	 * 
	 */
	protected static void setUpBasicShoppingEnvironment() {
		setupUsers();

		// Objectify setup
		ObjectifyService.setFactory(new ObjectifyFactory());
		ObjectifyService.register(GAEObjectifyUserEntity.class);
		ObjectifyService.register(GAEObjectifyBillEntity.class);
		ObjectifyService.register(GAEObjectifyShopEntity.class);
		ObjectifyService.register(GAEObjectifyBillinggroupEntity.class);
		ObjectifyService.register(GAEObjectifyBookEntity.class);
		ObjectifyService.register(GAEObjectifyTagEntity.class);

		userObjectifyDAO = new UserObjectifyDAOImpl();
		billinggroupObjectifyDAO = new BillinggroupObjectifyDAOImpl();
		billObjectifyDAO = new BillObjectifyDAOImpl();
		shopObjectifyDAO = new ShopObjectifyDAOImpl();

		setupShops();
		setupBillinggroups();
		setupBills();
		setupShopEntities();
		setupBillinggroupEntities();
		setupBillEntities();
		setupEntityMappers();
	}

	/**
	 * Setting up Users
	 */
	private static void setupUsers() {
		String userMail = "test@test.com";
		String authDomain = "domain";
		user = new User(userMail, authDomain);
		serviceUser = new amtc.gue.ws.base.inout.User();
		serviceUser.setId(EMAIL);
		serviceUser.getRoles().add("shopping");
		invalidServiceUser = new amtc.gue.ws.base.inout.User();
	}

	/**
	 * Setting up Shops
	 */
	private static void setupShops() {
		shop1 = new Shop();
		shop1.setShopId(SHOPID);
		shop1.setShopName(SHOPNAME);
		shops = new Shops();
		shops.getShops().add(shop1);
		shopsWithoutContent = new Shops();
	}

	/**
	 * Setting up Billinggroups
	 */
	private static void setupBillinggroups() {
		billinggroup1 = new Billinggroup();
		billinggroup1.setDescription(DESCRIPTION);
		billinggroup2 = new Billinggroup();
		billinggroup2.setBillinggroupId(BILLINGGROUPID);
		billinggroups = new Billinggroups();
		billinggroups.getBillinggroups().add(billinggroup1);
		billinggroupsWithoutContent = new Billinggroups();
	}

	/**
	 * Setting up Billss
	 */
	private static void setupBills() {
		bill1 = new Bill();
		bill2 = new Bill();
		bills = new Bills();
		bills.getBills().add(bill1);
		billsWithoutContent = new Bills();
	}

	/**
	 * Setting up UserEntities for testing
	 */
	private void setupUserEntities() {
		objectifyUserEntity1 = new GAEObjectifyUserEntity();
		objectifyUserEntity1.setKey(EMAIL);
		objectifyUserEntity2 = new GAEObjectifyUserEntity();
		objectifyUserEntity2.setKey(EMAIL_B);
	}

	/**
	 * Setting up BillinggroupEntities for testing
	 */
	private static void setupBillinggroupEntities() {
		// Objectify Billinggroup Entities
		objectifyBillinggroupEntity1 = new GAEObjectifyBillinggroupEntity();
		objectifyBillinggroupEntity1.setDescription(DESCRIPTION);
		objectifyBillinggroupEntity2 = new GAEObjectifyBillinggroupEntity();

		objectifyBillinggroupEntityList = new ArrayList<>();
		objectifyBillinggroupEntityList.add(objectifyBillinggroupEntity1);

		objectifyBillinggroupEntityEmptyList = new ArrayList<>();
	}

	/**
	 * Setting up Billentities for testing
	 */
	private static void setupBillEntities() {
		// Objectify Bill Entities
		objectifyBillEntity1 = new GAEObjectifyBillEntity();
		objectifyBillEntity2 = new GAEObjectifyBillEntity();

		objectifyBillEntityList = new ArrayList<>();
		objectifyBillEntityList.add(objectifyBillEntity1);

		objectifyBillEntityEmptyList = new ArrayList<>();
	}

	/**
	 * Setting up Shopentities for testing
	 */
	private static void setupShopEntities() {
		// Objectify Shop Entities
		objectifyShopEntity1 = new GAEObjectifyShopEntity();
		objectifyShopEntity2 = new GAEObjectifyShopEntity();
		objectifyShopEntity2.setShopName(SHOPNAME);
		objectifyShopEntity3 = new GAEObjectifyShopEntity();
		objectifyShopEntity3.setKey(SHOPID);
		objectifyShopEntity3.setShopName(SHOPNAME);

		objectifyShopEntityList = new ArrayList<>();
		objectifyShopEntityList.add(objectifyShopEntity3);

		objectifyShopEntityEmptyList = new ArrayList<>();
	}

	/**
	 * Method setting up entity mappers
	 */
	private static void setupEntityMappers() {
		objectifyShopEntityMapper = new ShoppingServiceObjectifyEntityMapper();
		objectifyBillEntityMapper = new ShoppingServiceObjectifyEntityMapper();
	}
}
