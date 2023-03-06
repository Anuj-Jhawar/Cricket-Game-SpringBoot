package org.project.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcConnection.class);
    private static Connection connection;

    public static void initializeConnection() {
        /*
            Initializing jdbc connection.
        */
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Cricket";
            String username = "root";
            String password = "rootroot";
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

    public static Connection getConnection() {
        /*
            Returns the connection.
        */
        return connection;
    }
}
