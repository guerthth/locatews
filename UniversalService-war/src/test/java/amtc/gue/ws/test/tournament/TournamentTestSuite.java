package amtc.gue.ws.test.tournament;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import amtc.gue.ws.test.tournament.delegate.persist.PlayerPersistenceDelegatorTest;
import amtc.gue.ws.test.tournament.persistence.dao.jpa.PlayerDAOJPATest;
import amtc.gue.ws.test.tournament.persistence.dao.objectify.PlayerDAOObjectifyTest;
import amtc.gue.ws.test.tournament.util.PlayerPersistenceDelegatorUtilTest;
import amtc.gue.ws.test.tournament.util.TournamentServiceEntityMapperTest;
import amtc.gue.ws.test.tournament.util.dao.PlayerDAOImplUtilTest;

@RunWith(Suite.class)
@SuiteClasses({ PlayerServiceTest.class, PlayerPersistenceDelegatorTest.class, PlayerPersistenceDelegatorUtilTest.class,
		TournamentServiceEntityMapperTest.class, PlayerDAOJPATest.class, PlayerDAOObjectifyTest.class,
		PlayerDAOImplUtilTest.class })
public class TournamentTestSuite {
	// base section done.Tournament have to be rewritten
}
