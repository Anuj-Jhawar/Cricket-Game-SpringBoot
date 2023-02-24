package org.project.service;

import org.project.repo.PlayerDB;

public class PlayerService {

    public int getPlayerId(String playerName) {
        /*
            Return player id from database.
        */
        PlayerDB playerDB = new PlayerDB();
        return playerDB.getPlayerId(playerName);
    }
}
