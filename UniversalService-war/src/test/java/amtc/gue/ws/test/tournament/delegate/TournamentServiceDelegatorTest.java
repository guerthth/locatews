package amtc.gue.ws.test.tournament.delegate;

import java.util.ArrayList;
import java.util.List;

import amtc.gue.ws.base.delegate.persist.input.PersistenceDelegatorInput;
import amtc.gue.ws.base.util.PersistenceTypeEnum;
import amtc.gue.ws.test.base.delegate.persist.BasePersistenceDelegatorTest;
import amtc.gue.ws.tournament.delegate.persist.PlayerPersistenceDelegator;
import amtc.gue.ws.tournament.inout.Player;
import amtc.gue.ws.tournament.inout.Players;
import amtc.gue.ws.tournament.persistence.model.GAEJPAPlayerEntity;

/**
 * Super Testclass for all TournamentService Delegator Testcases
 * 
 * @author Thomas
 *
 */
public abstract class TournamentServiceDelegatorTest extends BasePersistenceDelegatorTest{

	protected static final String TESTPLAYERNAME = "testPlayerName";
	
	protected static GAEJPAPlayerEntity retrievedPlayerEntity;
	protected static GAEJPAPlayerEntity removedPlayerEntity;
	
	protected static List<GAEJPAPlayerEntity> emptyPlayerEntityList;
	protected static List<GAEJPAPlayerEntity> removedPlayerEntityList;
	
	protected static PlayerPersistenceDelegator playerPersistenceDelegator;

	protected static PersistenceDelegatorInput addPlayerDelegatorInput;
	protected static PersistenceDelegatorInput deletePlayerDelegatorInput;
	protected static PersistenceDelegatorInput deletePlayerDelegatorInputWithId;
	protected static PersistenceDelegatorInput readPlayerDelegatorInput;

	protected static List<Player> playerList;
	protected static List<Player> playerListWithId;
	
	protected static Player firstPlayer;
	protected static Player secondPlayer;
	protected static Player thirdPlayer;
	
	protected static Players players;
	protected static Players playersWithId;

	/**
	 * Method building the initial setup for delegatortests of the
	 * TournamentService
	 */
	protected static void oneTimeInitialSetup() {
		setUpPlayers();
		setUpPlayerEntities();
		setUpBaseDelegatorInputs();
		setUpPlayerDelegatorInputs();
		setUpPlayerPersistenceDelegators();
	}

	/**
	 * Method setting up Players
	 */
	private static void setUpPlayers() {
		playerList = new ArrayList<>();

		firstPlayer = new Player();
		firstPlayer.setPlayerName(TESTPLAYERNAME);
		playerList.add(firstPlayer);

		secondPlayer = new Player();
		secondPlayer.setPlayerName(TESTPLAYERNAME);
		playerList.add(secondPlayer);

		players = new Players();
		players.setPlayers(playerList);
		
		playerListWithId = new ArrayList<>();
		
		thirdPlayer = new Player();
		thirdPlayer.setId(TESTKEY);
		thirdPlayer.setPlayerName(TESTPLAYERNAME);
		playerListWithId.add(thirdPlayer);
		
		playersWithId = new Players();
		playersWithId.setPlayers(playerListWithId);
	}

	/**
	 * Method setting up some PlayerEntities
	 */
	private static void setUpPlayerEntities() {
		emptyPlayerEntityList = new ArrayList<>();
		
		retrievedPlayerEntity = new GAEJPAPlayerEntity();
		retrievedPlayerEntity.setKey(TESTKEY);
		retrievedPlayerEntity.setPlayerName(TESTPLAYERNAME);
		
		removedPlayerEntity = new GAEJPAPlayerEntity();
		removedPlayerEntity.setKey(TESTKEY);
		removedPlayerEntity.setPlayerName(TESTPLAYERNAME);
		
		removedPlayerEntityList = new ArrayList<>();
		removedPlayerEntityList.add(removedPlayerEntity);
	}

	/**
	 * Method setting up delegator inputs
	 */
	private static void setUpPlayerDelegatorInputs() {
		// DelegatorInput for player entity adding
		addPlayerDelegatorInput = new PersistenceDelegatorInput();
		addPlayerDelegatorInput.setInputObject(players);
		addPlayerDelegatorInput.setType(PersistenceTypeEnum.ADD);
		
		// DelegatorInput for player entity deletion
		deletePlayerDelegatorInput = new PersistenceDelegatorInput();
		deletePlayerDelegatorInput.setInputObject(players);
		deletePlayerDelegatorInput.setType(PersistenceTypeEnum.DELETE);
		
		// DelegatorInput for player entity deletion with ID player
		deletePlayerDelegatorInputWithId = new PersistenceDelegatorInput();
		deletePlayerDelegatorInputWithId.setInputObject(playersWithId);
		deletePlayerDelegatorInputWithId.setType(PersistenceTypeEnum.DELETE);
		
		// DelegatorInput for player entity read
		readPlayerDelegatorInput = new PersistenceDelegatorInput();
		readPlayerDelegatorInput.setInputObject(null);
		readPlayerDelegatorInput.setType(PersistenceTypeEnum.READ);
	}

	/**
	 * Method setting up persistence delegator instances
	 */
	private static void setUpPlayerPersistenceDelegators() {
		playerPersistenceDelegator = new PlayerPersistenceDelegator();
	}
}
