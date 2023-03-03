package org.project.controller;

import org.bson.Document;
import org.project.model.ScoreCardForPlayer;
import org.project.model.stats.Stats;
import org.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class GetApis {
    @Autowired
    CricketGameService cricketGameService;
    @Autowired
    TournamentService tournamentService;
    @Autowired
    ScoreCardService scoreCardService;
    @Autowired
    PlayerStatsService playerStatsService;
    @Autowired
    CommentaryService commentaryService;
    @PostMapping("/scorecard")
    public ArrayList<ArrayList<ScoreCardForPlayer>> getScoreCardForGivenMatch(
            @RequestBody Map<String, Object> requestBody) {
        /*
            Four request variable that's why converted it to the post request.
        */
        return scoreCardService.get(requestBody);
    }

    @PostMapping("/playerStats")
    public Stats[] getPlayerDetailsForAParticularMatch(@RequestBody Map<String, Object> requestBody) {
        /*
            Four request variable that's why converted it to the post request.
        */
        return playerStatsService.get(requestBody);
    }

    @GetMapping("/startTournament/{tournamentName}")
    public String startANewTournament(@PathVariable String tournamentName) {
        tournamentService.start(tournamentName);
        return "Tournament Started";
    }

    @GetMapping("/getCommentary/{matchId}")
    public ArrayList<ArrayList<Document>> getCommentary(@PathVariable int matchId) {
        return commentaryService.getCommentary(matchId);
    }

    @PostMapping("/startGame")
    public String startGame(@RequestBody Map<String, Object> requestBody) {
        return cricketGameService.setUpGame(requestBody);
    }
}
