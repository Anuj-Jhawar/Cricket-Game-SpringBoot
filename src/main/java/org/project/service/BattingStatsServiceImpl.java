package org.project.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.model.Match;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.model.stats.BattingStats;
import org.project.repo.BattingStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Data
public class BattingStatsServiceImpl implements BattingStatsService {

    @Autowired
    private TeamServiceImpl teamServiceImpl;
    @Autowired
    private PlayerServiceImpl playerServiceImpl;
    @Autowired
    private MatchServiceImpl matchService;
    @Autowired
    private BattingStatsRepository battingStatsRepository;

    @Override
    public void updateBattingStats(String tournamentName, String team1Name, String team2Name, Player player,
                                   int battingIndex, String teamName, int runs, int runsScored, int balls) {
        int matchId = matchService.getMatchId(tournamentName, team1Name, team2Name, battingIndex);
        int teamId = teamServiceImpl.getTeamId(teamName);
        int playerId = playerServiceImpl.getPlayerId(player.getName());
        this.updateBattingStats(matchId, teamId, playerId, runs, runsScored, balls);
    }

    @Override
    public void updateBattingStats(int matchId, int teamId, int playerId, int outComeOfTheBall, int runsScored,
                                   int ballsPlayed) {
        battingStatsRepository.updateBattingStats(matchId, teamId, playerId, outComeOfTheBall, runsScored, ballsPlayed);
    }

    @Override
    public BattingStats getBattingStats(String tournamentName, String team1Name, String team2Name, Player player,
                                        int battingIndex, String teamName) {
        int matchId = matchService.getMatchId(tournamentName, team1Name, team2Name, battingIndex);
        int teamId = teamServiceImpl.getTeamId(teamName);
        int playerId = playerServiceImpl.getPlayerId(player.getName());
        return this.getBattingStats(matchId, teamId, playerId);
    }

    @Override
    public BattingStats getBattingStats(int matchId, int teamId, int playerId) {
        return battingStatsRepository.getBattingStats(matchId, teamId, playerId);
    }

    @Override
    public void addBattingStatsToBattingStatsTable(Match match, Team team) {
        /*
            Add batting stats for match.
        */
        String teamName = team.getTeamName();
        Player[] players = team.getPlayers();
        for (int i = 0; i < 11; i++) {
            int matchId = matchService.getMatchId(match.getTournamentName(), match.getTeam1().getTeamName(),
                    match.getTeam2().getTeamName(), match.getBattingTeamIndex());
            int teamId = teamServiceImpl.getTeamId(teamName);
            int playerId = playerServiceImpl.getPlayerId(players[i].getName());
            this.addBattingStats(players[i].getBattingStats(), matchId, teamId, playerId);
        }
    }

    @Override
    public void addBattingStats(org.project.model.stats.BattingStats battingStats, int matchId, int teamId,
                                int playerId) {
        this.battingStatsRepository.addBattingStats(battingStats, matchId, teamId, playerId);
    }
}
