package org.service.input;


import org.service.cricketgame.CricketGame;

import java.util.Scanner;

public class FormatInput implements InputInterface{
    CricketGame game;

    public FormatInput(CricketGame game){
        this.game = game;
    }

    public String collectInput(){
        /*
            Collects the input for format of the game.
        */
        Scanner scn = new Scanner(System.in);
        System.out.println("Please Enter the format of the Game from the following:");
        System.out.println("T10, T20, ODI");
        String FormatOfTheGame = scn.nextLine();
        game.setFormatForTheGame(FormatOfTheGame);
        return FormatOfTheGame;
    }

}
