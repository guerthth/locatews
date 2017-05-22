package amtc.gue.ws.test.tournament;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;

import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

import amtc.gue.ws.base.persistence.EMF;
import amtc.gue.ws.base.persistence.ProductiveEMF;
import amtc.gue.ws.test.base.BaseTest;
import amtc.gue.ws.tournament.inout.Player;
import amtc.gue.ws.tournament.inout.Players;
import amtc.gue.ws.tournament.persistence.dao.player.PlayerDAO;
import amtc.gue.ws.tournament.persistence.dao.player.jpa.PlayerJPADAOImpl;
import amtc.gue.ws.tournament.persistence.dao.player.objectify.PlayerObjectifyDAOImpl;
import amtc.gue.ws.tournament.persistence.model.player.GAEPlayerEntity;
import amtc.gue.ws.tournament.persistence.model.player.jpa.GAEJPAPlayerEntity;
import amtc.gue.ws.tournament.persistence.model.player.objectify.GAEObjectifyPlayerEntity;
import amtc.gue.ws.tournament.util.mapper.TournamentServiceEntityMapper;
import amtc.gue.ws.tournament.util.mapper.jpa.TournamentServiceJPAEntityMapper;
import amtc.gue.ws.tournament.util.mapper.objectify.TournamentServiceObjectifyEntityMapper;

/**
 * Class holding common data for all TournamentService Tests
 * 
 * @author Thomas
 *
 */
public class TournamentTest extends BaseTest {
	protected static PlayerDAO<GAEPlayerEntity, GAEObjectifyPlayerEntity, String> playerObjectifyDAO;
	protected static PlayerDAO<GAEPlayerEntity, GAEJPAPlayerEntity, String> playerJPADAO;
	protected static PlayerDAO<GAEPlayerEntity, GAEJPAPlayerEntity, String> failurePlayerJPADAO;

	protected static Player player1;
	protected static Player player2;
	protected static Player player3;
	protected static Players players;
	protected static Players playersWithId;
	protected static Players emptyPlayers;

	protected static GAEObjectifyPlayerEntity objectifyPlayerEntity1;
	protected static GAEObjectifyPlayerEntity objectifyPlayerEntity2;
	protected static GAEObjectifyPlayerEntity objectifyPlayerEntity3;
	protected static GAEObjectifyPlayerEntity objectifyPlayerEntity4;
	protected static GAEObjectifyPlayerEntity objectifyPlayerEntity5;

	protected static List<GAEPlayerEntity> objectifyPlayerEntityList;
	protected static List<GAEPlayerEntity> objectifyPlayerEntityEmptyList;
	protected static List<GAEPlayerEntity> objectifyPlayerEntityNullValueList;

	protected static GAEJPAPlayerEntity JPAPlayerEntity1;
	protected static GAEJPAPlayerEntity JPAPlayerEntity2;
	protected static GAEJPAPlayerEntity JPAPlayerEntity3;
	protected static GAEJPAPlayerEntity JPAPlayerEntity4;
	protected static GAEJPAPlayerEntity JPAPlayerEntity5;

	protected static List<GAEPlayerEntity> JPAPlayerEntityList;
	protected static List<GAEPlayerEntity> JPAPlayerEntityEmptyList;
	protected static List<GAEPlayerEntity> JPAPlayerEntityNullValueList;

	protected static final String PLAYERKEY = "testPlayerKey";
	protected static final String PLAYERKEY_2 = "testPlayerKey2";
	protected static final String DESCRIPTION = "testPlayerDescription";

	protected static TournamentServiceEntityMapper JPATournamentEntityMapper;
	protected static TournamentServiceEntityMapper objectifyTournamentEntityMapper;

	@Before
	public void setUp() {
		setupDBHelpers();
		setupPlayerEntities();
	}

	@After
	public void tearDown() {
		tearDownDBHelpers();
	}

