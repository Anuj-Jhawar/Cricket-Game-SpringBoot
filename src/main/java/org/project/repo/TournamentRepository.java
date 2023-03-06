package org.project.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

@Repository
public class TournamentRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentRepository.class);
    private Connection connection;

    public TournamentRepository() {
        JdbcConnection.initializeConnection();
        this.connection = JdbcConnection.getConnection();
    }

    public String addTournament(String tournamentName) {
        /*
            Add tournament details to table.
        */
        if (this.getTournamentId(tournamentName) != 0) {
            return "Tournament with this name already exists.";
        }
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
                int size = this.tableSize();
                String modifiedTournamentName = tournamentName + "_" + size;
                String sqlCommandToCreateTournamentTable = "INSERT INTO Tournaments (Name) VALUES ('" +
                                                           modifiedTournamentName + "')";
                try {
                    statement.executeUpdate(sqlCommandToCreateTournamentTable);
                    return modifiedTournamentName;
                } catch (Exception e) {
                    LOGGER.info(e.getMessage());
                }

            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            }
        } else {
            LOGGER.info("Connection not established in org.repo.TournamentDB.addTournament");
        }
        return "";
    }

    public int getTournamentId(String tournamentName) {
        /*
            Return tournament id.
        */
        if (connection != null) {
            PreparedStatement statement;
            try {
                String sqlCommandToGetTournamentId = "SELECT * FROM Tournaments WHERE Name = ?";
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
                    LOGGER.info(e.getMessage());
                }
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            }
        } else {
            LOGGER.info("Connection not established in org.repo.TournamentDB.getTournamentId.");
        }
        return 1;
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
                LOGGER.info(e.getMessage());
            }
        }
        return 0;
    }
}
