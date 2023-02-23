package org.project.service;

import org.bson.Document;
import org.project.model.BallCommentary;
import org.project.repo.CommentaryDB;
import org.project.repo.MatchDB;
import org.project.model.Ball;
import org.project.model.CricketGame;

import java.util.ArrayList;

public class CommentaryService {
    public void addCommentary(Ball ball, CricketGame game,String commentaryText,int inningNo){
        MatchDB matchDB = new MatchDB();
        int matchId = matchDB.getMatchId(game.getTournamentName(),game.getTeam1().getTeamName(),game.getTeam2().getTeamName(), game.getBattingTeamIndex());
        PlayerService playerService = new PlayerService();
        int batsmanID = playerService.getPlayerId(ball.getBatsmanName());
        int bowlerId = playerService.getPlayerId(ball.getBowlerName());
        BallCommentary ballCommentary = new BallCommentary(batsmanID,bowlerId,commentaryText);
        CommentaryDB commentaryDB = new CommentaryDB();
        commentaryDB.updateCommentary(matchId,ballCommentary,inningNo);
    }
    public ArrayList<ArrayList<Document>> getCommentary(int matchId){
        CommentaryDB commentaryDB = new CommentaryDB();
        return commentaryDB.getCommentary(matchId);
    }
}