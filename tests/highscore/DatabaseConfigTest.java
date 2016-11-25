package highscore;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Author: Linus Lagerhjelm
 * File: DatabaseConfigTest
 * Created: 16-11-23
 * Description: Contains test cases for the DatabaseConfig class
 */
public class DatabaseConfigTest {
    private final String PATH = System.getProperty("user.dir") + "/.db_config_home";
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String ADDRESS = "jdbc:mysql://127.0.0.1:8889";
    private final String USERNAME = "root";
    private final String PASSWORD = "root";
    private final String DB_NAME = "apjava";
    private final Integer TIMEOUT = 2;


    @Test
    public void testGetDriver() throws Exception {
        DatabaseConfig dbConf = new DatabaseConfig(PATH);
        assertEquals(dbConf.getDriver(), DRIVER);
    }

    @Test
    public void testGetAddress() throws Exception {
        DatabaseConfig dbConf = new DatabaseConfig(PATH);
        assertEquals(dbConf.getAddress(), ADDRESS);
    }

    @Test
    public void testGetUsername() throws Exception {
        DatabaseConfig dbConf = new DatabaseConfig(PATH);
        assertEquals(dbConf.getUsername(), USERNAME);
    }

    @Test
    public void testGetPassword() throws Exception {
        DatabaseConfig dbConf = new DatabaseConfig(PATH);
        assertEquals(dbConf.getPassword(), PASSWORD);
    }

    @Test
    public void testGetTimeout() throws Exception {
        DatabaseConfig dbConf = new DatabaseConfig(PATH);
        assertEquals(dbConf.getTimeOut(), TIMEOUT);
    }
}