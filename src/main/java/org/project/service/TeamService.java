package org.project.service;

import org.project.model.Team;
import org.project.model.player.Player;

import java.util.ArrayList;
import java.util.Map;


public class TeamService {
    public static Team setTeam(Map<String, Object> document) {
        /*
            Create team from the user input and return it.
        */
        String teamName = (String) document.get("teamName");
        Team team = new Team();
        team.setTeamName(teamName);
        Player[] players = new Player[11];
        int i = 0;
        ArrayList<Map<String, Object>> playerDocuments = (ArrayList<Map<String, Object>>) document.get("players");
        for (Map<String, Object> playerDocument : playerDocuments) {
            PlayerFactory playerFactory = new PlayerFactory();
            players[i] = playerFactory.getPlayer((String) playerDocument.get("type"));
            players[i].setName((String) playerDocument.get("name"));
            i++;
        }
        team.setPlayers(players);
        return team;
    }
}
