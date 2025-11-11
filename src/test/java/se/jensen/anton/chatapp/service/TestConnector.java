package se.jensen.anton.chatapp.service;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class TestConnector {
    private Properties properties;
    private static TestConnector instance;


    private TestConnector() {
        properties = new Properties();
        try (InputStream input = Connector.class.getClassLoader()
                .getResourceAsStream("testproperties")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"));
    }

    public static TestConnector getInstance() {
        if (instance == null) {
            instance = new TestConnector();
        }
        return instance;
    }

}
