package org.project.utilities;

import org.project.model.player.Batsman;
import org.project.model.player.Bowler;
import org.project.model.player.Player;

public class PlayerFactory {

    public Player getPlayer(String type) {
        /*
            Return player depending on the type asked.
        */
        String batsman = "Batsman";
        if (type.equals(batsman)) {
            return new Batsman();
        }
        return new Bowler();
    }
}
