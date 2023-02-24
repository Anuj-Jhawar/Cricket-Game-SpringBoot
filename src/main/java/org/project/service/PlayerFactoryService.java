package org.project.service;

import org.project.model.player.Bowler;
import org.project.model.player.Player;
import org.project.model.player.Batsman;

public class PlayerFactoryService {
    public Player getPlayer(String type) {
        String batsman = "Batsman";
        if (type.equals(batsman)) {
            return new Batsman();
        }
        return new Bowler();
    }
}
