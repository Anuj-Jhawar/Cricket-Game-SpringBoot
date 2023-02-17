package org.repo.databasequery.scorecard.bowlingscorecard;

import org.repo.databasequery.FindPlayerName;
import org.service.scorecardforplayer.ScoreCardForPlayer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class GetBowlingScoreCardOfAnInning {
    private int teamId;
    private int matchId;

    public GetBowlingScoreCardOfAnInning(int teamId, int matchId) {
        this.teamId = teamId;
        this.matchId = matchId;
    }

    public ArrayList<ScoreCardForPlayer> getBowlingScoreCardOfAnInning(Connection connection) {
        ArrayList<ScoreCardForPlayer> bowlingStats = new ArrayList<ScoreCardForPlayer>();
        ResultSet resultSet;
        if (connection != null) {
            PreparedStatement statement;
            String sqlCommandToGetBowlingScoreCardOfAnInning = "SELECT * FROM BowlingStats WHERE team_id = ? AND match_id = ?";
            try {
                statement = connection.prepareStatement(sqlCommandToGetBowlingScoreCardOfAnInning);
                statement.setInt(1, teamId);
                statement.setInt(2, matchId);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    do {
                        GetBowlingStatsFromDatabase getBowlingStatsFromDatabase = new GetBowlingStatsFromDatabase();
                        int playerId = resultSet.getInt("player_id");
                        FindPlayerName findPlayerName = new FindPlayerName(playerId);
                        String playerName = findPlayerName.find(connection);
                        ScoreCardForPlayer scoreCardForPlayer = new ScoreCardForPlayer(playerName, getBowlingStatsFromDatabase.createBowlingStats(resultSet));
                        bowlingStats.add(scoreCardForPlayer);
                    } while (resultSet.next());
                    return bowlingStats;
                } else
                    return null;
            } catch (Exception e) {
                System.out.println("Statement not created in org.service.databasequery.scorecard.battingscorecard.getBowlingScoreCardOfAnInning.");
            }
        } else {
            System.out.println("Connection not established in org.service.databasequery.scorecard.battingscorecard.getBowlingScoreCardOfAnInning.");
        }
        return bowlingStats;
    }

}
