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
import java.util.ArrayList;

public class GetScoreCard {
    String tournamentName;
    String team1Name;
    String team2Name;
    Date date;
    public GetScoreCard(String tournamentName, String team1Name, String team2Name, Date date){
        this.tournamentName = tournamentName;
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.date = date;
    }

    public int getTournamentId(String tournamentName, Connection connection) {
        FindTournamentId findTournamentId = new FindTournamentId();
        int tournamentId = findTournamentId.find(tournamentName, connection);
        return tournamentId;
    }

    public int getTeamId(String teamName, Connection connection) {
        FindTeamId findTeamId = new FindTeamId();
        int teamId = findTeamId.find(teamName, connection);
        return teamId;
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
        int tournamentId = getTournamentId(tournamentName, connection);
        int team1Id = getTeamId(team1Name, connection);
        int team2Id = getTeamId(team2Name, connection);
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
