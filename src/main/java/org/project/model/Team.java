package org.project.model;

import org.project.model.player.Batsman;
import org.project.model.player.Player;
import org.springframework.stereotype.Component;

@Component
public class Team {

    private static int teamCount = 0;

    public static void incrementTeamCount() {
        teamCount++;
    }

    public static int getTeamCount() {
        return teamCount;
    }

    private Player[] players = new Player[11];
    private int numberOfBatsman;
    private int numberOfBowler;
    private int numberOfAllRounder;
    private int runsScored;
    private int wicketsFallen;
    private String name;

    public Team() {
        //        InputService inputService = new InputService();
        //        inputService.playerTypeInput(this);
        //        InputInterface TakePlayerTypeInput = new PlayerTypeInput(this);
        //        TakePlayerTypeInput.collectInput();
        runsScored = 0;
        wicketsFallen = 0;
        numberOfAllRounder = 0;
        numberOfBatsman = 0;
        numberOfBowler = 0;
    }

    public String getTeamName() {
        return name;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setPlayers(Player[] players) {
        this.players = players;
    }

    public int GetNumberOfBatsman() {
        return numberOfBatsman;
    }

    public int getNumberOfBowler() {
        return numberOfBowler;
    }

    public int getNumberOfAllRounder() {
        return numberOfAllRounder;
    }

    public int getRunsScored() {
        return runsScored;
    }

    public int getWicketsFallen() {
        return wicketsFallen;
    }

    public Player getPlayer(int index) {
        return players[index];
    }

    public void setTeamName(String nameForTeam) {
        name = nameForTeam;
    }

    public void setRunsScored(int runsScoredTillNow) {
        runsScored += runsScoredTillNow;
    }

    public void setWicketsFallen() {
        wicketsFallen += 1;
    }

    public void setPlayers(int playerIndex, Player newPlayer) {
        players[playerIndex] = newPlayer;
    }

    public void updateNumberOfEachPlayers() {
        /*
            Counting the number of Batsmen,Bowlers and AllRounders in the team.
        */
        for (int i = 0; i < 11; i++) {
            if (players[i] instanceof Batsman) {
                this.numberOfBatsman++;
                //System.out.println("Number of Batsman : " + NumberOfBatsman);
            } else {
                //System.out.println("Number of Bowler : " + NumberOfBowler);
                this.numberOfBowler++;
            }
        }
    }

    public void updateBattingStatsOfPlayer(int playerIndex, int runs) {
        /*
            Updating the batting stats of player.
        */
        players[playerIndex].updateBattingStats(runs);
    }

    public void updateBowlingStatsOfPlayer(int playerIndex, int outComeOfTheBall) {
        /*
            Updating the bowling stats of player.
        */
        players[playerIndex].updateBowlingStats(outComeOfTheBall);
    }

}
