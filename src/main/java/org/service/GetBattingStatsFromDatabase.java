package org.service;



import org.service.stats.BattingStats;
import java.sql.ResultSet;

public class GetBattingStatsFromDatabase{
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
}
