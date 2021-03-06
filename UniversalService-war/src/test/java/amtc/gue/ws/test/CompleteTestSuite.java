package amtc.gue.ws.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import amtc.gue.ws.test.base.BaseTestSuite;
import amtc.gue.ws.test.books.BookTestSuite;
import amtc.gue.ws.test.shopping.ShoppingTestSuite;
import amtc.gue.ws.test.tournament.TournamentTestSuite;
import amtc.gue.ws.test.vcsapi.VCSTestSuite;

@RunWith(Suite.class)
@SuiteClasses({ BaseTestSuite.class, BookTestSuite.class, TournamentTestSuite.class, VCSTestSuite.class,
		ShoppingTestSuite.class })
public class CompleteTestSuite {

}
