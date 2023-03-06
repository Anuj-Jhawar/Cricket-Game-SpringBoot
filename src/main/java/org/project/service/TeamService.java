package org.project.service;

import lombok.NoArgsConstructor;
import org.project.model.player.Player;
import org.project.repo.TeamRepository;
import org.project.utilities.PlayerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
@NoArgsConstructor
public class TeamService {

    @Autowired
    private TeamRepository teamRepository;

    public org.project.model.Team setTeamRepository(Map<String, Object> document) {
        /*
            Create team from the user input and return it.
        */
        String teamName = (String) document.get("teamName");
        org.project.model.Team team = new org.project.model.Team();
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

    public String addTeamToTeamTable(org.project.model.Team team) {
        /*
            Add team.
        */
        return this.teamRepository.addTeam(team.getTeamName());
    }

    public int getTeamId(String teamName) {
        return teamRepository.getTeamId(teamName);
    }
}
