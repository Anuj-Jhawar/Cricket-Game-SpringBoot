package org.project.service;

import org.project.dto.ScoreCardDTO;
import org.project.model.ScoreCardForPlayer;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

public interface ScoreCardService {

    public ArrayList<ArrayList<ScoreCardForPlayer>> get(ScoreCardDTO scoreCardDTO);


    public int getMatchId(int tournamentId, int team1Id, int team2Id, Date date, Connection connection);

    public ArrayList<ScoreCardForPlayer> getBattingScoreCard(int teamId, int matchId, Connection connection);

    public ArrayList<ScoreCardForPlayer> getBowlingScoreCard(int teamId, int matchId, Connection connection);
}
