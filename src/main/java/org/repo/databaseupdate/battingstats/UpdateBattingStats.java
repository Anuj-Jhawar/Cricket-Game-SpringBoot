package org.repo.databaseupdate.battingstats;



import org.service.cricketgame.CricketGame;
import org.service.cricketgame.Team;
import org.repo.jdbc.JdbcConnection;
import org.service.player.Player;

import java.sql.Connection;

public class UpdateBattingStats {
    CricketGame game;
    Player player;
    Team team;
    int outcomeOfTheBall;
    public UpdateBattingStats(CricketGame game, Team team, Player player, int outcomeOfTheBall){
        this.game = game;
        this.team = team;
        this.player = player;
        this.outcomeOfTheBall = outcomeOfTheBall;
    }
    void updateBallsPlayed(Connection connection){
        UpdateBallsPlayed updateBallsPlayed = new UpdateBallsPlayed(game, player.getName(), team.getTeamName());
        updateBallsPlayed.update(outcomeOfTheBall,connection );
    }
    void updateNotOutOrNot(Connection connection){
        UpdateNotOut updateNotOutColumnInTable = new UpdateNotOut(game, player.getName(), team.getTeamName());
        updateNotOutColumnInTable.update(outcomeOfTheBall, connection);
    }
    void updateRunsScored(Connection connection){
        UpdateRunsScored updateRunsScored = new UpdateRunsScored(game, player.getName(), team.getTeamName());
        updateRunsScored.update(outcomeOfTheBall, connection);
    }
    void FoursScoredByPlayer(Connection connection){
        UpdateFoursScored updateFoursScoredByPlayer = new UpdateFoursScored(game, player.getName(),team.getTeamName());
        updateFoursScoredByPlayer.update(outcomeOfTheBall, connection);
    }
    void SixesScoredByPlayer(Connection connection){
        UpdateSixesScored updateSixesScoredByPlayer = new UpdateSixesScored(game, player.getName(),team.getTeamName());
        updateSixesScoredByPlayer.update(outcomeOfTheBall,connection);
    }
    void updateStrikeRateOfPlayer(Connection connection){
        UpdateStrikeRate updateStrikeRateOfPlayer = new UpdateStrikeRate(game, player.getName(), team.getTeamName(),player.getBattingStats().getScore(),player.getBattingStats().getBallsPlayed());
        updateStrikeRateOfPlayer.update(outcomeOfTheBall, connection);
    }
    public void updateBattingStatsOfPlayer(){
        /*
            Update all the stats of Batsman in database.
        */
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection connection = jdbcConnection.getConnection();
        this.updateBallsPlayed(connection);
        if(outcomeOfTheBall==7){
            this.updateNotOutOrNot(connection);
        }
        else{
            this.updateRunsScored(connection);
            if(outcomeOfTheBall==4){
                this.FoursScoredByPlayer(connection);
            }
            else if(outcomeOfTheBall==6){
                this.SixesScoredByPlayer(connection);
            }
        }
        this.updateStrikeRateOfPlayer(connection);
        try{
            connection.close();
        }
        catch (Exception e){
            System.out.println(e);
            System.out.println("Connection not closed.");
        }
    }
}
