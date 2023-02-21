package org.service.builder;

import org.repo.BattingStatsDB;
import org.repo.MatchDB;
import org.repo.PlayerDB;
import org.repo.TeamDB;
import org.service.cricketgame.CricketGame;
import org.service.player.Player;

public class BattingStatsBuilder {
    public static BattingStatsDB getBattingStatsObject(String tournamentName, String team1Name, String team2Name, Player player,int battingIndex,String teamName){
        MatchDB matchDB = new MatchDB();
        TeamDB teamDB = new TeamDB(teamName);
        PlayerDB playerDB = new PlayerDB();
        int matchId = matchDB.getMatchId(tournamentName,team1Name,team2Name,battingIndex);
        int teamId = teamDB.getTeamId();
        int playerId = playerDB.getPlayerId(player.getName());
        return new BattingStatsDB(matchId,teamId,playerId);
    }
}
