package org.project.repo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
@Slf4j
public class TeamRepository {
    private Connection connection;

    public String addTeam(String teamName) {
        /*
            Add team details to data base.
        */
        connection = JdbcConnection.getConnection();
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
                int size = this.tableSize();
                String modifiedTeamName = teamName + "_" + size;
                String sqlCommandToInsertTeamInTeamTable =
                        "INSERT INTO Teams (Name,is_deleted) VALUES ('" + modifiedTeamName +
                                                           "',0)";
                try {
                    statement.executeUpdate(sqlCommandToInsertTeamInTeamTable);
                    return modifiedTeamName;
                } catch (Exception e) {
                    log.error(e.getMessage());
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("Connection not established in org.repo.TeamDB.addTeam");
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
                String sqlCommandToGetTeamId = "SELECT * FROM Teams WHERE Name = '" + teamName + "' AND is_deleted!=1";
                try {
                    ResultSet resultSet = statement.executeQuery(sqlCommandToGetTeamId);
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
            log.error("Connection not established in org.repo.TeamDB.getTeamId");
        }
        return 1;
    }

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
                log.error(e.getMessage());
            }
        }
        return 0;
    }
}
