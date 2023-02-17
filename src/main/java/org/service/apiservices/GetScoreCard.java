package org.service.apiservices;

import org.repo.databasequery.FindBattingFirstTeamId;
import org.repo.databasequery.FindMatchIdByStartDate;
import org.repo.databasequery.FindTeamId;
import org.repo.databasequery.FindTournamentId;
import org.repo.databasequery.scorecard.battingscorecard.GetBattingScoreCardOfAnInning;
import org.repo.databasequery.scorecard.bowlingscorecard.GetBowlingScoreCardOfAnInning;
import org.repo.jdbc.JdbcConnection;
import org.service.scorecardforplayer.ScoreCardForPlayer;

import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public class GetScoreCard {
    int tournamentId;
    int team1Id;
    int team2Id;
    Date date;
    public GetScoreCard(Map<String,Object> requestBody){
        tournamentId = Integer.parseInt((String)requestBody.get("tournamentId"));
        team1Id = Integer.parseInt((String) requestBody.get("team1Id"));
        team2Id = Integer.parseInt((String)requestBody.get("team2Id"));
        //System.out.println(tournamentId + " " + team1Id + " " + team2Id);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = new Date(System.currentTimeMillis());
        try {
            sqlDate = new Date(dateFormat.parse((String) requestBody.get("date")).getTime());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        date = sqlDate;
    }


    public int getMatchId(int tournamentId, int team1Id, int team2Id, Date date, Connection connection) {
        FindMatchIdByStartDate findMatchIdByStartDate = new FindMatchIdByStartDate(tournamentId, team1Id, team2Id, date);
        int matchId = findMatchIdByStartDate.find("", connection);
        return matchId;
    }

    public ArrayList<ScoreCardForPlayer> getBattingScoreCard(int teamId, int matchId, Connection connection) {
        GetBattingScoreCardOfAnInning getBattingScoreCardOfAnInning = new GetBattingScoreCardOfAnInning(teamId, matchId);
        ArrayList<ScoreCardForPlayer> teamBattingStats = getBattingScoreCardOfAnInning.getBattingScoreCardOfAnInning(connection);
        return teamBattingStats;
    }

    public ArrayList<ScoreCardForPlayer> getBowlingScoreCard(int teamId, int matchId, Connection connection) {
        GetBowlingScoreCardOfAnInning getBowlingScoreCardOfAnInning = new GetBowlingScoreCardOfAnInning(teamId, matchId);
        ArrayList<ScoreCardForPlayer> teamBowlingStats = getBowlingScoreCardOfAnInning.getBowlingScoreCardOfAnInning(connection);
        return teamBowlingStats;
    }
    public ArrayList<ArrayList<ScoreCardForPlayer>> get(){
        ArrayList<ArrayList<ScoreCardForPlayer>> stats = new ArrayList<>();
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection connection = jdbcConnection.getConnection();

        int matchId = getMatchId(tournamentId, team1Id, team2Id, date, connection);

        FindBattingFirstTeamId findBattingFirstTeamId = new FindBattingFirstTeamId();
        int battingFirstTeamId = findBattingFirstTeamId.find(matchId, connection);
        int battingSecondTeamId = battingFirstTeamId == team1Id ? team2Id : team1Id;

        stats.add(getBattingScoreCard(battingFirstTeamId, matchId, connection));
        stats.add(getBowlingScoreCard(battingSecondTeamId, matchId, connection));
        stats.add(getBattingScoreCard(battingSecondTeamId, matchId, connection));
        stats.add(getBowlingScoreCard(battingFirstTeamId, matchId, connection));
        return stats;
    }
}
