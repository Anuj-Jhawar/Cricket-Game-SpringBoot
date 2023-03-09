package org.project.service;

import org.project.dto.PlayerStatsDTO;
import org.project.model.Match;
import org.project.model.stats.Stats;

import java.sql.Date;

public interface PlayerService {

    public int getPlayerId(String playerName);

    public String getPlayerName(int playerID);

    public void addPlayerToPlayerTable(Match match);

    public void addPlayer(String playerName, String type, int age);

    public Stats[] get(PlayerStatsDTO playerStatsDTO);

    public Stats[] findStats(int tournamentId, int team1Id, int team2Id, Date date, int playerId);
}
