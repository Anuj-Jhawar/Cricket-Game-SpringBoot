package org.project.repo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository
@Slf4j
public class PlayerRepository {

    private Connection connection;

    public void addPlayer(String playerName,String type, int age) {
        /*
            Add player details to database.
        */
        connection = JdbcConnection.getConnection();
        if (this.getPlayerId(playerName) != 0) {
            return;
        }
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToInsertTeamInTeamTable = "INSERT INTO Players(Name,Type,Age,is_deleted) VALUES (?,?,0,0)";
                statement = connection.prepareStatement(sqlCommandToInsertTeamInTeamTable);
                statement.setString(1, playerName);
                statement.setString(2, type);
                try {
                    statement.executeUpdate();
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("Connection not established in org.repo.PlayerDb.addPlayer");
        }
    }

    public int getPlayerId(String playerName) {
        /*
            Return player id.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToGetPlayerId = "SELECT * FROM Players WHERE Name = ? AND is_deleted !=1";
                statement = connection.prepareStatement(sqlCommandToGetPlayerId);
                statement.setString(1, playerName);
                try {
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
            log.error("Connection not established in org.repo.PlayerDb.getPlayerId");
        }
        return 1;
    }

    public String getPlayerName(int playerId) {
        /*
            Return player name.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            PreparedStatement statement;
            String sqlCommandToGetPlayerName = "SELECT Name from Players WHERE id = ? AND is_deleted != 1";
            try {
                statement = connection.prepareStatement(sqlCommandToGetPlayerName);
                statement.setInt(1, playerId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("Name");
                } else {
                    return "";
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("Connection not established in org.repo.PlayerDb.getPlayerName.");
        }
        return "";
    }
}
