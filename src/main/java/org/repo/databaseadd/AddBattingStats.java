package org.repo.databaseadd;



import org.repo.databasequery.FindMatchId;
import org.repo.databasequery.FindPlayerId;
import org.repo.databasequery.FindTeamId;
import org.service.cricketgame.CricketGame;
import org.repo.jdbc.JdbcConnection;
import org.service.stats.BattingStats;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddBattingStats implements AddToTable {
    private BattingStats battingStats;
    private String playerName;
    private String teamName;
    private CricketGame game;
    public AddBattingStats(BattingStats battingStats, String playerName, String teamName, CricketGame game){
        this.battingStats = battingStats;
        this.playerName = playerName;
        this.teamName = teamName;
        this.game = game;
    }
    @Override
    public void add() {
        /*
            Add BattingStats for a specific player for the current match.
        */
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection connection = jdbcConnection.getConnection();
        FindMatchId findMatchId = new FindMatchId(game);
        int matchId = findMatchId.find("",connection);
        FindTeamId findTeamId = new FindTeamId();
        int teamId = findTeamId.find(teamName,connection);
        FindPlayerId findPlayerId = new FindPlayerId();
        int playerId = findPlayerId.find(playerName,connection);
        if(connection!=null){
            PreparedStatement statement;
            try{
                String sqlCommandToInsertBattingStatsInBattingStatsTable = "INSERT INTO BattingStats (player_id, team_id, match_id, RunsScored, BallsPlayed,Fours,Sixes,NotOut,StrikeRate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(sqlCommandToInsertBattingStatsInBattingStatsTable);
                statement.setInt(1, playerId);
                statement.setInt(2, teamId);
                statement.setInt(3, matchId);
                statement.setInt(4,battingStats.getScore());
                statement.setInt(5,battingStats.getBallsPlayed());
                statement.setInt(6,battingStats.getNumberOfFours());
                statement.setInt(7,battingStats.getNumberOfSixes());
                statement.setInt(8,1);
                statement.setDouble(9,0);
                try {
                    statement.executeUpdate();
                }
                catch (Exception e){
                    System.out.println("Query not completed in databaseadd.AddBattingStatsToBattingStatsTable.");
                }
            }
            catch (Exception e){
                System.out.println("Statement not created in databaseadd.AddBattingStatsToBattingStatsTable.");
            }
            finally {
                try{
                    //connection.close();
                }
                catch (Exception e){
                    System.out.println("Connection not closed in databaseadd.AddBattingStatsToBattingStatsTable.");
                }
            }
        }
        else{
            System.out.println("Connection not established in databaseadd.AddBattingStatsToBattingStatsTable.");
        }
    }
}
