package org.project.repo;


import org.project.model.ScoreCardForPlayer;
import org.project.service.PlayerService;
import org.project.utilities.GetBowlingStatsFromDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Repository
public class BowlingStatsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BowlingStatsRepository.class);
    private Connection connection;
    @Autowired
    private PlayerService playerService;

    public void addBowlingStats(org.project.model.stats.BowlingStats bowlingStats, int matchId, int teamId,
                                int playerId) {
        /*
            Add bowling stats for a given match for a player.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToInsertBowlingStatsToBowlingStatsTable
                        = "INSERT INTO BowlingStats (player_id, team_id, match_id, RunsConceded, BallsBalled,Wickets,Average) VALUES (?, ?, ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(sqlCommandToInsertBowlingStatsToBowlingStatsTable);
                statement.setInt(1, playerId);
                statement.setInt(2, teamId);
                statement.setInt(3, matchId);
                statement.setInt(4, bowlingStats.getRunConceded());
                statement.setInt(5, bowlingStats.getBallsBowled());
                statement.setInt(6, bowlingStats.getWickets());
                statement.setDouble(7, 0);
                try {
                    statement.executeUpdate();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BowlingStatsDB.addBowlingStats.");
        }
    }

    public void updateBowlingStats(int matchId, int teamId, int playerId, int outComeOfTheBall,
                                   org.project.model.stats.BowlingStats bowlingStats) {
        /*
            Update bowling stats for a given match for given player.
        */
        connection = JdbcConnection.getConnection();
        int runsConceded = bowlingStats.getRunConceded();
        int wickets = bowlingStats.getWickets();
        int ballsBalled = bowlingStats.getBallsBowled();
        int bowlingStatsId = this.getBowlingStatsId(matchId, teamId, playerId);
        this.updateBallsBalled(bowlingStatsId, ballsBalled);
        if (outComeOfTheBall == 7) {
            this.updateWicketsTaken(bowlingStatsId);
        } else {
            this.updateRunsConceded(bowlingStatsId, runsConceded);
        }
        this.updateBowlingAverage(bowlingStatsId, runsConceded, wickets);

    }

    public int getBowlingStatsId(int matchId, int teamId, int playerId) {
        /*
            Return bowling stats id for a given player for a given match.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToGetBowlingStatsId
                        = "SELECT * FROM BowlingStats WHERE player_id = ? AND team_id = ?  AND match_id = ?";
                try {
                    statement = connection.prepareStatement(sqlCommandToGetBowlingStatsId);
                    statement.setInt(1, playerId);
                    statement.setInt(2, teamId);
                    statement.setInt(3, matchId);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                }

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BowlingStatsDB.getBowlingStatsId.");
        }
        return 0;
    }

    public void updateBallsBalled(int bowlingStatsId, int ballsBalled) {
        /*
            Update ballsBalled for a given bowlingStats row.
        */
        if (connection != null) {
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfBallsBalled = "UPDATE BowlingStats SET BallsBalled = ? Where id = ?";
            try {
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfBallsBalled);
                statement.setInt(1, ballsBalled);
                statement.setInt(2, bowlingStatsId);
                statement.executeUpdate();

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BowlingStatsDB.updateBallsBalled.");
        }
    }

    public void updateWicketsTaken(int bowlingStatsId) {
        /*
            Update wicketTaken for a given bowlingStats row.
        */
        if (connection != null) {
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfWicketsTaken = "UPDATE BowlingStats SET Wickets = Wickets+1 Where id = ?";
            try {
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfWicketsTaken);
                statement.setInt(1, bowlingStatsId);
                statement.executeUpdate();
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BowlingStatsDB.updateWicketsTaken.");
        }
    }

    public void updateRunsConceded(int bowlingStatsId, int runsConceded) {
        /*
            Update runsScored for a given bowlingStats row.
        */
        if (connection != null) {
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfRunsConceded = "UPDATE BowlingStats SET RunsConceded = ? Where id = ?";
            try {
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfRunsConceded);
                statement.setInt(1, runsConceded);
                statement.setInt(2, bowlingStatsId);
                statement.executeUpdate();

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BowlingStatsDB.updateRunsConceded.");
        }
    }

    public void updateBowlingAverage(int bowlingStatsId, int runsConceded, int wicketsTaken) {
        /*
            Update bowlingAverage for a given bowlingStats row.
        */
        if (connection != null) {
            PreparedStatement statement;
            double Average = runsConceded * 1.0;
            if (wicketsTaken != 0) {
                Average = (runsConceded * 1.0) / wicketsTaken;
            }
            String SqlQueryToUpdateBowlingAverageOfAPlayer = "UPDATE BowlingStats SET Average = ? Where id = ?";
            try {
                statement = connection.prepareStatement(SqlQueryToUpdateBowlingAverageOfAPlayer);
                statement.setDouble(1, Average);
                statement.setInt(2, bowlingStatsId);
                statement.executeUpdate();

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BowlingStatsDB.updateBowlingAverage.");
        }
    }

    public org.project.model.stats.BowlingStats getBowlingStats(int matchId, int teamId, int playerId) {
        /*
            Return bowlingStats.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlQueryToFetchBattingStatsOfAPlayer
                        = "SELECT * FROM BowlingStats WHERE player_id = ? AND team_id = ? AND match_id = ?";
                //System.out.println(playerId + " " + teamId + " " + matchId);
                statement = connection.prepareStatement(sqlQueryToFetchBattingStatsOfAPlayer);
                statement.setInt(1, playerId);
                statement.setInt(2, teamId);
                statement.setInt(3, matchId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    GetBowlingStatsFromDatabase getBowlingStatsFromDatabase = new GetBowlingStatsFromDatabase();
                    return getBowlingStatsFromDatabase.createBowlingStats(resultSet);
                } else {
                    LOGGER.error("Not able to fetch stats from org.repo.BowlingStatsDB.getBowlingStats");
                    return null;
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BowlingStatsDB.getBowlingStats.");
        }
        return null;
    }

    public ArrayList<ScoreCardForPlayer> getBowlingScoreCardOfAnInning(int matchId, int teamId, Connection connection) {
        /*
            Return bowling scorecard for a given match
        */
        ArrayList<ScoreCardForPlayer> bowlingStats = new ArrayList<ScoreCardForPlayer>();
        ResultSet resultSet;
        if (connection != null) {
            PreparedStatement statement;
            String sqlCommandToGetBowlingScoreCardOfAnInning
                    = "SELECT * FROM BowlingStats WHERE team_id = ? AND match_id = ?";
            try {
                statement = connection.prepareStatement(sqlCommandToGetBowlingScoreCardOfAnInning);
                statement.setInt(1, teamId);
                statement.setInt(2, matchId);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    do {
                        GetBowlingStatsFromDatabase getBowlingStatsFromDatabase = new GetBowlingStatsFromDatabase();
                        int playerId = resultSet.getInt("player_id");
                        String playerName = playerService.getPlayerName(playerId);
                        ScoreCardForPlayer scoreCardForPlayer = new ScoreCardForPlayer(playerName,
                                getBowlingStatsFromDatabase.createBowlingStats(resultSet));
                        bowlingStats.add(scoreCardForPlayer);
                    } while (resultSet.next());
                    return bowlingStats;
                } else {
                    return null;
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.GetBowlingScoreCardOfAnInning.");
        }
        return bowlingStats;
    }
}
