package org.repo.databaseupdate.bowlingstats;

import org.service.cricketgame.CricketGame;
import org.repo.databasequery.FindBowlingStatsId;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateWicketsTaken implements UpdatePlayerBowlingStats{
    private CricketGame game;
    private String teamName;
    private String playerName;
    public UpdateWicketsTaken(CricketGame game, String playerName, String teamName){
        this.game = game;
        this.playerName = playerName;
        this.teamName = teamName;
    }
    @Override
    public void update(int stats, Connection connection) {
        /*
            Update the WicketsTaken by Bowler in database.
        */
        FindBowlingStatsId findBowlingStatsId = new FindBowlingStatsId(game,playerName,teamName);
        int bowlingStatsId = findBowlingStatsId.find("",connection);
        if(connection!=null){
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfWicketsTaken = "UPDATE BowlingStats SET Wickets = Wickets+1 Where id = ?";
            try{
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfWicketsTaken);
                statement.setInt(1,bowlingStatsId);
                statement.executeUpdate();
            }
            catch (Exception e){
                System.out.println("Statement not prepared in databaseupdate.bowlingstats.UpdateWicketsTakenByPlayer.");
                System.out.println(e);
            }
        }
        else{
            System.out.println("Connection not established in databaseupdate.bowlingstats.UpdateWicketsTakenByPlayer.");
        }
    }
}
