package org.service.cricketgame;

import org.repo.databaseupdate.battingstats.UpdateBattingStats;
import org.repo.databaseupdate.bowlingstats.UpdateBowlingStats;
import org.service.input.FormatInput;
import org.service.input.InputInterface;
import org.service.input.TeamNameInput;
import org.service.input.VenueInput;
import org.service.storeteam.TeamMap;

public class CricketGame {
    private Team team1;
    private Team team2;
    private Toss tossForGame = new Toss();
    private Umpire umpire = new Umpire();
    private String venue;
    private String winner;
    private String format;
    private String tournamentName;

    CricketGame(String tournamentName){
        InputInterface TakeFormatInput = new FormatInput(this);
        InputInterface TakeVenueInput = new VenueInput(this);
        TakeVenueInput.collectInput();
        TakeFormatInput.collectInput();
        this.tournamentName = tournamentName;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setVenueForTheGame(String venueName) {
        venue = venueName;
    }

    public void setFormatForTheGame(String formatType) {
        format = formatType;
    }

    public int initiateToss() {
        return tossForGame.callForToss();
    }
    public String getFormat() {
        return format;
    }

    public void signalOutcomeOfTheBall(int outcomeOfTheBall) {
        umpire.signal(outcomeOfTheBall);
    }

    public void setWinner(String winningTeam) {
        winner = winningTeam;
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

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getTournamentName(){
        return tournamentName;
    }
    public int getBattingTeamIndex(){
        return tossForGame.getBattingTeamIndex();
    }
    public int getBowlingTeamIndex(){
        return tossForGame.getBowlingTeamIndex();
    }
    public void updateBattingStatsOfBatsman(int teamIndex, int playerIndex, int runs) {
        /*
            Updating the batsman stats depending on the runs scored on the ball.
        */
        if (teamIndex == 1) {
            team1.updateBattingStatsOfPlayer(playerIndex, runs);
            UpdateBattingStats updateBattingStatsOfPlayer = new UpdateBattingStats(this,team1,team1.getPlayer(playerIndex),runs);
            updateBattingStatsOfPlayer.updateBattingStatsOfPlayer();
        } else {
            team2.updateBattingStatsOfPlayer(playerIndex, runs);
            UpdateBattingStats updateBattingStatsOfPlayer = new UpdateBattingStats(this,team2,team2.getPlayer(playerIndex),runs);
            updateBattingStatsOfPlayer.updateBattingStatsOfPlayer();
        }
    }

    public void updateBowlingStatsOfBowler(int teamIndex, int playerIndex, int outcomeOfTheBall) {
        /*
            Updating the bowler stats depending on the outcome of the ball.
        */
        if (teamIndex == 1) {
            team2.updateBowlingStatsOfPlayer(playerIndex, outcomeOfTheBall);
            UpdateBowlingStats updateBowlingStatsOfPlayer = new UpdateBowlingStats(this,team2.getPlayer(playerIndex),team2,outcomeOfTheBall);
            updateBowlingStatsOfPlayer.updateBowlingStatsOfPlayer();
        } else {
            team1.updateBowlingStatsOfPlayer(playerIndex, outcomeOfTheBall);
            UpdateBowlingStats updateBowlingStatsOfPlayer = new UpdateBowlingStats(this,team1.getPlayer(playerIndex),team1,outcomeOfTheBall);
            updateBowlingStatsOfPlayer.updateBowlingStatsOfPlayer();
        }
    }

    public int getScoreOfTeam(int index) {
        /*
            Returning the target set by the Batting team.
        */
        if (index == 1)
            return team1.getRunsScored();
        else
            return team2.getRunsScored();
    }
    public String getWinner() {
        /*
            Returning the winner of the game.
        */
        return winner;
    }
    public Team setTeamForTheGame(){
        /*
            Setting team for the game. If previously team played then assigning it directly.
        */
        InputInterface TakeTeamNameInput = new TeamNameInput();
        String TeamName = TakeTeamNameInput.collectInput();
        Team team = TeamMap.getTeamMap().getTeam(TeamName);
        team.setTeamName(TeamName);
        if(TeamMap.getTeamMap().containsTeam(TeamName)==false)
            team.updateNumberOfEachPlayers();
        else
            team.addBattingAndBowlingStatsForEachPlayer();
        return team;
    }
    public void setTeamsForTheGame(){
        team1 = setTeamForTheGame();
        team2 = setTeamForTheGame();
    }
}
