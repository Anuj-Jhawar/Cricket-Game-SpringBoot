package org.project.service;

import org.project.model.Tournament;
import org.project.repo.TournamentDB;

public class TournamentService {
    public String start(String tournamentName) {
        /*
            Add tournament to database if not available.
        */
        Tournament tournament = new Tournament();
        tournament.setTournamentName(tournamentName);
        TournamentDB tournamentDB = new TournamentDB(tournamentName);
        tournament.setTournamentName(tournamentDB.addTournament());
        return "Tournament Created";
    }
}
