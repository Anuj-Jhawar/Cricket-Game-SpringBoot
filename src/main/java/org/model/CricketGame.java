package org.model;

import org.repo.*;
import org.service.InputService;
import org.service.objectbuilder.BattingStatsBuilder;
import org.service.objectbuilder.BowlingStatsBuilder;
import org.model.player.Player;
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

    public CricketGame(String tournamentName){
        InputService inputService = new InputService();
        inputService.venueInput(this);
        inputService.formatInput(this);
//        InputInterface TakeFormatInput = new FormatInput(this);
//        InputInterface TakeVenueInput = new VenueInput(this);
//        TakeVenueInput.collectInput();
//        TakeFormatInput.collectInput();
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

    public void signalOutcomeOfTheBall(Ball ball) {
        umpire.signal(this,ball);
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
            Player player = team1.getPlayer(playerIndex);
            BattingStatsDB battingStatsDB = BattingStatsBuilder.getBattingStatsObject(tournamentName,team1.getTeamName(),team2.getTeamName(),player,this.getBattingTeamIndex(), team1.getTeamName());
            battingStatsDB.updateBattingStats(runs,player.getBattingStats().getScore(),player.getBattingStats().getBallsPlayed());
        } else {
            team2.updateBattingStatsOfPlayer(playerIndex, runs);
            Player player = team2.getPlayer(playerIndex);
            BattingStatsDB battingStatsDB = BattingStatsBuilder.getBattingStatsObject(tournamentName,team1.getTeamName(),team2.getTeamName(),player,this.getBattingTeamIndex(), team2.getTeamName());
            battingStatsDB.updateBattingStats(runs,player.getBattingStats().getScore(),player.getBattingStats().getBallsPlayed());
        }
    }

    public void updateBowlingStatsOfBowler(int teamIndex, int playerIndex, int outcomeOfTheBall) {
        /*
            Updating the bowler stats depending on the outcome of the ball.
        */
        if (teamIndex == 1) {
            team2.updateBowlingStatsOfPlayer(playerIndex, outcomeOfTheBall);
            Player player = team2.getPlayer(playerIndex);
            BowlingStatsDB bowlingStatsDB = BowlingStatsBuilder.getBowlingStatsObject(tournamentName,team1.getTeamName(),team2.getTeamName(),player,this.getBattingTeamIndex(),team2.getTeamName());
            bowlingStatsDB.updateBowlingStats(outcomeOfTheBall,player.getBowlingStats().getRunConceded(),player.getBowlingStats().getWickets());
        } else {
            team1.updateBowlingStatsOfPlayer(playerIndex, outcomeOfTheBall);
            Player player = team1.getPlayer(playerIndex);
            BowlingStatsDB bowlingStatsDB = BowlingStatsBuilder.getBowlingStatsObject(tournamentName,team1.getTeamName(),team2.getTeamName(),player,this.getBattingTeamIndex(), team1.getTeamName());
            bowlingStatsDB.updateBowlingStats(outcomeOfTheBall,player.getBowlingStats().getRunConceded(),player.getBowlingStats().getWickets());
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
        InputService inputService = new InputService();
        String TeamName = inputService.teamNameInput();
//        InputInterface TakeTeamNameInput = new TeamNameInput();
//        String TeamName = TakeTeamNameInput.collectInput();
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