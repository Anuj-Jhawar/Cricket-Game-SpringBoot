package org.service.builder;

import org.repo.*;
import org.service.player.Player;

public class BowlingStatsBuilder {
    public static BowlingStatsDB getBowlingStatsObject(String tournamentName, String team1Name, String team2Name, Player player, int battingIndex,String teamName){
        MatchDB matchDB = new MatchDB();
        TeamDB teamDB = new TeamDB(teamName);
        PlayerDB playerDB = new PlayerDB();
        int matchId = matchDB.getMatchId(tournamentName,team1Name,team2Name,battingIndex);
        int teamId = teamDB.getTeamId();
        int playerId = playerDB.getPlayerId(player.getName());
        return new BowlingStatsDB(matchId,teamId,playerId);
    }
}
