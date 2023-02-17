package org.repo.databasequery.scorecard.battingscorecard;


import org.repo.databasequery.FindMatchId;
import org.repo.databasequery.FindPlayerId;
import org.repo.databasequery.FindTeamId;
import org.service.cricketgame.CricketGame;
import org.repo.databasequery.scorecard.GetScoreCardFromDatabase;
import org.repo.jdbc.JdbcConnection;
import org.service.stats.BattingStats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GetBattingStatsFromDatabase implements GetScoreCardFromDatabase {
    public BattingStats createBattingStats(ResultSet resultSet){
        /*
            Create and return BattingStats object from the resultSet.
        */
        BattingStats battingStats = new BattingStats();
        try{
            battingStats.setBallsPlayed(resultSet.getInt("BallsPlayed"));
            battingStats.setScore(resultSet.getInt("RunsScored"));
            battingStats.setNumberOfFours(resultSet.getInt("Fours"));
            battingStats.setNumberOfSixes(resultSet.getInt("Sixes"));
            battingStats.setStrikeRate();
            return battingStats;
        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("Not able to create Batting stats in databasequery.scorecard.battingscorecard");
        }
        return null;
    }
    @Override
    public BattingStats getStats(CricketGame game, String teamName, String playerName) {
        /*
            Create and return BattingStats object from the BattingStats table.
        */
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection connection = jdbcConnection.getConnection();
        FindMatchId findMatchId = new FindMatchId(game);
        FindTeamId findTeamId = new FindTeamId();
        FindPlayerId findPlayerId = new FindPlayerId();
        int match_id = findMatchId.find("",connection);
        int team_id = findTeamId.find(teamName,connection);
        int player_id = findPlayerId.find(playerName,connection);
        if(connection!=null){
            PreparedStatement statement;
            try{
                String sqlQueryToFetchBattingStatsOfAPlayer = "SELECT * FROM BattingStats WHERE player_id = ? AND team_id = ? AND match_id = ?";
                statement = connection.prepareStatement(sqlQueryToFetchBattingStatsOfAPlayer);
                statement.setInt(1,player_id);
                statement.setInt(2,team_id);
                statement.setInt(3,match_id);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    return this.createBattingStats(resultSet);
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
                System.out.println("Connection not closed in databasequery.scorecard.battingscorecard");
            }
        }
        else{
            System.out.println("Connection not established in databasequery.scorecard.battingscorecard");
        }
        return null;
    }
}
