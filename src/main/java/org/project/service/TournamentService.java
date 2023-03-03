package org.project.service;

import org.project.repo.Tournaments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentService {
    @Autowired
    Tournaments tournaments;

    public String start(String tournamentName) {
        /*
            Add tournament to database if not available.
        */
        org.project.model.Tournament tournament = new org.project.model.Tournament();
        tournament.setTournamentName(tournamentName);
        tournament.setTournamentName(this.tournaments.addTournament(tournamentName));
        return "Tournament Created";
    }
    public int getId(String tournamentName){
        return tournaments.getTournamentId(tournamentName);
    }
}
