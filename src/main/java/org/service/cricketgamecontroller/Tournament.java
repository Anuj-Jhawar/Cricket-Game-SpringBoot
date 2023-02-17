package org.service.cricketgamecontroller;


import org.service.cricketgame.CricketGamePlay;

public class Tournament {
    String tournamentName;

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void playTournament() {
        CricketGamePlay CricketGame = new CricketGamePlay();
        CricketGame.play(tournamentName);
        //CricketGame.play(tournamentName);
    }
}
