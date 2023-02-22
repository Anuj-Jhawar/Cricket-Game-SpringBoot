package org.service;

import org.model.player.Batsman;
import org.model.player.Bowler;
import org.model.player.Player;

public class PlayerFactoryService {
    public Player getPlayer(String type) {
        if (type.equals("Batsman")) {
            return new Batsman();
        }
        return new Bowler();
    }
}
