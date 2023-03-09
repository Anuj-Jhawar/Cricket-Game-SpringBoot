package org.project.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.project.model.Match;
import org.project.model.Team;
import org.project.model.player.Bowler;
import org.project.model.stats.BattingStats;
import org.project.model.stats.BowlingStats;
import org.project.model.stats.Stats;
import org.project.repo.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;

@Service
@NoArgsConstructor
@Data
public class PlayerServiceImpl {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private BattingStatsServiceImpl battingStatsServiceImpl;
    @Autowired
    private BowlingStatsServiceImpl bowlingStatsServiceImpl;
    @Autowired
    private MatchServiceImpl matchService;

    public int getPlayerId(String playerName) {
        /*
            Return player id from database.
        */
        return playerRepository.getPlayerId(playerName);
    }

    public String getPlayerName(int playerID) {
        return playerRepository.getPlayerName(playerID);
    }

    public void addPlayerToPlayerTable(Match match) {
        /*
            Add players to database.
        */
        for (int i = 0; i < 2; i++) {
            Team team = i == 0 ? match.getTeam1() : match.getTeam2();
            org.project.model.player.Player[] players = team.getPlayers();
            for (int j = 0; j < 11; j++) {
                String type = players[j] instanceof Bowler ? "Bowler" : "Batsman";
                this.addPlayer(players[j].getName(),type, 1);
            }
        }
    }

    public void addPlayer(String playerName,String type, int age) {
        playerRepository.addPlayer(playerName,type, age);
    }

    public Stats[] get(Map<String, Object> requestBody) {
        /*
            Return player stats for a specific match.
        */
        return this.setPlayerStatsMetadata(requestBody);
    }

    public Stats[] setPlayerStatsMetadata(Map<String, Object> requestBody) {
        int tournamentId = Integer.parseInt((String) requestBody.get("tournamentId"));
        int team1Id = Integer.parseInt((String) requestBody.get("team1Id"));
        int team2Id = Integer.parseInt((String) requestBody.get("team2Id"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = new Date(System.currentTimeMillis());
        try {
            sqlDate = new Date(dateFormat.parse((String) requestBody.get("date")).getTime());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Date date = sqlDate;
        int playerId = Integer.parseInt((String) requestBody.get("playerId"));
        return this.findStats(tournamentId,team1Id,team2Id,date,playerId);
    }
    public Stats[] findStats(int tournamentId,int team1Id,int team2Id, Date date,int playerId){
        int matchId = matchService.getMatchIdByDate(tournamentId, team1Id, team2Id, date);
        BattingStats battingStats = battingStatsServiceImpl.getBattingStats(matchId, team1Id, playerId);
        BowlingStats bowlingStats = bowlingStatsServiceImpl.getBowlingStats(matchId, team1Id, playerId);
        Stats[] stats = new Stats[2];
        stats[0] = battingStats;
        stats[1] = bowlingStats;
        return stats;
    }
}
