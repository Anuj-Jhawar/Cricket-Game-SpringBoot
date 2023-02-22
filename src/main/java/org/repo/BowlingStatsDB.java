package org.repo;


import org.service.GetBowlingStatsFromDatabase;
import org.model.stats.BowlingStats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BowlingStatsDB {
    int matchId;
    int playerId;
    int teamId;
    Connection connection;
    public BowlingStatsDB(int matchId,int teamId,int playerId){
        this.matchId = matchId;
        this.teamId = teamId;
        this.playerId = playerId;
        this.connection = JdbcConnection.getConnection();
    }
    public void addBowlingStats(BowlingStats bowlingStats){
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
                    System.out.println(e.getMessage());
                    System.out.println("Query not completed in org.repo.BowlingStatsDB.addBowlingStats.");
                }
            }
            catch (Exception e){
                System.out.println("Statement not created in org.repo.BowlingStatsDB.addBowlingStats.");
            }
        }
        else{
            System.out.println("Connection not established in org.repo.BowlingStatsDB.addBowlingStats.");
        }
    }
    public int getBowlingStatsId(){
        if(connection!=null){
            PreparedStatement statement;
            try{
                String sqlCommandToGetBowlingStatsId = "SELECT * FROM BowlingStats WHERE player_id = ? AND team_id = ?  AND match_id = ?";
                try {
                    statement = connection.prepareStatement(sqlCommandToGetBowlingStatsId);
                    statement.setInt(1,playerId);
                    statement.setInt(2,teamId);
                    statement.setInt(3,matchId);
                    ResultSet resultSet = statement.executeQuery();
                    if(resultSet.next())
                        return resultSet.getInt("id");
                    else return 0;
                }
                catch (Exception e){
                    System.out.println("Query not completed in org.repo.BowlingStatsDB.getBowlingStatsId.");
                }

            }
            catch (Exception e){
                System.out.println("Statement not created in org.repo.BowlingStatsDB.getBowlingStatsId.");
            }
        }
        else{
            System.out.println("Connection not established in org.repo.BowlingStatsDB.getBowlingStatsId.");
        }
        return 0;
    }
    public void updateBowlingStats(int outComeOfTheBall,int runsConceded,int wickets){
        int bowlingStatsId = this.getBowlingStatsId();
        this.updateBallsBalled(bowlingStatsId);
        if(outComeOfTheBall==7){
            this.updateWicketsTaken(bowlingStatsId);
        }
        else{
            this.updateRunsConceded(bowlingStatsId,outComeOfTheBall);
        }
        this.updateBowlingAverage(bowlingStatsId,runsConceded,wickets);

    }
    public void updateBallsBalled(int bowlingStatsId){
        if(connection!=null){
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfBallsBalled = "UPDATE BowlingStats SET BallsBalled = BallsBalled+1 Where id = ?";
            try{
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfBallsBalled);
                statement.setInt(1,bowlingStatsId);
                statement.executeUpdate();

            }
            catch (Exception e){
                System.out.println("Statement not prepared in org.repo.BowlingStatsDB.updateBallsBalled.");
                System.out.println(e);
            }
        }
        else{
            System.out.println("Connection not established in org.repo.BowlingStatsDB.updateBallsBalled.");
        }
    }
    public void updateBowlingAverage(int bowlingStatsId,int runsConceded, int wicketsTaken){
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
                System.out.println("Statement not prepared in org.repo.BowlingStatsDB.updateBowlingAverage.");
                System.out.println(e);
            }
        }
        else{
            System.out.println("Connection not established in org.repo.BowlingStatsDB.updateBowlingAverage.");
        }
    }
    public void updateRunsConceded(int bowlingStatsId,int runsConceded){
        if(connection!=null){
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfRunsConceded = "UPDATE BowlingStats SET RunsConceded = RunsConceded+? Where id = ?";
            try{
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfRunsConceded);
                statement.setInt(1,runsConceded);
                statement.setInt(2,bowlingStatsId);
                statement.executeUpdate();

            }
            catch (Exception e){
                System.out.println("Statement not prepared in org.repo.BowlingStatsDB.updateRunsConceded.");
                System.out.println(e);
            }
        }
        else{
            System.out.println("Connection not established in org.repo.BowlingStatsDB.updateRunsConceded.");
        }
    }
    public void updateWicketsTaken(int bowlingStatsId){
        if(connection!=null){
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfWicketsTaken = "UPDATE BowlingStats SET Wickets = Wickets+1 Where id = ?";
            try{
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfWicketsTaken);
                statement.setInt(1,bowlingStatsId);
                statement.executeUpdate();
            }
            catch (Exception e){
                System.out.println("Statement not prepared in org.repo.BowlingStatsDB.updateWicketsTaken.");
                System.out.println(e);
            }
        }
        else{
            System.out.println("Connection not established in org.repo.BowlingStatsDB.updateWicketsTaken.");
        }
    }
    public BowlingStats getBowlingStats(){
        if(connection!=null){
            PreparedStatement statement;
            try{
                String sqlQueryToFetchBattingStatsOfAPlayer = "SELECT * FROM BowlingStats WHERE player_id = ? AND team_id = ? AND match_id = ?";
                //System.out.println(playerId + " " + teamId + " " + matchId);
                statement = connection.prepareStatement(sqlQueryToFetchBattingStatsOfAPlayer);
                statement.setInt(1,playerId);
                statement.setInt(2,teamId);
                statement.setInt(3,matchId);
                ResultSet resultSet = statement.executeQuery();
                if(resultSet.next()){
                    GetBowlingStatsFromDatabase getBowlingStatsFromDatabase = new GetBowlingStatsFromDatabase();
                    return getBowlingStatsFromDatabase.createBowlingStats(resultSet);
                }
                else{
                    System.out.println("Not able to fetch stats from org.repo.BowlingStatsDB.getBowlingStats");
                    return null;
                }
            }
            catch (Exception e){
                System.out.println(e);
                System.out.println("org.repo.BowlingStatsDB.getBowlingStats.");
            }
        }
        else{
            System.out.println("Connection not established in org.repo.BowlingStatsDB.getBowlingStats.");
        }
        return null;
    }
}
