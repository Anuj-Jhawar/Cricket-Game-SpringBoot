package org.project.service.statsbuilder;

import org.project.model.player.Player;
import org.project.repo.BattingStatsDB;
import org.project.repo.MatchDB;
import org.project.repo.PlayerDB;
import org.project.repo.TeamDB;
import org.project.service.PlayerService;
import org.project.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

public class BattingStatsBuilder {
    @Autowired
    static TeamService teamService;
    @Autowired
    static PlayerService playerService;
//    public static BattingStatsDB getBattingStatsObject(String tournamentName, String team1Name, String team2Name,
//                                                       Player player, int battingIndex, String teamName) {
//        /*
//            Create a BattingStatsDB object and return it.
//        */
//        MatchDB matchDB = new MatchDB();
//        int matchId = matchDB.getMatchId(tournamentName, team1Name, team2Name, battingIndex);
//        int teamId = teamService.getTeamId(teamName);
//        int playerId = playerService.getPlayerId(player.getName());
//        return new BattingStatsDB(matchId, teamId, playerId);
//    }
}
