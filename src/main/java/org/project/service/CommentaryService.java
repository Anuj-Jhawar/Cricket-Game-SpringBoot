package org.project.service;

import org.bson.Document;
import org.project.model.Ball;
import org.project.model.BallCommentary;
import org.project.model.CricketGame;
import org.project.repo.CommentaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class CommentaryService {
    @Autowired
    PlayerService playerService;
    @Autowired
    CricketGameService cricketGameService;
    @Autowired
    CommentaryRepository commentaryRepository;
    public void addCommentary(Ball ball, CricketGame game, String commentaryText, int inningNo) {
        /*
            Add commentary of the match after every ball.
        */
        int matchId = cricketGameService.getMatchId(game.getTournamentName(), game.getTeam1().getTeamName(),
                game.getTeam2().getTeamName(), game.getBattingTeamIndex());
        int batsmanID = playerService.getPlayerId(ball.getBatsmanName());
        int bowlerId = playerService.getPlayerId(ball.getBowlerName());
        BallCommentary ballCommentary = new BallCommentary(batsmanID, bowlerId, commentaryText);
        commentaryRepository.updateCommentary(matchId, ballCommentary, inningNo);
    }

    public ArrayList<ArrayList<Document>> getCommentary(int matchId) {
        /*
            Return commentary of the given match.
        */
        return commentaryRepository.getCommentary(matchId);
    }
}
