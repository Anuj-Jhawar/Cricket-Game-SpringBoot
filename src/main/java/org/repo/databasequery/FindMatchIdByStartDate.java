package org.repo.databasequery;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FindMatchIdByStartDate implements QueryDatabase{
    int tournamentId;
    int team1Id;
    int team2Id;
    Date date;
    public FindMatchIdByStartDate(int tournamentId,int team1Id,int team2ID,Date date){
        this.tournamentId = tournamentId;
        this.team1Id = team1Id;
        this.team2Id = team2ID;
        this.date = date;
    }
    @Override
    public int find(String queryTypeName, Connection connection) {
        if(connection!=null){
            PreparedStatement statement;
            try{
                String sqlCommandToGetMatchId = "SELECT * FROM Matches WHERE (team1_id = ? AND team2_id = ? AND tournament_id = ? AND date = ?) OR (team1_id = ? AND team2_id = ? AND tournament_id = ? AND date = ?)";
                try {
                    statement = connection.prepareStatement(sqlCommandToGetMatchId);
                    statement.setInt(1,team1Id);
                    statement.setInt(2,team2Id);
                    statement.setInt(3,tournamentId);
                    statement.setDate(4,date);
                    statement.setInt(5,team2Id);
                    statement.setInt(6,team1Id);
                    statement.setInt(7,tournamentId);
                    statement.setDate(8,date);
                    ResultSet resultSet = statement.executeQuery();
                    //System.out.println(team1Id + " " + team2Id + " " + tournamentId + " " + date);
                    if(resultSet.next())
                        return resultSet.getInt("id");
                    else return 0;
                }
                catch (Exception e){
                    System.out.println("Query not completed in databasequery.FindMatchIdByStartDate.");
                }

            }
            catch (Exception e){
                System.out.println("Statement not created in databasequery.FindMatchIdByStartDate.");
            }
            finally {
                try{
                    //connection.close();
                }
                catch (Exception e){
                    System.out.println("Connection not closed in databasequery.FindMatchIdByStartDate.");
                }
            }
        }
        else{
            System.out.println("Connection not established in databasequery.FinFindMatchIdByStartDatedMatchId.");
        }
        return 0;
    }
}
