package org.repo.databasequery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FindPlayerId implements QueryDatabase{
    @Override
    public int find(String playerName, Connection connection) {
        /*
            Find and return Player id if present in the Players table.
        */
        if(connection!=null){
            PreparedStatement statement;
            try{
                String sqlCommandToGetPlayerId = "SELECT * FROM Players WHERE Name = ?";
                statement = connection.prepareStatement(sqlCommandToGetPlayerId);
                statement.setString(1,playerName);
                try {
                    ResultSet resultSet = statement.executeQuery();
                    if(resultSet.next())
                    return resultSet.getInt("id");
                    else return 0;
                }
                catch (Exception e){
                    System.out.println(e + "FindPlayer");
                    System.out.println("Query not completed in databasequery.FindPlayerId.");
                }

            }
            catch (Exception e){
                System.out.println("Statement not created in databasequery.FindPlayerId.");
            }
            finally {
                try{
                    //connection.close();
                }
                catch (Exception e){
                    System.out.println("Connection not closed in databasequery.FindPlayerId.");
                }

            }

        }
        else{
            System.out.println("Connection not established in databasequery.FindPlayerId.");
        }
        return 1;
    }
}
