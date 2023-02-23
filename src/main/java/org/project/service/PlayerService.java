package org.project.service;

import org.project.repo.PlayerDB;

public class PlayerService {
    public int getPlayerId(String playerName){
        PlayerDB playerDB = new PlayerDB();
        return playerDB.getPlayerId(playerName);
    }
}
