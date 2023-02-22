package org.service;

import org.repo.TournamentDB;
import org.model.Tournament;

import java.util.Scanner;

public class TournamentService {
    public String start(){
        Tournament tournament = new Tournament();
        String tournamentName;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter tournament name:");
        tournamentName = scanner.nextLine();
        tournament.setTournamentName(tournamentName);
        TournamentDB tournamentDB = new TournamentDB(tournamentName);
        tournament.setTournamentName(tournamentDB.addTournament());
        tournament.playTournament();
        return "Tournament Started";
    }
}
