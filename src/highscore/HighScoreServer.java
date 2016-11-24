package highscore;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: Linus Lagerhjelm
 * File: HighScoreServer
 * Created: 16-11-23
 * Description: A singleton that is responsible for HighScore synchronization
 * with the high score database.
 */
public final class HighScoreServer implements Serializable {
    private static HighScoreServer instance = null;
    private AtomicBoolean initialized = new AtomicBoolean(false);

    // Connection info
    private String driver;
    private String address;
    private String usr;
    private String pass;

    private int cacheLimit = 10;
    private LinkedBlockingQueue<HighScore> cache = new LinkedBlockingQueue<>();

    private HighScoreServer() {
        // Exists only to defeat instantiation
    }

    /**
     * (Thread safe) initializes the HighScoreServer with the necessary data
     * in order to make it functional. If the object were already instantiated,
     * no action will be performed. The need to perform a call to this method
     * can be checked using the getInitialized method.
     * @param
     */
    public synchronized HighScoreServer initialize(DatabaseConfig conf) {
        if (!initialized.get()) {
            this.driver = conf.getDriver();
            this.address = conf.getAddress();
            this.usr = conf.getUsername();
            this.pass = conf.getPassword();
        }
        return this;
    }

    /**
     * Returns if the object were initialized or not
     * @return true/false
     */
    public boolean getInitialized() {
        return initialized.get();
    }

    /**
     * (Thread safe) Returns the active instance of the HighScore server object
     * or instantiates it if it was not already created. Calls to this method
     * may need to be succeed by a call to the initialize method if the
     * singleton were not already initialized.
     * @return active instance of the HighScoreServer
     */
    public synchronized static HighScoreServer getInstance() {
        if (instance == null) {
            instance = new HighScoreServer();
        }
        return instance;
    }

    /**
     * {@inheritDoc}
     * @return the object instance
     */
    private Object readResolve() {
        return instance;
    }

    /**
     * Adds a new high score to the database. In order to improve performance,
     * the high score updates might be temporally stored and then bulk updated.
     * To force a push the current cache to the database, the forceUpdate method
     * is available.
     * @param score the score to add
     */
    public synchronized void addHighScore(HighScore score) {
        cache.add(score);
        if (cache.size() == cacheLimit) {
            postScores();
        }
    }


    public synchronized void forceUpdate() {
        postScores();
    }

    private void postScores() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(address, usr, pass);

        } catch (ClassNotFoundException|SQLException  e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // Nothing much to do
            }
        }
    }

    private PreparedStatement prepareStatement() {
        //String queryString = "INSERT INTO HighScores ";
        return null;
    }
}
