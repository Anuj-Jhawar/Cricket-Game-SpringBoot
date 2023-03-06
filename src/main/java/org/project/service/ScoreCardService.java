package org.project.service;

import org.project.model.ScoreCardForPlayer;
import org.project.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

@Service
public class ScoreCardService {

    private int tournamentId;
    private int team1Id;
    private int team2Id;
    private Date date;
    @Autowired
    private CricketGameService cricketGameService;
//    @Autowired
//    private BattingScoreCardRepository battingScoreCardRepository;
//    @Autowired
//    private BowlingScoreCardRepository bowlingScoreCardRepository;
    @Autowired
    private BattingStatsRepository battingStatsRepository;
    @Autowired
    private BowlingStatsRepository bowlingStatsRepository;

    public void setScoreCardService(Map<String, Object> requestBody) {
        tournamentId = Integer.parseInt((String) requestBody.get("tournamentId"));
        team1Id = Integer.parseInt((String) requestBody.get("team1Id"));
        team2Id = Integer.parseInt((String) requestBody.get("team2Id"));
        //System.out.println(tournamentId + " " + team1Id + " " + team2Id);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = new Date(System.currentTimeMillis());
        try {
            sqlDate = new Date(dateFormat.parse((String) requestBody.get("date")).getTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        date = sqlDate;
    }


    public int getMatchId(int tournamentId, int team1Id, int team2Id, Date date, Connection connection) {
        int matchId = cricketGameService.getMatchIdByDate(tournamentId,team1Id,team2Id,date);
        return matchId;
    }

    public ArrayList<ScoreCardForPlayer> getBattingScoreCard(int teamId, int matchId, Connection connection) {
        ArrayList<ScoreCardForPlayer> teamBattingStats = battingStatsRepository.getBattingScoreCardOfAnInning(
                matchId,teamId,connection);
        return teamBattingStats;
    }

    public ArrayList<ScoreCardForPlayer> getBowlingScoreCard(int teamId, int matchId, Connection connection) {
        ArrayList<ScoreCardForPlayer> teamBowlingStats = bowlingStatsRepository.getBowlingScoreCardOfAnInning(
                matchId,teamId,connection);
        return teamBowlingStats;
    }

    public ArrayList<ArrayList<ScoreCardForPlayer>> get(Map<String, Object> requestBody) {
        /*
            Return scorecard for a given match.
        */
        this.setScoreCardService(requestBody);
        JdbcConnection.initializeConnection();
        ArrayList<ArrayList<ScoreCardForPlayer>> stats = new ArrayList<>();
        Connection connection = JdbcConnection.getConnection();
        int matchId = getMatchId(tournamentId, team1Id, team2Id, date, connection);
        int battingFirstTeamId = cricketGameService.findBattingFirstTeam(matchId);
        int battingSecondTeamId = battingFirstTeamId == team1Id ? team2Id : team1Id;

        stats.add(getBattingScoreCard(battingFirstTeamId, matchId, connection));
        stats.add(getBowlingScoreCard(battingSecondTeamId, matchId, connection));
        stats.add(getBattingScoreCard(battingSecondTeamId, matchId, connection));
        stats.add(getBowlingScoreCard(battingFirstTeamId, matchId, connection));
        return stats;
    }
}
