package org.project.service;

import org.project.model.CricketGame;
import org.project.model.Team;
import org.project.repo.Players;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {
    @Autowired
    Players players;
    public int getPlayerId(String playerName) {
        /*
            Return player id from database.
        */
        return players.getPlayerId(playerName);
    }
    public String getPlayerName(int playerID){
        return players.getPlayerName(playerID);
    }
    public void addPlayer(String playerName,int age){
        players.addPlayer(playerName,age);
    }
    public void addPlayerToPlayerTable(CricketGame game) {
        /*
            Add players to database.
        */
        for (int i = 0; i < 2; i++) {
            Team team = i == 0 ? game.getTeam1() : game.getTeam2();
            org.project.model.player.Player[] players = team.getPlayers();
            for (int j = 0; j < 11; j++) {
                this.addPlayer(players[j].getName(),1);
            }
        }
    }
}
