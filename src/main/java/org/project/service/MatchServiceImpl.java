package org.project.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.model.Ball;
import org.project.model.Match;
import org.project.model.MatchHelper;
import org.project.model.Team;
import org.project.model.scorecard.ScoreCard;
import org.project.repo.JdbcConnection;
import org.project.repo.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;


@Data
@NoArgsConstructor
@Service
public class MatchServiceImpl {

    @Autowired
    private TeamServiceImpl teamServiceImpl;
    @Autowired
    private BowlingStatsServiceImpl bowlingStatsServiceImpl;
    @Autowired
    private BattingStatsServiceImpl battingStatsServiceImpl;
    @Autowired
    private PlayerServiceImpl playerServiceImpl;
    @Autowired
    private Match match;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private ScoreCard scoreCard;
    @Autowired
    private MatchHelper matchHelper;

    public String setUpMatch(Map<String, Object> requestBody) {
        /*
            Organize user input and start the match.
        */
        String tournamentName = (String) requestBody.get("tournamentName");
        String venue = (String) requestBody.get("venue");
        String format = (String) requestBody.get("format");
        Team team1 = teamServiceImpl.setTeamRepository((Map<String, Object>) requestBody.get("team1"));
        Team team2 = teamServiceImpl.setTeamRepository((Map<String, Object>) requestBody.get("team2"));
        this.play(tournamentName, team1, team2, venue, format);
        return "";
    }

    public void play(String tournamentName, Team team1, Team team2, String venue, String format) {
        /*
            Start playing cricket match.
        */
        Scanner scn = new Scanner(System.in);
        match.setCricketMatch(tournamentName, team1, team2, venue, format);
        System.out.println("match Start");
        String teamWhoWonTheToss = matchHelper.completeToss(match);
        System.out.println("Toss won by" + teamWhoWonTheToss);
        this.addToDataBase(match);
        System.out.println(teamWhoWonTheToss + " decided to bat first");
        letsPlayTheMatch(match);
        System.out.println("Team Who won the match is " + match.getWinner());
        matchHelper.printScoreCard(match);
    }


    public void addToDataBase(Match match) {
        /*
            Add respective things to their respective databases.
        */
        JdbcConnection.initializeConnection();
        match.getTeam1().setTeamName(teamServiceImpl.addTeamToTeamTable(match.getTeam1()));
        match.getTeam2().setTeamName(teamServiceImpl.addTeamToTeamTable(match.getTeam2()));
        this.addMatchToMatchTable(match);
        playerServiceImpl.addPlayerToPlayerTable(match);
        bowlingStatsServiceImpl.addBowlingStatsToBowlingStatsTable(match, match.getTeam1());
        bowlingStatsServiceImpl.addBowlingStatsToBowlingStatsTable(match, match.getTeam2());
        battingStatsServiceImpl.addBattingStatsToBattingStatsTable(match, match.getTeam1());
        battingStatsServiceImpl.addBattingStatsToBattingStatsTable(match, match.getTeam2());
    }

    void letsPlayTheMatch(Match match) {
        /*
            Function in which all the match happens.
        */
        int initializeNumberOfOvers = matchHelper.initializeNumberOfOvers(match);
        int target = -1;
        for (int i = 0; i < 2; i++) {
            playAInning(match, target, initializeNumberOfOvers,
                    i == 0 ? match.getBattingTeamIndex() : match.getBowlingTeamIndex());
            target = match.getScoreOfTeam(match.getBattingTeamIndex());
            System.out.println("Innings Break");
        }
        assignWinnerOfTheMatch(match);
    }

    public void addMatchToMatchTable(Match match) {
        /*
            Add match.
        */
        this.addMatch(match.getTournamentName(), match.getTeam1().getTeamName(), match.getTeam2().getTeamName(),
                match.getBattingTeamIndex());
    }

