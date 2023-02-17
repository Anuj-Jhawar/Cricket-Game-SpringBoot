package org.repo.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcConnection {
    public Connection getConnection(){
        /*
            Create and returns the connection.
        */
        Connection connection = null;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/Cricket";
            String username = "root";
            String password = "rootroot";

            Connection con = DriverManager.getConnection(url, username, password);
            return con;
        }
        catch(Exception e){
            System.out.println(e);
        }
        return connection;
    }
}
