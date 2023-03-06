package org.project.model.player;


import lombok.Data;
import org.project.model.stats.BattingStats;
import org.project.model.stats.BowlingStats;
import org.project.model.stats.Stats;
import org.springframework.stereotype.Component;

@Data
@Component
public class Player {

    private static int playerCount = 0;
    protected String name;
    private Stats battingStats = new BattingStats();
    private Stats bowlingStats = new BowlingStats();

    public Player() {
    }

    public static void incrementPlayerCount() {
        /*
            Increment the total number of object created of class Player.
        */
        playerCount++;
    }

    public static int getPlayerCount() {
        return playerCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateBattingStats(int runs) {
        /*
            Update Batting stats of the match for a player based on the outcome of the ball.
        */
        battingStats.updateStats(runs);
    }

    public BattingStats getBattingStats() {
        /*
            Return Batting Stats of the player for the current match.
        */
        return (BattingStats) battingStats;
    }

    public BowlingStats getBowlingStats() {
        /*
            Return Bowling Stats of the player for the current match.
        */
        return (BowlingStats) bowlingStats;
    }

    public void updateBowlingStats(int outcomeOfTheBall) {
        /*
            Update Bowling stats of the match for a player based on the outcome of the ball.
        */
        bowlingStats.updateStats(outcomeOfTheBall);
    }

}
