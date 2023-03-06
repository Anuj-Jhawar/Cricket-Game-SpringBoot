package org.project.controller;

import org.bson.Document;
import org.project.model.ScoreCardForPlayer;
import org.project.model.stats.Stats;
import org.project.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@RestController
public class GetApis {

    private static final Logger LOGGER = LoggerFactory.getLogger(GetApis.class);
    @Autowired
    private CricketGameService cricketGameService;
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
        LOGGER.info("Request for Scorecard for tournament_id {}, team1_id {}, team2_id {}",
                requestBody.get("tournamentId"), requestBody.get("team1Id"), requestBody.get("team2Id"));
        return scoreCardService.get(requestBody);
    }

    @PostMapping("/playerStats")
    public Stats[] getPlayerDetailsForAParticularMatch(@RequestBody Map<String, Object> requestBody) {
        /*
            Four request variable that's why converted it to the post request.
        */
        LOGGER.info("Request for Player Stats for tournament_id {}, team1_id {}, team2_id {}",
                requestBody.get("tournamentId"), requestBody.get("team1Id"), requestBody.get("team2Id"));
        return playerStatsService.get(requestBody);
    }

    @GetMapping("/startTournament/{tournamentName}")
    public String startANewTournament(@PathVariable String tournamentName) {
        LOGGER.info("Request for starting a tournament with name {}", tournamentName);
        tournamentService.start(tournamentName);
        return "Tournament Started";
    }

    @GetMapping("/getCommentary/{matchId}")
    public ArrayList<ArrayList<Document>> getCommentary(@PathVariable int matchId) {
        LOGGER.info("Request for Commentary of a match with id {}", matchId);
        return commentaryService.getCommentary(matchId);
    }

    @PostMapping("/startGame")
    public String startGame(@RequestBody Map<String, Object> requestBody) {
        LOGGER.info("Request for starting a match between team1_id {} and team2_id {} in tournament_id {}",
                requestBody.get("team1Id"), requestBody.get("team2Id"), requestBody.get("tournamentId"));
        return cricketGameService.setUpGame(requestBody);
    }
}
