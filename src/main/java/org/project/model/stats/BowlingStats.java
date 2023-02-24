package org.project.model.stats;


public class BowlingStats implements Stats {

    private int runConceded;

    private int wickets;

    private int ballsBowled;

    private double bowlingStrikeRate;

    private double bowlingAverage;

    public BowlingStats() {
        runConceded = 0;
        wickets = 0;
        ballsBowled = 0;
        bowlingAverage = 0.0;
        bowlingStrikeRate = 0.0;
    }

    public void setBowlingAverage() {
        bowlingAverage = wickets == 0 ? 0 : (runConceded * 1.0) / (wickets);
    }

    public void setWickets(int wickets) {
        this.wickets = wickets;
    }

    public void updateBowlingStrikeRate() {
        bowlingStrikeRate = (ballsBowled * 1.0) / wickets;
    }

    public void setRunsConceded(int runs) {
        runConceded += runs;
    }

    public void setBallsBowled(int ballsBowled) {
        this.ballsBowled = ballsBowled;
    }

    public int getRunConceded() {
        return runConceded;
    }

    public int getBallsBowled() {
        return ballsBowled;
    }

    public int getWickets() {
        return wickets;
    }

    public void updateBowlingStats(int outcomeOfTheBall) {
        /*
            Function to invoke all the batting stats of the bowler.
        */
        setBallsBowled(ballsBowled + 1);
        if (outcomeOfTheBall == 7) {
            setWickets(wickets + 1);
        } else {
            setRunsConceded(outcomeOfTheBall);
        }
        setBowlingAverage();
        updateBowlingStrikeRate();
    }

    public void updateStats(int runs) {
        updateBowlingStats(runs);
    }
}
