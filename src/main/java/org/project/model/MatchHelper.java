package org.project.model;

import org.project.model.scorecard.ScoreCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;

@Component
public class MatchHelper {
    @Autowired
    ScoreCard scoreCard;
    public String completeToss(Match match) {
        /*
            Function to complete the toss for the match.
        */
        System.out.println("Time for toss");
        int teamWhoWonTheToss = match.initiateToss();
        String Team1 = "Team1";
        if (teamWhoWonTheToss == 1) {
            return match.getTeam1().getTeamName();
        } else {
            return match.getTeam2().getTeamName();
        }
    }
    public void printScoreCard(Match match) {
        /*
            Function to print Others.ScoreCard
        */
        scoreCard.setScoreCard(match);
        scoreCard.printScoreCard();
    }
    public int initializeNumberOfOvers(Match match) {
        /*
            Decide number of overs based on the format of the match.
        */
        String t20Format = "T20";
        String t10Format = "T10";
        int numberofOversInmatch = 0;
        String Format = match.getFormat();
        if (Format.equals(t10Format)) {
            numberofOversInmatch = 10;
        } else if (Format.equals(t20Format)) {
            numberofOversInmatch = 20;
        } else {
            numberofOversInmatch = 50;
        }
        return numberofOversInmatch;
    }
    public int assignBowler(Match match, int bowlingTeamIndex, int lastBowler) {
        /*
            Assigning Others.Bowler for the next over and making sure that bowler does not repeat.
        */
        int numberOfAvailableBowlingOption = 11;
        int indexOfChosenBowler = 10 - (int) (Math.random() * (numberOfAvailableBowlingOption));
        while (lastBowler == indexOfChosenBowler) {
            indexOfChosenBowler = 10 - (int) (Math.random() * (numberOfAvailableBowlingOption));
        }
        return indexOfChosenBowler;
    }

    public ArrayList<Integer> assignBatsman(int batsman1, int batsman2, int outcomeOfTheBall, int overDone) {
        /*
            Assigning the Others.Batsman for the upcoming ball depending on the Outcome of last ball.
        */
        if (overDone == 1) {
            return assignBatsmanIfOverDone(batsman1, batsman2, outcomeOfTheBall);
        } else {
            return assignBatsmanIfOverNotDone(batsman1, batsman2, outcomeOfTheBall);
        }
    }
    public ArrayList<Integer> assignBatsmanIfOverDone(int batsman1, int batsman2, int outcomeOfTheBall) {
        /*
            Assigning the Others.Batsman depending on the OutcomeOfTheBall and If Others.Over done.
        */
        ArrayList<Integer> batsmanOrder = new ArrayList<Integer>();
        if (outcomeOfTheBall == 7) {
            int NewBatsman = assignNewBatsmanIfWicketFallen(batsman1, batsman2);
            batsmanOrder.add(batsman2);
            batsmanOrder.add(NewBatsman);
        } else {
            int RunsScoredOnTheBall = outcomeOfTheBall;
            if (RunsScoredOnTheBall % 2 == 0) {
                batsmanOrder.add(batsman2);
                batsmanOrder.add(batsman1);
            } else {
                batsmanOrder.add(batsman1);
                batsmanOrder.add(batsman2);
            }
        }
        return batsmanOrder;
    }

    public ArrayList<Integer> assignBatsmanIfOverNotDone(int batsman1, int batsman2, int outcomeOfTheBall) {
        /*
            Assigning the Others.Batsman depending on the outcomeOfTheBall and If Others.Over not done.
        */
        ArrayList<Integer> BatsmanOrder = assignBatsmanIfOverDone(batsman1, batsman2, outcomeOfTheBall);
        Collections.reverse(BatsmanOrder);
        return BatsmanOrder;
    }
    public int assignNewBatsmanIfWicketFallen(int batsman1, int batsman2) {
        /*
            Returns The new Others.Batsman
        */
        return Math.max(batsman1 + 1, batsman2 + 1);
    }
}
