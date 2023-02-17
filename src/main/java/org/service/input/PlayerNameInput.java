package org.service.input;


import org.service.player.Player;

import java.util.Scanner;

public class PlayerNameInput implements InputInterface{
    Player player;
    public PlayerNameInput(Player Player){
        this.player = Player;
    }
    public String collectInput(){
        /*
            Collect the input for player name.
        */
        Scanner scn = new Scanner(System.in);
        Player.incrementPlayerCount();
        int count = Player.getPlayerCount();
        if(count>11&&count%11!=0)
            count = count%11;
        else if(count%11==0)
            count = 11;
        System.out.println("Please Enter Player " + (count) + " Name: ");
        String PlayerName = scn.nextLine();
        player.setName(PlayerName);
        return PlayerName;
    }
}