    void playAInning(Match match, int target, int numberOfOversInmatch, int i) {
        /*
            Function to play a Inning.
        */
        int batsmanOnStrikeIndex = 0, batsman2 = 0, wickets = 0;
        Ball lastBall = new Ball();
        lastBall.setOutcomeOfTheBall('0');
        int lastBowler = -1;
        for (int j = 0; j < numberOfOversInmatch; j++) {
            int currentBowler = matchHelper.assignBowler(match, i, lastBowler);
            for (int k = 0; k < 6; k++) {
                int lastBallOutcome = lastBall.getOutcomeOfTheBall();
                ArrayList<Integer> batsmanOrder = matchHelper.assignBatsman(batsmanOnStrikeIndex, batsman2,
                        lastBallOutcome,
                        k == 0 ? 1 : 0);
                batsmanOnStrikeIndex = batsmanOrder.get(0);
                batsman2 = batsmanOrder.get(0);
                Ball NewBall = playTheBall(match, i, batsmanOnStrikeIndex, currentBowler, target == -1 ? 0 : 1);
                if (NewBall.getOutcomeOfTheBall() == 7) {
                    wickets++;
                }
                lastBall = NewBall;
                boolean checkIfInningIsOver = checkIfInningIsOver(match, wickets, target);
                if (checkIfInningIsOver) {
                    break;
                }
            }
            lastBowler = currentBowler;
            if (wickets == 10) {
                break;
            }
        }
    }

    void assignWinnerOfTheMatch(Match match) {
        /*
            Function to assign Winner of the match.
        */
        int matchId = this.getMatchId(match.getTournamentName(), match.getTeam1().getTeamName(),
                match.getTeam2().getTeamName(), match.getBattingTeamIndex());
        if (match.getTeam1().getRunsScored() > match.getTeam2().getRunsScored()) {
            match.setWinner(match.getTeam1().getTeamName());
            this.updateResult(1, matchId);
        } else {
            match.setWinner(match.getTeam2().getTeamName());
            this.updateResult(2, matchId);
        }
    }

    public void addMatch(String tournamentName, String team1Name, String team2Name, int battingTeamIndex) {
        matchRepository.addMatch(tournamentName, team1Name, team2Name, battingTeamIndex);
    }

    Ball playTheBall(Match match, int teamIndex, int batsmanOnStrikeIndex, int currentBowler, int inningNo) {
        /*
            Playing and assigning every outcome of the ball.
        */
        Ball newBall = new Ball();
        newBall.assignBallOutcome();
        Team battingTeam = teamIndex == 1 ? match.getTeam1() : match.getTeam2();
        Team bowlingTeam = teamIndex == 1 ? match.getTeam2() : match.getTeam1();
        newBall.setBowlerName(bowlingTeam.getPlayer(currentBowler).getName());
        newBall.setBatsmanName(battingTeam.getPlayer(batsmanOnStrikeIndex).getName());
        match.signalOutcomeOfTheBall(newBall, inningNo);
        updateBattingAndBowlingStatsAfterEachBall(match, teamIndex, batsmanOnStrikeIndex, currentBowler, newBall);
        return newBall;
    }

    boolean checkIfInningIsOver(Match match, int wickets, int target) {
        return (target != -1 && match.getScoreOfTeam(1) > target) || wickets == 10;
    }

    public int getMatchId(String tournamentName, String team1Name, String team2Name, int battingTeamIndex) {
        return matchRepository.getMatchId(tournamentName, team1Name, team2Name, battingTeamIndex);
    }

    public void updateResult(int teamNo, int matchId) {
        matchRepository.updateResult(teamNo, matchId);
    }

    void updateBattingAndBowlingStatsAfterEachBall(Match match, int teamIndex, int batsmanOnStrikeIndex,
                                                   int currentBowler, Ball newBall) {
        /*
            Updating the stats of batsman and bowler after every ball.
        */
        if (newBall.getOutcomeOfTheBall() == 7) {
            match.updateBattingStatsOfBatsman(teamIndex, batsmanOnStrikeIndex, 7);
        } else {
            match.updateTeamBattingStats(teamIndex, newBall.getOutcomeOfTheBall());
            match.updateBattingStatsOfBatsman(teamIndex, batsmanOnStrikeIndex, newBall.getOutcomeOfTheBall());
        }
        match.updateBowlingStatsOfBowler(teamIndex, currentBowler, newBall.getOutcomeOfTheBall());
    }

    public int getMatchIdByDate(int tournamentId, int team1Id, int team2Id, Date date) {
        return matchRepository.getMatchIdByDate(tournamentId, team1Id, team2Id, date);
    }

    public int findBattingFirstTeam(int matchId) {
        return matchRepository.findBattingFirstTeam(matchId);
    }
}