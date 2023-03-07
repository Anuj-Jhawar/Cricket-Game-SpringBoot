package org.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.project.model.ScoreCardForPlayer;
import org.project.model.stats.Stats;
import org.project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
@Slf4j
public class GetApis {
    @Autowired
    private MatchService matchService;
    @Autowired
    private TournamentService tournamentService;
    @Autowired
    private ScoreCardService scoreCardService;
    @Autowired
    private PlayerStatsService playerStatsService;
    @Autowired
    private CommentaryService commentaryService;

    @PostMapping("/scorecard")
    public ArrayList<ArrayList<ScoreCardForPlayer>> getScoreCardForGivenMatch(
            @RequestBody Map<String, Object> requestBody) {
        /*
            Four request variable that's why converted it to the post request.
        */
        log.info("Request for Scorecard for tournament_id {}, team1_id {}, team2_id {}",
                requestBody.get("tournamentId"), requestBody.get("team1Id"), requestBody.get("team2Id"));
        return scoreCardService.get(requestBody);
    }

    @PostMapping("/playerStats")
    public Stats[] getPlayerDetailsForAParticularMatch(@RequestBody Map<String, Object> requestBody) {
        /*
            Four request variable that's why converted it to the post request.
        */
        log.info("Request for Player Stats for tournament_id {}, team1_id {}, team2_id {}",
                requestBody.get("tournamentId"), requestBody.get("team1Id"), requestBody.get("team2Id"));
        return playerStatsService.get(requestBody);
    }

    @GetMapping("/startTournament/{tournamentName}")
    public String startANewTournament(@PathVariable String tournamentName) {
        log.info("Request for starting a tournament with name {}", tournamentName);
        tournamentService.start(tournamentName);
        return "Tournament Started";
    }

    @GetMapping("/getCommentary/{matchId}")
    public ArrayList<ArrayList<Document>> getCommentary(@PathVariable int matchId) {
        log.info("Request for Commentary of a match with id {}", matchId);
        return commentaryService.getCommentary(matchId);
    }

    @PostMapping("/startMatch")
    public String startMatch(@RequestBody Map<String, Object> requestBody) {
        log.info("Request for starting a match between team1_id {} and team2_id {} in tournament_id {}",
                requestBody.get("team1Id"), requestBody.get("team2Id"), requestBody.get("tournamentId"));
        return matchService.setUpMatch(requestBody);
    }
}
