package org.model;

import lombok.Data;

@Data
public class Ball {
    private String batsmanName;
    private String bowlerName;
    private String fielderName;
    private boolean isANoBall;
    private int outcomeOfTheBall;

    public Ball() {
        outcomeOfTheBall = -1;
        batsmanName = "Not defined";
        bowlerName = "Not defined";
        fielderName = "Not defined";
        isANoBall = false;
    }

    public void assignBallOutcome() {
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

    public void setOutcomeOfTheBall(int DesiredOutcomeOfTheBall) {
        /*
            Setting pre-defined outcome for the ball
        */
        outcomeOfTheBall = DesiredOutcomeOfTheBall;
    }

    public int getOutcomeOfTheBall() {
        /*
            Returning the outcome of the ball.
        */
        return outcomeOfTheBall;
    }

}
