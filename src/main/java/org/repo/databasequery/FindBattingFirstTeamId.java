package org.repo.databasequery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FindBattingFirstTeamId {
    public int find(int matchId, Connection connection) {
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
                    System.out.println("Statement not created in org.service.databasequeryFindBattingFirstTeamId.");
                }

            } catch (Exception e) {
                System.out.println("Query not executed in org.service.databasequeryFindBattingFirstTeamId");
            }
        } else {
            System.out.println("Connection not established in org.service.databasequeryFindBattingFirstTeamId.");
        }
        return 0;
    }

}
