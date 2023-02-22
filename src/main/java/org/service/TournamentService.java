package org.service;

import org.service.cricketgamecontroller.PlayTournament;

public class TournamentService {
    public String start(){
        PlayTournament playTournament = new PlayTournament();
        playTournament.main();
        return "Tournament Started";
    }
}
