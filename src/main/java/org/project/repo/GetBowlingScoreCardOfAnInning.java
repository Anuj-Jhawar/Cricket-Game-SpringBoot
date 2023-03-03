package org.project.repo;

import org.project.model.ScoreCardForPlayer;
import org.project.utilities.GetBowlingStatsFromDatabase;
import org.project.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Repository
public class GetBowlingScoreCardOfAnInning {
    @Autowired
    PlayerService playerService;

    public ArrayList<ScoreCardForPlayer> getBowlingScoreCardOfAnInning(int matchId, int teamId,Connection connection) {
        /*
            Return bowling scorecard for a given match
        */
        ArrayList<ScoreCardForPlayer> bowlingStats = new ArrayList<ScoreCardForPlayer>();
        ResultSet resultSet;
        if (connection != null) {
            PreparedStatement statement;
            String sqlCommandToGetBowlingScoreCardOfAnInning
                    = "SELECT * FROM BowlingStats WHERE team_id = ? AND match_id = ?";
            try {
                statement = connection.prepareStatement(sqlCommandToGetBowlingScoreCardOfAnInning);
                statement.setInt(1, teamId);
                statement.setInt(2, matchId);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    do {
                        GetBowlingStatsFromDatabase getBowlingStatsFromDatabase = new GetBowlingStatsFromDatabase();
                        int playerId = resultSet.getInt("player_id");
                        String playerName = playerService.getPlayerName(playerId);
                        ScoreCardForPlayer scoreCardForPlayer = new ScoreCardForPlayer(playerName,
                                getBowlingStatsFromDatabase.createBowlingStats(resultSet));
                        bowlingStats.add(scoreCardForPlayer);
                    } while (resultSet.next());
                    return bowlingStats;
                } else {
                    return null;
                }
            } catch (Exception e) {
                System.out.println("Statement not created in org.repo.GetBowlingScoreCardOfAnInning.");
            }
        } else {
            System.out.println("Connection not established in org.repo.GetBowlingScoreCardOfAnInning.");
        }
        return bowlingStats;
    }

}
