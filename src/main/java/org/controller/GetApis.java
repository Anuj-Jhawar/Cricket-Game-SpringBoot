package org.controller;

import org.service.PlayerStatsService;
import org.service.ScoreCardService;
import org.service.TournamentService;
import org.model.scorecardforplayer.ScoreCardForPlayer;
import org.model.stats.Stats;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class GetApis {
    @PostMapping("/scorecard")
    public ArrayList<ArrayList<ScoreCardForPlayer>> getScoreCardForGivenMatch(@RequestBody Map<String,Object> requestBody) {
        /*
            Four request variable that's why converted it to the post request.
        */
        ScoreCardService scoreCardService = new ScoreCardService(requestBody);
        return scoreCardService.get();
    }
    @PostMapping("/playerStats")
    public Stats[] getPlayerDetailsForAParticularMatch(@RequestBody Map<String,Object> requestBody) {
        /*
            Four request variable that's why converted it to the post request.
        */
        PlayerStatsService playerStatsService = new PlayerStatsService(requestBody);
        return playerStatsService.get();
    }
    @GetMapping("/startTournament")
    public String startANewTournament() {
        TournamentService tournamentService = new TournamentService();
        tournamentService.start();
        return "Tournament Started";
    }
}
