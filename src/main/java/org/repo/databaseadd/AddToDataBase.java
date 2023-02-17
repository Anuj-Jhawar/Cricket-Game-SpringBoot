package org.repo.databaseadd;


import org.service.cricketgame.CricketGame;
import org.service.cricketgame.Team;
import org.service.player.Player;

public class AddToDataBase {
    CricketGame game;
    public AddToDataBase(CricketGame game){
        this.game = game;
    }
    public void addTeamToTeamTable(Team team){
        AddTeam addTeamToTeamTable = new AddTeam(team);
        addTeamToTeamTable.add();
    }
    public void addMatchToMatchTable(){
        AddMatch addMatchToMatchTable = new AddMatch(game);
        addMatchToMatchTable.add();
    }
    public void addBattingStatsToBattingStatsTable(Team team){
        String teamName = team.getTeamName();
        Player[] players = team.getPlayers();
        for(int i = 0;i<11;i++){
            AddBattingStats addBattingStatsToBattingStatsTable = new AddBattingStats(players[i].getBattingStats(),players[i].getName(),teamName,game);
            addBattingStatsToBattingStatsTable.add();
        }
    }
    public void addBowlingStatsToBowlingStatsTable(Team team){
        String teamName = team.getTeamName();
        Player[] players = team.getPlayers();
        for(int i = 0;i<11;i++){
            AddBowlingStats addBowlingStatsToBowlingStatsTable = new AddBowlingStats(players[i].getBowlingStats(),players[i].getName(),teamName,game);
            addBowlingStatsToBowlingStatsTable.add();
        }
    }
    public void addPlayerToPlayerTable(){
        for(int i = 0;i<2;i++){
            Team team = i==0? game.getTeam1():game.getTeam2();
            Player[] players = team.getPlayers();
            for(int j = 0;j<11;j++){
                AddPlayer addPlayerToPlayerTable = new AddPlayer(players[j]);
                addPlayerToPlayerTable.add();
            }
        }
    }
    public void addToDataBase(){
        /*
            Add respective things to their respective databases.
        */
        this.addTeamToTeamTable(game.getTeam1());
        this.addTeamToTeamTable(game.getTeam2());
        this.addMatchToMatchTable();
        this.addPlayerToPlayerTable();
        this.addBowlingStatsToBowlingStatsTable(game.getTeam1());
        this.addBowlingStatsToBowlingStatsTable(game.getTeam2());
        this.addBattingStatsToBattingStatsTable(game.getTeam1());
        this.addBattingStatsToBattingStatsTable(game.getTeam2());

    }
}
