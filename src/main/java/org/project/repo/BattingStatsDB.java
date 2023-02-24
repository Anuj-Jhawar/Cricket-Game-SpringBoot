package org.project.repo;

import org.project.model.stats.BattingStats;
import org.project.service.GetBattingStatsFromDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BattingStatsDB {

    int matchId;
    int teamId;
    int playerId;
    Connection connection;

    public BattingStatsDB(int matchId, int teamId, int playerId) {
        this.matchId = matchId;
        this.teamId = teamId;
        this.playerId = playerId;
        this.connection = JdbcConnection.getConnection();
    }

    public void addBattingStats(BattingStats battingStats) {
        /*
            Adding batting stats row for a given player for a particular match.
        */
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToInsertBattingStatsInBattingStatsTable
                        = "INSERT INTO BattingStats (player_id, team_id, match_id, RunsScored, BallsPlayed,Fours,Sixes,NotOut,StrikeRate) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                    System.out.println(e.getMessage());
                    System.out.println("Query not completed in org.repo.BattingStats.addBattingStats");
                }
            } catch (Exception e) {
                System.out.println("Statement not created in org.repo.BattingStats.addBattingStats");
            }
        } else {
            System.out.println("Connection not established in org.repo.BattingStats.addBattingStats");
        }
    }

    public int getBattingStatsId() {
        /*
            Getting batting stats row of a given player for a particular match.
        */
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
                    System.out.println("Query not completed in org.repo.BattingStats.getBattingStatsId");
                }
            } catch (Exception e) {
                System.out.println("Statement not created in org.repo.BattingStats.getBattingStatsId");
            }
        } else {
            System.out.println("Connection not established in org.repo.BattingStats.getBattingStatsId");
        }
        return 1;
    }

    public void updateBattingStats(int outComeOfTheBall, int runsScored, int ballsPlayed) {
        /*
            Adding batting stats row for a given player for a particular match.
        */
        int battingStatsId = this.getBattingStatsId();
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
                System.out.println("Statement not prepared in org.repo.BattingStats.updateBallsPlayed");
                System.out.println(e);
            }
        } else {
            System.out.println("Connection not established in org.repo.BattingStats.updateBallsPlayed");
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
                System.out.println("Statement not prepared in org.repo.BattingStats.updateFoursScored");
                System.out.println(e);
            }
        } else {
            System.out.println("Connection not established in org.repo.BattingStats.updateFoursScored");
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
                System.out.println("Statement not prepared in org.repo.BattingStats.updateNotOut");
                System.out.println(e);
            }
        } else {
            System.out.println("Connection not established in org.repo.BattingStats.updateNotOut");
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
                System.out.println("Statement not prepared in org.repo.BattingStats.updateRunsScored");
                System.out.println(e);
            }
        } else {
            System.out.println("Connection not established in org.repo.BattingStats.updateRunsScored");
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
                System.out.println("Statement not prepared in org.repo.BattingStats.updateSixesScored");
                System.out.println(e);
            }
        } else {
            System.out.println("Connection not established in org.repo.BattingStats.updateSixesScored");
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
                System.out.println("Statement not prepared in org.repo.BattingStats.updateStrikeRate");
                System.out.println(e);
            }
        } else {
            System.out.println("Connection not established in org.repo.BattingStats.updateStrikeRate");
        }
    }

    public BattingStats getBattingStats() {
        /*
            Return batting stats.
        */
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
                System.out.println(e);
                System.out.println("Not able to fetch the batting stats from database");
            }
        } else {
            System.out.println("Connection not established in org.repo.BattingStats.getBattingStats");
        }
        return null;
    }
}
