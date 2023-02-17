package org.repo.databaseadd;



import org.repo.databasequery.FindTeamId;
import org.repo.databasequery.FindTournamentId;
import org.service.cricketgame.CricketGame;
import org.repo.jdbc.JdbcConnection;

import java.sql.*;

public class AddMatch implements AddToTable {
    private CricketGame game;
    public AddMatch(CricketGame game){
        this.game = game;
    }
    @Override
    public void add() {
        /*
            Add Match row for the current match.
        */
        JdbcConnection jdbc = new JdbcConnection();
        Connection connection = jdbc.getConnection();
        FindTeamId findTeamId = new FindTeamId();
        int team1Id;
        int team2Id;
        //int winningTeamId = findTeamId.find(game.getWinner(),connection);
        if(game.getBattingTeamIndex()==1){
            team1Id = findTeamId.find(game.getTeam1().getTeamName(),connection);
            team2Id = findTeamId.find(game.getTeam2().getTeamName(),connection);
        }
        else {
            team1Id = findTeamId.find(game.getTeam2().getTeamName(),connection);
            team2Id = findTeamId.find(game.getTeam1().getTeamName(),connection);
        }
        FindTournamentId findTournamentId = new FindTournamentId();
        int tournamentId = findTournamentId.find(game.getTournamentName(), connection);
        if(connection!=null){
            PreparedStatement statement;
            java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
            try{
                String sqlCommandToInsertMatchInMatchTable = "INSERT INTO Matches(team1_id, team2_id, tournament_id, result, Date) VALUES( ?, ?, ?, ?, ?)";
                statement = connection.prepareStatement(sqlCommandToInsertMatchInMatchTable);
                statement.setInt(1, team1Id);
                statement.setInt(2, team2Id);
                statement.setInt(3, tournamentId);
                statement.setInt(4, 1);
                statement.setDate(5, date);
                try {
                    statement.executeUpdate();
                }
                catch (Exception e){
                    System.out.println(e);
                    System.out.println("Query not completed in databaseadd.AddMatchToMatchTable.");
                }
            }
            catch (Exception e){
                System.out.println(e);
                System.out.println("Statement not created in databaseadd.AddMatchToMatchTable.");
            }
            finally {
                try{
                    //connection.close();
                }
                catch (Exception e){
                    System.out.println("Connection not closed in databaseadd.AddMatchToMatchTable.");
                }
            }
        }
        else{
            System.out.println("Connection not established in databaseadd.AddMatchToMatchTable.");
        }
    }
}
