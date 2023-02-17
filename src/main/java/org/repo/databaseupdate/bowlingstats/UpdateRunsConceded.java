package org.repo.databaseupdate.bowlingstats;


import org.service.cricketgame.CricketGame;
import org.repo.databasequery.FindBowlingStatsId;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateRunsConceded implements UpdatePlayerBowlingStats {
    private CricketGame game;
    private String teamName;
    private String playerName;
    public UpdateRunsConceded(CricketGame game,String playerName,String teamName){
        this.game = game;
        this.playerName = playerName;
        this.teamName = teamName;
    }
    @Override
    public void update(int stats,Connection connection) {
        /*
            Update the RunsConceded by Bowler in database.
        */
        FindBowlingStatsId findBowlingStatsId = new FindBowlingStatsId(game,playerName,teamName);
        int bowlingStatsId = findBowlingStatsId.find("",connection);
        if(connection!=null){
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfRunsConceded = "UPDATE BowlingStats SET RunsConceded = RunsConceded+? Where id = ?";
            try{
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfRunsConceded);
                statement.setInt(1,stats);
                statement.setInt(2,bowlingStatsId);
                statement.executeUpdate();

            }
            catch (Exception e){
                System.out.println("Statement not prepared in databaseupdate.bowlingstats.UpdateRunsConceded.");
                System.out.println(e);
            }
        }
        else{
            System.out.println("Connection not established in databaseupdate.bowlingstats.UpdateRunsConceded.");
        }
    }
}
