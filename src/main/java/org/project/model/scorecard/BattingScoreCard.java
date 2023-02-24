package org.project.model.scorecard;


import org.project.model.CricketGame;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.model.stats.BattingStats;
import org.project.repo.BattingStatsDB;
import org.project.service.statsbuilder.BattingStatsBuilder;

public class BattingScoreCard implements InningScoreCard {

    Player[] players;
    CricketGame game;
    Team battingTeam;

    public BattingScoreCard(CricketGame game, Team battingTeam) {
        this.game = game;
        players = battingTeam.getPlayers();
        this.battingTeam = battingTeam;
    }

    void printHeadings() {
        /*
            Printing the headers for the Batting part of the scorecard.
        */
        String batsmanName = "BatsmanName";
        String runs = "Runs";
        String balls = "Balls";
        String fours = "4s";
        String sixes = "6s";
        String strikeRate = "S.R.";
        System.out.printf("%-20s %10s %10s %10s %10s %10.6s %n", batsmanName, runs, balls, fours, sixes, strikeRate);
    }

    public void showStats() {
        /*
            Printing the batting part of the scorecard.
        */
        printHeadings();
        for (Player batsman : players) {
            BattingStatsDB battingStatsDB = BattingStatsBuilder.getBattingStatsObject(game.getTournamentName(),
                    game.getTeam1().getTeamName(), game.getTeam2().getTeamName(), batsman, game.getBattingTeamIndex(),
                    battingTeam.getTeamName());
            BattingStats battingStats = battingStatsDB.getBattingStats();
            if (battingStats.getBallsPlayed() > 0) {
                System.out.printf("%-20s %10s %10s %10s %10s %10.2f %n", batsman.getName(), battingStats.getScore(),
                        battingStats.getBallsPlayed(), battingStats.getNumberOfFours(), battingStats.getNumberOfSixes(),
                        battingStats.getBattingStrikeRate());
            }
        }

    }
}
