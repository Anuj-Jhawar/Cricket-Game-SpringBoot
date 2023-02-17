package org.repo.databaseadd;



import org.repo.jdbc.JdbcConnection;

import java.sql.Connection;
import java.sql.Statement;

public class AddTournament implements AddToTable {
    private String tournamentName;

    public AddTournament(String tournamentName){
        this.tournamentName = tournamentName;
    }
    @Override
    public void add() {
        /*
            Add tournament to Tournaments table if tournament not in the database.
        */
        JdbcConnection jdbc = new JdbcConnection();
        Connection connection = jdbc.getConnection();
        if(connection!=null){
            Statement statement;
            try{
                statement = connection.createStatement();
                String sqlCommandToCreateTournamentTable = "INSERT INTO Tournaments (Name) VALUES ('" + tournamentName + "')";
                try {
                    statement.executeUpdate(sqlCommandToCreateTournamentTable);
                }
                catch (Exception e){
                    System.out.println("Query not completed in databaseadd.AddTournamentToTournamentTable.");
                }

            }
            catch (Exception e){
                System.out.println("Statement not created in databaseadd.AddTournamentToTournamentTable..");
            }
            finally {
                try{
                    //connection.close();
                }
                catch (Exception e){
                    System.out.println("Connection not closed in databaseadd.AddTournamentToTournamentTable..");
                }

            }

        }
        else{
            System.out.println("Connection not established in databaseadd.AddTournamentToTournamentTable..");
        }
    }
}
