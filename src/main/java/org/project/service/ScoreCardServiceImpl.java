package org.project.service;

import lombok.Data;
import org.project.dto.ScoreCardDTO;
import org.project.model.ScoreCardForPlayer;
import org.project.repo.BattingStatsRepository;
import org.project.repo.BowlingStatsRepository;
import org.project.repo.JdbcConnection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

@Service
@Data
public class ScoreCardServiceImpl implements ScoreCardService {

    private int tournamentId;
    private int team1Id;
    private int team2Id;
    private Date date;
    @Autowired
    private MatchServiceImpl matchService;
    @Autowired
    private BattingStatsRepository battingStatsRepository;
    @Autowired
    private BowlingStatsRepository bowlingStatsRepository;

    @Override
    public ArrayList<ArrayList<ScoreCardForPlayer>> get(ScoreCardDTO scoreCardDTO) {
        /*
            Return scorecard for a given match.
        */
        tournamentId = scoreCardDTO.getTournamentId();
        team1Id = scoreCardDTO.getTeam1Id();
        team2Id = scoreCardDTO.getTeam2Id();
        date = scoreCardDTO.getDate();
        JdbcConnection.initializeConnection();
        ArrayList<ArrayList<ScoreCardForPlayer>> stats = new ArrayList<>();
        Connection connection = JdbcConnection.getConnection();
        int matchId = getMatchId(tournamentId, team1Id, team2Id, date, connection);
        int battingFirstTeamId = matchService.findBattingFirstTeam(matchId);
        int battingSecondTeamId = battingFirstTeamId == team1Id ? team2Id : team1Id;

        stats.add(getBattingScoreCard(battingFirstTeamId, matchId, connection));
        stats.add(getBowlingScoreCard(battingSecondTeamId, matchId, connection));
        stats.add(getBattingScoreCard(battingSecondTeamId, matchId, connection));
        stats.add(getBowlingScoreCard(battingFirstTeamId, matchId, connection));
        return stats;
    }

    @Override
    public int getMatchId(int tournamentId, int team1Id, int team2Id, Date date, Connection connection) {
        int matchId = matchService.getMatchIdByDate(tournamentId, team1Id, team2Id, date);
        return matchId;
    }

    @Override
    public ArrayList<ScoreCardForPlayer> getBattingScoreCard(int teamId, int matchId, Connection connection) {
        ArrayList<ScoreCardForPlayer> teamBattingStats = battingStatsRepository.getBattingScoreCardOfAnInning(matchId,
                teamId, connection);
        return teamBattingStats;
    }

    @Override
    public ArrayList<ScoreCardForPlayer> getBowlingScoreCard(int teamId, int matchId, Connection connection) {
        ArrayList<ScoreCardForPlayer> teamBowlingStats = bowlingStatsRepository.getBowlingScoreCardOfAnInning(matchId,
                teamId, connection);
        return teamBowlingStats;
    }
}
