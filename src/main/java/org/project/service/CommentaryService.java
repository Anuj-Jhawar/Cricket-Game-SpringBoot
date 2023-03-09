package org.project.service;

import org.bson.Document;
import org.project.model.Ball;
import org.project.model.Match;

import java.util.ArrayList;

public interface CommentaryService {

    public void addCommentary(Ball ball, Match match, String commentaryText, int inningNo);

    public ArrayList<ArrayList<Document>> getCommentary(int matchId);
}
