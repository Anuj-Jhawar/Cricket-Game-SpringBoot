package org.repo.databasequery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FindTournamentId implements QueryDatabase{
    @Override
    public int find(String tournamentName, Connection connection) {
        /*
            Find and return Tournament id if present in the Tournaments table.
        */
        if(connection!=null){
            PreparedStatement statement;
            try{
                String sqlCommandToGetTournamentId = "SELECT * FROM Tournaments WHERE Name = ?";
                statement = connection.prepareStatement(sqlCommandToGetTournamentId);
                statement.setString(1,tournamentName);
                try {
                    ResultSet resultSet = statement.executeQuery();
                    if(resultSet.next())
                    return resultSet.getInt("id");
                    else
                        return 0;
                }
                catch (Exception e){
                    System.out.println(e.getMessage());
                    System.out.println("Query not completed in databasequery.FindTournamentId.");
                }

            }
            catch (Exception e){
                System.out.println("Statement not created in databasequery.FindTournamentId.");
            }
            finally {
                try{
                    //connection.close();
                }
                catch (Exception e){
                    System.out.println("Connection not closed in databasequery.FindTournamentId.");
                }

            }

        }
        else{
            System.out.println("Connection not established in databasequery.FindTournamentId.");
        }
        return 1;
    }
}
