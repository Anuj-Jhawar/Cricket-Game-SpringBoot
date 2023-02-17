package org.repo.databasequery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FindPlayerName {
    private int playerId;

    public FindPlayerName(int playerId) {
        this.playerId = playerId;
    }

    public String find(Connection connection) {
        if (connection != null) {
            PreparedStatement statement;
            String sqlCommandToGetPlayerName = "SELECT Name from Players WHERE id = ?";
            try {
                statement = connection.prepareStatement(sqlCommandToGetPlayerName);
                statement.setInt(1, playerId);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return resultSet.getString("Name");
                } else
                    return "";
            } catch (Exception e) {
                System.out.println("Statement not created org.service.databasequery.FindPlayerNam.");
            }
        } else {
            System.out.println("Connection not established in org.repo.databasequery.FindPlayerName");
        }
        return "";
    }
}
