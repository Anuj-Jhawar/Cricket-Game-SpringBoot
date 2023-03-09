package org.project.service;

import org.project.model.ScoreCardForPlayer;
import org.project.repo.JdbcConnection;

import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Map;

public interface ScoreCardService {
    public ArrayList<ArrayList<ScoreCardForPlayer>> get(Map<String, Object> requestBody);

    public void setScoreCardService(Map<String, Object> requestBody);

    public int getMatchId(int tournamentId, int team1Id, int team2Id, Date date, Connection connection);

    public ArrayList<ScoreCardForPlayer> getBattingScoreCard(int teamId, int matchId, Connection connection);

    public ArrayList<ScoreCardForPlayer> getBowlingScoreCard(int teamId, int matchId, Connection connection);
}
