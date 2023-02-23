package org.repo;

import org.service.GetBattingStatsFromDatabase;
import org.model.ScoreCardForPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GetBattingScoreCardOfAnInning {
    int teamId;
    int matchId;

    public GetBattingScoreCardOfAnInning(int teamId, int matchId) {
        this.teamId = teamId;
        this.matchId = matchId;
    }

    public ArrayList<ScoreCardForPlayer> getBattingScoreCardOfAnInning(Connection connection) {
        ArrayList<ScoreCardForPlayer> battingStats = new ArrayList<ScoreCardForPlayer>();
        ResultSet resultSet;
        if (connection != null) {
            PreparedStatement statement;
            String sqlCommandToGetBattingScoreCardOfAnInning = "SELECT * FROM BattingStats WHERE team_id = ? AND match_id = ?";
            try {
                statement = connection.prepareStatement(sqlCommandToGetBattingScoreCardOfAnInning);
                statement.setInt(1, teamId);
                statement.setInt(2, matchId);
                resultSet = statement.executeQuery();
                //System.out.println(teamId + " " + matchId + " " + resultSet.getFetchSize());
                if (resultSet.next()) {
                    do {
                        GetBattingStatsFromDatabase getBattingStatsFromDatabase = new GetBattingStatsFromDatabase();
                        int playerId = resultSet.getInt("player_id");
                        PlayerDB playerDB = new PlayerDB();
                        String playerName = playerDB.getPlayerName(playerId);
                        ScoreCardForPlayer scoreCardForPlayer = new ScoreCardForPlayer(playerName, getBattingStatsFromDatabase.createBattingStats(resultSet));
                        battingStats.add(scoreCardForPlayer);
                    } while (resultSet.next());
                    return battingStats;
                } else
                    return null;
            } catch (Exception e) {
                System.out.println("Statement not created in org.repo.GetBattingScoreCardOfAnInning.");
            }
        } else {
            System.out.println("Connection not established in org.repo.GetBattingScoreCardOfAnInning.");
        }
        return battingStats;
    }
}
