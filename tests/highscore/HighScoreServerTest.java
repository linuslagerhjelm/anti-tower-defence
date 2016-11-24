package highscore;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Linus Lagerhjelm
 * File: HighScoreServerTest
 * Created: 16-11-23
 * Description: Test cases for the HighScoreServer class
 */
public class HighScoreServerTest {
    HighScoreServer hsServer = HighScoreServer.getInstance();

    @Test
    public void testCreateServer() throws Exception {
        DatabaseConfig dbConf = new DatabaseConfig(".db_config_home");
        hsServer.initialize(dbConf);
        hsServer.forceUpdate();
    }
}