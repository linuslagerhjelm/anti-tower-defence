package highscore;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Author: Linus Lagerhjelm
 * File: HighScoreServer
 * Created: 16-11-23
 * Description: A singleton that is responsible for HighScore synchronization
 * with the high score database.
 */
public final class HighScoreServer implements Runnable {
    private static HighScoreServer instance = null;
    private AtomicBoolean initialized = new AtomicBoolean(false);

    // Connection info
    private String driver;
    private String address;
    private String usr;
    private String pass;
    private int timeout = 5;

    private LinkedBlockingQueue<Runnable> msgQueue = new LinkedBlockingQueue<>();

    // Cache
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
     * @param conf database configuration
     */
    public synchronized HighScoreServer initialize(DatabaseConfig conf) {
        if (!initialized.get()) {
            this.driver = conf.getDriver();
            this.address = conf.getAddress() + "/" + conf.getName();
            this.usr = conf.getUsername();
            this.pass = conf.getPassword();

            if (conf.getTimeOut() != null) {
                this.timeout = conf.getTimeOut();
            }
            initialized.set(true);
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
     * Returns the current cache limit
     * @return current cache limit
     */
    public int getCacheLimit() {
        return cacheLimit;
    }

    /**
     * Updates the cache limit
     * @param newLimit new cache limit
     */
    public synchronized void setCacheLimit(int newLimit) {
        cacheLimit = newLimit;
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
            new Thread(instance).start();
        }
        return instance;
    }

    /**
     * Adds a new high score to the database. In order to improve performance,
     * the high score updates might be temporally stored and then bulk updated.
     * To force a push of the current cache to the database, the forceUpdate
     * method is available.
     * @param score the score to add
     */
    public synchronized void addHighScore(HighScore score) {
        msgQueue.add(() -> {
            cache.add(score);
            if (cache.size() >= cacheLimit) {
                postScores();
            }
        });
    }

    /**
     * Forces the HighScoreServer to try to perform an insert operation to
     * the database. Upon success, all items currently in cache is inserted
     * into the underlying database and the cache will be cleared.
     * Upon failure, all items will be stored in cache for later retries.
     */
    public void forceUpdate() {
        msgQueue.add(this::postScores);
    }

    /**
     * Tries to post the scores in cache to the database. Upon success,
     * the cache is cleared. On failure, the cache will persist.
     * @return true/false weather the call was successful or not
     */
    private synchronized boolean postScores() {
        boolean success = true;
        Connection connection = getConnection();

        if (connection == null) {
            return false;     // Return unsuccessful if no connection available
        }

        try {
            prepareAndExecuteInsert(connection);
            cache.clear();

        } catch (SQLException e) {
            success = false;
        } finally {
            closeConnection(connection);
        }

        return success;
    }

    /**
     * Gets high scores from the database and returns them as a sorted list of
     * length in range [0 - current cache limit] with the highest score first.
     * If database call fails, the scores are read from cache. The result
     * is returned in as a callback.
     * @param callback callback that will receive the result of the request
     */
    public void getHighScores(DatabaseResult callback) {
        msgQueue.add(() -> {
            callback.receiveResult(handleGetHighScores(cacheLimit));
        });
    }

    /**
     * Gets high scores from the database and returns them as a sorted list of
     * length in range [0 - limit] with the highest score first.
     * If database call fails, the scores are read from cache.
     * @param limit set max number of scores to return
     * @param callback callback that will receive the result of the request
     */

    public void getHighScores(int limit, DatabaseResult callback) {
        msgQueue.add(() -> {
            callback.receiveResult(handleGetHighScores(limit));
        });
    }

    /**
     * Implement the functionality described int getHighScores in order to
     * allow the seamless use of a default value for the limit parameter.
     * @param limit max limit of return collection
     * @return max-sorted list of scores
     */
    private synchronized List<HighScore> handleGetHighScores(int limit) {
        Connection connection = getConnection();
        List<HighScore> result = null;

        if (connection == null) {
            return prepareCache();
        }

        try {
            ResultSet set = prepareAndExecuteSelect(connection, limit);
            result = mergeCacheAndResultSet(set, limit);

        } catch (SQLException e) {
            // Nothing much to do here

        } finally {
            closeConnection(connection);
        }

        return result;
    }

    /**
     * Returns the items stored in cache as a sorted ArrayList of scores
     * @return sorted list of scores
     */
    private ArrayList<HighScore> prepareCache() {
        ArrayList<HighScore> ret = new ArrayList<>();
        cache.iterator().forEachRemaining(ret::add);

        ret.sort((h1, h2) ->
                h2.getScore().getScore() - h1.getScore().getScore());

        return ret;
    }

    /**
     * Merges database result with the scores currently in cache and returns a
     * max sorted list who's length varies in range [0 - limit]
     * @param result result set retrieved from database
     * @param limit max size of the returned list
     * @return max sorted list of scores
     */
    private List<HighScore> mergeCacheAndResultSet(ResultSet result, int limit) {
        List<HighScore> list = prepareCache();

        try {
            while (result.next()) {
                Score score = new Score(result.getInt("Points"));
                Date date = new Date(Long.parseLong(result.getString("Time")));
                int id = result.getInt("Level");
                list.add(new HighScore(score, date, id));
            }
            list.sort((h1, h2) ->
                     h2.getScore().getScore() - h1.getScore().getScore());

        } catch (SQLException ignore) {}

        return (list.size() >= limit) ? list.subList(0, limit):list;
    }

    /**
     * Tries to establish a connection to the database and returns the result
     * as a boolean. Recommended use is to detect if we are running offline.
     * A successful call to this method will not guarantee that succeeding
     * calls to the database will be successful.
     * @return true/false weather the call was successful or not
     */
    public boolean tryConnection() {
        Connection con = getConnection();
        boolean success = (con != null);

        closeConnection(con);
        return success;
    }

    /**
     * Creates a new database connection using the connection info stored
     * in this class. Will return null on failure
     * @return Physical connection to an external database
     */
    private Connection getConnection() {
        Connection connection;
        try {
            Class.forName(driver);
            DriverManager.setLoginTimeout(timeout);
            connection = DriverManager.getConnection(address, usr, pass);

        } catch (ClassNotFoundException|SQLException e) {
            connection = null;
        }
        return connection;
    }

    /**
     * Handles close operations on the provided connection
     * @param connection the connection to close
     */
    private void closeConnection(Connection connection) {
        if (connection == null) return;
        try {
            connection.close();
        } catch (SQLException e) {
            // Nothing much to do here
        }
    }

    /**
     * Performs a query to the database to insert all the elements in cache
     * @param connection the database connection
     * @throws SQLException thrown if query failed
     */
    private void prepareAndExecuteInsert(Connection connection)
            throws SQLException {

        final String QUERY = "" +
                "INSERT INTO HighScores " +
                "(UserName, Time, Points, Level) " +
                "VALUES (?,?,?,?);";
        PreparedStatement statement = connection.prepareStatement(QUERY);

        for (HighScore hs : cache) {
            statement.setString(1, "Linus");
            statement.setString(2, String.valueOf(hs.getTime()));
            statement.setInt(3, hs.getScore().getScore());
            statement.setInt(4, hs.getID());
            statement.addBatch();
        }
        statement.executeBatch();
    }

    /**
     * Performs a query on the database to get the
     * @param connection the database connection to use
     * @param limit max number of entries to receive
     * @return A ResultSet containing the result of the query
     * @throws SQLException if something went wrong
     */
    private ResultSet prepareAndExecuteSelect(Connection connection, int limit)
            throws SQLException {

        final String QUERY = "" +
                "SELECT * " +
                "FROM HighScores " +
                "ORDER BY Points DESC " +
                "LIMIT " + limit + ";";

        PreparedStatement statement = connection.prepareStatement(QUERY);
        return statement.executeQuery();
    }

    @Override
    public void run() {
        while (true) {
            try {
                msgQueue.take().run();
            } catch (InterruptedException ignore) {}
        }
    }

}
