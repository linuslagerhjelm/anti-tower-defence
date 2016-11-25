package highscore;

import org.junit.Test;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Author: Linus Lagerhjelm
 * File: HighScoreServerTest
 * Created: 16-11-23
 * Description: Test cases for the HighScoreServer class
 */
public class HighScoreServerTest {
    private final int CACHE_LIMIT = 5;
    private HighScoreServer hsServer = HighScoreServer.getInstance();
    private HighScore SCORE = new HighScore(new Score(10), new Date(), 1337);
    private HighScore SCORE2 = new HighScore(new Score(10), new Date(), 137);

    @Test
    public void testCreateServer() throws Exception {
        DatabaseConfig dbConf = new DatabaseConfig(".db_config_home");
        hsServer.initialize(dbConf);
    }

    @Test
    public void testIsInitialized() throws Exception {
         assertTrue(hsServer.getInitialized());
    }

    @Test
    public void testSameInstance() throws Exception {
        assertEquals(hsServer, hsServer.getInstance());
    }

    @Test
    public void testUpdateCacheLimit() throws Exception {
        hsServer.setCacheLimit(CACHE_LIMIT);
        assertEquals(CACHE_LIMIT, hsServer.getCacheLimit());
    }

    @Test
    public void testForceUpdate() throws Exception {
        hsServer.addHighScore(SCORE);
        hsServer.addHighScore(SCORE2);

        if (hsServer.tryConnection()) {
            assertTrue(hsServer.forceUpdate());
        }
    }

    @Test
    public void testAutoUpdate() throws Exception {
        hsServer.setCacheLimit(CACHE_LIMIT);
        for (int i = 0; i < CACHE_LIMIT; i++) {
            HighScore hs = new HighScore(new Score(i), new Date(), i);
            if (!hsServer.addHighScore(hs) && hsServer.tryConnection()) {
                fail();
            }
        }
    }

    @Test
    public void testGetScoreSortingOrder() throws Exception {
        List<HighScore> result = hsServer.getHighScores();
        Iterator<HighScore> iter = result.iterator();

        if (iter.hasNext()) {
            HighScore hs = iter.next();

            while (iter.hasNext()) {
                HighScore hs2 = iter.next();
                if (hs2.getScore().getScore() > hs.getScore().getScore()) {
                    fail();
                }
            }
        }
    }
}