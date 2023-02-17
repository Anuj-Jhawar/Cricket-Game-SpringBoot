package org.service.apiservices;

import org.repo.databasequery.FindBattingStatsOfAPlayerForAMatch;
import org.repo.databasequery.FindBowlingStatsOfAPlayerForAMatch;
import org.repo.jdbc.JdbcConnection;
import org.service.stats.BattingStats;
import org.service.stats.BowlingStats;
import org.service.stats.Stats;

import java.sql.Connection;
import java.sql.Date;

public class GetPlayerStatsForAGivenMatch {
    String tournamentName;
    String team1Name;
    String team2Name;
    Date date;
    String playerName;
    public GetPlayerStatsForAGivenMatch(String tournamentName,String team1Name,String team2Name,Date date,String playerName){
        this.tournamentName = tournamentName;
        this.team1Name = team1Name;
        this.team2Name = team2Name;
        this.date = date;
        this.playerName = playerName;
    }
    public Stats[] get(){
        JdbcConnection jdbcConnection = new JdbcConnection();
        Connection connection = jdbcConnection.getConnection();
        FindBattingStatsOfAPlayerForAMatch findBattingStatsOfAPlayerForSpecificMatch = new FindBattingStatsOfAPlayerForAMatch();
        BattingStats battingStats = findBattingStatsOfAPlayerForSpecificMatch.find(tournamentName, team1Name, team2Name, playerName, date, connection);
        FindBowlingStatsOfAPlayerForAMatch findBowlingStatsOfAPlayerForSpecificMatch = new FindBowlingStatsOfAPlayerForAMatch();
        BowlingStats bowlingStats = findBowlingStatsOfAPlayerForSpecificMatch.find(tournamentName, team1Name, team2Name, playerName, date, connection);
        Stats[] stats = new Stats[2];
        stats[0] = battingStats;
        stats[1] = bowlingStats;
        return stats;
    }
}
