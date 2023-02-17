package org.repo.databasequery;

import org.repo.databasequery.scorecard.bowlingscorecard.GetBowlingStatsFromDatabase;
import org.service.stats.BowlingStats;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FindBowlingStatsOfAPlayerForAMatch {
    public BowlingStats find(String tournamentName, String team1Name, String team2Name, String playerName, Date date, Connection connection){
        FindTournamentId findTournamentId = new FindTournamentId();
        int tournamentId = findTournamentId.find(tournamentName,connection);
        FindTeamId findTeamId = new FindTeamId();
        int team1Id = findTeamId.find(team1Name,connection);
        int team2Id = findTeamId.find(team2Name,connection);
        FindPlayerId findPlayerId = new FindPlayerId();
        int playerId = findPlayerId.find(playerName,connection);
        FindMatchIdByStartDate findMatchIdByStartDate = new FindMatchIdByStartDate(tournamentId,team1Id,team2Id,date);
        int matchId = findMatchIdByStartDate.find("",connection);
        if(connection!=null){
            PreparedStatement statement;
            try{
                String sqlQueryToFetchBattingStatsOfAPlayer = "SELECT * FROM BowlingStats WHERE player_id = ? AND team_id = ? AND match_id = ?";
                statement = connection.prepareStatement(sqlQueryToFetchBattingStatsOfAPlayer);
                statement.setInt(1,playerId);
                statement.setInt(2,team1Id);
                statement.setInt(3,matchId);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    GetBowlingStatsFromDatabase getBowlingStatsFromDatabase = new GetBowlingStatsFromDatabase();
                    return (BowlingStats) getBowlingStatsFromDatabase.createBowlingStats(resultSet);
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
                System.out.println("Connection not closed in databasequery.scorecard.FindBowlingStatsOfAPlayerForSpecificMatch");
            }
        }
        else{
            System.out.println("Connection not established in databasequery.scorecard.FindBowlingStatsOfAPlayerForSpecificMatch");
        }
        return null;
    }
}
