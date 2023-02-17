package org.repo.databaseupdate.bowlingstats;


import org.service.cricketgame.CricketGame;
import org.repo.databasequery.FindBowlingStatsId;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateBowlingAverage implements UpdatePlayerBowlingStats {
    private CricketGame game;
    private String teamName;
    private String playerName;
    private int runsConceded;
    private int wicketsTaken;
    public UpdateBowlingAverage(CricketGame game, String playerName, String teamName, int runsConceded, int wicketsTaken){
        this.game = game;
        this.playerName = playerName;
        this.teamName = teamName;
        this.runsConceded = runsConceded;
        this.wicketsTaken = wicketsTaken;
    }
    @Override
    public void update(int stats,Connection connection) {
        /*
            Update the BowlingAverage of Bowler in database.
        */
        FindBowlingStatsId findBowlingStatsId = new FindBowlingStatsId(game,playerName,teamName);
        int bowlingStatsId = findBowlingStatsId.find("",connection);
        if(connection!=null){
            PreparedStatement statement;
            double Average = runsConceded*1.0;
            if(wicketsTaken!=0)
            Average = (runsConceded*1.0)/wicketsTaken;
            String SqlQueryToUpdateBowlingAverageOfAPlayer = "UPDATE BowlingStats SET Average = ? Where id = ?";
            try{
                statement = connection.prepareStatement(SqlQueryToUpdateBowlingAverageOfAPlayer);
                statement.setDouble(1,Average);
                statement.setInt(2,bowlingStatsId);
                statement.executeUpdate();

            }
            catch (Exception e){
                System.out.println("Statement not prepared in databaseupdate.bowlingstats.UpdateBowlingAverageOfPlayer.");
                System.out.println(e);
            }
        }
        else{
            System.out.println("Connection not established in databaseupdate.bowlingstats.UpdateBowlingAverageOfPlayer.");
        }
    }
}
