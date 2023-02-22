package org.service;

import org.repo.PlayerDB;

public class PlayerService {
    public int getPlayerId(String playerName){
        PlayerDB playerDB = new PlayerDB();
        return playerDB.getPlayerId(playerName);
    }
}
