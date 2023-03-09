package org.project.service;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;
import org.project.model.Ball;
import org.project.model.BallCommentary;
import org.project.model.Match;
import org.project.repo.CommentaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@NoArgsConstructor
@Data
public class CommentaryServiceImpl implements CommentaryService{

    @Autowired
    private PlayerServiceImpl playerServiceImpl;
    @Autowired
    private MatchServiceImpl matchService;
    @Autowired
    private CommentaryRepository commentaryRepository;

    public void addCommentary(Ball ball, Match match, String commentaryText, int inningNo) {
        /*
            Add commentary of the match after every ball.
        */
        int matchId = matchService.getMatchId(match.getTournamentName(), match.getTeam1().getTeamName(),
                match.getTeam2().getTeamName(), match.getBattingTeamIndex());
        int batsmanID = playerServiceImpl.getPlayerId(ball.getBatsmanName());
        int bowlerId = playerServiceImpl.getPlayerId(ball.getBowlerName());
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
