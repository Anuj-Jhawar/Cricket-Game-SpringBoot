package org.project.model.scorecard;


import org.project.model.Match;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.model.stats.BowlingStats;
import org.project.service.BowlingStatsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BowlingScoreCard implements InningScoreCard {

    @Autowired
    private Player[] players = new Player[11];
    @Autowired
    private Match match;
    @Autowired
    private Team bowlingTeam;
    @Autowired
    private BowlingStatsServiceImpl bowlingStatsServiceImpl;

    public void setBowlingScoreCard(Match match, Team bowlingTeam) {
        this.match = match;
        players = bowlingTeam.getPlayers();
        this.bowlingTeam = bowlingTeam;
    }

    public void showStats() {
        /*
            Printing the bowling part of the scorecard.
        */
        printHeadings();
        for (Player currentBowler : players) {
            BowlingStats bowlingStats = bowlingStatsServiceImpl.getBowlingStats(match.getTournamentName(),
                    match.getTeam1().getTeamName(), match.getTeam2().getTeamName(), currentBowler,
                    match.getBattingTeamIndex(), bowlingTeam.getTeamName());
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
