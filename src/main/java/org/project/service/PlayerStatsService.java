package org.project.service;

import org.project.model.stats.BattingStats;
import org.project.model.stats.BowlingStats;
import org.project.model.stats.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

@Service
public class PlayerStatsService {

    int tournamentId;
    int team1Id;
    int team2Id;
    Date date;
    int playerId;
    @Autowired
    private BattingStatsService battingStatsService;
    @Autowired
    private BowlingStatsService bowlingStatsService;
    @Autowired
    private CricketGameService cricketGameService;

    public void setPlayerStatsService(Map<String, Object> requestBody) {
        tournamentId = Integer.parseInt((String) requestBody.get("tournamentId"));
        team1Id = Integer.parseInt((String) requestBody.get("team1Id"));
        team2Id = Integer.parseInt((String) requestBody.get("team2Id"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = new Date(System.currentTimeMillis());
        try {
            sqlDate = new Date(dateFormat.parse((String) requestBody.get("date")).getTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        date = sqlDate;
        playerId = Integer.parseInt((String) requestBody.get("playerId"));
        ;
    }

    public Stats[] get(Map<String, Object> requestBody) {
        /*
            Return player stats for a specific match.
        */
        this.setPlayerStatsService(requestBody);
        int matchId = cricketGameService.getMatchIdByDate(tournamentId,team1Id,team2Id,date);
        BattingStats battingStats = battingStatsService.getBattingStats(matchId,team1Id,playerId);
        BowlingStats bowlingStats = bowlingStatsService.getBowlingStats(matchId,team1Id,playerId);
        Stats[] stats = new Stats[2];
        stats[0] = battingStats;
        stats[1] = bowlingStats;
        return stats;
    }
}
