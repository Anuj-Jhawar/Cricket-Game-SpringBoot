package org.service.apiservices;

import org.service.cricketgamecontroller.PlayTournament;

public class StartNewTournament {
    public String start(){
        PlayTournament playTournament = new PlayTournament();
        playTournament.main();
        return "Tournament Started";
    }
}
