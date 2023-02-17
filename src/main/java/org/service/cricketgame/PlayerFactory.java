package org.service.cricketgame;

import org.service.player.Batsman;
import org.service.player.Bowler;
import org.service.player.Player;

public class PlayerFactory {
    public Player getPlayer(String type) {
        if (type.equals("Batsman")) {
            return new Batsman();
        }
        return new Bowler();
    }
}
