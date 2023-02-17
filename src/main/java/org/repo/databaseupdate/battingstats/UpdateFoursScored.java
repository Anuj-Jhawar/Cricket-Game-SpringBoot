package org.repo.databaseupdate.battingstats;


import org.service.cricketgame.CricketGame;
import org.repo.databasequery.FindBattingStatsId;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateFoursScored implements UpdatePlayerBattingStats{
    private CricketGame game;
    private String teamName;
    private String playerName;
    public UpdateFoursScored(CricketGame game, String playerName, String teamName){
        this.game = game;
        this.playerName = playerName;
        this.teamName = teamName;
    }
    @Override
    public void update(int stats, Connection connection) {
        /*
            Update the fours played by Batsman in database.
        */
        FindBattingStatsId findBattingStatsId = new FindBattingStatsId(game,playerName,teamName);
        int battingStatsId = findBattingStatsId.find("",connection);
        if(connection!=null){
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfFoursPlayed = "UPDATE BattingStats SET Fours = Fours+? Where id = ?";
            try{
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfFoursPlayed);
                statement.setInt(1,1);
                statement.setInt(2,battingStatsId);
                statement.executeUpdate();

            }
            catch (Exception e){
                System.out.println("Statement not prepared in databaseupdate.battingstats.UpdateFoursScoredByPlayer.");
                System.out.println(e);
            }
        }
        else{
            System.out.println("Connection not established in databaseupdate.battingstats.UpdateFoursScoredByPlayer.");
        }
    }
}
