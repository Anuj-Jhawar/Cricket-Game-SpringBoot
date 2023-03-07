package org.project.repo;

import lombok.extern.slf4j.Slf4j;
import org.project.service.TeamService;
import org.project.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
@Slf4j
public class MatchRepository {

    private Connection connection;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private TeamService teamService;

    public void addMatch(String tournamentName, String team1Name, String team2Name, int battingTeamIndex) {
        /*
            Add match in the database.
        */
        connection = JdbcConnection.getConnection();
        int team1Id;
        int team2Id;
        if (battingTeamIndex == 1) {
            team1Id = teamService.getTeamId(team1Name);
            team2Id = teamService.getTeamId(team2Name);
        } else {
            team1Id = teamService.getTeamId(team2Name);
            team2Id = teamService.getTeamId(team1Name);
        }
        int tournamentId = tournamentService.getId(tournamentName);
        if (connection != null) {
            PreparedStatement statement;
            Date date = new Date(System.currentTimeMillis());
            try {
                String sqlCommandToInsertMatchInMatchTable
                        = "INSERT INTO Matches(team1_id, team2_id, tournament_id, result, Date,is_deleted) VALUES( ?," +
                          " ?, ?, ?, ?,0)";
                statement = connection.prepareStatement(sqlCommandToInsertMatchInMatchTable);
                statement.setInt(1, team1Id);
                statement.setInt(2, team2Id);
                statement.setInt(3, tournamentId);
                statement.setInt(4, 0);
                statement.setDate(5, date);
                try {
                    statement.executeUpdate();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("Connection not established in org.repo.MatchDb.addMatch");
        }
    }

    public int getMatchId(String tournamentName, String team1Name, String team2Name, int battingTeamIndex) {
        /*
            Return match id.
        */
        connection = JdbcConnection.getConnection();
        int team1Id;
        int team2Id;
        if (battingTeamIndex == 1) {
            team1Id = teamService.getTeamId(team1Name);
            team2Id = teamService.getTeamId(team2Name);
        } else {
            team1Id = teamService.getTeamId(team2Name);
            team2Id = teamService.getTeamId(team1Name);
        }
        int tournamentId = tournamentService.getId(tournamentName);
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToGetMatchId
                        = "SELECT * FROM Matches WHERE team1_id = ? AND team2_id = ? AND tournament_id = ? AND " +
                          "is_deleted != 1";
                try {
                    statement = connection.prepareStatement(sqlCommandToGetMatchId);
                    statement.setInt(1, team1Id);
                    statement.setInt(2, team2Id);
                    statement.setInt(3, tournamentId);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("Connection not established in org.repo.MatchDb.getMatchId");
        }
        return 0;
    }

    public int getMatchIdByDate(int tournamentId, int team1Id, int team2Id, Date date) {
        /*
            Return match id after finding it by date.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToGetMatchId
                        = "SELECT * FROM Matches WHERE (team1_id = ? AND team2_id = ? AND tournament_id = ? AND date " +
                          "= ?) OR (team1_id = ? AND team2_id = ? AND tournament_id = ? AND date = ?) AND is_deleted " +
                          "!= 1";
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
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("Connection not established in org.repo.MatchDb.getMatchIdByDate");
        }
        return 0;
    }

    public int findBattingFirstTeam(int matchId) {
        /*
            Return batting first team.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToGetBattingFirstTeamId = "SELECT team1_id FROM Matches WHERE id = ? AND is_deleted " +
                                                           "!= 1";
                try {
                    statement = connection.prepareStatement(sqlCommandToGetBattingFirstTeamId);
                    statement.setInt(1, matchId);
                    ResultSet resultSet = statement.executeQuery();
                    if (resultSet.next()) {
                        return resultSet.getInt("team1_id");
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("Connection not established in org.repo.MatchDb.FindBattingFirstTeamId.");
        }
        return 0;
    }

    public void updateResult(int teamNo, int matchId) {
        /*
            Update result of the match.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToUpdateResult = "UPDATE Matches SET result = ? Where id = ?";
                statement = connection.prepareStatement(sqlCommandToUpdateResult);
                statement.setInt(1, teamNo);
                statement.setInt(2, matchId);
                statement.executeUpdate();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("Connection not established in org.repo.MatchDb.updateResult.");
        }
    }
}
