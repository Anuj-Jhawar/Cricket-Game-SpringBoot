package org.project.utilities;

import org.project.model.stats.BowlingStats;

import java.sql.ResultSet;

public class GetBowlingStatsFromDatabase {

    public BowlingStats createBowlingStats(ResultSet resultSet) {
        /*
            Create and return BowlingStats object from the resultSet.
        */
        BowlingStats bowlingStats = new BowlingStats();
        try {
            bowlingStats.setBallsBowled(resultSet.getInt("BallsBalled"));
            bowlingStats.setWickets(resultSet.getInt("Wickets"));
            bowlingStats.setRunsConceded(resultSet.getInt("RunsConceded"));
            return bowlingStats;
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Not able to create Batting stats in databasequery.scorecard.battingscorecard");
        }
        return null;
    }
}
