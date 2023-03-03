package org.project.service;

import lombok.NoArgsConstructor;
import org.project.model.Team;
import org.project.model.player.Player;
import org.project.repo.TeamDB;
import org.project.utilities.PlayerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
@NoArgsConstructor
public class TeamService {
    @Autowired
    TeamDB teamDB;
    public Team setTeam(Map<String, Object> document) {
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
    public String addTeamToTeamTable(Team team) {
        /*
            Add team.
        */
        return teamDB.addTeam(team.getTeamName());
    }
    public int getTeamId(String teamName){
        return teamDB.getTeamId(teamName);
    }
}
