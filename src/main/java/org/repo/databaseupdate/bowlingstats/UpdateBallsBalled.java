package org.repo.databaseupdate.bowlingstats;



import org.service.cricketgame.CricketGame;
import org.repo.databasequery.FindBowlingStatsId;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateBallsBalled implements UpdatePlayerBowlingStats{
    private CricketGame game;
    private String teamName;
    private String playerName;
    public UpdateBallsBalled(CricketGame game,String playerName,String teamName){
        this.game = game;
        this.playerName = playerName;
        this.teamName = teamName;
    }
    @Override
    public void update(int stats, Connection connection) {
        /*
            Update the ballsBalled by Bowler in database.
        */
        FindBowlingStatsId findBowlingStatsId = new FindBowlingStatsId(game,playerName,teamName);
        int bowlingStatsId = findBowlingStatsId.find("",connection);
        if(connection!=null){
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfBallsBalled = "UPDATE BowlingStats SET BallsBalled = BallsBalled+1 Where id = ?";
            try{
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfBallsBalled);
                statement.setInt(1,bowlingStatsId);
                statement.executeUpdate();

            }
            catch (Exception e){
                System.out.println("Statement not prepared in databaseupdate.bowlingstats.UpdateBallsBalled.");
                System.out.println(e);
            }
        }
        else{
            System.out.println("Connection not established in databaseupdate.bowlingstats.UpdateBallsBalled.");
        }
    }
}
