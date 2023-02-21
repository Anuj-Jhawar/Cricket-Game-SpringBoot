package org.service.apiservices;

import org.repo.BattingStatsDB;
import org.repo.BowlingStatsDB;
import org.repo.MatchDB;
import org.repo.JdbcConnection;
import org.service.stats.BattingStats;
import org.service.stats.BowlingStats;
import org.service.stats.Stats;

import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

public class GetPlayerStatsForAGivenMatch {
    int tournamentId;
    int team1Id;
    int team2Id;
    Date date;
    int playerId;
    public GetPlayerStatsForAGivenMatch(Map<String,Object> requestBody){
        tournamentId = Integer.parseInt((String)requestBody.get("tournamentId"));
        team1Id = Integer.parseInt((String) requestBody.get("team1Id"));
        team2Id = Integer.parseInt((String)requestBody.get("team2Id"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = new Date(System.currentTimeMillis());
        try {
            sqlDate = new Date(dateFormat.parse((String) requestBody.get("date")).getTime());
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        date = sqlDate;
        playerId = Integer.parseInt((String)requestBody.get("playerId"));;
    }
    public Stats[] get(){
        Connection connection = JdbcConnection.getConnection();
        MatchDB matchDB = new MatchDB();
        int matchId = matchDB.getMatchIdByDate(tournamentId,team1Id,team2Id,date);
        BattingStatsDB battingStatsDB = new BattingStatsDB(matchId,team1Id,playerId);
        BattingStats battingStats = battingStatsDB.getBattingStats();
        BowlingStatsDB bowlingStatsDB = new BowlingStatsDB(matchId,team1Id,playerId);
        BowlingStats bowlingStats = bowlingStatsDB.getBowlingStats();
        Stats[] stats = new Stats[2];
        stats[0] = battingStats;
        stats[1] = bowlingStats;
        return stats;
    }
}
