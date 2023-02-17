package org.service.cricketgame;

public class Ball {
    private final String batsmanName;
    private final String bowlerName;
    private final String fielderName;
    private final boolean isANoBall;
    private int outcomeOfTheBall;

    Ball() {
        outcomeOfTheBall = -1;
        batsmanName = "Not defined";
        bowlerName = "Not defined";
        fielderName = "Not defined";
        isANoBall = false;
    }

    void assignBallOutcome() {
        /*
            Assigning the outcome of the ball.
        */
        int BallOutcome = (int) (Math.random() * 100);
        if (BallOutcome >= 95) {
            outcomeOfTheBall = 7;
        } else {
            outcomeOfTheBall = (BallOutcome / 17);
        }
    }

    void setOutcomeOfTheBall(int DesiredOutcomeOfTheBall) {
        /*
            Setting pre-defined outcome for the ball
        */
        outcomeOfTheBall = DesiredOutcomeOfTheBall;
    }

    int getOutcomeOfTheBall() {
        /*
            Returning the outcome of the ball.
        */
        return outcomeOfTheBall;
    }

}
