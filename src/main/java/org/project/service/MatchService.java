package org.project.service;

import org.project.model.Ball;
import org.project.model.Match;
import org.project.model.Team;

import java.sql.Date;
import java.util.Map;

public interface MatchService {

    public String setUpMatch(Map<String, Object> requestBody);

    public void play(String tournamentName, Team team1, Team team2, String venue, String format);


    public void addToDataBase(Match match);

    void letsPlayTheMatch(Match match);

    public void addMatchToMatchTable(Match match);

    void playAInning(Match match, int target, int numberOfOversInmatch, int i);

    void assignWinnerOfTheMatch(Match match);

    public void addMatch(String tournamentName, String team1Name, String team2Name, int battingTeamIndex);

    Ball playTheBall(Match match, int teamIndex, int batsmanOnStrikeIndex, int currentBowler, int inningNo);

    boolean checkIfInningIsOver(Match match, int wickets, int target);

    public int getMatchId(String tournamentName, String team1Name, String team2Name, int battingTeamIndex);

    public void updateResult(int teamNo, int matchId);

    void updateBattingAndBowlingStatsAfterEachBall(Match match, int teamIndex, int batsmanOnStrikeIndex,
                                                   int currentBowler, Ball newBall);

    public int getMatchIdByDate(int tournamentId, int team1Id, int team2Id, Date date);

    public int findBattingFirstTeam(int matchId);

}
