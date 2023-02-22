package org.model.stats;

public class BattingStats implements Stats{

    public BattingStats(){
        score = 0;
        ballsPlayed = 0;
        battingStrikeRate = 0;
        numberOfFours = 0;
        numberOfSixes = 0;
    }
    private int score;
    private int ballsPlayed;
    private double battingStrikeRate;
    private int numberOfSixes;
    private int numberOfFours;

    public void setBallsPlayed(int ballsPlayed) {
        this.ballsPlayed = ballsPlayed;
    }

    public void setStrikeRate() {
        battingStrikeRate = ballsPlayed == 0? 0 : (score * 100.0) / ballsPlayed;
    }

    public void setNumberOfFours(int numberOfFours){
        this.numberOfFours = numberOfFours;
    }

    public void setNumberOfSixes(int numberOfSixes) {
        this.numberOfSixes = numberOfSixes;
    }

    public void setBoundaries(int runs) {
        if (runs == 4)
            setNumberOfFours(numberOfFours+1);
        else if (runs == 6)
            setNumberOfSixes(numberOfSixes+1);
    }

    public int getScore() {
        return score;
    }

    public void setScore(int runs) {
        score += runs;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public int getNumberOfSixes() {
        return numberOfSixes;
    }

    public int getNumberOfFours() {
        return numberOfFours;
    }

    public double getBattingStrikeRate() {
        return battingStrikeRate;
    }
    public void updateBattingStats(int runs) {
        if (runs == 7) {
            setBallsPlayed(ballsPlayed+1);
            return;
        }
        setScore(runs);
        setBallsPlayed(ballsPlayed+1);
        setStrikeRate();
        setBoundaries(runs);
    }

    public void updateStats(int runs){
        updateBattingStats(runs);
    }
}
