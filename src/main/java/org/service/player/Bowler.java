package org.service.player;


import org.service.cricketgame.UpdatingBowlingStats;
import org.service.input.InputInterface;
import org.service.input.PlayerNameInput;

public class Bowler extends Player implements UpdatingBowlingStats {
    public void setName(String Name)
    {
        this.name = Name;
    }
    public Bowler() {
        InputInterface TakePlayerNameInput = new PlayerNameInput(this);
        TakePlayerNameInput.collectInput();
    }
}
