package org.repo.databaseadd;



import org.repo.databasequery.FindMatchId;
import org.repo.databasequery.FindPlayerId;
import org.repo.databasequery.FindTeamId;
import org.service.cricketgame.CricketGame;
import org.repo.jdbc.JdbcConnection;
import org.service.stats.BowlingStats;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddBowlingStats implements AddToTable {
    private BowlingStats bowlingStats;
    private String playerName;
    private String teamName;
    private CricketGame game;
    public AddBowlingStats(BowlingStats bowlingStats, String playerName, String teamName, CricketGame game){
        this.bowlingStats = bowlingStats;
        this.playerName = playerName;
        this.teamName = teamName;
        this.game = game;
    }
    @Override
    public void add() {
        /*
            Add BowlingStats for a specific player for the current match.
        */
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection connection = jdbcConnection.getConnection();
        FindMatchId findMatchId = new FindMatchId(game);
        int matchId = findMatchId.find(teamName,connection);
        FindTeamId findTeamId = new FindTeamId();
        int teamId = findTeamId.find(teamName,connection);
        FindPlayerId findPlayerId = new FindPlayerId();
        int playerId = findPlayerId.find(playerName,connection);
        if(connection!=null){
            PreparedStatement statement;
            try{
                String sqlCommandToInsertBowlingStatsToBowlingStatsTable = "INSERT INTO BowlingStats (player_id, team_id, match_id, RunsConceded, BallsBalled,Wickets,Average) VALUES (?, ?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(sqlCommandToInsertBowlingStatsToBowlingStatsTable);
                statement.setInt(1, playerId);
                statement.setInt(2, teamId);
                statement.setInt(3, matchId);
                statement.setInt(4,bowlingStats.getRunConceded());
                statement.setInt(5,bowlingStats.getBallsBowled());
                statement.setInt(6,bowlingStats.getWickets());
                statement.setDouble(7,0);
                try {
                    statement.executeUpdate();
                }
                catch (Exception e){
                    System.out.println("Query not completed in databaseadd.AddBowlingStatsToBowlingStatsTable.");
                }
            }
            catch (Exception e){
                System.out.println("Statement not created in databaseadd.AddBowlingStatsToBowlingStatsTable.");
            }
            finally {
                try{
                    //connection.close();
                }
                catch (Exception e){
                    System.out.println("Connection not closed in databaseadd.AddBowlingStatsToBowlingStatsTable.");
                }
            }
        }
        else{
            System.out.println("Connection not established in databaseadd.AddBowlingStatsToBowlingStatsTable.");
        }
    }
}
