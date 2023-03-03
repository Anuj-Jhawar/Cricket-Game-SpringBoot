package org.project.service;


import lombok.Data;
import org.project.model.CricketGame;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Data
public class DataBaseService {
    @Autowired
    TeamService teamService;
    @Autowired
    PlayerService playerService;
    @Autowired
    BattingStatsService battingStatsService;
    @Autowired
    BowlingStatsService bowlingStatsService;
    @Autowired
    CricketGameService cricketGameService;
    public void addMatchToMatchTable(CricketGame game) {
        /*
            Add match.
        */
        cricketGameService.addMatch(game.getTournamentName(), game.getTeam1().getTeamName(), game.getTeam2().getTeamName(),
                game.getBattingTeamIndex());
    }

    public void addBattingStatsToBattingStatsTable(CricketGame game, Team team) {
        /*
            Add batting stats for match.
        */
        String teamName = team.getTeamName();
        Player[] players = team.getPlayers();
        for (int i = 0; i < 11; i++) {
            int matchId = cricketGameService.getMatchId(game.getTournamentName(), game.getTeam1().getTeamName(),
                    game.getTeam2().getTeamName(), game.getBattingTeamIndex());
            int teamId = teamService.getTeamId(teamName);
            int playerId = playerService.getPlayerId(players[i].getName());
            battingStatsService.addBattingStats(players[i].getBattingStats(),matchId,teamId,playerId);
        }
    }

    public void addBowlingStatsToBowlingStatsTable(CricketGame game, Team team) {
        /*
            Add bowling stats for match.
        */
        String teamName = team.getTeamName();
        Player[] players = team.getPlayers();
        for (int i = 0; i < 11; i++) {
            int matchId = cricketGameService.getMatchId(game.getTournamentName(), game.getTeam1().getTeamName(),
                    game.getTeam2().getTeamName(), game.getBattingTeamIndex());
            int teamId = teamService.getTeamId(teamName);
            int playerId = playerService.getPlayerId(players[i].getName());
            bowlingStatsService.addBowlingStats(players[i].getBowlingStats(),matchId,teamId,playerId);
        }
    }

    public void addPlayerToPlayerTable(CricketGame game) {
        /*
            Add players to database.
        */
        for (int i = 0; i < 2; i++) {
            Team team = i == 0 ? game.getTeam1() : game.getTeam2();
            Player[] players = team.getPlayers();
            for (int j = 0; j < 11; j++) {
                playerService.addPlayer(players[j].getName(),1);
            }
        }
    }

    public void addToDataBase(CricketGame game) {
        /*
            Add respective things to their respective databases.
        */
        JdbcConnection.initializeConnection();
        game.getTeam1().setTeamName(teamService.addTeamToTeamTable(game.getTeam1()));
        game.getTeam2().setTeamName(teamService.addTeamToTeamTable(game.getTeam2()));
        this.addMatchToMatchTable(game);
        this.addPlayerToPlayerTable(game);
        this.addBowlingStatsToBowlingStatsTable(game, game.getTeam1());
        this.addBowlingStatsToBowlingStatsTable(game, game.getTeam2());
        this.addBattingStatsToBattingStatsTable(game, game.getTeam1());
        this.addBattingStatsToBattingStatsTable(game, game.getTeam2());
    }
}
