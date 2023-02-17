package org.repo.databasequery.scorecard.battingscorecard;

import org.repo.databasequery.FindPlayerName;
import org.service.scorecardforplayer.ScoreCardForPlayer;

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
                        FindPlayerName findPlayerName = new FindPlayerName(playerId);
                        String playerName = findPlayerName.find(connection);
                        ScoreCardForPlayer scoreCardForPlayer = new ScoreCardForPlayer(playerName, getBattingStatsFromDatabase.createBattingStats(resultSet));
                        battingStats.add(scoreCardForPlayer);
                    } while (resultSet.next());
                    return battingStats;
                } else
                    return null;
            } catch (Exception e) {
                System.out.println("Statement not created in org.service.databasequery.scorecard.battingscorecard.getBattingScoreCardOfAnInning.");
            }
        } else {
            System.out.println("Connection not established in org.service.databasequery.scorecard.battingscorecard.getBattingScoreCardOfAnInning.");
        }
        return battingStats;
    }
}
