package org.project.service;

import lombok.Data;
import org.project.repo.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentRepository tournamentRepository;

    @Override
    public String start(String tournamentName) {
        /*
            Add tournament to database if not available.
        */
        org.project.model.Tournament tournament = new org.project.model.Tournament();
        tournament.setTournamentName(tournamentName);
        tournament.setTournamentName(this.tournamentRepository.addTournament(tournamentName));
        return "Tournament Created";
    }

    @Override
    public int getId(String tournamentName) {
        return tournamentRepository.getTournamentId(tournamentName);
    }
}
