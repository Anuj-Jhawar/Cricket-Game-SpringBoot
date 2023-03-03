package org.project.service;

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
}
