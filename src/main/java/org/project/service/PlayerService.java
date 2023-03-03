package org.project.service;

import org.project.model.CricketGame;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.repo.PlayerDB;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    public int getPlayerId(String playerName) {
        /*
            Return player id from database.
        */
        PlayerDB playerDB = new PlayerDB();
        return playerDB.getPlayerId(playerName);
    }
    public String getPlayerName(int playerID){
        PlayerDB playerDB = new PlayerDB();
        return playerDB.getPlayerName(playerID);
    }
    public void addPlayer(String playerName,int age){
        PlayerDB playerDB = new PlayerDB();
        playerDB.addPlayer(playerName,age);
    }
    public void addPlayerToPlayerTable(CricketGame game) {
        /*
            Add players to database.
        */
        for (int i = 0; i < 2; i++) {
            Team team = i == 0 ? game.getTeam1() : game.getTeam2();
            Player[] players = team.getPlayers();
            for (int j = 0; j < 11; j++) {
                this.addPlayer(players[j].getName(),1);
            }
        }
    }
}
