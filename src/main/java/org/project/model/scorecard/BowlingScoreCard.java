package org.project.model.scorecard;


import org.project.model.CricketGame;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.model.stats.BowlingStats;
import org.project.service.BowlingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BowlingScoreCard implements InningScoreCard {

    @Autowired
    private Player[] players = new Player[11];
    @Autowired
    private CricketGame game;
    @Autowired
    private Team bowlingTeam;
    @Autowired
    private BowlingStatsService bowlingStatsService;

    public void setBowlingScoreCard(CricketGame game, Team bowlingTeam) {
        this.game = game;
        players = bowlingTeam.getPlayers();
        this.bowlingTeam = bowlingTeam;
    }

    public void showStats() {
        /*
            Printing the bowling part of the scorecard.
        */
        printHeadings();
        for (Player currentBowler : players) {
            BowlingStats bowlingStats = bowlingStatsService.getBowlingStats(game.getTournamentName(),
                    game.getTeam1().getTeamName(), game.getTeam2().getTeamName(), currentBowler,
                    game.getBattingTeamIndex(), bowlingTeam.getTeamName());
            if (bowlingStats.getBallsBowled() > 0) {
                System.out.printf("%-20s %10s %10s %5s %n", currentBowler.getName(), bowlingStats.getRunConceded(),
                        bowlingStats.getBallsBowled(), bowlingStats.getWickets());
            }
        }
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

}
