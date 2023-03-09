package org.project.controller;

import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.project.dto.PlayerStatsDTO;
import org.project.dto.ScoreCardDTO;
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
    private MatchServiceImpl matchService;
    @Autowired
    private TournamentServiceImpl tournamentServiceImpl;
    @Autowired
    private ScoreCardServiceImpl scoreCardServiceImpl;
    @Autowired
    private PlayerServiceImpl playerService;
    @Autowired
    private CommentaryServiceImpl commentaryService;

    @PostMapping("/scorecard")
    public ArrayList<ArrayList<ScoreCardForPlayer>> getScoreCardForGivenMatch(@RequestBody ScoreCardDTO scoreCardDTO) {
        /*
            Four request variable that's why converted it to the post request.
        */
        log.info("Request for Scorecard for tournament_id {}, team1_id {}, team2_id {}", scoreCardDTO.getTournamentId(),
                scoreCardDTO.getTeam1Id(), scoreCardDTO.getTeam2Id());
        return scoreCardServiceImpl.get(scoreCardDTO);
    }

    @PostMapping("/playerStats")
    public Stats[] getPlayerDetailsForAParticularMatch(@RequestBody PlayerStatsDTO playerStatsDTO) {
        /*
            Four request variable that's why converted it to the post request.
        */
        log.info("Request for Player Stats for tournament_id {}, team1_id {}, team2_id {}",
                playerStatsDTO.getTournamentId(), playerStatsDTO.getTeam1Id(), playerStatsDTO.getTeam2Id());
        return playerService.get(playerStatsDTO);
    }

    @GetMapping("/startTournament/{tournamentName}")
    public String startANewTournament(@PathVariable String tournamentName) {
        log.info("Request for starting a tournament with name {}", tournamentName);
        tournamentServiceImpl.start(tournamentName);
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
