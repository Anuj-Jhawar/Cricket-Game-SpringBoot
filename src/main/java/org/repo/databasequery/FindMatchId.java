package org.repo.databasequery;


import org.service.cricketgame.CricketGame;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FindMatchId implements QueryDatabase{
    private CricketGame game;
    public FindMatchId(CricketGame game){
        this.game = game;
    }
    @Override
    public int find(String queryTypeName, Connection connection) {
        /*
            Find and return Match id if present in the Matches table.
        */
        int team1Id;
        int team2Id;
        FindTeamId findTeamId = new FindTeamId();
        FindTournamentId findTournamentId = new FindTournamentId();
        if(game.getBattingTeamIndex()==1){
            team1Id = findTeamId.find(game.getTeam1().getTeamName(),connection);
            team2Id = findTeamId.find(game.getTeam2().getTeamName(),connection);
        }
        else{
            team1Id = findTeamId.find(game.getTeam2().getTeamName(),connection);
            team2Id = findTeamId.find(game.getTeam1().getTeamName(),connection);
        }
        int tournamentId = findTournamentId.find(game.getTournamentName(),connection);
        if(connection!=null){
            PreparedStatement statement;
            try{
                String sqlCommandToGetMatchId = "SELECT * FROM Matches WHERE team1_id = ? AND team2_id = ? AND tournament_id = ?";
                try {
                    statement = connection.prepareStatement(sqlCommandToGetMatchId);
                    statement.setInt(1,team1Id);
                    statement.setInt(2,team2Id);
                    statement.setInt(3,tournamentId);
                    ResultSet resultSet = statement.executeQuery();
                    if(resultSet.next())
                    return resultSet.getInt("id");
                    else return 0;
                }
                catch (Exception e){
                    System.out.println("Query not completed in databasequery.FindMatchId.");
                }

            }
            catch (Exception e){
                System.out.println("Statement not created in databasequery.FindMatchId.");
            }
            finally {
                try{
                    //connection.close();
                }
                catch (Exception e){
                    System.out.println("Connection not closed in databasequery.FindMatchId.");
                }

            }

        }
        else{
            System.out.println("Connection not established in databasequery.FindMatchId.");
        }
        return 1;
    }
}
