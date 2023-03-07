package org.project.service;

import org.project.model.Match;
import org.project.model.Team;
import org.project.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public int getPlayerId(String playerName) {
        /*
            Return player id from database.
        */
        return playerRepository.getPlayerId(playerName);
    }

    public String getPlayerName(int playerID) {
        return playerRepository.getPlayerName(playerID);
    }

    public void addPlayerToPlayerTable(Match match) {
        /*
            Add players to database.
        */
        for (int i = 0; i < 2; i++) {
            Team team = i == 0 ? match.getTeam1() : match.getTeam2();
            org.project.model.player.Player[] players = team.getPlayers();
            for (int j = 0; j < 11; j++) {
                this.addPlayer(players[j].getName(), 1);
            }
        }
    }

    public void addPlayer(String playerName, int age) {
        playerRepository.addPlayer(playerName, age);
    }
}
