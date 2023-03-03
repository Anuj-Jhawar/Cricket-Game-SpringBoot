package org.project.service;

import org.project.model.player.Player;
import org.project.model.stats.BattingStats;
import org.project.repo.BattingStatsDB;
import org.project.repo.MatchDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattingStatsService {
    @Autowired
    TeamService teamService;
    @Autowired
    PlayerService playerService;
    @Autowired
    CricketGameService cricketGameService;
    @Autowired
    BattingStatsDB battingStatsDB;
    public void addBattingStats(BattingStats battingStats,int matchId,int teamId,int playerId){
        battingStatsDB.addBattingStats(battingStats,matchId,teamId,playerId);
    }
    public int getBattingStatsId(int matchId,int teamId,int playerId){
        return battingStatsDB.getBattingStatsId( matchId,teamId,playerId);
    }
    public void updateBattingStats(int matchId,int teamId,int playerId,int outComeOfTheBall, int runsScored, int ballsPlayed){
        battingStatsDB.updateBattingStats(matchId,teamId,playerId,outComeOfTheBall,runsScored,ballsPlayed);
    }
    public void updateBattingStats(String tournamentName, String team1Name, String team2Name,
                                   Player player, int battingIndex, String teamName,int runs,int runsScored,int balls){
        int matchId = cricketGameService.getMatchId(tournamentName, team1Name, team2Name, battingIndex);
        int teamId = teamService.getTeamId(teamName);
        int playerId = playerService.getPlayerId(player.getName());
        this.updateBattingStats(matchId,teamId,playerId,runs,runsScored,balls);
    }
    public BattingStats getBattingStats(int matchId,int teamId,int playerId){
        return battingStatsDB.getBattingStats(matchId,teamId,playerId);
    }
    public BattingStats getBattingStats(String tournamentName, String team1Name, String team2Name,
                                        Player player, int battingIndex, String teamName){
        int matchId = cricketGameService.getMatchId(tournamentName, team1Name, team2Name, battingIndex);
        int teamId = teamService.getTeamId(teamName);
        int playerId = playerService.getPlayerId(player.getName());
        return this.getBattingStats(matchId,teamId,playerId);
    }
}
