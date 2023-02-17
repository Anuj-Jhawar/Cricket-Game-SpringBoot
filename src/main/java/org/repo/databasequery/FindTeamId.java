package org.repo.databasequery;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class FindTeamId implements QueryDatabase{
    @Override
    public int find(String teamName, Connection connection) {
        /*
            Find and return Team id if present in the Teams table.
        */
        if(connection!=null){
            Statement statement;
            try{
                statement = connection.createStatement();
                String sqlCommandToGetTeamId = "SELECT * FROM Teams WHERE Name = '" + teamName + "'";
                try {
                    ResultSet resultSet = statement.executeQuery(sqlCommandToGetTeamId);
                    if(resultSet.next())
                    return resultSet.getInt("id");
                    else
                        return 0;
                }
                catch (Exception e){
                    System.out.println(e);
                    System.out.println("Query not completed in databasequery.FindTeamId.");
                }

            }
            catch (Exception e){
                System.out.println(e);
                System.out.println("Statement not created in databasequery.FindTeamId.");
            }
            finally {
                try{
                    //connection.close();
                }
                catch (Exception e){
                    System.out.println("Connection not closed in databasequery.FindTeamId.");
                }

            }

        }
        else{
            System.out.println("Connection not established in databasequery.FindTeamId.");
        }
        return 1;
    }
}
