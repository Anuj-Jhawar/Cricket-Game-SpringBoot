package org.project.service;


import org.project.model.CricketGame;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.repo.*;

public class DataBaseService {
    CricketGame game;

    public DataBaseService(CricketGame game) {
        this.game = game;
    }

    public String addTeamToTeamTable(Team team) {
        /*
            Add team.
        */
        TeamDB teamDB = new TeamDB(team.getTeamName());
        return teamDB.addTeam();
    }

    public void addMatchToMatchTable() {
        /*
            Add match.
        */
        MatchDB matchDB = new MatchDB();
        matchDB.addMatch(game.getTournamentName(), game.getTeam1().getTeamName(), game.getTeam2().getTeamName(), game.getBattingTeamIndex());
    }

    public void addBattingStatsToBattingStatsTable(CricketGame game, Team team) {
        /*
            Add batting stats for match.
        */
        String teamName = team.getTeamName();
        Player[] players = team.getPlayers();
        for (int i = 0; i < 11; i++) {
            MatchDB matchDB = new MatchDB();
            TeamDB teamDB = new TeamDB(teamName);
            PlayerDB playerDB = new PlayerDB();
            int matchId = matchDB.getMatchId(game.getTournamentName(), game.getTeam1().getTeamName(), game.getTeam2().getTeamName(), game.getBattingTeamIndex());
            int teamId = teamDB.getTeamId();
            int playerId = playerDB.getPlayerId(players[i].getName());
            BattingStatsDB battingStatsDB = new BattingStatsDB(matchId, teamId, playerId);
            battingStatsDB.addBattingStats(players[i].getBattingStats());
        }
    }

    public void addBowlingStatsToBowlingStatsTable(CricketGame game, Team team) {
        /*
            Add bowling stats for match.
        */
        String teamName = team.getTeamName();
        Player[] players = team.getPlayers();
        for (int i = 0; i < 11; i++) {
            MatchDB matchDB = new MatchDB();
            TeamDB teamDB = new TeamDB(teamName);
            PlayerDB playerDB = new PlayerDB();
            int matchId = matchDB.getMatchId(game.getTournamentName(), game.getTeam1().getTeamName(), game.getTeam2().getTeamName(), game.getBattingTeamIndex());
            int teamId = teamDB.getTeamId();
            int playerId = playerDB.getPlayerId(players[i].getName());
            BowlingStatsDB bowlingStatsDB = new BowlingStatsDB(matchId, teamId, playerId);
            bowlingStatsDB.addBowlingStats(players[i].getBowlingStats());
        }
    }

    public void addPlayerToPlayerTable() {
        /*
            Add players to database.
        */
        for (int i = 0; i < 2; i++) {
            Team team = i == 0 ? game.getTeam1() : game.getTeam2();
            Player[] players = team.getPlayers();
            for (int j = 0; j < 11; j++) {
                PlayerDB playerDB = new PlayerDB();
                playerDB.addPlayer(players[j].getName(), 1);
            }
        }
    }

    public void addToDataBase() {
        /*
            Add respective things to their respective databases.
        */
        JdbcConnection.initializeConnection();
        game.getTeam1().setTeamName(this.addTeamToTeamTable(game.getTeam1()));
        game.getTeam2().setTeamName(this.addTeamToTeamTable(game.getTeam2()));
        this.addMatchToMatchTable();
        this.addPlayerToPlayerTable();
        this.addBowlingStatsToBowlingStatsTable(game, game.getTeam1());
        this.addBowlingStatsToBowlingStatsTable(game, game.getTeam2());
        this.addBattingStatsToBattingStatsTable(game, game.getTeam1());
        this.addBattingStatsToBattingStatsTable(game, game.getTeam2());

    }
}
