package org.project.service;

import org.project.model.Match;
import org.project.model.Team;
import org.project.model.player.Bowler;

public interface PlayerService {
    public int getPlayerId(String playerName);

    public String getPlayerName(int playerID);

    public void addPlayerToPlayerTable(Match match);

    public void addPlayer(String playerName,String type, int age);
}
