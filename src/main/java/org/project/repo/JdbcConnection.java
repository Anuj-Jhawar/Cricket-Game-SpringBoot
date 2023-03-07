package org.project.repo;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;

@Slf4j
public class JdbcConnection {
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
            log.error(e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static Connection getConnection() {
        /*
            Returns the connection.
        */
        return connection;
    }
}
