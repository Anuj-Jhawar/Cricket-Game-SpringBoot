package org.project.model.player;



import org.project.model.stats.BattingStats;
import org.project.model.stats.BowlingStats;
import org.project.model.stats.Stats;

import java.util.ArrayList;

public class Player {
    static int playerCount = 0;
    protected String name;
    private ArrayList<Stats> battingStats = new ArrayList<Stats>();
    private ArrayList<Stats> bowlingStats = new ArrayList<Stats>();

    public Player(){
        battingStats.add(new BattingStats());
        bowlingStats.add(new BowlingStats());
    }
    public static void incrementPlayerCount(){
        /*
            Increment the total number of object created of class Player.
        */
        playerCount++;
    }
    public static int getPlayerCount(){
        return playerCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void updateBattingStats(int runs) {
        /*
            Update Batting stats of the match for a player based on the outcome of the ball.
        */
        battingStats.get(battingStats.size() - 1).updateStats(runs);
    }

    public BattingStats getBattingStats(){
        /*
            Return Batting Stats of the player for the current match.
        */
        return (BattingStats) battingStats.get(battingStats.size() - 1);
    }

    public BowlingStats getBowlingStats(){
        /*
            Return Bowling Stats of the player for the current match.
        */
        return (BowlingStats) bowlingStats.get(bowlingStats.size() - 1);
    }

    public void updateBowlingStats(int outcomeOfTheBall) {
        /*
            Update Bowling stats of the match for a player based on the outcome of the ball.
        */
        bowlingStats.get(bowlingStats.size() - 1).updateStats(outcomeOfTheBall);
    }
    public void addBattingStatsObject(){
        /*
            Adding new object of Batting Stats.
        */
        battingStats.add(new BattingStats());
    }
    public void addBowlingStatsObject(){
        /*
            Adding new object of Bowling Stats.
        */
        bowlingStats.add(new BowlingStats());
    }

}
