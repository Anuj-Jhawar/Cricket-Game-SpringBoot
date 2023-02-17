package org.controller;

import org.service.apiservices.GetPlayerStatsForAGivenMatch;
import org.service.apiservices.GetScoreCard;
import org.service.cricketgamecontroller.PlayTournament;
import org.service.scorecardforplayer.ScoreCardForPlayer;
import org.service.stats.Stats;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

@RestController
public class GetApis {
    @PostMapping("/scorecard")
    public ArrayList<ArrayList<ScoreCardForPlayer>> getScoreCardForGivenMatch(@RequestBody Map<String,Object> requestBody) {
        /*
            Four request variable that's why converted it to the post request.
        */
        GetScoreCard getScoreCard = new GetScoreCard(requestBody);
        return getScoreCard.get();
    }
    @PostMapping("/playerStats")
    public Stats[] getPlayerDetailsForAParticularMatch(@RequestBody Map<String,Object> requestBody) {
        /*
            Four request variable that's why converted it to the post request.
        */
        GetPlayerStatsForAGivenMatch getPlayerStatsForAGivenMatch = new GetPlayerStatsForAGivenMatch(requestBody);
        return getPlayerStatsForAGivenMatch.get();
    }
    @GetMapping("/startTournament")
    public String startANewTournament() {
        PlayTournament playTournament = new PlayTournament();
        playTournament.main();
        return "Tournament Started";
    }
}
