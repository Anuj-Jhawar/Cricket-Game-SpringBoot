package org.service.input;



import org.service.cricketgame.Team;

import java.util.Scanner;

public class TeamNameInput implements InputInterface{
    public String collectInput(){
        /*
            Collect the input for team name.
        */
        Scanner scn = new Scanner(System.in);
        System.out.println("Please Enter Team " + (Team.getTeamCount()+1) + " Name: ");
        Team.incrementTeamCount();
        String TeamName = scn.nextLine();
        return TeamName;
    }
}
