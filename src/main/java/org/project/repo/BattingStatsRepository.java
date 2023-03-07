package org.project.repo;

import org.project.model.ScoreCardForPlayer;
import org.project.service.PlayerService;
import org.project.utilities.GetBattingStatsFromDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Repository
public class BattingStatsRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(BattingStatsRepository.class);
    private Connection connection;
    @Autowired
    private PlayerService playerService;

    public void addBattingStats(org.project.model.stats.BattingStats battingStats, int matchId, int teamId,
                                int playerId) {
        /*
            Adding batting stats row for a given player for a particular match.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToInsertBattingStatsInBattingStatsTable
                        = "INSERT INTO BattingStats (player_id, team_id, match_id, RunsScored, BallsPlayed,Fours," +
                          "Sixes,NotOut,StrikeRate,is_deleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,0)";
                statement = connection.prepareStatement(sqlCommandToInsertBattingStatsInBattingStatsTable);
                statement.setInt(1, playerId);
                statement.setInt(2, teamId);
                statement.setInt(3, matchId);
                statement.setInt(4, battingStats.getScore());
                statement.setInt(5, battingStats.getBallsPlayed());
                statement.setInt(6, battingStats.getNumberOfFours());
                statement.setInt(7, battingStats.getNumberOfSixes());
                statement.setInt(8, 1);
                statement.setDouble(9, 0);
                try {
                    statement.executeUpdate();
                } catch (Exception e) {
                    LOGGER.error(e.getMessage());
                    LOGGER.error("Query not completed in org.repo.BattingStats.addBattingStats");
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BattingStats.addBattingStats");
        }
    }

    public void updateBattingStats(int matchId, int teamId, int playerId, int outComeOfTheBall, int runsScored,
                                   int ballsPlayed) {
        /*
            Adding batting stats row for a given player for a particular match.
        */
        connection = JdbcConnection.getConnection();
        int battingStatsId = this.getBattingStatsId(matchId, teamId, playerId);
        this.updateBallsPlayed(battingStatsId);
        if (outComeOfTheBall == 7) {
            this.updateNotOut(battingStatsId);
        } else {
            this.updateRunsScored(outComeOfTheBall, battingStatsId);
            if (outComeOfTheBall == 4) {
                this.updateFoursScored(battingStatsId);
            } else if (outComeOfTheBall == 6) {
                this.updateSixesScored(battingStatsId);
            }
        }
        this.updateStrikeRate(battingStatsId, runsScored, ballsPlayed);
    }

    public int getBattingStatsId(int matchId, int teamId, int playerId) {
        /*
            Getting batting stats row of a given player for a particular match.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToGetBattingStatsId
                        = "SELECT * FROM BattingStats WHERE player_id = ? AND team_id = ?  AND match_id = ?";
                try {
                    statement = connection.prepareStatement(sqlCommandToGetBattingStatsId);
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
            LOGGER.error("Connection not established in org.repo.BattingStats.getBattingStatsId");
        }
        return 1;
    }

    public void updateBallsPlayed(int battingStatsId) {
        /*
            Update ballsPlayed for a given battingStats row.
        */
        if (connection != null) {
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfBallsPlayed
                    = "UPDATE BattingStats SET BallsPlayed = BallsPlayed+1 Where id = ?";
            try {
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfBallsPlayed);
                statement.setInt(1, battingStatsId);
                statement.executeUpdate();

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BattingStats.updateBallsPlayed");
        }
    }

    public void updateNotOut(int battingStatsId) {
        /*
            Update if the batsman is out.
        */
        if (connection != null) {
            PreparedStatement statement;
            String SqlQueryToUpdateNotOut = "UPDATE BattingStats SET NotOut = 0 Where id = ?";
            try {
                statement = connection.prepareStatement(SqlQueryToUpdateNotOut);
                statement.setInt(1, battingStatsId);
                statement.executeUpdate();

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BattingStats.updateNotOut");
        }
    }

    public void updateRunsScored(int runsScored, int battingStatsId) {
        /*
            Update runScored for a given battingStats row.
        */
        if (connection != null) {
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfRunsScored
                    = "UPDATE BattingStats SET RunsScored = RunsScored+? Where id = ?";
            try {
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfRunsScored);
                statement.setInt(1, runsScored);
                statement.setInt(2, battingStatsId);
                statement.executeUpdate();

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BattingStats.updateRunsScored");
        }
    }

    public void updateFoursScored(int battingStatsId) {
        /*
            update fours scored for a given battingStats row.
        */
        if (connection != null) {
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfFoursPlayed = "UPDATE BattingStats SET Fours = Fours+? Where id = ?";
            try {
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfFoursPlayed);
                statement.setInt(1, 1);
                statement.setInt(2, battingStatsId);
                statement.executeUpdate();

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BattingStats.updateFoursScored");
        }
    }

    public void updateSixesScored(int battingStatsId) {
        /*
            Update sixes scored for a given battingStats row.
        */
        if (connection != null) {
            PreparedStatement statement;
            String SqlQueryToUpdateNumberOfSixesPlayed = "UPDATE BattingStats SET Sixes = Sixes+? Where id = ?";
            try {
                statement = connection.prepareStatement(SqlQueryToUpdateNumberOfSixesPlayed);
                statement.setInt(1, 1);
                statement.setInt(2, battingStatsId);
                statement.executeUpdate();

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BattingStats.updateSixesScored");
        }
    }

    public void updateStrikeRate(int battingStatsId, int runsScored, int ballsPlayed) {
        /*
            Update strike rate for a given battingStats row.
        */
        if (connection != null) {
            PreparedStatement statement;
            double strikeRate = (runsScored * 100.0) / (ballsPlayed);
            String SqlQueryToUpdateStrikeRateOfPlayer = "UPDATE BattingStats SET StrikeRate = ? Where id = ?";
            try {
                statement = connection.prepareStatement(SqlQueryToUpdateStrikeRateOfPlayer);
                statement.setDouble(1, strikeRate);
                statement.setInt(2, battingStatsId);
                statement.executeUpdate();

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BattingStats.updateStrikeRate");
        }
    }

    public org.project.model.stats.BattingStats getBattingStats(int matchId, int teamId, int playerId) {
        /*
            Return batting stats.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlQueryToFetchBattingStatsOfAPlayer
                        = "SELECT * FROM BattingStats WHERE player_id = ? AND team_id = ? AND match_id = ?";
                statement = connection.prepareStatement(sqlQueryToFetchBattingStatsOfAPlayer);
                statement.setInt(1, playerId);
                statement.setInt(2, teamId);
                statement.setInt(3, matchId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    GetBattingStatsFromDatabase getBattingStatsFromDatabase = new GetBattingStatsFromDatabase();
                    return getBattingStatsFromDatabase.createBattingStats(resultSet);
                } else {
                    return null;
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.BattingStats.getBattingStats");
        }
        return null;
    }

    public ArrayList<ScoreCardForPlayer> getBattingScoreCardOfAnInning(int matchId, int teamId, Connection connection) {
        /*
            Return batting scorecard for a given match
        */
        ArrayList<ScoreCardForPlayer> battingStats = new ArrayList<ScoreCardForPlayer>();
        ResultSet resultSet;
        if (connection != null) {
            PreparedStatement statement;
            String sqlCommandToGetBattingScoreCardOfAnInning
                    = "SELECT * FROM BattingStats WHERE team_id = ? AND match_id = ?";
            try {
                statement = connection.prepareStatement(sqlCommandToGetBattingScoreCardOfAnInning);
                statement.setInt(1, teamId);
                statement.setInt(2, matchId);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    do {
                        GetBattingStatsFromDatabase getBattingStatsFromDatabase = new GetBattingStatsFromDatabase();
                        int playerId = resultSet.getInt("player_id");
                        String playerName = playerService.getPlayerName(playerId);
                        ScoreCardForPlayer scoreCardForPlayer = new ScoreCardForPlayer(playerName,
                                getBattingStatsFromDatabase.createBattingStats(resultSet));
                        battingStats.add(scoreCardForPlayer);
                    } while (resultSet.next());
                    return battingStats;
                } else {
                    return null;
                }
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        } else {
            LOGGER.error("Connection not established in org.repo.GetBattingScoreCardOfAnInning.");
        }
        return battingStats;
    }
}
