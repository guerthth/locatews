package amtc.gue.ws.test.tournament.persistence.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.ProductiveEMF;
import amtc.gue.ws.test.base.persistence.dao.BaseDAOTest;
import amtc.gue.ws.tournament.persistence.dao.player.PlayerDAO;
import amtc.gue.ws.tournament.persistence.dao.player.impl.PlayerDAOImpl;
import amtc.gue.ws.tournament.persistence.model.GAEJPAPlayerEntity;

/**
 * Super Testclass for all TournamentService JPA Testcases
 * 
 * @author Thomas
 *
 */
public abstract class TournamentServiceJPATest extends BaseDAOTest{
	
	protected static PlayerDAO playerEntityDAO;
	protected static PlayerDAO failurePlayerEntityDAO;
	
	protected GAEJPAPlayerEntity playerEntity1;
	protected GAEJPAPlayerEntity playerEntity2;
	
	protected static final String PLAYER_NAME_A = "playerNameA";
	protected static final String PLAYER_NAME_B = "playerNameB";
	
	
	@BeforeClass
	public static void oneTimeInititalSetup(){
		setUpBasicEnvironment();
	}

	@Before
	public void setUp(){
		helper.setUp();
		setUpPlayerEntities();
	}
	
	@After
	public void tearDown(){
		helper.tearDown();
	}
	
	/**
	 * Method creating basic test environment for that particular testszenario
	 */
	private static void setUpBasicEnvironment() {
		EMF emf = new ProductiveEMF();
		playerEntityDAO = new PlayerDAOImpl(emf);
		failurePlayerEntityDAO = new PlayerDAOImpl(null);
	}
	
	/**
	 * Method initializing some PlayerEntities for testing
	 */
	private void setUpPlayerEntities() {
		playerEntity1 = new GAEJPAPlayerEntity();
		playerEntity1.setPlayerName(PLAYER_NAME_A);
		playerEntity2 = new GAEJPAPlayerEntity();
		playerEntity2.setPlayerName(PLAYER_NAME_A);
	}
}
