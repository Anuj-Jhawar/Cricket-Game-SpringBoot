package org.service.input;



import org.service.cricketgame.CricketGame;

import java.util.Scanner;

public class VenueInput implements InputInterface{
    CricketGame game;

    public VenueInput(CricketGame Game){
        this.game = Game;
    }

    public String collectInput(){
        Scanner scn = new Scanner(System.in);
        System.out.println("Please Enter the Venue of the Game: ");
        String FormatOfTheGame = scn.nextLine();
        game.setVenueForTheGame(FormatOfTheGame);
        return FormatOfTheGame;
    }
}
