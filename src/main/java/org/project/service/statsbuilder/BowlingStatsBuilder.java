package org.project.service.statsbuilder;

import org.project.model.player.Player;
import org.project.repo.BowlingStatsDB;
import org.project.repo.MatchDB;
import org.project.repo.PlayerDB;
import org.project.repo.TeamDB;

public class BowlingStatsBuilder {
    public static BowlingStatsDB getBowlingStatsObject(String tournamentName, String team1Name, String team2Name, Player player, int battingIndex, String teamName) {
        /*
            Create a BowlingStatsDB object and return it.
        */
        MatchDB matchDB = new MatchDB();
        TeamDB teamDB = new TeamDB(teamName);
        PlayerDB playerDB = new PlayerDB();
        int matchId = matchDB.getMatchId(tournamentName, team1Name, team2Name, battingIndex);
        int teamId = teamDB.getTeamId();
        int playerId = playerDB.getPlayerId(player.getName());
        return new BowlingStatsDB(matchId, teamId, playerId);
    }
}
