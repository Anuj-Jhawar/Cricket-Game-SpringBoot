package org.project.service;

import org.project.model.Tournament;
import org.project.repo.TournamentDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {
    @Autowired
    TournamentDB tournamentDB;

    public String start(String tournamentName) {
        /*
            Add tournament to database if not available.
        */
        Tournament tournament = new Tournament();
        tournament.setTournamentName(tournamentName);
        tournament.setTournamentName(tournamentDB.addTournament(tournamentName));
        return "Tournament Created";
    }
    public int getId(String tournamentName){
        return tournamentDB.getTournamentId(tournamentName);
    }
}
