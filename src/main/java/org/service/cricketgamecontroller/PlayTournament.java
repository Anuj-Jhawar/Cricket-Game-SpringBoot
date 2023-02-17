package org.service.cricketgamecontroller;

import org.repo.databaseadd.AddTournament;

import java.util.Scanner;

public class PlayTournament {
    public void main() {
        Tournament tournament = new Tournament();
        String tournamentName;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter tournament name:");
        tournamentName = scanner.nextLine();
        tournament.setTournamentName(tournamentName);
        AddTournament addTournamentToTournamentTable = new AddTournament(tournamentName);
        addTournamentToTournamentTable.add();
        tournament.playTournament();
    }
}
