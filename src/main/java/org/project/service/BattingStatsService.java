package org.project.service;

import org.project.model.Match;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.model.stats.BattingStats;

public interface BattingStatsService {
    public void updateBattingStats(String tournamentName, String team1Name, String team2Name, Player player,
                                   int battingIndex, String teamName, int runs, int runsScored, int balls);

    public void updateBattingStats(int matchId, int teamId, int playerId, int outComeOfTheBall, int runsScored,
                                   int ballsPlayed);

    public BattingStats getBattingStats(String tournamentName, String team1Name,
                                        String team2Name, Player player, int battingIndex,
                                        String teamName);
    public org.project.model.stats.BattingStats getBattingStats(int matchId, int teamId, int playerId);

    public void addBattingStatsToBattingStatsTable(Match match, Team team) ;

    public void addBattingStats(org.project.model.stats.BattingStats battingStats, int matchId, int teamId,
                                int playerId) ;
}
