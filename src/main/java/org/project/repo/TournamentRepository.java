package org.project.repo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
@Slf4j
public class TournamentRepository {

    private Connection connection;

    public TournamentRepository() {
        JdbcConnection.initializeConnection();
        this.connection = JdbcConnection.getConnection();
    }

    public String addTournament(String tournamentName) {
        /*
            Add tournament details to table.
        */
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
                int size = this.tableSize();
                String modifiedTournamentName = tournamentName + "_" + size;
                String sqlCommandToCreateTournamentTable = "INSERT INTO Tournaments (Name,is_deleted) VALUES ('" +
                                                           modifiedTournamentName + "',0)";
                try {
                    statement.executeUpdate(sqlCommandToCreateTournamentTable);
                    return modifiedTournamentName;
                } catch (Exception e) {
                    log.error(e.getMessage());
                }

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("Connection not established in org.repo.TournamentDB.addTournament");
        }
        return "";
    }

    public int tableSize() {
        /*
            Return table size.
        */
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
                String sqlCommandToGetSize = "SELECT COUNT(*) FROM Tournaments";
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

    public int getTournamentId(String tournamentName) {
        /*
            Return tournament id.
        */
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToGetTournamentId = "SELECT * FROM Tournaments WHERE Name = ? AND is_deleted != 1";
                statement = connection.prepareStatement(sqlCommandToGetTournamentId);
                statement.setString(1, tournamentName);
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
            log.error("Connection not established in org.repo.TournamentDB.getTournamentId.");
        }
        return 1;
    }
}
