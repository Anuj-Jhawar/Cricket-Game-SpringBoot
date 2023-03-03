package org.project.service;

import org.project.model.player.Player;
import org.project.model.stats.BowlingStats;
import org.project.repo.BowlingStatsDB;
import org.project.repo.MatchDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BowlingStatsService {
    @Autowired
    TeamService teamService;
    @Autowired
    PlayerService playerService;
    @Autowired
    CricketGameService cricketGameService;
    @Autowired
    BowlingStatsDB bowlingStatsDB;
    public void addBowlingStats(BowlingStats bowlingStats,int matchId,int teamId,int playerId){
        bowlingStatsDB.addBowlingStats(bowlingStats,matchId,teamId,playerId);
    }
    public int getBowlingStatsId(int matchId,int teamId,int playerId){
        return bowlingStatsDB.getBowlingStatsId(matchId,teamId,playerId);
    }
    public void updateBowlingStats(int matchId,int teamId,int playerId,int outComeOfTheBall, BowlingStats bowlingStats){
        bowlingStatsDB.updateBowlingStats(matchId,teamId,playerId,outComeOfTheBall,bowlingStats);
    }
    public void updateBowlingStats(String tournamentName, String team1Name, String team2Name,
                                   Player player, int battingIndex, String teamName,int outcomeOfTheBall,BowlingStats bowlingStats){
        int matchId = cricketGameService.getMatchId(tournamentName, team1Name, team2Name, battingIndex);
        int teamId = teamService.getTeamId(teamName);
        int playerId = playerService.getPlayerId(player.getName());
        this.updateBowlingStats(matchId,teamId,playerId,outcomeOfTheBall,bowlingStats);
    }
    public BowlingStats getBowlingStats(int matchId,int teamId,int playerId){
        return bowlingStatsDB.getBowlingStats(matchId,teamId,playerId);
    }
    public BowlingStats getBowlingStats(String tournamentName, String team1Name, String team2Name,
                                        Player player, int battingIndex, String teamName){
        int matchId = cricketGameService.getMatchId(tournamentName, team1Name, team2Name, battingIndex);
        int teamId = teamService.getTeamId(teamName);
        int playerId = playerService.getPlayerId(player.getName());
        return this.getBowlingStats(matchId,teamId,playerId);
    }

}
