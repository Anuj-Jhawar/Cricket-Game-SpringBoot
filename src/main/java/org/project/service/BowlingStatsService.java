package org.project.service;

import org.project.model.Match;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.repo.BowlingStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BowlingStatsService {

    @Autowired
    private TeamService teamService;
    @Autowired
    private PlayerService playerService;
    @Autowired
    private MatchService matchService;
    @Autowired
    private BowlingStatsRepository bowlingStatsRepository;

    public void updateBowlingStats(String tournamentName, String team1Name, String team2Name, Player player,
                                   int battingIndex, String teamName, int outcomeOfTheBall,
                                   org.project.model.stats.BowlingStats bowlingStats) {
        int matchId = matchService.getMatchId(tournamentName, team1Name, team2Name, battingIndex);
        int teamId = teamService.getTeamId(teamName);
        int playerId = playerService.getPlayerId(player.getName());
        this.updateBowlingStats(matchId, teamId, playerId, outcomeOfTheBall, bowlingStats);
    }

    public void updateBowlingStats(int matchId, int teamId, int playerId, int outComeOfTheBall,
                                   org.project.model.stats.BowlingStats bowlingStats) {
        this.bowlingStatsRepository.updateBowlingStats(matchId, teamId, playerId, outComeOfTheBall, bowlingStats);
    }

    public org.project.model.stats.BowlingStats getBowlingStats(String tournamentName, String team1Name,
                                                                String team2Name, Player player, int battingIndex,
                                                                String teamName) {
        int matchId = matchService.getMatchId(tournamentName, team1Name, team2Name, battingIndex);
        int teamId = teamService.getTeamId(teamName);
        int playerId = playerService.getPlayerId(player.getName());
        return this.getBowlingStats(matchId, teamId, playerId);
    }

    public org.project.model.stats.BowlingStats getBowlingStats(int matchId, int teamId, int playerId) {
        return bowlingStatsRepository.getBowlingStats(matchId, teamId, playerId);
    }

    public void addBowlingStatsToBowlingStatsTable(Match match, Team team) {
        /*
            Add bowling stats for match.
        */
        String teamName = team.getTeamName();
        Player[] players = team.getPlayers();
        for (int i = 0; i < 11; i++) {
            int matchId = matchService.getMatchId(match.getTournamentName(), match.getTeam1().getTeamName(),
                    match.getTeam2().getTeamName(), match.getBattingTeamIndex());
            int teamId = teamService.getTeamId(teamName);
            int playerId = playerService.getPlayerId(players[i].getName());
            this.addBowlingStats(players[i].getBowlingStats(), matchId, teamId, playerId);
        }
    }

    public void addBowlingStats(org.project.model.stats.BowlingStats bowlingStats, int matchId, int teamId,
                                int playerId) {
        this.bowlingStatsRepository.addBowlingStats(bowlingStats, matchId, teamId, playerId);
    }
}
