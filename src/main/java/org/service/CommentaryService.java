package org.service;

import org.model.Ball;
import org.model.BallCommentary;
import org.model.CricketGame;
import org.repo.CommentaryDB;
import org.repo.MatchDB;
import org.repo.PlayerDB;

public class CommentaryService {
    public void addCommentary(Ball ball, CricketGame game,String commentaryText){
        MatchDB matchDB = new MatchDB();
        int matchId = matchDB.getMatchId(game.getTournamentName(),game.getTeam1().getTeamName(),game.getTeam2().getTeamName(), game.getBattingTeamIndex());
        PlayerService playerService = new PlayerService();
        int batsmanID = playerService.getPlayerId(ball.getBatsmanName());
        int bowlerId = playerService.getPlayerId(ball.getBowlerName());
        BallCommentary ballCommentary = new BallCommentary(batsmanID,bowlerId,commentaryText);
        CommentaryDB commentaryDB = new CommentaryDB();
        commentaryDB.addCommentary(matchId,ballCommentary);
    }
}
