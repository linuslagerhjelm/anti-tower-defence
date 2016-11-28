package highscore;

import exceptions.InvalidConnectionDataException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.stream.Stream;

/**
 * Author: Linus Lagerhjelm
 * File: DatabaseConfig
 * Created: 16-11-23
 * Description: Immutable object that represents a configuration for a database
 * connection
 */
public final class DatabaseConfig {
    private String driver;
    private String address;
    private String username;
    private String password;
    private String timeOut;
    private String dbName;

    /**
     * Creates a new DatabaseConfig file from the data within the file
     * that is provided as the argument
     * @param conf path to config file
     * @throws InvalidConnectionDataException if config was malformed
     */
    public DatabaseConfig(String conf) throws InvalidConnectionDataException {
        HashMap<String, String> map = new HashMap<>();

        try (Stream<String> stream = Files.lines(Paths.get(conf))) {

            stream.forEach(line -> addToMap(line, map));

        } catch (IOException e) {
            throw new InvalidConnectionDataException();
        }

        assignFields(map);
        validateFields();
    }

    /**
     * Properly inserts the key value pair represented by the line into the map
     * @param line String representing a string value pair
     * @param map the map to insert the string into

     */
    private void addToMap(String line, HashMap<String, String> map) {
        if (!line.contains(" ")) {
            return;
        }
        String[] parts = line.split(" ");
        map.put(parts[0].toLowerCase(), parts[1]);
    }

    /**
     * Retrieves the interesting fields from the map and assigns them to the
     * corresponding attributes of the class
     * @param map map cointaining the data from the config file
     */
    private void assignFields(HashMap<String, String> map) {
        driver = map.get("driver");
        address = "jdbc:mysql://" + map.get("address");
        username = map.get("username");
        password = map.get("password");
        timeOut = map.get("timeout");
        dbName = map.get("dbname");
    }

    /**
     * Makes sure that all the values were set after the config file was read
     * @throws InvalidConnectionDataException if a field was not set
     */
    private void validateFields() throws InvalidConnectionDataException {
        if (driver == null) throw new InvalidConnectionDataException("No driver field");
        if (address == null) throw new InvalidConnectionDataException("No address field");
        if (username == null) throw new InvalidConnectionDataException("No username field");
        if (password == null) throw new InvalidConnectionDataException("No password field");
    }

    public String getDriver() {
        return driver;
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return dbName;
    }

    public Integer getTimeOut() {
        return timeOut != null ? Integer.parseInt(timeOut) : null;
    }
}
