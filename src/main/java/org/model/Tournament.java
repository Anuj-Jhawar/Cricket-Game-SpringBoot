package org.model;


import org.service.CricketGameService;

public class Tournament {
    String tournamentName;

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void playTournament() {
        CricketGameService CricketGame = new CricketGameService();
        CricketGame.play(tournamentName);
        //CricketGame.play(tournamentName);
    }
}
