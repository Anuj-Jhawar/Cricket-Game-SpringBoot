package org.project.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class TournamentDB {
    String tournamentName;
    Connection connection;

    public TournamentDB(String tournamentName) {
        this.tournamentName = tournamentName;
        JdbcConnection.initializeConnection();
        this.connection = JdbcConnection.getConnection();
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
                System.out.println(e.getMessage());
            }
        }
        return 0;
    }

    public String addTournament() {
        /*
            Add tournament details to table.
        */
        if (this.getTournamentId() != 0)
            return "Tournament with this name already exists.";
        if (connection != null) {
            Statement statement;
            try {
                statement = connection.createStatement();
                int size = this.tableSize();
                String modifiedTournamentName = tournamentName + "_" + size;
                String sqlCommandToCreateTournamentTable = "INSERT INTO Tournaments (Name) VALUES ('" + modifiedTournamentName + "')";
                try {
                    statement.executeUpdate(sqlCommandToCreateTournamentTable);
                    return modifiedTournamentName;
                } catch (Exception e) {
                    System.out.println("Query not completed in org.repo.TournamentDB.addTournament");
                }

            } catch (Exception e) {
                System.out.println("Statement not created in org.repo.TournamentDB.addTournament");
            }
        } else {
            System.out.println("Connection not established in org.repo.TournamentDB.addTournament");
        }
        return "";
    }

    public int getTournamentId() {
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
                    if (resultSet.next())
                        return resultSet.getInt("id");
                    else
                        return 0;
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    System.out.println("Query not completed in org.repo.TournamentDB.getTournamentId.");
                }
            } catch (Exception e) {
                System.out.println("Statement not created in org.repo.TournamentDB.getTournamentId.");
            }
        } else {
            System.out.println("Connection not established in org.repo.TournamentDB.getTournamentId.");
        }
        return 1;
    }
}
