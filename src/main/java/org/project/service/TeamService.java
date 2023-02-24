package org.project.service;

import org.bson.Document;
import org.project.model.Team;
import org.project.model.player.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;


public class TeamService {
    public static Team setTeam(Map<String,Object> document){
        String teamName = (String)document.get("teamName");
        Team team = new Team();
        team.setTeamName(teamName);
        Player[] players = new Player[11];
        int i = 0;
        ArrayList<Map<String,Object>> playerDocuments = (ArrayList<Map<String,Object>>)document.get("players");
        for(Map<String,Object> playerDocument : playerDocuments){
            PlayerFactoryService playerFactoryService = new PlayerFactoryService();
            players[i] = playerFactoryService.getPlayer((String)playerDocument.get("type"));
            players[i].setName((String)playerDocument.get("name"));
            i++;
        }
        team.setPlayers(players);
        return team;
    }
}
