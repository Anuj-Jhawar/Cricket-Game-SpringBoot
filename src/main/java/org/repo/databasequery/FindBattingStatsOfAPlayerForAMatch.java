package org.repo.databasequery;

import org.repo.databasequery.scorecard.battingscorecard.GetBattingStatsFromDatabase;
import org.service.stats.BattingStats;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FindBattingStatsOfAPlayerForAMatch {
    public BattingStats find(int tournamentId, int team1Id, int team2Id, int playerId, Date date, Connection connection) {
        FindMatchIdByStartDate findMatchIdByStartDate = new FindMatchIdByStartDate(tournamentId,team1Id,team2Id,date);
        int matchId = findMatchIdByStartDate.find("",connection);
        if(connection!=null){
            PreparedStatement statement;
            try{
                String sqlQueryToFetchBattingStatsOfAPlayer = "SELECT * FROM BattingStats WHERE player_id = ? AND team_id = ? AND match_id = ?";
                statement = connection.prepareStatement(sqlQueryToFetchBattingStatsOfAPlayer);
                statement.setInt(1,playerId);
                statement.setInt(2,team1Id);
                statement.setInt(3,matchId);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    GetBattingStatsFromDatabase getBattingStatsFromDatabase = new GetBattingStatsFromDatabase();
                    return getBattingStatsFromDatabase.createBattingStats(resultSet);
                }
                else{
                    return null;
                }
            }
            catch (Exception e){
                System.out.println(e);
                System.out.println("Not able to fetch the batting stats from database");
            }
            try{
                connection.close();
            }
            catch (Exception e){
                System.out.println(e);
                System.out.println("Connection not closed in databasequery.scorecard.FindBattingStatsOfAPlayerForSpecificMatch");
            }
        }
        else{
            System.out.println("Connection not established in databasequery.scorecard.FindBattingStatsOfAPlayerForSpecificMatch");
        }
        return null;
    }
}
