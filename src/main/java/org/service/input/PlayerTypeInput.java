package org.service.input;



import org.service.cricketgame.PlayerFactory;
import org.service.cricketgame.Team;

import java.util.Scanner;

public class PlayerTypeInput implements InputInterface{
    Team team;
    public PlayerTypeInput(Team team){
        this.team = team;
    }
    public String collectInput(){
        /*
            Collect the input for player type.
        */
        PlayerFactory playerFactory = new PlayerFactory();
        Scanner scn = new Scanner(System.in);
        for(int i = 0;i<11;i++){
            System.out.println("Please Enter Player " + (i+1) +" type: ");
            String PlayerType = scn.nextLine();
            team.setPlayers(i,playerFactory.getPlayer(PlayerType));
        }
        return "";
    }
}
