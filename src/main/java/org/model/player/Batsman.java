package org.model.player;


import org.service.InputService;

public class Batsman extends Player {
    public Batsman() {
        InputService inputService = new InputService();
        inputService.playerNameInput(this);
//        InputInterface TakePlayerNameInput = new PlayerNameInput(this);
//        TakePlayerNameInput.collectInput();
    }

    public void setName(String Name)
    {
        this.name = Name;
    }

}