	/**
	 * Setting up basic tournament environment
	 * 
	 */
	protected static void setUpBasicTournamentEnvironment() {
		// Objectify setup
		ObjectifyService.setFactory(new ObjectifyFactory());
		ObjectifyService.register(GAEObjectifyPlayerEntity.class);

		playerObjectifyDAO = new PlayerObjectifyDAOImpl();

		// JPA setup
		EMF emf = new ProductiveEMF();
		playerJPADAO = new PlayerJPADAOImpl(emf);
		failurePlayerJPADAO = new PlayerJPADAOImpl(null);

		setupPlayers();
		setupPlayerEntities();
		setupEntityMappers();
	}

	/**
	 * Setting up all required PlayerEntities
	 */
	private static void setupPlayerEntities() {
		// Objectify PlayerEntities
		objectifyPlayerEntity1 = new GAEObjectifyPlayerEntity();
		objectifyPlayerEntity1.setKey(PLAYERKEY);
		objectifyPlayerEntity2 = new GAEObjectifyPlayerEntity();
		objectifyPlayerEntity2.setKey(PLAYERKEY_2);
		objectifyPlayerEntity3 = new GAEObjectifyPlayerEntity();
		objectifyPlayerEntity3.setDescription(DESCRIPTION);
		objectifyPlayerEntity4 = new GAEObjectifyPlayerEntity();
		objectifyPlayerEntity5 = new GAEObjectifyPlayerEntity();
		objectifyPlayerEntity5.setDescription(DESCRIPTION);

		objectifyPlayerEntityList = new ArrayList<>();
		objectifyPlayerEntityList.add(objectifyPlayerEntity1);
		objectifyPlayerEntityList.add(objectifyPlayerEntity2);

		objectifyPlayerEntityEmptyList = new ArrayList<>();

		objectifyPlayerEntityNullValueList = new ArrayList<>();
		objectifyPlayerEntityNullValueList.add(null);

		// JPA PlayerEntities
		JPAPlayerEntity1 = new GAEJPAPlayerEntity();
		JPAPlayerEntity1.setKey(PLAYERKEY);
		JPAPlayerEntity2 = new GAEJPAPlayerEntity();
		JPAPlayerEntity2.setKey(PLAYERKEY_2);
		JPAPlayerEntity3 = new GAEJPAPlayerEntity();
		JPAPlayerEntity3.setKey(PLAYERKEY);
		JPAPlayerEntity3.setDescription(DESCRIPTION);
		JPAPlayerEntity4 = new GAEJPAPlayerEntity();
		JPAPlayerEntity5 = new GAEJPAPlayerEntity();
		JPAPlayerEntity5.setDescription(DESCRIPTION);

		JPAPlayerEntityList = new ArrayList<>();
		JPAPlayerEntityList.add(JPAPlayerEntity1);
		JPAPlayerEntityList.add(JPAPlayerEntity2);

		JPAPlayerEntityEmptyList = new ArrayList<>();

		JPAPlayerEntityNullValueList = new ArrayList<>();
		JPAPlayerEntityNullValueList.add(null);
	}

	/**
	 * Setting up all required Player objects
	 */
	private static void setupPlayers() {
		List<Player> playerList = new ArrayList<>();

		player1 = new Player();
		player1.setDescription(DESCRIPTION);
		playerList.add(player1);
		player2 = new Player();
		player2.setDescription(DESCRIPTION);
		playerList.add(player2);

		players = new Players();
		players.setPlayers(playerList);

		List<Player> playerListWithId = new ArrayList<>();

		player3 = new Player();
		player3.setId(PLAYERKEY);
		playerListWithId.add(player3);

		playersWithId = new Players();
		playersWithId.setPlayers(playerListWithId);

		emptyPlayers = new Players();
	}

	/**
	 * Method setting up entity mappers
	 */
	private static void setupEntityMappers() {
		JPATournamentEntityMapper = new TournamentServiceJPAEntityMapper();
		objectifyTournamentEntityMapper = new TournamentServiceObjectifyEntityMapper();
	}
}
