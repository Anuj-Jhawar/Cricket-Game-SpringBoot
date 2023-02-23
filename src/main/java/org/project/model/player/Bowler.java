package org.project.model.player;

import org.project.service.InputService;

public class Bowler extends Player{
    public void setName(String Name)
    {
        this.name = Name;
    }
    public Bowler() {
        InputService inputService = new InputService();
        inputService.playerNameInput(this);
//        InputInterface TakePlayerNameInput = new PlayerNameInput(this);
//        TakePlayerNameInput.collectInput();
    }
}
