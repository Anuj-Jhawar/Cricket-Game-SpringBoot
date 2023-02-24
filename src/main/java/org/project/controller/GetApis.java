package org.project.controller;

import org.bson.Document;
import org.project.model.ScoreCardForPlayer;
import org.project.model.stats.Stats;
import org.project.service.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class GetApis {
    @PostMapping("/scorecard")
    public ArrayList<ArrayList<ScoreCardForPlayer>> getScoreCardForGivenMatch(@RequestBody Map<String, Object> requestBody) {
        /*
            Four request variable that's why converted it to the post request.
        */
        ScoreCardService scoreCardService = new ScoreCardService(requestBody);
        return scoreCardService.get();
    }

    @PostMapping("/playerStats")
    public Stats[] getPlayerDetailsForAParticularMatch(@RequestBody Map<String, Object> requestBody) {
        /*
            Four request variable that's why converted it to the post request.
        */
        PlayerStatsService playerStatsService = new PlayerStatsService(requestBody);
        return playerStatsService.get();
    }

    @GetMapping("/startTournament/{tournamentName}")
    public String startANewTournament(@PathVariable String tournamentName) {
        TournamentService tournamentService = new TournamentService();
        tournamentService.start(tournamentName);
        return "Tournament Started";
    }

    @GetMapping("/getCommentary/{matchId}")
    public ArrayList<ArrayList<Document>> getCommentary(@PathVariable int matchId) {
        CommentaryService commentaryService = new CommentaryService();
        return commentaryService.getCommentary(matchId);
    }

    @PostMapping("/startGame")
    public String startGame(@RequestBody Map<String, Object> requestBody) {
        CricketGameService cricketGameService = new CricketGameService();
        return cricketGameService.setUpGame(requestBody);
    }
}
