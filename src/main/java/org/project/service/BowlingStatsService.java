package org.project.service;

import org.project.model.Match;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.model.stats.BowlingStats;

public interface BowlingStatsService {

    public void updateBowlingStats(String tournamentName, String team1Name, String team2Name, Player player,
                                   int battingIndex, String teamName, int outcomeOfTheBall,
                                   org.project.model.stats.BowlingStats bowlingStats);

    public void updateBowlingStats(int matchId, int teamId, int playerId, int outComeOfTheBall,
                                   org.project.model.stats.BowlingStats bowlingStats);

    public org.project.model.stats.BowlingStats getBowlingStats(String tournamentName, String team1Name,
                                                                String team2Name, Player player, int battingIndex,
                                                                String teamName);

    public BowlingStats getBowlingStats(int matchId, int teamId, int playerId);

    public void addBowlingStatsToBowlingStatsTable(Match match, Team team);

    public void addBowlingStats(org.project.model.stats.BowlingStats bowlingStats, int matchId, int teamId,
                                int playerId);

}
