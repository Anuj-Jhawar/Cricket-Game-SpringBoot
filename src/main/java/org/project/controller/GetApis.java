package org.project.controller;

import org.bson.Document;
import org.project.service.CommentaryService;
import org.project.service.PlayerStatsService;
import org.project.service.ScoreCardService;
import org.project.service.TournamentService;
import org.project.model.ScoreCardForPlayer;
import org.project.model.stats.Stats;
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
    @GetMapping("/getCommentary/{matchId}")
    public ArrayList<ArrayList<Document>> getCommentary(@PathVariable int matchId){
        CommentaryService commentaryService = new CommentaryService();
        return commentaryService.getCommentary(matchId);
    }
}
