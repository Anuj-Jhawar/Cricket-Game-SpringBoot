package org.repo.databaseupdate.battingstats;



import org.service.cricketgame.CricketGame;
import org.repo.databasequery.FindBattingStatsId;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateRunsScored implements UpdatePlayerBattingStats{
    private CricketGame game;
    private String teamName;
    private String playerName;
    public UpdateRunsScored(CricketGame game,String playerName,String teamName){
        this.game = game;
        this.playerName = playerName;
        this.teamName = teamName;
    }
    @Override
    public void update(int stats, Connection connection) {
        /*
            Update the runsScored by Batsman in database.
        */
        FindBattingStatsId findBattingStatsId = new FindBattingStatsId(game,playerName,teamName);
        int battingStatsId = findBattingStatsId.find("",connection);
        if(connection!=null){
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfRunsScored= "UPDATE BattingStats SET RunsScored = RunsScored+? Where id = ?";
            try{
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfRunsScored);
                statement.setInt(1,stats);
                statement.setInt(2,battingStatsId);
                statement.executeUpdate();

            }
            catch (Exception e){
                System.out.println("Statement not prepared in databaseupdate.battingstats.UpdateRunsScored.");
                System.out.println(e);
            }
        }
        else{
            System.out.println("Connection not established in databaseupdate.battingstats.UpdateRunsScored.");
        }
    }
}
