package org.service.cricketgamecontroller;

import org.repo.TournamentDB;

import java.util.Scanner;

public class PlayTournament {
    public void main() {
        Tournament tournament = new Tournament();
        String tournamentName;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter tournament name:");
        tournamentName = scanner.nextLine();
        tournament.setTournamentName(tournamentName);
        TournamentDB tournamentDB = new TournamentDB(tournamentName);
        tournament.setTournamentName(tournamentDB.addTournament());
        tournament.playTournament();
    }
}
