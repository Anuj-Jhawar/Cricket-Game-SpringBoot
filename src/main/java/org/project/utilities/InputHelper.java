package org.project.utilities;

import org.project.model.CricketGame;
import org.project.model.Team;
import org.project.model.player.Player;

import java.util.Scanner;

public class InputHelper {

    public void playerNameInput(Player player) {
        Scanner scn = new Scanner(System.in);
        Player.incrementPlayerCount();
        int count = Player.getPlayerCount();
        if (count > 11 && count % 11 != 0) {
            count = count % 11;
        } else if (count % 11 == 0) {
            count = 11;
        }
        System.out.println("Please Enter Player " + (count) + " Name: ");
        String PlayerName = scn.nextLine();
        player.setName(PlayerName);
    }

    public void formatInput(CricketGame game) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Please Enter the format of the Game from the following:");
        System.out.println("T10, T20, ODI");
        String FormatOfTheGame = scn.nextLine();
        game.setFormatForTheGame(FormatOfTheGame);
    }

    public void venueInput(CricketGame game) {
        Scanner scn = new Scanner(System.in);
        System.out.println("Please Enter the Venue of the Game: ");
        String VenueOfTheGame = scn.nextLine();
        game.setVenueForTheGame(VenueOfTheGame);
    }

    public void playerTypeInput(Team team) {
        PlayerFactory playerFactory = new PlayerFactory();
        Scanner scn = new Scanner(System.in);
        for (int i = 0; i < 11; i++) {
            System.out.println("Please Enter Player " + (i + 1) + " type: ");
            String PlayerType = scn.nextLine();
            team.setPlayers(i, playerFactory.getPlayer(PlayerType));
        }
    }

    public String teamNameInput() {
        Scanner scn = new Scanner(System.in);
        System.out.println("Please Enter Team " + (Team.getTeamCount() + 1) + " Name: ");
        Team.incrementTeamCount();
        String TeamName = scn.nextLine();
        return TeamName;
    }
}
