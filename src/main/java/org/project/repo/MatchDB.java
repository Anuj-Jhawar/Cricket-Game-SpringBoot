package org.project.repo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MatchDB {
    Connection connection;

    public MatchDB() {
        this.connection = JdbcConnection.getConnection();
    }

    public void addMatch(String tournamentName, String team1Name, String team2Name, int battingTeamIndex) {
        /*
            Add match in the database.
        */
        TeamDB teamDB = new TeamDB(team1Name);
        TeamDB teamDB1 = new TeamDB(team2Name);
        int team1Id;
        int team2Id;
        if (battingTeamIndex == 1) {
            team1Id = teamDB.getTeamId();
            team2Id = teamDB1.getTeamId();
        } else {
            team1Id = teamDB1.getTeamId();
            team2Id = teamDB.getTeamId();
        }
        TournamentDB tournamentDB = new TournamentDB(tournamentName);
        System.out.println(tournamentName);
        int tournamentId = tournamentDB.getTournamentId();
        if (connection != null) {
            PreparedStatement statement;
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            try {
                String sqlCommandToInsertMatchInMatchTable = "INSERT INTO Matches(team1_id, team2_id, tournament_id, result, Date) VALUES( ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(sqlCommandToInsertMatchInMatchTable);
                statement.setInt(1, team1Id);
                statement.setInt(2, team2Id);
                statement.setInt(3, tournamentId);
                statement.setInt(4, 0);
                statement.setDate(5, date);
                try {
                    statement.executeUpdate();
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Query not completed in org.repo.MatchDb.addMatch.");
                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Statement not created in org.repo.MatchDb.addMatch");
            }
        } else {
            System.out.println("Connection not established in org.repo.MatchDb.addMatch");
        }
    }

    public int getMatchId(String tournamentName, String team1Name, String team2Name, int battingTeamIndex) {
        /*
            Return match id.
        */
        TeamDB teamDB = new TeamDB(team1Name);
        TeamDB teamDB1 = new TeamDB(team2Name);
        int team1Id, team2Id;
        if (battingTeamIndex == 1) {
            team1Id = teamDB.getTeamId();
            team2Id = teamDB1.getTeamId();
        } else {
            team1Id = teamDB1.getTeamId();
            team2Id = teamDB.getTeamId();
        }
        TournamentDB tournamentDB = new TournamentDB(tournamentName);
        int tournamentId = tournamentDB.getTournamentId();
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToGetMatchId = "SELECT * FROM Matches WHERE team1_id = ? AND team2_id = ? AND tournament_id = ?";
                try {
                    statement = connection.prepareStatement(sqlCommandToGetMatchId);
                    statement.setInt(1, team1Id);
                    statement.setInt(2, team2Id);
                    statement.setInt(3, tournamentId);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next())
                        return resultSet.getInt("id");
                    else return 0;
                } catch (Exception e) {
                    System.out.println("Query not completed in org.repo.MatchDb.getMatchId");
                }
            } catch (Exception e) {
                System.out.println("Statement not created in org.repo.MatchDb.getMatchId");
            }
        } else {
            System.out.println("Connection not established in org.repo.MatchDb.getMatchId");
        }
        return 0;
    }

    public int getMatchIdByDate(int tournamentId, int team1Id, int team2Id, Date date) {
        /*
            Return match id after finding it by date.
        */
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToGetMatchId = "SELECT * FROM Matches WHERE (team1_id = ? AND team2_id = ? AND tournament_id = ? AND date = ?) OR (team1_id = ? AND team2_id = ? AND tournament_id = ? AND date = ?)";
                try {
                    statement = connection.prepareStatement(sqlCommandToGetMatchId);
                    statement.setInt(1, team1Id);
                    statement.setInt(2, team2Id);
                    statement.setInt(3, tournamentId);
                    statement.setDate(4, date);
                    statement.setInt(5, team2Id);
                    statement.setInt(6, team1Id);
                    statement.setInt(7, tournamentId);
                    statement.setDate(8, date);
                    ResultSet resultSet = statement.executeQuery();
                    //System.out.println(team1Id + " " + team2Id + " " + tournamentId + " " + date);
                    if (resultSet.next())
                        return resultSet.getInt("id");
                    else return 0;
                } catch (Exception e) {
                    System.out.println("Query not completed in org.repo.MatchDb.getMatchIdByDate");
                }

            } catch (Exception e) {
                System.out.println("Statement not created in org.repo.MatchDb.getMatchIdByDate");
            }
        } else {
            System.out.println("Connection not established in org.repo.MatchDb.getMatchIdByDate");
        }
        return 0;
    }

    public int findBattingFirstTeam(int matchId) {
        /*
            Return batting first team.
        */
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToGetBattingFirstTeamId = "SELECT team1_id FROM Matches WHERE id = ?";
                try {
                    statement = connection.prepareStatement(sqlCommandToGetBattingFirstTeamId);
                    statement.setInt(1, matchId);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next())
                        return resultSet.getInt("team1_id");
                    else
                        return 0;
                } catch (Exception e) {
                    System.out.println("Statement not created in org.repo.MatchDb.FindBattingFirstTeamId.");
                }

            } catch (Exception e) {
                System.out.println("Query not executed in org.repo.MatchDb.FindBattingFirstTeamId");
            }
        } else {
            System.out.println("Connection not established in org.repo.MatchDb.FindBattingFirstTeamId.");
        }
        return 0;
    }

    public void updateResult(int teamNo, int matchId) {
        /*
            Update result of the match.
        */
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToUpdateResult = "UPDATE Matches SET result = ? Where id = ?";
                statement = connection.prepareStatement(sqlCommandToUpdateResult);
                statement.setInt(1, teamNo);
                statement.setInt(2, matchId);
                statement.executeUpdate();
            } catch (Exception e) {
                System.out.println("Query not executed in org.repo.MatchDb.updateResult");
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Connection not established in org.repo.MatchDb.updateResult.");
        }
    }
}
