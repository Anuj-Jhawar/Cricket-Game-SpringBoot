package org.project.repo;

import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
@Repository
public class TeamDB {

    Connection connection;

    public int tableSize() {
        /*
            Return team table size.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
                String sqlCommandToGetSize = "SELECT COUNT(*) FROM Teams";
                ResultSet resultSet = statement.executeQuery(sqlCommandToGetSize);
                if (resultSet.next()) {
                    return resultSet.getInt(1);
                } else {
                    return 0;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return 0;
    }

    public String addTeam(String teamName) {
        /*
            Add team details to data base.
        */
        connection = JdbcConnection.getConnection();
        if (this.getTeamId(teamName) != 0) {
            return "";
        }
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
                int size = this.tableSize();
                String modifiedTeamName = teamName + "_" + size;
                String sqlCommandToInsertTeamInTeamTable = "INSERT INTO Teams (Name) VALUES ('" + modifiedTeamName +
                                                           "')";
                try {
                    statement.executeUpdate(sqlCommandToInsertTeamInTeamTable);
                    return modifiedTeamName;
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Query not completed in org.repo.TeamDB.addTeam");
                }
            } catch (Exception e) {
                System.out.println("Statement not created in org.repo.TeamDB.addTeam");
            }
        } else {
            System.out.println("Connection not established in org.repo.TeamDB.addTeam");
        }
        return "";
    }

    public int getTeamId(String teamName) {
        /*
            Return team id.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
                String sqlCommandToGetTeamId = "SELECT * FROM Teams WHERE Name = '" + teamName + "'";
                try {
                    ResultSet resultSet = statement.executeQuery(sqlCommandToGetTeamId);
                    if (resultSet.next()) {
                        return resultSet.getInt("id");
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    System.out.println(e);
                    System.out.println("Query not completed in org.repo.TeamDB.getTeamId");
                }
            } catch (Exception e) {
                System.out.println(e);
                System.out.println("Statement not created in org.repo.TeamDB.getTeamId");
            }
        } else {
            System.out.println("Connection not established in org.repo.TeamDB.getTeamId");
        }
        return 1;
    }
}
