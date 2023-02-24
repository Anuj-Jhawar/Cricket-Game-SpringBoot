package org.project.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlayerDB {

    Connection connection;

    public PlayerDB() {
        this.connection = JdbcConnection.getConnection();
    }

    public void addPlayer(String playerName, int age) {
        /*
            Add player details to database.
        */
        if (this.getPlayerId(playerName) != 0) {
            return;
        }
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToInsertTeamInTeamTable = "INSERT INTO Players(Name,Age) VALUES (?,?)";
                statement = connection.prepareStatement(sqlCommandToInsertTeamInTeamTable);
                statement.setString(1, playerName);
                statement.setInt(2, 1);
                try {
                    statement.executeUpdate();
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Query not completed in org.repo.PlayerDb.addPlayer");
                }

            } catch (Exception e) {
                System.out.println("Statement not created in org.repo.PlayerDb.addPlayer");
            }
        } else {
            System.out.println("Connection not established in org.repo.PlayerDb.addPlayer");
        }
    }

    public int getPlayerId(String playerName) {
        /*
            Return player id.
        */
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToGetPlayerId = "SELECT * FROM Players WHERE Name = ?";
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
                    System.out.println(e + "FindPlayer");
                    System.out.println("Query not completed in org.repo.PlayerDb.getPlayerId");
                }

            } catch (Exception e) {
                System.out.println("Statement not created in org.repo.PlayerDb.getPlayerId");
            }
        } else {
            System.out.println("Connection not established in org.repo.PlayerDb.getPlayerId");
        }
        return 1;
    }

    public String getPlayerName(int playerId) {
        /*
            Return player name.
        */
        if (connection != null) {
            PreparedStatement statement;
            String sqlCommandToGetPlayerName = "SELECT Name from Players WHERE id = ?";
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
                System.out.println("Statement not created org.repo.PlayerDb.getPlayerName.");
            }
        } else {
            System.out.println("Connection not established in org.repo.PlayerDb.getPlayerName.");
        }
        return "";
    }
}
