package org.project.model.scorecard;


import org.project.model.CricketGame;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.model.stats.BattingStats;
import org.project.service.BattingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BattingScoreCard implements InningScoreCard {

    @Autowired
    private Player[] players;
    @Autowired
    private CricketGame game;
    @Autowired
    private Team battingTeam;
    @Autowired
    private BattingStatsService battingStatsService;

    public void setBattingScoreCard(CricketGame game, Team battingTeam) {
        this.game = game;
        players = battingTeam.getPlayers();
        this.battingTeam = battingTeam;
    }

    public void showStats() {
        /*
            Printing the batting part of the scorecard.
        */
        printHeadings();
        for (Player batsman : players) {
            BattingStats battingStats = battingStatsService.getBattingStats(game.getTournamentName(),
                    game.getTeam1().getTeamName(), game.getTeam2().getTeamName(), batsman, game.getBattingTeamIndex(),
                    battingTeam.getTeamName());
            if (battingStats.getBallsPlayed() > 0) {
                System.out.printf("%-20s %10s %10s %10s %10s %10.2f %n", batsman.getName(), battingStats.getScore(),
                        battingStats.getBallsPlayed(), battingStats.getNumberOfFours(), battingStats.getNumberOfSixes(),
                        battingStats.getBattingStrikeRate());
            }
        }

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
}
