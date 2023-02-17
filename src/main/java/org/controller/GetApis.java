package org.controller;

import org.service.apiservices.GetPlayerStatsForAGivenMatch;
import org.service.apiservices.GetScoreCard;
import org.service.cricketgamecontroller.PlayTournament;
import org.service.scorecardforplayer.ScoreCardForPlayer;
import org.service.stats.Stats;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.util.ArrayList;

@RestController
public class GetApis {
    @GetMapping("/scorecard/{tournamentName}/{team1Name}/{team2Name}/{date}")
    public ArrayList<ArrayList<ScoreCardForPlayer>> getScoreCardForGivenMatch(@PathVariable String tournamentName, @PathVariable String team1Name, @PathVariable String team2Name, @PathVariable Date date) {
        GetScoreCard getScoreCard = new GetScoreCard(tournamentName,team1Name,team2Name,date);
        return getScoreCard.get();
    }
    @GetMapping("/details/{tournamentName}/{team1Name}/{team2Name}/{date}/{playerName}")
    public Stats[] getPlayerDetailsForAParticularMatch(@PathVariable String tournamentName, @PathVariable String team1Name, @PathVariable String team2Name, @PathVariable String playerName, @PathVariable Date date) {
        GetPlayerStatsForAGivenMatch getPlayerStatsForAGivenMatch = new GetPlayerStatsForAGivenMatch(tournamentName,team1Name,team2Name,date,playerName);
        return getPlayerStatsForAGivenMatch.get();
    }
    @GetMapping("/startTournament")
    public String startANewTournament() {
        PlayTournament playTournament = new PlayTournament();
        playTournament.main();
        return "Tournament Started";
    }
}
