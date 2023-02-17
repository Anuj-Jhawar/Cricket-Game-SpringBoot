package org.service.scorecard;


import org.service.cricketgame.CricketGame;
import org.service.cricketgame.Team;
import org.repo.databasequery.scorecard.GetScoreCardFromDatabase;
import org.repo.databasequery.scorecard.battingscorecard.GetBattingStatsFromDatabase;
import org.service.player.Player;
import org.service.stats.BattingStats;

public class BattingScoreCard implements InningScoreCard {
    Player[] players;
    CricketGame game;
    Team battingTeam;
    public BattingScoreCard(CricketGame game,Team battingTeam) {
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
            GetScoreCardFromDatabase getScoreCardFromDatabase = new GetBattingStatsFromDatabase();
            BattingStats battingStats = (BattingStats) getScoreCardFromDatabase.getStats(game,battingTeam.getTeamName(),batsman.getName());
            if (battingStats.getBallsPlayed() > 0) {
                System.out.printf("%-20s %10s %10s %10s %10s %10.2f %n", batsman.getName(), battingStats.getScore(), battingStats.getBallsPlayed(),
                        battingStats.getNumberOfFours(), battingStats.getNumberOfSixes(), battingStats.getBattingStrikeRate());
            }
        }

    }
}
