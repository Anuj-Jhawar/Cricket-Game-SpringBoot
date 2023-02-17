package org.repo.databaseupdate.battingstats;



import org.service.cricketgame.CricketGame;
import org.repo.databasequery.FindBattingStatsId;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateNotOut implements UpdatePlayerBattingStats{
    private CricketGame game;
    private String teamName;
    private String playerName;
    public UpdateNotOut(CricketGame game, String playerName, String teamName){
        this.game = game;
        this.playerName = playerName;
        this.teamName = teamName;
    }
    @Override
    public void update(int stats, Connection connection) {
        /*
            Update the NotOut column if Batsman is out in database.
        */
        FindBattingStatsId findBattingStatsId = new FindBattingStatsId(game,playerName,teamName);
        int battingStatsId = findBattingStatsId.find("",connection);
        if(connection!=null){
            PreparedStatement statement;
            String SqlQueryToUpdateNotOut = "UPDATE BattingStats SET NotOut = 0 Where id = ?";
            try{
                statement = connection.prepareStatement(SqlQueryToUpdateNotOut);
                statement.setInt(1,battingStatsId);
                statement.executeUpdate();

            }
            catch (Exception e){
                System.out.println("Statement not prepared in databaseupdate.battingstats.UpdateNotOutColumnInTable.");
                System.out.println(e);
            }
        }
        else{
            System.out.println("Connection not established in databaseupdate.battingstats.UpdateNotOutColumnInTable.");
        }
    }
}
