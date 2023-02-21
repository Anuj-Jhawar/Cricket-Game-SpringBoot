package org.service.scorecard;


import org.repo.BowlingStatsDB;
import org.service.builder.BowlingStatsBuilder;
import org.service.cricketgame.CricketGame;
import org.service.cricketgame.Team;
import org.service.player.Bowler;
import org.service.player.Player;
import org.service.stats.BowlingStats;

public class BowlingScoreCard implements InningScoreCard {
    Player[] players = new Player[11];
    CricketGame game;
    Team bowlingTeam;
    public BowlingScoreCard(CricketGame game,Team bowlingTeam) {
        this.game = game;
        players = bowlingTeam.getPlayers();
        this.bowlingTeam = bowlingTeam;
    }

    void printHeadings() {
        /*
            Printing the headers for the Bowling part of the scorecard.
        */
        String bowlerNames = "BowlerName";
        String runs = "Runs";
        String balls = "Balls";
        String wickets = "W";
        System.out.printf("%-20s %10s %10s %5s %n", bowlerNames, runs, balls, wickets);
    }

    public void showStats() {
        /*
            Printing the bowling part of the scorecard.
        */
        printHeadings();
        for (Player currentBowler : players) {
            BowlingStatsDB bowlingStatsDB = BowlingStatsBuilder.getBowlingStatsObject(game.getTournamentName(),game.getTeam1().getTeamName(),game.getTeam2().getTeamName(),currentBowler,game.getBattingTeamIndex(),bowlingTeam.getTeamName());
            BowlingStats bowlingStats = bowlingStatsDB.getBowlingStats();
            if (currentBowler instanceof Bowler && bowlingStats.getBallsBowled() > 0) {
                System.out.printf("%-20s %10s %10s %5s %n", currentBowler.getName(), bowlingStats.getRunConceded(), bowlingStats.getBallsBowled(), bowlingStats.getWickets());
            }
        }
    }
}
