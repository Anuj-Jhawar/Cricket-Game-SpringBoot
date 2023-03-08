package org.project.model;

import lombok.Data;
import org.project.model.player.Player;
import org.project.service.BattingStatsService;
import org.project.service.BowlingStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Data
public class Match {

    @Autowired
    BattingStatsService battingStatsService;
    @Autowired
    BowlingStatsService bowlingStatsService;
    private org.project.model.Team team1;
    private org.project.model.Team team2;
    private org.project.model.Toss tossForMatch = new Toss();
    @Autowired
    private Umpire umpire;
    private String venue;
    private String winner;
    private String format;
    private String tournamentName;
    private int battingTeamIndex;

    public Match() {
    }

    public void setCricketMatch(String tournamentName, org.project.model.Team team1, org.project.model.Team team2,
                               String venue, String format) {
        this.tournamentName = tournamentName;
        this.team1 = team1;
        this.team2 = team2;
        this.venue = venue;
        this.format = format;
    }

    public org.project.model.Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setVenueForThematch(String venueName) {
        venue = venueName;
    }

    public void setFormatForThematch(String formatType) {
        format = formatType;
    }

    public int initiateToss() {
        int returnValue = tossForMatch.callForToss();
        battingTeamIndex = tossForMatch.getBattingTeamIndex();
        return returnValue;
    }

    public String getFormat() {
        return format;
    }

    public void signalOutcomeOfTheBall(Ball ball, int inningNo) {
        umpire.signal(this, ball, inningNo);
    }

    public void updateTeamBattingStats(int index, int outComeOfTheBall) {
        /*
            Updating the Team Batting Stats.
        */
        if (index == 0) {
            team1.setRunsScored(outComeOfTheBall);
        } else {
            team2.setRunsScored(outComeOfTheBall);
        }
    }

    public void updateTeamWicketsFallen(int index) {
        /*
            Updating the Others.Team WicketFallen Stats.
        */
        if (index == 0) {
            team1.setWicketsFallen();
        } else {
            team1.setWicketsFallen();
        }
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public int getBowlingTeamIndex() {
        return tossForMatch.getBowlingTeamIndex();
    }

    public void updateBattingStatsOfBatsman(int teamIndex, int playerIndex, int runs) {
        /*
            Updating the batsman stats depending on the runs scored on the ball.
        */
        if (teamIndex == 1) {
            team1.updateBattingStatsOfPlayer(playerIndex, runs);
            Player player = team1.getPlayer(playerIndex);
            battingStatsService.updateBattingStats(tournamentName, team1.getTeamName(), team2.getTeamName(), player,
                    this.getBattingTeamIndex(), team1.getTeamName(), runs, player.getBattingStats().getScore(),
                    player.getBattingStats().getBallsPlayed());
        } else {
            team2.updateBattingStatsOfPlayer(playerIndex, runs);
            Player player = team2.getPlayer(playerIndex);
            battingStatsService.updateBattingStats(tournamentName, team1.getTeamName(), team2.getTeamName(), player,
                    this.getBattingTeamIndex(), team2.getTeamName(), runs, player.getBattingStats().getScore(),
                    player.getBattingStats().getBallsPlayed());
        }
    }

    public int getBattingTeamIndex() {
        return battingTeamIndex;
    }

    public void updateBowlingStatsOfBowler(int teamIndex, int playerIndex, int outcomeOfTheBall) {
        /*
            Updating the bowler stats depending on the outcome of the ball.
        */
        if (teamIndex == 1) {
            team2.updateBowlingStatsOfPlayer(playerIndex, outcomeOfTheBall);
            Player player = team2.getPlayer(playerIndex);
            bowlingStatsService.updateBowlingStats(tournamentName, team1.getTeamName(), team2.getTeamName(), player,
                    this.getBattingTeamIndex(), team2.getTeamName(), outcomeOfTheBall, player.getBowlingStats());
        } else {
            team1.updateBowlingStatsOfPlayer(playerIndex, outcomeOfTheBall);
            Player player = team1.getPlayer(playerIndex);
            bowlingStatsService.updateBowlingStats(tournamentName, team1.getTeamName(), team2.getTeamName(), player,
                    this.getBattingTeamIndex(), team1.getTeamName(), outcomeOfTheBall, player.getBowlingStats());
        }
    }

    public int getScoreOfTeam(int index) {
        /*
            Returning the target set by the Batting team.
        */
        if (index == 1) {
            return team1.getRunsScored();
        } else {
            return team2.getRunsScored();
        }
    }

    public String getWinner() {
        /*
            Returning the winner of the match.
        */
        return winner;
    }

    public void setWinner(String winningTeam) {
        winner = winningTeam;
    }

    public void setBattingTeamIndex(int teamIndex){
        battingTeamIndex = teamIndex;
    }
}
