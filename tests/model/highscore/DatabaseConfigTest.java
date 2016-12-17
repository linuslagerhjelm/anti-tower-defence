package model.highscore;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Author: Linus Lagerhjelm
 * File: DatabaseConfigTest
 * Created: 16-11-23
 * Description: Contains test cases for the DatabaseConfig class
 */
public class DatabaseConfigTest {
    private final String PATH = getClass().getResource("/.db_config_home").getFile();
    private final String DRIVER = "com.mysql.jdbc.Driver";
    private final String ADDRESS = "jdbc:mysql://127.0.0.1:3306";
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