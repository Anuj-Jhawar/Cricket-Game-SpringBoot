package org.project.repo;

import org.project.model.ScoreCardForPlayer;
import org.project.utilities.GetBattingStatsFromDatabase;
import org.project.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Repository
public class BattingScoreCardRepository {

    @Autowired
    private PlayerService playerService;
    private static final Logger LOGGER = LoggerFactory.getLogger(BattingScoreCardRepository.class);

    public ArrayList<ScoreCardForPlayer> getBattingScoreCardOfAnInning(int matchId,int teamId,Connection connection) {
        /*
            Return batting scorecard for a given match
        */
        ArrayList<ScoreCardForPlayer> battingStats = new ArrayList<ScoreCardForPlayer>();
        ResultSet resultSet;
        if (connection != null) {
            PreparedStatement statement;
            String sqlCommandToGetBattingScoreCardOfAnInning
                    = "SELECT * FROM BattingStats WHERE team_id = ? AND match_id = ?";
            try {
                statement = connection.prepareStatement(sqlCommandToGetBattingScoreCardOfAnInning);
                statement.setInt(1, teamId);
                statement.setInt(2, matchId);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    do {
                        GetBattingStatsFromDatabase getBattingStatsFromDatabase = new GetBattingStatsFromDatabase();
                        int playerId = resultSet.getInt("player_id");
                        String playerName = playerService.getPlayerName(playerId);
                        ScoreCardForPlayer scoreCardForPlayer = new ScoreCardForPlayer(playerName,
                                getBattingStatsFromDatabase.createBattingStats(resultSet));
                        battingStats.add(scoreCardForPlayer);
                    } while (resultSet.next());
                    return battingStats;
                } else {
                    return null;
                }
            } catch (Exception e) {
                LOGGER.info(e.getMessage());
            }
        } else {
            LOGGER.info("Connection not established in org.repo.GetBattingScoreCardOfAnInning.");
        }
        return battingStats;
    }
}
