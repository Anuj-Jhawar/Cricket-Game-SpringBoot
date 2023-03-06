package org.project.service;

import org.project.model.CricketGame;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.repo.BattingStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BattingStatsService {

    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private CricketGameService cricketGameService;
    @Autowired
    private BattingStatsRepository battingStatsRepository;

    public int getBattingStatsId(int matchId, int teamId, int playerId) {
        return battingStatsRepository.getBattingStatsId(matchId, teamId, playerId);
    }

    public void updateBattingStats(String tournamentName, String team1Name, String team2Name, Player player,
                                   int battingIndex, String teamName, int runs, int runsScored, int balls) {
        int matchId = cricketGameService.getMatchId(tournamentName, team1Name, team2Name, battingIndex);
        int teamId = teamService.getTeamId(teamName);
        int playerId = playerService.getPlayerId(player.getName());
        this.updateBattingStats(matchId, teamId, playerId, runs, runsScored, balls);
    }

    public void updateBattingStats(int matchId, int teamId, int playerId, int outComeOfTheBall, int runsScored,
                                   int ballsPlayed) {
        battingStatsRepository.updateBattingStats(matchId, teamId, playerId, outComeOfTheBall, runsScored, ballsPlayed);
    }

    public org.project.model.stats.BattingStats getBattingStats(String tournamentName, String team1Name,
                                                                String team2Name, Player player, int battingIndex,
                                                                String teamName) {
        int matchId = cricketGameService.getMatchId(tournamentName, team1Name, team2Name, battingIndex);
        int teamId = teamService.getTeamId(teamName);
        int playerId = playerService.getPlayerId(player.getName());
        return this.getBattingStats(matchId, teamId, playerId);
    }

    public org.project.model.stats.BattingStats getBattingStats(int matchId, int teamId, int playerId) {
        return battingStatsRepository.getBattingStats(matchId, teamId, playerId);
    }

    public void addBattingStatsToBattingStatsTable(CricketGame game, Team team) {
        /*
            Add batting stats for match.
        */
        String teamName = team.getTeamName();
        Player[] players = team.getPlayers();
        for (int i = 0; i < 11; i++) {
            int matchId = cricketGameService.getMatchId(game.getTournamentName(), game.getTeam1().getTeamName(),
                    game.getTeam2().getTeamName(), game.getBattingTeamIndex());
            int teamId = teamService.getTeamId(teamName);
            int playerId = playerService.getPlayerId(players[i].getName());
            this.addBattingStats(players[i].getBattingStats(), matchId, teamId, playerId);
        }
    }

    public void addBattingStats(org.project.model.stats.BattingStats battingStats, int matchId, int teamId,
                                int playerId) {
        this.battingStatsRepository.addBattingStats(battingStats, matchId, teamId, playerId);
    }
}
